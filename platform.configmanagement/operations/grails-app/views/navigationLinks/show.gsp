<%@ page import="net.sandlotnow.config.NavigationLinks" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'navigationLinks.label', default: 'NavigationLinks')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-navigationLinks" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="show-navigationLinks" class="content scaffold-show" role="main">
			<h3><g:message code="default.show.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="row">
		 		<div class="col-sm-offset-3 col-sm-6 col-sm-offset-3">
					<ol class="property-list navigationLinks">
						
						<g:if test="${navigationLinksInstance?.name}">
						<li class="fieldcontain">
							<b><span id="name-label" class="property-label"><g:message code="navigationLinks.name.label" default="Name" />:</span></b>
							<span class="property-value" aria-labelledby="name-label">&nbsp&nbsp<g:fieldValue bean="${navigationLinksInstance}" field="name"/></span>
						</li>
						</g:if>
						
						<g:if test="${navigationLinksInstance?.linkType}">
						<li class="fieldcontain">
							<b><span id="linkType-label" class="property-label"><g:message code="navigationLinks.linkType.label" default="Link Type" />:</span></b>
							<span class="property-value" aria-labelledby="linkType-label">&nbsp&nbsp<g:fieldValue bean="${navigationLinksInstance}" field="linkType"/></span>
						</li>
						</g:if>
					
						<g:if test="${navigationLinksInstance?.url}">
						<li class="fieldcontain">
							<b><span id="url-label" class="property-label"><g:message code="navigationLinks.url.label" default="Url" />:</span></b>
							<span class="property-value" aria-labelledby="url-label">&nbsp&nbsp<g:fieldValue bean="${navigationLinksInstance}" field="url"/></span>
						</li>
						</g:if>
						
						<g:if test="${navigationLinksInstance?.orderNumber}">
							<li class="fieldcontain">
								<b><span id="orderNumber-label" class="property-label"><g:message code="navigationLinks.orderNumber.label" default="Order Number" />:</span></b>
								<span class="property-value" aria-labelledby="orderNumber-label">&nbsp&nbsp<g:fieldValue bean="${navigationLinksInstance}" field="orderNumber"/></span>
							</li>
						</g:if>
					</ol>
				</div>
			</div>
			<div class="whitefont leftButton">
					<g:link class="btn btn-success" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
				</div>
			<div class="whitefont rightButton">
				<g:form url="[resource:navigationLinksInstance, action:'delete']" method="DELETE">
					<g:link class="btn btn-success" action="edit" resource="${navigationLinksInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="btn btn-success" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"  />
				</g:form>
			</div>
			
		</div>
	</body>
</html>
