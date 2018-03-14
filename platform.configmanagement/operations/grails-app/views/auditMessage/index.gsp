<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="custom.menu.interOp.auditMessage" /></title>
<g:javascript>
     $(document).ready(function(){
		 
		 var startDate;
		 var endDate;
		 var transactionTypes = [];
		 
		 $("input[name=statusFilter][value='${status}']").attr('checked', true);
		
		 
		 $('#datetimepicker1').datetimepicker().on("dp.change", function(e) {   
		   		    
		    endDate=e.date._d.toLocaleString();		    	   
		 }).datetimepicker("setDate", new Date());;		 
		 
		 $('#datetimepicker2').datetimepicker().on("dp.change", function(e) {   
		   		    
		    startDate=e.date._d.toLocaleString();		     
		 });		 
		 
		 $('#datetimepicker1').data("DateTimePicker").date('${toDate}');
		 $('#datetimepicker2').data("DateTimePicker").date('${fromDate}');
		 
		 $("#all").click(function(){

		   $("input:checkbox").prop('checked', $(this).prop("checked"));		   	 
		 });
		  
		 if($.session.get('transTypeArray') == "null") {
		 
		    $("input[type=checkbox]").attr('checked', true);
		 }else {

		    $("input[type=checkbox]").attr('checked', false);
		    var sessionTransTypeAray = $.session.get('transTypeArray');
		    var selectedTransType = sessionTransTypeAray.split(',');
	    
		    for(var i =0;i < selectedTransType.length;i++){
		      
		      $("input[name='transType'][value="+selectedTransType[i]+"]").prop('checked', true);
		    }
		    
		    if(selectedTransType.length == 6) {
		      
		      $("#all").prop('checked', true);
		    }
		 }
		 
		 $("input:checkbox").click(function() {
		 
		    var isAllcheckBoxChecked = true;
		    
		    $('input[type=checkbox]').each(function () {
		    
			   if($(this).val() != 'All' && !$(this).is(":checked")) {
			    
			      $("#all").removeAttr('checked');
			      isAllcheckBoxChecked = false;
			   }			   
			});
			if(isAllcheckBoxChecked) {
			  $("#all").prop('checked','checked');
			}
			
			//$("#fetchData").click();  
			
			fetchAuditMessage();
								    
		 });
		 
		  $("input[name=statusFilter]").click(function(){
		  
		      fetchAuditMessage();
		  });
		 
		 
		 function fetchAuditMessage() {
		 
		    $("#transactionTypeError").css("display","none");  
			
			var transValues = $('input[name=transType]:checked').map(function()
	        {
	          return $(this).val();
	        }).get();
	        
	        if(transValues.length == 0) {
	        
	              $("#transactionTypeError").css("display","block");
	        }else{
              
	              $.session.set('transTypeArray',transValues);
		          $.ajax({
		      
					url: appContext+'/auditMessage/getAuditMessageDetailBasedOnTransType',
					type:'POST',
					data:{transactionValues:transValues,status:$('input[name=statusFilter]:radio:checked').val()},
		            success:function(data,textStatus) {
		               if(data == "error"){
		              
		                  $("#errorMessage").text("Exception occurred while processing your request");
		                  $("#dateErrorMessage").modal("show");
		              
		              }else if(data == "errorDate") {
		                  
		                  $("#errorMessage").text("End Date cannot be grater than Start Date");
		                  $("#dateErrorMessage").modal("show"); 
		              }else{		                
			               jQuery('#auditMessageList').html(data);
		              }		              
		            },error:function(XMLHttpRequest,textStatus,errorThrown) {
		                  $("#errorMessage").text("Exception occurred while processing your request");
		                  $("#dateErrorMessage").modal("show");
		            }
			      });	        
	        }
		 
		 }
		 
		 
		 $("#fetchData").click(function() {
		    
		    $("#transactionTypeError").css("display","none");		    
			var transValues = $('input[name=transType]:checked').map(function()
	        {
	          return $(this).val();
	        }).get();
	        
	        if(transValues.length == 0) {
	        
	              $("#transactionTypeError").css("display","block");
	        }else{
              
	              $.session.set('transTypeArray',transValues);
		          $.ajax({
		      
					url: appContext+'/auditMessage/getAuditMessageDetailOnDates',
					type:'POST',
					data:{startDates:startDate,endDates:endDate,status:$('input[name=statusFilter]:radio:checked').val(),transactionValues:transValues},
		            success:function(data,textStatus) {
		               if(data == "error"){
		              
		                  $("#errorMessage").text("Exception occurred while processing your request");
		                  $("#dateErrorMessage").modal("show");
		              
		              }else if(data == "errorDate") {
		                  
		                  $("#errorMessage").text("End Date cannot be grater than Start Date");
		                  $("#dateErrorMessage").modal("show"); 
		              }else{		                
		                
			               jQuery('#auditMessageList').html(data);
		              }		              
		            },error:function(XMLHttpRequest,textStatus,errorThrown) {
		           
		            }
			      });	        
	        }	    
		 });		 
		 		
	 });
	
