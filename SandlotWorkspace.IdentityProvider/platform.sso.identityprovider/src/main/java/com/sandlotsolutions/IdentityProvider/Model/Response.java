/**
 *======================================================================================
 * Response.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Response
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			02/12/2015
 *  				Original development
 *  @author 		Hari Moosani
 *  @version 		1.0
 *  Description:    Response class contains a public,private and a protected	
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
package com.sandlotsolutions.IdentityProvider.Model;

//Project Imports

//System Imports
import java.util.Set;


public class Response{
	
	//PROPERTIES
	private String status;
    private String errorMessage;
    private Set<String> ADGroups; 
	

	// PROPERTY ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION DETAIL ATTRIBUTES
		


	// STATIC DEFINITIONS
	
	// CONSTANTS & ENUMERATIONS

	//====================================================================
	// CONSTRUCTOR(S)
	//====================================================================	

	//====================================================================
	// PUBLIC METHODS
	//====================================================================	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public Set<String> getADGroups() {
		return ADGroups;
	}


	public void setADGroups(Set<String> aDGroups) {
		ADGroups = aDGroups;
	}

	//====================================================================
	// PROTECTED METHODS
	//====================================================================	

	//====================================================================
	// PRIVATE METHODS
	//====================================================================	


}
