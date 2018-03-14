/**
 *======================================================================================
 * AuditService.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- AuditService
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			May 27, 2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:  	This is a service facade into the InteropAuditDaoImpl used by JPA 
 *                  for CRUD operations 
 *  				Design Pattern(s):
 *  				 - Facade
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.interop.audit;

//Imports
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InteropAuditService {
    
    Logger log = LoggerFactory.getLogger(InteropAuditService.class);

    /**
     * The serializerMap property defines how certain types of objects are serialized
     * by means of implementations of InteropAuditMessageSerializer
     */
    @Autowired
    private InteropAuditSerializerMap serializerMap;

    @Autowired
    private InteropAuditDaoImpl auditDao;
 
    @Transactional
    public InteropAudit add(InteropAudit audit) {
    	return auditDao.saveOrUpdate(audit);
    }
     
    @Transactional
    public void addAll(Collection<InteropAudit> audits) {
        for (InteropAudit audit : audits) {
        	audit = auditDao.saveOrUpdate(audit);
        }
    }
 
    @Transactional(readOnly = true)
    public List<InteropAudit> listAll() {
        return auditDao.findAll();
 
    }
 
    @Transactional(readOnly = true)
    public InteropAudit findAuditById(long id) {
        return auditDao.findAuditById(id);
 
    }
 
    @Transactional(readOnly = true)
    public List<InteropAudit> findAuditsByExchangeId(String exchangeId) {
        return auditDao.findAuditsByExchangeId(exchangeId);
 
    }
 
    @Transactional(readOnly = true)
    public InteropAuditMessage findAuditMessageById(long id) {
        return auditDao.findAuditMessageById(id);
 
    }
 
    @Transactional(readOnly = true)
    public List<InteropAuditMessage> getAuditMessages(long auditId) {
        return auditDao.getAuditMessages(auditId);
 
    }

    @Transactional
    public void delete(InteropAudit audit) {
    	auditDao.delete(audit);
    }
    
    @Transactional(readOnly = true)
    public List<InteropAudit> findByTimespan(String startTime, String stopTime) {
    	return auditDao.findAuditsByTimespan(startTime, stopTime);
    }

    
    @Transactional(readOnly = true)
    public List<InteropAudit> findByFilters(InteropAuditSearch searchFilters) {
        return auditDao.findAuditsByFilters(searchFilters);
    }
    
    /*
     * Getters and Setters
     */
    
    public InteropAuditSerializerMap getSerializerMap() {
        return serializerMap;
    }

    public void setSerializerMap(InteropAuditSerializerMap serializerMap) {
        this.serializerMap = serializerMap;
    }
}
