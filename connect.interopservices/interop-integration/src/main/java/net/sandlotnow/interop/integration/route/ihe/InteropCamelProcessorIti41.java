/**
 *======================================================================================
 * InteropCamelProcessorIti41.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropCamelProcessorIti41
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
import org.apache.commons.lang3.StringUtils;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Document;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable;
import org.openehealth.ipf.commons.ihe.xds.core.requests.ProvideAndRegisterDocumentSet;


public class InteropCamelProcessorIti41 extends InteropCamelProcessorBase implements Processor{
        
    @Override
    public void process(Exchange exchange) throws Exception {

        String edgeSystemOID = submissionSetSourceId(exchange);
        exchange = this.doProcessTransactionEdgeSystem(exchange, edgeSystemOID, null, EdgeTransaction.TRANSACTION_ITI41);
        
    }

    private String submissionSetSourceId(Exchange exchange) {
        ProvideAndRegisterDocumentSet pnr = exchange.getIn().getBody(ProvideAndRegisterDocumentSet.class);
        setPatientInfoExchangeHeader(exchange, pnr);
        log.info("Finding repository unique id in " + pnr.toString());
        return pnr.getSubmissionSet().getSourceId();
    }
    
    private void setPatientInfoExchangeHeader(Exchange exchange, ProvideAndRegisterDocumentSet pnr){
        SubjectInfo subjectInfo = new SubjectInfo(); 
        
        for(Document d : pnr.getDocuments()){
            AuditSubject subject = new AuditSubject();
            if(d.getDocumentEntry().getSourcePatientId() != null){
                subject.addId(d.getDocumentEntry().getSourcePatientId().toString());
            }
            if(d.getDocumentEntry().getSourcePatientInfo() != null){
                subject.setName(d.getDocumentEntry().getSourcePatientInfo().getName().toString());
                for(Identifiable id : d.getDocumentEntry().getSourcePatientInfo().getIds()){
                    subject.addId(id.toString());
                }
            }
            subjectInfo.addSubject(subject);
        }

        exchange.setProperty(InteropRouteConstants.HEADER_CAMEL_EXCHANGE_SUBJECT_INFO, subjectInfo);
    }

}
