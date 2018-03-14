package net.sandlotnow.config.interop

import net.sandlotnow.config.SessionConstants
import net.sandlotnow.config.interop.InteropServerConfig
import net.sandlotnow.config.interop.InteropServer

class InteropServerController {

	def grailsApplication
	
    def index() { 
		
		log.info("InteropServerController:index:Start:loading the servers")
		
		def interopServerConfig = grailsApplication.mainContext.getBean('serverConfigMap', InteropServerConfig)
		
		log.info("InteropServerController:index:Start:serverconfig = " + interopServerConfig.servers.toString())
		def serverMap = interopServerConfig.servers
		
		def serverList = new ArrayList()
		serverMap.each(){
			serverList.add(it.value)
		}	
			
		if(serverList == null) {
			
		  render(view:"/error")
		}else {
		
		  render( view : "index",model : [ serverList : serverList])
		}
	}
	
	def gotoServerAudits() {
				
		String serverName = params.id
		
		log.debug("InteropServerController:gotoServerAudits:Start:going to server audits for server ${serverName}")
		
		session.setAttribute(SessionConstants.INTEROP_SERVER_NAME, serverName)
		
		if(serverName == null) {
			
		  log.debug("InteropServerController:gotoServerAudits:Start:Error page")
		  
		  render(view:"/error")
		}else {
		   
		  log.debug("InteropServerController:gotoServerAudits:Start:going  page")
		
		  redirect(controller: "auditMessage", action : "index")
		}
	}
	
	def gotoServerEdgeSystems() {
				
		String serverName = params.id
		
		log.debug("InteropServerController:gotoEdgeSystems:Start:going to edge systems for server ${serverName}")
		
		session.setAttribute(SessionConstants.INTEROP_SERVER_NAME, serverName)
		
		if(serverName == null) {
			
		  log.debug("InteropServerController:gotoEdgeSystems:Start:Error page")
		  
		  render(view:"/error")
		}else {
		   
		  log.debug("InteropServerController:gotoEdgeSystems:Start:going  page")
		
		  redirect(controller: "edgeSystems", action : "index")
		}
	}
}
