/**
 *======================================================================================
 * View.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- View
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Nov 18, 2015
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
package net.sandlotnow.interop.ihe.presentation;

//Imports

public class View {
    public interface EdgeSystemSummary {}
    public interface EdgeSystemComplete extends EdgeSystemSummary {}
}
