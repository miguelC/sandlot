package net.sandlotnow.interop.integration.route

import org.apache.camel.Exchange
import org.apache.camel.spring.SpringRouteBuilder

class SampleRouteBuilder extends SpringRouteBuilder {

    void configure() {
				
        from('direct:input1')
            .transmogrify { it * 2 }                // duplicate the request string
            .setFileHeaderFrom('destination')       // set name of result file to be written (a custom DSL extension)
            .to('file:target/output')               // replace content of file in target/output directory with body of in-message.
		
        from('direct:input2').reverse()
        
        from('direct:deadLetter')
            .setFileHeaderFrom('destination')       // set name of result file to be written (a custom DSL extension)
            .to('file:target/output/dead')               // replace content of file in t
	
    }
    
}