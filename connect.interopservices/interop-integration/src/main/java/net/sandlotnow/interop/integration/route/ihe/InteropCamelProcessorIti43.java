/**
 *======================================================================================
 * InteropCamelProcessorIti43.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropCamelProcessorIti43
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Aug 6, 2015
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

import net.sandlotnow.interop.audit.AuditSubject;
import net.sandlotnow.interop.audit.SubjectInfo;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.integration.route.InteropRouteConstants;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocument;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocumentSet;

public class InteropCamelProcessorIti43 extends InteropCamelProcessorBase implements Processor {

    /***
     * TODO: Implement multiple document retrieves
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        String edgeSystemOID = repositoryUniqueId(exchange);
        exchange = this.doProcessTransactionEdgeSystem(exchange, edgeSystemOID, null, EdgeTransaction.TRANSACTION_ITI43);
        
    }

    /***
     * Return the repository unique id from the retrieve document set meta-data
     * NOTE: Currently supports only one document at a time
     * @param exchange
     * @return
     */
    private String repositoryUniqueId(Exchange exchange) {
        RetrieveDocumentSet retDocSet = exchange.getIn().getBody(RetrieveDocumentSet.class);
        log.debug("Finding repository unique id in " + retDocSet.toString());
        setPatientInfoExchangeHeader(exchange, retDocSet);
        return retDocSet.getDocuments().get(0).getRepositoryUniqueId();
    }

    private void setPatientInfoExchangeHeader(Exchange exchange, RetrieveDocumentSet retDocSet){
        SubjectInfo subjectInfo = new SubjectInfo(); 
        for(RetrieveDocument d : retDocSet.getDocuments()){
            AuditSubject subject = new AuditSubject();
            subject.addId(d.getDocumentUniqueId());
            subject.addId(d.getRepositoryUniqueId());
            subjectInfo.addSubject(subject);
            
        }
        
        exchange.setProperty(InteropRouteConstants.HEADER_CAMEL_EXCHANGE_SUBJECT_INFO, subjectInfo);
    }
}