</g:javascript>
</head>
<body>
	<div class="row rowClassStyling">
		<div class="col-md-5 col-xs-5 leftAlignText">
		     <ul class="breadcrumb" style="background-color:transparent">
		        <li><g:link uri="/"><g:message code="audit.label" /></g:link></li>
		        <li class="active"><g:message code="transactions.label" /></li>
		    </ul>
		</div>
		<div class="col-md-4 col-xs-5" style="float: right">
			<g:link url="/operations/auditMessage/index">
				<g:message code="custom.menu.interOp.refresh" />
			</g:link>
		</div>
	</div>	
	<div class="row rowClassStyling rightSidePanel">
		<div class="col-md-9 responsive-table" id="auditMessageList">
			<g:render template="allAuditMessage"
				model="['auditMessageList':auditMessageList]" />
		</div>
		<div class="col-md-3">
			<div class="row">
				<div class="col-md-12 appLable">
                    <label><b><g:message code="status.label" /></b></label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<g:radioGroup class="status" name="statusFilter"
					              labels="['All','Fail','Succeed']"
					              values="['All','FailedOnly','SucceededOnly']">
					${it.label} ${it.radio} &nbsp; | &nbsp;
					</g:radioGroup>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<lable id="transactionTypeError" style="display:none;color:red"><b><g:message code="transactions.error" /></b></lable>
				</div>
			</div>
            <div class="row">
                <div class="col-md-12 appLable">
                    <label><b><g:message code="txtype.label" /></b></label>
                </div>
            </div>
			<div class="row">
				<div class="col-md-6">
					<g:checkBox name="transType"  value="ITI-18" />
					<g:message code="ITI18.label" />
				</div>
				<div class="col-md-6">
					<g:checkBox name="transType"  value="ITI-41" />
					<g:message code="ITI41.label" />
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-6">
					<g:checkBox name="transType" value="ITI-43" />
					<g:message code="ITI43.label" />
				</div>
				<div class="col-md-6">
					<g:checkBox name="transType" value="ITI-44" />
					<g:message code="ITI44.label" />
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-6">
					<g:checkBox name="transType" value="ITI-45" />
					<g:message code="ITI45.label" />
				</div>
				<div class="col-md-6">
					<g:checkBox name="transType"  value="ITI-47" />
					<g:message code="ITI47.label" />
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-6">
					<g:checkBox name="All" id="all" value="All" />
					<g:message code="ALL.label" />
				</div>				
			</div>
            <div class="row">
                <div class="col-md-12">
                    <hr />
                </div>
            </div>
			<div class="row">
				<div class="col-md-12 appLable">
					<label for="email"><b><g:message code="starttime.label" /></b></label>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12 col-xs-8">
					<input type="hidden" id="hidden" />
					<div class='input-group date' id='datetimepicker2'>
						<input type='text' id="text1" class="datepicker form-control" /> <span
							class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 appLable">
					<label for="email"><b><g:message code="endtime.label" /></b></label>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12 col-xs-8">
					<input type="hidden" id="hidden" />
					<div class='input-group date' id='datetimepicker1'>
						<input type='text' class="datepicker form-control"/> <span
							class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
			</div>
			<div class="row">
			    <div class="col-md-2"></div>
				<div class="col-md-8">
					<g:actionSubmit class="btn btn-success btn-block" id="fetchData"
						value="Fetch Message" />
				</div>

			</div>
			<br />
		</div>
	</div>
	
	
	<!-- Error Message Window -->
	
	<div class="modal fade" id="dateErrorMessage" role="dialog"	
		style="background-color: rgba(0, 0, 0, 0.5);">
		<div class="modal-dialog">
			<div class="modal-content">
			    <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		        </div>
				<div class="modal-body">
					<div class="row">
						<span id="errorMessage"> 
								<g:message code="common.error" />							
						</span>						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
