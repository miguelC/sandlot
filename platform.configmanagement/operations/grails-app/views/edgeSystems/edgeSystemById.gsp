<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="interop.edgesystem.title.edgeSystem"/></title>
<g:javascript>    
     
     function openMessage(){
       $("#viewMessage").modal("show");  
     }
     
     function closeModal() {
       
       $("#editEdgeSystem").modal("hide");
       $("#addEdgeEndPoint").modal("hide");  
       $("#editEdgeSystemDevice").modal("hide"); 
       $("#addEdgeSystemDevice").modal("hide");                  
     }
     
     
     function validateEdgePointFeild() {
     
        if($("#editCamelEndPoint").val() == null || $("#editCamelEndPoint").val().trim() == "") {
        
           $(".CamelEndPointError").css("display","block");
           return false;
        }         
        $(".CamelEndPointError").css("display","none");
        return true;
     }
     
     function validateNewEdgePointFeild() {
     
        if($("#addCamelEndPoint").val() == null || $("#addCamelEndPoint").val().trim() == ""){
        
           $(".CamelEndPointError").css("display","block");
           return false;        
        }
         
        $(".CamelEndPointError").css("display","none");
        return true;
     }
     
     function validateEdgeSystemDeviceFeild() {     
     
        $(".deviceNameError").css("display","none");
        $(".deviceOIDError").css("display","none");
        $(".addDeviceOIDMatchingError").css("display","none");	       
     
        if($("#editDeviceName").val() == null || $("#editDeviceName").val().trim() == "") {
        
           $(".deviceNameError").css("display","block");
           return false;
        }
        if($("#editDeviceOID").val() == null || $("#editDeviceOID").val().trim() == ""){        
           $(".deviceOIDError").css("display","block");
           return false;        
        }if(!$("#editDeviceOID").val().match($("#editDeviceOID").prop('pattern'))){
          
            $(".addDeviceOIDMatchingError").css("display","block");	           
            return false;
        }                
        return true;  
     }
     
     function validateNewEdgeSystemDeviceFeild() {   
       
        $(".addDeviceNameError").css("display","none");
        $(".addDeviceOIDError").css("display","none");
        $(".addDeviceOIDMatchingError").css("display","none");	      
       
        if($("#addDeviceName").val() == null || $("#addDeviceName").val().trim() == ""){        
           $(".addDeviceNameError").css("display","block");
           return false;        
        } 
        if($("#addDeviceOID").val() == null || $("#addDeviceOID").val().trim() == ""){        
           $(".addDeviceOIDError").css("display","block");
           return false;        
        }if(!$("#addDeviceOID").val().match($("#addDeviceOID").prop('pattern'))){
          
            $(".addDeviceOIDMatchingError").css("display","block");	           
            return false;
        }                  
        return true;  
     }
     
     function editEdgeSystemPoint(id) {       
          $.ajax({		      
			url:appContext+'/edgeSystems/getEdgeEndPointDetails',
			type:'GET',
			data:{edgeSystemId:id},
            success:function(data,textStatus) {
              
              if(data == "error"){
              
                  $("#errorMessage").modal("show");              
              }else{		                
                
	              jQuery('#edgeSystemPopoup').html(data);
	              $("#editEdgeSystem").modal("show");
              } 	        		              
            },
            error:function(XMLHttpRequest,textStatus,errorThrown) {
            
                  $("#errorText").text(errorThrown);
                  $("#errorMessage").modal("show");
            }
		 });
	}
	
	function editEdgeSystemDevice(id) {       
          $.ajax({		      
			url:appContext+'/edgeSystems/getEdgeSystemDeviceDetails',
			type:'GET',
			data:{edgeSystemDeviceId:id},
            success:function(data,textStatus) {
              
              if(data == "error"){
              
                  $("#errorMessage").modal("show");              
              }else{		                
                
	              jQuery('#edgeSystemPopoup').html(data);
	              $("#editEdgeSystemDevice").modal("show");
              } 	        		              
            },
            error:function(XMLHttpRequest,textStatus,errorThrown) {
            
                  $("#errorText").text(errorThrown);
                  $("#errorMessage").modal("show");
            }
		 });
	}
	
	function saveEdgeSystemDevice() {  
	
	    if(validateEdgeSystemDeviceFeild()) {
	    
	      $.ajax({		      
				url:appContext+'/edgeSystems/saveEdgeSystemDevice',
				type:'GET',
				data:{ deviceOID : $("#editDeviceOID").val() , deviceType : $("#editDeviceType").val(),editDeviceName:$("#editDeviceName").val()},
		           success:function(data,textStatus) {
		              console.log(data);
		              if(data == "error"){	
		              	              
		                  $("#commonError").css("display","block");		              
		              }if(data == "combinationError"){
		              
		                  $("#combinationError").css("display","block");
		              }if(data != "error" && data != "combinationError"){                
		                
		                  $("#commonError").css("display","none");
		                  $("#combinationError").css("display","none");	             
			              $("#editEdgeSystemDevice").modal("hide");
			              $("#reloadEdgeSystem").click();  			           
		              } 	        		              
		        },
		        error:function(XMLHttpRequest,textStatus,errorThrown) {
		           
		                 $("#errorText").text(errorThrown);
		                 $("#errorMessage").modal("show");
		        }
		 });
	    
	    }        
     }
		 
	function saveEdgeSystemPoint() { 
	
	    if(validateEdgePointFeild()) {
	    
		       $.ajax({		      
					url:appContext+'/edgeSystems/saveEdgeEndPoint',
					type:'GET',
					data:{ camelEndpointName : $("#editCamelEndPoint").val() , actor : $("#editActor").val(), transaction : $("#editTransaction").val() },
		            success:function(data,textStatus) {
		              
		              if(data == "error"){	
		              	              
		                  $("#commonError").css("display","block");		              
		              }if(data == "combinationError"){
		              
		                  $("#combinationError").css("display","block");
		              }else{                
		                
		                  $("#commonError").css("display","none");
		                  $("#combinationError").css("display","none");	             
			              $("#editEdgeSystem").modal("hide");
			              $("#reloadEdgeSystem").click();  			           
		              } 	        		              
		            },
		            error:function(XMLHttpRequest,textStatus,errorThrown) {
		            
		                  $("#errorText").text(errorThrown);
		                  $("#errorMessage").modal("show");
		            }
			 });
	    }      
     }
     
     function addEdgeEndPointPopUp() {       
          $.ajax({		      
			url:appContext+'/edgeSystems/addEdgeEndPoint',
			type:'GET',
            success:function(data,textStatus) {
              
              if(data == "error"){	
              	              
                  $("#commonError").css("display","block");		              
              }else{                
                
                  $("#commonError").css("display","none");
                  jQuery('#edgeSystemPopoup').html(data);	             
	              $("#addEdgeEndPoint").modal("show");
              } 	        		              
            },
            error:function(XMLHttpRequest,textStatus,errorThrown) {
            
                  $("#errorText").text(errorThrown);
                  $("#errorMessage").modal("show");
            }
		 });
       
     }
     
     function addEdgeSystemDevicePopUp() {       
          $.ajax({		      
			url:appContext+'/edgeSystems/addNewEdgeDevice',
			type:'GET',
            success:function(data,textStatus) {
              
              if(data == "error"){	
              	              
                  $("#commonError").css("display","block");		              
              }else{                
                
                  $("#commonError").css("display","none");
                  jQuery('#edgeSystemPopoup').html(data);	             
	              $("#addEdgeSystemDevice").modal("show");
              } 	        		              
            },
            error:function(XMLHttpRequest,textStatus,errorThrown) {
            
                  $("#errorText").text(errorThrown);
                  $("#errorMessage").modal("show");
            }
		 });
       
     }
     
     
     function saveNewEdgeEndPoint() {      
	      if(validateNewEdgePointFeild()) {	      
	         $.ajax({		      
				url:appContext+'/edgeSystems/addNewEdgeEndPoint',
				type:'GET',
				data:{ camelEndpointName : $("#addCamelEndPoint").val() , actor : $("#addActor").val(), transaction : $("#addTransaction").val() },
	            success:function(data,textStatus) {
	              
	              if(data == "error"){	
	              	              
	                  $("#commonError").css("display","block");		              
	              }
	              if(data == "combinationError"){	
	              	              
	                  $("#combinationError").css("display","block");		              
	              }else{                
	                
	                  $("#commonError").css("display","none");	
	                  $("#combinationError").css("display","none");              
		              $("#addEdgeEndPoint").modal("hide");
		              $("#reloadEdgeSystem").click();  			           
	              } 	        		              
	            },
	            error:function(XMLHttpRequest,textStatus,errorThrown) {
	            
	                  $("#errorText").text(errorThrown);
	                  $("#errorMessage").modal("show");
	            }
			 });
	      }	         
     }
     
     function saveNewEdgeSystemDevice() {
	      if(validateNewEdgeSystemDeviceFeild()) {
	            $.ajax({		      
					url:appContext+'/edgeSystems/saveNewEdgeSystemDevice',
					type:'GET',
					data:{ deviceOID : $("#addDeviceOID").val() , deviceType : $("#addDeviceType").val(),editDeviceName:$("#addDeviceName").val()},
		            success:function(data,textStatus) {
		              
		              if(data == "error"){	
		              	              
		                  $("#commonError").css("display","block");		              
		              }
		              if(data == "combinationError"){	
		              	              
		                  $("#combinationError").css("display","block");		              
		              }if(data != "error" && data != "combinationError") {                
		                
		                  $("#commonError").css("display","none");	
		                  $("#combinationError").css("display","none");              
			              $("#addEdgeEndPoint").modal("hide");
			              $("#reloadEdgeSystem").click();  			           
		              } 	        		              
		            },
		            error:function(XMLHttpRequest,textStatus,errorThrown) {
		            
		                  $("#errorText").text(errorThrown);
		                  $("#errorMessage").modal("show");
		            }
				 });
	      }
     }
     
