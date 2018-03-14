/**
 *======================================================================================
 * RegistryResponseFailureEventProcessor.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- RegistryResponseFailureEventProcessor
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Sep 28, 2015
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

//Imports
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType
import net.sandlotnow.interop.integration.route.ihe.InteropXdsException
import net.sandlotnow.interop.integration.route.ihe.utils.XdsConstants

class RegistryResponseFailureEventProcessor implements Processor{
    
    @Override
    public void process(Exchange exchange) throws Exception {
        // TODO Auto-generated method stub
        def registryResponse = exchange.in.getBody(RegistryResponseType.class)
        if(registryResponse.status != XdsConstants.XDS_B_STATUS_SUCCESS){
            exchange.out.setBody(exchange.in.getBody(RegistryResponseType.class))
            throw new InteropXdsException(registryResponse.getRegistryErrorList().getRegistryError().get(0).getCodeContext())
        }
    }
    

}
