/**
 *======================================================================================
 * NavigationLinksController.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- NavigationsLinks list controller
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Render NavigationsLinks Page.
 *					Save/Update, delete navigation link instance from database
 *
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
 * Controller class to handle the NavigationsLinks list
 * @author praveen13126
 *
 */
class NavigationLinksController {

	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	def NavigationLinksService
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	//===================================================================
	// PUBLIC FUNCTIONS
	//===================================================================
	/**
	 * Action invoked as default controller action.
	 * Action will render navigationsLinks list in current controller index page.
	 *
	 * @render Current controller index page
	 */
    def index(Integer max) {
        
		log.info("NavigationLinksController:index:-		Rendering navigationLinks list")
		params.max = Math.min(max ?: 10, 100)
        respond NavigationLinks.list(params), model:[navigationLinksInstanceCount: NavigationLinks.count()]
    }
	
	/**
	 * Action invoked to get selected navigationLink instance.
	 * Action will render navigationLink instance in show page.
	 *
	 * @render Show page of navigationLink to show selected navigationLink instance
	 */
    def show(NavigationLinks navigationLinksInstance) {
        
		log.info("NavigationLinksController:show:-		Rendering show instance page")
		respond navigationLinksInstance
    }
	
	/**
	 * Action to empty the saved navigationLink instance from modal
	 *
	 * @render Create page of navigationLink to show empty fields
	 */
    def create() {
        
		log.info("NavigationLinksController:create:-		Rendering empty create page")
		respond new NavigationLinks(params)
    }

	/**
	 * Action to save entered navigationsLinks instance data into database
	 * Action will check if the instance is null or has error
	 * Action will invoke service method to save/update to database
	 *
	 * @param navigationLinksInstance		Selected instance of navigationsLinks
	 *
	 * @render Create page if there is error while saving instance
	 * 		   Show page if save successfully
	 */
    def save(NavigationLinks navigationLinksInstance) {
        
		//check if the instance is null
		if (navigationLinksInstance == null) {
            
			notFound()
            return
        }
		
		//check if the instance has error
        if (navigationLinksInstance.hasErrors()) {
            
			log.info("NavigationLinksController:save:-		Rendering create page with erros")
			respond navigationLinksInstance.errors, view:'create'
            return
        }
		
		//invoke service method to save to database
        NavigationLinksService.saveNavigationLinks(navigationLinksInstance)
		
		log.info("NavigationLinksController:save:-		Rendering index page with save successfully")
		//set message if save successfully
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'navigationLinks.label', default: 'NavigationLinks'), navigationLinksInstance.id])
                redirect navigationLinksInstance
            }
            '*' { respond navigationLinksInstance, [status: CREATED] }
        }
    }
	
	/**
	 * Action invoked to get selected navigationLink instance.
	 * Action will render navigationLink instance in edit page.
	 *
	 * @param navigationLinksInstance		Selected instance of navigationsLinks
	 *
	 * @render Edit page with the selected navigationLink instance
	 */
    def edit(NavigationLinks navigationLinksInstance) {
        
		log.info("NavigationLinksController:edit:-		Rendering edit page with instance")
		respond navigationLinksInstance
    }
	
	/**
	 * Action to save entered navigationsLinks instance data into database
	 * Action will check if the instance is null or has error
	 * Action will invoke service method to save/update to database
	 *
	 * @param navigationLinksInstance		Selected instance of navigationsLinks
	 *
	 * @render Edit page if there is error while saving instance
	 * 		   Show page if save successfully
	 */
	def update(NavigationLinks navigationLinksInstance) {
		
		//check if the instance is null
		if (navigationLinksInstance == null) {
            
			notFound()
            return
        }
		
		//check if the instance has error
        if (navigationLinksInstance.hasErrors()) {
			
			log.info("NavigationLinksController:update:-		Rendering edit page with erros")
			respond navigationLinksInstance.errors, view:'edit'
            return
        }
		
		//invoke service method to update to database
        NavigationLinksService.saveNavigationLinks(navigationLinksInstance)
		
		log.info("NavigationLinksController:update:-		Rendering index page with update successfully")
		//set message if save successfully
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'NavigationLinks.label', default: 'NavigationLinks'), navigationLinksInstance.id])
                redirect navigationLinksInstance
            }
            '*'{ respond navigationLinksInstance, [status: OK] }
        }
    }

   /**
	 * Action to delete selected navigationsLinks from database
	 * Action will check if the instance is null or has error
	 * Action will invoke service method to delete it from database
	 *  
	 * @render index page if deleted successfully
	 * 		  
	 */
    def delete(NavigationLinks navigationLinksInstance) {

		//check if the instance is null
		if (navigationLinksInstance == null) {
            
			notFound()
            return
        }
		
		//invoke service method to delete to database
        NavigationLinksService.deleteNavigationLinks(navigationLinksInstance)
		
		log.info("NavigationLinksController:delete:-		Rendering index page with delete successfully")
		//set message if save successfully
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'NavigationLinks.label', default: 'NavigationLinks'), navigationLinksInstance.id])
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
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'navigationLinks.label', default: 'NavigationLinks'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
