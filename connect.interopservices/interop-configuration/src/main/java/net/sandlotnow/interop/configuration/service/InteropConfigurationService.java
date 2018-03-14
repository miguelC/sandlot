/**
 *======================================================================================
 * InteropConfigurationService.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- InteropConfigurationService
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			04/31/2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:    A service interface that defines the functionality available in the
 *                  Sandlot configuration REST services
 *                 
 *  				Design Pattern(s):
 *  				 - None
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/
package net.sandlotnow.interop.configuration.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonView;

import net.sandlotnow.interop.ihe.model.EdgeDeviceType;
import net.sandlotnow.interop.ihe.model.EdgeEndpointActor;
import net.sandlotnow.interop.ihe.model.EdgeOrganization;
import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeDevice;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.ihe.presentation.View;

@Path("/configservice")
public interface InteropConfigurationService {
    
    /**  EDGE SYSTEMS **/
    /***
     * Returns a List of @see net.sandlotnow.ihe.model.EdgeSystem object in json format
     * @return
     */
    @GET
    @Path("ihe/edgesystems")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(View.EdgeSystemSummary.class)
    public List<EdgeSystem> getEdgeSystems();
    
    @GET
    @Path("ihe/edgesystems/eager")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(View.EdgeSystemComplete.class)
    public List<EdgeSystem> getEdgeSystemsEager();

    @GET
    @Path("ihe/edgesystem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(View.EdgeSystemComplete.class)
    public EdgeSystem getEdgeSystemById(@PathParam("id") int id);

    @GET
    @Path("ihe/edgesystem/{id}/lazy")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(View.EdgeSystemSummary.class)
    public EdgeSystem getEdgeSystemSummaryById(@PathParam("id") int id);
    
    @POST
    @Path("ihe/edgesystem/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EdgeSystem saveEdgeSystem(EdgeSystem data);

    @GET
    @Path("ihe/edgesystem/endpoint/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EdgeEndpointActor> getEdgeEndpointActors();
    
    @POST
    @Path("ihe/edgesystem/endpoint/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EdgeEndpoint saveEdgeEndpoint(EdgeEndpoint data);

    @GET
    @Path("ihe/edgesystem/device/types")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EdgeDeviceType> getEdgeDeviceTypes();
    
    @POST
    @Path("ihe/edgesystem/device/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EdgeDevice saveEdgeDevice(EdgeDevice data);
    
    @GET
    @Path("ihe/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EdgeTransaction> getEdgeTransactions();

    @POST
    @Path("ihe/transaction/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EdgeTransaction saveEdgeTransaction(EdgeTransaction data);

    @GET
    @Path("ihe/organizations")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EdgeOrganization> getEdgeOrganizations();
    
    @GET
    @Path("ihe/organization/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public EdgeOrganization getEdgeOrganizationById(@PathParam("id") int id);

    @POST
    @Path("ihe/organization/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EdgeOrganization saveEdgeOrganization(EdgeOrganization data);
    
    
    /** SPRING CONFIG **/
    @GET
    @Path("config/{id}")
    public String getConfig(@PathParam("id") String id);

    /***
     * Returns a @see net.sandlotnow.configuration.SandlotConfiguration object in json format
     * @return
     */
    @GET
    @Path("config/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getConfigs(); 

    @PUT
    @Path("config/update/{id}")
    public String updateConfig(@PathParam("id") String id);

    @POST
    @Path("config/add")
    public String createConfig(String data);

    @DELETE
    @Path("config/delete/{id}")
    public String deleteConfig(@PathParam("id") String id);
}
