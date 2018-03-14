/**
 *======================================================================================
 * BlacklistController.groovy
 *======================================================================================
 *
 *	Definitions for class:
 *		- Blacklist organization controller
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			08/24/2015
 *  				Original development
 *  @author 		Devendra Patil
 *  @version 		1.0
 *  Description:  	Render Blacklist Page.
 *					Save/Update, delete Blacklist instance from database
 *
 *
 *
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

 //Package
package net.sandlotnow.config

class BlacklistController {

	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	def BlacklistService
	
	// PROPERTY ASSOCIATIONS & AGGREGATIONS
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	
	//===================================================================
	// PUBLIC FUNCTIONS
	//===================================================================
	/**
	 * Action invoked as default controller action.
	 * Action will render blacklist organization list in current controller index page.
	 *
	 * @render Current controller index page
	 */
	def index(Integer max) {
		
		log.info("BlacklistController:index:-Rendering blacklist organization list")
		params.max = Math.min(max ?: 10, 100)
		respond Blacklist.list(params), model:[blackListInstanceCount: Blacklist.count()]
	}
	
	
	
	/**
	 * Action to empty the saved blacklist organization instance from modal
	 *
	 * @render Create page of blacklist organization to show empty fields
	 */
	def create() {
		
		log.info("BlacklistController:create:-Rendering empty create page")
		respond new Blacklist(params)
	}

	/**
	* Action to save entered blacklist organization instance data into database
	* Action will check if the instance is null or has error
	* Action will invoke service method to save/update to database
	*
	* @param blacklistsInstance		Selected instance of blacklist organization
	*
	* @render Create page if there is error while saving instance
	* 		   Show page if save successfully
	*/
   def save(Blacklist blacklistsInstance) {
	   
	   //check if the instance is null
	   if (blacklistsInstance == null) {
		   
		   notFound()
		   return
	   }
	   
	   //check if the instance has error
	   if (blacklistsInstance.hasErrors()) {
		   
		   log.info("BlacklistController:save:-		Rendering create page with erros")
		   respond blacklistsInstance.errors, view:'create'
		   return
	   }
	   
	   //invoke service method to save to database
	   BlacklistService.saveBlackList(blacklistsInstance)
	   
	   log.info("BlacklistController:save:-		Rendering index page with save successfully")
	   //set message if save successfully
	   request.withFormat {
		   form multipartForm {
			   flash.message = message(code: 'default.created.message', args: [message(code: 'sad.label', default: 'Blacklist'), blacklistsInstance.id])
			   redirect blacklistsInstance
		   }
		   '*' { respond blacklistsInstance, [status: CREATED] }
	   }
   }
   
   
   /**
   * Action invoked to get selected blacklist organization instance.
   * Action will render blacklist organization instance in show page.
   *
   * @render Show page of blacklist organization to show selected blacklist organization instance
   */
  def show(Blacklist blacklistInstance) {
	  
	  log.info("BlacklistController:show:-Rendering show instance page")
	  respond blacklistInstance
  }
   
  
  /**
  * Action invoked to get selected blacklist organization instance.
  * Action will render blacklist organization instance in edit page.
  *
  * @param blacklistInstance		Selected instance of blacklist organization
  *
  * @render Edit page with the selected blacklist organization instance
  */
 def edit(Blacklist blacklistInstance) {
	 
	 log.info("BlacklistController:edit:-Rendering edit page with instance")
	 respond blacklistInstance
 }
  
 /**
 * Action to save entered blacklist organization instance data into database
 * Action will check if the instance is null or has error
 * Action will invoke service method to save/update to database
 *
 * @param blacklistInstance		Selected instance of blacklist organization
 *
 * @render Edit page if there is error while saving instance
 * 		   Show page if save successfully
 */
def update(Blacklist blacklistInstance) {
	
	//check if the instance is null
	if (blacklistInstance == null) {
		
		notFound()
		return
	}
	
	//check if the instance has error
	if (blacklistInstance.hasErrors()) {
		
		log.info("BlacklistController:update:-		Rendering edit page with erros")
		respond blacklistInstance.errors, view:'edit'
		return
	}
	
	//invoke service method to update to database
	BlacklistService.saveBlackList(blacklistInstance)
	
	log.info("BlacklistController:update:-		Rendering index page with update successfully")
	//set message if save successfully
	request.withFormat {
		form multipartForm {
			flash.message = message(code: 'default.updated.message', args: [message(code: 'dsfds.label', default: 'Blacklist'), blacklistInstance.id])
			redirect blacklistInstance
		}
		'*'{ respond blacklistInstance, [status: OK] }
	}
}
 
 

/**
* Action to delete selected blacklist organization from database
* Action will check if the instance is null or has error
* Action will invoke service method to delete it from database
*
* @render index page if deleted successfully
*
*/
def delete(Blacklist blacklistInstance) {

   //check if the instance is null
   if (blacklistInstance == null) {
	   
	   notFound()
	   return
   }
   
   //invoke service method to delete to database
   BlacklistService.deleteBlackListOrganization(blacklistInstance)
   
   log.info("BlacklistController:delete:-		Rendering index page with delete successfully")
   //set message if save successfully
   request.withFormat {
	   form multipartForm {
		   flash.message = message(code: 'default.deleted.message', args: [message(code: 'dsfds.label', default: 'Blacklist'), blacklistInstance.id])
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
			   flash.message = message(code: 'default.not.found.message', args: [message(code: 'sad.label', default: 'Blacklist'), params.id])
			   redirect action: "index", method: "GET"
		   }
		   '*'{ render status: NOT_FOUND }
	   }
   }   
  
	 

}
