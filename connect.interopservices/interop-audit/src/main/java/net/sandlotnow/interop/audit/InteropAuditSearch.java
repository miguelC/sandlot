/**
 *======================================================================================
 * InteropAuditSearch.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropAuditSearch
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jan 18, 2016
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

import java.util.List;

//Imports

public class InteropAuditSearch {
     private String startTime;
     private String stopTime;
     InteropAuditStatusFilter statusFilter;
     List<String> transactionTypes;
     
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getStopTime() {
        return stopTime;
    }
    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }
    public InteropAuditStatusFilter getStatusFilter() {
        return statusFilter;
    }
    public void setStatusFilter(InteropAuditStatusFilter statusFilter) {
        this.statusFilter = statusFilter;
    }
    public List<String> getTransactionTypes() {
        return transactionTypes;
    }
    public void setTransactionTypes(List<String> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }
}
