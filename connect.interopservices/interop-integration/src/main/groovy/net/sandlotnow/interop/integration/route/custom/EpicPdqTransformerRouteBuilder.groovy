/**
 *======================================================================================
 * EpicPdqTransformerRouteBuilder.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- EpicPdqTransformerRouteBuilder
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 20, 2015
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

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.spring.SpringRouteBuilder
import groovy.util.XmlParser
import groovy.xml.XmlUtil
import groovy.xml.StreamingMarkupBuilder

//Imports

class EpicPdqTransformerRouteBuilder extends SpringRouteBuilder {
    
    String endpointName = "epicPdqTransformer"
    
    void configure() {
        
        from("direct:" + endpointName)  
            .transmogrify { body, headers ->
                 def pdqOrig = body
                 log.info("PDQ BODY ${pdqOrig}")
                 def pdqQuery = new XmlParser().parseText(pdqOrig)
                 if(pdqQuery.receiver.device.id.@extension.value){
                     String rootOid = pdqQuery.receiver.device.id.@root 
                     String extensionOid = pdqQuery.receiver.device.id.@extension
                     rootOid = rootOid.replace("[", "").replace("]", "") + "." + extensionOid.replace("[", "").replace("]", "")
                     pdqQuery.receiver.device.id.replaceNode{
                         id(root: rootOid)
                     }
                 }
                 if(pdqQuery.sender.device.id.@extension.value){
                     String rootOid = pdqQuery.sender.device.id.@root
                     String extensionOid = pdqQuery.sender.device.id.@extension
                     rootOid = rootOid.replace("[", "").replace("]", "") + "." + extensionOid.replace("[", "").replace("]", "")
                     pdqQuery.sender.device.id.replaceNode{
                         id(root: rootOid)
                     }
                 }
                 
                 // Remove telecom from query parameter list
                 if(pdqQuery.controlActProcess.queryByParameter.parameterList.patientTelecom != null){
                     pdqQuery.controlActProcess.queryByParameter.parameterList.patientTelecom.each { telecomNode ->
                         log.info("DELETING: ${telecomNode.value}")
                         telecomNode.replaceNode{}
                     }
                 }
                 
                 // Remove ALL livingSubjectId from parameterList
                 pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectId.each { subjectId ->
                     log.info("DELETING: ${subjectId.value.'@extension'} Root ${subjectId.value.'@root'}")
                     subjectId.replaceNode{}
                 }
                 
                 def pdqResult = XmlUtil.serialize(pdqQuery)
                 log.info("MODIFIED PDQ MESSAGE ***** "  + pdqResult)
                 return pdqResult    
            }
        
    }

}
