/**
 *======================================================================================
 * ADListController.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Active directory list controller
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Render ADList Page.
 *  				Controller to get, set, delete, test selected active directory instance
 *  				Controller to test list of active directories
 *  				
 *
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.config

// Project Imports
import net.sandlotnow.config.SessionConstants

/**
 * Controller class to handle the Active Directory details list
 * @author praveen13126
 *
 */
class ADListController {
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	def ADListService
	
	/**
	 * Action invoked as default controller action.
	 * Action will render active directory list in current controller index page.
	 * 
	 * @render Current controller index page
	 */
	def index() {
		
		log.info("ClientInfoController:index:-		Rendering active directory list")
		session.setAttribute(SessionConstants.TESTED_ADLIST, null)
		respond ADList.list(), model:[ADListInstanceCount: ADList.count()]
	}
	
	/**
	 * Action to get selected active directory instance
	 * Action will get active directory and render it in form page.
	 *
	 * @render Form page of adlist to show selected AD instance
	 */
	def beforeEdit() {
		
		log.info("ClientInfoController:beforeEdit:-		Rendering active directory instance")
		ADList ADListInstance = ADList.get(params.id)
		render (template:"form",model:[ADListInstance:ADListInstance])
	}
	
	/**
	 * Action to save entered active directory instance data into database
	 * Action will invoke service method to save/update active directory to database
	 * Action will invoke service method to test, save/update active directory to session
	 *
	 * @param ADListInstance		Selected instance of active directory
	 * 
	 * @render Table page of adlist to show updated list of active directory
	 */
	def save(ADList ADListInstance) {
		
		log.info("ClientInfoController:save:-		Updating active directory instance")
		ADListService.saveADList(ADListInstance)
		List adListClone = ADList.list();
		
		//check whether instance is in session ad list
		if(session.getAttribute(SessionConstants.TESTED_ADLIST)!=null) {
			
			adListClone = session.getAttribute(SessionConstants.TESTED_ADLIST)
			adListClone = ADListService.saveADListInSession(adListClone,ADListInstance)
			session.setAttribute(SessionConstants.TESTED_ADLIST, adListClone)
		}
		
		render (template:"table",model:[ADListInstanceList:adListClone])
	}
	
	/**
	 * Action to empty the saved active directory instance from modal
	 * 
	 * @render Form page of adlist to show empty fields
	 */
	def emptyForm() {
		
		log.info("ClientInfoController:emptyForm:-		New active directory instance")
		def ADListInstance = new ADList();
		render (template:"form",model:[ADListInstance:ADListInstance])
	}
	
	/**
	 * Action to delete selected active directory instance
	 * Action will invoke service method to delete selected active directory from database
	 * Action will invoke service method to delete selected active directory from session
	 * 
	 * @render Table page of adlist to show updated list of active directory
	 */
	def delete() {
		
		log.info("ClientInfoController:delete:-		Deleting active directory instance")
		ADList ADListInstance = ADList.get(params.id)
		ADListService.deleteADList(ADListInstance)
		List adListClone = ADList.list();
		
		//check whether instance is in session ad list
		if(session.getAttribute(SessionConstants.TESTED_ADLIST)!=null) {
			
			adListClone = session.getAttribute(SessionConstants.TESTED_ADLIST)
			ADListService.deleteADListInSession(adListClone,ADListInstance)
			session.setAttribute(SessionConstants.TESTED_ADLIST, adListClone)
		}
		
		render (template:"table",model:[ADListInstanceList:adListClone])
	}
	
	/**
	 * Action to test connection of current active directory instance
	 * Action invoke service method to test connection
	 * 
	 * @param ADListInstance		Selected instance of active directory
	 * 
	 * @render Ajax json response to show connection message.
	 */
	def test(ADList ADListInstance) {
		
		log.info("ClientInfoController:test:-		Testing active directory instance connection")
		boolean result = false
		String data;
		result = ADListService.checkConnection(ADListInstance)
		
		// check the connection is build up
		if(result) {
			
			data = '{"success":"'+message(code: 'custom.adlist.conn.success')+'"}'
		} 
		else {
			
			data = '{"error":"'+message(code: 'custom.adlist.conn.unsuccess')+'"}'
		}
		
		render(data)
	}
	
	/**
	 * Action to test connection of all active directories
	 * Action will test connection and store tested list in session
	 * 
	 * @response Table page of adlist to show updated list of active directory
	 */
	def testAll() {
		
		log.info("ClientInfoController:testAll:-		Testing all active directory list connection")
		List adList = ADListService.checkAllConnection()
		session.setAttribute(SessionConstants.TESTED_ADLIST, adList)
		render (template:"table",model:[ADListInstanceList:adList])
	}
	
	
}
