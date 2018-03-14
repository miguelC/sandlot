/**
 *======================================================================================
 * LicenseService.java
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
 *  Description:  	Singleton service class for license list controller
 *  				Service will save, update, delete, license instance
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
 * Service class to handle functionality to license controller
 * @author Praveen13126
 *
 */
@Transactional
class LicenseService {
	
	/**
	 * Method will save license instance
	 *
	 * @param licenseInstance		Selected instance of license
	 */
	def saveLicense(License licenseInstance) {
		
		log.info("LicenseService:saveLicense:-		Start saving license instance")
		licenseInstance.save flush:true
		log.info("LicenseService:saveLicense:-		Start saving license instance")
	}
	
	
}
