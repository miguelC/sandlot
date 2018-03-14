/**
 *======================================================================================
 * RegRepModelExtension.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- RegRepModelExtension
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			May 5, 2015
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
package net.sandlotnow.interop.integration.route.xds

import org.joda.time.DateTime
import org.apache.camel.model.ProcessorDefinition
import org.openehealth.ipf.commons.core.config.ContextFacade;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.ObjectReference
import org.openehealth.ipf.commons.ihe.xds.core.responses.RetrievedDocument
import org.openehealth.ipf.commons.ihe.xds.core.validate.XDSMetaDataException

import javax.activation.DataHandler

import net.sandlotnow.interop.ihe.data.InteropIheDataService;
import net.sandlotnow.interop.integration.xds.ContentUtils;
import net.sandlotnow.interop.integration.route.InteropRouteConstants

class RegRepModelExtension {
     
    static ProcessorDefinition store(ProcessorDefinition self) {
        self.process { dataStore().store(it.in.body.entry) }
    }
    
    static ProcessorDefinition retrieve(ProcessorDefinition self) {
        self.transform { 
            new RetrievedDocument(dataStore().get(
                it.in.body.documentUniqueId), 
                it.in.body, 
                null, 
                null, 
                'text/plain')
        }
    }
        
    static ProcessorDefinition fail(ProcessorDefinition self, message) {
        self.process { throw new XDSMetaDataException(message) }
    }
    
    static ProcessorDefinition updateWithRepositoryData(ProcessorDefinition self) {
        self.process {
            def documentEntry = it.in.body.entry.documentEntry
            def dataHandler = it.in.body.entry.getContent(DataHandler)
            documentEntry.hash = ContentUtils.sha1(dataHandler)
            documentEntry.size = ContentUtils.size(dataHandler)
            documentEntry.repositoryUniqueId = '1.19.6.24.109.42.1'
        }
    }
    
    static ProcessorDefinition splitEntries(ProcessorDefinition self, entriesClosure) {
        self.split { exchange ->
            def body = exchange.in.body
            def entries = entriesClosure(body) 
            entries.collect { entry -> body.clone() + [entry: entry] }
        }    
    }

    static ProcessorDefinition assignUuid(ProcessorDefinition self) {
        self.process {
            def entry = it.in.body.entry
            if (!entry.entryUuid.startsWith('urn:uuid:')) {
                def newEntryUuid = 'urn:uuid:' + UUID.randomUUID()
                it.in.body.uuidMap[entry.entryUuid] = newEntryUuid
                entry.entryUuid = newEntryUuid
            }
        }
    }
    
    static ProcessorDefinition changeAssociationUuids(ProcessorDefinition self) {
        self.process {
            def assoc = it.in.body.entry
            def uuidMap = it.in.body.uuidMap
            def sourceUuid = uuidMap[assoc.sourceUuid]
            if (sourceUuid != null) assoc.sourceUuid = sourceUuid
            def targetUuid = uuidMap[assoc.targetUuid]
            if (targetUuid != null) assoc.targetUuid = targetUuid
        }
    }
    
    static ProcessorDefinition status(ProcessorDefinition self, status) {
        self.process {
            it.in.body.entry.availabilityStatus = status
        }
    }

    // Updates the last update time and ensures that the time is actually changed
    static ProcessorDefinition updateTimeStamp(ProcessorDefinition self) {
        self.process {
            it.in.body.entry.lastUpdateTime = DateTime.now()
        }
    }
    
        // Converts entries to ObjectReferences
    static ProcessorDefinition convertToObjectRefs(ProcessorDefinition self, closure) {
        self.process {
            def entries = closure.call(it.in.body)
            it.in.body.resp.references.addAll(entries.collect { 
                new ObjectReference(it.entryUuid)
            })
            entries.clear()
        }
    }
    
    static ProcessorDefinition processBody(ProcessorDefinition self, closure) {
        self.process {closure(it.in.body)}
    }

    static ProcessorDefinition log(ProcessorDefinition self, log, closure) {
        self.process {log.info(closure.call(it)) }
    }

    static ProcessorDefinition logInfo(ProcessorDefinition self, log, closure) {
        self.process {log.info(closure.call(it)) }
    }

    static ProcessorDefinition logWarn(ProcessorDefinition self, log, closure) {
        self.process {log.warn(closure.call(it)) }
    }

    static ProcessorDefinition logError(ProcessorDefinition self, log, closure) {
        self.process {log.error(closure.call(it)) }
    }

    static ProcessorDefinition logDebug(ProcessorDefinition self, log, closure) {
        self.process {log.debug(closure.call(it)) }
    }
    
    /*
     * Added by sandlot to sample code
     */
        
    static ProcessorDefinition forwardItiTransaction(ProcessorDefinition self, closure){
        self.process{
            def targetRoute = closure.call(it)
            self.to(targetRoute)
        }
    }
    
}
