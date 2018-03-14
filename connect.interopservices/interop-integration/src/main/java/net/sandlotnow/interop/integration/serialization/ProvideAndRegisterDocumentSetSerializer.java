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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.extern.slf4j.Slf4j;

import org.openehealth.ipf.commons.ihe.xds.core.requests.ProvideAndRegisterDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType;

import net.sandlotnow.interop.audit.InteropAuditMessageSerializer;
import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageRenderingUtils;

import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.EbXML30Converters;

@Slf4j
public class ProvideAndRegisterDocumentSetSerializer implements
        InteropAuditMessageSerializer {
    
    private boolean readDocumentStream;
    
    public boolean isReadDocumentStream() {
        return readDocumentStream;
    }

    public void setReadDocumentStream(boolean readStream) {
        this.readDocumentStream = readStream;
    }

    @Override
    public String serialize(Object message) {

        if (message == null) {
            return null;
        }
        String renderedMessage = new String();
        if (message instanceof ProvideAndRegisterDocumentSet) {
            
            ProvideAndRegisterDocumentSetRequestType converted = EbXML30Converters.convert((ProvideAndRegisterDocumentSet) message);
            if(this.isReadDocumentStream()){
                log.info("*** Reading stream in ProvideAndRegisterDocumentSet ***");
                //List<byte[]> docs = XdsMessageRenderingUtils.copyOrderedDocumentData(converted);
                renderedMessage = XdsMessageRenderingUtils.renderEbxml(message, true);
                //XdsMessageRenderingUtils.reWriteOrderedDocumentData(converted, docs);
            }
            else{
                // TODO this is similar to ProvideAndRegisterDocumentSetRequestTypeSerializer but does not read the stream
                log.info("*** NOT Reading stream in ProvideAndRegisterDocumentSet ***");
                renderedMessage = XdsMessageRenderingUtils.renderEbxml(converted);
            }
            
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
            ProvideAndRegisterDocumentSet converted = EbXML30Converters.convert(original);
            return converted;
        } catch (JAXBException jbe) {
            log.error(
                    "Error de-serializing ProvideAndRegisterDocumentSet .. returning string object",
                    jbe);
            return message;
        }
    }

}
