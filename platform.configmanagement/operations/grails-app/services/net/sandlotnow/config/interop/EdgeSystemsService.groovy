package net.sandlotnow.config.interop

import com.fasterxml.jackson.databind.ObjectMapper

import grails.transaction.Transactional
import groovy.json.JsonSlurper
import static groovyx.net.http.ContentType.*

import java.text.BreakDictionary;
import java.text.SimpleDateFormat
import java.util.Date;

import javassist.bytecode.stackmap.BasicBlock.Catch;
import groovyx.net.http.*
import net.sandlotnow.interop.ihe.model.EdgeDevice;
import net.sandlotnow.interop.ihe.model.EdgeDeviceType
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeEndpointActor;
import net.sandlotnow.interop.ihe.model.EdgeOrganization
import net.sandlotnow.interop.ihe.model.EdgeSystem
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.config.SessionConstants
import net.sandlotnow.config.interop.InteropServerConfig
import net.sandlotnow.config.interop.InteropServer

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.util.WebUtils

@Transactional
class EdgeSystemsService {

	def grailsApplication
	
	private InteropServer getInteropServer(){
		
		def httpSession = WebUtils.retrieveGrailsWebRequest().currentRequest.session
		
		def sessionServerName = httpSession.getAttribute(SessionConstants.INTEROP_SERVER_NAME)

		InteropServerConfig interopServerConfig = grailsApplication.mainContext.getBean('serverConfigMap', InteropServerConfig)
		
		return interopServerConfig.getServer(sessionServerName)
	}
	
