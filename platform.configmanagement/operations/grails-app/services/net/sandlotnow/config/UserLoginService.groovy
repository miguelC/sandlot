/**
 *======================================================================================
 * UserLoginService.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- User Login Service
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Singleton service class for User Login Controller
 *  				Service class to handle functionality to user login
 *  				Service will validate, get user Name and user Role
 *  				
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

 // Package
package net.sandlotnow.config

// System Imports
import grails.transaction.Transactional
import org.springframework.context.i18n.LocaleContextHolder as LCH

/**
 * Service class to handle functionality to User Login controller
 * @author Praveen13126
 *
 */
@Transactional
class UserLoginService {
	
	def messageSource
	
	/**
	 * Method will get email id and password to validate from web service
	 *
	 * 
	 * @param email		User email id
	 * @param password	User password
	 * 
	 * @return true		For valid user
	 * 		   false	for invalid user
	 */
	def validateUser(String email, String password) {
		
		log.info("UserLoginService:validateUser:-		Start validating user")
		boolean validUser = false
		
		// web service method will be here
		String emailId = messageSource.getMessage("constant.user.email",[] as Object[], "Unkown Email",LCH.getLocale())
		String pass = messageSource.getMessage("constant.user.password",[] as Object[], "Unkown Email",LCH.getLocale())
		if(email.equals(emailId)&&password.equals(pass)) {
			validUser =  true;
		}else{
			validUser = false;
		}
		
		log.info("UserLoginService:validateUser:-		Ends validating user")
		return true
	}
	/**
	 * Method will get user role from web service
	 * 
	 * @return userRole		Role of user to display on index page
	 */
	def getUserRole() {
		
		log.info("UserLoginService:getuserRole:-		Start fetching user role")
		String userRole = messageSource.getMessage("constant.user.role",[] as Object[], "Unkown Name",LCH.getLocale())
		log.info("UserLoginService:validateUser:-		Ends fetching user role")
		return userRole
	}
	/**
	 * Method will get user name from web service
	 * 
	 * @return userName		Name of user to display on index page
	 */
	def getUserName() {
		
		log.info("UserLoginService:getuserName:-		Start fetching user name")
		String userName = messageSource.getMessage("constant.user.name",[] as Object[], "Unkown Role",LCH.getLocale())
		log.info("UserLoginService:validateUser:-		Ends fetching user name")
		return userName;
	}
}
