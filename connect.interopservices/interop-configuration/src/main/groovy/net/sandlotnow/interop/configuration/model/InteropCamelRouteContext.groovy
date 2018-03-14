/**
 *======================================================================================
 * InteropCamelRouteContext.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropCamelRouteContext
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    This class represents the configuration of a camel route context in a
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

class InteropCamelRouteContext {
	String id
	
	Map<String, InteropCamelRoute> routes = new HashMap<String, InteropCamelRoute>()
}
