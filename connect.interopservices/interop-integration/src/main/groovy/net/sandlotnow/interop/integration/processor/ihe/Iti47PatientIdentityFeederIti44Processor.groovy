/**
 *======================================================================================
 * Iti47PatientIdentityFeederIti44Processor.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- Iti47PatientIdentityFeederIti44Processor
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 26, 2015
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

import groovy.xml.XmlUtil
import groovy.util.XmlSlurper
import net.sandlotnow.interop.ihe.data.InteropIheDataService;
import net.sandlotnow.interop.ihe.model.EdgeSystem
import net.sandlotnow.interop.integration.route.ihe.InteropXdsException
import net.sandlotnow.interop.integration.route.ihe.utils.HL7Constants;
import net.sandlotnow.interop.integration.route.ihe.utils.HL7FormattedDataFactory
import net.sandlotnow.interop.integration.processor.ihe.utils.IheUtils
import net.sandlotnow.interop.integration.audit.InteropAuditingEventNotifier

import org.apache.camel.Exchange
import org.apache.camel.Processor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

class Iti47PatientIdentityFeederIti44Processor implements Processor{
    
    Logger log = LoggerFactory.getLogger(Iti47PatientIdentityFeederIti44Processor.class);
        
    InteropProcessorIti44 iti44Processor;
        
    InteropProcessorIti45 iti45Processor;
    
    String localPatientIdentityAssigningAuthority;
    
    boolean inline = false
    
    boolean ignoreErrors = true
    
    IheUtils iheUtils = new IheUtils()
    
    @Autowired
    public InteropIheDataService iheDataService;
    
    @Override
    public void process(Exchange exchange) throws Exception {
                
        if(iti44Processor == null){
            log.warn("ITI-44 Processor not set on this Iti47PatientIdentityFeederIti44Processor. Patient Identity Feed will be skipped.")
            return
        }
        
        def pdqQueryString = exchange.in.getBody(String.class)
        def pdqQuery = new XmlSlurper().parseText(pdqQueryString)
        
        def localAA = localPatientIdentityAssigningAuthority
        
        EdgeSystem edgeSystem
        String senderDeviceId = localPatientIdentityAssigningAuthority
        
        if(!localPatientIdentityAssigningAuthority){            
            senderDeviceId = pdqQuery.sender.device.id.@root
            
            edgeSystem = iheDataService.findEdgeSystemByDeviceOrAssigningAuthorityOID(senderDeviceId);
        }
        else{
            edgeSystem = iheDataService.findEdgeSystemByAssigningAuthorityOID(localPatientIdentityAssigningAuthority);
        }
        
        if(edgeSystem == null){
            log.warn("Could not find an Edge System configured for the OID ${localAA} to feed patient ids from. Patient Identity Feed will be skipped.")
            return
        }
        localAA = edgeSystem.getAssigningAuthorityOID()
        String providerOrganizationId = edgeSystem.organization.organizationOID
        String providerOrganizationName = edgeSystem.getName()
        log.info("Found matching edge system to use as patient identity source ${localAA} org:${providerOrganizationId} name:${providerOrganizationName}")
        
        if(!localAA){
            log.warn("Could not identify a local patient identity assigning authority to feed patient ids from. Patient Identity Feed will be skipped.")
            return
        }
        
        def localPatientIdNode = pdqQuery.controlActProcess.queryByParameter.parameterList.depthFirst().find { node ->
               node.name() == 'livingSubjectId' && node.value.@root == localAA
            }
        
        if(localPatientIdNode){
            
            String localPatientId = localPatientIdNode.value.@extension
            String patientFirstName = pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.given[0].text()
            String patientMiddleName
            if(pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.given[1]){
              patientMiddleName = pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.given[1].text()
            }
            String patientLastName = pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.family.text()
            String patientGender = pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectAdministrativeGender.value.@code
            String patientBirthTime = pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectBirthTime.value.@value
            String patientAddrStreet = pdqQuery.controlActProcess.queryByParameter.parameterList.patientAddress.value.streetAddressLine.text()
            String patientAddrCity = pdqQuery.controlActProcess.queryByParameter.parameterList.patientAddress.value.city.text()
            String patientAddrState = pdqQuery.controlActProcess.queryByParameter.parameterList.patientAddress.value.state.text()
            String patientAddrPostalCode = pdqQuery.controlActProcess.queryByParameter.parameterList.patientAddress.value.postalCode.text()
            
            def ssnNode = pdqQuery.controlActProcess.queryByParameter.parameterList.depthFirst().find { node ->
                node.name() == 'livingSubjectId' && node.value.@root == HL7Constants.SSN_ID_ROOT
             }
            String patientSsn
            if(ssnNode){
                patientSsn = ssnNode.value.@extension
            }
            
            String mpiAssigningAuthorityOid = iti44Processor.getDestinationIdentifier()
            if(!mpiAssigningAuthorityOid){
                mpiAssigningAuthorityOid = senderDeviceId
            }
            log.info("Found local patient id ${localPatientId} to send in iti-44 patient id feed to mpi ${mpiAssigningAuthorityOid}")
            
            if(iti45Processor != null){
                def pixQueryString = iheUtils.createPixQueryMessage(iti45Processor.getDestinationIdentifier(), localAA, localPatientId)            
                
                exchange.in.setBody(pixQueryString)
                
                try{
                    iti45Processor.process(exchange)
                    
                    if(exchange.getException() != null){
                        log.warn("Error attempting to send PIX v3 Query to iti-45 processor", exchange.getException())
    
                    }
                    else{
                    
                        def pixV3QueryResponseString = exchange.out.getBody(String.class)
                        def globalPatientId = iheUtils.readPixQueryResponseGetGlobalId(pixV3QueryResponseString, mpiAssigningAuthorityOid)
                        
                        if(globalPatientId == null || globalPatientId.isEmpty()){
                            
                            // No match for local id means we can go ahead an feed it
                            def pixFeedString = iheUtils.createPixFeedMessage(
                                localAA,
                                localPatientId,
                                senderDeviceId,
                                mpiAssigningAuthorityOid,
                                patientFirstName,
                                patientMiddleName,
                                patientLastName,
                                patientGender,
                                patientBirthTime,
                                patientAddrStreet,
                                patientAddrCity,
                                patientAddrState,
                                patientAddrPostalCode,
                                patientSsn,
                                providerOrganizationId,
                                providerOrganizationName)
                            
                            exchange.in.setBody(pixFeedString)
                    
                            iti44Processor.process(exchange)
                            
                            if(exchange.getException() != null){
                                log.warn("Error attempting to send PIX v3 FEED to iti-44 processor", exchange.getException())
            
                            }
                            //TODO Handle failed acks
                            
                        }
                        else{
                            log.info("PIX Query returned a match. PIX Feed will be skipped.")
                        }
                    }
                }
                catch(Exception exc){                    
                    log.error("Exception caught during PIX Query / PIX Feed in support of PDQ", exc)
                    if(!ignoreErrors){
                        throw exc
                    }
                    else{      
                        // Let this succeed but mark the audit log as an error                  
                        exchange.setProperty(InteropAuditingEventNotifier.EXCHANGE_PROPERTY_FAILED_EVENT, exc.getMessage())
                        exchange.setProperty(InteropAuditingEventNotifier.EXCHANGE_PROPERTY_FAILED_EVENT_STACKTRACE, ExceptionUtils.getFullStackTrace(exc))
                    }
                }
            }
            else{                
                log.warn("ITI-45 Processor not set on this Iti47PatientIdentityFeederIti44Processor. Patient Identity Feed will be skipped.")
            }
            
        }
        else{
            log.info("PDQ Message did not have a matching local patient id in AA ${localAA}. iti-44 patient id feed will be skipped. ")
            
        }
        
        // The variable inline defines this processor to run inline and not as a wiretap which is the intended use
        if(inline){
            exchange.out.setBody(pdqQueryString)
        }
        else{
            exchange.out.setBody(null)
        }
    }}