    def fetchEdgeSystems() {
		
		log.debug("EdgeSystemsService:fetchEdgeSystems:start:fetching the edge systems")
		
		List<EdgeSystem> edgeSystemsList
		
		def edgeSystemsMessage
		
		def serviceUrl = interopServer.getServiceUrlEdgeSystemList()
				
		try {
			
			def http = new HTTPBuilder(serviceUrl)
		
			log.debug("EdgeSystemsService:fetchEdgeSystems:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					edgeSystemsMessage= reader.text
					log.debug("EdgeSystemsService:fetchEdgeSystems:fetching the response")
					
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("EdgeSystemsService:fetchEdgeSystems:Exception occured. Exception is : "+exception.getMessage())
						
			return edgeSystemsList
		}
		
		edgeSystemsList = new ArrayList<EdgeSystem>()
		
		log.debug("EdgeSystemsService:fetchEdgeSystems:Converting the String Json to EdgeSystem Object List")
		
		if(StringUtils.isNotBlank(edgeSystemsMessage)) {
			def jsonSlurper = new JsonSlurper()
			edgeSystemsList = jsonSlurper.parseText(edgeSystemsMessage)
		}	
		
		log.debug("EdgeSystemsService:fetchEdgeSystems:End:Converted the String Json to EdgeSystem Object List and returning the result")
				
		return edgeSystemsList
	}
	
	def fetchEdgeSystemById(def edgeId) {
		
		log.debug("EdgeSystemsService:fetchEdgeSystemById:start:fetching the edge systems")
		
		EdgeSystem edgeSystem
		
		def edgeSystemMessage
		
		def serviceUrl = interopServer.getServiceUrlEdgeSystemById(edgeId)
				
		try {
			
			def http = new HTTPBuilder(serviceUrl)
		
			log.debug("EdgeSystemsService:fetchEdgeSystemById:sending request to service ${serviceUrl}")
			
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					edgeSystemMessage = reader.text
					log.debug("EdgeSystemsService:fetchEdgeSystemById:fetching the response")
					
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("EdgeSystemsService:fetchEdgeSystemById:Exception occured. Exception is : "+exception.getMessage())
						
			return edgeSystem
		}
		
		edgeSystem = new EdgeSystem()
		
		log.debug("EdgeSystemsService:fetchEdgeSystemById:Converting the String Json to EdgeSystem ")
		
		if(StringUtils.isNotBlank(edgeSystemMessage)) {
			
			ObjectMapper mapper = new ObjectMapper();        
            edgeSystem = mapper.readValue(edgeSystemMessage, EdgeSystem.class)
		}
		
		log.debug("EdgeSystemsService:fetchEdgeSystemById:End:Converted the String Json to EdgeSystem and returning the result")
				
		return edgeSystem
	}
	
	def fetchEdgeOrganizations() {
		
		log.debug("EdgeSystemsService:fetchEdgeOrganizations:start:fetching the edge organizations")
		
		List<EdgeOrganization> edgeOrgsList
		
		def edgeOrgsMessage
		
		def serviceUrl = interopServer.getServiceUrlEdgeOrganizationList()
				
		try {
			
			def http = new HTTPBuilder(serviceUrl)
		
			log.debug("EdgeSystemsService:fetchEdgeOrganizations:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					edgeOrgsMessage= reader.text
					
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("EdgeSystemsService:fetchEdgeOrganizations:Exception occured. Exception is : "+exception.getMessage())
						
			return edgeOrgsList
		}
		
		edgeOrgsList = new ArrayList<EdgeOrganization>()
		
		if(StringUtils.isNotBlank(edgeOrgsMessage)) {
			def jsonSlurper = new JsonSlurper()
			edgeOrgsList = jsonSlurper.parseText(edgeOrgsMessage)
		}
		
		log.debug("EdgeSystemsService:fetchEdgeOrganizations:End:Converted the String Json to EdgeOrganization Object List and returning the result")
				
		return edgeOrgsList
	}
	
	def fetchEdgeOrganizationById(def edgeOrgId) {
		
		log.info("EdgeSystemsService:fetchEdgeOrganizationById:start:fetching the edge organization ${edgeOrgId}")
		
		EdgeOrganization edgeOrg
		
		def edgeOrgMessage
		
		def serviceUrl = interopServer.getServiceUrlEdgeOrganizationById(edgeOrgId)
				
		try {
			
			def http = new HTTPBuilder(serviceUrl)
		
			log.debug("EdgeSystemsService:fetchEdgeOrganizationById:sending request to service ${serviceUrl}")
			
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					edgeOrgMessage = reader.text
					log.debug("EdgeSystemsService:fetchEdgeOrganizationById:fetching the response")
					
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("EdgeSystemsService:fetchEdgeSystemById:Exception occured. Exception is : "+exception.getMessage())
						
			return edgeOrg
		}
		
		edgeOrg = new EdgeOrganization()
		
		log.debug("EdgeSystemsService:fetchEdgeSystemById:Converting the String Json to EdgeSystem ")
		
		if(StringUtils.isNotBlank(edgeOrgMessage)) {
			
			ObjectMapper mapper = new ObjectMapper();
			edgeOrg = mapper.readValue(edgeOrgMessage, EdgeOrganization.class)
		}
		
		log.debug("EdgeSystemsService:fetchEdgeOrganizationById:End:Converted the String Json to EdgeOrganization and returning the result")
				
		return edgeOrg
	}
	
	def saveEdgeOrganization(def edgeOrg) {
		
		if(edgeOrg == null){
			return null
		}
		
		log.info("EdgeSystemsService:saveEdgeOrganization:start:saing the edge organization ${edgeOrg.id}")
		
		def edgeOrgMessage
		
		def serviceUrl = interopServer.getServiceUrlEdgeOrganizationSave()
				
		try {
			ObjectMapper mapper = new ObjectMapper();
			def edgeOrgRequestBody = mapper.writeValueAsString(edgeOrg)
			
			def http = new HTTPBuilder(serviceUrl)
			log.info("EdgeSystemsService:saveEdgeOrganization:sending request to service ${serviceUrl}")
			
			log.info("organization: ${edgeOrgRequestBody}")
			
			http.request(Method.POST) {req ->
				body = edgeOrgRequestBody
				requestContentType = ContentType.JSON
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					edgeOrgMessage = reader.text
				}
				response.failure = { resp ->
					log.error("Request failed with status ${resp.status}")
					throw new InteropServiceException("Request failed with status ${resp.status}")
				}
			}
		}
		catch(Exception exception) {
			log.error("EdgeSystemsService:saveEdgeOrganization:Exception occured. Exception is : "+exception.getMessage())
			throw exception
		}
		
		if(StringUtils.isNotBlank(edgeOrgMessage)) {
			ObjectMapper mapper = new ObjectMapper();
			edgeOrg = mapper.readValue(edgeOrgMessage, EdgeOrganization.class)
		}
		log.debug("EdgeSystemsService:saveEdgeOrganization:End:Converted the String Json to EdgeOrganization and returning the result")
				
		return edgeOrg
	}
	
	/**
	 * This method return the Edge End point details.
	 * 
	 * @param id --> selected edge end point record id
	 * @param edgeSystem --> EdgeSystem instance
	 * @return edgeEndPointDetails --> EdgeEndPoint instance
	 */
	def getEdgeEndPoint(def id, def edgeSystem) {
		
		log.debug("EdgeSystemsService:getEdgeSystem:Start fetching the edge system")		
		
		def session = getSession()
		
		EdgeEndpoint edgeEndPointDetails
		
		EdgeSystem edgeSystems = edgeSystem
		
		for (EdgeEndpoint edgeEndPoint in edgeSystems.getEndpoints()) {
			
			try {
				
				if(edgeEndPoint.id == id) {
					
					edgeEndPointDetails = new EdgeEndpoint()
					edgeEndPointDetails = edgeEndPoint
					session.setAttribute(SessionConstants.EDGE_END_POINT,edgeEndPointDetails)
					break
				}						
			} catch (Exception exception) {
			
				log.error("EdgeSystemsService:getEdgeSystem:Exception occured. Exception is : "+exception.getMessage())				
				return edgeEndPointDetails
			}			
		}
		
		log.debug("EdgeSystemsService:getEdgeSystem:returning the edge system instance")
				
		return edgeEndPointDetails
	}
	
	/**
	 * 
	 * Get the Edge System Device based on received Edge System device ID.
	 * 
	 * @param id
	 * @param edgeSystem
	 * @return edgeDeviceObj --> Edge System Instance
	 */
	def getEdgeSystemDeviceDetail(def id, def edgeSystem) {
		
		log.debug("EdgeSystemsService:getEdgeSystemDeviceDetail:Start fetching the edge system device")
		
		def session = getSession()
		
		EdgeDevice edgeDeviceObj
		
		EdgeSystem edgeSystems = edgeSystem
		
		for (EdgeDevice edgeDevice in edgeSystems.getDevices()) {
			
			try {
				
				if(edgeDevice.id == id) {
					
					edgeDeviceObj = new EdgeDevice()
					edgeDeviceObj = edgeDevice
					break
				}
			} catch (Exception exception) {
			
				log.error("EdgeSystemsService:getEdgeSystemDeviceDetail:Exception occured. Exception is : "+exception.getMessage())
				return edgeDeviceObj
			}
		}
		
		log.debug("EdgeSystemsService:getEdgeSystemDeviceDetail:returning the edge system device instance")
				
		return edgeDeviceObj
	}
	
	/**
	 * Get the List of Edge Device Type
	 * 
	 * @return edgeDeviceTypes --> List of Edge Device Type
	 */
	def getEdgeDeviceType() {
	    
		List<String> edgeDeviceTypes = Arrays.asList(EdgeDeviceType.values())
		
		return edgeDeviceTypes
	}
	
	/**
	 * This method perform check Edge Device Type is already exits or not.
	 * If exits it will return error.
	 * 
	 * @param deviceType
	 * @param edgeSystem
	 * @param edgeDevice
	 * @return true/false
	 */
	def checkEdgeDeviceCombination(def deviceType, EdgeSystem edgeSystem , def edgeDevice) {
		
		log.debug("EdgeSystemsService:checkEdgeEndPointCombination:Start:start evaluating combination.")
		
		for (EdgeDevice edgeDeviceObj in edgeSystem.getDevices()) {			
			
			if(edgeDeviceObj.deviceType == EdgeDeviceType.valueOf(deviceType)) {
				
				log.debug("EdgeSystemsService:checkEdgeEndPointCombination:combination exits.")
				
				return true
			}
		}
		
		log.debug("EdgeSystemsService:checkEdgeEndPointCombination:END:combination doesn't exits.")
		
		return false
	}
	
	/**
	 * This method return Edge Device List.
	 * 
	 * @return edgeDeviceList --> Return the Edge Device List
	 */
	def getEdgeDeviceList() {
		
		log.debug("EdgeSystemsService:getEdgeTransactions:Start fetching the edge transactions")
		
		def serviceUrl =  interopServer.getSericeUrlEdgeSystemDevice()
		
		def edgeDeviceText
		
		List<EdgeDevice> edgeDeviceList  = new ArrayList<EdgeDevice>()
		
		try {
			
			def http = new HTTPBuilder(serviceUrl)
		
			log.debug("EdgeSystemsService:getEdgeTransactions:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					log.debug("EdgeSystemsService:getEdgeTransactions:fetching the response")
					edgeDeviceText= reader.text
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("EdgeSystemsService:getEdgeTransactions:Exception occured. Exception is : "+exception.getMessage())
			return edgeDeviceList
		}
		
		log.debug("EdgeSystemsService:getEdgeTransactions:Converting the String Json to EdgeTransaction Object List")
		
		if(StringUtils.isNotBlank(edgeDeviceText)) {
			def jsonSlurper = new JsonSlurper()
			edgeDeviceList = jsonSlurper.parseText(edgeDeviceText)
		}
		
		log.debug("EdgeSystemsService:getEdgeTransactions:End:Converted the String Json to EdgeTransaction Object List and returning the result")
				
		return edgeDeviceList
	}
	
	/**
	 *  
	 * This method return the list of transaction.
	 * 
	 * @return edgeTransactionList --> List of EdgeTransaction
	 */
	def getEdgeTransactions () {
		
		log.debug("EdgeSystemsService:getEdgeTransactions:Start fetching the edge transactions")
		
		def serviceUrl =  interopServer.getServiceUrlEdgeTransaction()
		
		def edgeTransactionText
		
		List<EdgeTransaction> edgeTransactionList  = new ArrayList<EdgeTransaction>()
		
		try {
			
			def http = new HTTPBuilder(serviceUrl)
		
			log.debug("EdgeSystemsService:getEdgeTransactions:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.GET,ContentType.TEXT) {req ->
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					log.debug("EdgeSystemsService:getEdgeTransactions:fetching the response")
					edgeTransactionText= reader.text	
				}
			}
			
		}
		catch(Exception exception) {
			
			log.error("EdgeSystemsService:getEdgeTransactions:Exception occured. Exception is : "+exception.getMessage())		
			return edgeTransactionList
		}		
		
		log.debug("EdgeSystemsService:getEdgeTransactions:Converting the String Json to EdgeTransaction Object List")
		
		if(StringUtils.isNotBlank(edgeTransactionText)) {
			def jsonSlurper = new JsonSlurper()
			edgeTransactionList = jsonSlurper.parseText(edgeTransactionText)
		}
		
		session.setAttribute(SessionConstants.TRANSACTION_LIST,edgeTransactionList)
		
		log.debug("EdgeSystemsService:getEdgeTransactions:End:Converted the String Json to EdgeTransaction Object List and returning the result")
				
		return edgeTransactionList
	}
	
	/**
	 * This method return the list of Actor
	 * 
	 * @return actorList --> List of Actor(SENDER/RECEIVER)
	 */
	def getEdgeSystemActor() {
		
		log.debug("EdgeSystemsServicegetEdgeSystemActors:Start fetching the edge end system actor list")
		
		List<String> actorList = new ArrayList<String>()
		
		actorList.add(SessionConstants.SENDER);
		actorList.add(SessionConstants.RECEIVER);
		
		log.debug("EdgeSystemsServicegetEdgeSystemActors:return the edge end system actor list")
				
		return actorList
	}
	
	/**
	 * This method save/Update the EdgeEnd Point record.
	 * 
	 * @param edgeSystem --> EdgeSystem insatnce
	 * @return true/false
	 */
	def saveEdgeSystem(def edgeSystem) {		
		
		log.debug("EdgeSystemsService:saveEdgeEndPoint:Start saving the edgeSystem instance")
		
		def serviceUrl =  interopServer.getServiceUrlEdgeSystemSave()
		
		def edgeSystemResponseText
		
		EdgeSystem edgeSystemObj = new EdgeSystem()
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			def edgeSystemRequestBody = mapper.writeValueAsString(edgeSystem)
			
			def http = new HTTPBuilder(serviceUrl)
		
			log.debug("EdgeSystemsService:saveEdgeEndPoint:sending request to service ${serviceUrl}")
			
			// ContentType.TEXT :- Rather than ContentType.JSON we have used ContentType.TEXT. The reason is
			//                     ContentType.JSON Automatic response parsing & is parse it incorrectly.
			// perform a POST request, expecting JSON response
			http.request(Method.POST) {req ->
				body = edgeSystemRequestBody
				requestContentType = ContentType.JSON				
				
				headers.Accept = 'application/json';
				// response handler for a success response code
				response.success = { resp, reader ->
					
					log.debug("EdgeSystemsService:saveEdgeEndPoint:fetching the response")
					edgeSystemResponseText= reader.text
				}
			}
			
		}
		catch(Exception exception) {
			exception.printStackTrace()
			log.error("EdgeSystemsService:saveEdgeEndPoint:Exception occured. Exception is : "+exception.getMessage())
			return false
		}		
		
		session.setAttribute(SessionConstants.EDGE_SYSTEM,edgeSystem)		
		
		log.debug("EdgeSystemsService:saveEdgeEndPoint:End:returning the result")
				
		return true
		
	}	
	