</g:javascript>
</head>
<body>

<div class="row rowClassStyling">
    <div class="col-md-8 col-xs-12 leftAlignText">
         <ul class="breadcrumb" style="background-color:transparent">
            <li><g:link uri="/edgeSystems/index"><g:message code="interop.edgesystem.breadcrumb.index" /></g:link></li>
            <li class="active"><g:message code="interop.edgesystem.breadcrumb.edgeSystem" /></li>
        </ul>
    </div>  
</div>

<div class="row">
    <div class="col-md-10 col-xs-10 leftAlignText">
      <div class="sectionHeader"><g:message code="interop.audit.label.serverName" /><font color="#088A29">${interopServer.name}</font></div>
    </div>   
    <div class="col-md-2 col-xs-2 rightAlignText">  
       <g:link controller="interopServer" action="index"><g:message code="interop.audit.link.switchServers" /></g:link>
    </div>
</div>

<HR WIDTH="75%" ALIGN="left">
<div class="row">
    <div class="col-md-12 col-xs-12 leftAlignText">
        <div class="sectionHeaderSmall"><g:message code="interop.edgesystem.title.edgeSystem" /> '<font color="#088A29">${edgeSystem.id}</font>'</div>
    </div>
</div>
<br/>

<g:form name="myForm" url="[action:'saveEdgeSystem',controller:'edgeSystems']">
  <div class="row">		
	<div class="col-md-7 col-xs-7">
		<g:if test="${flash.message}">
			<b id="errorMsg" style="color: red">
				${flash.message}
			</b>
			<br/>
		</g:if>
	</div>
  </div>
  <div class="row">		
	<div class="col-md-7 col-xs-7">
		<br/>
	</div>
  </div> 
  <div class="row">
    <div class="col-md-7 col-xs-7">
        <div class="row">
            <div class="col-md-3 col-xs-3 rightAlignText">
              <b><g:message code="interop.edgesystem.label.edgeSystemName" />:</b>
            </div>
            <div class="col-md-9 col-xs-9 leftAlignText">
              <g:field type="text" style="width:100%" name="name" value="${edgeSystem.name}"/>
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="col-md-3 col-xs-3 rightAlignText">
              <b><g:message code="interop.edgesystem.label.edgeSystemAssigningAuthority" />:</b>
            </div>
            <div class="col-md-9 col-xs-9 leftAlignText">
              <g:field type="text" style="width:100%" pattern="[0-9][0-9.]*[0-9]+" required="" name="assigningAuthorityOID" value="${edgeSystem.assigningAuthorityOID}"/>
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="col-md-3 col-xs-3 rightAlignText">
              <b><g:message code="interop.edgesystem.label.edgeSystemDocumentSource" />:</b>
            </div>
            <div class="col-md-9 col-xs-9 leftAlignText">
              <g:field type="text" style="width:100%" pattern="[0-9][0-9.]*[0-9]+" required="" name="documentSourceOID" value="${edgeSystem.documentSourceOID}"/>
            </div>
        </div>
    </div>
    <div class="col-md-1 col-xs-1">
        <div class="row">
        </div>
    </div>
    <div class="col-md-4 col-xs-4">
        <div class="row">
            <div class="col-md-2 col-xs-2"></div>
            <div class="col-md-5 col-xs-5">
              <b><g:message code="interop.edgesystem.label.edgeSystemOrganization" /></b>
              <br/>
            </div>
        </div>
        <div class="row">
          <div class="col-md-12 col-xs-12"><br/></div>
        </div>
        <div class="row">
            <div class="col-md-2 col-xs-2 leftAlignText">
              <b><g:message code="interop.edgeorganization.label.name" />:</b>
            </div>
            <div class="col-md-10 col-xs-10 leftAlignText">
              <font >${edgeSystem.organization.name}</font>
            </div>
        </div>
        <div class="row">
            <div class="col-md-2 col-xs-2 leftAlignText">
              <b><g:message code="interop.edgeorganization.label.oid" />:</b>
            </div>
            <div class="col-md-10 col-xs-10 leftAlignText">
              <font >${edgeSystem.organization.organizationOID}</font>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 col-xs-12"><br/></div>
        </div>
        <div class="row">
          <div class="col-md-2 col-xs-2"><br/></div>
          <div class="col-md-9 col-xs-9">
          <g:actionSubmit  class="btn btn-success btn-block width100" value="Change Organization" action="edgeOrganizationsFromEdgeSystemDetails" /></div>
        </div>
    </div>
  </div>
  <HR WIDTH="100%" ALIGN="left">
  
  <div class="row">
    <div class="col-md-12 col-xs-12">        
        <div class="row">
            <div class="col-md-12 col-xs-12 leftAlignText">            
                <b><g:message code="interop.edgesystem.label.endpoints" />:</b>
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="col-md-12 col-xs-12 leftAlignText">  
                <div class="table-responsive divStylingTransactionTable">
                        <table class="table table-bordered" style="table-layout: fixed;">
                            <thead>
                                <tr>
                                    <th width="60px"><g:message code="interop.edgesystem.endpoints.column.id" /></th>
                                    <th><g:message code="interop.edgesystem.endpoints.column.camelEndpointName" /></th>
                                    <th width="90px"><g:message code="interop.edgesystem.endpoints.column.actor" /></th>
                                    <th width="90px"><g:message code="interop.edgesystem.endpoints.column.transaction" /></th>
                                </tr>
                            </thead>
                            <tbody>
                            <g:each var="endpoint" in="${edgeSystem.endpoints}">
                                <tr>
                                    <td><a href="#" onclick="editEdgeSystemPoint('${endpoint.id}')">${endpoint.id}</a></td>
                                    <td>${endpoint.camelEndpointName}</td>
                                    <td>${endpoint.actor}</td>
                                    <td>${endpoint.transaction.name}</td>
                                </tr>                   
                            </g:each>                               
                            </tbody>
                        </table>
                 </div> 
            </div>
        </div>
        <div class="row">
           <div class="col-md-9 col-xs-9">                             
           </div>
           <div class="col-md-3 col-xs-3 rightAlign">
               <a href="#" onclick="addEdgeEndPointPopUp()"><g:message code="interop.edgesystem.addSystemEndpoint" /></a>                  
           </div>
        </div>
    </div>
    <br/>
