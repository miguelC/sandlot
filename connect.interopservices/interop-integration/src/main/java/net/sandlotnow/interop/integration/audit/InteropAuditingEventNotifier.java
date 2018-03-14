/**
 *======================================================================================
 * SandlotAuditingEventNotifier.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotAuditingEventNotifier
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    An audit event handler. This class is used to record audit events in 
 *                  the integration system's camel context
 *                 
 *  				Design Pattern(s):
 *  				 - None
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/
package net.sandlotnow.interop.integration.audit;

import java.io.IOException;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.management.event.ExchangeCompletedEvent;
import org.apache.camel.management.event.ExchangeCreatedEvent;
import org.apache.camel.management.event.ExchangeFailedEvent;
import org.apache.camel.management.event.ExchangeSendingEvent;
import org.apache.camel.management.event.ExchangeSentEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sandlotnow.interop.audit.InteropAuditService;
import net.sandlotnow.interop.audit.InteropAudit;
import net.sandlotnow.interop.audit.InteropAuditMessage;
import net.sandlotnow.interop.audit.InteropAuditMessageSerializer;
import net.sandlotnow.interop.integration.route.InteropRouteConstants;

@Component
public class InteropAuditingEventNotifier extends EventNotifierSupport {
	
    // If an exchange doesn't fail but you want to mark it as failed in the audits. 
    // Set the exchange property with this name to a string message that you want to display in audit.errorMessage
    public static final String EXCHANGE_PROPERTY_FAILED_EVENT = "AuditingEventNotifier.FAILED";
    public static final String EXCHANGE_PROPERTY_FAILED_EVENT_STACKTRACE = "AuditingEventNotifier.FAILEDSTACKTRACE";
    
	@Autowired
	private InteropAuditService auditService;
	
	/**
	 * This method is called by camel when events are fired such as MessageSentEvent
	 * @param event The event fired by camel
	 */
    public void notify(EventObject event) throws Exception {

        log.info("Event notification received for event " + event.getClass().getName());

        if(event instanceof ExchangeCreatedEvent){
            // This seems to be what's triggered at first in ITI-18 transactions so we have to log it
            ExchangeCreatedEvent created = (ExchangeCreatedEvent) event;
            Exchange exchange = created.getExchange();
            log.info("Created exchange from endpoint: " + created.getExchange().getFromEndpoint());

            InteropAudit audit = new InteropAudit();
            audit.setTimeStamp(new Date());
            audit.setEventName(ExchangeCreatedEvent.class.getSimpleName());

            audit = fillAndPersistAudit(exchange, audit);
            
        }
        else if (event instanceof ExchangeSendingEvent) {
            ExchangeSendingEvent sennding = (ExchangeSendingEvent) event;
            log.info("Sending to: " + sennding.getEndpoint());
        }
        else if (event instanceof ExchangeSentEvent) {
            ExchangeSentEvent sent = (ExchangeSentEvent) event;
            Exchange exchange = sent.getExchange();
            log.info("Exchange sent event notification: It took " + sent.getTimeTaken() + " millis to send to: " + sent.getEndpoint());
            
            InteropAudit audit = new InteropAudit();
            audit.setTimeStamp(new Date());
            audit.setProcessingTimeMillis(sent.getTimeTaken());
            audit.setToEndPoint(sent.getEndpoint().getEndpointKey());
            audit.setEventName(ExchangeSentEvent.class.getSimpleName());
            
            audit = fillAndPersistAudit(exchange, audit);
        }
        else if(event instanceof ExchangeFailedEvent){
            ExchangeFailedEvent failed = (ExchangeFailedEvent) event;
            Exchange exchange = failed.getExchange();
            log.info("Exchange FAILED .. " + exchange.getExchangeId());
            InteropAudit audit = new InteropAudit();
            audit.setTimeStamp(new Date());
            audit.setEventName(ExchangeFailedEvent.class.getSimpleName());
            
            audit = fillAndPersistAudit(exchange, audit);
        }
        else if(event instanceof ExchangeCompletedEvent){
            ExchangeCompletedEvent completed = (ExchangeCompletedEvent) event;
            log.info("Exchange COMPLETED .. " + completed.getExchange().getExchangeId());
        }
 
    }
    
    private InteropAudit fillAndPersistAudit(Exchange exchange, InteropAudit audit){
        audit = fillAuditWithExchangeData(exchange, audit);
        return persistAudit(audit);
    }
    
    private InteropAudit persistAudit(InteropAudit audit){

        log.info("Persisting audit for " + audit.getExchangeId());
        try {
            return auditService.add(audit);
        }
        catch(Exception e){
            log.error("failed to persist audit", e);
            //TODO: Revisit, should we throw exception here??
            return audit;
        }
    }
    
    private InteropAudit fillAuditWithExchangeData(Exchange exchange, InteropAudit audit){

        if(audit == null){
            audit = new InteropAudit();
            audit.setTimeStamp(new Date());            
        }
        if(exchange != null){
            audit.setExchangeId(exchange.getExchangeId());
            audit.setCamelContext(exchange.getContext().getName());
            audit.setRoute(exchange.getFromRouteId());
            if(exchange.getFromEndpoint() != null){
                audit.setFromEndPoint(exchange.getFromEndpoint().getEndpointKey());
            }
            audit.setExchangePattern(exchange.getPattern().toString());
            audit.setPropertiesJson(doStringExchange(exchange));
            audit = this.fillTransactionData(audit, exchange);
            
            log.debug("Built Audit object .. gathering IN Message");
            // Create the message audits for In and Out messages and add them to the audit
            Message inMessage = exchange.getIn();
            InteropAuditMessage auditMessageIn = new InteropAuditMessage();
            auditMessageIn.setDirection(InteropAuditMessage.DIRECTION_IN);            
            auditMessageIn.setMessageBody(serializeMessageBody(inMessage));
            auditMessageIn.setMessageHeadersJson(doMapAsJson(inMessage.getHeaders()));
            auditMessageIn.setMessageBodyType(inMessage.getBody() != null ? inMessage.getBody().getClass().getName() : "");

            if(inMessage.getHeaders().containsKey(InteropRouteConstants.HEADER_CAMEL_MESSAGE_BREADCRUMB_ID)){
                audit.setBreadcrumbId(inMessage.getHeaders().get(InteropRouteConstants.HEADER_CAMEL_MESSAGE_BREADCRUMB_ID).toString());
            }
            
            auditMessageIn.setAudit(audit);
    
            audit.addAuditMessage(auditMessageIn);
    
            log.debug("Built IN Message object .. gathering OUT Message");
            Message outMessage = exchange.getOut();
            if(outMessage != null && outMessage.getBody() != null){                
                InteropAuditMessage auditMessageOut = new InteropAuditMessage();
                auditMessageOut.setDirection(InteropAuditMessage.DIRECTION_OUT);            
                auditMessageOut.setMessageBody(serializeMessageBody(outMessage));
                auditMessageOut.setMessageHeadersJson(doMapAsJson(outMessage.getHeaders()));
                auditMessageOut.setMessageBodyType(outMessage.getBody().getClass().getName());
                auditMessageOut.setAudit(audit);
                audit.addAuditMessage(auditMessageOut);
            }
                        
            // If message failed.. log the errors
            boolean exchangeFailed = exchange.isFailed();
            
            Object eventNotficationOfException = exchange.getProperty(EXCHANGE_PROPERTY_FAILED_EVENT);
            if(eventNotficationOfException != null){
                exchangeFailed = true;
                audit.setExceptionMessage(eventNotficationOfException.toString());
                Object eventNotificationOfExceptionStacktrace = exchange.getProperty(EXCHANGE_PROPERTY_FAILED_EVENT_STACKTRACE);
                if(eventNotificationOfExceptionStacktrace != null){
                    audit.setExceptionStackTrace(eventNotificationOfExceptionStacktrace.toString());
                }
            }
            audit.setFailed(exchangeFailed);
            if(exchange.getException() != null){
                audit.setExceptionMessage(exchange.getException().getMessage());
                String errorStackTrace = ExceptionUtils.getFullStackTrace(exchange.getException());
                audit.setExceptionStackTrace(errorStackTrace);
                log.warn("The exchange contained a failure notification: " + errorStackTrace);
            }
        }
        return audit;
    }
    
    private InteropAudit fillTransactionData(InteropAudit audit, Exchange exchange){
        
        Map<String, Object> properties = exchange.getProperties();
        if(properties.containsKey(InteropRouteConstants.HEADER_EDGESYSTEM_TRANSACTION_ID)){
            audit.setTransactionType(properties.get(InteropRouteConstants.HEADER_EDGESYSTEM_TRANSACTION_ID).toString());
        }
        if(properties.containsKey(InteropRouteConstants.HEADER_EDGESYSTEM_DEST)){
            audit.setReceiver(properties.get(InteropRouteConstants.HEADER_EDGESYSTEM_DEST).toString());
        }
        if(properties.containsKey(InteropRouteConstants.HEADER_EDGESYSTEM_SRC)){
            audit.setSender(properties.get(InteropRouteConstants.HEADER_EDGESYSTEM_SRC).toString());
        }

        if(properties.containsKey(InteropRouteConstants.HEADER_CAMEL_EXCHANGE_SUBJECT_INFO)){
            audit.setSubjectInfo(properties.get(InteropRouteConstants.HEADER_CAMEL_EXCHANGE_SUBJECT_INFO).toString());
        }
        return audit;
    }    
    
    private String serializeMessageBody(Message message){
        if(message == null || message.getBody() == null){
            return null;
        }
        InteropAuditMessageSerializer serializer = getObjectTypeSerializer(message.getBody().getClass().getName());
        if(serializer != null){
            return serializer.serialize(message.getBody());
        }
        return message.toString();
    }
    
    private String serializeObjectWithCustomSerializer(Object obj){

        if(obj == null){
            return null;
        }
        InteropAuditMessageSerializer serializer = getObjectTypeSerializer(obj.getClass().getName());
        if(serializer != null){
            return serializer.serialize(obj);
        }
        return obj.toString();
    }
    
    private InteropAuditMessageSerializer getObjectTypeSerializer(String messageType){
        if(auditService.getSerializerMap() != null){
            return auditService.getSerializerMap().getMessageSerializer(messageType);
        }
        return null;
    }
    
    private String doStringExchange(Exchange exchange){
        StringBuffer buffer = new StringBuffer();
        buffer.append(doMapAsJson(exchange.getProperties()));
        return buffer.toString();
    }
    
    private String doMapAsJson(Map<String, Object> map){        
        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = "";

            Map<String, String> mapString = new HashMap<String, String>();
            for(String key : map.keySet()){
                Object value = map.get(key);
                mapString.put(key, this.serializeObjectWithCustomSerializer(value));
            }

            // convert map to JSON string
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapString);

            return json;

        } catch (JsonGenerationException e) {
            log.error("Error converting map into JSON", e);
        } catch (JsonMappingException e) {
            log.error("Error converting map into JSON", e);
        } catch (IOException e) {
            log.error("Error converting map into JSON", e);
        }
        return null;
        
    }
     
    public boolean isEnabled(EventObject event) {
        // we only want the sent events
        //return event instanceof ExchangeSentEvent;
        return true;
    }
 
    protected void doStart() throws Exception {
        // noop
    }
 
    protected void doStop() throws Exception {
        // noop
    }
 
}