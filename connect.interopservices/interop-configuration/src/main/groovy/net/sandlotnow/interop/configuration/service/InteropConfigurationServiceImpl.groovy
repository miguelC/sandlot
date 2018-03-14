/**
 *======================================================================================
 * InteropConfigurationServiceImpl.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropConfigurationServiceImpl
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Aug 28, 2015
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
package net.sandlotnow.interop.configuration.service

//Imports
import java.util.List

import groovy.json.JsonOutput
import net.sandlotnow.interop.configuration.json.ConfigurationUtils;
import net.sandlotnow.interop.configuration.service.InteropConfigurationService
import net.sandlotnow.interop.configuration.SpringConfigurationParser
import net.sandlotnow.interop.ihe.data.InteropIheDataService;
import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.ihe.model.EdgeOrganization;
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeEndpointActor;
import net.sandlotnow.interop.ihe.model.EdgeDevice;
import net.sandlotnow.interop.ihe.model.EdgeDeviceType;
import net.sandlotnow.interop.ihe.presentation.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.fasterxml.jackson.annotation.JsonView;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Component
class InteropConfigurationServiceImpl implements InteropConfigurationService{    
    
    Logger log = LoggerFactory.getLogger(InteropConfigurationServiceImpl.class);
    
    @Autowired
    private SpringConfigurationParser parser
    
    public void setParser(SpringConfigurationParser parser) { this.parser = parser }
    
    @Autowired
    private InteropIheDataService iheDataService
    
    /** SECTION : IHE Components management ***/
    
    @Override
    @JsonView(View.EdgeSystemSummary.class)
    public List<EdgeSystem> getEdgeSystems() {
        List<EdgeSystem> edgesystems = iheDataService.listAllEdgeSystems()
        log.debug("Edge systems : " + ConfigurationUtils.toJson(edgesystems, View.EdgeSystemSummary.class))
        return edgesystems
    }
    
    @Override
    @JsonView(View.EdgeSystemComplete.class)
    public List<EdgeSystem> getEdgeSystemsEager() {        
        List<EdgeSystem> edgesystems = iheDataService.listAllEdgeSystemsEager()
        edgesystems.each {
            it = ConfigurationUtils.cleanCircularRefs(it)
        }
        log.info("Edge systems EAGER: " + ConfigurationUtils.toJson(edgesystems, View.EdgeSystemComplete.class))
                
        return edgesystems
    }
    
    @Override
    @JsonView(View.EdgeSystemComplete.class)
    public EdgeSystem  getEdgeSystemById(int id) {
        EdgeSystem edgesystem = iheDataService.findEdgeSystemById(id)
        edgesystem = ConfigurationUtils.cleanCircularRefs(edgesystem)
        def json = ConfigurationUtils.toJson(edgesystem, View.EdgeSystemComplete.class)
        log.debug(json)
        return edgesystem
    }
    
    @Override
    @JsonView(View.EdgeSystemSummary.class)
    public EdgeSystem getEdgeSystemSummaryById(int id){
        EdgeSystem edgesystem = iheDataService.findEdgeSystemByIdLazy(id)
        def json = ConfigurationUtils.toJson(edgesystem, View.EdgeSystemSummary.class)
        log.debug(json)
        return edgesystem
    }
    
    @Override
    @JsonView(View.EdgeSystemComplete.class)
    public EdgeSystem saveEdgeSystem(EdgeSystem data){
        def json = ConfigurationUtils.toJson(data, View.EdgeSystemSummary.class)
        log.info("Saving edge system " + json)
        data.setCascade(true)
        //data = ConfigurationUtils.fillChildrenRefs(data)
        data = iheDataService.saveEdgeSystem(data)
        return data
    }
    
    @Override
    public List<EdgeEndpointActor> getEdgeEndpointActors(){
        List<EdgeEndpointActor> actors = Arrays.asList(EdgeEndpointActor.values())
        return actors
    }
    
    @Override
    public EdgeEndpoint saveEdgeEndpoint(EdgeEndpoint data){
        def json = ConfigurationUtils.toJson(data)
        log.info("Saving endpoint " + json)
        data = iheDataService.saveEdgeEndpoint(data)
        return data
    }
    
    @Override
    public List<EdgeDeviceType> getEdgeDeviceTypes(){
        List<EdgeDeviceType> devTypes = Arrays.asList(EdgeDeviceType.values())
        return devTypes
    }
    
    @Override
    public EdgeDevice saveEdgeDevice(EdgeDevice data){
        def json = ConfigurationUtils.toJson(data)
        log.info("Saving device " + json)
        data = iheDataService.saveEdgeDevice(data)
        return data
    }
    
    @Override
    public List<EdgeTransaction>  getEdgeTransactions() {
        List<EdgeTransaction> txs = iheDataService.listAllEdgeTransactions()
        log.debug("Edge transactions : " + ConfigurationUtils.toJson(txs))
        return txs
    }
    
    @Override
    public EdgeTransaction saveEdgeTransaction(EdgeTransaction data){
        data = iheDataService.saveEdgeTransaction(data)
        def json = ConfigurationUtils.toJson(data)
        log.debug(json)
        return data
    }
    
    @Override
    public List<EdgeOrganization> getEdgeOrganizations() {
        List<EdgeOrganization> edgeOrgs = iheDataService.listAllEdgeOrganizations()
        return edgeOrgs
    }
    
    @Override
    public EdgeOrganization  getEdgeOrganizationById(int id) {
        EdgeOrganization edgeOrg = iheDataService.findEdgeOrganizationById(id)
        EdgeOrganization org = edgeOrg.shallowCopy()
        def json = ConfigurationUtils.toJson(org)
        log.debug(json)
        return org
    }
    
    @Override
    public EdgeOrganization saveEdgeOrganization(EdgeOrganization data){
        def json = ConfigurationUtils.toJson(data)
        log.info("Saving organization " + json)
        data = iheDataService.saveEdgeOrganization(data)
        return data
    }
    
    /** SECTION : Spring Components management ***/
    
    @Override
    public String getConfig(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String getConfigs() {
        // TODO Auto-generated method stub
        return parser.parseXmlToJson()
    }

    @Override
    public String updateConfig(String id) {
        // TODO Auto-generated method stub
        return null
    }

    @Override
    public String createConfig(String data) {
        // TODO Auto-generated method stub
        return null
    }

    @Override
    public String deleteConfig(String id) {
        // TODO Auto-generated method stub
        return null
    }
    
    /** SECTION : Camel Components management ***/
    
    /** SECTION : Utility methods **/
    
    
    public void setIheDataService(InteropIheDataService dataServ) { this.iheDataService = dataServ }
    
}
