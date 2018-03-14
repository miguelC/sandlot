/**
 *======================================================================================
 * AdhocQueryResponseSerializer.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- AdhocQueryResponseSerializer
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jul 28, 2015
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
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import lombok.extern.slf4j.Slf4j;

import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType;

import net.sandlotnow.interop.audit.InteropAuditMessageSerializer;

@Slf4j
public class RegistryResponseSerializer implements
        InteropAuditMessageSerializer {

    @Override
    public String serialize(Object message) {

        if (message == null) {
            return null;
        }
        if (message instanceof RegistryResponseType) {
            try {
                JAXBContextFactory factory = JAXBContextFactory.getInstance();
                JAXBContext context = factory.getJaxBContext("org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs");
                Marshaller marshaller = context.createMarshaller();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                marshaller.marshal(message, outputStream);

                String serialized = outputStream.toString();
                return serialized;
            } catch (JAXBException jbe) {
                log.error(
                        "Error serializing RegistryResponseType .. returning toString()",
                        jbe);
                return message.toString();
            }
        } else {
            return message.toString();
        }
    }

    @Override
    public Object deserialize(String message) {
        try {
            JAXBContext context = JAXBContext.newInstance("org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs");
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader reader = new StringReader(message);

            Object unmarshalled = unmarshaller.unmarshal(reader);
            RegistryResponseType original = (RegistryResponseType) unmarshalled;
            return original;
        } catch (JAXBException jbe) {
            log.error(
                    "Error de-serializing RegistryResponseType .. returning string object",
                    jbe);
            return message;
        }
    }

}
