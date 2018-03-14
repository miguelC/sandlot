/**
 *======================================================================================
 * XdsMessageRenderingUtils.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- XdsMessageRenderingUtils
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Nov 13, 2015
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

//Imports

import groovy.lang.Closure;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.TypeConverter;
import org.apache.commons.io.IOUtils;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetResponseType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetResponseType.DocumentResponse;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveImagingDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType.Document;
import org.openehealth.ipf.commons.ihe.xds.core.requests.*;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Response;
import org.openehealth.ipf.commons.ihe.xds.core.responses.RetrievedDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.lcm.RemoveObjectsRequest;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.lcm.SubmitObjectsRequest;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryRequest;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.attachment.AttachmentMarshaller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Utility class for rendering of ebXML stub POJOs and simplified
 * XDS model classes into XML.
 *
 * @author Dmytro Rud
 */
abstract public class XdsMessageRenderingUtils {

    /**
     * Correspondence between relevant XDS data types from
     * ebXML model and IPF simplified model.
     */
    private static final Map<Class, Class> TYPES_CORRESPONDENCE;
    private static final JAXBContext JAXB_CONTEXT;

    static {
        TYPES_CORRESPONDENCE = new HashMap<>();

        /* --------- REQUESTS --------- */

        // ITI-18, 38, 51, 63
        TYPES_CORRESPONDENCE.put(QueryRegistry.class, AdhocQueryRequest.class);

        // ITI-41
        TYPES_CORRESPONDENCE.put(ProvideAndRegisterDocumentSet.class, ProvideAndRegisterDocumentSetRequestType.class);

        // ITI-42, 57, 61
        TYPES_CORRESPONDENCE.put(RegisterDocumentSet.class, SubmitObjectsRequest.class);

        // ITI-39, 43
        TYPES_CORRESPONDENCE.put(RetrieveDocumentSet.class, RetrieveDocumentSetRequestType.class);

        // ITI-62
        TYPES_CORRESPONDENCE.put(RemoveDocumentSet.class, RemoveObjectsRequest.class);

        // RAD-69, 75
        TYPES_CORRESPONDENCE.put(RetrieveImagingDocumentSet.class, RetrieveImagingDocumentSetRequestType.class);

        /* --------- RESPONSES --------- */

        // ITI-18, 38, 51, 63
        TYPES_CORRESPONDENCE.put(QueryResponse.class, AdhocQueryResponse.class);

        // ITI-41, 42, 57, 61, 62
        TYPES_CORRESPONDENCE.put(Response.class, RegistryResponseType.class);

        // ITI-39, ITI-43, RAD-69, RAD-75
        TYPES_CORRESPONDENCE.put(RetrievedDocumentSet.class, RetrieveDocumentSetResponseType.class);


        try {
            JAXB_CONTEXT = JAXBContext.newInstance(
                    AdhocQueryRequest.class,
                    ProvideAndRegisterDocumentSetRequestType.class,
                    SubmitObjectsRequest.class,
                    RetrieveDocumentSetRequestType.class,
                    RemoveObjectsRequest.class,
                    RetrieveImagingDocumentSetRequestType.class,
                    AdhocQueryResponse.class,
                    RegistryResponseType.class,
                    RetrieveDocumentSetResponseType.class,

                    org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml21.ProvideAndRegisterDocumentSetRequestType.class,
                    org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs21.rs.SubmitObjectsRequest.class,
                    org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs21.rs.RegistryResponse.class,
                    org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs21.query.AdhocQueryRequest.class
            );
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Constructor.
     */
    private XdsMessageRenderingUtils() {
        throw new IllegalStateException("Cannot instantiate helper class");
    }


    /**
     * Renders an XDS object (either ebXML POJO or an object from the simplified model)
     * contained in the input message of given Camel exchange.
     *
     * @param exchange
     *      Camel exchange containing the XDS object in <code>exchange.in.body</code>.
     * @return
     *      XML representation of the XDS object contained in the given Camel exchange.
     */
    public static String render(Exchange exchange) {
        return doRender(exchange, exchange.getIn().getBody());
    }


    /**
     * Renders an XDS object (either ebXML POJO or an object from the simplified model)
     * contained in the given Camel exchange.
     *
     * @param exchange
     *      Camel exchange containing the XDS object.
     * @param closure
     *      Groovy closure to extract the XDS object from the exchange.
     * @return
     *      XML representation of the XDS object contained in the given Camel exchange.
     */
    public static String render(Exchange exchange, Closure closure) {
        return doRender(exchange, closure.call(exchange));
    }


    /**
     * Renders an XDS object (either ebXML POJO or an object from the simplified model)
     * contained in the given Camel exchange.
     *
     * @param exchange
     *      Camel exchange containing the XDS object.
     * @param expression
     *      Camel expression to extract the XDS object from the exchange.
     * @return
     *      XML representation of the XDS object contained in the given Camel exchange.
     */
    public static String render(Exchange exchange, Expression expression) {
        return doRender(exchange, expression.evaluate(exchange, Object.class));
    }


    /**
     * Renders an XDS object (either ebXML POJO or an object from the simplified model).
     *
     * @param exchange
     *      Camel exchange.
     * @param body
     *      XDS object (either ebXML POJO or an object from the simplified model).
     * @return
     *      XML representation of the given XDS object.
     */
    public static String doRender(Exchange exchange, Object body) {
        if (TYPES_CORRESPONDENCE.containsKey(body.getClass())) {
            TypeConverter converter = exchange.getContext().getTypeConverter();
            body = converter.convertTo(TYPES_CORRESPONDENCE.get(body.getClass()), exchange, body);
        }
        return renderEbxml(body);
    }


    public static String renderEbxml(Object ebXml) {
        return renderEbxml(ebXml, false);
    }
    /**
     * Returns marshaled XML representation of the given ebXML POJO.
     * @param ebXml
     *      ebXML POJO.
     * @return
     *      XML string representing the given POJO.
     */
    public static String renderEbxml(Object ebXml, boolean readAttachments) {
        try {
            StringWriter writer = new StringWriter();
            Marshaller marshaller = JAXB_CONTEXT.createMarshaller();
            if(!readAttachments){
              marshaller.setAttachmentMarshaller(new NonReadingAttachmentMarshaller());
            }
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(ebXml, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * An attachment marshaller implementation which does not read any data
     * from the provided data handlers in order to keep all streams usable.
     */
    private static class NonReadingAttachmentMarshaller extends AttachmentMarshaller {
        @Override
        public boolean isXOPPackage() {
            return true;
        }

        @Override
        public String addMtomAttachment(DataHandler data, String elementNamespace, String elementLocalName) {            
            return attachmentDescription(data.getName(), null, data.getContentType());
        }

        @Override
        public String addMtomAttachment(byte[] data, int offset, int length, String mimeType,
                                        String elementNamespace, String elementLocalName)
        {
            String size = Integer.toString(Math.min(length, data.length - offset));
            return attachmentDescription(null, size, mimeType);
        }

        @Override
        public String addSwaRefAttachment(DataHandler data) {
            return attachmentDescription(data.getName(), null, data.getContentType());
        }

        private static String attachmentDescription(String name, String size, String contentType) {
            return "Attachment: name='" + defaultString(name, "[unknown]") + "', size='" + defaultString(size, "[unknown]") + "', content type='" + defaultString(contentType, "[unknown]") + '\'';
        }

    }

    public static List<byte[]> copyOrderedDocumentData(ProvideAndRegisterDocumentSetRequestType pnr){

        List<byte[]> docs = new ArrayList<byte[]>();
        try{
            for ( Document doc : pnr.getDocument()){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                IOUtils.copy(doc.getValue().getInputStream(), baos);
                byte[] bytes = baos.toByteArray();
                docs.add( bytes);
            }
        }
        catch(IOException ex){
            
        }
        return docs;
    }
    
    public static void reWriteOrderedDocumentData(
            ProvideAndRegisterDocumentSetRequestType pnr,
            List<byte[]> docs){
        int counter = 0;
        for ( Document doc : pnr.getDocument()){

            DataSource data = new ByteArrayDataSource(docs.get(counter), "text/xml");
            doc.setValue(new DataHandler(data));
            counter++;
        }
    }
    

    public static List<byte[]> copyOrderedRetrievedDocumentData(RetrieveDocumentSetResponseType retrieve){

        List<byte[]> docs = new ArrayList<byte[]>();
        try{
            for ( DocumentResponse doc : retrieve.getDocumentResponse()){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                IOUtils.copy(doc.getDocument().getInputStream(), baos);
                byte[] bytes = baos.toByteArray();
                docs.add( bytes);
            }
        }
        catch(IOException ex){
            
        }
        return docs;
    }
    
    public static void reWriteOrderedRetrievedDocumentData(
            RetrieveDocumentSetResponseType retrieve,
            List<byte[]> docs){
        int counter = 0;
        for ( DocumentResponse doc : retrieve.getDocumentResponse()){

            DataSource data = new ByteArrayDataSource(docs.get(counter), "text/xml");
            doc.setDocument(new DataHandler(data));
            counter++;
        }
    }
}
