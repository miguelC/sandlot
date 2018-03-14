
<%@ page import="net.sandlotnow.config.License" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'license.label', default: 'License')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-license" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="list-license" class="content scaffold-list" role="main">
			<h3><g:message code="default.list.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="text" title="${message(code: 'license.text.label', default: 'Text')}" />
						<g:sortableColumn property="createdDate" title="${message(code: 'license.createdDate.label', default: 'Created Date')}" />
						<g:sortableColumn property="effectiveDate" title="${message(code: 'license.effectiveDate.label', default: 'Effective Date')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${licenseInstanceList}" status="i" var="licenseInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="show" id="${licenseInstance.id}">${fieldValue(bean: licenseInstance, field: "text")}</g:link></td>
						<td><g:formatDate date="${licenseInstance.createdDate}" /></td>
						<td><g:formatDate date="${licenseInstance.effectiveDate}" /></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${licenseInstanceCount ?: 0}" />
			</div>
			<div class="rightButton whitefont">
				<g:link class="btn btn-success" action="create"><g:message code="custom.common.add" args="[entityName]" /></g:link>
			</div>
		</div>
	</body>
</html>
