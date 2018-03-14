package net.sandlotnow.interop.integration.route.custom

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.processor.aggregate.AggregationStrategy
import org.apache.camel.spring.SpringRouteBuilder

class OHPDeadLetterRouteBuilder extends SpringRouteBuilder {
		
    String endpointName = "ohpDeadLetter"
	String errorDirectory
    String backupDirectory
    OHPFailureEventMessageAggregationStrategy aggregationStrategy
	
	void configure() {
		
		from("direct:" + endpointName)     
            //Pollenrich is not working so I am just using an inline processor                             
			//.pollEnrich ("file://${backupDirectory}?fileName=test.xml", aggregationStrategy)
            .process(new Processor() {
                 @Override
                 public void process(Exchange e) {
                     def fileName = e.getIn().getHeader(Exchange.FILE_NAME)
                     String fileContents = "<FileName>Original message file not found</FileName>"
                     if(fileName){
                         fileContents = "<FileName>${fileName}</FileName>"
//                         def filePath = "${backupDirectory}/" + fileName
//                         try{
//                             fileContents = new File(filePath).getText('UTF-8')
//                         }catch(Exception ex){}
                         //boolean fileSuccessfullyDeleted =  new File(filePath).delete() 
                     }
                     e = aggregationStrategy.aggregateContent(fileContents, e)
                 }
              })
            //.log(log){ exchange -> "ERROR MESSAGE ***** " + exchange.in.body.toString() }
			.setHeader(Exchange.FILE_NAME) {exchange ->
                 def destination = exchange.in.headers."CamelFileName"
                 destination ? "ERR_${destination}" : "ERR_SVC_${exchange.exchangeId}.xml"         
			}
            .to("file://${errorDirectory}")                           
		
	}

}
