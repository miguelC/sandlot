/**
 *======================================================================================
 * InteropAuditSubjectInfo.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropAuditSubjectInfo
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

//Imports

public class SubjectInfo {
    
    private List<AuditSubject> subjects;
    
    public List<AuditSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<AuditSubject> subjects) {
        this.subjects = subjects;
    }
    
    public void addSubject(AuditSubject subject){
        if(subjects == null) subjects = new ArrayList<AuditSubject>();
        subjects.add(subject);
    }

    public String toString(){
        return InteropAuditUtils.jsonFromSubjetInfo(this);
    }
}
