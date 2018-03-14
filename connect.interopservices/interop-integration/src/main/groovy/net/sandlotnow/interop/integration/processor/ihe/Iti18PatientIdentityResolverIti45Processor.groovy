/**
 *======================================================================================
 * Iti18PatientIdentityResolverIti45Processor.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- Iti18PatientIdentityResolverIti45Processor
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 16, 2015
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

import net.sandlotnow.interop.integration.route.ihe.InteropXdsException
import net.sandlotnow.interop.integration.route.ihe.utils.XdsUtils
import net.sandlotnow.interop.integration.route.ihe.InteropExchangeHeadersHelper
import net.sandlotnow.interop.ihe.model.EdgeTransaction
import net.sandlotnow.interop.integration.route.InteropRouteConstants
import net.sandlotnow.interop.integration.route.InteropOidQualifier
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.Query
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.PatientIdBasedStoredQuery
import org.apache.camel.Exchange
import org.apache.camel.Processor

//Imports

class Iti18PatientIdentityResolverIti45Processor extends PatientIdentityResolverIti45ProcessorBase implements Processor{
    
        
    @Override
    public void process(Exchange exchange) throws Exception {

        if(iti45Processor == null){
            def regErr = 
                XdsUtils.createRegistryErrorResponseRegistryError("No ITI-45 Processor has been configured for this ITI-18 process");
            exchange.out.setBody(regErr)
            throw new InteropXdsException("No ITI-45 Processor has been configured for this ITI-18 process")
            
        }
        String mpiAssigningAuthorityOid = iti45Processor.getDestinationIdentifier()

        QueryRegistry regQuery = exchange.in.getBody(QueryRegistry.class);
        Identifiable localPatientIdentifier = regQuery.query.getPatientId()
        
        log.info("Processing ITI-18 transaction - Resolving local patient identifiers to global patient identifiers in the ${mpiAssigningAuthorityOid} domain")
        
        def localPatientAssigningAuthority = localPatientIdentifier.assigningAuthority.universalId
        def localPatientId = localPatientIdentifier.id
                        
         def globalPatientId = doProcessReturnGlobalId(exchange,
                mpiAssigningAuthorityOid,
                localPatientAssigningAuthority, 
                localPatientId)
        
        regQuery.query.getPatientId().getAssigningAuthority().setUniversalId(mpiAssigningAuthorityOid)
        regQuery.query.getPatientId().setId(globalPatientId)
        
        // Reset headers to ITI18 values
        exchange = InteropExchangeHeadersHelper.fillInteropHeaders(mpiAssigningAuthorityOid, null, EdgeTransaction.TRANSACTION_ITI18, false, exchange)
        
        exchange.out.setBody(regQuery)
        
    }


}
