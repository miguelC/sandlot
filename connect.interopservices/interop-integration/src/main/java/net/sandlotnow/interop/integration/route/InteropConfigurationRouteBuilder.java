/**
 *======================================================================================
 * SandlotConfigurationRouteBuilder.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotConfigurationRouteBuilder
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    A camel route builder for the sandlot configuration service
 *                 
 *  				Design Pattern(s):
 *  				 - None
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/
package net.sandlotnow.interop.integration.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class InteropConfigurationRouteBuilder extends RouteBuilder {
	
	   /**
	    * TODO: Move this to an XML defined route
	    * 
        <camel:route>
           <camel:from uri="cxfrs:http://0.0.0.0:7798?resourceClasses=net.sandlotnow.configuration.service.SandlotConfigurationService;bindingStyle=SimpleConsumer"/> 
           <camel:choice>
               <camel:when>
                   <camel:xpath>$operationName = 'getConfigs'</camel:xpath>
                   <camel:to uri="direct:parseConfig"/>
               </camel:when>
               <camel:otherwise>
                   <camel:to uri="direct:configurationRouteBuilder"/>
               </camel:otherwise>
           </camel:choice>
        </camel:route>
	    */
	   @Override
	   public void configure() throws Exception {
		     from("cxfrs:http://0.0.0.0:7798?resourceClasses=net.sandlotnow.configuration.service.SandlotConfigurationService&bindingStyle=SimpleConsumer")
	         //from("direct:configurationRouteBuilder")
	               .choice()
	                   .when(header("operationName").isEqualTo("getConfig"))
	                     .to("sql:SELECT * from company where id = :#id")
	                   .when(header("operationName").isEqualTo("createConfig"))
	                     .to("sql:INSERT INTO company(name, symbol) VALUES (:#name, :#symbol)")
	                   .when(header("operationName").isEqualTo("getConfigs"))
	                     .to("direct:parseConfig")
	                   .when(header("operationName").isEqualTo("updateConfig"))
	                     .to("sql:UPDATE company SET name = :#name, symbol = :#symbol where id = :#id")
	                   .when(header("operationName").isEqualTo("deleteConfig"))
	                    .to("sql:DELETE FROM company where id = :#id")
	               .end()
	               .marshal().json(JsonLibrary.Jackson);
	   }

}
