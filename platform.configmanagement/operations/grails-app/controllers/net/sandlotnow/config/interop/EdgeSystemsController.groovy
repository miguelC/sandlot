package net.sandlotnow.config.interop

import java.util.regex.Pattern;
import java.util.regex.Pattern;
import net.sandlotnow.config.SessionConstants
import net.sandlotnow.config.interop.InteropServerConfig
import net.sandlotnow.config.interop.InteropServer
import net.sandlotnow.interop.ihe.model.EdgeDevice;
import net.sandlotnow.interop.ihe.model.EdgeDeviceType;
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeEndpointActor;
import net.sandlotnow.interop.ihe.model.EdgeOrganization
import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.ihe.model.EdgeTransaction

class EdgeSystemsController {
		
	def edgeSystemsService
	
	Pattern patternOIDMatch = java.util.regex.Pattern.compile('^\\d+(\\.\\d+)*$')
		
    def index() { 
		
		log.debug("EdgeSystemsController:index:Start:loading all edge systems")
		
		def edgeSystemList = edgeSystemsService.fetchEdgeSystems()		
					
		log.debug("EdgeSystemsController:index:End:loading the edge systems to index page")
		
		if(edgeSystemList == null) {			
		  render(view:"/error")		  
		}else {		
		  render( view : "index",model : [ interopServer: edgeSystemsService.interopServer, edgeSystemList : edgeSystemList])
		}
		
	}
	
	def edgeSystemById() {
		
		log.debug("EdgeSystemsController:edgeSystemById:Start:loading the edge system based on id")
		
		String edgeSystemId = params.id
		
		if(!edgeSystemId) {
			
			edgeSystemId = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		}
		
		
		log.debug("EdgeSystemsController:edgeSystemById:Sending edge system id to service method to fetch data")
		
		def edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemId)
		
		session.setAttribute(SessionConstants.EDGE_SYSTEM, edgeSystem)
				
		session.setAttribute(SessionConstants.EDGE_SYSTEM_ID, edgeSystemId)
		
