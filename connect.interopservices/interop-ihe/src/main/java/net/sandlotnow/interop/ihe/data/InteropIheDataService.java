/**
 *======================================================================================
 * SandlotIheDataService.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotIheDataService
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

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.sandlotnow.interop.ihe.model.EdgeDevice;
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeOrganization;
import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;

@Component
public class InteropIheDataService {

    public static final String TX_MANAGER_QUALIFIER_IHE = "transactionManagerIhe";
    
    @Autowired
    private InteropIheDaoImpl iheDao;

    /**
     * Systems
     */
    
    @Transactional(TX_MANAGER_QUALIFIER_IHE)
    public EdgeSystem saveEdgeSystem(EdgeSystem edge) {
        return iheDao.saveOrUpdateEdgeSystem(edge);
    }
      
    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public List<EdgeSystem> listAllEdgeSystems() {
        return iheDao.findAllEdgeSystems();
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public List<EdgeSystem> listAllEdgeSystemsEager() {
        return iheDao.findAllEdgeSystemsEager();
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeSystem findEdgeSystemById(int id) {
        return iheDao.findEdgeSystemById(id);
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeSystem findEdgeSystemByIdLazy(int id) {
        return iheDao.findEdgeSystemByIdLazy(id);
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public List<EdgeSystem> findEdgeSystemsByOrganizationOID(String oid) {
        return iheDao.findEdgeSystemsByOrganizationOID(oid);
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeSystem findEdgeSystemByAssigningAuthorityOID(String oid) {
        return iheDao.findEdgeSystemByAssigningAuthorityOID(oid);
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeSystem findEdgeSystemByDeviceOrAssigningAuthorityOID(String oid) {
        return iheDao.findEdgeSystemByDeviceOrAssigningAuthorityOID(oid);
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeSystem findEdgeSystemByDevice(String oid) {
        return iheDao.findEdgeSystemByDeviceOID(oid);
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public List<EdgeSystem> findEdgeSystemsByAssigningAuthorityOID(String oid) {
        return iheDao.findEdgeSystemsByAssigningAuthorityOID(oid);
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeSystem findEdgeSystemByDocumentSourceOID(String oid) {
        return iheDao.findEdgeSystemByDocumentSourceOID(oid);
 
    }
    
    /**
     * Endpoints
     */

    @Transactional(TX_MANAGER_QUALIFIER_IHE)
    public EdgeEndpoint saveEdgeEndpoint(EdgeEndpoint edgeEndpoint) {
        return iheDao.saveOrUpdateEdgeEndpoint(edgeEndpoint);
    }
    
    /**
     * Devices
     */

    @Transactional(TX_MANAGER_QUALIFIER_IHE)
    public EdgeDevice saveEdgeDevice(EdgeDevice edgeDevice) {
        return iheDao.saveOrUpdateEdgeDevice(edgeDevice);
    }
    
    /**
     * Organizations
     */

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeOrganization findEdgeOrganizationById(int id) {
        return iheDao.findEdgeOrganizationById(id);
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeOrganization findEdgeOrganizationByIdLazy(int id) {
        return iheDao.findEdgeOrganizationByIdLazy(id);
 
    }

    @Transactional(TX_MANAGER_QUALIFIER_IHE)
    public EdgeOrganization saveEdgeOrganization(EdgeOrganization edgeOrg) {
        return iheDao.saveOrUpdateEdgeOrganization(edgeOrg);
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public List<EdgeOrganization> listAllEdgeOrganizations() {
        return iheDao.findAllEdgeOrganizations();
 
    }
    
    /** 
     *  Edge Transaction Management
     */

    @Transactional(TX_MANAGER_QUALIFIER_IHE)
    public EdgeTransaction saveEdgeTransaction(EdgeTransaction transaction) {
        return iheDao.saveOrUpdateEdgeTransaction(transaction);
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public List<EdgeTransaction> listAllEdgeTransactions() {
        return iheDao.findAllEdgeTransactions();
 
    }

    @Transactional(value = TX_MANAGER_QUALIFIER_IHE, readOnly = true)
    public EdgeTransaction findEdgeTransactionByName(String name) {
        return iheDao.findEdgeTransactionByName(name);
 
    }
}
