/**
 *======================================================================================
 * ClientInfoController.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Client information controller
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Render Client Info Page.
 *  				Use to put individual field entry in name, acronym, logo, authentication type, redirect url of client
 *
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.config

/**
 *  Controller class to handle the Client Info Details
 * @author praveen13126
 *
 */
class ClientInfoController {
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	def clientInfoService
    
	/**
	 * Action invoked as default controller action.
	 * Action will render client in current controller index page.
	 * 
	 * @render Current controller index page
	 */
	def index() { 
		
		log.info("ClientInfoController:index:-		Rendering client info instance")
		def clientInfo = clientInfoService.getClientInfo();
		render(view:'index',model:['clientInfo':clientInfo])
	}
		
	/**
	 * Action to update only name of client.
	 * Action will invoke service method
	 * 
	 * @render Ajax success response to index page
	 * 		  
	 */
	def updateName() {
		
		log.info("ClientInfoController:updateName:-		Updating client name")
		clientInfoService.saveClientName(params.value)
		render('success')
	}
	
	/**
	 * Action to update only acronym of client
	 * Action will invoke service method
	 * 
	 * @render Ajax success response to index page
	 */
	def updateAcronym() {
		
		log.info("ClientInfoController:updateAcronym:-		Updating client acronym")
		clientInfoService.saveClientAcronym(params.value)
		render('success')
	}
	
	/**
	 * Action to update only logo of client
	 * Action will invoke service method
	 * 
	 * @render Ajax success response to index page
	 */
	def updateLogo() {
		
		log.info("ClientInfoController:updateLogo:-		Updating client logo")
		clientInfoService.saveClientLogo(params.logo)
		render('success')
	}
	
	/**
	 * Action to update only authentication type of client
	 * Action will invoke service method
	 * 
	 * @render Ajax success resonse to index page
	 */
	def updateAuthType() {
		
		log.info("ClientInfoController:updateAuthType:-		Updating client authentication type")
		clientInfoService.saveClientAuthType(params.authType)
		render('success')
	}
	
	/**
	 * Action to update only redirect URL of client
	 * Action will invoke service method
	 * 
	 * @render Ajax success resonse to index page
	 */
	def updateRedirectURL() {
		
		log.info("ClientInfoController:updateRedirectURL:-		Updating client redirect URL")
		clientInfoService.saveClientRedirectURL(params.value)
		render('success')
	}
}
