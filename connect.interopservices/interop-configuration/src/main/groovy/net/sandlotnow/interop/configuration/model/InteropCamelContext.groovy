/**
 *======================================================================================
 * InteropCamelContext.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropCamelContext
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    This class represents the configuration of the camel context in a
 *                  sandlot integration system
 *
 *  				Design Pattern(s):
 *  				 - None
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/
package net.sandlotnow.interop.configuration.model

class InteropCamelContext {
	
	String id
	
	String routeBuildersPackage
	
	List<String> routeContextRefs = new ArrayList<String>()
}
