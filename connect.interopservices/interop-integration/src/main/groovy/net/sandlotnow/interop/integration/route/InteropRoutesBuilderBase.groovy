/**
 *======================================================================================
 * SandlotRoutesBuilderBase.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotRoutesBuilderBase
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    A base class for all sandlot DSL defined routes defining as a minimum 
 *                  a from endpoint name and an optional to endpoint name. The idea behind 
 *                  this is to use DSL to define single routes in groovy code that can be 
 *                  used in the camel context of the application. The application will use 
 *                  any groovy class defined in this class's package by default.
 *
 *  				Design Pattern(s):
 *  				 - None
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/
package net.sandlotnow.interop.integration.route

import org.apache.camel.spring.SpringRouteBuilder;

abstract class InteropRoutesBuilderBase extends SpringRouteBuilder {
	
	String routeFromName
	
	String routeToName
	
    abstract void configure()
}
