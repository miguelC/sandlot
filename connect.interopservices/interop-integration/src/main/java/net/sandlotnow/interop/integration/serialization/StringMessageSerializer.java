/**
 *======================================================================================
 * PixV3QueryRequestSerializer.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- PixV3QueryRequestSerializer
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

import net.sandlotnow.interop.audit.InteropAuditMessageSerializer;

//Imports

public class StringMessageSerializer implements InteropAuditMessageSerializer {

    @Override
    public String serialize(Object message) {
        if(message == null){
            return null;
        }
        return message.toString();
    }

    @Override
    public Object deserialize(String message) {
        return message;
    }

}
