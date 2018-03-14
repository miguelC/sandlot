/**
 *======================================================================================
 * LDAPServerConfiguration.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- LDAPServerConfiguration
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			03/20/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    This is a domain model class that serves as a configuration entry for 
 *                  an LDAP server. It contains all that is needed to connect and interact
 *                  with the LDAP server for the functionality provided in these services.
 *                 
 *  				Design Pattern(s):
 *  				 - None
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

//Package
package com.sandlotsolutions.IdentityProvider.Model;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Imports
import org.springframework.ldap.core.LdapTemplate;

public class LDAPServerConfiguration {


	//PROPERTIES
	private String userIdEntryName;
    private LdapTemplate ldapTemplate;
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION DETAIL ATTRIBUTES
		
	// STATIC DEFINITIONS
	
	// CONSTANTS & ENUMERATIONS

    
	//====================================================================
	// CONSTRUCTOR(S)
	//====================================================================	
    
	public LDAPServerConfiguration() {

		// ********************************
		//     BODY INTENTIONALLY BLANK	
		// ********************************	
	}

	//====================================================================
	// PUBLIC FUNCTIONS
	//====================================================================	
	
	/**
	 * Setter for user id entry name. 
	 * This is the name of the entry in the LDAP server that defines the username value.
	 * @param userIdEntryName
	 */
	public void setUserIdEntryName(String userIdEntryName){
		this.userIdEntryName = userIdEntryName;
	}
	/**
	 * Getter for user id entry name.
	 * This is the name of the entry in the LDAP server that defines the username value.
	 * @return
	 */
	public String getUserIdEntryName(){
		return userIdEntryName;
	}
	
    /**
     * Setter method for the LdapTemplate object associated with this server configuration.
     * @param ldapTemplate
     */
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
    /**
     * Getter method for the LdapTemplate object associated with this server configuration.
     * @return
     */
    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }

    
    //====================================================================
	// PROTECTED FUNCTIONS
	//====================================================================	

    
	//====================================================================
	// PRIVATE FUNCTIONS
	//====================================================================	

}
