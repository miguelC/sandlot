/**
 *======================================================================================
 * InteropIntegrationRouteBuilder.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropIntegrationRouteBuilder
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jun 19, 2015
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
package net.sandlotnow.interop.integration.route;

import net.sandlotnow.interop.integration.service.InteropIntegrationService;

//Imports
import org.apache.camel.builder.RouteBuilder;

public class InteropIntegrationRouteBuilder extends RouteBuilder {

    private String integrationSvcUri = "cxfrs:?resourceClasses=" + InteropIntegrationService.class.getName() + "&bindingStyle=SimpleConsumer";
    
    
    @Override
    public void configure() throws Exception {
          from(integrationSvcUri)
          //from("direct:integrationRouteBuilder")
                .choice()
                    .when(header("operationName").isEqualTo("adtA01Feed"))
                      .to("direct:inputAdtA01");          

          from("direct:interopIti18")
              .to("iti18Processor");
    }
}
