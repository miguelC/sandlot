/**
 *======================================================================================
 * InteropConfiguration.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropConfiguration
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    This class is used to represent a complete sandlot integration system 
 *                  configuration. It contains concepts defined by the spring framework, 
 *                  apache camel and ipf
 *
 *  				Design Pattern(s):
 *  				 - None
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/
package net.sandlotnow.interop.configuration.model

import com.fasterxml.jackson.core.JsonGenerationException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.Map;

class InteropConfiguration {

	String ipfGlobalContext
	
	List<String> imports = new ArrayList<String>()
	
	Map<String, InteropCamelContext> camelContexts = new HashMap<String, InteropCamelContext>()	
	
	Map<String, InteropCamelRouteContext> routeContexts = new HashMap<String, InteropCamelRouteContext>()
    
    String toString(){
        
       ObjectMapper mapper = new ObjectMapper()        
       try {
           return mapper.writeValueAsString(this)
    
       } catch (JsonGenerationException e) {    
           return base.toString()    
       } catch (JsonMappingException e) {
           return base.toString()    
       } catch (IOException e) {
           return base.toString()    
       }
    }
}
