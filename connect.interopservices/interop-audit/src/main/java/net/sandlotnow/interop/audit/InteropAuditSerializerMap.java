/**
 *======================================================================================
 * SandlotAuditSerializerMap.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotAuditSerializerMap
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jun 23, 2015
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
package net.sandlotnow.interop.audit;

import java.util.HashMap;
//Imports
import java.util.Map;

public class InteropAuditSerializerMap {
    
    private InteropAuditMessageSerializer defaultMessageSerializer;
    
    public InteropAuditMessageSerializer getDefaultMessageSerializer() {
        return defaultMessageSerializer;
    }

    public void setDefaultMessageSerializer(
            InteropAuditMessageSerializer defaultMessageSerializer) {
        this.defaultMessageSerializer = defaultMessageSerializer;
    }

    private Map<String, InteropAuditMessageSerializer> messageSerializers;

    public Map<String, InteropAuditMessageSerializer> getMessageSerializers() {
        return messageSerializers;
    }

    public void setMessageSerializers(
            Map<String, InteropAuditMessageSerializer> messageSerializers) {
        this.messageSerializers = messageSerializers;
    }
    
    public void addMessageSerializer(String messageType, InteropAuditMessageSerializer serializer){
        if(messageSerializers == null) messageSerializers = new HashMap<String, InteropAuditMessageSerializer>();
        
        messageSerializers.put(messageType, serializer);
    }
    
    public InteropAuditMessageSerializer getMessageSerializer(String messageType){
        if(messageSerializers == null) return defaultMessageSerializer;
        InteropAuditMessageSerializer serializer = messageSerializers.get(messageType);
        if(serializer == null){
            return defaultMessageSerializer;
        }
        return serializer;
        
    }
}
