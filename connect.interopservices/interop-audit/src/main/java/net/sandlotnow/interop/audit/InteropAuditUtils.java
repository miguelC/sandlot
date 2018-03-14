/**
 *======================================================================================
 * InteropAuditUtils.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropAuditUtils
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Aug 31, 2015
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
package net.sandlotnow.interop.audit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//Imports
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public final class InteropAuditUtils {

	private final static String SIMPLE_DATE_FORMAT = "yyyyMMddHHmm";
	
    public static String jsonFromAuditList(List<InteropAudit> audits){

        ObjectMapper mapper = new ObjectMapper();        
        try {
            return mapper.writeValueAsString(audits);
     
        } catch (JsonGenerationException e) {    
            return "ERROR: " + e.getMessage();    
        } catch (JsonMappingException e) {
            return "ERROR: " + e.getMessage();    
        } catch (IOException e) {
            return "ERROR: " + e.getMessage();    
        }
    }
    
    public static List<InteropAudit> auditListFromJson(String json) 
            throws JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();        
        return mapper.readValue(json, new TypeReference<List<InteropAudit>>() {});
    }

    public static String jsonFromAudit(InteropAudit audit){

        ObjectMapper mapper = new ObjectMapper();        
        try {
            return mapper.writeValueAsString(audit);
     
        } catch (JsonGenerationException e) {    
            return "ERROR: " + e.getMessage();    
        } catch (JsonMappingException e) {
            return "ERROR: " + e.getMessage();    
        } catch (IOException e) {
            return "ERROR: " + e.getMessage();    
        }
    }
    
    public static InteropAudit auditFromJson(String json) 
            throws JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();        
        return mapper.readValue(json, InteropAudit.class);
    }


    public static String jsonFromAuditMessageList(List<InteropAuditMessage> auditMessages){

        ObjectMapper mapper = new ObjectMapper();        
        try {
            return mapper.writeValueAsString(auditMessages);
     
        } catch (JsonGenerationException e) {    
            return "ERROR: " + e.getMessage();    
        } catch (JsonMappingException e) {
            return "ERROR: " + e.getMessage();    
        } catch (IOException e) {
            return "ERROR: " + e.getMessage();    
        }
    }
    
    public static List<InteropAuditMessage> auditMessageListFromJson(String json) 
            throws JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();        
        return mapper.readValue(json, new TypeReference<List<InteropAuditMessage>>() {});
    }

    public static String jsonFromAuditMessage(InteropAuditMessage auditMessage){

        ObjectMapper mapper = new ObjectMapper();        
        try {
            return mapper.writeValueAsString(auditMessage);
     
        } catch (JsonGenerationException e) {    
            return "ERROR: " + e.getMessage();    
        } catch (JsonMappingException e) {
            return "ERROR: " + e.getMessage();    
        } catch (IOException e) {
            return "ERROR: " + e.getMessage();    
        }
    }
    
    public static InteropAuditMessage auditMessageFromJson(String json) 
            throws JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();        
        return mapper.readValue(json, InteropAuditMessage.class);
    }
    
    public static Date getDateFromStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		
	    Date dateObj = null;
		try {
			dateObj = sdf.parse(dateStr);
		} catch (ParseException e) {
		}
	    return dateObj;
	}
    
    public static String getStrFromDate(Date date) {
    	SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
    	return sdf.format(date);
    }

    public static String jsonFromSubjetInfo(SubjectInfo subject){

        ObjectMapper mapper = new ObjectMapper();        
        try {
            return mapper.writeValueAsString(subject);
     
        } catch (JsonGenerationException e) {    
            return "ERROR: " + e.getMessage();    
        } catch (JsonMappingException e) {
            return "ERROR: " + e.getMessage();    
        } catch (IOException e) {
            return "ERROR: " + e.getMessage();    
        }
    }
    public static String jsonFromSubjet(AuditSubject subject){

        ObjectMapper mapper = new ObjectMapper();     
        try {
            return mapper.writeValueAsString(subject);
     
        } catch (JsonGenerationException e) {    
            return "ERROR: " + e.getMessage();    
        } catch (JsonMappingException e) {
            return "ERROR: " + e.getMessage();    
        } catch (IOException e) {
            return "ERROR: " + e.getMessage();    
        }
    }

    public static InteropAuditSearch auditSearchFromJson(String json)  {

        try {
            ObjectMapper mapper = new ObjectMapper();        
            return mapper.readValue(json, InteropAuditSearch.class);
     
        }catch (JsonMappingException e) {
            return null;    
        } catch (IOException e) {
            return null;    
        }
    }
}
