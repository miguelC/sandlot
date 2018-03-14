/**
 *======================================================================================
 * MarshallingException.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- MarshallingException
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

//Imports

public class MarshallingException extends Exception{

    private static final long serialVersionUID = 201509173232807009L;
    
    public MarshallingException()
    {
    }

    public MarshallingException(String message)
    {
        super(message);
    }

    public MarshallingException(Throwable cause)
    {
        super(cause);
    }

    public MarshallingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MarshallingException(String message, Throwable cause, 
                                       boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
