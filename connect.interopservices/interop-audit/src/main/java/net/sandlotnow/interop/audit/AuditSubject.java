/**
 *======================================================================================
 * AuditSubject.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- AuditSubject
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jan 21, 2016
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

//Imports
@JsonInclude(JsonInclude.Include.NON_EMPTY) 
public class AuditSubject {

    private List<String> ids;
    private String name;
    private HashMap<String, String> otherInfo;
    
    public List<String> getIds() {
        return ids;
    }
    public void setIds(List<String> ids) {
        this.ids = ids;
    }
    public void addId(String id){
        if(ids == null) ids = new ArrayList<String>();
        ids.add(id);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public HashMap<String, String> getOtherInfo() {
        return otherInfo;
    }
    public void setOtherInfo(HashMap<String, String> otherInfo) {
        this.otherInfo = otherInfo;
    }
    public void addInfo(String key, String value){
        if(otherInfo == null) otherInfo = new HashMap<String, String>();
        otherInfo.put(key, value);
    }
    public String toString(){
        return InteropAuditUtils.jsonFromSubjet(this);
    }
}
