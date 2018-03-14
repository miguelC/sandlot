/**
 *======================================================================================
 * CCDToIti41MessageProcessor.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- CCDToIti41MessageProcessor
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 15, 2015
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

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource
import javax.mail.util.ByteArrayDataSource
import net.sandlotnow.interop.integration.route.ihe.utils.HL7MessageIdFactory;
import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageFactory
import net.sandlotnow.interop.integration.xds.ContentUtils

/**
 * 
 * @author mcuri
 *
 */
class CCDToIti41MessageProcessor implements Processor{
    
    Logger log = LoggerFactory.getLogger(CCDToIti41MessageProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        //Read CCD
        def ccdString = exchange.in.getBody(String.class)
        def ccdXml = new XmlSlurper().parse(ccdString)
        
        def patientId = ccdXml.recordTarget.patientRole.id.@extension
        def assigningAuthority = ccdXml.recordTarget.patientRole.id.@root
        
        def docTypeCode = ccdXml.code.@code
        def docTypeCodeSystem = ccdXml.code.@codeSystem
        def docTypeCodeSystemName = ccdXml.code.@codeSystemName
        def docTypeDisplayName = ccdXml.code.@displayName
        
        def docIdRoot = ccdXml.id.@root
        def docIdExt = ccdXml.id.@extension
        
        def docSourceIdRoot = ccdXml.assignedCustodian.representedOrganization.id.@root
        def docSourceIdExt = ccdXml.assignedCustodian.representedOrganization.id.@extension
        
        def docEffectiveTime = ccdXml.effectiveTime.@value
        def docLanguageCode = ccdXml.languageCode.@code
        
        def patientFirstName = ccdXml.recordTarget.patientRole.patient.name.given[0].text()        
        def patientMiddleName
        if(ccdXml.recordTarget.patientRole.patient.name.given.size > 1){
            patientMiddleName = ccdXml.recordTarget.patientRole.patient.name.given[1].text()
        }
        def patientLastName = ccdXml.recordTarget.patientRole.patient.name.family.text()
        def patientSuffix
        def patientPrefix
        def patientDegree
        def patientBirthTime = ccdXml.recordTarget.patientRole.patient.birthTime.@value
        def patientGender = ccdXml.recordTarget.patientRole.patient.administrativeGenderCode.@code
        def patientAddressStreet = ccdXml.recordTarget.patientRole.addr.streetAddressLine.text()
        def patientAddressCity = ccdXml.recordTarget.patientRole.addr.city.text()
        def patientAddressState = ccdXml.recordTarget.patientRole.addr.state.text()
        def patientAddressZipcode = ccdXml.recordTarget.patientRole.addr.postalCode.text()
        
        def docTitle = ccdXml.title.text()
        
        def authorPersonId = ccdXml.author.assignedPerson.id.@extension
        def authorPersonIdRoot = ccdXml.author.assignedPerson.id.@root
        def authorPersonFirstName = ccdXml.author.assignedPerson.name.given[0].text()
        def authorPersonMiddleName
        if(ccdXml.author.assignedPerson.name.given.size > 1){
            authorPersonMiddleName = ccdXml.author.assignedPerson.name.given[1].text()
        }
        def authorPersonLastName = ccdXml.author.assignedPerson.name.family.text()
        def authorPersonSuffix = ccdXml.author.assignedPerson.name.suffix.text()     
        def authorPersonPrefix
        def authorPersonDegree   
        def authorInstitution = ccdXml.author.assignedAuthor.representedOrganization.name.text()        
        def authorSpecialty = ccdXml.author.assignedAuthor.code.@displayName   
        def authorSpecialtyCode = ccdXml.author.assignedAuthor.code.@code
        def authorRole = "Attending"
        
        def confidentialityCode = ccdXml.confidentialityCode.@code
        def confidentialityCodeSystem = ccdXml.confidentialityCode.@codeSystem
        def confidentialityCodeSystemName = ccdXml.confidentialityCode.@codeSystemName
        def confidentialityDisplayName = ccdXml.confidentialityCode.@displayName
        
        def docFormatCode = ccdXml.typeId.@extension
        def docFormatCodeRoot = ccdXml.typeId.@root
        
        def patientIdentifiable = XdsMessageFactory.createIdentifiable(patientId, assigningAuthority)
        def patientInfo = XdsMessageFactory.createPatientInfo(
            patientLastName, 
            patientFirstName, 
            patientMiddleName, 
            patientSuffix, 
            patientPrefix, 
            patientDegree, 
            patientAddressStreet, 
            patientAddressCity, 
            patientAddressState, 
            patientAddressZipcode, 
            patientBirthTime, 
            patientGender)
        
        def authorIdentifiable = XdsMessageFactory.createIdentifiable(authorPersonId, authorPersonIdRoot)
        
        def authorName = XdsMessageFactory.createName(
            authorPersonLastName, 
            authorPersonFirstName, 
            authorPersonMiddleName, 
            authorPersonSuffix, 
            authorPersonPrefix, 
            authorPersonDegree)
        
        def docTypeCodeCode = XdsMessageFactory.createCode(docTypeCode, docTypeCodeSystemName, docTypeCodeSystem)
        def docClassCode = XdsMessageFactory.createCode(docTypeCode, docTypeCodeSystemName, docTypeCodeSystemName)
        def docFormatCodeCode = XdsMessageFactory.createCode(docFormatCode, 'CDA', docFormatCodeRoot)
        def authorSpecialtyCodeCode = XdsMessageFactory.createCode(authorSpecialtyCode, authorSpecialty, 'Health Care Provider Taxonomy')
        
        def docSize = ccdString.getBytes().length
        def docSha1 = ContentUtils.calcSha1(ccdString.getBytes())
        // Document content is read using ByteArrayDataSource
        DataSource data = new ByteArrayDataSource(ccdString, 'text/xml')

        // Generate PNR
        
        def pnr = XdsMessageFactory.createProvideAndRegisterDocumentSet(
            patientIdentifiable, 
            patientIdentifiable,
            patientInfo, 
            authorIdentifiable, 
            authorName, 
            null, 
            docClassCode, 
            docFormatCodeCode, 
            docTypeCodeCode, 
            docLanguageCode, 
            XdsMessageFactory.createCode(confidentialityCode, confidentialityDisplayName, confidentialityCodeSystemName), 
            authorSpecialtyCodeCode, 
            authorSpecialtyCodeCode, 
            docTitle, 
            docEffectiveTime, 
            HL7MessageIdFactory.generateMessageId(), 
            docIdRoot + '.' + docIdExt, 
            docSize, 
            docSha1, 
            docSourceIdRoot, 
            docSourceIdExt, 
            docTypeCodeCode, 
            HL7MessageIdFactory.generateMessageId(), 
            HL7MessageIdFactory.generateMessageId(), 
            null,
            data)
        exchange.out.setBody(pnr)
        
    }

}