</div>
<div class="row">
    <div class="col-md-12 col-xs-12">     
	    <div class="row">
	        <div class="col-md-12 col-xs-12 leftAlignText">            
	            <b><g:message code="interop.edgesystem.label.devices" />:</b>
	        </div>
	    </div>
	    <br/>
	    <div class="row">
	        <div class="col-md-12 col-xs-12 leftAlignText">  
	             <div class="table-responsive">
	                  <table class="table table-bordered">
	                      <thead>
	                          <tr>
	                              <th width="60px"><g:message code="interop.edgesystem.devices.column.id" /></th>
	                              <th><g:message code="interop.edgesystem.devices.column.name" /></th>
	                              <th><g:message code="interop.edgesystem.devices.column.oid" /></th>
	                              <th width="120px"><g:message code="interop.edgesystem.devices.column.type" /></th>
	                          </tr>
	                      </thead>
	                      <tbody>
	                      <g:each var="device" in="${edgeSystem.devices}">
	                          <tr>
	                              <td><a href="#" onclick="editEdgeSystemDevice('${device.id}')">${device.id}</a></td>
	                              <td>${device.name}</td>
	                              <td>${device.deviceOID}</td>
	                              <td>${device.deviceType}</td>
	                          </tr>                   
	                      </g:each>                               
	                      </tbody>
	                  </table>
	              </div>
	         </div>
	    </div>
	    <div class="row">
	         <div class="col-md-9 col-xs-9">
	                             
	         </div>
	         <div class="col-md-3 col-xs-3 rightAlign">
	               <a href="#" onclick="addEdgeSystemDevicePopUp()"><g:message code="interop.edgesystem.addSystemDevice" /></a>                  
	         </div>
	    </div>
	    <br />
   </div>
