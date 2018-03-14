/**
 *======================================================================================
 * Iti41PatientIdentityResolverIti45RouteBuilder.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- Iti41PatientIdentityResolverIti45RouteBuilder
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
package net.sandlotnow.interop.integration.processor.ihe

import groovy.xml.XmlUtil
import net.sandlotnow.interop.ihe.model.EdgeTransaction
import net.sandlotnow.interop.integration.route.InteropRouteConstants
import net.sandlotnow.interop.integration.route.InteropOidQualifier
import net.sandlotnow.interop.integration.route.ihe.InteropCamelProcessorBase
import net.sandlotnow.interop.integration.route.ihe.InteropExchangeHeadersHelper
import net.sandlotnow.interop.integration.route.ihe.InteropXdsException
import net.sandlotnow.interop.integration.route.ihe.utils.HL7FormattedDataFactory
import net.sandlotnow.interop.integration.route.ihe.utils.XdsMessageFactory;
import net.sandlotnow.interop.integration.route.ihe.utils.XdsUtils

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable
import org.openehealth.ipf.commons.ihe.xds.core.requests.ProvideAndRegisterDocumentSet
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class Iti41PatientIdentityResolverIti45Processor extends PatientIdentityResolverIti45ProcessorBase implements Processor{
    
        
    @Override
    public void process(Exchange exchange) throws Exception {

        if(iti45Processor == null){
            def regErr = 
                XdsUtils.createRegistryErrorResponseRegistryError("No ITI-45 Processor has been configured for this ITI-41 process");
            exchange.out.setBody(regErr)
            throw new InteropXdsException("No ITI-45 Processor has been configured for this ITI-41 process")
            
        }
        String mpiAssigningAuthorityOid = iti45Processor.getDestinationIdentifier()
        // TODO Auto-generated method stub
        ProvideAndRegisterDocumentSet pnr = exchange.in.getBody(ProvideAndRegisterDocumentSet.class)
        Identifiable localPatientIdentifier = pnr.getDocuments().get(0).getDocumentEntry().getSourcePatientId()
        
        log.info("Processing ITI41 transaction - Resolving local patient identifiers to global patient identifiers in the ${mpiAssigningAuthorityOid} domain")
        
        def localPatientAssigningAuthority = localPatientIdentifier.assigningAuthority.universalId
        def localPatientId = localPatientIdentifier.id
                        
         def globalPatientId = doProcessReturnGlobalId(exchange,
                mpiAssigningAuthorityOid,
                localPatientAssigningAuthority, 
                localPatientId)
        
        if(pnr.getSubmissionSet().getPatientId() == null){
            pnr.getSubmissionSet().setPatientId(XdsMessageFactory.createIdentifiable(globalPatientId, mpiAssigningAuthorityOid))
        }
        else{         
            pnr.getSubmissionSet().getPatientId().getAssigningAuthority().setUniversalId(mpiAssigningAuthorityOid)
            pnr.getSubmissionSet().getPatientId().setId(globalPatientId)
        }
        
        if(pnr.getDocuments().get(0).getDocumentEntry().getPatientId() == null){
            pnr.getDocuments().get(0).getDocumentEntry().setPatientId(XdsMessageFactory.createIdentifiable(globalPatientId, mpiAssigningAuthorityOid))
        }
        else{
            pnr.getDocuments().get(0).getDocumentEntry().getPatientId().getAssigningAuthority().setUniversalId(mpiAssigningAuthorityOid)
            pnr.getDocuments().get(0).getDocumentEntry().getPatientId().setId(globalPatientId)
        }
        
        // Reset headers to ITI41 values
        exchange = InteropExchangeHeadersHelper.fillInteropHeaders(pnr.getSubmissionSet().getSourceId(), null, EdgeTransaction.TRANSACTION_ITI41, false, exchange)
        
        exchange.out.setBody(pnr)
        
    }

}
