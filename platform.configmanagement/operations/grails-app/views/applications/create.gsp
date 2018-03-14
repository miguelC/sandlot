<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'applications.label', default: 'Applications')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-applications" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="create-applications" class="content scaffold-create" role="main">
			<h3><g:message code="default.create.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${applicationsInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${applicationsInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:applicationsInstance, action:'save']" >
				<div class="row">
			 		<div class="col-sm-offset-3 col-sm-6 col-sm-offset-3">
						<fieldset class="form">
							<g:render template="form"/>
						</fieldset>
					</div>
				</div>
				<br>
				<br>
				<div class="whitefont leftButton">
					<g:link class="btn btn-success" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
				</div>
				<div class="rightButton">
					<g:submitButton name="save" class="btn btn-success" value="${message(code: 'custom.common.save', default: 'Save')}" />
				</div>
			</g:form>
		</div>
	</body>
</html>
