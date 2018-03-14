/**
 *======================================================================================
 * ProvideAndRegisterDocumentSetRequestSerializer.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- ProvideAndRegisterDocumentSetRequestSerializer
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Aug 11, 2015
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
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.extern.slf4j.Slf4j;

import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType;

import net.sandlotnow.interop.audit.InteropAuditMessageSerializer;
import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageRenderingUtils;

@Slf4j
public class ProvideAndRegisterDocumentSetRequestSerializer implements
        InteropAuditMessageSerializer {

    @Override
    public String serialize(Object message) {

        if (message == null) {
            return null;
        }
        if (message instanceof ProvideAndRegisterDocumentSetRequestType) {
            ProvideAndRegisterDocumentSetRequestType pnr = (ProvideAndRegisterDocumentSetRequestType) message;
            List<byte[]> docs = XdsMessageRenderingUtils.copyOrderedDocumentData(pnr);
            String renderedMessage = XdsMessageRenderingUtils.renderEbxml(message, true);
            XdsMessageRenderingUtils.reWriteOrderedDocumentData(pnr, docs);

            return renderedMessage;
        } else {
            return message.toString();
        }
    }

    @Override
    public Object deserialize(String message) {
        try {
            JAXBContextFactory factory = JAXBContextFactory.getInstance();
            JAXBContext context = factory.getJaxBContext(ProvideAndRegisterDocumentSetRequestType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader reader = new StringReader(message);

            Object unmarshalled = unmarshaller.unmarshal(reader);
            ProvideAndRegisterDocumentSetRequestType original = (ProvideAndRegisterDocumentSetRequestType) unmarshalled;
            return original;
        } catch (JAXBException jbe) {
            log.error(
                    "Error de-serializing ProvideAndRegisterDocumentSet .. returning string object",
                    jbe);
            return message;
        }
    }

}
