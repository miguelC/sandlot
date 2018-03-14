<!DOCTYPE html>
<html>
<head>
<title><g:message code="page.title" /></title>
<meta name="layout" content="main">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'bootstrap.min.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'application.css')}" type="text/css">
<g:javascript src="jquery.js" />
<g:javascript src="jquery-cookies.js" />
<g:javascript src="bootstrap.js" />
<g:javascript src="application.js"></g:javascript>
<g:javascript>     
     $(document).ready(function(){     
        $("#errorDiv").addClass("in").show();  
     });     
</g:javascript>
</head>
<body>
	<div class="modal fade" id="errorDiv" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<span id="errorText"> <g:if test="${flash.message}">
								<asset:image src="exclamation.png" alt="Grails" />
								<b id="errorMsg" style="margin-left: 5px;"> ${flash.message}
								</b>
								<br />
								<br />
							</g:if> <g:else>
								<g:message code="common.error" />
							</g:else>
						</span>
					</div>					
				</div>
			</div>
		</div>
	</div>
</body>
</html>
s
