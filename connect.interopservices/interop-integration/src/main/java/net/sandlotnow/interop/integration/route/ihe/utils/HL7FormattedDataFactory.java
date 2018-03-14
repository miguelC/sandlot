/**
 *======================================================================================
 * HL7FormattedDataFactory.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- HL7FormattedDataFactory
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
package net.sandlotnow.interop.integration.route.ihe.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;


//Imports

public final class HL7FormattedDataFactory {

    public static  DateFormat hl7DateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    public static  DateFormat hl7DateFormat = new SimpleDateFormat("yyyyMMdd");
    
    public static String currentDateTime(){
        return dateTime(new GregorianCalendar(TimeZone.getTimeZone("GMT")));
    }
    
    public static String currentDate(){
        return date(new GregorianCalendar(TimeZone.getTimeZone("GMT")));
    }
      
    public static String dateTime(GregorianCalendar cal){ 
        return hl7DateTimeFormat.format(cal.getTime());
    }
      
    public static String date(GregorianCalendar cal){ 
        return hl7DateFormat.format(cal.getTime());
    }
}
