/**
 *======================================================================================
 * EdgeDevice.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- EdgeDevice
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Oct 19, 2015
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;

//Imports

@Entity
@Table(name="edge_device", 
       uniqueConstraints = { 
            @UniqueConstraint(columnNames = { "name" }), 
            @UniqueConstraint(columnNames = { "device_oid" }),
            @UniqueConstraint(columnNames = { "edge_system_id", "device_type" })
      })
public class EdgeDevice {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer id;
    
    @Version
    private long version;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "device_oid", nullable = false)
    private String deviceOID;

    @Column(name = "device_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EdgeDeviceType deviceType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceOID() {
        return deviceOID;
    }

    public void setDeviceOID(String deviceOID) {
        this.deviceOID = deviceOID;
    }

    public EdgeDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(EdgeDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public EdgeSystem getEdgeSystem() {
        return edgeSystem;
    }

    public void setEdgeSystem(EdgeSystem edgeSystem) {
        this.edgeSystem = edgeSystem;
    }
}
