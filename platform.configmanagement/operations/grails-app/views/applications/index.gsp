
<%@ page import="net.sandlotnow.config.Applications" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'applications.label', default: 'Applications')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-applications" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="list-applications" class="content scaffold-list" role="main">
			<h3><g:message code="default.list.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'applications.name.label', default: 'Name')}" />
						<g:sortableColumn property="url" title="${message(code: 'applications.url.label', default: 'Url')}" />
						<g:sortableColumn property="display" title="${message(code: 'applications.display.label', default: 'Display')}" />
					    <g:sortableColumn property="description" title="${message(code: 'applications.description.label', default: 'Description')}" />
						
					</tr>
				</thead>
				<tbody>
				<g:each in="${applicationsInstanceList}" status="i" var="applicationsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="show" id="${applicationsInstance.id}">${fieldValue(bean: applicationsInstance, field: "name")}</g:link></td>
						<td>${fieldValue(bean: applicationsInstance, field: "url")}</td>
						<td>${fieldValue(bean: applicationsInstance, field: "display")}</td>
						<td>${fieldValue(bean: applicationsInstance, field: "description")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${applicationsInstanceCount ?: 0}" />
			</div>
			<div class="rightButton whitefont">
				<g:link class="btn btn-success" action="create"><g:message code="custom.common.add" args="[entityName]" /></g:link>
			</div>
		</div>
	</body>
</html>
