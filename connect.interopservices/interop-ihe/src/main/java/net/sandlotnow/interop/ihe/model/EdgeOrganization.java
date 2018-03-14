/**
 *======================================================================================
 * EdgeOrganization.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- EdgeOrganization
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Nov 23, 2015
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

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import net.sandlotnow.interop.ihe.presentation.View;

import com.fasterxml.jackson.annotation.JsonView;

//Imports

@Entity
@Table(name="edge_organization", 
       uniqueConstraints = { 
            @UniqueConstraint(columnNames = { "name" }), 
            @UniqueConstraint(columnNames = { "organization_oid" })
            })
public class EdgeOrganization {

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
    
    @Column(name = "short_name", nullable = false) 
    @JsonView(View.EdgeSystemSummary.class)  
    private String shortName;
    
    @Column(name = "organization_oid", nullable = false) 
    @JsonView(View.EdgeSystemSummary.class)  
    private String organizationOID;

    @OneToMany(mappedBy = "organization", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private Set<EdgeSystem> edgeSystems;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOrganizationOID() {
        return organizationOID;
    }

    public void setOrganizationOID(String organizationOID) {
        this.organizationOID = organizationOID;
    }

    public Set<EdgeSystem> getEdgeSystems() {
        return edgeSystems;
    }

    public void setEdgeSystems(Set<EdgeSystem> edgeSystems) {
        this.edgeSystems = edgeSystems;
    }
    
    
    public EdgeOrganization shallowCopy(){
        EdgeOrganization org = new EdgeOrganization();
        org.id = this.id;
        org.name = this.name;
        org.shortName = this.shortName;
        org.organizationOID = this.organizationOID;
        return org;
    }
}
