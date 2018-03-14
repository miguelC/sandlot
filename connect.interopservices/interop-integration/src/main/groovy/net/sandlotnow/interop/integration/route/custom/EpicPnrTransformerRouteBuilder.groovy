/**
 *======================================================================================
 * EpicPnrTransformerRouteBuilder.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- EpicPnrTransformerRouteBuilder
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 30, 2015
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
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable
import org.openehealth.ipf.commons.ihe.xds.core.requests.ProvideAndRegisterDocumentSet

import java.util.Map

class EpicPnrTransformerRouteBuilder extends SpringRouteBuilder {
    
    String endpointName = "epicPnrTransformer"
    
    def documentSourcesMap = [:]
    
    void configure() {
        
        from("direct:" + endpointName)
            .transform { 
                ProvideAndRegisterDocumentSet pnr = it.in.getBody(ProvideAndRegisterDocumentSet.class)
                def docSource = pnr.getSubmissionSet().getSourceId()
                if(documentSourcesMap != null && documentSourcesMap[docSource]){
                    log.info("Setting Epic Document Source in PNR Message to ${docSource}.5.2.5")
                    pnr.getSubmissionSet().setSourceId(documentSourcesMap[docSource])
                }
                
                pnr.getDocuments().get(0).getDocumentEntry().getConfidentialityCodes().get(0).setSchemeName('HL7 Confidentiality Codes')
                
                pnr.getDocuments().get(0).getDocumentEntry().getHealthcareFacilityTypeCode().setCode('261Q00000X')
                pnr.getDocuments().get(0).getDocumentEntry().getHealthcareFacilityTypeCode().setSchemeName('Health Care Provider Taxonomy')
                
                pnr.getDocuments().get(0).getDocumentEntry().getPracticeSettingCode().setCode('261Q00000X')
                pnr.getDocuments().get(0).getDocumentEntry().getPracticeSettingCode().setSchemeName('Health Care Provider Taxonomy')
                
                pnr.getDocuments().get(0).getDocumentEntry().getFormatCode().setCode('CCDA')
                pnr.getDocuments().get(0).getDocumentEntry().getFormatCode().setSchemeName('2.16.840.1.113883.3.37.4.1.9.101')
                
                pnr.getSubmissionSet().getContentTypeCode().setCode('34133-9')
                pnr.getSubmissionSet().getContentTypeCode().setSchemeName('2.16.840.1.113883.6.1')
                
                return pnr
            }
    }

}
