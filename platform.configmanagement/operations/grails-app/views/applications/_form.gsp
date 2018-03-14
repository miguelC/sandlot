<%@ page import="net.sandlotnow.config.Applications" %>



<div class="fieldcontain ${hasErrors(bean: applicationsInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="applications.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" pattern="${applicationsInstance.constraints.name.matches}" required="" value="${applicationsInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: applicationsInstance, field: 'url', 'error')} required">
	<label for="url">
		<g:message code="applications.url.label" default="Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="url" pattern="${applicationsInstance.constraints.url.matches}" required="" value="${applicationsInstance?.url}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: applicationsInstance, field: 'display', 'error')} required">
	<label for="display">
		<g:message code="applications.display.label" default="Display" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="display" pattern="${applicationsInstance.constraints.display.matches}" required="" value="${applicationsInstance?.display}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: applicationsInstance, field: 'description', 'error')} required">
	<label for="display">
		<g:message code="applications.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="description" pattern="${applicationsInstance.constraints.description.matches}" required="" value="${applicationsInstance?.description}"/>

</div>

