/**
 *======================================================================================
 * AuditMessageService.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- call service and fetch the audit messages and return it to calling method
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
import static groovyx.net.http.ContentType.*

import java.text.SimpleDateFormat
import java.util.Date;

import groovyx.net.http.*
import net.sandlotnow.interop.audit.InteropAudit
import net.sandlotnow.interop.audit.InteropAuditMessage
import net.sandlotnow.interop.audit.InteropAuditSearch;
import net.sandlotnow.interop.audit.InteropAuditUtils

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger

import com.fasterxml.jackson.databind.ObjectMapper;


class AuditMessageService {
	
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	// MessageSouce Object
	def messageSource
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	//Logger for AuthenticationService class
	private static org.apache.log4j.Logger log=Logger.getLogger(AuditMessageService.class)
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	private final static String SIMPLE_DATE_FORMAT = "MM/dd/yyyy, h:mm:ss a";
	
	
	/**
	 * This method fetch the List of audit message from particular time interval.
	 * The method call service URL and expect the response in string JSON format.
	 * Method convert the received string JSON into  List of InteropAudit Object.
	 * This method handle the exception.
	 *
	 * @param serviceUrl
	 * @return interOpAuditList : List of InteropAudit
	 */
	def fetchAuditMessages(String serviceUrl) {
		
		log.debug("AuditMessageService:fetchAuditMessages:start:fetching the audit messages")
		
		List<InteropAudit> interOpAuditList

		String auditMessage = null
								
		// fetch the all audit log message from service
		def fetchAuditMessagesUrl = serviceUrl
		//def fetchAuditMessagesUrl = "http://localhost:8082/interopServices/services/audits/auditService/audit/startTime/201601210000/stopTime/201601221400/status/All"
		
		try {
						
			def http = new HTTPBuilder(fetchAuditMessagesUrl)
			//http.parser.'text/json' = http.parser.'application/json'
			log.debug("AuditMessageService:fetchAuditMessages:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic does response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				//requestContentType : JSON
				headers.Accept = 'application/json'
				// response handler for a success response code
				response.success = { resp, reader ->
					
					auditMessage= reader.text
					log.debug("AuditMessageService:fetchAuditMessages:fetching the response")
					
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("AuditMessageService:fetchAuditMessages:Exception occured. Exception is : "+exception.getMessage())
						
			return interOpAuditList
		}
		
		interOpAuditList = new ArrayList<InteropAudit>()
		
		log.debug("AuditMessageService:fetchAuditMessages:Converting the String Json to InteropAudit Object List")
		
		if(StringUtils.isNotBlank(auditMessage)) {
			
			interOpAuditList = InteropAuditUtils.auditListFromJson(auditMessage.toString())
		}
		
		log.debug("AuditMessageService:fetchAuditMessages:End:Converted the String Json to InteropAudit Object List and returning the result")
				
		return interOpAuditList
	}
	
	/**
	 * This method fetch the List of audit message from particular time interval ,transaction types and status flag.
	 * The method call service URL and expect the response in string JSON format.
	 * Method convert the received string JSON into  List of InteropAudit Object.
	 * This method handle the exception.
	 * 
	 * @param serviceUrl
	 * @return interOpAuditList : List of InteropAudit
	 */
	def fetchAuditMessagesBasedOnCriteria(String serviceUrl,InteropAuditSearch auditSearch) {
		
		log.debug("AuditMessageService:fetchAuditMessagesBasedOnCriteria:start:fetching the audit messages")
		
		List<InteropAudit> interOpAuditList

		String auditMessage = null
								
		// fetch the all audit log message from service		
		def validateSAMLTokenurl = serviceUrl		
				
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			def auditSearchRequestBody = mapper.writeValueAsString(auditSearch)
			
			def http = new HTTPBuilder(validateSAMLTokenurl)
			//http.parser.'text/json' = http.parser.'application/json'
			log.debug("AuditMessageService:fetchAuditMessagesBasedOnCriteria:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.POST,ContentType.TEXT) {req ->
				
				body = auditSearchRequestBody
				//requestContentType : JSON
				requestContentType = ContentType.JSON
				headers.Accept = 'application/json'
				// response handler for a success response code
				response.success = { resp, reader ->
					
					log.debug("AuditMessageService:fetchAuditMessagesBasedOnCriteria:fetching the response")
					auditMessage= reader.text
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("AuditMessageService:fetchAuditMessagesBasedOnCriteria:Exception occured. Exception is : "+exception.getMessage())
			return interOpAuditList
		}
		interOpAuditList = new ArrayList<InteropAudit>()
		
		try{
			
			log.debug("AuditMessageService:fetchAuditMessagesBasedOnCriteria:Converting the String Json to InteropAudit Object List")
			
			if(StringUtils.isNotBlank(auditMessage)) {
				
				interOpAuditList = InteropAuditUtils.auditListFromJson(auditMessage)
			}
			
		}catch(Exception exception) {
		
			log.error("AuditMessageService:fetchAuditMessagesBasedOnCriteria:Exception occured. Exception is : "+exception.getMessage())
			return interOpAuditList
		}		
		
		log.debug("AuditMessageService:fetchAuditMessagesBasedOnCriteria:End:Converted the String Json to InteropAudit Object List and returning the result")
				
		return interOpAuditList
	}
	
	/**
	 * This method fetch the List of audit message for received exchange id.
	 * The method call service URL and expect the response in string JSON format.
	 * Method convert the received string JSON into  List of InteropAudit Object.
	 * This method handle the exception.
	 * 
	 * @param serviceUrl
	 * @return interOpAuditList : List of InteropAudit
	 */
	def fetchAuditMessageDetailBasedOnExchangeID(String serviceUrl) {
		
		log.debug("AuditMessageService:fetchAuditMessageDetailBasedOnExchangeID:start:fetching the audit messages")
				
		List<InteropAudit> interOpAuditList
		
		String auditMessage = null
		
		// fetch the all audit log message from service		
		def getExchangeAuditUrl = serviceUrl
		
		try {			
			
			def http = new HTTPBuilder(getExchangeAuditUrl)
			log.debug("AuditMessageService:fetchAuditMessageDetailBasedOnExchangeID:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->			
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					auditMessage = reader.text
					log.debug("AuditMessageService:fetchAuditMessageDetailBasedOnExchangeID:fetching the response")
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("AuditMessageService:fetchAuditMessageDetailBasedOnExchangeID:Exception occured. Exception is : "+exception.getMessage())
			
			return interOpAuditList
		}
		
		interOpAuditList = new ArrayList<InteropAudit>()
		
		log.debug("AuditMessageService:fetchAuditMessageDetailBasedOnExchangeID:Converting the String Json to InteropAudit Object List")
		
		if(StringUtils.isNotBlank(auditMessage)) {
			
			interOpAuditList = InteropAuditUtils.auditListFromJson(auditMessage)
		}
				
		log.debug("AuditMessageService:fetchAuditMessageDetailBasedOnExchangeID:End:Converted the String Json to InteropAudit Object List and returning the result")
						
		return interOpAuditList
	}
	
	/**
	 * This method fetch the audit message for received message id.
	 * The method call service URL and expect the response in string JSON format.
	 * Method convert the received string JSON into InteropAuditMessage Object.
	 * This method handle the exception.
	 * 
	 * @param serviceUrl
	 * @return interOpMessage: Object of InteropAuditMessage
	 */
	def fetchAuditTransactionDetails(String serviceUrl) {		
		
		log.debug("AuditMessageService:fetchAuditTransactionDetails:start:fetching the audit messages")
				
		String auditMessage = null
		
		def getInterOpAuditServiceUrl = serviceUrl
		
		try {
			
			def http = new HTTPBuilder(getInterOpAuditServiceUrl)
			
			log.debug("AuditMessageService:fetchAuditTransactionDetails:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->			
				
				headers.Accept = 'application/json';
				
				// response handler for a success response code
				response.success = { resp, reader ->
					
					auditMessage = reader.text
					log.debug("AuditMessageService:fetchAuditTransactionDetails:fetching the reponse from service")
					
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("AuditMessageService:fetchAuditTransactionDetails:Exception occured. Exception is : "+exception.getMessage())
						
			return null
		}
		
		def interopAudit = new InteropAudit()	
		
		log.debug("AuditMessageService:fetchAuditTransactionDetails:Coverting the String Json into InteropAuditMessage object")
		
		if(StringUtils.isNotBlank(auditMessage)) {
			
			interopAudit = InteropAuditUtils.auditFromJson(auditMessage)			
		}
		
		log.debug("AuditMessageService:fetchAuditTransactionDetails:Coverted the String Json into InteropAuditMessage object and returning the response")
				
		return interopAudit
	}
	
	
	/**
	 * This method fetch the List of InteropAuditMessage for received id.
	 * The method call service URL and expect the response in string JSON format.
	 * Method convert the received string JSON into  List of InteropAuditMessage Object.
	 * This method handle the exception.
	 * 
	 * @param serviceUrl
	 * @return interOpAuditMessageList: List of InteropAuditMessage
	 */
	def fetchAuditMessageDetailsBasedOnMessageID(String serviceUrl) {
		
		log.debug("AuditMessageService:fetchAuditMessageDetailsBasedOnMessageID:start:fetching the audit messages")
		
		List<InteropAuditMessage> interOpAuditMessageList
		
		String auditMessage = null
		
		// fetch the all audit log message from service
		def getAuditMessageOnIDUrl = serviceUrl
		
		try {
			
			def http = new HTTPBuilder(getAuditMessageOnIDUrl)
			log.debug("AuditMessageService:fetchAuditMessageDetailsBasedOnMessageID:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					auditMessage = reader.text
					log.debug("AuditMessageService:fetchAuditMessageDetailsBasedOnMessageID:fetching the response")
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("AuditMessageService:fetchAuditMessageDetailsBasedOnMessageID:Exception occured. Exception is : "+exception.getMessage())
			
			return interOpAuditMessageList
		}
		
		interOpAuditMessageList = new ArrayList<InteropAuditMessage>()
		
		log.debug("AuditMessageService:fetchAuditMessageDetailsBasedOnMessageID:Converting the String Json to InteropAuditMessage Object List")
		
		if(StringUtils.isNotBlank(auditMessage)) {
			
			interOpAuditMessageList = InteropAuditUtils.auditMessageListFromJson(auditMessage)
		}
				
		log.debug("AuditMessageService:fetchAuditMessageDetailsBasedOnMessageID:End:Converted the String Json to InteropAuditMessage Object List and returning the result")
						
		return interOpAuditMessageList
		
	}	
	
	
	def fetchAuditMessageDetails(String serviceUrl) {
		
		log.debug("AuditMessageService:fetchAuditMessageDetails:start:fetching the audit messages")
		
		String auditMessage = null
		
		def getInterOpAuditServiceUrl = serviceUrl
		
		try {
			
			def http = new HTTPBuilder(getInterOpAuditServiceUrl)
			
			log.debug("AuditMessageService:fetchAuditMessageDetails:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				headers.Accept = 'application/json';
				
				// response handler for a success response code
				response.success = { resp, reader ->
					
					auditMessage = reader.text
					log.debug("AuditMessageService:fetchAuditMessageDetails:fetching the reponse from service")
					
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("AuditMessageService:fetchAuditMessageDetails:Exception occured. Exception is : "+exception.getMessage())
						
			return null
		}
		
		def interopAuditMessage = new InteropAuditMessage()
		
		log.debug("AuditMessageService:fetchAuditMessageDetails:Coverting the String Json into InteropAuditMessage object")
		
		if(StringUtils.isNotBlank(auditMessage)) {
			
			interopAuditMessage = InteropAuditUtils.auditMessageFromJson(auditMessage)
		}
		
		log.debug("AuditMessageService:fetchAuditMessageDetails:Coverted the String Json into InteropAuditMessage object and returning the response")
				
		return interopAuditMessage
	}
}
