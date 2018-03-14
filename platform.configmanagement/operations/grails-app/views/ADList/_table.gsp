<%@ page import="net.sandlotnow.config.ADList" %>
<div id="list-ADList" class="content scaffold-list" role="main">
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<table style="width:100%">
		<thead>
			<tr>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${ADListInstanceList}" status="i" var="ADListInstance">
				<tr bgcolor="#ffffff">
					<td width="50%">
						<p>
							<g:radio class="ADGrp" name="ADGrp" id="${ADListInstance.id}" value="${ADListInstance.id}" onClick="radioClick(this)"/>
							&nbsp; &nbsp;${fieldValue(bean: ADListInstance, field: "name")}
						</p>
					</td>
					<td width="50%">
						<p>
							<g:if test="${ADListInstance.connectionChecked == true}">
							    <g:img id="chkImg${ADListInstance.id}" uri="${request.getContextPath()}/images/chk-logo.jpg" width="20" height="20"/>
							</g:if>
							<g:else>
							     <g:img id="chkImg${ADListInstance.id}" uri="${request.getContextPath()}/images/nchk-logo.jpg" width="20" height="20"/>
							</g:else>
								<g:img id="deleteImg${ADListInstance.id}" style="display:none" uri="${request.getContextPath()}/images/delete-logo.jpg" width="20" height="20" onclick="deleteADList();"/>
						</p>
					</td>
				</tr>
			</g:each>
		</tbody>
	</table>
</div>