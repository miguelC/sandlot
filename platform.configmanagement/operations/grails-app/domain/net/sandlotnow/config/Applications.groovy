/**
 *======================================================================================
 * Applications.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Applications domain
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Application element class.
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
 * Domain class of application
 * @author praveen13126
 *
 */
class Applications {
	
	//PROPERTIES
	String name
	String url
	String display
	String description
	
	//STATIC PROPERTIES
    static constraints = {
		
		name blank:false, matches: "[a-zA-Z0-9 ]+"
		url blank:false, matches: "[a-zA-Z0-9+&@#/%?=~_|!:,.;]+"
		display blank:false, matches: "[a-zA-Z0-9]+"
				validator: {
					
					if (!(it.equals('yes')||it.equals('no'))) return ['invalid']
				}
	}
	
	static mapping = {
		
		table 'applications'
		version false
		name column: 'name'
		url column: 'url'
		display column: 'display'
	}
	
	/**
	 * Method fired immediately after an object is loaded from the db
	 * Method to trim the data.
	 *
	 */
	def afterLoad() {
		
		name = name?.trim()
		url = url?.trim()
		display = display?.trim()
		description = description?.trim()
	}
	
	/**
	 * Method fired immediately before an object is validated.
	 * Method to make uppercase the data.
	 *
	 */
	def beforeValidate() {
		
		display = display?.toUpperCase()
	}
}
