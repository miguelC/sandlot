/**
 *======================================================================================
 * InteropProcessorIti44.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropProcessorIti44
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

//Imports
import groovy.util.slurpersupport.GPathResult;
import net.sandlotnow.interop.audit.AuditSubject
import net.sandlotnow.interop.audit.SubjectInfo
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.integration.route.ihe.InteropCamelProcessorBase;
import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageFactory
import net.sandlotnow.interop.integration.route.InteropRouteConstants

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.openehealth.ipf.commons.ihe.hl7v3.Hl7v3Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class InteropProcessorIti44 extends InteropCamelProcessorBase implements Processor{
    
    Logger log = LoggerFactory.getLogger(InteropProcessorIti44.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        String pixFeedString = exchange.getIn().getBody(String.class)
        log.info("iti-44 message: ${pixFeedString}")        
        
        String edgeSystemOIDDestination = receiverOrganizationOrDevice(pixFeedString)
        String edgeSystemOIDSource = senderOrganizationOrDevice(pixFeedString)
        setPatientInfoExchangeHeader(exchange, pixFeedString)
        exchange = this.doProcessTransactionEdgeSystem(exchange, edgeSystemOIDSource, edgeSystemOIDDestination, EdgeTransaction.TRANSACTION_ITI44)
        
        pixFeedString = exchange.getOut().getBody(String.class)
        log.info("iti-44 response: ${pixFeedString}")
        
     }
    
    /***
     * Gets the assigning authority value out of the patient identifier in a registry stored query message
     * @param exchange
     * @return
     */
    public String receiverOrganizationOrDevice(String pixV3FeedString) { 
        
        def pixV3Feed = new XmlSlurper().parseText(pixV3FeedString)
        String receiverDeviceOid = pixV3Feed.receiver.device.id.@root
        
        return receiverDeviceOid;
    }
    
    public String senderOrganizationOrDevice(String pixV3FeedString) { 
        
        def pixV3Feed = new XmlSlurper().parseText(pixV3FeedString)
        String senderDeviceOid = pixV3Feed.sender.device.id.@root
        
        return senderDeviceOid;
    }
    
    protected void setPatientInfoExchangeHeader(Exchange exchange, String pixV3FeedString){
        def pixV3Feed = new XmlSlurper().parseText(pixV3FeedString)
        SubjectInfo subjectInfo = new SubjectInfo()
        AuditSubject subject = new AuditSubject()
        
        String subjectIdExt = pixV3Feed.controlActProcess.subject.registrationEvent.subject1.patient.id.@extension
        String subjectIdRoot = pixV3Feed.controlActProcess.subject.registrationEvent.subject1.patient.id.@root
        subject.addId(XdsMessageFactory.createIdentifiable(subjectIdExt, subjectIdRoot).toString())
        
        String subjectName = XdsMessageFactory.createName(
            pixV3Feed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.name.family.text(),
            pixV3Feed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.name.given[0].text(),
            (pixV3Feed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.name.given[1] ? pixV3Feed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.name.given[1].text() : null),
            null, null, null
            ).toString()
        subject.setName(subjectName)
        
        subjectInfo.addSubject(subject)
        
        exchange.setProperty(InteropRouteConstants.HEADER_CAMEL_EXCHANGE_SUBJECT_INFO, subjectInfo)
    }

}
