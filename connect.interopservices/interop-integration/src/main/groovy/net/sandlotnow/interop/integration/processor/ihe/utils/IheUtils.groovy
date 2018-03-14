/**
 *======================================================================================
 * IheUtils.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- IheUtils
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
package net.sandlotnow.interop.integration.processor.ihe.utils

//Imports
import groovy.util.XmlParser
import groovy.util.XmlSlurper
import groovy.xml.XmlUtil
import net.sandlotnow.interop.integration.route.ihe.utils.HL7FormattedDataFactory

class IheUtils {
    
    def pdqErrorString = '''<?xml version="1.0" encoding="UTF-8"?>
<PRPA_IN201306UV02 ITSVersion="XML_1.0" xmlns="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
         <id root="2.16.840.1.113883.3.37.4.1.1.2.1.4" extension="15f2ab66-1d8f-4c2a-aedd-ce1c2dbe357e"/>
         <creationTime value="${currentDateTime}"/>
         <interactionId root="2.16.840.1.113883.1.6" extension="PRPA_IN201306UV02"/>
         <processingCode code="P"/>
         <processingModeCode code="T"/>
         <acceptAckCode code="NE"/>
         <receiver typeCode="RCV">
            <device classCode="DEV" determinerCode="INSTANCE">
               <id root="${pdqQuery.sender.device.id.@root}"/>
            </device>
         </receiver>
         <sender typeCode="SND">
            <device classCode="DEV" determinerCode="INSTANCE">
               <id root="${pdqQuery.receiver.device.id.@root}"/>
            </device>
         </sender>
         <acknowledgement>
            <typeCode code="AE"/>
            <targetMessage>
               <id root="${pdqQuery.id.@root}" />
            </targetMessage>
            <acknowledgementDetail typeCode="E">
               <code code="${errorCode}" codeSystem="2.16.840.1.113883.5.1100"/>
               <text>${errorText}</text>
            </acknowledgementDetail>
         </acknowledgement>
         <controlActProcess classCode="CACT" moodCode="EVN">
            <code code="PRPA_TE201306UV02" codeSystem="2.16.840.1.113883.1.6"/>
            <reasonOf typeCode="RSON">
               <detectedIssueEvent classCode="ALRT" moodCode="EVN">
                  <code code="ActAdministrativeDetectedIssueCode" codeSystem="2.16.840.1.113883.5.4"/>
                  <mitigatedBy typeCode="MITGT">
                     <detectedIssueManagement classCode="ACT" moodCode="EVN">
                        <code code="VALIDAT" codeSystem="2.16.840.1.113883.5.4"/>
                     </detectedIssueManagement>
                  </mitigatedBy>
               </detectedIssueEvent>
            </reasonOf>
            <queryAck>
               <queryId root="${pdqQuery.controlActProcess.queryByParameter.queryId.@root}"/>
               <statusCode code="aborted"/>
               <queryResponseCode code="QE"/>
               <resultTotalQuantity value="0"/>
               <resultCurrentQuantity value="0"/>
               <resultRemainingQuantity value="0"/>
            </queryAck>
            <queryByParameter>
               <queryId root="5998dd44-769d-11e5-9a90-b203cb04cb35"/>
               <statusCode code="new"/>
               <responseModalityCode code="R"/>
               <responsePriorityCode code="I"/>
               <parameterList>
               </parameterList>
            </queryByParameter>
         </controlActProcess>
      </PRPA_IN201306UV02>
'''
    
    String pixv3ErrorString = '''<?xml version="1.0" encoding="UTF-8"?>
<PRPA_IN201310UV02 ITSVersion="XML_1.0" xmlns="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
         <id root="2.16.840.1.113883.3.37.4.1.1.2.1.4" extension="${java.util.UUID.randomUUID()}"/>
         <creationTime value="20151026122822"/>
         <interactionId root="2.16.840.1.113883.1.6" extension="PRPA_IN201310UV02"/>
         <processingCode code="P"/>
         <processingModeCode code="T"/>
         <acceptAckCode code="NE"/>
         <receiver typeCode="RCV">
            <device classCode="DEV" determinerCode="INSTANCE">
               <id root="2.16.840.1.113883.3.37.4.1.1.2.1.1"/>
            </device>
         </receiver>
         <sender typeCode="SND">
            <device classCode="DEV" determinerCode="INSTANCE">
               <id root="2.16.840.1.113883.3.37.4.1.1.2.1.1"/>
            </device>
         </sender>
         <acknowledgement>
            <typeCode code="AE"/>
            <targetMessage>
               <id root="b99f43d0-de36-49ee-83cd-8b61154dcf4d"/>
            </targetMessage>
            <acknowledgementDetail typeCode="E">
               <code code="204" codeSystem="2.16.840.1.113883.12.357"/>
               <text>PIXv2 Interface Reported [[Unknown patient ID]]</text>
               <location>/PRPA_IN201309UV02/controlActProcess/queryByParameter/parameterList/patientIdentifier/value</location>
            </acknowledgementDetail>
         </acknowledgement>
         <controlActProcess classCode="CACT" moodCode="EVN">
            <code code="PRPA_TE201310UV02" codeSystem="2.16.840.1.113883.1.6"/>
            <effectiveTime value="20151026122822"/>
            <queryAck>
               <queryId root="1.3.6.1.4.1.38630.2.1.1.998.37.1.1.2"/>
               <queryResponseCode code="AE"/>
            </queryAck>
            <queryByParameter>
               <queryId extension="77688" root="1.3.6.1.4.1.38630.2.1.1.998.37.1.1.2"/>
               <statusCode code="new"/>
               <responsePriorityCode code="I"/>
            </queryByParameter>
         </controlActProcess>
      </PRPA_IN201310UV02>'''
    
    def pixV3FeedString = '''<?xml version="1.0" encoding="UTF-8"?>
<PRPA_IN201301UV02 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="urn:hl7-org:v3 ../../schema/HL7V3/NE2008/multicacheschemas/PRPA_IN201301UV02.xsd"
 xmlns="urn:hl7-org:v3" ITSVersion="XML_1.0">
    <id root="${java.util.UUID.randomUUID()}"/>
    <creationTime value="${currentDateTime}"/>
    <interactionId root="2.16.840.1.113883.1.6" extension="PRPA_IN201301UV02"/>
    <processingCode code="P"/>
    <processingModeCode code="T"/>
    <acceptAckCode code="AL"/>
    <receiver typeCode="RCV">
        <device classCode="DEV" determinerCode="INSTANCE">
            <id root="${pdqQuery.receiver.device.id.@root}" />
        </device>
    </receiver>
    <sender typeCode="SND">
        <device classCode="DEV" determinerCode="INSTANCE">
            <id root="${pdqQuery.sender.device.id.@root}" />
        </device>
    </sender>
    <controlActProcess classCode="CACT" moodCode="EVN">
        <code code="PRPA_TE201301UV02" codeSystem="2.16.840.1.113883.1.6" />
        <subject typeCode="SUBJ">
            <registrationEvent classCode="REG" moodCode="EVN">
                <id nullFlavor="NA"/>
                <statusCode code="active"/>
                <subject1 typeCode="SBJ">
                    <patient classCode="PAT">
                        <id root="${localAssigningAuthority}" extension="${localPatientId}"/>
                        <statusCode code="active"/>
                        <patientPerson classCode="PSN" determinerCode="INSTANCE">
                            <name>
                                <given>${pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.given[0].text()}</given>
                                <given>${pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.given[1].text()}</given>
                                <family>${pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectName.value.family.text()}</family>
                            </name>
                            <administrativeGenderCode code="${pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectAdministrativeGender.value.@code}"/>
                            <birthTime value="${pdqQuery.controlActProcess.queryByParameter.parameterList.livingSubjectBirthTime.value.@value}"/>
                            <addr>
                                <streetAddressLine>${pdqQuery.controlActProcess.queryByParameter.parameterList.patientAddress.value.streetAddressLine.text()}</streetAddressLine>
                                <city>${pdqQuery.controlActProcess.queryByParameter.parameterList.patientAddress.value.city.text()}</city>
                                <state>${pdqQuery.controlActProcess.queryByParameter.parameterList.patientAddress.value.state.text()}</state>
                                <postalCode>${pdqQuery.controlActProcess.queryByParameter.parameterList.patientAddress.value.postalCode.text()}</postalCode>
                            </addr>
                            <asOtherIDs classCode="PAT">
                                <id root="2.16.840.1.113883.4.1" extension="999-99-4452"/>
                                <scopingOrganization classCode="ORG" determinerCode="INSTANCE">
                                    <id root="2.16.840.1.113883.4.1"/>
                                </scopingOrganization>
                            </asOtherIDs>
                        </patientPerson>
                        <providerOrganization classCode="ORG" determinerCode="INSTANCE">
                            <id root="${providerOrganizationOid}"/>
                            <name>${providerOrganizationName}</name>
                            <contactParty classCode="CON">
                                <telecom value="tel:+1-555-555-5555"/>
                            </contactParty>
                        </providerOrganization>
                    </patient>
                </subject1>
                <custodian typeCode="CST">
                    <assignedEntity classCode="ASSIGNED">
                        <id  root="${providerOrganizationOid}"/>
                        <assignedOrganization classCode="ORG" determinerCode="INSTANCE">
                            <name>${providerOrganizationName}</name>
                        </assignedOrganization>   
                    </assignedEntity>
                </custodian>
            </registrationEvent>
        </subject>
    </controlActProcess>
</PRPA_IN201301UV02>
'''
      
    def pixV3QueryString = '''<?xml version="1.0" encoding="UTF-8"?>
<PRPA_IN201309UV02 ITSVersion="XML_1.0" xmlns="urn:hl7-org:v3" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <id root="2.16.840.1.113883.3.37.4.1.1.2.1.4" />
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

     /**
     * Create a pix v3 response error message from a pix v3 query string
     * @param pdqQueryString
     * @param errorCode For codes go to http://phinvads.cdc.gov/vads/ViewCodeSystem.action?id=2.16.840.1.113883.12.357
     * @param errorText
     * @param errorLocation
     * @return
     */
    String createPixV3ErrorResponse(String pixQueryString, String errorCode, String errorText, String errorLocation){
        
        def currentDateTime = HL7FormattedDataFactory.currentDateTime()
        def pixErrorResponse = new XmlParser().parseText(pixErrorString)
        def pixQuery = new XmlSlurper().parseText(pixQueryString)
        
        pixErrorResponse.id.@extension = java.util.UUID.randomUUID()
        pixErrorResponse.creationTime.@value = currentDateTime
        pixErrorResponse.receiver.device.id.@root = pixQuery.sender.device.id.@root
        pixErrorResponse.sender.device.id.@root = pixQuery.receiver.device.id.@root
        pixErrorResponse.acknowledgement.targetMessage.id.@root = pixQuery.id.@root
        if(pixQuery.id.@extension.value){
            pixErrorResponse.acknowledgement.targetMessage.id.@extension = pixQuery.id.@extension
        }
        pixErrorResponse.acknowledgement.acknowledgementDetail.code.@code = errorCode
        pixErrorResponse.acknowledgement.acknowledgementDetail.text.replaceNode{ text(errorText)}
        pixErrorResponse.acknowledgement.acknowledgementDetail.location.replaceNode{ location(errorLocation)}
        pixErrorResponse.controlActProcess.effectiveTime.@value = currentDateTime
        pixErrorResponse.controlActProcess.queryAck.queryId.@root = pixQuery.controlActProcess.queryByParameter.queryId.@root
        if(pixQuery.controlActProcess.queryByParameter.queryId.@extension.value){
            pixErrorResponse.controlActProcess.queryAck.queryId.@extension = pixQuery.controlActProcess.queryByParameter.queryId.@extension
        }
        pixErrorResponse.controlActProcess.queryByParameter.replaceNode{
            mkp.yield(pixQuery.controlActProcess.queryByParameter)
        }
        
        def serializedErrorResponse = new XmlUtil().serialize(pixErrorResponse)
        return serializedErrorResponse
    }
    /**
     * 
     * @param pdqQueryString
     * @param errorCode See http://vico.org/HL7_RIM/infrastructure/vocabulary/AcknowledgementDetailCode.html
     * @param errorText
     * @return
     */
    String createPdqErrorResponse(String pdqQueryString, String errorCode, String errorText){
        
        def currentDateTime = HL7FormattedDataFactory.currentDateTime()
        def pdqErrorResponse = new XmlParser().parseText(pdqErrorString)
        def pdqQuery = new XmlSlurper().parseText(pdqQueryString)
        
        pdqErrorResponse.id.@extension = java.util.UUID.randomUUID()
        pdqErrorResponse.creationTime.@value = currentDateTime
        pdqErrorResponse.receiver.device.id.@root = pdqQuery.sender.device.id.@root
        pdqErrorResponse.sender.device.id.@root = pdqQuery.receiver.device.id.@root
        pdqErrorResponse.acknowledgement.targetMessage.id.@root = pdqQuery.id.@root
        if(pdqQuery.id.@extension.value){
            pdqErrorResponse.acknowledgement.targetMessage.id.@extension = pdqQuery.id.@extension
        }
        pdqErrorResponse.acknowledgement.acknowledgementDetail.code.@code = errorCode
        pdqErrorResponse.acknowledgement.acknowledgementDetail.text.replaceNode{ text(errorText)}
        pdqErrorResponse.controlActProcess.effectiveTime.@value = currentDateTime
        pdqErrorResponse.controlActProcess.queryAck.queryId.@root = pdqQuery.controlActProcess.queryByParameter.queryId.@root
        if(pdqQuery.controlActProcess.queryByParameter.queryId.@extension.value){
            pdqErrorResponse.controlActProcess.queryAck.queryId.@extension = pdqQuery.controlActProcess.queryByParameter.queryId.@extension
        }
        pdqErrorResponse.controlActProcess.queryByParameter.replaceNode{
            mkp.yield(pdqQuery.controlActProcess.queryByParameter)
        }
        
        def serializedPdqErrorResponse = new XmlUtil().serialize(pdqErrorResponse)
        return serializedPdqErrorResponse
    }

    String createPixFeedMessage(
            String localAA, 
            String localPatientId,
            String senderDevice,
            String receiverDevice,
            String patientFirstName,
            String patientMiddleName,
            String patientLastName,
            String gender,
            String birthDate,
            String addrStreet,
            String addrCity,
            String addrState,
            String addrPostalCode,
            String ssn,
            String providerOrganizationId,
            String providerOrganizationName){      
          
        def currentDateTime = HL7FormattedDataFactory.currentDateTime()
        def pixFeed = new XmlParser().parseText(pixV3FeedString)
        
        pixFeed.id.@root = "${localAA}.4" 
        pixFeed.id.@extension = java.util.UUID.randomUUID()
        pixFeed.creationTime.@value = currentDateTime
        pixFeed.sender.device.id.@root = senderDevice
        pixFeed.receiver.device.id.@root = receiverDevice
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.id.@root = localAA
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.id.@extension = localPatientId
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.name.given[0].replaceNode { given(patientFirstName) }
        if(patientMiddleName){
            pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.name.given[1].replaceNode { given(patientMiddleName) }
        }else{
            pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.name.given[1].replaceNode { }
        }
        if(ssn){
            pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.asOtherIDs.id.@extension = ssn
        }else{
            pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.asOtherIDs.replaceNode { }
        }
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.name.family.replaceNode { family(patientLastName)}
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.administrativeGenderCode.@code = gender
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.birthTime.@value = birthDate
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.addr.streetAddressLine.replaceNode { streetAddressLine(addrStreet)}
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.addr.city.replaceNode { city(addrCity)}
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.addr.state.replaceNode { state (addrState)}
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.patientPerson.addr.postalCode.replaceNode{ postalCode(addrPostalCode)}
        
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.providerOrganization.id.@root = providerOrganizationId
        pixFeed.controlActProcess.subject.registrationEvent.subject1.patient.providerOrganization.name.replaceNode { name(providerOrganizationName)}
        
        pixFeed.controlActProcess.subject.registrationEvent.custodian.assignedEntity.id.@root = providerOrganizationId
        pixFeed.controlActProcess.subject.registrationEvent.custodian.assignedEntity.assignedOrganization.name.replaceNode { name(providerOrganizationName)}
        
        return new XmlUtil().serialize(pixFeed)
    }
    
    String createPixQueryMessage(
        String mpiAssigningAuthorityOid,
        String localPatientAssigningAuthority,
        String localPatientId){
        
        def currentDateTime = HL7FormattedDataFactory.currentDateTime()
        def pixV3Query = new XmlSlurper().parseText(pixV3QueryString)
        
        //pixV3Query.id.@root = "${localPatientAssigningAuthority}.4"
        pixV3Query.id.@extension = java.util.UUID.randomUUID()
        
        pixV3Query.creationTime.@value = currentDateTime
        pixV3Query.receiver.device.id.@root = mpiAssigningAuthorityOid ? mpiAssigningAuthorityOid : localPatientAssigningAuthority
        pixV3Query.sender.device.id.@root = localPatientAssigningAuthority
        pixV3Query.controlActProcess.authorOrPerformer.assignedPerson.id.@root = localPatientAssigningAuthority
        pixV3Query.controlActProcess.queryByParameter.queryId.@root = localPatientAssigningAuthority
        pixV3Query.controlActProcess.queryByParameter.queryId.@extension = java.util.UUID.randomUUID()
        
        if(mpiAssigningAuthorityOid){
            //We are only interested in the id of this assigning authority so we populate datasource.id
            pixV3Query.controlActProcess.queryByParameter.parameterList.dataSource.value.@root = mpiAssigningAuthorityOid
        }
        else{
            pixV3Query.controlActProcess.queryByParameter.parameterList.dataSource.replaceNode {}
        }
        pixV3Query.controlActProcess.queryByParameter.parameterList.patientIdentifier.value.@root = localPatientAssigningAuthority
        pixV3Query.controlActProcess.queryByParameter.parameterList.patientIdentifier.value.@extension = localPatientId
        
        def serializedPixQuery = new XmlUtil().serialize(pixV3Query)
        return serializedPixQuery
    }
    
    String readPixQueryResponseGetGlobalId(String pixV3QueryResponseString, String assigningAuthority){
        
        def pixV3QueryResponse = new XmlSlurper().parseText(pixV3QueryResponseString)
        
        def globalPatientIdNode = pixV3QueryResponse.controlActProcess.subject.registrationEvent.subject1.patient.'*'.find { node ->
            node.name() == 'id' && node.@root == assigningAuthority
         }
        
        def globalPatientId
        
        if(globalPatientIdNode){
            globalPatientId = globalPatientIdNode.@extension
        }
        
        return globalPatientId
    }
}
