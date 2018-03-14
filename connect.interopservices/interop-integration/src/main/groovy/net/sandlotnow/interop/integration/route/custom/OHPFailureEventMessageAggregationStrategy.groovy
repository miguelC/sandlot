/**
 *======================================================================================
 * OHPFailureEventMessageTransmogrifier.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- OHPFailureEventMessageTransmogrifier
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Sep 29, 2015
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

//Imports
import groovy.xml.MarkupBuilder

import org.apache.camel.Exchange
import org.apache.camel.processor.aggregate.AggregationStrategy
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.XdsRenderingUtils
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sandlotnow.interop.integration.route.ihe.utils.XdsUtils

class OHPFailureEventMessageAggregationStrategy implements AggregationStrategy{
    
    Logger log = LoggerFactory.getLogger(OHPFailureEventMessageAggregationStrategy.class);
    
    @Override
    public Exchange aggregate(Exchange resource, Exchange exchange) {
        def resourceBody = resource.getIn().getBody().toString()
        return this.aggregateContent(resourceBody, exchange)
        
    }
    
    public Exchange aggregateContent(String content, Exchange exchange){
        def regResponseXml = ""
        Object exchangeBody = null;
        
        if(exchange != null && exchange.getOut() != null){
            exchangeBody = exchange.getOut().getBody()
        }
//        else if(exchange != null && exchange.getIn() != null){
//            exchangeBody = exchange.getIn().getBody()
//        }
        if(exchangeBody != null){
            if(exchangeBody instanceof RegistryResponseType){
                regResponseXml = XdsRenderingUtils.renderEbxml(exchangeBody)
            }
            else{
                regResponseXml = XdsRenderingUtils.renderEbxml(
                    XdsUtils.createRegistryErrorResponseRepositoryError(exchangeBody.toString()))
            }
        }
        else{
            regResponseXml = XdsRenderingUtils.renderEbxml(
                XdsUtils.createRegistryErrorResponseRepositoryError("Unknown Error message body is empty"))
        }
        regResponseXml = regResponseXml.replaceAll("\\<\\?xml(.+?)\\?\\>", "")
        content = content.replaceAll("\\<\\?xml(.+?)\\?\\>", "")
        
        log.info("Aggregating messages while building failure exchange message...")
        //log.info("Orig: ${content}")
        //log.info("Resp: ${regResponseXml}")
        
        def timeStamp = exchange.getProperty("CamelCreatedTimeStamp") == null ? (new Date()).toString()
                 : exchange.getProperty("CamelCreatedTimeStamp").toString()
        def exception = exchange.getException() != null ? exchange.getException().getMessage() : ""
        
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)

        xml.ExchangeFailureEvent() {
            Failure(){
                TimeStamp(timeStamp)
                ExchangeId(exchange.getExchangeId())
                FailureMessage(exception)
                Response() {
                    mkp.yieldUnescaped(regResponseXml)
                }
            }
            OriginalMessage(){
                    mkp.yieldUnescaped(content )
            }
        }
        def mergeResult = writer.toString()
        
        //log.info("Result: ${mergeResult}")
        
        if (exchange.getPattern().isOutCapable() && exchange.getOut() != null) {
            exchange.getOut().setBody(mergeResult);
        } else {
            exchange.getIn().setBody(mergeResult);
        }
        return exchange;
    }

}
