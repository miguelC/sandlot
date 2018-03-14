/**
 *======================================================================================
 * HL7BridgeRouteBuilder.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- HL7BridgeRouteBuilder
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Aug 18, 2015
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
package net.sandlotnow.interop.integration.route;

//Imports

import org.apache.camel.spring.SpringRouteBuilder;
import org.openehealth.ipf.commons.ihe.hl7v3.translation.PixFeedRequest2to3Translator;
import org.openehealth.ipf.commons.ihe.hl7v3.translation.PixAck3to2Translator;
import static org.openehealth.ipf.platform.camel.ihe.hl7v3.PixPdqV3CamelTranslators.*;
 
public class HL7BridgeRouteBuilder extends SpringRouteBuilder {
    // injected by Spring
    private PixFeedRequest2to3Translator pixFeedRequestTranslator;
    private PixAck3to2Translator pixFeedAckTranslator;

    public void configure() throws Exception {
        from("direct:pixFeedv2tov3")
            .onException(Exception.class)
            .maximumRedeliveries(0)
            // some reasonable exception handling
            .end()
        .process(translatorHL7v2toHL7v3(getPixFeedRequestTranslator()))
        .to("iti44Processor")
        .process(translatorHL7v3toHL7v2(getPixFeedAckTranslator()));
    }

    public PixFeedRequest2to3Translator getPixFeedRequestTranslator() {
        return pixFeedRequestTranslator;
    }

    public void setPixFeedRequestTranslator(PixFeedRequest2to3Translator pixFeedRequestTranslator) {
        this.pixFeedRequestTranslator = pixFeedRequestTranslator;
    }

    public PixAck3to2Translator getPixFeedAckTranslator() {
        return pixFeedAckTranslator;
    }

    public void setPixFeedAckTranslator(PixAck3to2Translator pixFeedAckTranslator) {
        this.pixFeedAckTranslator = pixFeedAckTranslator;
    }
}
