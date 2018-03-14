/**
 *======================================================================================
 * EdgeSystem.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- EdgeSystem
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
package net.sandlotnow.interop.ihe.model;

//Imports
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
//import javax.validation.constraints.NotNull;





import net.sandlotnow.interop.ihe.presentation.View;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name="edge_system", 
       uniqueConstraints = { 
            @UniqueConstraint(columnNames = { "name" }), 
            @UniqueConstraint(columnNames = { "assigning_authority_oid" }), 
            @UniqueConstraint(columnNames = { "document_source_oid" })
            })
public class EdgeSystem {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @JsonView(View.EdgeSystemSummary.class)  
    private Integer id;
    
    @Version
    private long version;

    @Column(name = "name", nullable = false) 
    @JsonView(View.EdgeSystemSummary.class)  
    private String name;
    
    @Column(name = "assigning_authority_oid") 
    @JsonView(View.EdgeSystemSummary.class)  
    private String assigningAuthorityOID;
    
    @Column(name = "document_source_oid") 
    @JsonView(View.EdgeSystemSummary.class)  
    private String documentSourceOID;

    @OneToMany(mappedBy = "edgeSystem", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    @JsonView(View.EdgeSystemComplete.class)  
    @JsonManagedReference
    private Set<EdgeEndpoint> endpoints;
    
    @OneToMany(mappedBy = "edgeSystem", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    @JsonView(View.EdgeSystemComplete.class)  
    @JsonManagedReference
    private Set<EdgeDevice> devices;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "organization_id")
    @JsonView(View.EdgeSystemSummary.class)  
    private EdgeOrganization organization;

    @Transient 
    @JsonView(View.EdgeSystemComplete.class) 
    private boolean cascade = true;

    /**
     * Utility methods
     */
    
    public void addEndpoint(EdgeEndpoint endpoint){
        if(endpoints == null){
            endpoints = new HashSet<EdgeEndpoint>();
        }
        endpoint.setEdgeSystem(this);
        endpoints.add(endpoint);
    }
    
    public EdgeEndpoint getEndpointForTransaction(String transactionId){
        if(endpoints == null) return null;
        for(EdgeEndpoint endpoint : endpoints){
            if(endpoint.getTransaction() != null &&
               endpoint.getTransaction().getName() != null &&
               endpoint.getTransaction().getName().equals(transactionId)){
                return endpoint;
            }
        }
        return null;
    }

    public EdgeEndpoint getSenderEndpointForTransaction(String transactionId){
        if(endpoints == null) return null;
        for(EdgeEndpoint endpoint : endpoints){
            if(endpoint.getTransaction() != null &&
               endpoint.getTransaction().getName() != null &&
               endpoint.getTransaction().getName().equals(transactionId)){
                if(endpoint.getActor() == EdgeEndpointActor.SENDER || 
                   endpoint.getActor() == EdgeEndpointActor.ANY ){
                    return endpoint;
                }
            }
        }
        return null;
    }
    public EdgeEndpoint getReceiverEndpointForTransaction(String transactionId){
        if(endpoints == null) return null;
        for(EdgeEndpoint endpoint : endpoints){
            if(endpoint.getTransaction() != null &&
               endpoint.getTransaction().getName() != null &&
               endpoint.getTransaction().getName().equals(transactionId)){
                if(endpoint.getActor() == EdgeEndpointActor.RECEIVER || 
                   endpoint.getActor() == EdgeEndpointActor.ANY ){
                     return endpoint;
                }
            }
        }
        return null;
    }

    public void addDevice(EdgeDevice device){
        if(devices == null){
            devices = new HashSet<EdgeDevice>();
        }
        device.setEdgeSystem(this);
        devices.add(device);
    }
    
    /**
     * Getters and Setters
     */
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EdgeEndpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Set<EdgeEndpoint> endpoints) {
        if(isCascade()){
            for(EdgeEndpoint endpoint : endpoints){
                endpoint.setEdgeSystem(this);
            }
        }
        this.endpoints = endpoints;
    }

    public String getAssigningAuthorityOID() {
        return assigningAuthorityOID;
    }

    public void setAssigningAuthorityOID(String assigningAuthorityOID) {
        this.assigningAuthorityOID = assigningAuthorityOID;
    }

    public String getDocumentSourceOID() {
        return documentSourceOID;
    }

    public void setDocumentSourceOID(String documentSourceOID) {
        this.documentSourceOID = documentSourceOID;
    }

    public Set<EdgeDevice> getDevices() {
        return devices;
    }

    public void setDevices(Set<EdgeDevice> devices) {
        if(isCascade()){
            for(EdgeDevice device : devices){
                device.setEdgeSystem(this);
            }
        }
        this.devices = devices;
    }
    
    public EdgeOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(EdgeOrganization organization) {
        this.organization = organization;
    }
    
    public boolean isCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }
    
    public EdgeSystem clone(){
        EdgeSystem cloned = new EdgeSystem();
        cloned.setAssigningAuthorityOID(assigningAuthorityOID);
        cloned.setCascade(cascade);
        cloned.setDocumentSourceOID(documentSourceOID);
        cloned.setId(id);
        cloned.setName(name);
        cloned.setVersion(version);
        if(organization != null){
            EdgeOrganization org = new EdgeOrganization();
            org.setOrganizationOID(organization.getOrganizationOID());
            org.setId(organization.getId());
            org.setName(organization.getName());
            org.setShortName(organization.getShortName());
            org.setVersion(organization.getVersion());
            cloned.setOrganization(org);
        }
        if(endpoints != null){
            for(EdgeEndpoint endpoint: endpoints){
                EdgeEndpoint cendpoint = new EdgeEndpoint();
                cendpoint.setActor(endpoint.getActor());
                cendpoint.setCamelEndpointName(endpoint.getCamelEndpointName());
                cendpoint.setId(endpoint.getId());
                cendpoint.setTransaction(endpoint.getTransaction());
                cendpoint.setVersion(endpoint.getVersion());
                cloned.addEndpoint(cendpoint);
            }
        }
        if(devices != null){
            for(EdgeDevice device : devices){
                EdgeDevice cdevice = new EdgeDevice();
                cdevice.setDeviceOID(device.getDeviceOID());
                cdevice.setDeviceType(device.getDeviceType());
                cdevice.setId(device.getId());
                cdevice.setName(device.getName());
                cdevice.setVersion(device.getVersion());
                cloned.addDevice(cdevice);
            }
        }
        return cloned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EdgeSystem [id=").append(id).append(", name=").append(name).append(", organization=").append(organization != null ? organization.getOrganizationOID() : "null");
        sb.append(" endpoints= [ ");
        for(EdgeEndpoint endpoint : endpoints){
            if(endpoint.getTransaction() != null)
                sb.append(endpoint.getTransaction().getName());
            sb.append(" : ").append(endpoint.getCamelEndpointName()).append(", ");
        }
        sb.append("]]");
        return  sb.toString();
    }
}