</div>  
<div class="row">
      <div class="col-md-8 col-xs-8">
         <div class="row">
         </div>
      </div>
      <div class="col-md-4 col-xs-4">
         <div class="row">
              <g:actionSubmit
                         class="btn btn-success btn-block width100" value="Save Changes" action="saveEdgeSystem" />
         </div>
      </div>
 </div>
<div class="row" id="edgeSystemPopoup">
</div>
</g:form>

<g:form name="edgeSystemForm" url="[action:'reloadEdgeSystemDetails',controller:'edgeSystems']">
  <div class="row" style="display: none">           
            <div class="col-md-4 col-xs-4">
                <div class="row">
                    <g:actionSubmit
                            class="btn btn-success btn-block width100" id="reloadEdgeSystem" value="Save Changes" action="reloadEdgeSystemDetails" />
                </div>
            </div>
        </div>
</g:form>	

<!-- Error Message Window -->
    
    
    <div class="modal fade" id="errorMessage" role="dialog" class="modalBackgroundColor">
        <div class="modal-dialog">
            <div class="modal-content padding10">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body break-word overflowStyling">
                    <div class="float_left width100">                          
                        <b><g:message code="common.error" /></b><br/>                        
                    </div>                      
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="modal fade" id="viewMessage" role="dialog" class="modalBackgroundColor">
        <div class="modal-dialog">
            <div class="modal-content padding10">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body break-word overflowStyling">
                    <div class="float_left width100">                          
                        <b><g:message code="errormessage.label" />:</b><br/>
                        <textarea class="form-control" rows="3" id="errorMsg"></textarea>
                        <br/>
                        <b><g:message code="errorstacktrace.label" />:</b><br/>
                        <textarea class="form-control whiteSpaceStyling" rows="30" id="errorStackTrace"></textarea>
                    </div>                      
                </div>
            </div>
        </div>
    </div>

<br />
</body>
</html>