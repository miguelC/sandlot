/**
 *======================================================================================
 * XdsMessageFactory.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- XdsMessageFactory
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
package net.sandlotnow.interop.integration.route.ihe.utils;

//Imports
import net.sandlotnow.interop.integration.xds.ContentUtils;

import org.apache.commons.lang.StringUtils;
import org.openehealth.ipf.commons.ihe.ws.utils.LargeDataSource;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.*;
import org.openehealth.ipf.commons.ihe.xds.core.requests.*;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.*;
import org.openehealth.ipf.commons.ihe.xds.core.responses.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class XdsMessageFactory {

    /**
     * @return a sample query response using leaf class return type.
     */
    public static QueryResponse createQueryResponseWithLeafClass(
            Identifiable patientId,
            Identifiable sourcePatientId,
            PatientInfo patientInfo,
            Identifiable authorId,
            XpnName authorName,
            Organization authorInstitution,
            Code classCode,
            Code formatCode,
            Code typeCode,
            String languageCode,
            Code confidentialityCode,
            Code practiceSettingCode,
            Code facilityTypeCode,  
            String title,
            String documentCreateTime,
            String documentEntryUuid,
            String documentUniqueId,
            long size,
            String hash,
            String repositoryUniqueId,  
            String documentSourceId, 
            Code contentTypeCode,
            String submissionSetEntryUuid,
            String submissionSetUniqueId,
            Recipient recipient) {

        SubmissionSet submissionSet = createSubmissionSet(
                patientId, 
                authorId, 
                authorName, 
                languageCode, 
                confidentialityCode, 
                contentTypeCode, 
                title, 
                documentSourceId, 
                submissionSetEntryUuid, 
                submissionSetUniqueId, 
                recipient);        
        DocumentEntry docEntry = createDocumentEntry(
                patientId,           
                sourcePatientId,
                patientInfo,
                authorId,
                authorName,
                authorInstitution,
                classCode,
                formatCode,
                typeCode,
                languageCode,
                confidentialityCode,
                practiceSettingCode,
                facilityTypeCode,
                title,
                documentCreateTime,
                documentEntryUuid,
                documentUniqueId,
                size,
                hash,
                repositoryUniqueId);
        //Folder folder = createFolder(patientId);
        
        Association docAssociation = createAssociationDocEntryToSubmissionSet(
                documentEntryUuid, submissionSetEntryUuid, documentEntryUuid + ".100", null);
        //Association folderAssociation = createAssociationFolderToSubmissionSet();
        //Association docFolderAssociation = createAssociationDocEntryToFolder();
        
        QueryResponse response = new QueryResponse();
        response.getSubmissionSets().add(submissionSet);
        response.getDocumentEntries().add(docEntry);
        //response.getFolders().add(folder);
        response.getAssociations().add(docAssociation);
        //response.getAssociations().add(folderAssociation);
        //response.getAssociations().add(docFolderAssociation);
        response.setStatus(Status.PARTIAL_SUCCESS);
        
        return response;
    }   

    /**
     * @return a sample query response using object reference return type.
     */
    public static QueryResponse createQueryResponseWithObjRef() {
        ObjectReference ref1 = new ObjectReference("ref1");
        ObjectReference ref2 = new ObjectReference("ref2");

        QueryResponse response = new QueryResponse();
        response.setStatus(Status.SUCCESS);
        response.getReferences().add(ref1);
        response.getReferences().add(ref2);
        
        return response;
    }   

    /**
     * @return a sample response.
     */
    public static Response createResponse() {
        Response response = new Response();
        response.setStatus(Status.FAILURE);
        response.getErrors().addAll(Arrays.asList(
                new ErrorInfo(ErrorCode.PATIENT_ID_DOES_NOT_MATCH, "context1", Severity.ERROR, "location1", null),
                new ErrorInfo(ErrorCode.SQL_ERROR, "context2", Severity.WARNING, null, null),
                new ErrorInfo(ErrorCode._USER_DEFINED, "context3", Severity.ERROR, "location3", "MyCustomErrorCode")));
        return response;
    }    

    /**
     * @return a sample response for retrieved documents.
     */
    public static RetrievedDocumentSet createRetrievedDocumentSet() {
        RetrieveDocument requestData1 = new RetrieveDocument();
        requestData1.setDocumentUniqueId("doc1");
        requestData1.setHomeCommunityId("urn:oid:1.2.3");
        requestData1.setRepositoryUniqueId("repo1");
        
        DataHandler dataHandler1 = createDataHandler();
        
        RetrievedDocument doc1 = new RetrievedDocument();
        doc1.setRequestData(requestData1);
        doc1.setDataHandler(dataHandler1);
        doc1.setMimeType("application/test1");

        RetrieveDocument requestData2 = new RetrieveDocument();
        requestData2.setDocumentUniqueId("doc2");
        requestData2.setHomeCommunityId("urn:oid:1.2.4");
        requestData2.setRepositoryUniqueId("repo2");

        DataHandler dataHandler2 = createDataHandler();        
        RetrievedDocument doc2 = new RetrievedDocument();
        doc2.setRequestData(requestData2);
        doc2.setDataHandler(dataHandler2);
        doc2.setMimeType("application/test2");
        doc2.setNewRepositoryUniqueId("repo2-new");
        doc2.setNewDocumentUniqueId("doc2-new");

        RetrievedDocumentSet response = new RetrievedDocumentSet();
        response.getDocuments().add(doc1);
        response.getDocuments().add(doc2);
        response.setStatus(Status.SUCCESS);
        
        return response;
    }
    
    /**
     * Creates a dummy data handler
     * @return the new data handler.
     */
    public static DataHandler createDataHandler() {
        return new DataHandler(new LargeDataSource());
    }
    
    public static DataHandler createDataHandler(DataSource data){
        return new DataHandler(data);
    }

    /**
     * @return a sample request to provide and register documents.
     */
    public static ProvideAndRegisterDocumentSet createProvideAndRegisterDocumentSet(
            Identifiable patientId,
            Identifiable sourcePatientId,
            PatientInfo patientInfo,
            Identifiable authorId,
            XpnName authorName,
            Organization authorInstitution,
            Code classCode,
            Code formatCode,
            Code typeCode,
            String languageCode,
            Code confidentialityCode,
            Code practiceSettingCode,
            Code facilityTypeCode,  
            String title,
            String documentCreateTime,
            String documentEntryUuid,
            String documentUniqueId,
            long size,
            String hash,
            String repositoryUniqueId,  
            String documentSourceId, 
            Code contentTypeCode,
            String submissionSetEntryUuid,
            String submissionSetUniqueId,
            Recipient recipient,
            DataSource data) {

        DataHandler dataHandler = createDataHandler(data);
        
        SubmissionSet submissionSet = createSubmissionSet(
                patientId, 
                authorId, 
                authorName, 
                languageCode, 
                confidentialityCode, 
                contentTypeCode, 
                title, 
                documentSourceId, 
                submissionSetEntryUuid, 
                submissionSetUniqueId, 
                recipient);          
        
        DocumentEntry documentEntry = createDocumentEntry(
                patientId,           
                sourcePatientId,
                patientInfo,
                authorId,
                authorName,
                authorInstitution,
                classCode,
                formatCode,
                typeCode,
                languageCode,
                confidentialityCode,
                practiceSettingCode,
                facilityTypeCode,
                title,
                documentCreateTime,
                documentEntryUuid,
                documentUniqueId,
                size,
                hash,
                repositoryUniqueId);

        //Folder folder = createFolder(patientId);
        
        Association docAssociation = createAssociationDocEntryToSubmissionSet(
                documentEntryUuid, submissionSetEntryUuid, documentEntryUuid + ".100", null);
        //Association folderAssociation = createAssociationFolderToSubmissionSet();
        //Association docFolderAssociation = createAssociationDocEntryToFolder();
        
        Document doc = new Document(documentEntry, dataHandler);
        
        ProvideAndRegisterDocumentSet request = new ProvideAndRegisterDocumentSet();
        request.setSubmissionSet(submissionSet);
        request.getDocuments().add(doc);
        //request.getFolders().add(folder);
        request.getAssociations().add(docAssociation);
        //request.getAssociations().add(folderAssociation);
        //request.getAssociations().add(docFolderAssociation);
        
        return request;
    }

    private static Association createAssociationDocEntryToFolder(
            String folderUniqueId, String documentUniqueId, String associationUniqueId) {
        Association docFolderAssociation = new Association();
        docFolderAssociation.setAssociationType(AssociationType.HAS_MEMBER);
        docFolderAssociation.setSourceUuid(folderUniqueId);
        docFolderAssociation.setTargetUuid(documentUniqueId);
        docFolderAssociation.setEntryUuid(associationUniqueId);
        return docFolderAssociation;
    }

    private static Association createAssociationFolderToSubmissionSet(
            String folderUniqueId, String submissionSetUniqueId, String associationUniqueId, String previousVersionId) {
        Association folderAssociation = new Association();
        folderAssociation.setAssociationType(AssociationType.HAS_MEMBER);
        folderAssociation.setSourceUuid(submissionSetUniqueId);
        folderAssociation.setTargetUuid(folderUniqueId);
        folderAssociation.setEntryUuid(associationUniqueId);
        folderAssociation.setPreviousVersion(previousVersionId);
        return folderAssociation;
    }

    private static Association createAssociationDocEntryToSubmissionSet(
            String documentEntryUniqueId, String submissionSetUniqueId, String associationUniqueId, String previousVersionId) {
        Association docAssociation = new Association();
        docAssociation.setAssociationType(AssociationType.HAS_MEMBER);
        docAssociation.setSourceUuid(submissionSetUniqueId);
        docAssociation.setTargetUuid(documentEntryUniqueId);
        docAssociation.setLabel(AssociationLabel.ORIGINAL);
        docAssociation.setEntryUuid(associationUniqueId);
        docAssociation.setPreviousVersion(previousVersionId);
        return docAssociation;
    }

    /**
     * Creates a sample folder.
     * @param patientID
     *          the patient ID to use.
     * @return the folder.                         
     */
    public static Folder createFolder(Identifiable patientID) {
        Folder folder = new Folder();
        folder.setAvailabilityStatus(AvailabilityStatus.APPROVED);
        folder.getCodeList().add(new Code("code7", new LocalizedString("code7"), "scheme7"));
        folder.setComments(new LocalizedString("comments3"));
        folder.setEntryUuid("folder01");
        folder.setLastUpdateTime("198209");
        folder.setPatientId(patientID);
        folder.setTitle(new LocalizedString("Folder 01", "en-US", "UTF8"));
        folder.setUniqueId("48574589");
        return folder;
    }

    /**
     * Creates a sample submission set.
     * @param patientID
     *          the patient ID to use.
     * @return the submission set.
     */
    public static SubmissionSet createSubmissionSet(
            Identifiable patientId,
            Identifiable authorId,
            XpnName authorName,
            String languageCode,
            Code confidentialityCode,
            Code contentTypeCode,
            String title,
            String documentSourceId,
            String submissionSetEntryUuid,
            String submissionSetUniqueId,
            Recipient recipient) {
        

        if(StringUtils.isBlank(languageCode )){
            languageCode = "en-US";
        }
        
        Author author = new Author();
        author.setAuthorPerson(new Person(authorId,authorName));

        SubmissionSet submissionSet = new SubmissionSet();
        submissionSet.getAuthors().add(author);
        submissionSet.setAvailabilityStatus(AvailabilityStatus.APPROVED);
        submissionSet.setContentTypeCode(contentTypeCode);
        submissionSet.setEntryUuid(submissionSetEntryUuid);
        submissionSet.setPatientId(patientId);
        submissionSet.setSourceId(documentSourceId);
        submissionSet.setSubmissionTime(HL7FormattedDataFactory.currentDateTime());
        submissionSet.setTitle(new LocalizedString(title, languageCode, "UTF8"));
        submissionSet.setUniqueId(submissionSetUniqueId);
        //submissionSet.setComments(new LocalizedString("comments1"));
        if(recipient != null){
            submissionSet.getIntendedRecipients().add(recipient);
        }
        return submissionSet;
    }

    /**
     * Creates a document entry
     * @param patientID
     *          patient used for the document entry.
     * @return the new entry.
     */
    public static DocumentEntry createDocumentEntry(
            Identifiable patientId,
            Identifiable sourcePatientId,
            PatientInfo patientInfo,
            Identifiable authorId,
            XpnName authorName,
            Organization authorInstitution,
            Code classCode,
            Code formatCode,
            Code typeCode,
            String languageCode,
            Code confidentialityCode,
            Code practiceSettingCode,
            Code facilityTypeCode,
            String title,
            String documentCreateTime,
            String documentEntryUuid,
            String documentUniqueId,
            long size,
            String hash,
            String repositoryUniqueId
            ) {
        
        if(StringUtils.isBlank(languageCode )){
            languageCode = "en-US";
        }
        Author author = new Author();
        author.setAuthorPerson(createPerson(authorId, authorName));
        author.getAuthorInstitution().add(authorInstitution);
      
        
        DocumentEntry docEntry = new DocumentEntry();
        docEntry.getAuthors().add(author);
        docEntry.setAvailabilityStatus(AvailabilityStatus.APPROVED);
        docEntry.setClassCode(classCode);
        docEntry.getConfidentialityCodes().add(confidentialityCode);
        docEntry.setCreationTime(documentCreateTime);
        docEntry.setEntryUuid(documentEntryUuid);
        //docEntry.getEventCodeList().add(new Code("code9", new LocalizedString("code9"), "scheme9"));
        docEntry.setFormatCode(formatCode);
        docEntry.setLanguageCode(languageCode);
//        docEntry.setLegalAuthenticator(new Person(new Identifiable("legal", new AssigningAuthority("1.7")),
//                new XpnName("Gustav", null, null, null, null, null)));
        //docEntry.setMimeType("application/octet-stream");
        docEntry.setPatientId(patientId);
        docEntry.setPracticeSettingCode(practiceSettingCode);
        docEntry.setHealthcareFacilityTypeCode(facilityTypeCode);
        docEntry.setServiceStartTime(HL7FormattedDataFactory.currentDateTime());
        docEntry.setServiceStopTime(HL7FormattedDataFactory.currentDateTime());
        docEntry.setSourcePatientId(sourcePatientId);
        docEntry.setSourcePatientInfo(patientInfo);
        docEntry.setTitle(new LocalizedString(title, languageCode, "UTF8"));
        docEntry.setTypeCode(typeCode);
        docEntry.setUniqueId(documentUniqueId);
        docEntry.setSize(size);
        docEntry.setHash(hash);
        if(StringUtils.isNotBlank(repositoryUniqueId)){
            docEntry.setRepositoryUniqueId(repositoryUniqueId);
        }
        //docEntry.setComments(new LocalizedString("comment2"));
        //docEntry.setUri("http://hierunten.com");
        return docEntry;
    }    
    public static Recipient createRecipient(Person person, Organization organization){

        Recipient recipient = new Recipient();
        recipient.setOrganization(organization);
        recipient.setPerson(person);
        return recipient;
    }
    
    public static Code createCode(String code, String displayName, String codeSystem){
        Code codeObj = new Code(code, new LocalizedString(displayName), codeSystem);
        return codeObj;
    }
    public static Identifiable createIdentifiable(String id, String root){
        return new Identifiable(id, new AssigningAuthority( root));
    }
    public static Person createPerson(Identifiable id, Name name){
        return new Person(id, name);
    }   
    
    public Organization createOrganization(String name, String id, String root){
        return new Organization(name, id, new AssigningAuthority(root));
    }
    /**
     * Create a name object
     * @param lastName
     * @param firstName
     * @param middleName
     * @param suffix
     * @param prefix
     * @param degree
     * @return
     */
    public static XpnName createName(
            String lastName,
            String firstName,
            String middleName,
            String suffix,
            String prefix,
            String degree){
        return new XpnName(lastName, firstName, middleName, suffix, prefix, degree);
    }
    /**
     * Create a patient info object
     * @param firstName
     * @param lastName
     * @param middleName
     * @param suffix
     * @param prefix
     * @param degree
     * @param addrStreet
     * @param addrCity
     * @param addrState
     * @param addrPostalCode
     * @param dateOfBirth
     * @param gender
     * @return
     */
    public static PatientInfo createPatientInfo(
            String lastName,
            String firstName,
            String middleName,
            String suffix,
            String prefix,
            String degree,
            String addrStreet,
            String addrCity,
            String addrState,
            String addrPostalCode,
            String dateOfBirth,
            String gender){

        PatientInfo patientInfo = new PatientInfo();

        Address address = new Address();
        address.setStreetAddress(addrStreet);   
        address.setCity(addrCity);
        address.setStateOrProvince(addrState);
        address.setZipOrPostalCode(addrPostalCode);        
        patientInfo.setAddress(address);
        
        patientInfo.setDateOfBirth(dateOfBirth);
        patientInfo.setGender(gender);
        patientInfo.setName(createName(lastName, firstName, middleName, suffix, prefix, degree));
        return patientInfo;
    }

    /**
     * @return a sample request to register a document set.
     */
    public static RegisterDocumentSet createRegisterDocumentSet(
            Identifiable patientId,
            Identifiable sourcePatientId,
            PatientInfo patientInfo,
            Identifiable authorId,
            XpnName authorName,
            Organization authorInstitution,
            Code classCode,
            Code formatCode,
            Code typeCode,
            String languageCode,
            Code confidentialityCode,
            Code practiceSettingCode,
            Code facilityTypeCode,  
            String title,
            String documentCreateTime,
            String documentEntryUuid,
            String documentUniqueId,
            long size,
            String hash,
            String repositoryUniqueId,  
            String documentSourceId, 
            Code contentTypeCode,
            String submissionSetEntryUuid,
            String submissionSetUniqueId,
            Recipient recipient) {
        
        SubmissionSet submissionSet = createSubmissionSet(
                patientId, 
                authorId, 
                authorName, 
                languageCode, 
                confidentialityCode, 
                contentTypeCode, 
                title, 
                documentSourceId, 
                submissionSetEntryUuid, 
                submissionSetUniqueId, 
                recipient);        
        DocumentEntry docEntry = createDocumentEntry(
                patientId,           
                sourcePatientId,
                patientInfo,
                authorId,
                authorName,
                authorInstitution,
                classCode,
                formatCode,
                typeCode,
                languageCode,
                confidentialityCode,
                practiceSettingCode,
                facilityTypeCode,
                title,
                documentCreateTime,
                documentEntryUuid,
                documentUniqueId,
                size,
                hash,
                repositoryUniqueId);        
        //Folder folder = createFolder(patientId);
        
        Association docAssociation = createAssociationDocEntryToSubmissionSet(
                documentEntryUuid, submissionSetEntryUuid, documentEntryUuid + ".100", null);
        //Association folderAssociation = createAssociationFolderToSubmissionSet();
        //Association docFolderAssociation = createAssociationDocEntryToFolder();
        
        RegisterDocumentSet request = new RegisterDocumentSet();
        request.setSubmissionSet(submissionSet);
        request.getDocumentEntries().add(docEntry);
        //request.getFolders().add(folder);
        request.getAssociations().add(docAssociation);
        //request.getAssociations().add(folderAssociation);
        //request.getAssociations().add(docFolderAssociation);
        
        return request;
    }

    /**
     * @return a sample request to retrieve a document set.
     */
    public static RetrieveDocumentSet createRetrieveDocumentSet() {
        RetrieveDocumentSet request = new RetrieveDocumentSet();
        request.getDocuments().add(new RetrieveDocument("repo1", "doc1", "urn:oid:1.2.3"));
        request.getDocuments().add(new RetrieveDocument("repo2", "doc2", "urn:oid:1.2.4"));
        return request;
    }
    
    /**
     * @return a sample request to retrieve an imaging document set.
     */
    public static RetrieveImagingDocumentSet createRetrieveImagingDocumentSet() {
        RetrieveImagingDocumentSet request = new RetrieveImagingDocumentSet();

        List<RetrieveDocument> retrieveDocuments = new ArrayList<RetrieveDocument>();
        RetrieveDocument retrieveDocument1 = new RetrieveDocument("repo1", "doc1", "urn:oid:1.2.3");
        retrieveDocuments.add(retrieveDocument1);
        RetrieveDocument retrieveDocument2 = new RetrieveDocument("repo2", "doc2", "urn:oid:1.2.4");
        retrieveDocuments.add(retrieveDocument2);

        List<RetrieveSeries> retrieveSerieses = new ArrayList<RetrieveSeries>();
        RetrieveSeries retrieveSeries1 = new RetrieveSeries("urn:oid:1.2.1", retrieveDocuments);
        retrieveSerieses.add(retrieveSeries1);
        RetrieveSeries retrieveSeries2 = new RetrieveSeries("urn:oid:1.2.2", retrieveDocuments);
        retrieveSerieses.add(retrieveSeries2);

        List<RetrieveStudy> retrieveStudies = request.getRetrieveStudies();
        RetrieveStudy retrieveStudy1 = new RetrieveStudy("urn:oid:1.1.1", retrieveSerieses);
        retrieveStudies.add(retrieveStudy1);
        RetrieveStudy retrieveStudy2 = new RetrieveStudy("urn:oid:1.1.2", retrieveSerieses);
        retrieveStudies.add(retrieveStudy2);

        return request;
    }

    /**
     * @return a sample sql query.
     */
    public static QueryRegistry createSqlQuery() {
        SqlQuery query = new SqlQuery();        
        query.setSql("SELECT * FROM INTERNET");
        return new QueryRegistry(query);
    }
    
    /**
     * @return a sample stored query for get documents.
     */
    public static QueryRegistry createGetDocumentsQuery() {
        GetDocumentsQuery query = new GetDocumentsQuery();
        query.setHomeCommunityId("urn:oid:1.2.3.14.15.926");
        query.setUuids(Collections.singletonList("document01"));
        
        return new QueryRegistry(query);
    }
    
    /**
     * @return a sample stored query for find documents.
     */
    public static QueryRegistry createFindDocumentsQuery() {
        FindDocumentsQuery query = new FindDocumentsQuery();
        populateDocumentsQuery(query);
        query.setPatientId(new Identifiable("id3", new AssigningAuthority("1.3")));
        query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
        query.setDocumentEntryTypes(Arrays.asList(DocumentEntryType.STABLE));
        return new QueryRegistry(query);
    }

    private static void populateDocumentsQuery(DocumentsQuery query) {
        
        query.setHomeCommunityId("12.21.41");

        query.setClassCodes(Arrays.asList(new Code("code1", null, "scheme1"), new Code("code2", null, "scheme2")));
        query.setTypeCodes(Arrays.asList(new Code("codet1", null, "schemet1"), new Code("codet2", null, "schemet2")));
        query.setPracticeSettingCodes(Arrays.asList(new Code("code3", null, "scheme3"), new Code("code4", null, "scheme4")));
        query.getCreationTime().setFrom("1980");
        query.getCreationTime().setTo("1981");
        query.getServiceStartTime().setFrom("1982");
        query.getServiceStartTime().setTo("1983");
        query.getServiceStopTime().setFrom("1984");
        query.getServiceStopTime().setTo("1985");
        query.setHealthcareFacilityTypeCodes(Arrays.asList(new Code("code5", null, "scheme5"), new Code("code6", null, "scheme6")));
        QueryList<Code> eventCodes = new QueryList<Code>();
        eventCodes.getOuterList().add(
                Arrays.asList(new Code("code7", null, "scheme7"), new Code("code8", null, "scheme8")));
        eventCodes.getOuterList().add(
                Arrays.asList(new Code("code9", null, "scheme9")));
        query.setEventCodes(eventCodes);
        QueryList<Code> confidentialityCodes = new QueryList<Code>();
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code10", null, "scheme10"), new Code("code11", null, "scheme11")));
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code12", null, "scheme12")));
        query.setConfidentialityCodes(confidentialityCodes);
        query.setAuthorPersons(Arrays.asList("per'son1", "person2"));
        query.setFormatCodes(Arrays.asList(new Code("code13", null, "scheme13"), new Code("code14", null, "scheme14")));
    }

    /**
     * @return a sample stored query for find documents (Multi Patient).
     */
    public static QueryRegistry createFindDocumentsForMultiplePatientsQuery() {
        FindDocumentsForMultiplePatientsQuery query = new FindDocumentsForMultiplePatientsQuery();
        populateDocumentsQuery(query);
        query.setPatientIds(Arrays.asList(
                new Identifiable("id3", new AssigningAuthority("1.3")),
                new Identifiable("id4", new AssigningAuthority("1.4"))));
        query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
        query.setDocumentEntryTypes(Arrays.asList(DocumentEntryType.STABLE));
        return new QueryRegistry(query);
    }


    /**
     * @return a sample stored query for find folders.
     */
    public static QueryRegistry createFindFoldersQuery() {
        FindFoldersQuery query = new FindFoldersQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setPatientId(new Identifiable("id1", new AssigningAuthority("1.2")));
        query.getLastUpdateTime().setFrom("1980");
        query.getLastUpdateTime().setTo("1981");
        QueryList<Code> codes = new QueryList<Code>();
        codes.getOuterList().add(
                Arrays.asList(new Code("code7", null, "scheme7"), new Code("code8", null, "scheme8")));
        codes.getOuterList().add(
                Arrays.asList(new Code("code9", null, "scheme9")));
        query.setCodes(codes);
        query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
        
        return new QueryRegistry(query);
    }


    /**
     * @return a sample stored query for find folders.
     */
    public static QueryRegistry createFindFoldersForMultiplePatientsQuery() {
        FindFoldersForMultiplePatientsQuery query = new FindFoldersForMultiplePatientsQuery();

        query.setHomeCommunityId("12.21.41");
        query.setPatientIds(Arrays.asList(new Identifiable("id1", new AssigningAuthority("1.2")), new Identifiable("id2", new AssigningAuthority("1.2"))));
        query.getLastUpdateTime().setFrom("1980");
        query.getLastUpdateTime().setTo("1981");
        QueryList<Code> codes = new QueryList<Code>();
        codes.getOuterList().add(
                Arrays.asList(new Code("code7", null, "scheme7"), new Code("code8", null, "scheme8")));
        codes.getOuterList().add(
                Arrays.asList(new Code("code9", null, "scheme9")));
        query.setCodes(codes);
        query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));

        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for find submission sets.
     */
    public static QueryRegistry createFindSubmissionSetsQuery() {
        FindSubmissionSetsQuery query = new FindSubmissionSetsQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setPatientId(new Identifiable("id1", new AssigningAuthority("1.2")));
        query.getSubmissionTime().setFrom("1980");
        query.getSubmissionTime().setTo("1981");
        query.setAuthorPerson("per'son1");
        query.setSourceIds(Arrays.asList("1.2.3", "3.2.1"));
        query.setContentTypeCodes(Arrays.asList(new Code("code1", null, "scheme1"), new Code("code2", null, "scheme2")));
        query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
        
        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting data from all types.
     */
    public static QueryRegistry createGetAllQuery() {
        GetAllQuery query = new GetAllQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setPatientId(new Identifiable("id1", new AssigningAuthority("1.2")));
        QueryList<Code> codes = new QueryList<Code>();
        codes.getOuterList().add(
                Arrays.asList(new Code("code7", null, "scheme7"), new Code("code8", null, "scheme8")));
        codes.getOuterList().add(
                Arrays.asList(new Code("code9", null, "scheme9")));
        query.setConfidentialityCodes(codes);
        query.setFormatCodes(Arrays.asList(new Code("code1", null, "scheme1"), new Code("code2", null, "scheme2")));
        query.setStatusDocuments(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
        query.setStatusFolders(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
        query.setStatusSubmissionSets(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
        query.setDocumentEntryTypes(Arrays.asList(DocumentEntryType.STABLE));

        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting associations.
     */
    public static QueryRegistry createGetAssociationsQuery() {
        GetAssociationsQuery query = new GetAssociationsQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setUuids(Arrays.asList("urn:uuid:1.2.3.4", "urn:uuid:2.3.4.5"));
        
        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting associations and documents.
     */
    public static QueryRegistry createGetDocumentsAndAssociationsQuery() {
        GetDocumentsAndAssociationsQuery query = new GetDocumentsAndAssociationsQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setUuids(Arrays.asList("urn:uuid:1.2.3.4", "urn:uuid:2.3.4.5"));
        query.setUniqueIds(Arrays.asList("12.21.34", "43.56.89"));
        
        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting folders and their content.
     */
    public static QueryRegistry createGetFolderAndContentsQuery() {
        GetFolderAndContentsQuery query = new GetFolderAndContentsQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setUuid("urn:uuid:1.2.3.4");
        query.setUniqueId("12.21.34");
        QueryList<Code> confidentialityCodes = new QueryList<Code>();
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code10", null, "scheme10"), new Code("code11", null, "scheme11")));
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code12", null, "scheme12")));
        query.setConfidentialityCodes(confidentialityCodes);
        query.setFormatCodes(Arrays.asList(new Code("code13", null, "scheme13"), new Code("code14", null, "scheme14")));
        query.setDocumentEntryTypes(Arrays.asList(DocumentEntryType.STABLE));
        
        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting folders based on their documents.
     */
    public static QueryRegistry createGetFoldersForDocumentQuery() {
        GetFoldersForDocumentQuery query = new GetFoldersForDocumentQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setUuid("urn:uuid:1.2.3.4");
        query.setUniqueId("12.21.34");
        QueryList<Code> confidentialityCodes = new QueryList<Code>();
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code10", null, "scheme10"), new Code("code11", null, "scheme11")));
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code12", null, "scheme12")));
        
        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting folders.
     */
    public static QueryRegistry createGetFoldersQuery() {
        GetFoldersQuery query = new GetFoldersQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setUuids(Arrays.asList("urn:uuid:1.2.3.4", "urn:uuid:2.3.4.5"));
        query.setUniqueIds(Arrays.asList("12.21.34", "43.56.89"));
        
        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting documents related to some other object.
     */
    public static QueryRegistry createGetRelatedDocumentsQuery() {
        GetRelatedDocumentsQuery query = new GetRelatedDocumentsQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setUuid("urn:uuid:1.2.3.4");
        query.setUniqueId("12.21.34");
        query.setAssociationTypes(Arrays.asList(AssociationType.APPEND, AssociationType.TRANSFORM));
        query.setDocumentEntryTypes(Arrays.asList(DocumentEntryType.STABLE));
                
        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting submission sets and their contents.
     */
    public static QueryRegistry createGetSubmissionSetAndContentsQuery() {
        GetSubmissionSetAndContentsQuery query = new GetSubmissionSetAndContentsQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setUuid("urn:uuid:1.2.3.4");
        query.setUniqueId("12.21.34");
        QueryList<Code> confidentialityCodes = new QueryList<Code>();
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code10", null, "scheme10"), new Code("code11", null, "scheme11")));
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code12", null, "scheme12")));
        query.setDocumentEntryTypes(Arrays.asList(DocumentEntryType.STABLE));

        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for getting submission sets.
     */
    public static QueryRegistry createGetSubmissionSetsQuery() {
        GetSubmissionSetsQuery query = new GetSubmissionSetsQuery();
        
        query.setHomeCommunityId("12.21.41");
        query.setUuids(Arrays.asList("urn:uuid:1.2.3.4", "urn:uuid:2.3.4.5"));
                
        return new QueryRegistry(query);
    }

    /**
     * @return a sample stored query for find documents.
     */
    public static QueryRegistry createFetchQuery() {
        FetchQuery query = new FetchQuery();

        query.setHomeCommunityId("urn:oid:1.2.21.41");
        query.setPatientId(new Identifiable("id3", new AssigningAuthority("1.3")));
        query.setClassCodes(Arrays.asList(new Code("code1", null, "scheme1"), new Code("code2", null, "scheme2")));
        query.setTypeCodes(Arrays.asList(new Code("codet1", null, "schemet1"), new Code("codet2", null, "schemet2")));
        query.setPracticeSettingCodes(Arrays.asList(new Code("code3", null, "scheme3"), new Code("code4", null, "scheme4")));
        query.getCreationTime().setFrom("1980");
        query.getCreationTime().setTo("1981");
        query.getServiceStartTime().setFrom("1982");
        query.getServiceStartTime().setTo("1983");
        query.getServiceStopTime().setFrom("1984");
        query.getServiceStopTime().setTo("1985");
        query.setHealthcareFacilityTypeCodes(Arrays.asList(new Code("code5", null, "scheme5"), new Code("code6", null, "scheme6")));
        QueryList<Code> eventCodes = new QueryList<Code>();
        eventCodes.getOuterList().add(
                Arrays.asList(new Code("code7", null, "scheme7"), new Code("code8", null, "scheme8")));
        eventCodes.getOuterList().add(
                Arrays.asList(new Code("code9", null, "scheme9")));
        query.setEventCodes(eventCodes);
        QueryList<Code> confidentialityCodes = new QueryList<Code>();
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code10", null, "scheme10"), new Code("code11", null, "scheme11")));
        confidentialityCodes.getOuterList().add(
                Arrays.asList(new Code("code12", null, "scheme12")));
        query.setConfidentialityCodes(confidentialityCodes);
        query.setAuthorPersons(Arrays.asList("per'son1", "person2"));
        query.setFormatCodes(Arrays.asList(new Code("code13", null, "scheme13"), new Code("code14", null, "scheme14")));
        //query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));

        QueryRegistry queryRegistry = new QueryRegistry(query);
        queryRegistry.setReturnType(QueryReturnType.LEAF_CLASS_WITH_REPOSITORY_ITEM);
        return queryRegistry;
    }


    public static RemoveDocumentSet createRemoveDocumentSet(){
        RemoveDocumentSet removeDocs = new RemoveDocumentSet();
        removeDocs.getReferences().add(new ObjectReference("urn:uuid:b2632452-1de7-480d-94b1-c2074d79c871", "1.2.3"));
        removeDocs.getReferences().add(new ObjectReference("urn:uuid:b2632df2-1de7-480d-1045-c2074d79aabd", "5.6.7"));
        //removeDocs.setDeletionScope("urn:oasis:names:tc:ebxml-regrep:DeletionScopeType:DeleteAll");

        return removeDocs;
    }
}