	/**
	 * This method return the details of EdgeTransaction instance based on received transaction name.
	 * 
	 * @param name --> Transaction name
	 * @return edgeTransObj -->  EdgeTransaction instance
	 */
	def getTransactionDetail(def name) {
		
		log.debug("EdgeSystemsService:getTransactionDetail:Start saving the edgeEndPoint instance")
				
		List<EdgeTransaction> edgeTransactionList  = session.getAttribute(SessionConstants.TRANSACTION_LIST)
		
		EdgeTransaction edgeTransObj = new EdgeTransaction()
		
		for (EdgeTransaction edgeTransaction in edgeTransactionList) {
			
			if(edgeTransaction.name.equals(name)) {
				
				edgeTransObj = edgeTransaction
				log.debug("EdgeSystemsService:getTransactionDetail:object matched")
				break
			}
			
		}
		
		log.debug("EdgeSystemsService:getTransactionDetail:returning the edgeTransaction instance")
				
		return edgeTransObj
	}
	
	/**
	 * This Method return Edge Device Details.
	 * 
	 * @param deviceType
	 * @return edgeDeviceList --> EdgeDevice List
	 */
	def getEdgeSystemDeviceDetails(def deviceType) {
		
		List<EdgeDevice> edgeDeviceList = getEdgeDeviceList()
		
		EdgeDevice edgeDeviceObj
		
		for (EdgeDevice edgeDevice in edgeDeviceList) {
			
			if(edgeDevice.deviceType.equals(EdgeDeviceType.valueOf(deviceType))) {
				
				edgeDeviceObj = new EdgeDevice()
				edgeDeviceObj = edgeDevice
				log.debug("EdgeSystemsService:getTransactionDetail:object matched")
				break
			}
		}
		
		log.debug("EdgeSystemsService:getTransactionDetail:returning the edgeTransaction instance")
		
		return edgeDeviceObj		
	}
	
