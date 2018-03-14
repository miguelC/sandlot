/**
 *======================================================================================
 * InteropAuditServiceRest.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropAuditServiceRest
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Aug 31, 2015
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
package net.sandlotnow.interop.audit;

//Imports
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Component
@Path("/auditService")
public class InteropAuditServiceRest {
    
    Logger log = LoggerFactory.getLogger(InteropAuditServiceRest.class);

    @Autowired
    private InteropAuditService auditService;
    

    @GET
    @Path("getall")
    @Produces("application/json")
    public String getAllAudits() {
        List<InteropAudit> audits = auditService.listAll();
        return InteropAuditUtils.jsonFromAuditList(audits);
    }

    @GET
    @Path("audit/{id}")
    @Produces("application/json")
    public String getAudit(@PathParam("id") long id) {
        log.info("Audits Service called: audit/{id}. Retrieving audits by id " + id);
        InteropAudit audit = auditService.findAuditById(id);
        return InteropAuditUtils.jsonFromAudit(audit);
    }

    @GET
    @Path("audits/exchange/{exchangeId}")
    @Produces("application/json")
    public String getExchangeAudits(@PathParam("exchangeId") String exchangeId) {
        log.info("Audits Service called: audits/exchange/{exchangeId}. Retrieving exchange audits by exchange id " + exchangeId);
        List<InteropAudit> audits = auditService.findAuditsByExchangeId(exchangeId);
        log.info("Found " + audits.size() + " audits for exchange " + exchangeId);
        return InteropAuditUtils.jsonFromAuditList(audits);
    }


    @GET
    @Path("audit/messages/audit/{id}")
    @Produces("application/json")
    public String getAuditMessages(@PathParam("id") long id) {
        log.info("Audits Service called: audit/messages/audit/{id}. Retrieving exchange audit messages by audit id " + id);
        List<InteropAuditMessage> auditMessages = auditService.getAuditMessages(id);
        return InteropAuditUtils.jsonFromAuditMessageList(auditMessages);
    }

    @GET
    @Path("audit/message/{id}")
    @Produces("application/json")
    public String getAuditMessage(@PathParam("id") long id) {
        log.info("Audits Service called: audit/message/{id}. Retrieving exchange audit message by id " + id);
        InteropAuditMessage audit = auditService.findAuditMessageById(id);
        return InteropAuditUtils.jsonFromAuditMessage(audit);
    }
    
    @GET
    @Path("audit/startTime/{startTime}/stopTime/{stopTime}")
    @Produces("application/json")
    public String getAuditsByTimespan(
    		@PathParam("startTime") String startTime, 
			@PathParam("stopTime") String stopTime) {
        log.info("Audits Service called: audit/startTime/{startTime}/stopTime/{stopTime}. Retrieving exchange audit message by timespan " + startTime + " to " + stopTime);
    	List<InteropAudit> audits = auditService.findByTimespan(startTime, stopTime);
        return InteropAuditUtils.jsonFromAuditList(audits);
    }

    
    @GET
    @Path("audit/startTime/{startTime}/stopTime/{stopTime}/status/{status}")
    @Produces("application/json")
    public String getAuditsByFilters(
            @PathParam("startTime") String startTime, 
            @PathParam("stopTime") String stopTime,
            @PathParam("status") String status) {
        log.info("Audits Service called: audit/startTime/{startTime}/stopTime/{stopTime}/status/{status}. Retrieving exchange audit message by timespan " + startTime + " to " + stopTime);
        InteropAuditStatusFilter statusFilter = InteropAuditStatusFilter.valueOf(status);
        InteropAuditSearch search = new InteropAuditSearch();
        search.setStartTime(startTime);
        search.setStopTime(stopTime);
        search.setStatusFilter(statusFilter);
        List<InteropAudit> audits = auditService.findByFilters(search);
        return InteropAuditUtils.jsonFromAuditList(audits);
    }

    @POST
    @Path("audit/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAuditsBySearch(String searchJson){
        InteropAuditSearch search = InteropAuditUtils.auditSearchFromJson(searchJson);
        log.info("Audits Service called: audit/search. Retrieving exchange audit message by search " + searchJson);
        
        List<InteropAudit> audits = auditService.findByFilters(search);
        log.info("Audits Service: audit/search. Retrieved " + audits.size() + " records.");
        return InteropAuditUtils.jsonFromAuditList(audits);
    }
}
