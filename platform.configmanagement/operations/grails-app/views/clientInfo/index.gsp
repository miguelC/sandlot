<html>
	<head>
		<meta name="layout" content="main"/>
		<title>User Login</title>
		<g:javascript src="client-info.js" />
	</head>
	<body>
		<br>
		<br>
		<br>
		<div class="row">
			<div class="col-sm-6" id="clientInfoDiv">
		  		<div class="row">
	  	  			<div class="col-sm-3">
	  	  				<label for="name">
	  	  					<g:message code="custom.clientInfo.name"/>
	  	  				</label>
	  	  			</div>
			      	<div class="col-sm-9">
			      		<g:remoteField action="updateName"  name="name" pattern="${clientInfo.constraints.name.matches}" value="${clientInfo?.name}" required=""/>
			      	</div>
		     	</div>
			    <br>
			    <div class="row">
		     		<div class="col-sm-3">
		     			<label for="acronym">
		     				<g:message code="custom.clientInfo.acronym"/>
		     			</label>
		     		</div>
				    <div class="col-sm-9">
				    	<g:remoteField action="updateAcronym"  name="acronym" value="${clientInfo?.acronym}" required=""/>
				    </div>
			     </div>
			     <br>
			     <div class="row">
					  <div class="col-sm-11">
					  		<p>&nbsp; &nbsp;
					  			<g:radio class="authGrp" name="authGrp" id="authTypeAD" value="AD"/>
					  				<g:message code="custom.clientInfo.ad"/>
					  		</p>
							<p>&nbsp; &nbsp;
								<g:radio class="authGrp" name="authGrp" id="authTypeFA" value="FA"/>
									<g:message code="custom.clientInfo.fa"/>
							</p>
							<input type="hidden" id="authType" name="authType" value="${clientInfo?.authType}"/>
					   </div>
				</div>
				<br>
			    <div class="row">
					<div class="col-sm-1">
						<label  for="name">
							<g:message code="custom.clientInfo.logo"/>
						</label>
					</div>
				   	<div class="col-sm-11">
				   		<img id="vendorLogo" class="img-responsive" src="${clientInfo?.logo}" alt="${message(code:'custom.clientInfo.addLogo')}"></img>
				   	</div>
				</div>
			  	<br>
			  	<div class="row">
			  		<div class="col-sm-offset-5 col-sm-2 col-sm-offset-5">
				   		<input type="file" id="OpenFileDialog" name="files" title="Load File" accept="image/*"/>
						<input type="button" class="btn btn-success btnOpenFileDialog" id="addLogoBttn" value = "${message(code:'custom.common.add',args: [message(code: 'custom.clientInfo.logo')])}"/>
						<input type="button" class="btn btn-success btnOpenFileDialog" id="updateLogoBttn" value = "${message(code:'custom.common.update',args: [message(code: 'custom.clientInfo.logo')])}"/>
					</div>
				</div>
			</div>
		  	<div class="col-sm-6" id="show-ad">
		  		<g:include controller="ADList"/>
		  	</div>
		  	<div class="col-sm-6" id="show-fa">
		  		<label  for="name">
		  			<g:message code="custom.clientInfo.redirecturl"/>
		  		</label>
		  		<br>
		  		<g:remoteField action="updateRedirectURL" size="50%" name="redirectURL" value="${clientInfo?.redirectURL}" required="" pattern="${clientInfo.constraints.redirectURL.matches}"/>
		  	</div>
	  	</div>
	</body>
</html>