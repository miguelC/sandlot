/**
 *======================================================================================
 * ClientInfoService.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Client Information Service
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Singleton service class for Client Info Controller
 *  				Service class to handle functionality to Client info
 *  				Service will save entry into individual field  name, acronym, logo, authentication type, redirect url of client
 *
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

 // Package
package net.sandlotnow.config

// System Imports
import grails.transaction.Transactional

/**
 * Service class to handle functionality to Client Info controller
 * @author Praveen13126
 *
 */
@Transactional
class ClientInfoService {

    /**
     * Method will check first entry of client info
     * If there is no entry it will put entry with blank data.
     * 
     * @return client
     */
	def getClientInfo() {
		
		log.info("ClientInfoService:getClientInfo:-		Start fetching client info")
		def clientInfo = ClientInfo.first()
		
		//check if clientInfo instance is found then save it
		if(clientInfo==null) {
			
			clientInfo = new ClientInfo().save(flush:true)
		}
		log.info("ClientInfoService:getClientInfo:-		Ends fetching client info")
		return clientInfo
	}
	
	/**
	 * Method will update name in first entry of client info
	 * 
	 * @param name		Name of client
	 */
	def saveClientName(String name) {
		
		log.info("ClientInfoService:saveClientName:-		Start saving client info name")
		ClientInfo clientInfo = ClientInfo.first()
		clientInfo.setName(name)
		clientInfo.save(flush: true)
		log.info("ClientInfoService:saveClientName:-		Ends saving client info name")
	}
	
	/**
	 * Method will update acronym in first entry of client info
	 *
	 * @param acronym		Acronym of client
	 */
	def saveClientAcronym(String acronym) {
		
		log.info("ClientInfoService:saveClientAcronym:-		Start saving client info acronym")
		ClientInfo clientInfo = ClientInfo.first()
		clientInfo.setAcronym(acronym)
		clientInfo.save(flush: true)
		log.info("ClientInfoService:saveClientAcronym:-		Start saving client info acronym")
	}
	
	/**
	 * Method will update logo in first entry of client info
	 *
	 * @param logo		Logo of client to be shown
	 */
	def saveClientLogo(String logo) {
		
		log.info("ClientInfoService:saveClientLogo:-		Start saving client info logo")
		ClientInfo clientInfo = ClientInfo.first()
		clientInfo.setLogo(logo)
		clientInfo.save(flush: true)
		log.info("ClientInfoService:saveClientLogo:-		Start saving client info logo")
	}
	
	/**
	 * Method will update authType in first entry of client info
	 *
	 * @param authType		Authentication type of client
	 */
	def saveClientAuthType(String authType) {
		
		log.info("ClientInfoService:saveClientAuthType:-		Start saving client info authentication type")
		ClientInfo clientInfo = ClientInfo.first()
		clientInfo.setAuthType(authType)
		clientInfo.save(flush: true)
		log.info("ClientInfoService:saveClientAuthType:-		Start saving client info authentication type")
	}
	
	/**
	 * Method will update redirectURL in first entry of client info
	 *
	 * @param redirectURL		Redirect url of client
	 */
	def saveClientRedirectURL(String redirectURL) {
		
		log.info("ClientInfoService:saveClientRedirectURL:-		Start saving client info redirect url")
		ClientInfo clientInfo = ClientInfo.first()
		clientInfo.setRedirectURL(redirectURL)
		clientInfo.save(flush: true)
		log.info("ClientInfoService:saveClientRedirectURL:-		Start saving client info redirect url")
	}
}
