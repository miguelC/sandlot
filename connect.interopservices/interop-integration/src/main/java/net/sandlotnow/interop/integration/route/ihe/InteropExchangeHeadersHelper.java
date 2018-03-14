/**
 *======================================================================================
 * InteropExchangeHeadersHelper.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropExchangeHeadersHelper
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 20, 2015
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
package net.sandlotnow.interop.integration.route.ihe;

import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.integration.route.InteropOidQualifier;
import net.sandlotnow.interop.integration.route.InteropRouteConstants;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;

//Imports

public abstract class InteropExchangeHeadersHelper {

    public static Exchange fillInteropHeaders(
            String edgeSystemOid, 
            String edgeSystemId,
            String transactionId,
            boolean isDestination,
            Exchange exchange){

        boolean outNotNull = exchange.getOut() != null;
        boolean edgeIdNotNull = StringUtils.isNotBlank(edgeSystemId);
        exchange.setProperty(InteropRouteConstants.HEADER_EDGESYSTEM_TRANSACTION_ID, transactionId);
        if(outNotNull){
            exchange.getOut().setHeader(InteropRouteConstants.HEADER_EDGESYSTEM_TRANSACTION_ID, transactionId);
        }
        
        
        InteropOidQualifier qualifier = getOidQualifierFromTransaction(transactionId, isDestination);
        
        if(isDestination){
            if(edgeIdNotNull){
                exchange.setProperty(InteropRouteConstants.HEADER_EDGESYSTEM_DEST, edgeSystemId);
            }
            exchange.setProperty(InteropRouteConstants.HEADER_EDGESYSTEM_OID_DEST, edgeSystemOid);
            exchange.setProperty(InteropRouteConstants.HEADER_EDGESYSTEM_OID_DEST_QUALIFIER, qualifier);
            if(outNotNull){
                if(edgeIdNotNull){
                  exchange.getOut().setHeader(InteropRouteConstants.HEADER_EDGESYSTEM_DEST, edgeSystemId);
                }
                exchange.getOut().setHeader(InteropRouteConstants.HEADER_EDGESYSTEM_OID_DEST, edgeSystemOid);
                exchange.getOut().setHeader(InteropRouteConstants.HEADER_EDGESYSTEM_OID_DEST_QUALIFIER, qualifier);
            }
        }
        else {
            if(edgeIdNotNull){
                exchange.setProperty(InteropRouteConstants.HEADER_EDGESYSTEM_SRC, edgeSystemId);
            }
            exchange.setProperty(InteropRouteConstants.HEADER_EDGESYSTEM_OID_SRC, edgeSystemOid);
            exchange.setProperty(InteropRouteConstants.HEADER_EDGESYSTEM_OID_SRC_QUALIFIER, qualifier);
            if(outNotNull){
                if(edgeIdNotNull){
                    exchange.getOut().setHeader(InteropRouteConstants.HEADER_EDGESYSTEM_SRC, edgeSystemId);
                }
                exchange.getOut().setHeader(InteropRouteConstants.HEADER_EDGESYSTEM_OID_SRC, edgeSystemOid);
                exchange.getOut().setHeader(InteropRouteConstants.HEADER_EDGESYSTEM_OID_SRC_QUALIFIER, qualifier);
            }
        }
        
        return exchange;
    }

    public static InteropOidQualifier getOidQualifierFromTransaction(String transactionId, boolean isDestination){

        if(transactionId.equals(EdgeTransaction.TRANSACTION_ITI18) && !isDestination){
            return InteropOidQualifier.ASSIGNINGAUTHORITY;           
        }
        else if(transactionId.equals(EdgeTransaction.TRANSACTION_ITI44) || 
                transactionId.equals(EdgeTransaction.TRANSACTION_ITI45) || 
                transactionId.equals(EdgeTransaction.TRANSACTION_ITI47) ){
            return InteropOidQualifier.DEVICEORASSIGNINGAUTHORITY;
        }
        else if(transactionId.equals(EdgeTransaction.TRANSACTION_ITI41) ||
                transactionId.equals(EdgeTransaction.TRANSACTION_ITI43) ||
                (transactionId.equals(EdgeTransaction.TRANSACTION_ITI18) && isDestination)){
            return InteropOidQualifier.REPOSITORYUNIQUEOID;                    
        }
        else {
            return InteropOidQualifier.UNKNOWN;           
        }
    }
}
