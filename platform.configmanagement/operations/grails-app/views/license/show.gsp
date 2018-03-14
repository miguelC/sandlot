<%@ page import="net.sandlotnow.config.License" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'license.label', default: 'License')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-license" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="show-license" class="content scaffold-show" role="main">
			<h3><g:message code="default.show.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="row">
		 		<div class="col-sm-offset-3 col-sm-6 col-sm-offset-3">
					<ol class="property-list license">
					
						<g:if test="${licenseInstance?.text}">
						<li class="fieldcontain">
							<b><span id="text-label" class="property-label"><g:message code="license.text.label" default="Text" />:</span></b>
							<span class="property-value" aria-labelledby="text-label">&nbsp&nbsp<g:fieldValue bean="${licenseInstance}" field="text"/></span>
						</li>
						</g:if>
					
						<g:if test="${licenseInstance?.createdDate}">
						<li class="fieldcontain">
							<b><span id="createdDate-label" class="property-label"><g:message code="license.createdDate.label" default="Created Date" />:</span></b>
							<span class="property-value" aria-labelledby="createdDate-label">&nbsp&nbsp<g:formatDate date="${licenseInstance?.createdDate}" /></span>
						</li>
						</g:if>
					
						<g:if test="${licenseInstance?.effectiveDate}">
						<li class="fieldcontain">
							<b><span id="effectiveDate-label" class="property-label"><g:message code="license.effectiveDate.label" default="Effective Date" />:</span></b>
							<span class="property-value" aria-labelledby="effectiveDate-label">&nbsp&nbsp<g:formatDate date="${licenseInstance?.effectiveDate}" /></span>
						</li>
						</g:if>
					
					</ol>
				</div>
			</div>
			<div class="whitefont leftButton">
					<g:link class="btn btn-success" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
				</div>
		</div>
	</body>
</html>
