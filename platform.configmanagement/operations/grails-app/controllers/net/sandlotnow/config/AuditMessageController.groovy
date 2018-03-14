/**
 *======================================================================================
 * AuditMessageController.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- render the audit messages
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			08/10/2015
 *  				Original development
 *  @author 		Devendra Patil
 *  @version 		1.0
 *  Description:  	Active directory element class.
 *
 *
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.config

// System Imports
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException
import java.text.SimpleDateFormat;
import java.text.MessageFormat;

import grails.web.RequestParameter;
import net.sandlotnow.interop.audit.InteropAudit;
import net.sandlotnow.interop.audit.InteropAuditSearch
import net.sandlotnow.interop.audit.InteropAuditStatusFilter;
import net.sandlotnow.interop.audit.InteropAuditUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonFactory
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;

import net.sandlotnow.config.interop.InteropServer
import net.sandlotnow.config.interop.InteropServerConfig
import net.sf.json.JSON

class AuditMessageController {

	def grailsApplication
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	/**
	 * AuditMessageService instance
	 */	
	def auditMessageService
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	private final static String SIMPLE_DATE_FORMAT = "MM/dd/yyyy, h:mm:ss a";
	
	
	//===================================================================
	// PUBLIC FUNCTIONS
	//===================================================================	
	
	/**
	 * This method call the fetchAuditMessages() method of auditMessageService class.
	 * fetchAuditMessages() communicate with service to fetch the list of audit message.
	 * Upon fetching the list of audit message, it will get render on index page.
	 *
	 * @return auditMessageList --> List of Audit message
	 */
	def index() {
		
  	    log.debug("AuditMessageController:index:Start:loading the audit messages")
		
		String startTime = null
		String stopTime = null
		String status = null
		def transType = new ArrayList<String>()
		def serviceUrl = null
		def auditMessageList = null
		def interopAuditSearch = new InteropAuditSearch()
		Date toDate = new Date()		
		Date fromDate = new Date()	
				
		if(StringUtils.isBlank(session.getAttribute(SessionConstants.START_TIME)) && StringUtils.isBlank(session.getAttribute(SessionConstants.STOP_TIME))) {
		
			Calendar cal = new GregorianCalendar();
			// reset hour, minutes, seconds and millis
			cal.set(Calendar.HOUR_OF_DAY, -1);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.DATE, 0);
			
			fromDate = cal.getTime()
			
			startTime = InteropAuditUtils.getStrFromDate(fromDate)
			stopTime = InteropAuditUtils.getStrFromDate(toDate)
			
			status = SessionConstants.ALL
			transType.add(SessionConstants.ITI_18)
			transType.add(SessionConstants.ITI_41)
			transType.add(SessionConstants.ITI_43)
			transType.add(SessionConstants.ITI_44)
			transType.add(SessionConstants.ITI_45)
			transType.add(SessionConstants.ITI_47)
			
			session.setAttribute(SessionConstants.START_TIME, startTime)
			session.setAttribute(SessionConstants.STOP_TIME, stopTime)
			session.setAttribute(SessionConstants.STATUS, status)
			session.setAttribute(SessionConstants.TRANSACTION_TYPE, transType)
			
			interopAuditSearch.startTime = startTime
			interopAuditSearch.stopTime = stopTime
			interopAuditSearch.transactionTypes = transType
			interopAuditSearch.statusFilter = InteropAuditStatusFilter.valueOf(status)
			
			log.debug("AuditMessageController:index:Start:loading the audit messages for time "+fromDate+" to "+toDate)
			
		}else {
		
			startTime = session.getAttribute(SessionConstants.START_TIME)			
			stopTime = session.getAttribute(SessionConstants.STOP_TIME)
			
			toDate = InteropAuditUtils.getDateFromStr(stopTime)
			fromDate = InteropAuditUtils.getDateFromStr(startTime)
			
			status = session.getAttribute(SessionConstants.STATUS)
			transType = session.getAttribute(SessionConstants.TRANSACTION_TYPE)
			
			interopAuditSearch.startTime = startTime
			interopAuditSearch.stopTime = stopTime
			interopAuditSearch.statusFilter = InteropAuditStatusFilter.valueOf(status)
			interopAuditSearch.transactionTypes = transType
			
			log.debug("AuditMessageController:index:Start:loading the audit messages for time "+fromDate+" to "+toDate)		
		}		
		
		serviceUrl = interopServer.getAuditSearch()
		
		auditMessageList = auditMessageService.fetchAuditMessagesBasedOnCriteria(serviceUrl, interopAuditSearch)
			
		session.setAttribute(SessionConstants.AUD_MESSAGE_LIST, auditMessageList)
		
		log.debug("AuditMessageController:index:End:loading the audit messages to index page")
		
		if(auditMessageList == null) {
			
		  render(view:"/error")
		}else {
		
		  render( view : "index",model : [ interopServer: interopServer, auditMessageList : auditMessageList,toDate:getStringFromDate(toDate),fromDate:getStringFromDate(fromDate),transType:transType,status:status,interopAuditSearch:interopAuditSearch])
		}		
	}
	
	/**
	 * Upon click of Particular message ID this method will get called.
	 * This method accept the message ID.
	 * This method send this message id to fetchAuditMessageDetailBasedOnID() of auditMessageService class.
	 * fetchAuditMessageDetailBasedOnID() sent this message id to service to get the list of message id having same
	 * exchnageID.
	 *
	 * Received list of audit message will then render to auditMessageDetail view.
	 *
	 *
	 * @return auditMessageList --> List of Audit message
	 */
	def getAuditMessageDetailOnExchangeID() {
		
		log.debug("AuditMessageController:getAuditMessageDetailOnExchangeID:Start:loading the audit messages based on exchange id")
		
		String auditMessageID = params.id
		
		if(StringUtils.isBlank(auditMessageID)) {
			
			auditMessageID = session.getAttribute(SessionConstants.EXCHANGE_ID)
		}		
		
		def serviceUrl = interopServer.getServiceUrlAuditByExchangeId( auditMessageID)
		
		log.debug("AuditMessageController:getAuditMessageDetailOnExchangeID:Sending exchange id to service method to fetch data")		
		
		def auditMessageList = auditMessageService.fetchAuditMessageDetailBasedOnExchangeID(serviceUrl)
		
		session.setAttribute(SessionConstants.EXCHANGE_ID, auditMessageID)				
		
		if(auditMessageList == null) {
			
		  log.debug("AuditMessageController:getAuditMessageDetailOnExchangeID:END:loading Error page")
		  
		  render(view:"/error")
	    }else {
		   
		  log.debug("AuditMessageController:getAuditMessageDetailOnExchangeID:END:loading audit messages to auditMessageDetailOnExchangeID page")
		
		  render( view : "auditMessageDetailOnExchangeID",model : [ interopServer: interopServer, auditMessageList : auditMessageList, exchangeID:auditMessageID])
	    }		
	}
	
	/**
	 * This method accept the messageID from user.
	 * This method communicate with service class fetchAuditMessageDetailsBasedOnID() method.
	 * Audit message list received from service class fetchAuditMessageDetailsBasedOnID() method
	 * will be render to auditMessageDetails page.
	 *
	 *
	 * @param messageID
	 * @return auditMessageList --> message list will be render to allAuditMessage page
	 */
	
	def getAuditTransactionDetail() {
		
		log.debug("AuditMessageController:getAuditTransactionDetail:Start:loading the audit messages based on id")
				
		String auditMessageId = params.messageID		
		
		// if message ID returned as null return error to gsp
		if(auditMessageId == null) {
			
			render "error"
		}		
		def serviceUrl = interopServer.getServiceUrlAuditById(auditMessageId)
		
		log.debug("AuditMessageController:getAuditTransactionDetail:Sending id to service method to fetch data")
				
		def auditMessage = auditMessageService.fetchAuditTransactionDetails(serviceUrl)		
				
		if(auditMessage == null) {
			
		  log.debug("AuditMessageController:getAuditTransactionDetail:END:loading Error page")
		 	
		  render "error"
	    }else {
		 
		  HashMap<String,String> propertyMap = new HashMap();
		  try {
			  
			  propertyMap = convertStringJSONtoMap(auditMessage.propertiesJson)			  
		  }
		  catch(Exception exception) {
		     
			  render "error"
		  }
		  
		  log.debug("AuditMessageController:getAuditTransactionDetail:END:loading audit messages to auditMessageDetails page")
		  
		  render template : "auditTransactionDetails" , model:[ interopServer: interopServer, auditMessage : auditMessage,"propertyMap":propertyMap]
	    }				
		
	}
	
	/**
	 * This method accept the start time and stop time from user.
	 * This method convert the received string date to Date object.
	 * This method communicate with service class fetchAuditMessages() method.
	 * Audit message list received from service class fetchAuditMessages() method
	 * will be render to allAuditMessage page.
	 *   	
	 * 
	 * @param startDates
	 * @param endDates
	 * @return auditMessageList --> message list will be render to allAuditMessage page
	 */	
	def getAuditMessageDetailOnDates(@RequestParameter('startDates') String startDates,@RequestParameter('endDates')  String endDates,@RequestParameter('status')  String status){
		
 		log.debug("AuditMessageController:getAuditMessageDetailOnDates:Start:loading the audit messages based on startDates and endDates")
		
		List<String> transTypes = request.getParameterValues("transactionValues[]");
        try{
			
			String fromDate = InteropAuditUtils.getStrFromDate(convertStringToDate(startDates))
			
			String toDate = InteropAuditUtils.getStrFromDate(convertStringToDate(endDates))
			
		}catch(Exception ex) {
		
		  ex.printStackTrace()
		}
		String fromDate = InteropAuditUtils.getStrFromDate(convertStringToDate(startDates))
		
		String toDate = InteropAuditUtils.getStrFromDate(convertStringToDate(endDates))	
		
		if(convertStringToDate(endDates).before(convertStringToDate(startDates))) {
			
			flash.message="End Date cannot be grater than Start Date"			
			render "errorDate"
		}else {
		
			session.setAttribute(SessionConstants.START_TIME, fromDate)
			session.setAttribute(SessionConstants.STOP_TIME, toDate)
			
			def serviceUrl = interopServer.getAuditSearch()
			
			def auditSearch = new InteropAuditSearch()
			auditSearch.startTime = fromDate
			auditSearch.stopTime = toDate
			auditSearch.transactionTypes = transTypes
			auditSearch.setStatusFilter(InteropAuditStatusFilter.valueOf(status))
			
			session.setAttribute(SessionConstants.TRANSACTION_TYPE, transTypes)
			session.setAttribute(SessionConstants.STATUS, status)
			
			log.debug("AuditMessageController:getAuditMessageDetailOnDates:Fetching the audit message based on time "+fromDate+" to "+toDate)
			
			def auditMessageList = auditMessageService.fetchAuditMessagesBasedOnCriteria(serviceUrl,auditSearch)
			
			if(auditMessageList == null) {
			  
			  log.debug("AuditMessageController:getAuditMessageDetailOnDates:END:loading Error page")
			  
			  render "error"
			}else {
			 
			  log.debug("AuditMessageController:getAuditMessageDetailOnDates:End:loading audit messages to allAuditMessage page")
			  session.setAttribute(SessionConstants.AUDIT_MSG_LIST, auditMessageList)
			  
			  render template : "allAuditMessage" , model : [ interopServer: interopServer, auditMessageList : auditMessageList]
			}		
		}				
	}
	
	/**
	 * This method accept the messageID from user.
	 * This method communicate with service class fetchAuditMessageDetailsBasedOnMessageID() method.
	 * Audit message list received from service class fetchAuditMessageDetailsBasedOnMessageID() method
	 * will be render to auditMessageDetailOnMessageID page.
	 *
	 *
	 * @param messageID
	 * @return auditMessageList --> message list will be render to auditMessageDetailOnMessageID page
	 */
	def getAuditMessageDetailOnMessageID() {
		
		log.debug("AuditMessageController:getAuditMessageDetailOnMessageID:Start:loading the audit messages based on exchange id")
				
		String auditMessageID =  (String)request.getParameter(SessionConstants.MESSAGE_ID)
		
		if(StringUtils.isBlank(auditMessageID)) {
			
			auditMessageID = session.getAttribute(SessionConstants.AUDIT_MSG_ID)
		}
		
		def serviceUrl = interopServer.getServiceUrlAuditMessageByAuditId(auditMessageID)
		
		log.debug("AuditMessageController:getAuditMessageDetailOnMessageID:Sending exchange id to service method to fetch data")
		
		def auditMessageList = auditMessageService.fetchAuditMessageDetailsBasedOnMessageID(serviceUrl)
		
		session.setAttribute(SessionConstants.AUDIT_MSG_ID, auditMessageID)
		def exchangeID = session.getAttribute(SessionConstants.EXCHANGE_ID)
		
		if(auditMessageList == null) {
			
		  log.debug("AuditMessageController:getAuditMessageDetailOnMessageID:END:loading Error page")
			
		  render(view:"/error")
		}else {
		
		  log.debug("AuditMessageController:getAuditMessageDetailOnMessageID:END:loading audit messages to auditMessageDetailOnMessageID page")
				   
		  render( view : "auditMessageDetailOnMessageID",model : [ interopServer: interopServer, exchangeID: exchangeID, auditMessageList : auditMessageList, messageID:auditMessageID])
		}
	}
	
	/**
	 * This method accept the messageID from user.
	 * This method communicate with service class fetchAuditMessageDetails() method.
	 * Audit message received from service class fetchAuditMessageDetails() method
	 * will be render to auditMessageDetails page.
	 * 
	 * @param messageID
	 * @return auditMessage :- Object of InterOpMessage
	 */
	def getMessageDetails() {
		
		log.debug("AuditMessageController:getMessageDetails:Start:loading the audit messages based on id")
		
		String auditMessageId = params.messageID
		
		// if message ID returned as null return error to gsp
		if(auditMessageId == null) {
			
			render "error"
		}
		
		def serviceUrl = interopServer.getServiceUrlAuditMessageById(auditMessageId)
		
		log.debug("AuditMessageController:getMessageDetails:Sending id to service method to fetch data")
				
		def auditMessage = auditMessageService.fetchAuditMessageDetails(serviceUrl)
		
		if(auditMessage == null) {
			
		  log.debug("AuditMessageController:getMessageDetails:END:loading Error page")
		  render "error"
		}else {
		  
		  log.debug("AuditMessageController:getMessageDetails:END:loading audit messages to auditMessageDetails page")
		  render template : "auditMessageDetails" , model:[ interopServer: interopServer, auditMessage : auditMessage]
		}
	}
	
	/**
	 * Fetch the Audit message based on the Selected Transaction Type
	 * 
	 * @return
	 */
	def getAuditMessageDetailBasedOnTransType(@RequestParameter('status')  String status) {
	
		log.debug("AuditMessageController:getAuditMessageDetailBasedOnTransType:START:loading audit messages to auditMessageDetails page")
		
		List<String> transTypes = request.getParameterValues("transactionValues[]");
		
		List<InteropAudit> auditMessageList = session.getAttribute(SessionConstants.AUDIT_MSG_LIST)
		
		def selectedAuditMessagesBasedOnTXType = new ArrayList<InteropAudit>()
		def transactionStatus = null
				
		if(status.equals("FailedOnly")) {
			
			transactionStatus = true
		}else if(status.equals("SucceededOnly")) {
		
		    transactionStatus = false
		}		
 		
		try {
			
			log.debug("AuditMessageController:getAuditMessageDetailBasedOnTransType:checking and adding audit message based on tx type")
			
			 for (InteropAudit interOpAudit in auditMessageList) {
				 
				 if(transTypes.contains(interOpAudit.transactionType) && transactionStatus == interOpAudit.failed) {
					 
					 selectedAuditMessagesBasedOnTXType.add(interOpAudit)
			     }else if(transTypes.contains(interOpAudit.transactionType) && transactionStatus == null) {
				 
				     selectedAuditMessagesBasedOnTXType.add(interOpAudit)
				 }
			 }			
			log.debug("AuditMessageController:getAuditMessageDetailBasedOnTransType:list pepared")
		}catch(Exception exception) {
		   
		    log.error("AuditMessageController:getAuditMessageDetailBasedOnTransType:END:Error whicle loading the list. Exception : "+exception.getMessage())
		}	

		session.setAttribute(SessionConstants.TRANSACTION_TYPE, transTypes)
		session.setAttribute(SessionConstants.STATUS, status)
		session.setAttribute(SessionConstants.AUDIT_MSG_LIST, auditMessageList)
		log.debug("AuditMessageController:getAuditMessageDetailBasedOnTransType:END:rendering audit messages to auditMessageDetails page")
		
		render template : "allAuditMessage" , model : [ interopServer: interopServer, auditMessageList : selectedAuditMessagesBasedOnTXType]
		
    }
	
	
	//===================================================================
	// PRIVATE FUNCTIONS
	//===================================================================
	
	
	/**
	 * This method use to convert String Date to Date object
	 * 
	 * @param date --> in String format
	 * @return convertertedDate --> String converted Date
	 */
	private Date convertStringToDate(String date) {
		
		log.debug("AuditMessageController:convertStringToDate:Start:Start converting String to date")
				
		SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		Date convertertedDate;	
		try {
		  
		  convertertedDate = format.parse(date);		  
		  
		} catch (Exception e) {
		   
		   log.debug("AuditMessageController:convertStringToDate:Error occured while converting string to date for"+date+". Error is :->"+e.getMessage())
		   render(view:"/error")
		}
		
		log.debug("AuditMessageController:convertStringToDate:End:Successfully converted String to Date")
				
		return convertertedDate;
	}	
	
	/**
	 * This method convert the date into specified date format
	 * 
	 * @param date 
	 * @return String - Simple date format of received date
	 */
	private String getStringFromDate(Date date) {
		
		log.debug("AuditMessageController:getStringFromDate:Start:Start converting Date to String")
		
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		
		log.debug("AuditMessageController:getStringFromDate:End:Successfully converted Date to String")
				
		return sdf.format(date);
	}
	
	/**
	 * 
	 * This method convert the String json into HASHMAP
	 * 
	 * @param properties :- String json of properties  
	 * @return propertyMap - converted JSON map
	 *
	 */
	private convertStringJSONtoMap(String properties) {	
		
		String sampleJson ='''{
		  "CamelToEndpoint":"pdqv3-iti47://localhost:8728/pdqv3-iti47",
		  "CamelCreatedTimestamp":"Wed Oct 28 08:55:46 EDT 2015",
		  "CamelCharsetName":"UTF-8"
		}'''
	  log.debug("Properties : " + properties)	
	  //Workaround for old properties string in database TODO remove once all audits are updated with new code
	  if(properties.startsWith('Properties: ')){
	      properties = properties - 'Properties: '	
	  }
		  
	  JsonFactory factory = new JsonFactory();
	  ObjectMapper mapper = new ObjectMapper(factory);
	  
	  TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>() {};
	  
	  HashMap<String,String> propertyMap=new HashMap()
	  
	  if(StringUtils.isNotBlank(properties)) {
		  
		  propertyMap = mapper.readValue(properties, typeRef);
		  return propertyMap
	  }
	  
	  return propertyMap	  
	}	
		
	
	/**
	 * Get the interop server configuration settings from the Spring context (see conf/spring/resources.groovy).
	 * Currently only one server is supported, should consider being able to configured multiple servers.
	 * @return
	 */
	private InteropServer getInteropServer(){
		
		def sessionServerName = session.getAttribute(SessionConstants.INTEROP_SERVER_NAME)

		def interopServerConfig = grailsApplication.mainContext.getBean('serverConfigMap', InteropServerConfig)
		
		return interopServerConfig.getServer(sessionServerName)
	}
}
