/**
 *======================================================================================
 * StringClonePreparerProcessor.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- StringClonePreparerProcessor
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 27, 2015
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
package net.sandlotnow.interop.integration.processor

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.util.ExchangeHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class StringClonePreparerProcessor implements Processor {
    
    Logger log = LoggerFactory.getLogger(StringClonePreparerProcessor.class)
    
       public void process(Exchange exchange) throws Exception {
           String body = exchange.getIn().getBody(String.class)
           log.info("EXCHANGE BODY StringClonePreparerProcessor : ${body} ")
           String clone = body.toString()
           exchange = ExchangeHelper.createCopy(exchange, true)
           exchange.getIn().setBody(clone)
       }
   }