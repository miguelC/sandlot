/**
 *======================================================================================
 * TestDataIhe.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- TestDataIhe
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Aug 12, 2015
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
package net.sandlotnow.interop.ihe;

import java.util.List;

import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;

//Imports

public class TestDataIhe {
    private List<EdgeTransaction> transactions;
    private List<EdgeSystem> edgeSystems;
    
    public List<EdgeTransaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<EdgeTransaction> transactions) {
        this.transactions = transactions;
    }
    public List<EdgeSystem> getEdgeSystems() {
        return edgeSystems;
    }
    public void setEdgeSystems(List<EdgeSystem> edgeSystems) {
        this.edgeSystems = edgeSystems;
    }
}