		if(edgeSystem == null) {
			
		  log.debug("EdgeSystemsController:edgeSystemById:END:loading Error page")
		  
		  render(view:"/error")
		}else {
		   
		  log.debug("EdgeSystemsController:edgeSystemById:END:loading edge systems to edgeSystemById page")
		
		  render( view : "edgeSystemById",model : [ interopServer: edgeSystemsService.interopServer, edgeSystem : edgeSystem, edgeSystemId:edgeSystemId])
		}
	}
	
	def edgeOrganizations() {
		
		log.debug("EdgeSystemsController:edgeOrganizations:Start:loading all edge organizations")
						
		def edgeOrgsList = edgeSystemsService.fetchEdgeOrganizations()
		
		session.setAttribute(SessionConstants.EDGE_ORG_SOURCE, SessionConstants.EDGE_SYSTEM_LIST)
					
		if(edgeOrgsList == null) {
		  
		  log.debug("EdgeSystemsController:edgeOrganizations:Error:Error Occured while fetching data.")
			
		  render(view:"/error")
		}else {
		
		  render( view : "edgeOrganizations",model : [ interopServer: edgeSystemsService.interopServer, edgeOrgsList : edgeOrgsList , source : "edgeSystemList"])
		}
		
	}
	
	def edgeOrganizationsFromEdgeSystemDetails() {
		
		log.debug("EdgeSystemsController:edgeOrganizationsFromEdgeSystemDetails:Start:loading all edge organizations")
						
		def edgeOrgsList = edgeSystemsService.fetchEdgeOrganizations()
		
		session.setAttribute(SessionConstants.EDGE_ORG_SOURCE, SessionConstants.EDGE_SYSTEM_DETAILS)
					
		if(edgeOrgsList == null) {
		
		  log.debug("EdgeSystemsController:edgeOrganizationsFromEdgeSystemDetails:Error:Error Occured while fetching data.")
			
		  render(view:"/error")
		}else {
		
		  render( view : "edgeOrganizations",model : [ interopServer: edgeSystemsService.interopServer, edgeOrgsList : edgeOrgsList , source : "edgeSystemDetails"])
		}
		
	}
	
	def edgeOrganizationById() {
		
		log.debug("EdgeSystemsController:edgeOrganizationById:Start:loading the edge organization based on id")
		
		String edgeOrgId = params.id
		
		if(!edgeOrgId) {
			
			edgeOrgId = session.getAttribute(SessionConstants.EDGE_ORGANIZATION_ID)
		}
		
		def edgeOrg = new EdgeOrganization()
		if(edgeOrgId){
			
			edgeOrg = edgeSystemsService.fetchEdgeOrganizationById(edgeOrgId)
		} 
		
		session.setAttribute(SessionConstants.EDGE_ORGANIZATION_ID, edgeOrgId)
		
		def source = session.getAttribute(SessionConstants.EDGE_ORG_SOURCE)
		
		if(edgeOrg == null) {
			
		  log.debug("EdgeSystemsController:edgeOrganizationsById:END:loading Error page")
		  
		  render(view:"/error")
		}else {
		   
		  log.debug("EdgeSystemsController:edgeorganizationById:END:loading edge systems to edgeOrganizationById page")
		
		  render( view : "edgeOrganizationById",model : [ interopServer: edgeSystemsService.interopServer, edgeOrganization : edgeOrg, edgeOrganizationId:edgeOrgId,source:source])
		}
	}
	
	/**
	 * This method fetch the edgeEndPoint details, transaction list and actor list.
	 * After successfully fetching all information, it send back response to  editEdgeEndPoint template.
	 * All this details will be open in Pop-Up 
	 * 
	 * @return edgeEndPoint --> EdgeEndpoint instance
	 *         edgeTransactions -->  EdgeTransaction List
	 *         edgeSystemActorList --> Actor list  
	 */
	def getEdgeEndPointDetails() {
		
		log.debug("EdgeSystemsController:getEdgeEndPointDetails:Start:loading the edgeEndPoint Details.")		
		
		def edgeEndPointId= params.int('edgeSystemId')
		
		session.setAttribute(SessionConstants.EDGE_END_POINT_ID, edgeEndPointId)
		
		def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		
		log.debug("EdgeSystemsController:getEdgeEndPointDetails:sending request to fetch the edge System detail.")
				
		def edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
		
		if(edgeSystem == null) {
			
			log.debug("EdgeSystemsController:getEdgeEndPointDetails:END:error occured while fetching the edge System detail.")
			
			render "error"			
		}else {		
		
		    log.debug("EdgeSystemsController:getEdgeEndPointDetails:sending request to fetch the edge end point detail.")
		
			def edgeEndPoint = edgeSystemsService.getEdgeEndPoint(edgeEndPointId, edgeSystem)
			
			if(edgeEndPoint == null) {
				
			    log.debug("EdgeSystemsController:getEdgeEndPointDetails:END:error occured while fetching the edge end point detail.")	
							
			    render "error"			
			}else {
			    
			    log.debug("EdgeSystemsController:getEdgeEndPointDetails:sending request to fetch the transaction list")			
			
				def edgeTransactions = edgeSystemsService.getEdgeTransactions()
				
				def edgeSystemActorList = edgeSystemsService.getEdgeSystemActor()
				
				log.debug("EdgeSystemsController:getEdgeEndPointDetails:END:redering the template.")
								
				render(template:'editEdgeEndPoint', model:[ edgeEndPoint : edgeEndPoint , edgeTransactions : edgeTransactions , edgeSystemActorList : edgeSystemActorList])
			}
		}
	}
	
	/**
	 * This method fetch the details of Edge System Device based on received id.
	 * The fetched information will then render to UI.
	 * 
	 * @return
	 */
	def getEdgeSystemDeviceDetails() {
		
		log.debug("EdgeSystemsController:getEdgeSystemDeviceDetails:Start:loading the edge system device Details.")
				
		def edgeSystemDeviceId= params.int('edgeSystemDeviceId')
		
		def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		
		session.setAttribute(SessionConstants.EDGE_SYSTEM_DEVICE_ID, edgeSystemDeviceId)
		
		log.debug("EdgeSystemsController:getEdgeSystemDeviceDetails:sending request to fetch the edge System detail.")
				
		def edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
		
		if(edgeSystem == null) {
			
			log.debug("EdgeSystemsController:getEdgeSystemDeviceDetails:END:error occured while fetching the edge System detail.")
			
			render "error"
		}else {
		
		    log.debug("EdgeSystemsController:getEdgeSystemDeviceDetails:sending request to fetch the edge system device detail.")
		
			def edgeDevice = edgeSystemsService.getEdgeSystemDeviceDetail(edgeSystemDeviceId, edgeSystem)
			
			if(edgeDevice == null) {
				
				log.debug("EdgeSystemsController:getEdgeSystemDeviceDetails:END:error occured while fetching the edge system device detail.")
							
				render "error"
			}else {
				
				log.debug("EdgeSystemsController:getEdgeSystemDeviceDetails:sending request to fetch the transaction list")
			
				def edgeSystemDeviceList = edgeSystemsService.getEdgeDeviceType()				
				
				log.debug("EdgeSystemsController:getEdgeSystemDeviceDetails:END:redering the template.")
								
				render(template:'editEdgeSystemDevice', model:[ edgeDevice : edgeDevice , edgeSystemDeviceList : edgeSystemDeviceList])
			}
		}
		
	}
	
	/**
	 * This method is use to save the Edge System Device details into database.
	 * 
	 * @return
	 */
	def saveEdgeSystemDevice() {
		
		log.debug("EdgeSystemsController:saveEdgeSystemDevice:Start:start saving the edge system device data.")
		
		def deviceOID = params.deviceOID
		def deviceType = params.deviceType
		def editDeviceName = params.editDeviceName

		def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		def edgeSystemDeviceId = session.getAttribute(SessionConstants.EDGE_SYSTEM_DEVICE_ID)		
		
		log.debug("EdgeSystemsController:saveEdgeSystemDevice:sending request to fetch the edge system data.")
		
		def edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
				
		if(edgeSystem == null) {
			
			log.debug("EdgeSystemsController:saveEdgeSystemDevice:END:error occured while fetching the edge System detail.")
			
			render "error"
		}else {
		
		    EdgeDevice edgeDevice = edgeSystemsService.getEdgeSystemDeviceDetail(edgeSystemDeviceId, edgeSystem)
			
			def isCombinationAvailable = false
			
			if(edgeDevice.deviceType != EdgeDeviceType.valueOf(deviceType)) {
				
				isCombinationAvailable = edgeSystemsService.checkEdgeDeviceCombination(deviceType, edgeSystem, edgeDevice)
			}
			
			if(isCombinationAvailable) {
				
				log.debug("EdgeSystemsController:saveEdgeSystemDevice:END:DeviceType combination Already exits.")
				
				render "combinationError"
			} else{
			
				edgeDevice.setDeviceOID(deviceOID)
				edgeDevice.setDeviceType(EdgeDeviceType.valueOf(deviceType))
				edgeDevice.name = editDeviceName
				EdgeSystem edgeSystemInstance = edgeSystem
				
				edgeSystemInstance.getDevices().add(edgeDevice)
				
				log.debug("EdgeSystemsController:saveEdgeSystemDevice:sending request to save the edge system device data.")
				
				boolean savedData = edgeSystemsService.saveEdgeSystem(edgeSystemInstance)
				
				if(savedData) {
					
					log.debug("EdgeSystemsController:saveEdgeSystemDevice:END:Succesfully saved the edge system device data.rendering the page.")
					
					render "sucess"
				}else {
				   
				   log.debug("EdgeSystemsController:saveEdgeSystemDevice:END:Error occured while saving the edge system device data.")
				
				   render "error"
				}			
			}			
		}		
	}
	
	/**
	 * This method use to save the newly added Edge System Device details.
	 * 
	 * @return
	 */
	def saveNewEdgeSystemDevice() {
	
		log.debug("EdgeSystemsController:saveEdgeSystemDevice:Start:start saving the edge system device data.")
		
		def deviceOID = params.deviceOID
		def deviceType = params.deviceType
		def editDeviceName = params.editDeviceName
		
	
		EdgeDevice edgeDevice = new EdgeDevice()
		edgeDevice.deviceOID = deviceOID
		edgeDevice.deviceType = EdgeDeviceType.valueOf(deviceType)
		edgeDevice.name = editDeviceName
		
		def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		
		log.debug("EdgeSystemsController:saveNewEdgeSystemDevice:sending the request to fetch the edge sysytem detail.")
		
		EdgeSystem edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
		
		if(edgeSystem == null) {
			
			log.debug("EdgeSystemsController:saveNewEdgeSystemDevice:END:Error occured while fetching the edge system details.")
			
			render "error"
		}else {
		
			def isCombinationAvailable = edgeSystemsService.checkEdgeDeviceCombination(deviceType, edgeSystem, edgeDevice)
			
			if(isCombinationAvailable) {
				
				log.debug("EdgeSystemsController:saveEdgeSystemDevice:END:DeviceType combination Already exits.")
				
				render "combinationError"
			} else{		
			
				edgeDevice.edgeSystem = edgeSystem
				edgeSystem.getDevices().add(edgeDevice)
				
				log.debug("EdgeSystemsController:saveEdgeSystemDevice:Sending request to save new Edge end point entry.")
				
				boolean savedData = edgeSystemsService.saveEdgeSystem(edgeSystem)			
				
				if(savedData) {
					
					log.debug("EdgeSystemsController:saveEdgeSystemDevice:END:Data saved succesfully.Loaing the page.")
					
					render "sucess"
				}else {
				
					log.debug("EdgeSystemsController:saveEdgeSystemDevice:END:Error Occured while saving the Edge End point data.")
				
					render "error"
				}
				
			}		
		}
    }
	
	/**
	 * This method fetch the Edge Device type and then render thoes on UI to add new Edge System Device.
	 * 
	 * @return
	 */
	def addNewEdgeDevice() {
		
		log.debug("EdgeSystemsController:addNewEdgeDevice:Start:loading the details for adding new edge system device entry.")
		
		def edgeSystemDeviceList = edgeSystemsService.getEdgeDeviceType()
		
		log.debug("EdgeSystemsController:addNewEdgeDevice:End:rendering the details for adding new edge system device entry.")
				
		render(template:'addEdgeSystemDevice', model:[edgeSystemDeviceList : edgeSystemDeviceList])
		
	}
	
	
	/**
	 * This method save the updated data about EdgeEndPoint.
	 * 
	 * @return "error/Success/combinationError" error
	 */
	def saveEdgeEndPoint () {
	
		log.debug("EdgeSystemsController:saveEdgeEndPoint:Start:start saving the edge end point data.")
		
		def actor = params.actor
		def camelEndpointName = params.camelEndpointName
		def transaction = params.transaction
		
		def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		def edgeEndPointId = session.getAttribute(SessionConstants.EDGE_END_POINT_ID)
	
		log.debug("EdgeSystemsController:saveEdgeEndPoint:sending request to fetch the edge system data.")
		
		def edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
		
		if(edgeSystem == null) {
			
			log.debug("EdgeSystemsController:saveEdgeEndPoint:END:error occured while fetching the edge System detail.")
			
			render "error"			
		}else {
		
			EdgeEndpoint edgeEndPoint = edgeSystemsService.getEdgeEndPoint(edgeEndPointId, edgeSystem)
			
			def isCombinationAvailable = false
			
			if(!edgeEndPoint.actor.equals(actor) && !edgeEndPoint.transaction.name.equals(transaction)) {
				
				isCombinationAvailable = edgeSystemsService.checkEdgeEndPointCombination(actor, transaction,edgeSystem,edgeEndPoint)
			}			
			
			if(isCombinationAvailable) {
				
				log.debug("EdgeSystemsController:saveEdgeEndPoint:END:Actor and Transaction combination Already exits.")
				
				render "combinationError"
			} else {
				
				if(actor.equals(SessionConstants.RECEIVER)) {
					
					edgeEndPoint.setActor(EdgeEndpointActor.RECEIVER)
				}else {
				
					edgeEndPoint.setActor(EdgeEndpointActor.SENDER)
				}
				
				edgeEndPoint.setCamelEndpointName(camelEndpointName)
				
				log.debug("EdgeSystemsController:saveEdgeEndPoint:loading the transaction Details.")
								
				EdgeTransaction edgeTransaction = edgeSystemsService.getTransactionDetail(transaction)
				
				edgeEndPoint.setTransaction(edgeTransaction)
				
				EdgeSystem edgeSystemInstance = edgeSystem
				
				edgeSystemInstance.getEndpoints().add(edgeEndPoint)
				
				log.debug("EdgeSystemsController:saveEdgeEndPoint:sending request to save the edge end point data.")
				
				boolean savedData = edgeSystemsService.saveEdgeSystem(edgeSystemInstance)
				
				if(savedData) {
					
					log.debug("EdgeSystemsController:saveEdgeEndPoint:END:Succesfully saved the Edge End Point data.rendering the page.")
					
					render "sucess"
				}else {
				   
				   log.debug("EdgeSystemsController:saveEdgeEndPoint:END:Error occured while saving the Edge End Point data.")
				
				   render "error"
				}				
			}		
		}				
	}	
	
	/**
	 * This method use to render the updated details on page.
	 * 
	 * @return edgeSystem --> EdgeSystem instance
	 *                        edgeSystemId --> Edge System id 
	 */
	def reloadEdgeSystemDetails() {
		
		log.debug("EdgeSystemsController:reloadEdgeSystemDetails:reloadind the edge system details.")
				
		def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
	
		log.debug("EdgeSystemsController:reloadEdgeSystemDetails:sending the request to load edge system detail.")
		
		def edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
		
		if(edgeSystem == null) {
			
			log.debug("EdgeSystemsController:reloadEdgeSystemDetails:END:Error occured while fetching the edgeSystem data.")
			
			redirect(action: "errorPage")
		}else {		
		
		    log.debug("EdgeSystemsController:reloadEdgeSystemDetails:END:loading edgeSystemById page.")
		
			def edgeSystemId = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
			
			render( view : "edgeSystemById",model : [ interopServer: edgeSystemsService.interopServer, edgeSystem : edgeSystem, edgeSystemId:edgeSystemId])
		}		
	}
	
	/**
	 * This method fetch the transaction list and actor list and render it to pop-up.
	 * This Pop-up will be use for adding new entry of Edge Point.
	 * 
	 * @return edgeTransactions -->  EdgeTransaction List
	 *         edgeSystemActorList --> Actor list 
	 */
	def addEdgeEndPoint() {
		
		log.debug("EdgeSystemsController:addEdgeEndPoint:Start:loading the details for adding new edge end point entry.")
				
		def edgeTransactions = edgeSystemsService.getEdgeTransactions()
		
		def edgeSystemActorList = edgeSystemsService.getEdgeSystemActor()
		
		log.debug("EdgeSystemsController:addEdgeEndPoint:End:rendering the details for adding new edge end point entry.")
				
		render(template:'addEdgePoint', model:[ edgeTransactions : edgeTransactions , edgeSystemActorList : edgeSystemActorList])
	}
	
	/**
	 * This method use to add the new EdgeEndPoint. 
	 * 
	 * @return "error/Success/combinationError" error
	 */
	def addNewEdgeEndPoint() {
		
		log.debug("EdgeSystemsController:addNewEdgeEndPoint:Start:start saving the new entry for edge end point.")
		
		def actor = params.actor
		def camelEndpointName = params.camelEndpointName
		def transaction = params.transaction
		
		EdgeEndpoint edgeEndPoint = new EdgeEndpoint()
		edgeEndPoint.actor = actor 
		edgeEndPoint.camelEndpointName = camelEndpointName
		
		log.debug("EdgeSystemsController:addNewEdgeEndPoint:loading the transaction details.")
		
		EdgeTransaction edgeTransac = edgeSystemsService.getTransactionDetail(transaction)		
		edgeEndPoint.setTransaction(edgeTransac)
		
		def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		
		log.debug("EdgeSystemsController:addNewEdgeEndPoint:sending the request to fetch the edge sysytem detail.")
		
		EdgeSystem edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
		
		if(edgeSystem == null) {			
			
			log.debug("EdgeSystemsController:addNewEdgeEndPoint:END:Error occured while fetching the edge system details.")
			
			render "error"
		}else {
		
			def isCombinationAvailable = edgeSystemsService.checkEdgeEndPointCombination(actor, transaction,edgeSystem,edgeEndPoint)
			
			if(isCombinationAvailable) {
				
				log.debug("EdgeSystemsController:addNewEdgeEndPoint:End:Actor and Transaction combination Already exits.")
				
				render "combinationError"
			}else {
			
				edgeEndPoint.edgeSystem = edgeSystem
				edgeSystem.getEndpoints().add(edgeEndPoint)
				
				log.debug("EdgeSystemsController:addNewEdgeEndPoint:Sending request to save new Edge end point entry.")
				
				boolean savedData = edgeSystemsService.saveEdgeSystem(edgeSystem)
			
				if(savedData) {
					
					log.debug("EdgeSystemsController:addNewEdgeEndPoint:END:Data saved succesfully.Loaing the page.")
					
					render "sucess"
				}else {
				
				    log.debug("EdgeSystemsController:addNewEdgeEndPoint:END:Error Occured while saving the Edge End point data.")
				
				    render "error"
				}
			}
		}			
	}
	
	/**
	 * This method use to save the selected organization for particular Edge System.
	 *  
	 * @return
	 */
	def saveSelectedOrganization() {
		
		def orgId = params.edgeOrgId
		
	    log.debug("EdgeSystemsController:saveSelectedOrganization:Start:fetching the organization based on orgnization ID")
		
        def orgnizationByID = edgeSystemsService.fetchEdgeOrganizationById(orgId)
       
		if(orgnizationByID == null) {
			
			render "error"			
		}else {
		
		     def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
		
			 log.debug("EdgeSystemsController:saveSelectedOrganization:sending request to fetch the edge system data.")
			
			 EdgeSystem edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
			
			 if(edgeSystem == null) {
				
				log.debug("EdgeSystemsController:saveSelectedOrganization:END:error occured while fetching the edge System detail.")
				
				render "error"
			 }else {			
				 
			    edgeSystem.setOrganization(orgnizationByID)
								
				log.debug("EdgeSystemsController:saveSelectedOrganization:sending request to save the edge end point data.")
				
				boolean savedData = edgeSystemsService.saveEdgeSystem(edgeSystem)
				
				if(savedData) {
					
					log.debug("EdgeSystemsController:saveSelectedOrganization:END:Succesfully saved the data.rendering the page.")
					
					render "sucess"
				}else {
				   
				   log.debug("EdgeSystemsController:saveSelectedOrganization:END:Error occured while saving data.")
				
				   render "error"
				}			 
			 }
		
		}    
	}
	
	def saveEdgeOrganization() {
		
		log.debug("EdgeSystemsController:saveEdgeOrganization:Start:saving the edge organization")
		
		def paramId = params.id
		
		def edgeOrgId = paramId? paramId.toInteger() : null
		def edgeOrgName = params.name
		def edgeOrgShortName = params.shortName
		def edgeOrgOid = params.organizationOID
		
		def edgeOrg = new EdgeOrganization()
		if(edgeOrgId > 0){
		  edgeOrg.id = edgeOrgId
		}
		edgeOrg.name = edgeOrgName
		edgeOrg.shortName = edgeOrgShortName
		edgeOrg.organizationOID = edgeOrgOid
		
		def source = session.getAttribute(SessionConstants.EDGE_ORG_SOURCE)
				
		def isOrganizationIsExits = edgeSystemsService.checkOrganizationExits(edgeOrgName, edgeOrgShortName, edgeOrgOid)
		
		if(true) {
			
			flash.message = message(code: 'duplicate.entry.error')
			render( view : "edgeOrganizationById",model : [ interopServer: edgeSystemsService.interopServer, edgeOrganization : edgeOrg, edgeOrganizationId:edgeOrgId,source:source])
		}else{
		 
			boolean savedStatusSuccess = false
			def savedStatusMessage
			
			try {
				
				edgeOrg = edgeSystemsService.saveEdgeOrganization(edgeOrg)
				savedStatusSuccess = true
				savedStatusMessage = "Successfully saved organization with id ${edgeOrg.id}"
			}
			catch(InteropServiceException ise) {
				savedStatusSuccess = false
				savedStatusMessage = "Error saving organization with id ${edgeOrg.id}. Check the service logs."
			}
			catch(Exception ex) {
				savedStatusSuccess = false
				savedStatusMessage = "Error saving organization with id ${edgeOrg.id}. Check the application logs."
			}
			
			if(edgeOrg == null) {
				
				log.debug("EdgeSystemsController:saveEdgeOrganization:END:loading Error page")
				render(view:"/error")
			}else {			
				
				if(source.equals(SessionConstants.EDGE_SYSTEM_DETAILS)) {
					 
				  def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
			 
				  log.debug("EdgeSystemsController:saveEdgeOrganization:sending request to fetch the edge system data.")
				 
				  EdgeSystem edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
				 
				  if(edgeSystem == null) {
					 
					 log.debug("EdgeSystemsController:saveEdgeOrganization:END:error occured while fetching the edge System detail.")
					 
					 render(view:"/error")
				  }else {
				  
					 def orgDetails = edgeSystemsService.fetchEdgeOrgBasedOnName(edgeOrg.name)
					  
					 edgeSystem.setOrganization(orgDetails)
									 
					 log.debug("EdgeSystemsController:saveEdgeOrganization:sending request to save the edge end point data.")
					 
					 boolean savedData = edgeSystemsService.saveEdgeSystem(edgeSystem)
					 
					 if(savedData) {
						 
						 log.debug("EdgeSystemsController:saveEdgeOrganization:END:Succesfully saved the  data.rendering the page.")
						 
						 redirect(action:"reloadEdgeSystemDetails")
					 }else {
						
						log.debug("EdgeSystemsController:saveEdgeOrganization:END:Error occured while saving the data.")
					 
						render(view:"/error")
					 }
				  }
							  
							  
				}else {
				
					edgeOrgId = edgeOrg.id
					session.setAttribute(SessionConstants.EDGE_ORGANIZATION_ID, edgeOrgId)
					log.debug("EdgeSystemsController:saveEdgeOrganization:END:saved")
					render( view : "edgeOrganizationById",model : [ interopServer: edgeSystemsService.interopServer, edgeOrganization : edgeOrg, edgeOrganizationId:edgeOrgId, savedStatusSuccess:savedStatusSuccess, savedStatusMessage: savedStatusMessage,source:source])
				}
			}		
		}	
	}
	
	
	def saveEdgeSystem() {
		
		log.debug("EdgeSystemsController:saveEdgeSystem:Start:start saving the edge System data.")
				
		def name = params.name
		def assigningAuthorityOID = params.assigningAuthorityOID
		def documentSourceOID = params.documentSourceOID
		
		boolean duplicateEntry = edgeSystemsService.checkEdgeSystemDuplicateEntry(name,assigningAuthorityOID,documentSourceOID)
		
		if(duplicateEntry) {
			
			log.debug("EdgeSystemsController:saveEdgeSystem:END:Succesfully saved the data.rendering the page.")
			
			flash.message = message(code: 'edgesystem.duplicate.entry.error')
			redirect(action:"reloadEdgeSystemDetails" , params:[duplicateRecordExits: true])
		}else {
		
			def edgeSystemID = session.getAttribute(SessionConstants.EDGE_SYSTEM_ID)
			
			log.debug("EdgeSystemsController:saveEdgeSystem:sending request to fetch the edge system data.")
		
			EdgeSystem edgeSystem = edgeSystemsService.fetchEdgeSystemById(edgeSystemID)
		
			if(edgeSystem == null) {
			
			  log.debug("EdgeSystemsController:saveEdgeSystem:END:error occured while fetching the edge System detail.")
			
			  render(view:"/error")
			}else {
		 
			  edgeSystem.name = name
			  edgeSystem.assigningAuthorityOID = assigningAuthorityOID
			  edgeSystem.documentSourceOID = documentSourceOID
			  
			  log.debug("EdgeSystemsController:saveEdgeSystem:sending request to save the edge end point data.")
			  
			  boolean savedData = edgeSystemsService.saveEdgeSystem(edgeSystem)
			  
			  if(savedData) {
				  
				  log.debug("EdgeSystemsController:saveEdgeSystem:END:Succesfully saved the data.rendering the page.")
				  
				  redirect(action:"reloadEdgeSystemDetails")
			  }else {
				 
				 log.debug("EdgeSystemsController:saveEdgeSystem:END:Error occured while saving the data.")
			  
				 render(view:"/error")
			  }
			}		
		}
	}
	
	/**
	 * Load the error page with specified error
	 * @return Error
	 */
	def errorPage() {
				
		//return user to error page in case of any error.
		log.debug("EdgeSystemsController:errorPage:Start:End:rendering error to error page")
				
		render(view:"/error")
	}
}
