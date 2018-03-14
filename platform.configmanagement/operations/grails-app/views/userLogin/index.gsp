<html>
	<head>
		<meta name="layout" content="main"/>
		<title>User Login</title>
		<g:javascript src="user-login.js"/>
	</head>
	<body>
		  <div id="loginModal" class="modal fade" role="dialog" data-backdrop="static">
			  <div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">${message(code: 'custom.userLogin.detail')}</h4>
					</div>
					<div class="modal-body">
		        		<div class="row">
					 		<div class="col-sm-offset-1 col-sm-10 col-sm-offset-1">
					        	<g:if test="${flash.message}">
					      			<div class="message">${flash.message}</div>
					    		</g:if>
					 			<g:form class="form-horizontal" role="form"  method="post" id="user" name="user" url="[action:'authenticateUser',controller:'UserLogin']">
								    <div class="form-group">
						      			<div class="col-sm-3">
						      				<label class="control-label col-sm-2" for="email">
						      					<g:message code="custom.userLogin.email"/>
						      				</label>
						      			</div>
									    <div class="col-sm-9">
									        <input type="email" class="form-control" id="email" name='email' required placeholder="${message(code: 'custom.userLogin.email.placeholder')}" value="${email}" autocomplete="off">
										</div>
									</div>
								    <div class="form-group">
								    	<div class="col-sm-3">
									    	<label class="control-label col-sm-2" for="pwd">
									    		<g:message code="custom.userLogin.password"/>
									    	</label>
									    </div>
								      	<div class="col-sm-9">          
									        <input type="password" class="form-control" id="password" name='password'  required placeholder="${message(code: 'custom.userLogin.password.placeholder')}" >
									    </div>
								    </div>
								    <div class="form-group">        
							        	<div class="col-sm-offset-1 col-sm-11">
								        	<button type="submit" class="btn btn-success">${message(code: 'custom.userLogin.submit')}</button>
										</div>
								    </div>
							  	</g:form>
				 			</div>
				 		</div>
			      	</div>
			    </div>
			</div>
		</div>
	</body>
</html>