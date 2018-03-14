/**
 *======================================================================================
 * HL7Constants.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- HL7Constants
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Sep 16, 2015
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

public class HL7Constants {
    public static final String AGENT_CLASS_CODE="AGNT";
    public static final String ASSIGNED_DEVICE_CLASS_CODE = "ASSIGNED";
    public static final String CLASS_CODE_ASSIGNED = "ASSIGNED";
    public static final String CLASS_CODE_ORG = "ORG";
    public static final String CLASS_CODE_PATIENT = "PAT";
    public static final String CLASS_CODE_PSN = "PSN";
    public static final String CLASS_CODE_REGISTRATION = "REG";
    public static final String DEFAULT_LOCAL_DEVICE_ID = "1.1";
    public static final String DETECTED_ISSUE_CLASSCODE_ALRT = "ALRT";
    public static final String DETECTED_ISSUE_MOODCODE_EVN = "EVN";
    public static final String DETECTED_ISSUE_CODE_ADMINISTRATIVE = "ActAdministrativeDetectedIssueCode";
    public static final String DETECTED_ISSUE_CODESYSTEM_ERROR_CODE = "2.16.840.1.113883.5.4";
    public static final String DETECTED_ISSUE_MITIGATEDBY_TYPECODE_MITGT = "MITGT";
    public static final String DETECTEDISSUEMANAGEMENT_CLASSCODE = "ACT";
    public static final String DETECTEDISSUEMANAGEMENT_MOODCODE_RQO = "RQO";
    public static final String DETECTEDISSUEMANAGEMENT_CODE_RESPONDER_BUSY = "ResponderBusy";
    public static final String DETECTEDISSUEMANAGEMENT_CODESYSTEM = "1.3.6.1.4.1.19376.1.2.27.3";
    public static final String DETERMINER_CODE_INSTANCE = "INSTANCE";
    public static final String GENDER_CODE_MALE = "M";
    public static final String GENDER_CODE_FEMALE = "F";
    public static final String GENDER_CODE_UNKNOWN = "UN";
    public static final String INTERACTION_ID_ROOT = "2.16.840.1.113883.1.6";
    public static final String INTERACTION_EXTENSION_PRPAIN201309UV02 = "PRPA_IN201309UV02";
    public static final String INTERACTION_EXTENSION_MCCIIN000002UV01 = "MCCIIN000002UV01";
    public static final String ITS_VERSION = "XML_1.0";
    public static final String MESSAGE_ACKNOWLEDGEMENT_TYPE_CODE_COMMIT_ACCEPT = "CA";
    public static final String MESSAGE_ACKNOWLEDGEMENT_TYPE_CODE_COMMIT_ERROR = "CE";
    public static final String MESSAGE_ACKNOWLEDGEMENT_TYPE_CODE_COMMIT_REJECT = "CR";
    public static final String MESSAGE_ACKNOWLEDGEMENT_TYPE_CODE_APPLICATION_ACCEPT = "AA";
    public static final String MESSAGE_ACKNOWLEDGEMENT_TYPE_CODE_APPLICATION_ERROR = "AE";
    public static final String MOOD_COODE_EVN = "EVN";
    public static final String NULL_FLAVOR = "NA";
    public static final String ORG_CLASS_CODE="ORG";
    public static final String QUERY_ACK_AE = "AE";
    public static final String QUERY_ACK_NF = "NF";
    public static final String QUERY_ACK_OK = "OK";
    public static final String QUERY_ACK_QE = "QE";
    public static final String RECEIVER_DETERMINER_CODE = "INSTANCE";
    public static final String SENDER_DETERMINER_CODE = "INSTANCE";
    public static final String SSN_ID_ROOT = "2.16.840.1.113883.4.1";
    public static final String STATUS_CODE_ACTIVE = "active";
    public static final String TYPE_CODE_CST = "CST";
    public static final String TYPE_CODE_SUBJ = "SUBJ";
    public static final String UNIVERSAL_IDENTIFIER_TYPE_CODE_ISO = "ISO";
    public static final String UPDATE_NOTIFICATION_TRIGGER_EVENT_CODE = "PRPA_TE201302UV02";
    public static final String USE_CODE_SRCH = "SRCH";

    public static final String MALE_GENDER_CODE = "M";
    public static final String OPENEMPI_MALE_GENDER_CODE = "M";
    public static final String FEMALE_GENDER_CODE = "F";
    public static final String OPENEMPI_FEMALE_GENDER_CODE = "F";
    public static final String UNDIFFERENTIATED_GENDER_CODE = "UN";
    public static final String OPENEMPI_UNDIFFERENTIATED_GENDER_CODE = "UN";

    public static final String TELECOM_URL_SCHEME = "tel:";
    public static final String SSN_OID = "2.16.840.1.113883.4.1";

    public static final String PRPA_IN201306UV02 = "PRPA_IN201306UV02";
    public static final String PRPA_TE201306UV02 = "PRPA_TE201306UV02";
    public static final String PRPA_TE201309UV02 = "PRPA_TE201309UV02";
    public static final String MCCI_IN000002UV01 = "MCCI_IN000002UV01";
    
    public static final String ACCEPT_ACK_CODE_ALWAYS = "AL";
    public static final String ACCEPT_ACK_CODE_ON_ERRORS = "ER";
    public static final String ACCEPT_ACK_CODE_NEVER = "NE";
    public static final String ACT_RELATIONSHIP_TYPE = "SUBJ";
    public static final String AS_OTHER_IDS_SSN_CLASS_CODE = "SD";
    public static final String CLASS_CODE_AGENT = "AGNT";

    public static final String CLASS_CODE_REGISTRATION_EVENT = "REG";

    public static final String ENTITY_CLASS_CODE_ORG = "ORG";
    public static final String ENTITY_DETERMINER_CODE_INSTANCE = "INSTANCE";
    public static final String ENTITY_DETERMINER_CODE_KIND = "KIND";
    public static final String MOOD_CODE_EVENT = "EVN"; 
    public static final String NO_HEALTH_DATA_LOCATOR_CODE = "NoHealthDataLocator";
    public static final String NO_HEALTH_DATA_LOCATOR_CODE_SYSTEM = "1.3.6.1.4.1.19376.1.2.27.2";

    public static final String PARTICIPATION_CUSTODIAN = "CST";
    public static final String PROCESSING_CODE_DEBUGGING = "D";
    public static final String PROCESSING_CODE_PRODUCTION = "P";
    public static final String PROCESSING_CODE_TRAINING = "T";
    public static final String PROCESSING_MODE_CODE_ARCHIVE = "A";
    public static final String PROCESSING_MODE_CODE_INITIAL_LOAD = "I";
    public static final String PROCESSING_MODE_CODE_CURRENT_PROCESSING = "T";
    public static final String PROCESSING_MODE_CODE_RESTORE = "R";

    public static final String ROLE_CLASS_AGENT = "AGNT";
    public static final String ROLE_CLASS_ASSIGNED = "ASSIGNED";
    
    public static final String XML_QNAME_HL7_ORG = "urn:hl7-org:v3";
    
}
