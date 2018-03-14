/**
 *======================================================================================
 * EdgeEndpointActor.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- EdgeEndpointActor
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 21, 2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:  	Description 
 *  				Design Pattern(s):
 *  				 - Factory Method
 *  				 - Chain of Authority
 *  				 - Facade
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.interop.ihe.model;

//Imports

public enum EdgeEndpointActor {
    SENDER,
    RECEIVER,
    ANY
}
