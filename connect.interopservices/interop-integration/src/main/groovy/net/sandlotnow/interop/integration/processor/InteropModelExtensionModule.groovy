/**
 *======================================================================================
 * SampleModelExtensionModule.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- SampleModelExtensionModule
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    This class is a sample camel processor extension where methods can be
 *                  defined to be called within camel DSL or route definitions. To be able 
 *                  to use these DSL extensions the processor class must be added to the 
 *                  property "extensionClasses" in the file: 
 *                  ~/META-INF/services/org.codehaus.groovy.runtim.ExtensionModule
 *
 *  				Design Pattern(s):
 *  				 - None
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/
package net.sandlotnow.interop.integration.processor

import net.sandlotnow.interop.integration.route.InteropRouteConstants

import net.sandlotnow.interop.ihe.data.InteropIheDataService

import org.apache.camel.model.ProcessorDefinition
import org.apache.camel.Exchange
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.openehealth.ipf.commons.core.config.ContextFacade;
import org.openehealth.ipf.commons.ihe.xds.core.validate.ValidationMessage
import org.openehealth.ipf.commons.ihe.xds.core.validate.XDSMetaDataException
class InteropModelExtensionModule {
      
     static ProcessorDefinition reverse(ProcessorDefinition self) {
         self.transmogrify { it.reverse() } 
     }
	 
	 static ProcessorDefinition setFileHeaderFrom(ProcessorDefinition self, String sourceHeader) {
		 self.setHeader(Exchange.FILE_NAME) { exchange ->
			 def destination = exchange.in.headers."$sourceHeader"
			 destination ? "${destination}.txt" : 'default.txt'
		 }
	 }
     
}
