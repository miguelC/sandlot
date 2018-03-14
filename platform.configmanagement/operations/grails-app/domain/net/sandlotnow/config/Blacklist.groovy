

/**
 *======================================================================================
 * Blacklist.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- Class hold the information related to organization
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			09/22/2015
 *  				Original development
 *  @author 		Devendra Patil
 *  @version 		1.0
 *  Description:  	-
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.config

class Blacklist {
    
	//PROPERTIES
	String organizationId
	String organizationName
	
	//STATIC PROPERTIES
	static constraints = {
		
		organizationId blank:false, matches: "[a-zA-Z0-9 ]+"
		organizationName blank:false, matches: "[a-zA-Z0-9 ]+"	
	}
	
	//By default GORM is configured with optimistic locking enabled.
	//You can disable this by calling the version method with an argument of false
	static mapping = {
		
		version false
		organizationId column:'organization_id'
		organizationName column: 'organization_name'
	}
	
	/**
	 * The purpose of this method is to remove the
	 *  unwanted space from fetched database value.
	 *
	 */
	def afterLoad() {
		
		organizationId = organizationId?.trim()
		organizationName = organizationName?.trim()
	}
}

