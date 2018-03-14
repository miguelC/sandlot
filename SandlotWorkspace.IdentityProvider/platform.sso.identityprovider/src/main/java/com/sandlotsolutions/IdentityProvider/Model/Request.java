/**
 *======================================================================================
 * Request.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Request
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			02/12/2015
 *  				Original development
 *  @author 		Hari Moosani
 *  @version 		1.0
 *  Description:    A request object.. may need to be renamed if other request 
 *                  objects are defined for these services.
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
import java.io.Serializable;


public class Request implements Serializable{
	
	//PROPERTIES
	private String domain;
	private String userName;
	private String password;

	// PROPERTY ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION DETAIL ATTRIBUTES
		
	// STATIC DEFINITIONS
	private static final long serialVersionUID = 12346542341L;
	
	// CONSTANTS & ENUMERATIONS

	//====================================================================
	// CONSTRUCTOR(S)
	//====================================================================	

	//====================================================================
	// PUBLIC METHODS
	//====================================================================	

	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain; 
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName; 
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password; 
	}
	
	//====================================================================
	// PROTECTED METHODS
	//====================================================================	

	//====================================================================
	// PRIVATE METHODS
	//====================================================================	


}
