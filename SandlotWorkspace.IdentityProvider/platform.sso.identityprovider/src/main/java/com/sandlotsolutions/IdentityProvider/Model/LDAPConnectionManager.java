/**
 *======================================================================================
 * LDAPConnectionManager.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- LDAPConnectionManager
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			03/20/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    This class manages multiple LDAP connections in configuration objects
 *                  from spring framework
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
import java.util.List;
import java.util.Map;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

import javax.naming.directory.Attributes;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import com.sandlotsolutions.IdentityProvider.exceptions.SandlotException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LDAPConnectionManager {

	//PROPERTIES
	private Map<String, LDAPServerConfiguration> ldapConnections;
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION ASSOCIATIONS & AGGREGATIONS

	// IMPLEMENTATION DETAIL ATTRIBUTES
		
	// STATIC DEFINITIONS
	private static final Logger log = LoggerFactory.getLogger(LDAPConnectionManager.class);
	
	// CONSTANTS & ENUMERATIONS
    public static final String STATUS_OK = "OK";
    public static final String STATUS_FAIL = "FAIL";
	
    
	//====================================================================
	// CONSTRUCTOR(S)
	//====================================================================	

    public LDAPConnectionManager() {
		
		// ********************************
		//     BODY INTENTIONALLY BLANK	
		// ********************************		
    }

	//====================================================================
	// PUBLIC FUNCTIONS
	//====================================================================	
	
    /**
     * Setter method for @see ldapConnections property. 
     * Injected by Spring via autowired configuration from the spring config file.
     * @param ldapConnections
     */
	public void setLdapConnections(Map<String, LDAPServerConfiguration> ldapConnections) {
		this.ldapConnections = ldapConnections;
	}
    
	/**
	 * Do a search on an LDAP server associated with the given domain where objectclass=person
	 * @param domain
	 * @return List of persons cn values from ldap
	 * @throws SandlotException
	 */
    public List<String> getAllPersonNames(String domain) throws SandlotException {
    	LdapTemplate template = getLdapTemplate(domain);
	    return template.search(
	        query().where("objectclass").is("person"),
	        new AttributesMapper<String>() {
	            public String mapFromAttributes(Attributes attrs)
	                throws NamingException {
	                return attrs.get("cn").get().toString();
	            }
	       });
    }
    
    /**
     * Authenticate a user on an LDAP server associated with the given domain
     * @param domain
     * @param userName
     * @param password
     * @return "OK" if authentication succeeded or "FAIL" otherwise
     * @throws SandlotException
     */
    public boolean authenticate( String domain, String userName, String password ) throws SandlotException {
    	AndFilter filter = new AndFilter();
    	LDAPServerConfiguration serverConfig = getLdapServerConfiguration(domain);
    	filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter(serverConfig.getUserIdEntryName(), userName));
    	LdapTemplate template = serverConfig.getLdapTemplate();
    	if(template == null){    		
    		throw new SandlotException("Domain " + domain + " has a server configuration but it does not have an LDAP Connection configured");
        }
    	String dn = getDnOfTemplate(template);
    	log.info("Authenticating dn=" + dn + " userid=" + userName);
    	boolean result = template.authenticate(LdapUtils.emptyLdapName(), filter.toString(), password);
        return result;
    }

    /**
     * Method to test an ldap connection associated with a particular domain
     * @param domain
     * @return "OK" or "FAIL"
     * @throws SandlotException
     */
    public boolean testConnection( String domain) throws SandlotException {
    	    	
    	LdapTemplate template = getLdapTemplate(domain);
    	boolean result = true; //template.("", f.toString(), password);
        return result;
    }
    //====================================================================
	// PROTECTED FUNCTIONS
	//====================================================================	

    
	//====================================================================
	// PRIVATE FUNCTIONS
	//====================================================================	

    /**
     * Resolves and @see LdapTemplate by domain name.
     * The domain name is not necessarily the domain of the LDAP but an application assigned name for the ldap server.
     * @param domain Application assigned name for the ldap server as a domain of data
     * @return
     * @throws SandlotException
     */
    private LdapTemplate getLdapTemplate(String domain) throws SandlotException {

    	LDAPServerConfiguration serverConfig = getLdapServerConfiguration(domain);
    	LdapTemplate template = serverConfig.getLdapTemplate();
    	if(template == null){
    		throw new SandlotException("Domain " + domain + " has a server configuration but it does not have an LDAP Connection configured");
    	}
    	return template;
    }
    /**
     * Resolves an LDAP Server configuration by domain name. 
     * The domain name is not necessarily the domain of the LDAP but an application assigned name for the ldap server.
     * @param domain Application assigned name for the ldap server as a domain of data
     * @return
     * @throws SandlotException
     */
    private LDAPServerConfiguration getLdapServerConfiguration(String domain) throws SandlotException {

    	if(StringUtils.isEmpty(domain)){
    		throw new SandlotException("No domain provided in request");
    	}
    	LDAPServerConfiguration serverConfig = ldapConnections.get(domain);
    	if(serverConfig == null){
    		throw new SandlotException("Domain " + domain + " not configured with LDAP connection");
    	}
    	return serverConfig;
    }
    /**
     * Given an LdapTemplate object gets the Dn (Distinguished Name) of the LdapContext contained within
     * @param template
     * @return
     */
    private String getDnOfTemplate(LdapTemplate template){
    	String retVal = LdapUtils.emptyLdapName().toString();
    	try
    	{
    		retVal = template.getContextSource().getReadOnlyContext().getNameInNamespace();
    	}catch(NamingException ne){
    		log.error("Error getting dn of LDAP context", ne);
    	}
    	return retVal;
    }
}
