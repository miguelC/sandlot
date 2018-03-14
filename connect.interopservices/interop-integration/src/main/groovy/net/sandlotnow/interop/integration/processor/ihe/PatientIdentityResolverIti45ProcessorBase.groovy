/**
 *======================================================================================
 * PatientIdentityResolverIti45ProcessorBase.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- PatientIdentityResolverIti45ProcessorBase
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 16, 2015
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
import net.sandlotnow.interop.integration.route.ihe.InteropXdsException
import net.sandlotnow.interop.integration.route.ihe.utils.HL7FormattedDataFactory
import net.sandlotnow.interop.integration.route.ihe.utils.XdsUtils
import org.apache.camel.Exchange
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PatientIdentityResolverIti45ProcessorBase {
    Logger log = LoggerFactory.getLogger(Iti41PatientIdentityResolverIti45Processor.class);
    
    boolean failOnMatchNotFound = true
    
    InteropProcessorIti45 iti45Processor;
    
    def pixV3QueryString = '''<?xml version="1.0" encoding="UTF-8"?>
<PRPA_IN201309UV02 ITSVersion="XML_1.0" xmlns="urn:hl7-org:v3" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <id root="b99f43d0-de36-49ee-83cd-8b61154dcf4d" />
  <creationTime value="${currentDateTime}" />
  <interactionId root="2.16.840.1.113883.1.6" extension="PRPA_IN201309UV02" />
  <processingCode code="T" />
  <processingModeCode code="T" />
  <acceptAckCode code="AL" />
  <receiver typeCode="RCV">
    <device classCode="DEV" determinerCode="INSTANCE">
      <id root="${mpiAssigningAuthorityOid}" />
    </device>
  </receiver>
  <sender typeCode="SND">
    <device classCode="DEV" determinerCode="INSTANCE">
      <id root="${localPatientAssigningAuthority}" />
    </device>
  </sender>
  <controlActProcess classCode="CACT" moodCode="EVN">
    <code code="PRPA_TE201309UV02" codeSystem="2.16.840.1.113883.1.6" />
    <authorOrPerformer typeCode="AUT">
      <assignedPerson classCode="ASSIGNED">
        <id root="${localPatientAssigningAuthority}" extension="USR5568" />
      </assignedPerson>
    </authorOrPerformer>
    <queryByParameter>
      <queryId root="${localPatientAssigningAuthority}" extension="77688" />
      <statusCode code="new" />
      <responsePriorityCode code="I" />
      <parameterList>
        <dataSource>
            <value root="${mpiAssigningAuthorityOid}"/>
            <semanticsText>DataSource.id</semanticsText>
        </dataSource>
        <patientIdentifier>
          <value root="${localPatientAssigningAuthority}" extension="${localPatientId}" />
          <semanticsText>Patient.Id</semanticsText>
        </patientIdentifier>
      </parameterList>
    </queryByParameter>
  </controlActProcess>
</PRPA_IN201309UV02>
'''
    
    protected String doProcessReturnGlobalId(
        Exchange exchange, 
        mpiAssigningAuthorityOid,
        localPatientAssigningAuthority,
        localPatientId){
        
        if(iti45Processor == null){
            def regErr =
                XdsUtils.createRegistryErrorResponseRegistryError("No ITI-45 Processor has been configured for this patient identity resolution process");
            exchange.out.setBody(regErr)
            throw new InteropXdsException("No ITI-45 Processor has been configured for this patient identity resolution process")
            
        }
        def serializedPixQuery = buildPixQuery(mpiAssigningAuthorityOid, localPatientAssigningAuthority, localPatientId)
        exchange.in.setBody(serializedPixQuery)
        
        iti45Processor.process(exchange)
        
        if(exchange.getException() != null){
            log.warn("Error attempting to send PIX v3 Query to iti-45 processor");
            throw exchange.getException()
        }
        
        def pixV3QueryResponseString = exchange.out.getBody(String.class)
        def globalPatientId = readPixQueryResponseGetGlobalId(pixV3QueryResponseString)
        
        if(failOnMatchNotFound && ( globalPatientId == null || globalPatientId.isEmpty())){
            def regErr =
                XdsUtils.createRegistryErrorResponseRegistryError("No match found for ${localPatientId} on MPI");
            exchange.out.setBody(regErr)
            throw new InteropXdsException("No match found for ${localPatientId} on MPI")
        }
        return globalPatientId
    }    

    protected String buildPixQuery(
        mpiAssigningAuthorityOid,
        localPatientAssigningAuthority,
        localPatientId){
        
        def currentDateTime = HL7FormattedDataFactory.currentDateTime()
        def pixV3Query = new XmlSlurper().parseText(pixV3QueryString)
        
        pixV3Query.creationTime.@value = currentDateTime
        pixV3Query.receiver.device.id.@root = mpiAssigningAuthorityOid
        pixV3Query.sender.device.id.@root = localPatientAssigningAuthority
        pixV3Query.controlActProcess.authorOrPerformer.assignedPerson.id.@root = localPatientAssigningAuthority
        pixV3Query.controlActProcess.queryByParameter.queryId.@root = localPatientAssigningAuthority
        
        //We are only interested in the id of this assigning authority so we populate datasource.id
        pixV3Query.controlActProcess.queryByParameter.parameterList.dataSource.value.@root = mpiAssigningAuthorityOid
        pixV3Query.controlActProcess.queryByParameter.parameterList.patientIdentifier.value.@root = localPatientAssigningAuthority
        pixV3Query.controlActProcess.queryByParameter.parameterList.patientIdentifier.value.@extension = localPatientId
        
        def serializedPixQuery = new XmlUtil().serialize(pixV3Query)
        return serializedPixQuery
    }
        
    protected String readPixQueryResponseGetGlobalId(String pixV3QueryResponseString){
        
        def pixV3QueryResponse = new XmlSlurper().parseText(pixV3QueryResponseString)
        
        def globalPatientId = pixV3QueryResponse.controlActProcess.subject.registrationEvent.subject1.patient.id[0].@extension.toString()
        log.info("Resolved global patient id to ${globalPatientId}")
        
        return globalPatientId
    }
}
