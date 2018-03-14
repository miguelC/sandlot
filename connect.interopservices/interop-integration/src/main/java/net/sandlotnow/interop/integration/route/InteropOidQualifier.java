/**
 *======================================================================================
 * InteropOidQualifier.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropOidQualifier
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jul 2, 2015
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
package net.sandlotnow.interop.integration.route;

//Imports

public enum InteropOidQualifier {
    ORGANIZATION,
    ASSIGNINGAUTHORITY,
    REPOSITORYUNIQUEOID,
    DEVICE,
    DEVICEORASSIGNINGAUTHORITY,
    UNKNOWN
            
}
