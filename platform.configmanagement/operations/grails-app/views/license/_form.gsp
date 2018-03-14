<%@ page import="net.sandlotnow.config.License" %>



<div class="fieldcontain ${hasErrors(bean: licenseInstance, field: 'text', 'error')} required">
	<label for="text">
		<g:message code="license.text.label" default="Text" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="text" required="" value="${licenseInstance?.text}" rows="5" cols="40"/>

</div>

<div class="fieldcontain ${hasErrors(bean: licenseInstance, field: 'createdDate', 'error')} required" style="display:none">
	<label for="createdDate">
		<g:message code="license.createdDate.label" default="Created Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="createdDate"  value="${licenseInstance?.createdDate}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: licenseInstance, field: 'effectiveDate', 'error')} required">
	<label for="effectiveDate">
		<g:message code="license.effectiveDate.label" default="Effective Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="effectiveDate" precision="day" value="${licenseInstance?.effectiveDate}"  />

</div>

