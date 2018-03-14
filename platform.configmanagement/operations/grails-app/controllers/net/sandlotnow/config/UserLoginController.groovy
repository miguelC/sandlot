/**
 *======================================================================================
 * UserLoginController.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- User login controller
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Render Login Page.
 *  				Check user availability in session, then redirect to application index else,
 *  				Validate user and redirect to application index.
 *  				On logout remove user availability.
 *  				
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

 // Package
package net.sandlotnow.config
 
// System Imports
import grails.web.RequestParameter;

// Project Imports
import net.sandlotnow.config.SessionConstants


/**
 * Controller class to handle the User Login request and response
 * @author Praveen13126
 *
 */
class UserLoginController {
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	def userLoginService
	
	/**
	 * Action invoked as default controller action.
	 * Action will render current controller index page.
	 * 
	 * @render Current controller index page
	 */
	def index() { 
		// ********************************
		//     BODY INTENTIONALLY BLANK
		// ********************************
	}
	
	/**
	 * Action to load User Login Page.
	 * Action will check if user is already logged in then it will redirect to application index else,
	 * Action will get the parameter define in URL and set it in email id field and render it in current controller index page.
	 * 
	 * @param activeDirec	Parameter define in URL
	 * 
	 * @render Application index page
	 * 		   Current controller index page
	 */
	def goToLoginPage(@RequestParameter("ActiveDirec")String activeDirec){
		
		log.info("UserLoginController:goToLoginPage:-		Rendering login page");
		boolean isUserPresent = false;
		isUserPresent = session.getAttribute(SessionConstants.IS_USER_PRESENT)
		
		//check if user is present in session
		if(isUserPresent) {
			
			render(view:"/index")
		} 
		else {
			
			//check if user request parameter have domain name
			if(activeDirec!=null) {
				
				activeDirec = '@'+activeDirec
				render(view:"index",model:['email':activeDirec]);
			} 
			else {
				
				render(view:"index",);
			}
		}
	}
	
	/**
	 * Action to authenticate user.
	 * Action will check if user is already logged in then it will redirect to application index else,
	 * Action will get email id and password. 
	 * Action will invoke service to get user name, get user role and validate, if valid user then redirect to application index else,
	 * Action will render to current controller index page with flash message.
	 * 
	 * @param email  	User email id
	 * @param password	User password
	 * 
	 * @render Application index page
	 * 		   Current controller index page
	 */
	def authenticateUser(String email, String password){
		
		log.info("UserLoginController:authenticateUser:-		Authenticating User");
		boolean validUser = false
		boolean isUserPresent = false
		String userName = null
		String userRole = null
		isUserPresent = session.getAttribute(SessionConstants.IS_USER_PRESENT)
		
		//check if user is present in session
		if(isUserPresent){
			
			render(view:"/index")
		} 
		else {
			
			validUser = userLoginService.validateUser(email, password)
			userName = userLoginService.getUserName();
			userRole = userLoginService.getUserRole();
			
			//check if user is valid user then set it in session
			if(validUser) {
				
				session.setAttribute(SessionConstants.IS_USER_PRESENT, true);
				session.setAttribute(SessionConstants.USER_NAME, userName);
				session.setAttribute(SessionConstants.USER_ROLE, userRole);
				render(view:"/index",model:['userName':userName,'userRole':userRole]);
				log.info("UserLoginController:authenticateUser:-		User logged in successfully");
			} 
			else {
				
				flash.message = message(code: 'custom.userLogin.invalid.loginDetail', args: [message(code: 'custom.userLogin.email'), message(code: 'custom.userLogin.password')])
				render(view:"index");
			}
		}
		
	}
	
	/**
	 * Action to close the application
	 * Action will set all session value to null
	 * Action will redirect to controller as set in URLMapping
	 * 
	 * @render Page define in URLMapping
	 */
	def doLogout() {
		
		log.info("UserLoginController:doLogout:-		User logged out successfully");
		session.invalidate()
		redirect(uri: '/')
	}
}
