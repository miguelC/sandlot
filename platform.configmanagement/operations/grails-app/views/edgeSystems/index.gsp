<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="interop.edgesystem.title.index" /></title>
<g:javascript>
                  
        /* Formatting function for row details - modify as you need */
        function format ( d ) {
            return '<table cellpadding="5" class="paddingLeft50" cellspacing="0" border="0" style="padding-left:50px;">'+
                '<tr>'+
                    '<td>AssigningAuthorityOID :</td>'+
                    '<td>'+d.AssigningAuthorityOID+'</td>'+
                '</tr>'+
                 '<tr>'+
                    '<td>DocumentSourceOID :</td>'+
                    '<td>'+d.DocumentSourceOID+'</td>'+
                '</tr>'+
            '</table>';
        }

         $(document).ready(function(){       
          
               var table = $('#edgeSystemListRecord').DataTable({
                    <!-- responsive: true -->
                    
                    "columns": [
                        {
                            "className":      'details-control',
                            "orderable":      false,
                            "data":           null,
                            "defaultContent": ''
                        },
                        { "data": "ID" },
                        { "data": "Name" },
                        { "data": "AssigningAuthorityOID" },
                        { "data": "DocumentSourceOID" },
                        { "data": "OrganizationID" },
                        { "data": "OrganizationName" }
                    ],
                    "order": [[1, 'asc']],
                    "columnDefs": [
                            {
                                "targets": [ 0 ],
                                "width": "10%"
                            },
                            {
                                "targets": [ 1 ],
                                "width": "10%",
                                "searchable": true
                                
                            },
                            {
                                "targets": [ 2 ],
                                "searchable": true
                            },
                            {
                                "targets": [ 3 ],
                                "visible": false,
				                "searchable": true
                            },{
                                "targets": [ 4 ],
                                "visible": false,
				                "searchable": true
                            },{
                                "targets": [ 5 ],
                                "width": "10%",
                                "searchable": true
                            },{
                                "targets": [ 6 ],
                                "searchable": true
                            }
                        ]
                });
                
                // Add event listener for opening and closing details
                $('#edgeSystemListRecord tbody').on('click', 'td.details-control', function () {
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
</g:javascript>
</head>
<body>

    <div class="row rowClassStyling">
        <div class="col-md-8 col-xs-12 leftAlignText">
             <ul class="breadcrumb" style="background-color:transparent">
                <li class="active"><g:link uri="/edgeSystems/index"><g:message code="interop.edgesystem.breadcrumb.index" /></g:link></li>
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
    <HR WIDTH="75%" ALIGN="left">
    <div class="row">
        <div class="col-md-12 col-xs-12 leftAlignText">
            <div class="sectionHeaderSmall"><g:message code="interop.edgesystem.title.index" /></div>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-4 col-xs-4 leftAlignText">
        </div>
        <div class="col-md-4 col-xs-4 leftAlignText">
            <g:link controller="edgeSystems" action="edgeSystemById" class="linkBtn btn btn-success btn-block"><g:message code="interop.edgesystem.link.new" /></g:link>
        </div>
        <div class="col-md-4 col-xs-4 leftAlignText">
            <g:link controller="edgeSystems" action="edgeOrganizations" class="linkBtn btn btn-success btn-block"><g:message code="interop.edgesystem.link.organizations" /></g:link>
        </div>
    </div>
    <div class="row rowClassStyling">
<table class="table table-striped table-bordered margin_top25 width100 tableStylingTransactionTable"
    id="edgeSystemListRecord">
    <thead>
        <tr>
            <th width="60px"></th>           
            <th width="90px"><g:message code="interop.edgesystem.id.label" /></th>
            <th><g:message code="interop.edgesystem.name.label" /></th>
            <th><g:message code="interop.edgesystem.assigningAuthority.label" /></th>            
            <th><g:message code="interop.edgesystem.documentSource.label" /></th>
            <th>Organization ID</th>
            <th><g:message code="interop.edgesystem.organization.name.label" /></th>      
        </tr>
    </thead>
    <tbody>
        <g:each in="${edgeSystemList}" status="counter" var="edgeSystem">
            <tr class="${(counter % 2) == 0 ? 'even' : 'odd'}">
                <td></td>
                <td><g:link controller="edgeSystems" action="edgeSystemById" id="${edgeSystem.id}">${edgeSystem.id}</g:link></td>
                <td class="break-word">
                    ${edgeSystem.name}
                </td>               
                <td class="break-word">
                    ${edgeSystem.assigningAuthorityOID}
                </td>
                <td class="break-word">
                  ${edgeSystem.documentSourceOID}
                </td>
                <td class="break-word">
                  ${edgeSystem.organization.id}
                </td>               
                <td class="break-word">
                    ${edgeSystem.organization.name}
                </td>
            </tr>
        </g:each>
    </tbody>
</table>
    </div>
    
    
    <!-- Error Message Window -->
    
    <div class="modal fade" id="edgeSystemErrorPanel" role="dialog" 
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