<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="interop.server.title.index" /></title>
<g:javascript>
        
</g:javascript>
</head>
<body>
    <div class="row rowClassStyling">
        <div class="sectionHeaderSmall"><g:message code="interop.server.label.selectServer" /></div>
<table class="table table-striped table-bordered margin_top25 width100 tableStylingTransactionTable"
    id="auditMessageRecord">
    <thead>
        <tr>
            <th>Server Name</th>           
            <th>Server Description</th>    
            <th></th>        
        </tr>
    </thead>
    <tbody>
        <g:each in="${serverList}" status="counter" var="server">
            <tr class="${(counter % 2) == 0 ? 'even' : 'odd'}">                
                <td class="width9">
                    ${server.name}
                </td>
                <td class="width26 break-word">
                    ${server.description}
                </td>
                <td class="width9">
                <g:link controller="interopServer" action="gotoServerAudits" id="${server.id}"><g:message code="interop.server.link.viewAudits" /></g:link> |
                <g:link controller="interopServer" action="gotoServerConfig" id="${server.id}"><g:message code="interop.server.link.viewConfig" /></g:link> |
                <g:link controller="interopServer" action="gotoServerEdgeSystems" id="${server.id}"><g:message code="interop.server.link.viewEdgeSystems" /></g:link>
                
                </td>               
            </tr>
        </g:each>
    </tbody>
</table>
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