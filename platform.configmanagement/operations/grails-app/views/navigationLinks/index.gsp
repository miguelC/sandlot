
<%@ page import="net.sandlotnow.config.NavigationLinks" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'navigationLinks.label', default: 'NavigationLinks')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-navigationLinks" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="list-navigationLinks" class="content scaffold-list" role="main">
			<h3><g:message code="default.list.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'navigationLinks.name.label', default: 'Name')}" />
						<g:sortableColumn property="linkType" title="${message(code: 'navigationLinks.linkType.label', default: 'Link Type')}" />
						<g:sortableColumn property="url" title="${message(code: 'navigationLinks.url.label', default: 'Url')}" />
						<g:sortableColumn property="orderNumber" title="${message(code: 'navigationLinks.orderNumber.label', default: 'Order Number')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${navigationLinksInstanceList}" status="i" var="navigationLinksInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><b><g:link action="show" id="${navigationLinksInstance.id}" >${fieldValue(bean: navigationLinksInstance, field: "name")}</g:link></b></td>
						<td>${fieldValue(bean: navigationLinksInstance, field: "linkType")}</td>
						<td>${fieldValue(bean: navigationLinksInstance, field: "url")}</td>
						<td>${fieldValue(bean: navigationLinksInstance, field: "orderNumber")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${navigationLinksInstanceCount ?: 0}" />
			</div>
			<div class="rightButton whitefont">
				<g:link class="btn btn-success" action="create"><g:message code="custom.common.add" args="[entityName]" /></g:link>
			</div>
		</div>
	</body>
</html>
