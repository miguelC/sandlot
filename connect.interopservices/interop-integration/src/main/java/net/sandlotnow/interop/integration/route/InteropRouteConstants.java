/**
 *======================================================================================
 * InteropRouteConstants.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropRouteConstants
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

public class InteropRouteConstants {

    public static final String HEADER_EDGESYSTEM_SRC = "EdgeSystemSource";
    public static final String HEADER_EDGESYSTEM_DEST = "EdgeSystemDestination";
    public static final String HEADER_EDGESYSTEM_OID_SRC = "EdgeSystemOidSource";
    public static final String HEADER_EDGESYSTEM_OID_DEST = "EdgeSystemOidDestination";
    public static final String HEADER_EDGESYSTEM_TRANSACTION_ID = "EdgeSystemTransactionId";

    public static final String HEADER_EDGESYSTEM_OID_SRC_QUALIFIER = "EdgeSystemOidSourceQualifier";
    public static final String HEADER_EDGESYSTEM_OID_DEST_QUALIFIER = "EdgeSystemOidDestinationQualifier";
    
    public static final String HEADER_TARGET_IHE_ROUTE = "TargetIheRoute";    

    public static final String HEADER_CAMEL_MESSAGE_BREADCRUMB_ID = "breadcrumbId";

    public static final String HEADER_CAMEL_EXCHANGE_SUBJECT_INFO = "ExchangeSubjectInfo";
}
