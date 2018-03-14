<%@ page import="net.sandlotnow.config.Applications" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'applications.label', default: 'Applications')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-applications" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="show-applications" class="content scaffold-show" role="main">
			<h3><g:message code="default.show.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="row">
		 		<div class="col-sm-offset-3 col-sm-6 col-sm-offset-3">
					<ol class="property-list applications">
					
						<g:if test="${applicationsInstance?.name}">
						<li class="fieldcontain">
							<b><span id="name-label" class="property-label"><g:message code="applications.name.label" default="Name" />:</span></b>
							<span class="property-value" aria-labelledby="name-label">&nbsp&nbsp<g:fieldValue bean="${applicationsInstance}" field="name"/></span>
						</li>
						</g:if>
					
						<g:if test="${applicationsInstance?.url}">
						<li class="fieldcontain">
							<b><span id="url-label" class="property-label"><g:message code="applications.url.label" default="Url" />:</span></b>
							<span class="property-value" aria-labelledby="url-label">&nbsp&nbsp<g:fieldValue bean="${applicationsInstance}" field="url"/></span>
						</li>
						</g:if>
										
					
						<g:if test="${applicationsInstance?.display}">
						<li class="fieldcontain">
							<b><span id="display-label" class="property-label"><g:message code="applications.display.label" default="Display" />:</span></b>
							<span class="property-value" aria-labelledby="display-label">&nbsp&nbsp<g:fieldValue bean="${applicationsInstance}" field="display"/></span>
						</li>
						</g:if>
					
					    <g:if test="${applicationsInstance?.description}">
						<li class="fieldcontain">
							<b><span id="desc-label" class="property-label"><g:message code="applications.description.label" default="Description" />:</span></b>
							<span class="property-value" aria-labelledby="desc-label">&nbsp&nbsp<g:fieldValue bean="${applicationsInstance}" field="description"/></span>
						</li>
						</g:if>
					
					</ol>
				</div>
			</div>
			<div class="whitefont leftButton">
					<g:link class="btn btn-success" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
				</div>
			<div class="whitefont rightButton">
				<g:form url="[resource:applicationsInstance, action:'delete']" method="DELETE">
					<g:link class="btn btn-success" action="edit" resource="${applicationsInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="btn btn-success" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
				</g:form>
			</div>
		</div>
	</body>
</html>
