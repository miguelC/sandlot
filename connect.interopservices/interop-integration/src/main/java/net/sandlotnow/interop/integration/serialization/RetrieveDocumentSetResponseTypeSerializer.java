/**
 *======================================================================================
 * RetrieveDocumentSetRequestTypeSerializer.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- RetrieveDocumentSetRequestTypeSerializer
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Nov 17, 2015
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

import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetResponseType;

import net.sandlotnow.interop.audit.InteropAuditMessageSerializer;
import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageRenderingUtils;

@Slf4j
public class RetrieveDocumentSetResponseTypeSerializer implements
                                   InteropAuditMessageSerializer{
    

    @Override
    public String serialize(Object message) {

        if (message == null) {
            return null;
        }
        if (message instanceof RetrieveDocumentSetResponseType) {
            RetrieveDocumentSetResponseType retrieve = (RetrieveDocumentSetResponseType) message;
            List<byte[]> docs = XdsMessageRenderingUtils.copyOrderedRetrievedDocumentData(retrieve);
            String renderedMessage = XdsMessageRenderingUtils.renderEbxml(message, true);
            XdsMessageRenderingUtils.reWriteOrderedRetrievedDocumentData(retrieve, docs);

            return renderedMessage;
        } else {
            return message.toString();
        }
    }

    @Override
    public Object deserialize(String message) {
        try {
            JAXBContextFactory factory = JAXBContextFactory.getInstance();
            JAXBContext context = factory.getJaxBContext(RetrieveDocumentSetResponseType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader reader = new StringReader(message);

            Object unmarshalled = unmarshaller.unmarshal(reader);
            RetrieveDocumentSetResponseType original = (RetrieveDocumentSetResponseType) unmarshalled;
            return original;
        } catch (JAXBException jbe) {
            log.error(
                    "Error de-serializing RetrieveDocumentSetResponseType .. returning string object",
                    jbe);
            return message;
        }
    }

}
