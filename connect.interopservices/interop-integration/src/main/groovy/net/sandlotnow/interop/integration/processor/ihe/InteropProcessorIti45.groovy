/**
 *======================================================================================
 * InteropProcessorIti45.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropProcessorIti45
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Sep 18, 2015
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

import groovy.util.slurpersupport.GPathResult;
import net.sandlotnow.interop.audit.AuditSubject
import net.sandlotnow.interop.audit.SubjectInfo
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.integration.route.ihe.InteropCamelProcessorBase;
import net.sandlotnow.interop.integration.route.InteropRouteConstants;
import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageFactory

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.openehealth.ipf.commons.ihe.hl7v3.Hl7v3Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Imports

class InteropProcessorIti45 extends InteropCamelProcessorBase implements Processor{
    
    Logger log = LoggerFactory.getLogger(InteropProcessorIti45.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        String pixQueryString = exchange.getIn().getBody(String.class)
        log.info("iti-45 message: ${pixQueryString}")        
        
        String edgeSystemOIDDestination = receiverOrganizationOrDevice(pixQueryString)
        String edgeSystemOIDSource = senderOrganizationOrDevice(pixQueryString)
        setPatientInfoExchangeHeader(exchange, pixQueryString)
        exchange = this.doProcessTransactionEdgeSystem(exchange, edgeSystemOIDSource, edgeSystemOIDDestination, EdgeTransaction.TRANSACTION_ITI45)
        
        pixQueryString = exchange.getOut().getBody(String.class)
        log.info("iti-45 response: ${pixQueryString}")
        // TODO Deal with errors.
        
     }
    
    /***
     * Gets the assigning authority value out of the patient identifier in a registry stored query message
     * @param exchange
     * @return
     */
    public String receiverOrganizationOrDevice(String pixV3QueryString) { 
        
        def pixV3Query = new XmlSlurper().parseText(pixV3QueryString)
        String receiverDeviceOid = pixV3Query.receiver.device.id.@root
        
        return receiverDeviceOid;
    }
    
    public String senderOrganizationOrDevice(String pixV3QueryString) { 
        
        def pixV3Query = new XmlSlurper().parseText(pixV3QueryString)
        String senderDeviceOid = pixV3Query.sender.device.id.@root
        
        return senderDeviceOid;
    }
    
    protected void setPatientInfoExchangeHeader(Exchange exchange, String pixV3QueryString){
        def pixV3Query = new XmlSlurper().parseText(pixV3QueryString)
        SubjectInfo subjectInfo = new SubjectInfo()
        AuditSubject subject = new AuditSubject()
        String subjectIdExt = pixV3Query.controlActProcess.queryByParameter.parameterList.patientIdentifier.value.@extension
        String subjectIdRoot = pixV3Query.controlActProcess.queryByParameter.parameterList.patientIdentifier.value.@root
        subject.addId(XdsMessageFactory.createIdentifiable(subjectIdExt, subjectIdRoot).toString())
        
        subjectInfo.addSubject(subject)
        
        exchange.setProperty(InteropRouteConstants.HEADER_CAMEL_EXCHANGE_SUBJECT_INFO, subjectInfo)
    }
}
