/**
 *======================================================================================
 * License.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- License domain
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	License element class.
 *
 *
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

 // Package
package net.sandlotnow.config

// System Imports
import java.util.Date;

/**
 * Domain class of License
 * @author praveen13126
 *
 */
class License {
	
	//PROPERTIES
	String text
	Date createdDate
	Date effectiveDate
	
	//STATIC PROPERTIES
    static constraints = {
		
		text blank:false
		createdDate blank:false
		effectiveDate blank:false, 
					  validator: { val, obj ->
						  
						  if(val<obj.createdDate) {
							  
							  return ['invalid']
						  }
					  }
	}
	
	static mapping = {
		
		table 'license'
		version false
		text column: 'text'
		createdDate column: 'created_date'
		effectiveDate column: 'effective_date'
	}
	
	/**
	 * Method fired immediately after an object is loaded from the db
	 * Method to trim the data.
	 *
	 */
	def afterLoad() {
		
		text = text?.trim()
	}
}
