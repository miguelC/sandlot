<%@ page import="net.sandlotnow.config.ADList" %>
<g:hiddenField name="id" value="${ADListInstance?.id}" />
<g:hiddenField name="connectionChecked" value="${ADListInstance?.connectionChecked}" />
<div class="fieldcontain ${hasErrors(bean: ADListInstance, field: 'name', 'error')} required row custom-fieldcontain">
	<div class="form-group">
		<div class="col-sm-5">
			<label for="name">
				<g:message code="ADList.name.label" default="Name" />
				<span class="required-indicator">*</span>
			</label>
		</div>
		<div class="col-sm-7">
			<g:textField name="name" pattern="${ADListInstance.constraints.name.matches}" required="" value="${ADListInstance?.name}"/>
		</div>
	</div>
</div>
<div class="fieldcontain ${hasErrors(bean: ADListInstance, field: 'connectionString', 'error')} required row custom-fieldcontain">
	<div class="form-group">
		<div class="col-sm-5">
			<label for="connectionString">
				<g:message code="ADList.connectionString.label" default="Connection String" />
				<span class="required-indicator">*</span>
			</label>
		</div>
		<div class="col-sm-7">
			<g:textField name="connectionString" pattern="${ADListInstance.constraints.connectionString.matches}" required="" value="${ADListInstance?.connectionString}"/>
		</div>
	</div>
</div>
<div class="fieldcontain ${hasErrors(bean: ADListInstance, field: 'domain', 'error')} required row custom-fieldcontain">
	<div class="form-group">
		<div class="col-sm-5">
			<label for="domain">
				<g:message code="ADList.domain.label" default="Domain" />
				<span class="required-indicator">*</span>
			</label>
		</div>
		<div class="col-sm-7">
			<g:textField name="domain" pattern="${ADListInstance.constraints.domain.matches}" required="" value="${ADListInstance?.domain}"/>
		</div>
	</div>
</div>
<div class="fieldcontain ${hasErrors(bean: ADListInstance, field: 'sapassword', 'error')} required row custom-fieldcontain">
	<div class="form-group">
		<div class="col-sm-5">
			<label for="sapassword">
				<g:message code="ADList.sapassword.label" default="Sapassword" />
				<span class="required-indicator">*</span>
			</label>
		</div>
		<div class="col-sm-7">
			<g:textField name="sapassword" required="" value="${ADListInstance?.sapassword}"/>
		</div>
	</div>
</div>
<div class="fieldcontain ${hasErrors(bean: ADListInstance, field: 'serviceAccount', 'error')} required row custom-fieldcontain">
	<div class="form-group">
		<div class="col-sm-5">
			<label for="serviceAccount">
				<g:message code="ADList.serviceAccount.label" default="Service Account" />
				<span class="required-indicator">*</span>
			</label>
		</div>
		<div class="col-sm-7">
			<g:textField name="serviceAccount" pattern="${ADListInstance.constraints.serviceAccount.matches}" required="" value="${ADListInstance?.serviceAccount}"/>
		</div>
	</div>
</div>
<div class="fieldcontain ${hasErrors(bean: ADListInstance, field: 'technicalName', 'error')} required row custom-fieldcontain">
	<div class="form-group">
		<div class="col-sm-5">
			<label for="technicalName">
				<g:message code="ADList.technicalName.label" default="Technical Name" />
				<span class="required-indicator">*</span>
			</label>
		</div>
		<div class="col-sm-7">
			<g:textField name="technicalName" pattern="${ADListInstance.constraints.technicalName.matches}" required="" value="${ADListInstance?.technicalName}"/>
		</div>
	</div>
</div>