<%@ page import="net.sandlotnow.config.Blacklist" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'blacklist.label', default: 'Blacklist')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-blacklist" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="show-blacklist" class="content scaffold-show" role="main">
			<h3><g:message code="default.show.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="row">
		 		<div class="col-sm-offset-3 col-sm-6 col-sm-offset-3">
					<ol class="property-list navigationLinks">
						
						<g:if test="${blacklistInstance?.organizationName}">
						<li class="fieldcontain">
							<b><span id="name-label" class="property-label"><g:message code="blackList.orgnization.name.label" default="BlackList organization Name" />:</span></b>
							<span class="property-value" aria-labelledby="name-label">&nbsp&nbsp<g:fieldValue bean="${blacklistInstance}" field="organizationName"/></span>
						</li>
						</g:if>
						
						<g:if test="${blacklistInstance?.organizationId}">
						<li class="fieldcontain">
							<b><span id="linkType-label" class="property-label"><g:message code="blackList.orgnization.id.label" default="BlackList organization ID" />:</span></b>
							<span class="property-value" aria-labelledby="linkType-label">&nbsp&nbsp<g:fieldValue bean="${blacklistInstance}" field="organizationId"/></span>
						</li>
						</g:if>
					
					</ol>
				</div>
			</div>
			<div class="whitefont leftButton">
					<g:link class="btn btn-success" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
				</div>
			<div class="whitefont rightButton">
				<g:form url="[resource:blacklistInstance, action:'delete']" method="DELETE">
					<g:link class="btn btn-success" action="edit" resource="${blacklistInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="btn btn-success" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"  />
				</g:form>
			</div>
			
		</div>
	</body>
</html>
