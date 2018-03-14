/**
 *======================================================================================
 * HibernateAwareObjectMapper.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- HibernateAwareObjectMapper
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Nov 18, 2015
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
package net.sandlotnow.interop.configuration.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

//Imports

public class HibernateAwareObjectMapper extends ObjectMapper {

    /**
     * 
     */
    private static final long serialVersionUID = 8053597316069772685L;

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }

}
