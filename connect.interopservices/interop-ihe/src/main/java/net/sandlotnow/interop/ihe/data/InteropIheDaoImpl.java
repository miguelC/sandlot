/**
 *======================================================================================
 * SandlotIheDaoImpl.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotIheDaoImpl
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jun 11, 2015
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
package net.sandlotnow.interop.ihe.data;

//Imports
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sandlotnow.interop.ihe.model.EdgeDevice;
import net.sandlotnow.interop.ihe.model.EdgeDevice_;
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeOrganization;
import net.sandlotnow.interop.ihe.model.EdgeOrganization_;
import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.ihe.model.EdgeSystem_;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.ihe.model.EdgeTransaction_;

@Component
public class InteropIheDaoImpl {
    
    private static final Logger log = LoggerFactory.getLogger(InteropIheDaoImpl.class);

    protected final static String QUERY_EDGESYSTEM_BYOID = "select edge from EdgeSystem as edge where edge.OrganizationOID = ?1";
    protected final static String QUERY_EDGESYSTEM_ALL = "SELECT es FROM EdgeSystem es";
    protected final static String QUERY_EDGETRANSACTION_ALL = "SELECT transaction FROM EdgeTransaction transaction";
    
    @PersistenceContext( unitName = "sandlotIhePersistence" )
    private EntityManager em;

    public EdgeSystem saveOrUpdateEdgeSystem(EdgeSystem edge) {
        log.info("Saving edge system '" + edge.getName() + "'");
        edge = em.merge(edge);
        return edge;
    }

    public List<EdgeSystem> findAllEdgeSystems() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeSystem> criteria = builder.createQuery( EdgeSystem.class );
        Root<EdgeSystem> edge = criteria.from( EdgeSystem.class );
        criteria.select( edge );
        criteria.orderBy(builder.asc(edge.get( EdgeSystem_.id )));
        try{
            List<EdgeSystem> edgeSystems = em.createQuery( criteria ).getResultList(); 
            return edgeSystems;
        }
        catch(NoResultException nre){
            return null;
        }
    }

    public List<EdgeSystem> findAllEdgeSystemsEager() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeSystem> criteria = builder.createQuery( EdgeSystem.class );
        Root<EdgeSystem> edge = criteria.from( EdgeSystem.class );
        edge.fetch(EdgeSystem_.endpoints, JoinType.LEFT);
        edge.fetch(EdgeSystem_.devices, JoinType.LEFT);
        criteria.select( edge );
        criteria.orderBy(builder.asc(edge.get( EdgeSystem_.id )));
        criteria.distinct(true);
        try{
            List<EdgeSystem> edgeSystems = em.createQuery( criteria ).getResultList();  
            return edgeSystems;
        }
        catch(NoResultException nre){
            return null;
        }
    }

    public EdgeSystem findEdgeSystemById(int id) {
        try{
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EdgeSystem> criteria = builder.createQuery( EdgeSystem.class );
            Root<EdgeSystem> edge = criteria.from( EdgeSystem.class );
            edge.fetch(EdgeSystem_.endpoints, JoinType.LEFT);
            edge.fetch(EdgeSystem_.devices, JoinType.LEFT);
            criteria.select( edge );
            criteria.where( builder.equal(edge.get( EdgeSystem_.id ), id ) );
            
            EdgeSystem edgeSystem = em.createQuery( criteria ).getSingleResult(); 
            return edgeSystem;
        }
        catch(NoResultException nre){
            return null;
        }
    }

    public EdgeSystem findEdgeSystemByIdLazy(int id) {
        try{
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EdgeSystem> criteria = builder.createQuery( EdgeSystem.class );
            Root<EdgeSystem> edge = criteria.from( EdgeSystem.class );
            criteria.select( edge );
            criteria.where( builder.equal(edge.get( EdgeSystem_.id ), id ) );
            
            EdgeSystem edgeSystem = em.createQuery( criteria ).getSingleResult();
            return edgeSystem;
        }
        catch(NoResultException nre){
            return null;
        }
    }

    
    public List<EdgeSystem> findEdgeSystemsByOrganizationOID(String oid) {
        
        EdgeOrganization org = findEdgeOrganizationByOIDLazy(oid);
        if(org != null){
        
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EdgeSystem> criteria = builder.createQuery( EdgeSystem.class );
            Root<EdgeSystem> edge = criteria.from( EdgeSystem.class );
            edge.fetch(EdgeSystem_.endpoints, JoinType.LEFT);
            edge.fetch(EdgeSystem_.devices, JoinType.LEFT);
            criteria.select( edge );
            criteria.where( builder.equal(edge.get( EdgeSystem_.organization ), org ) );
            criteria.orderBy(builder.asc(edge.get( EdgeSystem_.id )));
            criteria.distinct(true);
            try{
                log.info("Finding edge system by organization OID " + oid);
                List<EdgeSystem> edgeSystems = em.createQuery( criteria ).getResultList(); 
                log.info("Edge System search for organization " + oid + " resulted in " + edgeSystems.size() + " edge systems");
                return edgeSystems;
            }
            catch(NoResultException nre){
                return null;
            }
        }
        log.warn("Edge Systems for organization search returned no matches ");
        return null;
        /*List<EdgeSystem> edges = em.createQuery( QUERY_EDGESYSTEM_BYOID ).setParameter(1, oid).getResultList();
        if (edges != null && edges.size() > 0) {
            return edges.get(0);
        }
        return null;*/
    }    

    public EdgeSystem findEdgeSystemByDeviceOrAssigningAuthorityOID(String oid) {
        EdgeSystem edge = findEdgeSystemByDeviceOID(oid);
        if(edge == null){
            edge = findEdgeSystemByAssigningAuthorityOID(oid);
        }
        return edge;
    }
    
    public EdgeSystem findEdgeSystemByDeviceOID(String oid) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeDevice> criteria = builder.createQuery( EdgeDevice.class );
        Root<EdgeDevice> edgeDevice = criteria.from( EdgeDevice.class );
        criteria.select( edgeDevice );
        criteria.where( 
                builder.equal(edgeDevice.get( EdgeDevice_.deviceOID ), oid ) );
        try{
            EdgeDevice dev = em.createQuery( criteria ).getSingleResult(); 
            if(dev != null){
                EdgeSystem edgeSystem = dev.getEdgeSystem();
                edgeSystem = findEdgeSystemById(edgeSystem.getId());
                return edgeSystem;
            }
            return null;
        }
        catch(NoResultException nre){
            return null;
        }
    }
    
    public EdgeSystem findEdgeSystemByAssigningAuthorityOID(String oid) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeSystem> criteria = builder.createQuery( EdgeSystem.class );
        Root<EdgeSystem> edge = criteria.from( EdgeSystem.class );
        edge.fetch(EdgeSystem_.endpoints, JoinType.LEFT);
        edge.fetch(EdgeSystem_.devices, JoinType.LEFT);
        criteria.select( edge );
        criteria.where( builder.equal(edge.get( EdgeSystem_.assigningAuthorityOID ), oid ) );
        try{
            EdgeSystem edgeSystem = em.createQuery( criteria ).getSingleResult(); 
            return edgeSystem;
        }
        catch(NoResultException nre){
            return null;
        }
    }
    
    public List<EdgeSystem> findEdgeSystemsByAssigningAuthorityOID(String oid) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeSystem> criteria = builder.createQuery( EdgeSystem.class );
        Root<EdgeSystem> edge = criteria.from( EdgeSystem.class );
        edge.fetch(EdgeSystem_.endpoints, JoinType.LEFT);
        edge.fetch(EdgeSystem_.devices, JoinType.LEFT);
        criteria.select( edge );
        criteria.where( builder.equal(edge.get( EdgeSystem_.assigningAuthorityOID ), oid ) );
        try{
            List<EdgeSystem> edgeSystems = em.createQuery( criteria ).getResultList(); 
            return edgeSystems;
        }
        catch(NoResultException nre){
            return null;
        }
    }
    
    public EdgeSystem findEdgeSystemByDocumentSourceOID(String oid) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeSystem> criteria = builder.createQuery( EdgeSystem.class );
        Root<EdgeSystem> edge = criteria.from( EdgeSystem.class );
        edge.fetch(EdgeSystem_.endpoints, JoinType.LEFT);
        edge.fetch(EdgeSystem_.devices, JoinType.LEFT);
        criteria.select( edge );
        criteria.where( builder.equal(edge.get( EdgeSystem_.documentSourceOID ), oid ) );
        try{
            EdgeSystem edgeSystem = em.createQuery( criteria ).getSingleResult(); 
            return edgeSystem;
        }
        catch(NoResultException nre){
            return null;
        }
    }

    /*
     * public void deleteEdgeSystem(EdgeSystem edge) { em.remove(edge); }
     */

    /**
     * Endpoints
     */

    public EdgeEndpoint saveOrUpdateEdgeEndpoint(EdgeEndpoint edgeEndpoint) {
        return em.merge(edgeEndpoint);
    }

    public void deleteEdgeEndpoint(EdgeEndpoint edgeEndpoint) { em.remove(edgeEndpoint); }
    
    /**
     * Devices
     */

    public EdgeDevice saveOrUpdateEdgeDevice(EdgeDevice edgeDevice) {
        return em.merge(edgeDevice);
    }

    public void deleteEdgeDevice(EdgeDevice edgeDevice) { em.remove(edgeDevice); }
    
    /**
     * Organizations
     */

    public EdgeOrganization saveOrUpdateEdgeOrganization(EdgeOrganization edgeOrg) {
        log.info("Saving organization '" + edgeOrg.getName() + "'");
        edgeOrg = em.merge(edgeOrg);
        log.info("Saved organization '" + edgeOrg.getId() + "'");
        return edgeOrg;
    }

    public List<EdgeOrganization> findAllEdgeOrganizations() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeOrganization> criteria = builder.createQuery( EdgeOrganization.class );
        Root<EdgeOrganization> edge = criteria.from( EdgeOrganization.class );
        criteria.select( edge );
        try{
            List<EdgeOrganization> edgeOrgs = em.createQuery( criteria ).getResultList(); 
            return edgeOrgs;
        }
        catch(NoResultException nre){
            return null;
        }
    }

    public EdgeOrganization findEdgeOrganizationById(int id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeOrganization> criteria = builder.createQuery( EdgeOrganization.class );
        Root<EdgeOrganization> edge = criteria.from( EdgeOrganization.class );
        edge.fetch(EdgeOrganization_.edgeSystems, JoinType.LEFT);
        criteria.select( edge );
        criteria.where( builder.equal(edge.get( EdgeOrganization_.id ), id ) );
        criteria.orderBy(builder.asc(edge.get(EdgeOrganization_.id)));
        criteria.distinct(true);
        try{
            log.info("Finding edge organization by id " + id);
            EdgeOrganization edgeOrg = em.createQuery( criteria ).getSingleResult(); 
            log.info("Edge Organization search result " + (edgeOrg == null ? "NULL" : edgeOrg.toString()));
            return edgeOrg;
        }
        catch(NoResultException nre){
            return null;
        }
    }

    public EdgeOrganization findEdgeOrganizationByIdLazy(int id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeOrganization> criteria = builder.createQuery( EdgeOrganization.class );
        Root<EdgeOrganization> edge = criteria.from( EdgeOrganization.class );
        criteria.select( edge );
        criteria.where( builder.equal(edge.get( EdgeOrganization_.id ), id ) );
        try{
            log.info("Finding edge organization by id " + id);
            EdgeOrganization edgeOrg = em.createQuery( criteria ).getSingleResult(); 
            log.info("Edge Organization search result " + (edgeOrg == null ? "NULL" : edgeOrg.toString()));
            return edgeOrg;
        }
        catch(NoResultException nre){
            return null;
        }
    }
    
    public EdgeOrganization findEdgeOrganizationByOID(String oid) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeOrganization> criteria = builder.createQuery( EdgeOrganization.class );
        Root<EdgeOrganization> edge = criteria.from( EdgeOrganization.class );
        edge.fetch(EdgeOrganization_.edgeSystems, JoinType.LEFT);
        criteria.select( edge );
        criteria.where( builder.equal(edge.get( EdgeOrganization_.organizationOID ), oid ) );
        try{
            log.info("Finding edge organization by organization OID " + oid);
            EdgeOrganization edgeOrg = em.createQuery( criteria ).getSingleResult(); 
            log.info("Edge Organization search result " + (edgeOrg == null ? "NULL" : edgeOrg.toString()));
            return edgeOrg;
        }
        catch(NoResultException nre){
            return null;
        }
    }
    
    public EdgeOrganization findEdgeOrganizationByOIDLazy(String oid) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeOrganization> criteria = builder.createQuery( EdgeOrganization.class );
        Root<EdgeOrganization> edge = criteria.from( EdgeOrganization.class );
        criteria.select( edge );
        criteria.where( builder.equal(edge.get( EdgeOrganization_.organizationOID ), oid ) );
        try{
            log.info("Finding edge organization by organization OID " + oid);
            EdgeOrganization edgeOrg = em.createQuery( criteria ).getSingleResult(); 
            log.info("Edge Organization search result " + (edgeOrg == null ? "NULL" : edgeOrg.toString()));
            return edgeOrg;
        }
        catch(NoResultException nre){
            return null;
        }
    }
    /**
     * Transactions
     */

    public EdgeTransaction saveOrUpdateEdgeTransaction(EdgeTransaction transaction) {
        return em.merge(transaction);
    }

    public List<EdgeTransaction> findAllEdgeTransactions() {
        return em.createQuery(QUERY_EDGETRANSACTION_ALL).getResultList();
    }
    
    public EdgeTransaction findEdgeTransactionByName(String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EdgeTransaction> criteria = builder.createQuery( EdgeTransaction.class );
        Root<EdgeTransaction> transaction = criteria.from( EdgeTransaction.class );
        criteria.select( transaction );
        criteria.where( builder.equal(transaction.get( EdgeTransaction_.name ), name ) );
        try{
            return em.createQuery( criteria ).getSingleResult(); 
        }
        catch(NoResultException nre){
            return null;
        }
    }
}
