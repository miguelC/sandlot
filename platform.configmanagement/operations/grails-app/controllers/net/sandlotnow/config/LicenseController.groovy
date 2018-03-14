/**
 *======================================================================================
 * LicenseController.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- License list controller
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			17/08/2015
 *  				Original development
 *  @author 		Praveen Kamble
 *  @version 		1.0
 *  Description:  	Render License Page.
 *  				Save/Update, delete license instance from database
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
 * Controller class to handle the License list
 * @author praveen13126
 *
 */
class LicenseController {
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	def LicenseService
	
	// STATIC DEFINITIONS
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	//===================================================================
	// PUBLIC FUNCTIONS
	//===================================================================
	/**
	 * Action invoked as default controller action.
	 * Action will render license list in current controller index page.
	 *
	 * @render Current controller index page
	 */
    def index(Integer max) {
       
		log.info("LicenseController:index:-		Rendering license list")
		params.max = Math.min(max ?: 10, 100)
        respond License.list(params), model:[licenseInstanceCount: License.count()]
    }
	
	/**
	 * Action invoked to get selected license instance.
	 * Action will render license instance in show page.
	 *
	 * @render Show page of license to show selected license instance
	 */
    def show(License licenseInstance) {
        
		log.info("LicenseController:show:-		Rendering show instance page")
		respond licenseInstance
    }
	
	/**
	 * Action to empty the saved license instance from modal
	 *
	 * @render Create page of license to show empty fields
	 */
    def create() {
        
		log.info("LicenseController:create:-		Rendering empty create page")
		respond new License(params)
    }

	/**
	 * Action to save entered license instance data into database
	 * Action will check if the instance is null or has error
	 * Action will invoke service method to save/update to database
	 *
	 * @param licenseInstance		Selected instance of license
	 *
	 * @render Create page if there is error while saving instance
	 * 		   Show page if save successfully
	 */
    def save(License licenseInstance) {
        
		//check if the instance is null
		if (licenseInstance == null) {
            
			notFound()
            return
        }
		
		//check if the instance has error
        if (licenseInstance.hasErrors()) {
            
			log.info("LicenseController:save:-		Rendering create page with erros")
			respond licenseInstance.errors, view:'create'
            return
        }
		
		//invoke service method to update to database
		LicenseService.saveLicense(licenseInstance)
		
		log.info("LicenseController:save:-		Rendering index page with save successfully")
		//set message if save successfully
		request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'license.label', default: 'License'), licenseInstance.id])
                redirect licenseInstance
            }
            '*' { respond licenseInstance, [status: CREATED] }
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
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'license.label', default: 'License'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
