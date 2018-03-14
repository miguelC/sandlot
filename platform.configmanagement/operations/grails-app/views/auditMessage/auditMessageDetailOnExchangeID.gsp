<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="custom.menu.interOp.auditMessageDetail" /></title>
<g:javascript>
    
	  function getTransationDetail(messageID){
	  
	     $.ajax({
		      
					url:appContext+'/auditMessage/getAuditTransactionDetail',
					type:'GET',
					data:{messageID:messageID},
		            success:function(data,textStatus) {
		              
		              if(data == "error"){
		              
		                  $("#errorMessage").modal("show");
		              
		              }else{		                
		                
			              jQuery('#audit_message').html(data);
			              $('#audit_message').css("border","1px solid");
			              $('#audit_message').css("padding","5px");	
		              } 	        		              
		            },
		            error:function(XMLHttpRequest,textStatus,errorThrown) {
		                  $("#errorText").text(errorThrown);
		                  $("#errorMessage").modal("show");
		            }
			   });	   
	 }
	 
	 
	 function expandText(counterId){
	      $('.plusImage'+counterId).css('display','none');
	      $('.smallText'+counterId).css('display','none');
	      $('.largeText'+counterId).css('display','block');
	      $('.collapseImage'+counterId).css('display','block');
	 }
	 
	 
	 function collapseText(counterId){
	 
	      $('.plusImage'+counterId).css('display','block');
	      $('.smallText'+counterId).css('display','block');
	      $('.largeText'+counterId).css('display','none');
	      $('.collapseImage'+counterId).css('display','none');
	 }
</g:javascript>
</head>
<body>


    <div class="row rowClassStyling">
		<div class="col-md-5 col-xs-12 leftAlignText">
		    <ul class="breadcrumb" style="background-color:transparent">
		        <li><g:link uri="/"><g:message code="audit.label" /></g:link></li>
		        <li><g:link uri="/auditMessage/index"><g:message code="transactions.label" /></g:link></li>
		        <li class="active"><g:message code="transactiondetail.label" /></li>
		    </ul>
		</div>	
	</div>	
	<div class="row">
        <div class="col-md-10 col-xs-10 leftAlignText">
          <div class="sectionHeader"><g:message code="interop.audit.label.serverName" /><font color="#088A29">${interopServer.name}</font></div>
        </div>   
        <div class="col-md-2 col-xs-2 rightAlignText">  
           <g:link controller="interopServer" action="index"><g:message code="interop.audit.link.switchServers" /></g:link>
        </div>
    </div>
    <br/>	
	<div class="row">
		<div class="col-md-3 col-xs-3 leftAlignText">
			<div class="sectionHeaderSmall"><g:message code="interop.audit.label.transactionDetails" /></div>
		</div>
        <div class="col-md-9 col-xs-9 rightAlignText">
            <b><g:message code="custom.menu.interOp.exchangeID" /> : </b>${exchangeID}
        </div>      
	</div>
	<div class="row">
		<div class="col-md-12 col-xs-12 table-responsive divStylingTransactionTable">		
				<table id="auditMessageRecordDetails"
					class="table table-bordered" style="table-layout: fixed;">
					<thead>
						<tr>
							<th class="width5"><g:message code="id.label" /></th>
							<th class="width9"><g:message code="status.label" /></th>
							<th class="width15"><g:message code="event.label" /></th>
							<th class="width20"><g:message code="timestamp.label" /></th>							
							<th class="width10"><g:message code="txtype.label" /></th>
							<th class="width15"><g:message code="sender.label" /></th>
							<th class="width26"><g:message code="destination.label" /></th>
						</tr>
					</thead>
					<tbody>
						<g:each var="auditMessage" in="${auditMessageList}"
							status="counter">
							<tr>
								<td class="width5" style="width: 5% !important"><a href="#"
									onclick="getTransationDetail('${auditMessage.id}')"> ${auditMessage.id}
								</a></td>
								<td class="width9">
								    <g:if test="${auditMessage.failed == true}">										
										<g:img dir="images" file="red_icon.gif" alt="Grails" />
									</g:if> 
									<g:else>
										<g:img dir="images" file="green_icon.png" alt="Grails" />
									</g:else>
								</td>
								<td class="width15">									
									<g:if test="${message(code: 'eventSentText') == auditMessage.eventName}">
										<g:message code="sentevent.label" />
									</g:if> 
									<g:else>
										<g:message code="createdevent.label" />
									</g:else>
								</td>
								<td  class="width20">
									${auditMessage.timeStamp.getDateTimeString()}
								</td>															
								<td class="width10">
									${auditMessage.transactionType}
								</td>
								<td class="width15">
									${auditMessage.sender }
								</td>
								<td class="width26 break-word">
								    <div class="smallText${counter} textStyling"></div><div class="float_left"><g:img dir="images" class="plusImage${counter}" onclick="expandText(${counter})" file="plus.gif" alt="Grails" /></div>
								    <div class="largeText${counter} textStyling"></div><div class="float_left"><g:img dir="images" class="collapseImage${counter}" onclick="collapseText(${counter})" file="minus.jpg" alt="Grails" /></div>
								    <g:javascript>
										var description="${auditMessage.receiver}";
										
										$('.largeText${counter}').text(description);
										
										if(description.length>25) {
										
										  var truncateText = jQuery.trim(description).substring(0, 25);
									      $('.smallText${counter}').text(truncateText);
									      $('largeText${counter}').text(description);
									      
								          $('.plusImage${counter}').css('display','block');
									      $('.smallText${counter}').css('display','block');
									      $('.collapseImage${counter}').css('display','none');
									      $('.largeText${counter}').css('display','none');
									      
									   }else {
									   
									       $('.largeText${counter}').html(description);
									       $('.smallText${counter}').css('display','none');
									       $('.plusImage${counter}').css('display','none');
									       $('.collapseImage${counter}').css('display','none');
									       
									   }
									 </g:javascript>  
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	<br />
	<div class="row">
		<div id="audit_message"></div>
	</div>
	
    <!-- Error Message Window -->
	
	<div class="modal fade modalBackgroundColor" id="errorMessage" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
			    <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		        </div>
				<div class="modal-body">
					<div class="row">
						<span id="errorText"> <g:if test="${flash.message}">
								<asset:image src="exclamation.png" alt="Grails" />
								<b id="errorMsg" class="margin_left"> ${flash.message}
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
