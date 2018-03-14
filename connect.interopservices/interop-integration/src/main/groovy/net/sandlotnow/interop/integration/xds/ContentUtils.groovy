/**
 *======================================================================================
 * ContentUtils.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- ContentUtils
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			May 5, 2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:  	Class taken from IPF xds samples 
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
package net.sandlotnow.interop.integration.xds

import java.security.MessageDigest
import org.apache.commons.io.IOUtils

/**
 * Utility functionality for document content.
 * @author Jens Riemschneider
 */
abstract class ContentUtils {
    private ContentUtils() {
        throw new UnsupportedOperationException('Cannot be instantiated')
    }
    
    /**
     * Calculates the size of the given content stream.
     * @param dataHandler
     *          the data handler to access the content.
     * @return the size in bytes.
     */
    static def size(dataHandler) {
        def content = getContent(dataHandler)
        def size = content.length
        size
    }

    /**
     * Calculates the SHA-1 of the given content stream.
     * @param dataHandler
     *          the data handler to access the content.
     * @return the SHA-1.
     */
    static def sha1(dataHandler) {
        def content = getContent(dataHandler)
        calcSha1(content)
    }

    private static def calcSha1(content) {
        def digest = MessageDigest.getInstance('SHA-1')
        def builder = new StringBuilder()
        digest.digest(content).each {
            def hexString = Integer.toHexString((int)it & 0xff)
            builder.append(hexString.length() == 2 ? hexString : '0' + hexString)
        }
        builder.toString()
    }

    /**
     * Retrieves the byte array from a datahandler.
     * @param dataHandler
     *          the data handler to access the content.
     * @return the content as a byte[].
     */
    static def getContent(dataHandler) {
        def inputStream = dataHandler.inputStream
        try {
            IOUtils.toByteArray(inputStream)
        }
        finally {
            inputStream.close()
        }
    }
}