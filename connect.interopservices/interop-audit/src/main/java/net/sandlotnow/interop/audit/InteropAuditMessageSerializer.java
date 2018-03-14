/**
 *======================================================================================
 * SandlotAuditMessageSerializer.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotAuditMessageSerializer
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jun 23, 2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:  	Interface that is used to serialize audit messages for storing in 
 *                  the database. Implementations of this interface deal with specific 
 *                  object types and are wired in the configuration of the auditing 
 *                  system by means of an InteropAuditSerializerMap bean in the Spring 
 *                  context.
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.interop.audit;

//Imports

public interface InteropAuditMessageSerializer {
    
    /**
     * Serialize the object message into a string
     * @param message
     * @return
     */
    String serialize(Object message);
    
    /**
     * De-serialize the string message into an object message
     * @param message
     * @return
     */
    Object deserialize(String message);
}
