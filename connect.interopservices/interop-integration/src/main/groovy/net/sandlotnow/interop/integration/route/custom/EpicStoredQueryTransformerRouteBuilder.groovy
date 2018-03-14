/**
 *======================================================================================
 * EpicStoredQueryTransformerRouteBuilder.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- EpicStoredQueryTransformerRouteBuilder
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Dec 15, 2015
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
package net.sandlotnow.interop.integration.route.custom

//Imports
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.spring.SpringRouteBuilder
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.PatientIdBasedStoredQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.Query;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.EbXML30Converters;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsQuery
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Code
import org.openehealth.ipf.commons.ihe.xds.core.metadata.LocalizedString

class EpicStoredQueryTransformerRouteBuilder extends SpringRouteBuilder {
    
    String endpointName = "epicStoredQueryTransformer"
    
    String typeCodeFilterCode = "MedicalSummary"
    String typeCodeFilterDisplayName = ""
    String typeCodeFilterSchemeName = "ICW Custom Document Type Codes"
    
    void configure() {
        
        from("direct:" + endpointName)
            .transform { 
                QueryRegistry regQuery = it.in.getBody(QueryRegistry.class);
                log.info("Adding format type code filter to Epic registry stored query")
                Query query = regQuery.getQuery();
                if(query instanceof FindDocumentsQuery){   
                    FindDocumentsQuery findQuery = (FindDocumentsQuery) query
                    if(findQuery.typeCodes == null || findQuery.typeCodes.size() <= 0){
                        if(findQuery.typeCodes == null){
                            findQuery.typeCodes = new ArrayList<Code>()
                        }
                        Code typeCodeODCCDA = new Code()
                        typeCodeODCCDA.setCode(typeCodeFilterCode)
                        typeCodeODCCDA.setDisplayName(new LocalizedString(typeCodeFilterDisplayName))
                        typeCodeODCCDA.setSchemeName(typeCodeFilterSchemeName)
                        findQuery.typeCodes.add(typeCodeODCCDA)
                    }
                }
                return regQuery
            }
    }

}
