// Place your Spring DSL code here
import java.util.HashMap
import net.sandlotnow.config.interop.InteropServer
import net.sandlotnow.config.interop.InteropServerConfig

beans = {
	
	 'localhostInterop'(InteropServer){
		 id = 'localhostInterop'
		 name= 'Localhost'
		 description = 'Localhost Interop Services'
		 baseUri= 'http://localhost:8080/interop/services'
	 }
	 
	 'qaInterop'(InteropServer){
		 id = 'qaInterop'
		 name= 'QA Interop'
		 description= 'QA Interop Services'
		 baseUri= 'http://172.16.101.59:8080/interop/services'
	 }
	 
	 'mccTestInterop'(InteropServer){
		 id = 'mccTestInterop'
		 name= 'MCC Test Interop'
		 description= 'MCC Test Interop Services'
		 baseUri= 'http://172.16.237.30:8080/interop/services'
	 }
	 
	 'mccProdInterop'(InteropServer){
		 id = 'mccProdInterop'
		 name= 'MCC Production Interop'
		 description= 'MCC Production Interop Services'
		 baseUri= 'http://172.16.238.62:8080/interopServices/services'
	 }
	 
	 'ohpTestInterop'(InteropServer){
		 id = 'ohpTestInterop'
		 name= 'OHP Test Interop'
		 description= 'OHP Test Interop Services'
		 baseUri= 'http://172.16.235.31:8080/interop/services'
	 }
	 
	 'ohpProdInterop'(InteropServer){
		 id = 'ohpProdInterop'
		 name= 'OHP Production Interop'
		 description= 'OHP Production Interop Services'
		 baseUri= 'http://172.16.236.62:8080/interopServices/services'
	 }
	 
	 'nueQaInterop'(InteropServer){
		 id = 'nueQaInterop'
		 name= 'Nueterra QA Interop'
		 description= 'Nueterra QA Interop Services'
		 baseUri= 'http://172.16.244.32:8080/interop/services'
	 }
	 
	 'nueUatInterop'(InteropServer){
		 id = 'nueUatInterop'
		 name= 'Nueterra UAT Interop'
		 description= 'Nueterra UAT Interop Services'
		 baseUri= 'http://172.16.245.10:8080/interop/services'
	 }
	 
	 'gwayDemoInterop'(InteropServer){
		 id = 'gwayDemoInterop'
		 name= 'Greenway Demo Interop'
		 description= 'Grenway Demo Interop Services'
		 baseUri= 'http://172.16.102.30:8080/interop/services'
	 }
	 
	'serverConfigMap'(InteropServerConfig){
		defaultServerName = 'localhostInterop'
		serverList = [localhostInterop, qaInterop, mccTestInterop, mccProdInterop, ohpTestInterop, ohpProdInterop, nueQaInterop, nueUatInterop, gwayDemoInterop]
	}
  
}
