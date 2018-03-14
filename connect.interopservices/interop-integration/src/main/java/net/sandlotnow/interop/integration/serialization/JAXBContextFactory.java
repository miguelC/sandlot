/**
 *======================================================================================
 * JAXBContextFactory.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- JAXBContextFactory
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Sep 17, 2015
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
package net.sandlotnow.interop.integration.serialization;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
 
public class JAXBContextFactory {
    private static JAXBContextFactory instance = new JAXBContextFactory();
     
    private static final Map< String, JAXBContext > instances = new ConcurrentHashMap< String, JAXBContext >();
  
    private JAXBContextFactory() {
    }
  
    /**
     * Returns an existing JAXBContext if one for the particular namespace exists, 
     * else it creates an instance adds it to a internal map.
     * @param contextPath the context path
     * @throws JAXBException exception in creating context
     * @return a created JAXBContext
     */
    public JAXBContext getJaxBContext(final String contextPath) throws JAXBException {
     JAXBContext context = instances.get(contextPath);
        if (context == null) {
            context = JAXBContext.newInstance(contextPath);
            instances.put(contextPath, context);
        }
        return context;
    }
  
  
    /**
     * Returns an existing JAXBContext if one for the particular namespace exists,
     * else it creates an instance adds it to a internal map.
     * @param contextPath the context path
     * @throws JAXBException exception in creating context
     * @return a created JAXBContext
     */
    public JAXBContext getJaxBContext(final Class contextPath) throws JAXBException {
        JAXBContext context = instances.get(contextPath.getName());
        if (context == null) {
            context = JAXBContext.newInstance(contextPath);
            instances.put(contextPath.getName(), context);
        }
        return context;
    }
  
    /**
     * Get instance.
     * @return Instance of this factory
     */
    public static JAXBContextFactory getInstance() {
        return instance;
    }
}
