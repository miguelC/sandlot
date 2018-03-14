/**
 *======================================================================================
 * EpicCCDModifierMCC45Processor.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- EpicCCDModifierMCC45Processor
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Dec 7, 2015
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
package net.sandlotnow.interop.integration.route.custom

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetResponseType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType.Document;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetResponseType.DocumentResponse;
import org.openehealth.ipf.commons.ihe.xds.core.requests.ProvideAndRegisterDocumentSet
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.EbXML30Converters;

import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageRenderingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Imports

class EpicCCDModifierMCC45Processor implements Processor {
    
    Logger log = LoggerFactory.getLogger(EpicCCDModifierMCC45Processor.class)
    
   public void process(Exchange exchange) throws Exception {
       
       ProvideAndRegisterDocumentSet message = exchange.in.getBody(ProvideAndRegisterDocumentSet.class)
       
       ProvideAndRegisterDocumentSetRequestType pnr = EbXML30Converters.convert(message);
       
       List<byte[]> docs = XdsMessageRenderingUtils.copyOrderedDocumentData(pnr);

       docs.each(){ doc ->
           def base64Doc = doc.toString()
           byte[] decoded = base64Doc.decodeBase64()
           def decodedDoc = new String(decoded)
           // Modify he ccd
           def ccd = new XmlSlurper().parseText(decodedDoc)
           
           byte[] encoded = decodedDoc.bytes.encodeBase64()
           doc = encoded
       }
       
       XdsMessageRenderingUtils.reWriteOrderedDocumentData(pnr, docs);
       
       exchange.getIn().setBody(pnr)
       
   }
   
}
