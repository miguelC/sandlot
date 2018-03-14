/**
 *======================================================================================
 * InteropXdsException.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropXdsException
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Sep 28, 2015
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

//Imports

public class InteropXdsException extends Exception {

    private static final long serialVersionUID = 201509283232807009L;
    
    public InteropXdsException()
    {
    }

    public InteropXdsException(String message)
    {
        super(message);
    }

    public InteropXdsException(Throwable cause)
    {
        super(cause);
    }

    public InteropXdsException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InteropXdsException(String message, Throwable cause, 
                                       boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
