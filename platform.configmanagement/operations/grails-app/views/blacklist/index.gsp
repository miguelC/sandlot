
<%@ page import="net.sandlotnow.config.NavigationLinks" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'blacklist.label', default: 'Blacklist')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-blacklist" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="list-blacklist" class="content scaffold-list" role="main">
			<h3><g:message code="default.list.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="organizationName" title="${message(code: 'blackList.id.label', default: 'Name')}" />
						<g:sortableColumn property="organizationId" title="${message(code: 'blackList.name.label', default: 'Organization Id')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${blacklistInstanceList}" status="i" var="blackListInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><b><g:link action="show" id="${blackListInstance.id}" >${fieldValue(bean: blackListInstance, field: "organizationId")}</g:link></b></td>
						<td>${fieldValue(bean: blackListInstance, field: "organizationName")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${blackListInstanceCount ?: 0}" />
			</div>
			<div class="rightButton whitefont">
				<g:link class="btn btn-success" action="create"><g:message code="custom.common.add" args="[entityName]" /></g:link>
			</div>
		</div>
	</body>
</html>
