/**
 *======================================================================================
 * SpringConfigurationParser.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- SpringConfigurationParser
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    This class is used as a spring bean by the application to assist in 
 *                  parsing spring and camel configuration files into configuration 
 *                  objects meant to be returned and managed by a set of REST services
 *                  called to make changes to spring and camel configurations.
 *
 *  				Design Pattern(s):
 *  				 - None
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/
package net.sandlotnow.interop.configuration


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import net.sandlotnow.interop.configuration.model.*;

import groovy.util.logging.Slf4j


@Slf4j
class SpringConfigurationParser {

	String springContextConfigFilePath = "context.xml"
	
	/***
	 * Parses the {@link #springContextConfigFilePath} into a sandlot configuration
	 * @return A sandlot configuration object obtained from {@link #springContextConfigFilePath}
	 */
	InteropConfiguration parseXmlToJson(){		
		InteropConfiguration sandlotConfig = readConfigFile(springContextConfigFilePath)
	}
	
	/***
	 * Method that reads a given config file and creates a new sandlot configuration object provided
	 * @param configFileName file to parse
	 * @return A sandlot configuration object obtained from the file
	 */
	InteropConfiguration readConfigFile(String configFileName){
        InteropConfiguration sandlotConfig = new InteropConfiguration()
		readConfigFile(configFileName, sandlotConfig)
		sandlotConfig
	}
	/***
	 * Method that reads a given config file and updates the sandlot configuration object provided
	 * @param configFileName file to parse
	 * @param sandlotConfig sandlot configuration that is to be updated with values from the file
	 * @return updated sandlot configuration
	 */
	InteropConfiguration readConfigFile(String configFileName, InteropConfiguration sandlotConfig){
		
		log.debug("Reading file " + configFileName)
		
        //TODO: Revisit this later to decide if we want to parse files in META-INF within Jars .. prob not
        if(configFileName.startsWith("classpath:")){
            return sandlotConfig;
        }
        
		Resource file = new ClassPathResource(configFileName)
		String fileName = file.getURI().toString()
		
		def configCtx = new XmlSlurper().parse(fileName)
		
		//First we process the camel context
		configCtx.camelContext.each { camelCtx ->
			InteropCamelContext sandCamelCtx = new InteropCamelContext()			
			sandCamelCtx.id = camelCtx.@id.text()
		    log.info("Adding Camel Context " + sandCamelCtx.id)
			sandCamelCtx.routeBuildersPackage = camelCtx.package.text()
			// Add the references to route contexts
			camelCtx.routeContextRef.each { routeCtxRef ->
				sandCamelCtx.routeContextRefs.add(routeCtxRef.@ref.text())
		        log.info("Adding Camel Route Context Ref " + routeCtxRef.@ref.text())
			}
			
			sandlotConfig.camelContexts.put(sandCamelCtx.id, sandCamelCtx)
		}
		
		// Add all camel route contexts to the config
		configCtx.routeContext.each { routeCtx ->
			InteropCamelRouteContext sandRouteCtx = new InteropCamelRouteContext()
			sandRouteCtx.id = routeCtx.@id.text()
			log.info("Reading route context " + sandRouteCtx.id)
			// Add all routes to the context
			routeCtx.route.each { route ->
				InteropCamelRoute sandRoute = new InteropCamelRoute()
				sandRoute.id = route.@id.text()
				sandRoute.contextId = sandRouteCtx.id
				sandRoute.from = route.from.@uri.text()
				//TODO: Add all camel custom components and bean references
				
				sandRouteCtx.routes.put(sandRoute.id, sandRoute)
			}
			sandlotConfig.routeContexts.put(sandRouteCtx.id, sandRouteCtx)
					
		}
		
		// TODO: Add all Spring bean definitions
		
		configCtx.import.each { imp ->
			String importFile = imp.@resource.text()
			sandlotConfig.imports.add(importFile)
			sandlotConfig = readConfigFile(importFile, sandlotConfig)
		}
		
		sandlotConfig
	}
}
