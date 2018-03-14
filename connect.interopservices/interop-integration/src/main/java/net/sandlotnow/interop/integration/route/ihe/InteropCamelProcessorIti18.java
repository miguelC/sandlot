/**
 *======================================================================================
 * InteropCamelProcessorIti18.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropCamelProcessorIti18
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jul 17, 2015
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
package net.sandlotnow.interop.integration.route.ihe;
//Imports

import lombok.extern.slf4j.Slf4j;
import net.sandlotnow.interop.audit.AuditSubject;
import net.sandlotnow.interop.audit.SubjectInfo;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.integration.route.InteropRouteConstants;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.Query;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.PatientIdBasedStoredQuery;

@Slf4j
public class InteropCamelProcessorIti18 extends InteropCamelProcessorBase implements Processor{
    
    private String repositoryUniqueId;
    
    public void setRepositoryUniqueId(String id){
        repositoryUniqueId = id;
    }
    
    public void process(Exchange exchange) throws Exception {
        String edgeSystemOID = assigningAuthority(exchange);
        this.doProcessTransactionEdgeSystem(exchange, edgeSystemOID, repositoryUniqueId, EdgeTransaction.TRANSACTION_ITI18);
     }
    

    /***
     * Gets the assigning authority value out of the patient identifier in a registry stored query message
     * @param exchange
     * @return
     */
    public String assigningAuthority(Exchange exchange) { 
        QueryRegistry regQuery = exchange.getIn().getBody(QueryRegistry.class);
        log.debug("Finding assigning authority in " + regQuery.toString());
        Query query = regQuery.getQuery();
        if(query instanceof PatientIdBasedStoredQuery){ 
            PatientIdBasedStoredQuery pidQuery = (PatientIdBasedStoredQuery)query;
            setPatientInfoExchangeHeader(exchange, pidQuery);
            String assigningAuthority = pidQuery.getPatientId().getAssigningAuthority().getUniversalId();
            log.debug("Assigning Authority OID : " + assigningAuthority);
            return  assigningAuthority;
        }
        log.debug("Message is not PatientIdBasedStoredQuery");
        return null;
    }

    private void setPatientInfoExchangeHeader(Exchange exchange, PatientIdBasedStoredQuery query){
        SubjectInfo subjectInfo = new SubjectInfo(); 
        AuditSubject subject = new AuditSubject();
        subject.addId(query.getPatientId().toString());
        subjectInfo.addSubject(subject);
        
        exchange.setProperty(InteropRouteConstants.HEADER_CAMEL_EXCHANGE_SUBJECT_INFO, subjectInfo);
    }
}
