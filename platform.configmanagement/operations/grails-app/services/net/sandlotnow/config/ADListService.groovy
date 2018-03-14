/**
 *======================================================================================
 * ADListService.java
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
 *  Description:  	Singleton service class for active directory list controller
 *  				Service class to handle functionality to active directory list
 *  				Service will save, update, delete, test active directory
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
 * Service class to handle functionality to ADList controller
 * @author Praveen13126
 *
 */
@Transactional
class ADListService {

	/**
	 * Method will save/update selected active directory instance
	 * 
	 * @param ADListInstance		Selected instance of active directory
	 * 
	 */
	def saveADList(ADList ADListInstance) {
		
		log.info("ADListService:saveADList:-		Start saving active directory instance")
		
		//check if instance connectionchecked property is null
		if(ADListInstance.getConnectionChecked()==null) {
			
			ADListInstance.setConnectionChecked(false)
		}
		ADListInstance.save(flush:true)
		log.info("ADListService:saveADList:-		Ends saving active directory instance")
	}
	
	/**
	 * Method will test connection of current active directory and update if already in session adlist else,
	 * Method will put new entry of active directory in session adlist.
	 * 
	 * @param adListClone		active directory instance to save
	 * @param ADListInstance	active directory session list
	 * 
	 * @return		Active Directory session list 
	 */
	def saveADListInSession(List adListClone,ADList ADListInstance) {
		
		log.info("ADListService:saveADListInSession:-		Start saving active directory instance in session")
		boolean result = checkConnection(ADListInstance)
		boolean updateAD = false
		int index = 0;
		ADListInstance.setConnectionChecked(result);
		for(ADList adlistIns : adListClone) {
			
			//check current instance with loop instance id
			if (adlistIns.id==ADListInstance.id) {
				
				updateAD = true;
				index = adListClone.indexOf(adlistIns)
			}
		}
		
		//check whether to update or add adlist instance in session
		if(!updateAD) {
			
			adListClone.add(ADListInstance)
		}
		else{
			
			adListClone.remove(index)
			adListClone.add(ADListInstance)
		}
		log.info("ADListService:saveADListInSession:-		Ends saving active directory instance in session")
		return adListClone
	}
	
	/**
	 * Method will delete selected active directory instance
	 *
	 * @param ADListInstance		Selected instance of active directory
	 *
	 */
	def deleteADList(ADList ADListInstance) {
		
		log.info("ADListService:deleteADList:-		Start deleting active directory instance")
		ADListInstance.delete(flush: true)
		log.info("ADListService:deleteADList:-		Ends deleting active directory instance")
	}
	
	/**
	 * Method will check current active directory in session adlist, then delete from the list.
	 * 
	 * @param adListClone		active directory instance to delete
	 * @param ADListInstance	active directory session list
	 * 
	 * @return		Active Directory session list
	 */
	def deleteADListInSession(List adListClone,ADList ADListInstance) {
		
		log.info("ADListService:deleteADListInSession:-		Start deleting active directory instance in session")
		int index = 0
		for(ADList adlistIns : adListClone) {
			
			//check current instance with loop instance id
			if (adlistIns.id==ADListInstance.id) {
				
				index = adListClone.indexOf(adlistIns)
			}
		}
		adListClone.remove(index)
		log.info("ADListService:deleteADListInSession:-		Ends deleting active directory instance in session")
		return adListClone
	}
	
	/**
	 * Method will test connection for selected active directory
	 * 
	 * @param ADListInstance		Selected instance of active directory
	 * 
	 * @return true			Connection is established
	 * 			false 		Connection is not established
	 */
	def checkConnection(ADList ADListInstance) {
		
		log.info("ADListService:checkConnection:-		Start testing connection of active directory instance")
		boolean result = false
		
		//Check the connectionString and write webservice for connection
		if(ADListInstance.getConnectionString()==null) {
			
			result = false
		}
		else{
			
			result = true
		}
		log.info("ADListService:checkConnection:-		Ends testing connection of active directory instance")
		return result
	}
	
	/**
	 * Method will test connection all active directories in database
	 * 
	 * @return adListClone		List of active directories tested
	 */
	def checkAllConnection() {
		
		log.info("ADListService:checkAllConnection:-		Start testing connection of all active directory list")
		List adList = ADList.list(flush:true)
		List<ADList> adListClone =  new ArrayList<Object>(adList);
		for(ADList adListIns: adListClone) {
			boolean chkConn = checkConnection(adListIns)
			adListIns.setConnectionChecked(chkConn)
		}
		log.info("ADListService:checkAllConnection:-		Ends testing connection of all active directory list")
		return adListClone
	}
}
