<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="custom.menu.interOp.auditMessageDetail" /></title>
<g:javascript>    
	 
	 function openMessage(){
	   $("#viewMessage").modal("show");	 
	 }
	 
</g:javascript>
</head>
<body>
<div class="row">
	<div class="col-md-6 col-xs-6">
	    <div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText">
			    <g:if test="${auditMessage.failed == true}">
				    <b><g:message code="id.label" />: <font style="color:red">${auditMessage.id}</font></b> 
				</g:if> 
				<g:else>
				    <b><g:message code="id.label" />: </b>${auditMessage.id}
				</g:else>	
			</div>
		</div>	
		<br/>
		<div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText"><b><g:message code="exchangeid.label" />:</b>${auditMessage.exchangeId}</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText"><b><g:message code="breadcrumbid.label" />:</b>${auditMessage.breadcrumbId}</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText">
				<b><g:message code="custom.menu.interOp.timestamp" /> </b> ${auditMessage.timeStamp}
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText"><b><g:message code="endpoint.label" />: </b> ${auditMessage.fromEndPoint}</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText"><b><g:message code="toendpoint.label" />: </b> ${auditMessage.toEndPoint}</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText">
			    <g:if test="${message(code: 'eventSentText') == auditMessage.eventName}">
					<b><g:message code="event.label" /> : </b><g:message code="sentevent.label" />
				</g:if> 
				<g:else>
					<b><g:message code="event.label" /> : </b><g:message code="createdevent.label" />
				</g:else>				
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText"><b><g:message code="route.label" />:</b>${auditMessage.route}</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText"><b>
				<g:message code="exchangepattern.label" />: </b>${auditMessage.exchangePattern}</div>
		</div>
	</div>
	<div class="col-md-6 col-xs-6 break-word">
        <div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText">
				<b><g:message code="properties.label" />:</b><br />				
				<div class="table-responsive divStylingTransactionTable">
						<table class="table table-bordered" style="table-layout: fixed;">
							<thead>
								<tr>
									<th><g:message code="name.label" /></th>
									<th><g:message code="value.label" /></th>
								</tr>
							</thead>
							<tbody>
							<g:each var="property" in="${propertyMap}">
								<tr>
									<td>${property.key}</td>
									<td>${property.value}</td>
								</tr>					
							</g:each>								
							</tbody>
						</table>
				</div>				
			</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="row">
				
				    <g:if test="${auditMessage.exceptionStackTrace!=null || auditMessage.exceptionMessage!=null}">										
						<div class="col-md-6 col-xs-11">
							<g:actionSubmit
								class="btn btn-success btn-block width110" onclick="openMessage()" value="View Error"/><br/>
						</div>
						
						<g:javascript>
						   $("#errorMsg").val('${auditMessage.exceptionMessage}');
						   $("#errorStackTrace").val('${auditMessage.exceptionStackTrace}');
						</g:javascript>
						
					</g:if> 					
					
					<div class="col-md-6 col-xs-12">
					    <g:form controller="auditMessage">
					        <input type="hidden" name="messageID" value="${auditMessage.id}"/>
						    <g:actionSubmit
							class="btn btn-success btn-block width100" value="View Messages" action="getAuditMessageDetailOnMessageID" />
						</g:form>						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- Error Message Window -->
	
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