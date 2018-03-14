/**
 *======================================================================================
 * SandlotAuditTest.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotAuditTest
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jun 17, 2015
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
package net.sandlotnow.interop.audit.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sandlotnow.interop.audit.InteropAuditSearch;
import net.sandlotnow.interop.audit.InteropAuditService;
import net.sandlotnow.interop.audit.InteropAudit;
import net.sandlotnow.interop.audit.InteropAuditStatusFilter;
import net.sandlotnow.interop.audit.InteropAuditUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = { "/context.xml" })
public class InteropAuditTest {

    private static final Logger log = LoggerFactory.getLogger(InteropAuditTest.class);
    
    @Autowired
    private InteropAuditService interopAuditService;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    //@Test
    public void testQueryAudit() throws Exception {

        List<InteropAudit> audits = interopAuditService.listAll();
    }
    
    @Test
    public void testQueryAuditTimespan() throws Exception {
    	List<InteropAudit> audits = interopAuditService.findByTimespan("201601011000", "201601181000");
    	log.info("---- BY TIMESTAMP ---- " + audits.size() + " Records ---");
        for(InteropAudit audit: audits){
            log.info("Audit " + audit.getExchangeId() + " , TX = " + audit.getTransactionType()  + " , Failed = " + audit.isFailed());
        }
    }
    
    @Test
    public void testQueryAuditByFilters() throws Exception {
        
        InteropAuditSearch search = new InteropAuditSearch();
        search.setStartTime("201601011000");
        search.setStopTime("201601181000");
        search.setStatusFilter(InteropAuditStatusFilter.FailedOnly);
        
        List<InteropAudit> audits = interopAuditService.findByFilters(search);
        log.info("---- BY FILTERS ----" + audits.size() + " Failed Records ---");
        for(InteropAudit audit: audits){
            log.info("Audit " + InteropAuditUtils.jsonFromAudit(audit));
        } 
        search.setStatusFilter(InteropAuditStatusFilter.All);
        List<String> txTypes = new ArrayList<String>();
        txTypes.add("ITI-41");
        txTypes.add("ITI-47");
        search.setTransactionTypes(txTypes);
        audits = interopAuditService.findByFilters(search);
        log.info("---- BY FILTERS ----" + audits.size() + " ITI-41 and ITI-47 Records ---");
        for(InteropAudit audit: audits){
            log.info("Audit " + audit.getExchangeId() + " , TX = " + audit.getTransactionType()  + " , Failed = " + audit.isFailed());
        }
    }
}
