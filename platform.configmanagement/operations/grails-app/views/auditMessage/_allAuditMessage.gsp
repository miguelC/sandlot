<g:javascript>
                  
        /* Formatting function for row details - modify as you need */
		function format ( d ) {
		    return '<table cellpadding="5" class="paddingLeft50" cellspacing="0" border="0" style="padding-left:50px;">'+
		        '<tr>'+
		            '<td>Destination :</td>'+
		            '<td>'+d.Destination+'</td>'+
		        '</tr>'+
		        '<tr>'+
		            '<td>Tx Time :</td>'+
		            '<td>'+d.TxTime+'</td>'+
		        '</tr>'+
		        '<tr>'+
		            '<td>Subject Info :</td>'+
		            '<td>'+d.SubjectInfo+'</td>'+
		        '</tr>'+
		        '<tr>'+
		            '<td>Exchange ID :</td>'+
		            '<td>'+d.ExchangeID+'</td>'+
		        '</tr>'+
		    '</table>';
		}

    	 $(document).ready(function(){   	 
    	  
			   var table = $('#auditMessageRecord').DataTable({
			   
			         "columns": [
			            {
			                "className":      'details-control',
			                "orderable":      false,
			                "data":           null,
			                "defaultContent": ''
			            },
			            { "data": "ID" },
			            { "data": "Status" },
			            { "data": "Time" },
			            { "data": "Sender" },
			            { "data": "TxType" },
			            { "data": "SubjectInfo" },			        
			            { "data": "TxTime" },
			            { "data": "Destination" },
			            { "data": "ExchangeID" }
			        ],
			        "order": [[1, 'desc']],
			        "columnDefs": [
			        
			                {
				                "targets": [ 0 ],
				                "width": "10%"
				            },
				            {
				                "targets": [ 1 ],
				                "width": "10%"
				            },
				            {
				                "targets": [ 2 ],
				                "width": "10%"
				            },
				            {
				                "targets": [ 3 ],
				                 "width": "30%"
				            },
				            {
				                "targets": [ 4 ],
				                 "width": "20%"
				            },
				            {
				                "targets": [ 5 ],
				                 "width": "20%"
				                
				            },
				            {
				                "targets": [ 6 ],
				                "visible": false,
				                "searchable": true
				            },
				            {
				                "targets": [ 7 ],
				                "visible": false,
				                "searchable": true
				            },
				            {
				                "targets": [ 8 ],
				                "visible": false,
				                "searchable": true
				            },
				            {
				                "targets": [ 9 ],
				                "visible": false,
				                "searchable": true
				                
				            }
			        ]
			        
			      
			   });
			    
			    // Add event listener for opening and closing details
			    $('#auditMessageRecord tbody').on('click', 'td.details-control', function () {
			        var tr = $(this).closest('tr');
			        var row = table.row( tr );
			 
			        if ( row.child.isShown() ) {
			            // This row is already open - close it
			            row.child.hide();
			            tr.removeClass('shown');
			        }
			        else {
			            // Open this row
			            row.child( format(row.data()) ).show();
			            tr.addClass('shown');
			        }
			    });
	   });

     function openAuditMessageDetail(auditMessageID){
		      $.ajax({
		      
					url:appContext+'/auditMessage/getAuditMessageDetailOnExchangeID',
					type:'GET',
					data:{messageID:auditMessageID},
		            success:function(data,textStatus) {
	                   var w = window.open();
	              	   $(w.document.body).html(data);		                           
		            },error:function(XMLHttpRequest,textStatus,errorThrown) {
		           
		            }
			   });
	}
</g:javascript>
<div class="row">
    <div class="col-md-10 col-xs-10 leftAlignText">
      <div class="sectionHeader"><g:message code="interop.audit.label.serverName" /><font color="#088A29">${interopServer.name}</font></div>
    </div>   
    <div class="col-md-2 col-xs-2 rightAlignText">  
       <g:link controller="interopServer" action="index"><g:message code="interop.audit.link.switchServers" /></g:link>
    </div>
</div>
<br/>
<table class="table table-striped table-bordered margin_top25 width100 tableStylingTransactionTable"
	id="auditMessageRecord">
	<thead>
		<tr>
		    <th></th>			
			<th><g:message code="id.label" /></th>
			<th><g:message code="status.label" /></th>
			<th><g:message code="time.label" /></th>			
			<th><g:message code="sender.label" /></th>
			<th><g:message code="txtype.label" /></th>
			<th>Subject Info</th>
			<th><g:message code="txtime.label" /></th>
			<th><g:message code="destination.label" /></th>
			<th><g:message code="exchangeid.label" /></th>			
		</tr>
	</thead>
	<tbody>
		<g:each in="${auditMessageList}" status="counter" var="auditMessage">
			<tr class="${(counter % 2) == 0 ? 'even' : 'odd'}">
				<td></td>
				<td><g:link controller="auditMessage" action="getAuditMessageDetailOnExchangeID" id="${auditMessage.exchangeId}">${auditMessage.id}</g:link></td>
				<td class="break-word"><g:if
						test="${auditMessage.failed==true}">						
						<g:img dir="images" file="red_icon.gif" alt="Grails" />
					</g:if> <g:else>
						<g:img dir="images" file="green_icon.png" alt="Grails" />
					</g:else></td>
				<td class="break-word">
					${auditMessage.timeStamp.getDateTimeString()}
				</td>				
				<td class="break-word">
					${auditMessage.sender}
				</td>
				<td class="break-word">
					${auditMessage.transactionType}
				</td>
				<td class="break-word">
				  ${auditMessage.subjectInfo}
				</td>				
				<td class="break-word">
					${auditMessage.processingTimeMillis}
				</td>
				<td class="break-word">
					${auditMessage.receiver}
				</td>
				<td class="break-word">
					${auditMessage.exchangeId}
				</td>
			</tr>
		</g:each>
	</tbody>
</table>
<div id="audit_message_detail"></div>