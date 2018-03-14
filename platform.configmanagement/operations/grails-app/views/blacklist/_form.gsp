<%@ page import="net.sandlotnow.config.Blacklist" %>

<div class="fieldcontain ${hasErrors(bean: blacklistInstance, field: 'organizationName', 'error')} required">
	<label for="organizationName">
		<g:message code="blackList.orgnization.name.label" default="Orgnization Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="organizationName" pattern="${blacklistInstance.constraints.organizationName.matches}"  required="" value="${blacklistInstance?.organizationName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: blacklistInstance, field: 'organizationId', 'error')} required">
	<label for="organizationId">
		<g:message code="blackList.orgnization.id.label" default="Orgnization ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="organizationId" pattern="${blacklistInstance.constraints.organizationId.matches}" required="" value="${blacklistInstance?.organizationId}"/>

</div>
