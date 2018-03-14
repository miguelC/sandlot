/**
 *======================================================================================
 * InteropIntegrationCustomTest.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropIntegrationCustomTest
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
 package net.sandlotnow.interop.integration.custom
 
//Imports
 
import org.junit.BeforeClass
import org.junit.AfterClass
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.BootstrapWith
import org.springframework.beans.factory.annotation.Autowired
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner
import org.apache.camel.test.spring.CamelSpringTestSupport
import org.apache.camel.test.spring.CamelTestContextBootstrapper

@RunWith(CamelSpringJUnit4ClassRunner.class)
@BootstrapWith(CamelTestContextBootstrapper.class)
@ContextConfiguration(locations = [ "/context.xml" ])
class InteropIntegrationCustomTest extends CamelSpringTestSupport {
        
    protected Logger log = LoggerFactory.getLogger(getClass());

    
    @Test
    void testPixPdqV3() {
            
        String pdqV3QueryString = this.getClass().getResource('/testData/Sample_ITI-47_Request.xml').getText('UTF-8')
        log.info("Unmarshalling pdqv3 query .. ${pdqV3QueryString}")

        def queryResponse = runPdq(pdqV3QueryString, "direct:modifyPdq")
        log.info("Service response : ${queryResponse}" )
    
    }
    
    String runPdq(String pdqV3QueryString, String endpoint){
        def pdqV3Query = new XmlSlurper().parseText(pdqV3QueryString)
        log.info("Sender device is ${pdqV3Query.sender.device.id.@root}")
        log.info("Receiver device is ${pdqV3Query.receiver.device.id.@root}")
        def queryResponse = send(endpoint, pdqV3QueryString, String.class)
        log.info("Received response from pdqv3 query .. ${pdqV3Query}")
        queryResponse as String
    }
}
