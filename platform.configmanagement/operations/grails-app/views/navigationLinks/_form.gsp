<%@ page import="net.sandlotnow.config.NavigationLinks" %>

<div class="fieldcontain ${hasErrors(bean: navigationLinksInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="navigationLinks.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" pattern="${navigationLinksInstance.constraints.name.matches}" required="" value="${navigationLinksInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLinksInstance, field: 'linkType', 'error')} required">
	<label for="linkType">
		<g:message code="navigationLinks.linkType.label" default="Link Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="linkType" pattern="${navigationLinksInstance.constraints.linkType.matches}" required="" value="${navigationLinksInstance?.linkType}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLinksInstance, field: 'url', 'error')} required">
	<label for="url">
		<g:message code="navigationLinks.url.label" default="Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="url" required="" pattern="${navigationLinksInstance.constraints.url.matches}" value="${navigationLinksInstance?.url}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLinksInstance, field: 'orderNumber', 'error')} required">
	<label for="orderNumber">
		<g:message code="navigationLinks.orderNumber.label" default="Order Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="orderNumber" type="number" min="1" value="${navigationLinksInstance.orderNumber}" required=""/>

</div>
