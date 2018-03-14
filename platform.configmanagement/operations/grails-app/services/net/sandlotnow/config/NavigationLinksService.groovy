/**
 *======================================================================================
 * NavigationLinksService.java
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
 *  Description:  	Singleton service class for NavigationLinks list controller
 *  				Service will save, update, delete, navigtionLinks instance
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
 * Service class to handle functionality to navigationLinks controller
 * @author Praveen13126
 *
 */
@Transactional
class NavigationLinksService {

	/**
	 * Method will save navigationLinks instance
	 *
	 * @param navigationLinksInstance		Selected instance of naviagationsLinks
	 */
	def saveNavigationLinks(NavigationLinks navigationLinks) {
		
		log.info("NavigationLinksService:saveNavigationLinks:-		Start saving navigationLinks instance")
		navigationLinks.save flush:true
		log.info("NavigationLinksService:saveNavigationLinks:-		Start saving navigationLinks instance")
	}
	
	/**
	 * Method will delete navigationLinks instance
	 *
	 * @param NavigationLinksInstance		Selected instance of navigationLinks
	 */
	def deleteNavigationLinks(NavigationLinks navigationLinks) {
		
		log.info("NavigationLinksService:deleteNavigationLinks:-		Start deleting navigationLinks instance")
		navigationLinks.delete flush:true
		log.info("NavigationLinksService:deleteNavigationLinks:-		Start deleting navigationLinks instance")
	}
}
