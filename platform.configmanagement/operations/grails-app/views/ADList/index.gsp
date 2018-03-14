<g:javascript src="ad-list.js" />
<label>
	<g:message code="custom.adlist.domains"/>
</label>
<div id="templatetable">
	<g:render template="table"/>
</div>
<br/>
<input type="button" id="add" class="btn btn-success addADListBtn"  data-toggle="modal" data-target="#addModal" onclick="emptyForm();" value="${message(code:'custom.common.add',args: [message(code: 'custom.adlist')])}"/>	
<input type="button" id="edit" style="display:none" class="btn btn-success addADListBtn"  data-toggle="modal" data-target="#updateModal"  onclick="updateADList();" value="${message(code:'custom.common.update',args: [message(code: 'custom.adlist')])}"/>
<div class="modal fade" id="addModal" role="dialog">
	<div class="modal-dialog">
    	<div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">${message(code:'custom.adlist.add.domain')}</h4>
	        </div>
	        <div class="modal-body">
		       	<div class="row">
		        	<div id="create-ADList" class="content scaffold-create" role="main">
						<g:hasErrors bean="${ADListInstance}">
							<ul class="errors" role="alert">
								<g:eachError bean="${ADListInstance}" var="error">
									<li>
										<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>
										<g:message error="${error}"/>
									</li>
								</g:eachError>
							</ul>
						</g:hasErrors>
						<g:formRemote url="[resource:ADListInstance, action:'save']" name="formADListInstance" onSuccess="saveAddSuccess(data)">
							<fieldset class="form">
								<div id="templateSaveForm">
									
								</div>
							</fieldset>
							<br/>
							<div id="addSuccessMsg" class="message" role="status" style="display:none">${flash.message}</div>
							<div id="addErrorMsg" class="errors" role="status" style="display:none">${flash.message}</div>
							<br/>
							<g:submitToRemote action="test" class="btn btn-success" value="${message(code: 'custom.common.test', default: 'Test')}" onSuccess="testAddSuccess(data)"/>
							<g:submitButton class="btn btn-success" name="${message(code: 'custom.common.save', default: 'Save')}"/>
						</g:formRemote>
					</div>
				</div>
			</div>
	   	</div>
	</div>
</div>
<div class="modal fade" id="updateModal" role="dialog">
	<div class="modal-dialog">
   		<div class="modal-content">
        	<div class="modal-header">
          		<button type="button" class="close" data-dismiss="modal">&times;</button>
	          	<h4 class="modal-title">${message(code:'custom.adlist.add.domain')}</h4>
	        </div>
	        <div class="modal-body">
	        	<div id="create-ADList" class="content scaffold-create" role="main">
					<g:hasErrors bean="${ADListInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${ADListInstance}" var="error">
							<li>
								<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>
								<g:message error="${error}"/>
							</li>
						</g:eachError>
					</ul>
					</g:hasErrors>
					<g:formRemote url="[resource:ADListInstance, action:'save']" name="formADListInstance"  onSuccess="saveUpdateSuccess(data)">
						<div id="templateUpdateForm"></div>
						<br/>
						<div id="updateSuccessMsg" class="message" role="status" style="display:none">${flash.message}</div>
						<div id="updateErrorMsg" class="errors" role="status" style="display:none">${flash.message}</div>
						<br/>
						<g:submitToRemote action="test" class="btn btn-success" value="${message(code: 'custom.common.test', default: 'Test')}" onSuccess="testUpdateSuccess(data)"/>
						<g:submitButton class="btn btn-success" name="${message(code: 'custom.common.save', default: 'Save')}"/>
          			</g:formRemote>
				</div>
	        </div>
	   </div>
    </div>
</div>