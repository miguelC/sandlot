<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Grails" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}"
	type="image/x-icon">
<link rel="apple-touch-icon"
	href="${assetPath(src: 'apple-touch-icon.png')}">
<link rel="apple-touch-icon" sizes="114x114"
	href="${assetPath(src: 'apple-touch-icon-retina.png')}">
<asset:stylesheet src="application.css" />
<asset:javascript src="dataTables.tableTools.js" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.9/css/dataTables.bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/responsive/1.0.7/css/responsive.bootstrap.min.css">
<asset:stylesheet src="dataTables.responsive.css" />
<asset:stylesheet src="dataTables.responsive.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet"
	href="${request.getContextPath()}/css/operations-app.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.9/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.9/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/responsive/1.0.7/js/dataTables.responsive.js"></script>
<asset:javascript src="dataTables.bootstrap.js" />
<asset:javascript src="moment.js" />
<asset:javascript src="date.format.js"/>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<g:javascript>
   window.appContext = '${request.contextPath}';
</g:javascript>	
<g:layoutHead />
</head>
<body>
	<g:javascript src="operations-app.js" />
	<script type="text/javascript"
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<g:javascript src="jquery.session.js" />

	<div class="container" id="grailLogo">
		<div class="row">
			<div class="col-sm-3">
				<a href="http://grails.org"><g:img dir="images"
						file="sandlot_logo.bmp" alt="Grails" width="170px" height="70px" /></a>
			</div>
			<div class="col-sm-9">
				<p class="appName" align="center">
					<g:message code="custom.common.appname" />
				</p>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-sm-9">
				<%--<div class="main-btn-grp">
					    <button type="button" class="btn btn-default" style="width:150px" onclick="showSSOPage()"><g:message code="custom.menu.sso"/></button>&nbsp
					    <button type="button" class="btn btn-default" style="width:150px" onclick="showinterOpPage()"><g:message code="custom.menu.interOp"/></button>&nbsp
					    <button type="button" class="btn btn-default" style="width:150px" onclick="showtabPage()"><g:message code="custom.menu.tab"/></button>&nbsp
					 </div>
					 --%>
				<ul class="nav nav-tabs" id="myTab">
					<li class="active"><a data-toggle="tab" href="#SSO-menu"
						onclick="showSSOPage()"><g:message code="custom.menu.sso" /></a></li>
					<li><a data-toggle="tab" href="#interOp-menu"
						onclick="showinterOpPage()"><g:message
								code="custom.menu.interOp" /></a></li>
					<li><a data-toggle="tab" href="#tab-menu"
						onclick="showtabPage()"><g:message code="custom.menu.tab" /></a></li>
				</ul>
			</div>
			<div class="col-sm-2 right-border" style="text-align: right">
				<g:message code="custom.common.welcome" />
				,
				${session.userName}<br>
				${session.userRole}
			</div>
			<div class="col-sm-1 rightAlign">
				<g:link controller="UserLogin" action="doLogout"
					onclick="removeClientSession()">
					<g:message code="custom.common.logout" />
				</g:link>
			</div>
		</div>

	</div>
	<br>
	<div class="container body-div">


		<div class="tab-content">
			<div id="SSO-menu" class="tab-pane fade in active">
				<g:link controller="ClientInfo">
					<g:message code="custom.menu.sso.clientInfo" />
				</g:link>
				|
				<g:link controller="NavigationLinks">
					<g:message code="custom.menu.sso.navigation" />
				</g:link>
				|
				<g:link controller="Applications">
					<g:message code="custom.menu.sso.applications" />
				</g:link>
				|
				<g:link controller="License">
					<g:message code="custom.menu.sso.termsOfService" />
				</g:link>
				|
				<g:link controller="Blacklist">
					<g:message code="custom.menu.sso.blacklistorganization" />
				</g:link>

			</div>
			<div id="interOp-menu" class="tab-pane fade">
				<div class="row">
					<div class="col-md-8 col-xs-12 col-sm-2">						
						<ul class="nav navbar-nav">
						    <li class="dropdown">
					          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Audit <b class="caret"></b></a>
					          <ul class="dropdown-menu">
					            <li><g:link controller="AuditMessage">Transactions</g:link></li>
								<li><a href="#">Reports</a></li>
					          </ul>
					        </li>
					        <li class="dropdown">
					          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Configuration <b class="caret"></b></a>
					          <ul class="dropdown-menu">
					            <li><g:link controller="AuditMessage">Camel</g:link></li>
								<li><a href="#">Spring</a></li>
					          </ul>
					        </li>
					        <li><g:link controller="EdgeSystems">
								<g:message code="custom.menu.interOp.edgeSystems" />
							</g:link></li>					       
					      </ul>								
					</div>
				</div>
			</div>
			<div id="tab-menu" class="tab-pane fade">
				<g:link controller="ClientInfo">
					<g:message code="custom.menu.tab.tab1" />
				</g:link>
				|
				<g:link controller="NavigationLinks">
					<g:message code="custom.menu.tab.tab2" />
				</g:link>
				|
				<g:link controller="Applications">
					<g:message code="custom.menu.tab.tab3" />
				</g:link>
				|
				<g:link controller="License">
					<g:message code="custom.menu.tab.tab4" />
				</g:link>
				|
				<g:link controller="License">
					<g:message code="custom.menu.tab.tab5" />
				</g:link>

			</div>
			<g:layoutBody />
		</div>


	</div>
	<div class="footer">
		<a href="http://grails.org"> <g:img dir="images"
				file="vendor_logo_footer.png" alt="Grails" />
		</a> <span class="sitemark"> <g:message
				code="custom.common.sitemark" />
		</span>
	</div>
	<div id="spinner" class="spinner" style="display: none;">
		<g:message code="spinner.alt" default="Loading&hellip;" />
	</div>
</body>
</html>
