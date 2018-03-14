/**
 *======================================================================================
 * ApplicationsService.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Active directory list service
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Singleton service class for Applications list controller
 *  				Service will save, update, delete, application instance
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
 * Service class to handle functionality to applications controller
 * @author Praveen13126
 *
 */
@Transactional
class ApplicationsService {
	
	/**
	 * Method will save application instance
	 * 
	 * @param applicationsInstance		Selected instance of applications
	 */
    def saveApplications(Applications applicationsInstance) {
		
		log.info("ApplicationService:saveApplications:-		Start saving application instance")
		applicationsInstance.save flush:true
		log.info("ApplicationService:saveApplications:-		Ends saving application instance")
	}
	
	/**
	 * Method will delete application instance
	 *
	 * @param applicationsInstance		Selected instance of applications
	 */
	def deleteApplications(Applications applicationsInstance) {
		
		log.info("ApplicationService:deleteApplications:-		Start deleting application instance")
		applicationsInstance.delete flush:true
		log.info("ApplicationService:deleteApplications:-		Ends deleting application instance")
	}
	
	
}
