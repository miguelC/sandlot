<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
</head>
<body>
<div class="modal fade modalBackgroundColor" id="editEdgeSystemDevice" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
			    <div class="modal-header">
			      <label> <g:message code="interop.edgesystem.device" /> </label>
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		        </div>
				<div class="modal-body" style="text-align: inherit;">
					<div class="row">
					     <div class="col-md-4"> <label><g:message code="interop.edgesystemDevice.name" /> </label></div>
					     <div class="col-md-8" style="float: left;"> <input type="text" style="width: 100%" id="editDeviceName" value="${edgeDevice.name}"/> </div>    
					</div>
					
					<div class="row">
					  <div class="col-md-4"></div>
					  <div class="col-md-8">
					      <label class="deviceNameError" style="color: red;display: none"><g:message code="interop.edgesystemDevice.name.emptyError" /> </label>		
					      <br/>			    
					  </div>
					</div>
					
					<div class="row">
					     <div class="col-md-4"> <label><g:message code="interop.edgesystemDevice.OID" /></label></div>
					     <div class="col-md-8" style="float: left;"> <input type="text" style="width: 100%" pattern="[0-9][0-9.]*[0-9]+" id="editDeviceOID" value="${edgeDevice.deviceOID}"/> </div>    
					</div>
					
					<div class="row">
					  <div class="col-md-4"></div>
					  <div class="col-md-8">
					   	  <label class="deviceOIDError" style="color: red;display: none"><g:message code="interop.edgesystemDevice.OID.error"/> </label>
					   	  <label class="addDeviceOIDMatchingError" style="color: red;display: none"><g:message code="interop.edgesystemDevice.OID.patternerror"/></label>
					   	  <br/>			    
					  </div>
					</div>
					
					<div class="row">
					     <div class="col-md-4"> <label><g:message code="interop.edgesystemDevice.type" /></label> </div>
					     <div class="col-md-6">
					         <g:select id="editDeviceType" value="${edgeDevice.deviceType}" optionKey="${{it}}" optionValue="${{it}}" name="deviceName" from="${edgeSystemDeviceList}" />				        
						 </div>						 									
					</div>
					
					<div class="row">
					  <div class="col-md-12">
					   <br/> 
					    <label id="commonError" style="color: red;display: none"><g:message code="common.error" /></label>
					    <label id="combinationError" style="color: red;display: none"><g:message code="device.combination.error" /></label>					    
					  </div>
					</div>				
					
					<div class="row">
					       <div class="col-md-4">
					         <input class="btn btn-success" type="button" Value="Save" onclick="saveEdgeSystemDevice()" id="saveEdgeSytemChange"/>
					       </div>
						   <div class="col-md-4">
					         <input class="btn btn-success" type="button" Value="Cancel" onclick="closeModal()" id="closePopup"/>
					       </div>			
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>	