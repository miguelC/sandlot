package net.sandlotnow.interop.integration.route

import org.apache.camel.Exchange
import org.apache.camel.spring.SpringRouteBuilder
import org.openehealth.ipf.modules.hl7.validation.DefaultValidationContext

import ca.uhn.hl7v2.util.StringUtil;

class HL7ADTRoutesBuilder extends InteropRoutesBuilderBase {
		
	String fileExtension = '.hl7'
	
	void configure() {
		StringUtil.validateNotEmpty(routeFromName)
		StringUtil.validateNotEmpty(routeToName)
		
		from(routeFromName)                                    // receive HL7 message
			.unmarshal().hl7()                                       
		    // Validation is not working so it's commented out
			/*.validate().ghl7()
				.profile(lookup(DefaultValidationContext.class))      // validate against custom validation context*/
//			.transmogrify { msg ->
//				msg.PV1[3][2] = ''                                    // clear room nr.
//				msg.PV1[3][3] = ''                                    // clear bed nr.
//				msg.PID[7][1] = msg.PID[7][1].value.substring(0, 8)   // format birth date
//				msg.PID[8]    = msg.PID[8].mapGender()                // map 'gender' code
//				msg                                                   // return result
//			}
//			.setHeader(Exchange.FILE_NAME) {exchange ->               // set filename header to
//				exchange.in.body.MSH[4].value +  fileExtension               // sending facility (MSH[4])
//			}
//			.marshal().ghl7()                                         // convert to external representation
			.to(routeToName)                                 // write external representation to file
		
	}

}
