/**
 *======================================================================================
 * ClientInfo.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Client information domain
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	CLient information element class.
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
 * Domain class of client information
 * @author praveen13126
 *
 */
class ClientInfo {

	//PROPERTIES
	int id
	String name
	String authType
	String logo
	String redirectURL
	String acronym
	
	
	//STATIC PROPERTIES
	static constraints = {
		
		name blank:true, nullable: true, matches: "[a-zA-Z0-9 ]+"
		authType blank:true, nullable: true
		logo blank:true, nullable: true 
		redirectURL blank:true, nullable: true, matches: "[a-zA-Z0-9+&@#/%?=~_|!:,.;]+"
		acronym blank:true, nullable: true
	}
	
	static mapping = {
		
		table 'client_info'
		version false
		name column: 'name'
		authType column: 'auth_type'
		logo column: 'logo'
		redirectURL column: 'redirect_url'
		acronym column: 'acronym'
	}
	
	/**
	 * Method fired immediately after an object is loaded from the db
	 * Method to trim the data.
	 *
	 */
	def afterLoad() {
		
		name = name?.trim()
		authType = authType?.trim()
		redirectURL = redirectURL?.trim()
		logo = logo?.trim()
		acronym = acronym?.trim()
	}
}
