<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
</head>
<body>
<div class="modal fade modalBackgroundColor" id="addEdgeEndPoint" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
			    <div class="modal-header">
			      <label> <g:message code="interop.edgesystem.addSystemEndpoint.lable" /> </label>
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		        </div>
				<div class="modal-body" style="text-align: inherit;">
					<div class="row">
					     <div class="col-md-4"> <label><g:message code="interop.edgesystem.endpoints.column.camelEndpointName" /> :</label></div>
					     <div class="col-md-8"> <input type="text" style="width: 100%" id="addCamelEndPoint" value=" "/> </div>    
					</div>
					
					<div class="row">
					  <div class="col-md-4"></div>
					  <div class="col-md-8">
					      <label class="CamelEndPointError" style="color: red;display: none"><g:message code="interop.edgesystemDevice.camelEnpoint.empty.error" /> </label>		
					      <br/>			    
					  </div>
					</div>
					
					<div class="row">
					     <div class="col-md-4"> <label><g:message code="interop.edgesystem.endpoints.column.actor" /> :</label> </div>
					     <div class="col-md-6">
					         <g:select id="addActor" optionKey="${{it?.toUpperCase()}}" optionValue="${{it?.toUpperCase()}}" name="actorName" from="${edgeSystemActorList}" />				        
						 </div>						 									
					</div>
					
					<div class="row">
					  <div class="col-md-4">
					   <br/>
					  </div>
					</div>
					
					<div class="row">
					     <div class="col-md-4"> <label><g:message code="interop.edgesystem.endpoints.column.transaction" /> :</label> </div>
					     <div class="col-md-6"> 
					        <g:select id="addTransaction" optionKey="${{it.name?.toUpperCase()}}" optionValue="${{it.name?.toUpperCase()}}" name="tranactionName" from="${edgeTransactions}" />				        
					      </div>
					</div>
					
					<div class="row">
					  <div class="col-md-12">
					    <br/> 
					    <label id="commonError" style="color: red;display: none"><g:message code="common.error" /></label>
					    <label id="combinationError" style="color: red;display: none"><g:message code="combination.error" /></label>					    
					  </div>
					</div>
					
					<div class="row">
					       <div class="col-md-4">
					         <input class="btn btn-success" type="button" Value="Save" onclick="saveNewEdgeEndPoint()" id="saveEdgeSytemChange"/>
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