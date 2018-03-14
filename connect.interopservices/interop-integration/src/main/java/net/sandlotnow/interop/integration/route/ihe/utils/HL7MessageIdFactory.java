/**
 *======================================================================================
 * HL7MessageIdGenerator.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- HL7MessageIdGenerator
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Sep 16, 2015
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
package net.sandlotnow.interop.integration.route.ihe.utils;

import org.apache.commons.lang.StringUtils;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable;

//Imports

public class HL7MessageIdFactory {
    

    public static Identifiable generateHL7MessageIdRootOnly() {
        return generateHL7MessageIdRootOnly(generateMessageId());
    }
    
    public static Identifiable generateHL7MessageIdRootOnly(String rootId) {
        Identifiable messageId = new Identifiable();
        messageId.getAssigningAuthority().setUniversalId(rootId);
        return messageId;
    }

    public static Identifiable generateHL7MessageId(String rootId) {
        return generateHL7MessageId(rootId, generateMessageId());
    }
    
    public static Identifiable generateHL7MessageId(String rootId, String extensionId) {
        Identifiable messageId = new Identifiable();
        messageId.getAssigningAuthority().setUniversalId(rootId);
        messageId.setId(extensionId);
        return messageId;
    }
    
    public static String generateMessageId() {
        java.util.UUID uuid = java.util.UUID.randomUUID();
        return uuid.toString();
    }
    

    public static Identifiable buildIdentifiable(String root) {
        return buildIdentifiable(root, null, null);
    }

    public static Identifiable buildIdentifiable(String root, String extension) {
        return buildIdentifiable(root, extension, null);
    }

    public static Identifiable buildIdentifiable(String root, String extension, String assigningAuthorityName) {
        Identifiable id = new Identifiable();
        if (StringUtils.isNotBlank(root)) {
            id.getAssigningAuthority().setUniversalId(root);
        }
        if (StringUtils.isNotBlank(extension)) {
            id.setId(extension);
        }
        if (StringUtils.isNotBlank(assigningAuthorityName)) {
            // TODO revise
            id.getAssigningAuthority().setUniversalIdType(assigningAuthorityName);
        }
        return id;
    }
//    public static Identifiable buildIdentifiableNull() {
//        Identifiable id = new Identifiable();
//        id.getNullFlavor().add(HL7Constants.NULL_FLAVOR);
//        return id;
//    }
}
