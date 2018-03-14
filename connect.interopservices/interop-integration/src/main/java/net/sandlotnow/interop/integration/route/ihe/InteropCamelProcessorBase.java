/**
 *======================================================================================
 * InteropCamelProcessorBase.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropCamelProcessorBase
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Aug 6, 2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:  	Description 
 *  				Design Pattern(s):
 *  				 - Factory Method
 *  				 - Chain of Authority
 *  				 - Facade
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.interop.integration.route.ihe;

import net.sandlotnow.interop.ihe.data.InteropIheDataService;
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.integration.route.InteropOidQualifier;
import net.sandlotnow.interop.integration.route.InteropRouteConstants;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang.StringUtils;
import org.openehealth.ipf.commons.ihe.xds.core.validate.ValidationMessage;
import org.openehealth.ipf.commons.ihe.xds.core.validate.XDSMetaDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class InteropCamelProcessorBase {
    
    Logger log = LoggerFactory.getLogger(InteropCamelProcessorBase.class);
    
    ProducerTemplate producer;
    
    boolean sourceDriven = true;
    
    String destinationIdentifier;
        
    @Autowired
    public InteropIheDataService iheDataService;
    
    public Exchange doProcessTransactionEdgeSystem(Exchange exchange, String edgeSystemOIDSource, String edgeSystemOidDestination, String transactionID)
        throws InteropXdsException{

        log.info("Searching configuration of transaction " + transactionID +  " for edge system with source OID=" + edgeSystemOIDSource + " routing to destination OID " + edgeSystemOidDestination );
        String edgeSystemOid = edgeSystemOIDSource;
        boolean usingDestination = false;
        if(StringUtils.isNotBlank(this.destinationIdentifier)){
            edgeSystemOidDestination = this.destinationIdentifier;
        }
        if(!sourceDriven && StringUtils.isNotBlank(edgeSystemOidDestination) || StringUtils.isNotBlank(this.destinationIdentifier)){
            edgeSystemOid = edgeSystemOidDestination;
            usingDestination = true;
        }
        setEdgeSystemHeaders(exchange, null, edgeSystemOIDSource, edgeSystemOidDestination, transactionID, usingDestination);
        EdgeSystem edgeSystem = getEdgeSystem(exchange, transactionID, edgeSystemOid, usingDestination);
        
        String edgeSystemEndpoint = null;

        if(edgeSystem == null){ 
            throw new InteropXdsException(
                    String.format("Edge System not found: %1s", edgeSystemOid)); //TODO revisit these exceptions
        }
        else{
            setEdgeSystemHeaders(exchange, edgeSystem.getId().toString(), edgeSystemOIDSource, edgeSystemOidDestination, transactionID, usingDestination);
            EdgeEndpoint endpoint = null;
            if(usingDestination){
                endpoint = edgeSystem.getReceiverEndpointForTransaction(transactionID);
            }
            else{
                endpoint = edgeSystem.getSenderEndpointForTransaction(transactionID);                
            }
            if(endpoint == null){ 
                throw new InteropXdsException( String.format( 
                        "Edge System %1s not configured with %2s transaction type as a %3s system", edgeSystemOid , transactionID, (usingDestination ? "RECEIVER":"SENDER") ));
            }
            edgeSystemEndpoint = endpoint.getCamelEndpointName();
        }
        
        log.info("Edge System endpoint set to be called " + edgeSystemEndpoint);
        setTargetIheRoute(exchange, edgeSystemEndpoint);
        exchange =  producer.send(edgeSystemEndpoint, exchange);
        //Have to reset the headers before exiting the processor because it may get overwritten by sub processes
        setEdgeSystemHeaders(exchange, edgeSystem.getId().toString(), edgeSystemOIDSource, edgeSystemOidDestination, transactionID, usingDestination);
        return exchange;
    }
    
    public void setEdgeSystemHeaders(Exchange exchange, String edgeSystemId, String edgeSystemOIDSource, String edgeSystemOidDestination, String transactionID, boolean usingDestination){
        
        
        if(usingDestination){
            exchange = InteropExchangeHeadersHelper.fillInteropHeaders(
                    edgeSystemOidDestination, 
                    edgeSystemId, 
                    transactionID, 
                    usingDestination, 
                    exchange);        
        }
        else{    
            exchange = InteropExchangeHeadersHelper.fillInteropHeaders(
                    edgeSystemOIDSource, 
                    edgeSystemId, 
                    transactionID, 
                    usingDestination, 
                    exchange);
                            
        }
    }
    
    public void  setTargetIheRoute(Exchange self, String route) {
        self.getOut().setHeader(InteropRouteConstants.HEADER_TARGET_IHE_ROUTE, route);
        self.setProperty(InteropRouteConstants.HEADER_TARGET_IHE_ROUTE, route);
    }

    public EdgeSystem getEdgeSystem(Exchange self, boolean isDestination) throws InteropXdsException{
        String transactionId = self.getOut().getHeader(InteropRouteConstants.HEADER_EDGESYSTEM_TRANSACTION_ID).toString();
        return getEdgeSystem(self, transactionId, isDestination);
    }
    
    public EdgeSystem getEdgeSystem(Exchange self, String transactionId, boolean isDestination) throws InteropXdsException{
        String edgeSrcOID = self.getOut().getHeader(InteropRouteConstants.HEADER_EDGESYSTEM_OID_SRC).toString();
        return getEdgeSystem(self, transactionId, edgeSrcOID, isDestination);
    }

        
    public EdgeSystem getEdgeSystem(Exchange self, String transactionId, String edgeSystemOID, boolean isDestination) throws InteropXdsException {
                
        if(StringUtils.isEmpty(edgeSystemOID)){
            throw new XDSMetaDataException(ValidationMessage.INVALID_OID, "NULL");            
        }
        EdgeSystem edgeSystem = null;
        
        InteropOidQualifier qualifier = InteropExchangeHeadersHelper.getOidQualifierFromTransaction(transactionId, isDestination);
        
        if(qualifier == InteropOidQualifier.ASSIGNINGAUTHORITY){
            edgeSystem = iheDataService.findEdgeSystemByAssigningAuthorityOID(edgeSystemOID);            
        }
        else if(qualifier == InteropOidQualifier.DEVICEORASSIGNINGAUTHORITY){
            edgeSystem = iheDataService.findEdgeSystemByDeviceOrAssigningAuthorityOID(edgeSystemOID);            
        }
        else if(qualifier == InteropOidQualifier.DEVICE){
            edgeSystem = iheDataService.findEdgeSystemByDevice(edgeSystemOID);            
        }
        else if(qualifier == InteropOidQualifier.REPOSITORYUNIQUEOID){
            edgeSystem = iheDataService.findEdgeSystemByDocumentSourceOID(edgeSystemOID);            
        }
        else if(qualifier == InteropOidQualifier.UNKNOWN){
            throw new InteropXdsException("Unknown qualifier .. unable to find Edge System based on input parameters");
        }
        return edgeSystem;
    }
    
    //Getters and setters

    public void setDestinationIdentifier(String destinationIdentifier) {
        this.destinationIdentifier = destinationIdentifier;
        this.sourceDriven = false;
    }
    
    public String getDestinationIdentifier(){
        return this.destinationIdentifier;
    }

    public boolean isSourceDriven() {
        return sourceDriven;
    }

    public void setSourceDriven(boolean sourceDriven) {
        this.sourceDriven = sourceDriven;
    }

    public void setProducer(ProducerTemplate producer) {
      this.producer = producer;
    }
}