	/**
	 * Check the actor and transaction combination is already exits or not.
	 * 
	 * @param actorDesc --> selected actor
	 * @param transactionDesc --> selected transaction
	 * @param edgeSystem --> EdgeSytem instance
	 * @param edgeEndPointObj --> EdgeEndPoint instance
	 * @return true/false
	 */
    def checkEdgeEndPointCombination(String actorDesc, String transactionDesc , EdgeSystem edgeSystem , EdgeEndpoint edgeEndPointObj) {		

		log.debug("EdgeSystemsService:checkEdgeEndPointCombination:Start:start evaluating combination.")
		
		for (EdgeEndpoint edgeEndPoints in edgeSystem.getEndpoints()) {
			
			if(edgeEndPoints.actor.equals(EdgeEndpointActor.valueOf(actorDesc)) && edgeEndPoints.transaction.name.equals(transactionDesc)) {
				
				log.debug("EdgeSystemsService:checkEdgeEndPointCombination:combination exits.")
				
				return true
			}
		}
		
		log.debug("EdgeSystemsService:checkEdgeEndPointCombination:END:combination doesn't exits.")
		
		return false		
	} 
	
	
	def fetchEdgeOrgBasedOnName(def name) {
		
		List<EdgeOrganization> organisationList = fetchEdgeOrganizations()
		
		EdgeOrganization edgeOrgObj 
		
		for (EdgeOrganization edgeOrganization in organisationList) {
			
			if(edgeOrganization.name.equals(name)) {
				edgeOrgObj = new EdgeOrganization()
				edgeOrgObj = edgeOrganization
				break
			}
		}
		
		return edgeOrgObj
	}
	
