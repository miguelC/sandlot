/**
 *======================================================================================
 * ADList.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Active directory list domain
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
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

/**
 * Domain class of active directory
 * @author praveen13126
 *
 */
class ADList {

	//PROPERTIES
	String name
	String technicalName
	String domain
	String connectionString
	String serviceAccount
	String sapassword
	Boolean connectionChecked
	
	//STATIC PROPERTIES
	static transients = ['connectionChecked']
	
	static constraints = {
		
		name blank:false,  matches: "[a-zA-Z0-9 ]+"
		technicalName blank:false,  matches: "[a-zA-Z0-9 ]+"
		domain blank:false,  matches: "[a-zA-Z0-9 ]+"
		connectionString blank:false,  matches: "[a-zA-Z0-9 ]+"
		serviceAccount blank:false,  matches: "[a-zA-Z0-9 ]+"
		sapassword blank:false
		connectionChecked  defaultValue: false
	}
	
	static mapping = {
		
        table 'adlist'
        version false
        name column: 'name'
        technicalName column: 'technical_name'
		domain column: 'domain'
		connectionString column: 'connection_string'
		serviceAccount column: 'service_account'
		sapassword column: 'sapassword'
	}
	
	/**
	 * Method fired immediately after an object is loaded from the db
	 * Method to trim the data.
	 * 
	 */
	def afterLoad() {
		
		name = name?.trim()
		technicalName = technicalName?.trim()
		domain = domain?.trim()
		connectionString = connectionString?.trim()
		serviceAccount = serviceAccount?.trim()
		sapassword = sapassword?.trim()
	}
}
