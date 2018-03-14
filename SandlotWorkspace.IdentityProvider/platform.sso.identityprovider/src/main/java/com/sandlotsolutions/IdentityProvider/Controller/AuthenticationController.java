/**
 *======================================================================================
 * AuthenticationController.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- AuthenticationController
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			02/12/2015
 *  				Original development
 *  @author 		Hari Moosani
 *  @version 		1.0
 *  Description:    AuthenticationController class contains a public,private and a protected	
 *  				A parametrized constructor,default constructor ,a private method
 *  				a public method that checks for file payload if it exists the file 
 *                  is processed further else a false value is returned.
 *                 
 *  				Design Pattern(s):
 *  				 - None
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package 
package com.sandlotsolutions.IdentityProvider.Controller;

// Project Imports
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;

import com.sandlotsolutions.IdentityProvider.Model.*;

import org.springframework.ldap.AuthenticationException;

// System Imports
import org.springframework.web.bind.annotation.*;
// Logging imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AuthenticationController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	@Qualifier("ldapConnectionManager")
	private LDAPConnectionManager ldapConnector;
	
	//PROPERTIES

	// PROPERTY ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION DETAIL ATTRIBUTES
		
	// STATIC DEFINITIONS
	
	// CONSTANTS & ENUMERATIONS

    
	//====================================================================
	// CONSTRUCTOR(S)
	//====================================================================	
	
	private AuthenticationController() {
		
		// ********************************
		//     BODY INTENTIONALLY BLANK	
		// ********************************		
	}

	//====================================================================
	// PUBLIC FUNCTIONS
	//====================================================================	
	

	 /** 
	  *
	  * A parameterized constructor that takes name and taxId as parameters of string type,
	  * the constructor throws a runtime exception named SandlotRuntimeException
	  *
	  * @exception		SandlotRuntimeExcpetion: A user defined exception class that handles all unchecked exceptions.
	  * 				whenever an exception occurs sandlotRuntimeException class is called  and the same is thrown in 
	  *                 the catch block . A finally block of exception is called  irrespective of any exception occurrence 
	  * 
	  **/
    @RequestMapping(value = AuthenticationURIEndPoints.AUTHENTICATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response authenticate(@RequestBody Request request) throws Exception {

    	// Log 
    	log.info("Started authenticate for user " + request.getUserName() + " on domain " + request.getDomain());
    	
    	Response authenticateResponse = new Response();
    	
    	
    	try {
            // Get the AD from the domain
    		String retVal = "OK";
    		//Following is a test block -- remove when not necessary anymore
    		/*
    		List<String> names = ldapConnector.getAllPersonNames(request.getDomain());
    		if(names == null || names.size() <= 0){
    			retVal = "FAIL";
    		}
            */
            boolean authStatus = ldapConnector.authenticate(request.getDomain(), request.getUserName(), request.getPassword());
            retVal = authStatus ? LDAPConnectionManager.STATUS_OK: LDAPConnectionManager.STATUS_FAIL;
            log.info("Authentication status is " + retVal);
            
            // TODO Put real response here, remove hardcoded response
            authenticateResponse.setStatus(retVal);
            HashSet<String> hardcodedUserGroups = new HashSet<String>();
            hardcodedUserGroups.add("INGEST_SANDLOT_ADMIN");
            hardcodedUserGroups.add("INGEST_SANDLOT_NORMAL");
            hardcodedUserGroups.add("ADMINCL_SANDLOT_ADMIN");
            authenticateResponse.setADGroups(hardcodedUserGroups);
    	}
    	catch(AuthenticationException aex){
    		log.error ("Authentication Failed : " + aex.getMessage());
        	authenticateResponse = buildErrorResponse(aex.getMessage());
    	}
    	catch (Exception ex) {
    		
    		log.error ("Application error", ex);
        	authenticateResponse = buildErrorResponse(ex.getMessage());
    	}

        return authenticateResponse;
    }
	 /** 
	  *
	  * A parameterized constructor that takes name and taxId as parameters of string type,
	  * the constructor throws a runtime exception named SandlotRuntimeException
	  *
	  * @exception		SandlotRuntimeExcpetion: A user defined exception class that handles all unchecked exceptions.
	  * 				whenever an exception occurs sandlotRuntimeException class is called  and the same is thrown in 
	  *                 the catch block . A finally block of exception is called  irrespective of any exception occurrence 
	  * 
	  **/
	  @RequestMapping(value = AuthenticationURIEndPoints.AUTHORIZE, method = RequestMethod.POST)
	  public @ResponseBody Response authorize(@RequestBody Request userCreds) throws Exception{
	
	  	Response authorizeResponse = new Response();
	  	
	    return authorizeResponse;
	  }

  
    //====================================================================
	// PROTECTED FUNCTIONS
	//====================================================================	

    
	//====================================================================
	// PRIVATE FUNCTIONS
	//====================================================================	

    /**
     * This method simply builds a @see Response object that indicates a failure with the error message
     */
    private Response buildErrorResponse(String errorMessage){

    	Response response = new Response();
    	response.setStatus("FAIL");
    	response.setErrorMessage(errorMessage);
    	return response;
    }
}
