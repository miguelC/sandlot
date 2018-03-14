/**
 *======================================================================================
 * ConfigurationUtils.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- ConfigurationUtils
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
package net.sandlotnow.interop.configuration.json;

//Imports
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sandlotnow.interop.ihe.model.EdgeDevice;
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeSystem;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;


public final class ConfigurationUtils {

	private final static String SIMPLE_DATE_FORMAT = "yyyyMMddHHmm";

    public static String toJson(Object data){
        return toJson(data, null);
    }
	
    public static String toJson(Object data, Class view){

        ObjectMapper mapper = new ObjectMapper();        
        try {
            if(view != null) {
                mapper.registerModule(new Hibernate4Module());
                return mapper.writerWithView(view).writeValueAsString(data);
            }
            else{
                return mapper.writeValueAsString(data);
            }
     
        } catch (JsonGenerationException e) {    
            return "ERROR: " + e.getMessage();    
        } catch (JsonMappingException e) {
            return "ERROR: " + e.getMessage();    
        } catch (IOException e) {
            return "ERROR: " + e.getMessage();    
        }
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

    public static EdgeSystem cleanCircularRefs(EdgeSystem edge){
        for(EdgeDevice it : edge.getDevices()) {
            it.setEdgeSystem(null);
        }
        for(EdgeEndpoint it : edge.getEndpoints()) {
            it.setEdgeSystem(null);
        }
        return edge;
    }
    
    public static EdgeSystem fillChildrenRefs(EdgeSystem edge){
        for(EdgeDevice it : edge.getDevices()) {
            it.setEdgeSystem(edge);
        }
        for(EdgeEndpoint it : edge.getEndpoints()) {
            it.setEdgeSystem(edge);
        }
        return edge;
    }
}
