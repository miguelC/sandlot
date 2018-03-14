/**
 *======================================================================================
 * XdsRepositoryAndRegistryTest.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- XdsRepositoryAndRegistryTest
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jun 19, 2015
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
package net.sandlotnow.interop.integration.test

import groovy.sql.Sql.QueryCommand;
import net.sandlotnow.interop.integration.serialization.JAXBContextFactory;

import org.apache.commons.io.IOUtils
import org.apache.cxf.transport.servlet.CXFServlet
import org.junit.BeforeClass
import org.junit.AfterClass
import org.junit.Test
//import org.openehealth.ipf.commons.ihe.xds.core.SampleData
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AvailabilityStatus
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocument
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocumentSet
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsQuery
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse
import org.openehealth.ipf.commons.ihe.xds.core.responses.Response
import org.openehealth.ipf.commons.ihe.xds.core.responses.RetrievedDocumentSet
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status
import org.openehealth.ipf.platform.camel.ihe.ws.StandardTestContainer

import static org.junit.Assert.assertEquals

import javax.activation.DataHandler
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.openehealth.ipf.commons.ihe.xds.core.requests.query.QueryReturnType
import org.openehealth.ipf.commons.ihe.xds.core.requests.ProvideAndRegisterDocumentSet
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryRequest
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
class InteropIntegrationTest extends StandardTestContainer {
    
    protected Logger log = LoggerFactory.getLogger(getClass());
    
    def ITI18 = "xds-iti18://localhost:${port}/iti18Service"
    def ITI41 = "xds-iti41://localhost:${port}/iti41Service"
    def ITI42 = "xds-iti42://localhost:${port}/iti42Service"
    def ITI43 = "xds-iti43://localhost:${port}/iti43Service"
    def ITI45 = "pixv3-iti45://localhost:${port}/iti45Service"
    def ITI47 = "pdqv3-iti47://localhost:${port}/iti47Service"
    
    JAXBContext JAXB_CONTEXT;

    @BeforeClass
    static void classSetUp() {
        startServer(new CXFServlet(), 'context.xml', false)
        
    }
    
    @AfterClass
    static void classTeardown(){
        stopServer()
    }
    
    
//    @Test
//    public void testReverse() throws Exception {
//        String expected = "cba";
//        Object actual = producerTemplate.requestBody("direct:input2", "abc");
//        assertEquals(expected, actual);
//    }
    
//    @Test
//    public void testHL7PixFeedRoute() throws Exception {
//        Resource input = new ClassPathResource("/msg-01.hl7");
//        producerTemplate.requestBody("direct:inputAdtA01", input.getInputStream());
//        Resource result = new FileSystemResource("target/output/HZL.hl7");
//        assertEquals(
//                MessageAdapters.load("msg-01.hl7.expected").toString(),
//                MessageAdapters.make(result.getInputStream()).toString());
//    }

//    @Test
//    public void testParseSpringConfig() throws Exception {
//        String json = producerTemplate.requestBody("direct:parseConfig", "", String.class);
//    }
    
    @Test
    void testPixPdqV3() {
        
        String pixV3QueryString = this.getClass().getResource('/testData/Sample_ITI-45_Request.xml').getText('UTF-8')
        log.info("Unmarshalling pixv3 query .. ${pixV3QueryString}")
        
        def pixV3Query = new XmlSlurper().parseText(pixV3QueryString)
        log.info("Sender device is ${pixV3Query.sender.device.id.@root}")
        log.info("Receiver device is ${pixV3Query.receiver.device.id.@root}")
        def queryResponse = send(ITI45, pixV3QueryString, String.class)
        log.info("Received response from pixv3 query .. ${pixV3Query}")
    
        String pdqV3QueryString = this.getClass().getResource('/testData/Sample_ITI-47_Request.xml').getText('UTF-8')
        log.info("Unmarshalling pdqv3 query .. ${pdqV3QueryString}")

        queryResponse = runPdq(pdqV3QueryString)
        log.info("Service response : ${queryResponse}" )
    
    }
    
    String runPdq(String pdqV3QueryString){        
        def pdqV3Query = new XmlSlurper().parseText(pdqV3QueryString)
        log.info("Sender device is ${pdqV3Query.sender.device.id.@root}")
        log.info("Receiver device is ${pdqV3Query.receiver.device.id.@root}")
        def queryResponse = send(ITI47, pdqV3QueryString, String.class)
        log.info("Received response from pdqv3 query .. ${pdqV3Query}")
        queryResponse as String
    }
    
    //@Test
    void testStoredQuery() {
        
        Resource input = new ClassPathResource("/testData/Sample_ITI-18_Request.xml");

        JAXBContext context = JAXBContext.newInstance("org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AdhocQueryRequest queryReg = (AdhocQueryRequest) unmarshaller.unmarshal(input.getInputStream());

        def queryResponse = send(ITI18, queryReg, QueryResponse.class)
        
        assertEquals(queryResponse.toString(), Status.SUCCESS, queryResponse.status)
    }
    
    //@Test
    void testProvideAndRegister() {
   
            Resource inputPnR = new ClassPathResource("/testData/Sample_ITI-41_Request.xml");
            
            JAXBContext contextPnR = JAXBContext.newInstance(org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType.class);
            Unmarshaller unmarshallerPnR = contextPnR.createUnmarshaller();
            Object  unmarshalledPnR = unmarshallerPnR.unmarshal(inputPnR.getInputStream());
            def response = send(ITI41, unmarshalledPnR, Response.class)
            
 //           assertEquals(response.toString(), Status.SUCCESS, response.status)
    
    }
    
    //@Test
    void testEpicPnr(){
        
        Resource inputPnR = new ClassPathResource("/testData/Epic/Sample_ITI-41_Request.xml");
        
        JAXBContext contextPnR = JAXBContext.newInstance(org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType.class);
        Unmarshaller unmarshallerPnR = contextPnR.createUnmarshaller();
        Object  unmarshalledPnR = unmarshallerPnR.unmarshal(inputPnR.getInputStream());
        def response = send(ITI41, unmarshalledPnR, Response.class)
    }
    
    //@Test
    void testEpicStoredQuery(){
        
        Resource input = new ClassPathResource("/testData/Epic/Sample_ITI-18_Request.xml");

        JAXBContext context = JAXBContext.newInstance("org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AdhocQueryRequest queryReg = (AdhocQueryRequest) unmarshaller.unmarshal(input.getInputStream());

        def queryResponse = send(ITI18, queryReg, QueryResponse.class)
        
        assertEquals(queryResponse.toString(), Status.SUCCESS, queryResponse.status)
    }
    
    //@Test
    void testEpicPdq(){
        
        String pdqV3QueryString = this.getClass().getResource('/testData/Epic/Sample_ITI-47_Request.xml').getText('UTF-8')
        log.info("Unmarshalling pdqv3 query .. ${pdqV3QueryString}")

        String queryResponse = runPdq(pdqV3QueryString)
        log.info("Service response : ${queryResponse}" )
    }

//    @Test
//    void testRegister() {
//        def register = SampleData.createRegisterDocumentSet()
//        def docEntry = register.documentEntries[0]
//        def patientId = docEntry.patientId
//        patientId.id = UUID.randomUUID().toString()
//        docEntry.uniqueId = '1.2.3.4'
//
//        def response = send(ITI42, register, Response.class)
//        assertEquals(response.toString(), Status.SUCCESS, response.status)
//
//        def query = new FindDocumentsQuery()
//        query.patientId = docEntry.patientId
//        query.status = [AvailabilityStatus.APPROVED]
//        def queryReg = new QueryRegistry(query)
//        queryReg.returnType = QueryReturnType.LEAF_CLASS
//        def queryResponse = send(ITI18, queryReg, QueryResponse.class)
//        assertEquals(queryResponse.toString(), Status.SUCCESS, queryResponse.status)
//        assertEquals(1, queryResponse.documentEntries.size())
//        assertEquals(docEntry.uniqueId, queryResponse.documentEntries[0].uniqueId)
//    }

//    @Test
//    void testRetrieve() {
//        
//        Resource inputRetrieve = new ClassPathResource("/testData/Sample_ITI-43_Request.xml");
//        JAXBContext contextRetrieve = JAXBContext.newInstance(org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetRequestType.class);
//        Unmarshaller unmarshallerRetrieve = contextRetrieve.createUnmarshaller();
//        Object  retrieve = unmarshallerRetrieve.unmarshal(inputRetrieve.getInputStream());
//        def retrieveResponse = send(ITI43, retrieve, RetrievedDocumentSet.class)
//        assertEquals(retrieveResponse.toString(), Status.SUCCESS, retrieveResponse.status)

//        def attachments = retrieveResponse.documents[0].dataHandler.dataSource.attachments
//        assertEquals(2, retrieveResponse.documents.size())
    
//    }

    def read(dataHandler) {
        def inputStream = dataHandler.inputStream
        try {
            return IOUtils.toString(inputStream)
        }
        finally {
            inputStream.close()
        }
    }
}
