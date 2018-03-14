/**
 *======================================================================================
 * ApplicationsController.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- Applications list controller
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Render Applications Page.
 *  				Save/Update, delete application instance from database
 *
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

 // Package
package net.sandlotnow.config

// System Imports
import static org.springframework.http.HttpStatus.*

/**
 * Controller class to handle the Applications list
 * @author praveen13126
 *
 */
class ApplicationsController {
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	def ApplicationsService
	
	// STATIC DEFINITIONS
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	//===================================================================
	// PUBLIC FUNCTIONS
	//===================================================================
	/**
	 * Action invoked as default controller action.
	 * Action will render applications list in current controller index page.
	 *
	 * @render Current controller index page
	 */
    def index(Integer max) {
        
		log.info("ApplicationsController:index:-		Rendering applications list")
		params.max = Math.min(max ?: 10, 100)
        respond Applications.list(params), model:[applicationsInstanceCount: Applications.count()]
    }
	
	/**
	 * Action invoked to get selected application instance.
	 * Action will render application instance in show page.
	 *
	 * @render Show page of application to show selected application instance
	 */
    def show(Applications applicationsInstance) {
        
		log.info("ApplicationsController:show:-		Rendering show instance page")
		respond applicationsInstance
    }
	
	/**
	 * Action to empty the saved application instance from modal
	 *
	 * @render Create page of application to show empty fields
	 */
    def create() {
        
		log.info("ApplicationsController:create:-		Rendering empty create page")
		respond new Applications(params)
    }

	/**
	 * Action to save entered applications instance data into database
	 * Action will check if the instance is null or has error
	 * Action will invoke service method to save/update to database
	 *
	 * @param applicationsInstance		Selected instance of applications
	 *
	 * @render Create page if there is error while saving instance
	 * 		   Show page if save successfully
	 */
	def save(Applications applicationsInstance) {
        
		//check if the instance is null
		if (applicationsInstance == null) {
            
			notFound()
            return
        }
		
		//check if the instance has error
        if (applicationsInstance.hasErrors()) {
			
			log.info("ApplicationsController:save:-		Rendering create page with erros")
			respond applicationsInstance.errors, view:'create'
            return
        }
		
		//invoke service method to save to database
		ApplicationsService.saveApplications(applicationsInstance)

		log.info("ApplicationsController:save:-		Rendering index page with save successfully")
		//set message if save successfully
		request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'applications.label', default: 'Applications'), applicationsInstance.id])
				redirect applicationsInstance
            }
            '*' { respond applicationsInstance, [status: CREATED] }
        }
		
	}

    /**
     * Action invoked to get selected application instance.
	 * Action will render application instance in edit page.
	 * 
	 * @param applicationsInstance		Selected instance of applications 
	 * 
     * @render Edit page with the selected application instance
     */
	def edit(Applications applicationsInstance) {
		
		log.info("ApplicationsController:edit:-		Rendering edit page with instance")
        respond applicationsInstance
	}

	
	/**
	 * Action to save entered applications instance data into database
	 * Action will check if the instance is null or has error
	 * Action will invoke service method to save/update to database
	 *
	 * @param applicationsInstance		Selected instance of applications
	 *
	 * @render Edit page if there is error while saving instance
	 * 		   Show page if save successfully
	 */
    def update(Applications applicationsInstance) {
        
		//check if the instance is null
		if (applicationsInstance == null) {
            
			notFound()
            return
        }
		
		//check if the instance has error
        if (applicationsInstance.hasErrors()) {
            
			log.info("ApplicationsController:update:-		Rendering edit page with erros")
			respond applicationsInstance.errors, view:'edit'
            return
        }
		
		//invoke service method to update to database
        ApplicationsService.saveApplications(applicationsInstance)
		
		log.info("ApplicationsController:update:-		Rendering index page with update successfully")
		//set message if save successfully
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Applications.label', default: 'Applications'), applicationsInstance.id])
                redirect applicationsInstance
            }
            '*'{ respond applicationsInstance, [status: OK] }
        }
	}
	
	/**
	 * Action to delete selected applications from database
	 * Action will check if the instance is null or has error
	 * Action will invoke service method to delete it from database
	 *  
	 * @render index page if deleted successfully
	 * 		  
	 */
    def delete(Applications applicationsInstance) {
		
		//check if the instance is null
        if (applicationsInstance == null) {
            
			notFound()
            return
        }
		
		//invoke service method to delete to database
        ApplicationsService.deleteApplications(applicationsInstance)

		log.info("ApplicationsController:delete:-		Rendering index page with delete successfully")
		//set message if save successfully
		request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Applications.label', default: 'Applications'), applicationsInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
	}
	
	
	//===================================================================
	// PROTECTED FUNCTIONS
	//===================================================================
	/**
	 * Function that will set message if instance is null
	 * 
	 * @render Index page and show message that no instance found
	 */
    protected void notFound() {
        
		request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'applications.label', default: 'Applications'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
