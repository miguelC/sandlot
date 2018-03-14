package net.sandlotnow.config.interop

import java.util.HashMap

class InteropServerConfig {

   String defaultServerName	
	
   HashMap<String, InteropServer> servers
   
   void setServerList(List<InteropServer> serversList){
	   servers = new HashMap<String, InteropServer>()
	   serversList.each {
		   servers.put(it.getId(), it)
	   }
   }
   
   InteropServer getServer(String name){
	   if(!name){
		   return servers.get(defaultServerName)
	   }
	   else{
		   return servers.get(name)
	   }
   }
}
