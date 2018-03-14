/**
 *======================================================================================
 * InteropAuditStatusFilter.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropAuditStatusFilter
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jan 18, 2016
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
package net.sandlotnow.interop.audit;

//Imports

public enum InteropAuditStatusFilter {
     All,
     FailedOnly,
     SucceededOnly
}
