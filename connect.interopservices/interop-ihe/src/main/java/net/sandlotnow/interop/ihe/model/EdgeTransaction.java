/**
 *======================================================================================
 * EdgeTransaction.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- EdgeTransaction
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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name="edge_transaction", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class EdgeTransaction {
    
    public static final String TRANSACTION_ITI18 = "ITI-18";
    public static final String TRANSACTION_ITI41 = "ITI-41";
    public static final String TRANSACTION_ITI43 = "ITI-43";
    public static final String TRANSACTION_ITI44 = "ITI-44";
    public static final String TRANSACTION_ITI45 = "ITI-45";
    public static final String TRANSACTION_ITI47 = "ITI-47";
    
    @Id
    private Integer id;
    
    @Version
    private long version;
    
    @Column(nullable = false)
    private String name;
    private String description;
    
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
