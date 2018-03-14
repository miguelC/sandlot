/**
 *======================================================================================
 * InteropProcessorIti47.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropProcessorIti47
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 5, 2015
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
package net.sandlotnow.interop.integration.processor.ihe

//Imports
import groovy.util.slurpersupport.GPathResult;
import net.sandlotnow.interop.audit.AuditSubject;
import net.sandlotnow.interop.audit.SubjectInfo;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.integration.route.InteropRouteConstants;
import net.sandlotnow.interop.integration.route.ihe.InteropCamelProcessorBase;
import net.sandlotnow.interop.audit.AuditSubject;
import net.sandlotnow.interop.audit.SubjectInfo
import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageFactory

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.openehealth.ipf.commons.ihe.hl7v3.Hl7v3Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class InteropProcessorIti47 extends InteropCamelProcessorBase implements Processor{
    
    Logger log = LoggerFactory.getLogger(InteropProcessorIti47.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        String pdqQueryString = exchange.getIn().getBody(String.class)
        log.info("iti-47 message: ${pdqQueryString}")        
        
        String edgeSystemOIDDestination = receiverOrganizationOrDevice(pdqQueryString)
        String edgeSystemOIDSource = senderOrganizationOrDevice(pdqQueryString)
        setPatientInfoExchangeHeader(exchange, pdqQueryString)
        exchange = this.doProcessTransactionEdgeSystem(exchange, edgeSystemOIDSource, edgeSystemOIDDestination, EdgeTransaction.TRANSACTION_ITI47)
        
        pdqQueryString = exchange.getOut().getBody(String.class)
        log.info("iti-47 response: ${pdqQueryString}")
        // TODO Deal with errors.
        
     }
    
    /***
     * Gets the assigning authority value out of the patient identifier in a registry stored query message
     * @param exchange
     * @return
     */
    public String receiverOrganizationOrDevice(String pdqQueryString) { 
        
        def pdqQuery = new XmlSlurper().parseText(pdqQueryString)
        String receiverDeviceOid = pdqQuery.receiver.device.id.@root
        
        return receiverDeviceOid;
    }
    
    public String senderOrganizationOrDevice(String pdqQueryString) { 
        
        def pdqQuery = new XmlSlurper().parseText(pdqQueryString)
        String senderDeviceOid = pdqQuery.sender.device.id.@root
        
        return senderDeviceOid;
    }
    
    protected void setPatientInfoExchangeHeader(Exchange exchange, String pdqQueryString){        
        def pdqQuery = new XmlSlurper().parseText(pdqQueryString)
        SubjectInfo subjectInfo = new SubjectInfo()
        AuditSubject subject = new AuditSubject()
        
        String subjectName = XdsMessageFactory.createName(
        pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.family.text(),
        pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.given[0].text(),
        (pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.given[1] ? pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.given[1].text() : null),
        null, null, null
        ).toString()        
        subject.setName(subjectName)     
           
        //TODO Review this because it doesn't seem to work but it should
//        if(pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectId){
//            pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectId.each { subjectId ->
//                String subjectIdExt = pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectId.value.@extension
//                String subjectIdRoot = pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectId.value.@root
//                subject.addId(XdsMessageFactory.createIdentifiable(subjectIdExt, subjectIdRoot).toString())
//            }
//        }
        
        subjectInfo.addSubject(subject)
        
        exchange.setProperty(InteropRouteConstants.HEADER_CAMEL_EXCHANGE_SUBJECT_INFO, subjectInfo)
    }

}