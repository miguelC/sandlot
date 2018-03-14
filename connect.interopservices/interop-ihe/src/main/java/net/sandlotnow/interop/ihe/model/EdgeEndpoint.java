/**
 *======================================================================================
 * EdgeEndpoint.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- EdgeEndpoint
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jun 15, 2015
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "edge_endpoint", 
        uniqueConstraints = { 
            @UniqueConstraint(columnNames = { "edge_system_id", "transaction_id", "actor" })
            })
public class EdgeEndpoint {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer id;
    
    @Version
    private long version;
    
    @Column(name = "camel_endpoint_name", nullable = false)
    private String camelEndpointName;

    @Column(name = "actor")
    @Enumerated(EnumType.STRING)
    private EdgeEndpointActor actor = EdgeEndpointActor.SENDER;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false) 
    @JoinColumn(name = "transaction_id")
    private EdgeTransaction transaction;

    @ManyToOne
    @JoinColumn(name = "edge_system_id")
    @JsonBackReference
    private EdgeSystem edgeSystem;

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

    public String getCamelEndpointName() {
        return camelEndpointName;
    }

    public void setCamelEndpointName(String camelEndpointName) {
        this.camelEndpointName = camelEndpointName;
    }

    public EdgeTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(EdgeTransaction transaction) {
        this.transaction = transaction;
    }

    public EdgeSystem getEdgeSystem() {
        return edgeSystem;
    }

    public void setEdgeSystem(EdgeSystem edgeSystem) {
        this.edgeSystem = edgeSystem;
    }

    public EdgeEndpointActor getActor() {
        return actor;
    }

    public void setActor(EdgeEndpointActor actor) {
        this.actor = actor;
    }
    
}
