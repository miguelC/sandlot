<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="custom.menu.interOp.auditMessageDetail" /></title>
	<g:javascript>  	
		 
	</g:javascript>	
	<g:javascript src="ace/ace.js"/>
</head>
<body>
<div class="row">
	<div class="col-md-8 col-xs-11">
	    <div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText">
				<b><g:message code="messageheader.label" />:</b><br/>
				
				<pre id="msgHeader" style="height: 16pc; overflow-y: scroll;">${auditMessage.messageHeadersJson}</pre>	
				<script>
				    var editor = ace.edit("msgBody");
				    editor.setTheme("ace/theme/chrome");
				    editor.session.setMode("ace/mode/json");
				</script>
			</div>
		</div>		
	</div>		
</div><br>
<div class="row">
	<div class="col-md-8 col-xs-11">
	    <div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText">
				<b><g:message code="messagebodytype.label" />:</b> ${auditMessage.messageBodyType}<br/>	 
			</div>
		</div>		
	</div>		
</div><br>
<div class="row">
	<div class="col-md-8 col-xs-11">
	    <div class="row">
			<div class="col-md-12 col-xs-12 leftAlignText">
				<b><g:message code="messagebody.label" />:</b><br/>
				<pre id="msgBody" style="height: 16pc; overflow-y: scroll;">${auditMessage.messageBody}</pre>	
				<script>
				    var editor = ace.edit("msgBody");
				    editor.setTheme("ace/theme/chrome");
				    editor.session.setMode("ace/mode/xml");
				</script>
			 </div>
		</div>		
	</div>		
</div>

</body>
</html>