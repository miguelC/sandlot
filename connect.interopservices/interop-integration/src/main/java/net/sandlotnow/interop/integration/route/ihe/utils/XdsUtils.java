/**
 *======================================================================================
 * XdsUtils.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- XdsUtils
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Sep 30, 2015
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
package net.sandlotnow.interop.integration.route.ihe.utils;

//Imports
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryError;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryErrorList;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType;

public class XdsUtils {


    public static RegistryResponseType createRegistryErrorResponseRepositoryError(
            String errorMessage){
        return createRegistryErrorResponse(errorMessage, XdsConstants.XDS_ERR_REPOSITORY_ERROR);
    }
    
    public static RegistryResponseType createRegistryErrorResponseRegistryError(
            String errorMessage){
        return createRegistryErrorResponse(errorMessage, XdsConstants.XDS_ERR_REGISTRY_ERROR);
    }
    
    public static RegistryResponseType createRegistryErrorResponse(
            String errorMessage,
            String errorCode) {
        RegistryResponseType response = new RegistryResponseType();
        response.setStatus(XdsConstants.XDS_B_STATUS_FAILURE);
        //response.setRequestId(requestId);
        
        response.setRegistryErrorList(new RegistryErrorList());
        response.getRegistryErrorList().setHighestSeverity(XdsConstants.XDS_ERR_SEVERITY_ERROR);
        RegistryError regError = new RegistryError();
        regError.setErrorCode(errorCode);
        regError.setCodeContext(errorMessage);
        regError.setSeverity(XdsConstants.XDS_ERR_SEVERITY_ERROR);
        response.getRegistryErrorList().getRegistryError().add(regError);
        return response;
    }
}
