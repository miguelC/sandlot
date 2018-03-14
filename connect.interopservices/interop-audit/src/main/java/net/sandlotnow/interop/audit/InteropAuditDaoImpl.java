/**
 *======================================================================================
 * AuditDaoImpl.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- AuditDaoImpl
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			May 27, 2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:  	This class is intended to be the main point of access to all the 
 *                  Audits data for storing and querying.
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.interop.audit;

//Imports
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InteropAuditDaoImpl {
    
    Logger log = LoggerFactory.getLogger(InteropAuditDaoImpl.class);
    
    @PersistenceContext(unitName = "sandlotAuditPersistence")
    private EntityManager em;
        
    /**
     * Store a new audit or save changes to an audit record.
     * Should cascade to audit messages.
     * @param audit
     * @return
     */
    public InteropAudit saveOrUpdate(InteropAudit audit) {
        return em.merge(audit);
    }
 
    /**
     * Retrieve all audits
     * @return
     */
    public List<InteropAudit> findAll() {
        return em.createQuery("SELECT audit FROM InteropAudit audit").getResultList();
    }

    /**
     * Get an audit record by its primary key id
     * @param id
     * @return
     */
    public InteropAudit findAuditById(long id) {
        try{
            return em.find(InteropAudit.class, id); 
        }
        catch(NoResultException nre){
            log.error(nre.getMessage(), nre);
            return null;
        }
    }

    /**
     * Find audits that are relevant to a particular exchange id
     * @param transactionId
     * @return
     */
    public List<InteropAudit> findAuditsByExchangeId(String exchangeId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<InteropAudit> criteria = builder.createQuery( InteropAudit.class );
        Root<InteropAudit> audit = criteria.from( InteropAudit.class );
        criteria.select( audit );
        criteria.where( builder.equal(audit.get( InteropAudit_.exchangeId ), exchangeId ) );
        try{
            return em.createQuery( criteria ).getResultList(); 
        }
        catch(NoResultException nre){
            log.error(nre.getMessage(), nre);
            return null;
        }
    }

    /**
     * Get an audit message record by its primary key id
     * @param id
     * @return
     */
    public InteropAuditMessage findAuditMessageById(long id) {
        try{
            return em.find(InteropAuditMessage.class, id); 
        }
        catch(NoResultException nre){
            log.error(nre.getMessage(), nre);
            return null;
        }
    }
    
    /**
     * Get all messages related to an audit
     * @param transactionId
     * @return
     */
    public List<InteropAuditMessage> getAuditMessages(long auditId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<InteropAuditMessage> criteria = builder.createQuery( InteropAuditMessage.class );
        Root<InteropAuditMessage> auditMessage = criteria.from( InteropAuditMessage.class );
        criteria.select( auditMessage );
        criteria.where( builder.equal(auditMessage.get( InteropAuditMessage_.audit), auditId ) );
        try{
            return em.createQuery( criteria ).getResultList(); 
        }
        catch(NoResultException nre){
            log.error(nre.getMessage(), nre);
            return null;
        }
    }
    
    /**
     * This is a placeholder method. Should not be used unless cleanups are necessary.
     * @param audit
     */
    public void delete(InteropAudit audit) {
        em.remove(audit);
    }

	/**
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	public List<InteropAudit> findAuditsByTimespan(String startTimeStr,
			String stopTimeStr) {
	    InteropAuditSearch search = new InteropAuditSearch();
	    search.setStartTime(startTimeStr);
	    search.setStopTime(stopTimeStr);
		return findAuditsByFilters(search);
	}

    public List<InteropAudit> findAuditsByFilters(InteropAuditSearch searchFilters) {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<InteropAudit> criteria = builder.createQuery( InteropAudit.class );
        Root<InteropAudit> audit = criteria.from( InteropAudit.class );
        criteria.select( audit );
        
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.HOUR, -5);
        Date fiveHoursBack = cal.getTime();
        
        Date startDate = StringUtils.isEmpty(searchFilters.getStartTime()) ? fiveHoursBack :InteropAuditUtils.getDateFromStr(searchFilters.getStartTime());
        Date stopDate = StringUtils.isEmpty(searchFilters.getStopTime()) ? currentDate :InteropAuditUtils.getDateFromStr(searchFilters.getStopTime());
        
        Subquery<Long> subQuery = criteria.subquery(Long.class);
        //CriteriaQuery<Date> subQuery = builder.createQuery(Date.class);
        
        if (startDate != null && stopDate != null)
        {
            //criteria.where( builder.between(audit.get( InteropAudit_.timeStamp ), startDate, stopDate) );
            
            Root<InteropAudit> subAudit = subQuery.from( InteropAudit.class );
            subQuery.groupBy(subAudit.get(InteropAudit_.exchangeId)); 
            subQuery.where( builder.between(subAudit.get( InteropAudit_.timeStamp ), startDate, stopDate) );
            subQuery.select(builder.greatest(subAudit.get(InteropAudit_.id)));
            Predicate filtersRestriction = null;
            if(searchFilters.getStatusFilter() == null || searchFilters.getStatusFilter() == InteropAuditStatusFilter.All){
            
                if(searchFilters.getTransactionTypes() == null || searchFilters.getTransactionTypes().isEmpty()){
                    filtersRestriction =  builder.in(audit.get( InteropAudit_.id )).value(subQuery);
                }
                else {

                    Expression<String> txExpr =  audit.get(InteropAudit_.transactionType);
                    filtersRestriction = builder.and(
                            builder.in(audit.get( InteropAudit_.id )).value(subQuery),
                            txExpr.in( searchFilters.getTransactionTypes() )
                        );
                }
            }
            else if(searchFilters.getStatusFilter() == InteropAuditStatusFilter.FailedOnly){
                if(searchFilters.getTransactionTypes() == null || searchFilters.getTransactionTypes().isEmpty()){
                    filtersRestriction = builder.and(
                            builder.in(audit.get( InteropAudit_.id )).value(subQuery),
                            builder.equal( audit.get(InteropAudit_.failed), true )
                        );                
                }
                else{
                    Expression<String> txExpr =  audit.get(InteropAudit_.transactionType);
                    filtersRestriction = builder.and(
                            builder.in(audit.get( InteropAudit_.id )).value(subQuery),
                            txExpr.in( searchFilters.getTransactionTypes() ),
                            builder.equal( audit.get(InteropAudit_.failed), true )
                        );
                }
            }
            else if(searchFilters.getStatusFilter() == InteropAuditStatusFilter.SucceededOnly){
                if(searchFilters.getTransactionTypes() == null || searchFilters.getTransactionTypes().isEmpty()){
                    filtersRestriction = builder.and(
                            builder.in(audit.get( InteropAudit_.id )).value(subQuery),
                            builder.equal( audit.get(InteropAudit_.failed), false )
                        );                
                }
                else{
                    Expression<String> txExpr =  audit.get(InteropAudit_.transactionType);
                    filtersRestriction = builder.and(
                            builder.in(audit.get( InteropAudit_.id )).value(subQuery),
                            txExpr.in( searchFilters.getTransactionTypes() ),
                            builder.equal( audit.get(InteropAudit_.failed), false )
                        );
                }
            }
            
            criteria.where(filtersRestriction);
            criteria.orderBy(builder.desc(audit.get( InteropAudit_.timeStamp )));
        }
        else{
            log.error("start time" + searchFilters.getStartTime() + " or stop time " + searchFilters.getStopTime() + " is invalid");
            return null;
        }
        
        try{
            return em.createQuery( criteria ).getResultList();
        }
        catch(NoResultException nre){
            log.error(nre.getMessage(), nre);
            return null;
        }
    }
}