	/**
	 * Method to check if the record with org name, short name and OID is already present or not.
	 * 
	 * 
	 * @param edgeOrgName --> Organization Name
	 * @param edgeOrgShortName --> Organization Short Name
	 * @param edgeOrgOid --> Organization OID
	 * @return true/false
	 */
	def checkOrganizationExits(def edgeOrgName , def edgeOrgShortName , def edgeOrgOid) {
		
		def organizationList = fetchEdgeOrganizations()
		
		for (EdgeOrganization edgeOrg in organizationList) {
			
		   if(edgeOrg.name.equals(edgeOrgName) && edgeOrg.shortName.equals(edgeOrgShortName) && edgeOrg.organizationOID.equals(edgeOrgOid)) {
			   
			   return true
		   }	
		}
		
		return false		
	}
	
	def checkEdgeSystemDuplicateEntry(def edgeSystemName , def assiginingAuth , def documentSource) {
		
		def session = getSession()
		
		def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		
		def edgeSystems = fetchEdgeSystems()
		
		for (EdgeSystem edgeSystem in edgeSystems) {			
			
		   if(Integer.valueOf(edgeSystemID) != edgeSystem.id && edgeSystem.name.equals(edgeSystemName) && edgeSystem.assigningAuthorityOID.equals(assiginingAuth) && edgeSystem.documentSourceOID.equals(documentSource)) {
			   
			   return true
		   }
		}
		
		return false
	}
	
	
	/**
	 * Method to get Current Session Object
	 * @return session Object
	 */
	def getSession() {
		
		return WebUtils.retrieveGrailsWebRequest().session
	}
}
