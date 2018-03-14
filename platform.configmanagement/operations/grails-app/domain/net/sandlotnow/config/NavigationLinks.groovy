/**
 *======================================================================================
 * Navigation Links.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Navigation links domain
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Naviation links element class.
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
 * Domain class of navigation links
 * @author praveen13126
 *
 */
class NavigationLinks {

	//PROPERTIES
	String name
	String linkType
	String url
	Integer orderNumber
	
	//STATIC PROPERTIES
	static constraints = {
		
		name blank:false, matches: "[a-zA-Z0-9 ]+"
		linkType blank:false, matches: "[a-zA-Z0-9 ]+"
		url blank:false,  matches: "[a-zA-Z0-9+&@#/%?=~_|!:,.;]+"
		orderNumber blank:false, min:1, unique: true 
	}
	
	static mapping = {
		
		table 'navigation_links'
		version false
		name column:'name'
		linkType column: 'link_type'
		url column: 'url'
		orderNumber column: 'order_number'
	}
	
	/**
	 * Method fired immediately after an object is loaded from the db
	 * Method to trim the data.
	 *
	 */
	def afterLoad() {
		
		name = name?.trim()
		linkType = linkType?.trim()
		url = url?.trim()
	}
}
