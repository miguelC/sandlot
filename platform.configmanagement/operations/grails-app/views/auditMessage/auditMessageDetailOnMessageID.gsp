<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="custom.menu.interOp.auditMessageDetail" /></title>
<g:javascript>
    
	  function getMessageDetail(messageID){
	 
	     $.ajax({
		      
					url:appContext+'/auditMessage/getMessageDetails',
					type:'GET',
					data:{messageID:messageID},
		            success:function(data,textStatus) {
		              
		              if(data == "error"){
		              
		                  $("#errorMessageDetails").modal("show");
		              
		              }else{		                
		                
			              jQuery('#audit_messageonID').html(data);			            
		              } 	        		              
		            },error:function(XMLHttpRequest,textStatus,errorThrown) {
		           
		            }
			   });	   
	 }
	 
	 
	 function expandExchangeIdText(counterId){
	      $('.plusExchangeIdImage'+counterId).css('display','none');
	      $('.smallExchangeIdText'+counterId).css('display','none');
	      $('.largeExchangeIdText'+counterId).css('display','block');
	      $('.collapseExchangeIdImage'+counterId).css('display','block');
	 }
	 
	 
	 function collapseExchangeIdText(counterId){
	 
	      $('.plusExchangeIdImage'+counterId).css('display','block');
	      $('.smallExchangeIdText'+counterId).css('display','block');
	      $('.largeExchangeIdText'+counterId).css('display','none');
	      $('.collapseExchangeIdImage'+counterId).css('display','none');
	 }
</g:javascript>
</head>
<body>
    <div class="row rowClassStyling">
		<div class="col-md-8 col-xs-12 leftAlignText">
		     <ul class="breadcrumb" style="background-color:transparent">
		        <li><g:link uri="/"><g:message code="audit.label" /></g:link></li>
		        <li><g:link uri="/auditMessage/index"><g:message code="transactions.label" /></g:link></li>
		        <li><g:link uri="/auditMessage/getAuditMessageDetailOnExchangeID"><g:message code="transactiondetail.label" /></g:link></li>
		        <li class="active"><g:message code="messagedetail.label" /></li>
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
		<div class="col-md-4 col-xs-4 leftAlignText">
			<div class="sectionHeaderSmall"><g:message code="interop.audit.label.messageDetails" /></div>
		</div>
        <div class="col-md-2 col-xs-2 lefttAlignText">
            <b><g:message code="custom.menu.interOp.messageID" /> : </b>${messageID}
        </div>  
        <div class="col-md-6 col-xs-6 rightAlignText">
            <b><g:message code="custom.menu.interOp.exchangeID" /> : </b>${exchangeID}
        </div>  
	</div>
	<div class="row">
		<div class="col-md-12 col-xs-12 table-responsive divStylingTransactionTable">		
				<table id="auditMessageDetailsOnID"
					class="table table-bordered" style="table-layout: fixed;">
					<thead>
						<tr>
							<th class="width5"><g:message code="id.label" /></th>
							<th class="width5"><g:message code="status.label" /></th>
							<th class="width15"><g:message code="audittimestamp.label" /></th>
							<th class="width15"><g:message code="exchangeid.label" /></th>
							<th class="width20"><g:message code="direction.label" /></th>
						</tr>
					</thead>
					<tbody>
						<g:each var="auditMessage" in="${auditMessageList}"
							status="counter">
							<tr>
								<td class="width5"><a href="#"
									onclick="getMessageDetail('${auditMessage.id}')"> ${auditMessage.id}
								</a></td>
								<td class="width9" >
								    <g:if test="${auditMessage.audit.failed==true}">										
										<g:img dir="images" file="red_icon.gif" alt="Grails" />
									</g:if> 
									<g:else>
										<g:img dir="images" file="green_icon.png" alt="Grails" />
									</g:else>
								</td>
								<td class="width15">									
									${auditMessage.audit.timeStamp.getDateTimeString()}
								</td>
								<td class="width15">
								    <div class="smallExchangeIdText${counter} textStyling"></div><div class="float_left" ><g:img dir="images" class="plusExchangeIdImage${counter}" onclick="expandExchangeIdText(${counter})" file="plus.gif" alt="Grails" /></div>
								    <div class="largeExchangeIdText${counter} textStyling"></div><div class="float_left" ><g:img dir="images" class="collapseExchangeIdImage${counter}" onclick="collapseExchangeIdText(${counter})" file="minus.jpg" alt="Grails" /></div>									
									<g:javascript>
										var description="	${auditMessage.audit.exchangeId}";
										
										$('.largeExchangeIdText${counter}').text(description);
										
										if(description.length>25) {
										
										  var truncateText = jQuery.trim(description).substring(0, 25);
									      $('.smallExchangeIdText${counter}').text(truncateText);
									      $('largeExchangeIdText${counter}').text(description);
									      
								          $('.plusExchangeIdImage${counter}').css('display','block');
									      $('.smallExchangeIdText${counter}').css('display','block');
									      $('.collapseExchangeIdImage${counter}').css('display','none');
									      $('.largeExchangeIdText${counter}').css('display','none');
									      
									   }else {
									   
									       $('.largeExchangeIdText${counter}').html(description);
									       $('.smallExchangeIdText${counter}').css('display','none');
									       $('.plusExchangeIdImage${counter}').css('display','none');
									       $('.collapseExchangeIdImage${counter}').css('display','none');
									       
									   }
									 </g:javascript>
								</td>
								<td  class="width20">
									${auditMessage.direction}
								</td>														
								
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	<br />
	<div class="row">
		<div id="audit_messageonID"></div>
	</div>
	
    <!-- Error Message Window -->
	
	<div class="modal fade modalBackgroundColor" id="errorMessageDetails" role="dialog">
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
