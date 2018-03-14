/**
 *======================================================================================
 * BlacklistService.gr0ovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- BlacklistService
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			09/24/2015
 *  				Original development
 *  @author 		Devendra Patil
 *  @version 		1.0
 *  Description:  	Singleton service class for Blacklist controller
 *  				Service will save, update, delete, Blacklist instance
 *
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

 // Package
package net.sandlotnow.config

//system imports
import grails.transaction.Transactional

@Transactional
class BlacklistService {

    /**
	 * Method will save Blacklist instance
	 *
	 * @param navigationLinksInstance		Selected instance of Blacklist
	 */
	def saveBlackList(Blacklist blackList) {
		
		log.info("BlacklistService:saveBlackList:-		Start saving Blacklist instance")
		blackList.save flush:true
		log.info("BlacklistService:saveBlackList:-		Start saving Blacklist instance")
	}
	
	/**
	 * Method will delete Blacklist instance
	 *
	 * @param Blacklist		Selected instance of Blacklist
	 */
	def deleteBlackListOrganization(Blacklist blackList) {
		
		log.info("BlacklistService:deleteBlackListOrganization:-		Start deleting Blacklist instance")
		blackList.delete flush:true
		log.info("BlacklistService:deleteBlackListOrganization:-		Start deleting Blacklist instance")
	}
}
