<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="interop.edgeorganizations.title.index" /></title>
<g:javascript>
                  
       function selectTheOrganization(id) {
          $.ajax({		      
				url:appContext+'/edgeSystems/saveSelectedOrganization',
				type:'GET',
				data:{edgeOrgId:id},
	            success:function(data,textStatus) {
	              
	              if(data == "error"){
	              
	                  $("#edgeSystemErrorPanel").modal("show");              
	              }else{		                
	                  
	                  $("#reloadPage").click();
	              } 	        		              
	            },
	            error:function(XMLHttpRequest,textStatus,errorThrown) {
	            
	                  $("#errorText").text(errorThrown);
	                  $("#edgeSystemErrorPanel").modal("show");
	            }
		 });
       }
       
       $(document).ready(function(){
       
           if('${source}' == "edgeSystemList"){
           
              $("#organizationTable1").css("display","block");
              $("#organizationTable2").css("display","none");
              $("#breadcrumb1").css("display","block");
              $("#breadcrumb2").css("display","none");              
           }else{
           
              $("#organizationTable1").css("display","none");
              $("#organizationTable2").css("display","block");
              $("#breadcrumb1").css("display","none");
              $("#breadcrumb2").css("display","block");
           }
       });           
                  
</g:javascript>
</head>
<body>

    <div class="row rowClassStyling" id="breadcrumb1">
        <div class="col-md-8 col-xs-12 leftAlignText">
             <ul class="breadcrumb" style="background-color:transparent">
                <li><g:link uri="/edgeSystems/index"><g:message code="interop.edgesystem.breadcrumb.index" /></g:link></li>
                <li class="active"><g:message code="interop.edgesystem.breadcrumb.edgeOrganizations" /></li>
            </ul>
        </div>  
    </div>
    
    
    <div class="row rowClassStyling" id="breadcrumb2">
        <div class="col-md-8 col-xs-12 leftAlignText">
             <ul class="breadcrumb" style="background-color:transparent">
                <li><g:link uri="/edgeSystems/index"><g:message code="interop.edgesystem.breadcrumb.index" /></g:link></li>
                <li><g:link uri="/edgeSystems/reloadEdgeSystemDetails"><g:message code="interop.edgesystem.details" /></g:link></li>
                <li class="active"><g:message code="interop.edgesystem.breadcrumb.edgeOrganizations" /></li>
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
        <div class="col-md-10 col-xs-10 leftAlignText">
            <div class="sectionHeaderSmall"><g:message code="interop.edgeorganizations.title.index" /></div>
        </div>
        <div class="col-md-2 col-xs-2">
            <div class="row">
                <g:link controller="edgeSystems" action="edgeOrganizationById" class="linkBtn btn btn-success btn-block">
                    <g:message code="interop.edgesystem.link.organizations.new" />
                </g:link>
            </div>
        </div>
    </div>
    
     <div class="row rowClassStyling" id="organizationTable2">
		<table class="table table-striped table-bordered margin_top25 width100 tableStylingTransactionTable"
		    id="edgeSystemListRecord">
		    <thead>
		        <tr>
		            <th width="60px"></th>           
		            <th width="90px"><g:message code="interop.edgeorganization.label.id" /></th>
		            <th><g:message code="interop.edgeorganization.label.name" /></th>
		            <th><g:message code="interop.edgeorganization.label.shortname" /></th>    
		            <th><g:message code="interop.edgeorganization.label.oid" /></th>    
		        </tr>
		    </thead>
		    <tbody>
		        <g:each in="${edgeOrgsList}" status="counter" var="edgeOrg">
		            <tr class="${(counter % 2) == 0 ? 'even' : 'odd'}">
		                <td><input type="radio" name="myGroup" onclick="selectTheOrganization(${edgeOrg.id})" value="1" /></td>
		                <td><g:link controller="edgeSystems" action="edgeOrganizationById" id="${edgeOrg.id}">${edgeOrg.id}</g:link></td>
		                <td class="break-word">
		                    ${edgeOrg.name}
		                </td>               
		                <td class="break-word">
		                    ${edgeOrg.shortName}
		                </td>       
		                <td class="break-word">
		                    ${edgeOrg.organizationOID}
		                </td>
		            </tr>
		        </g:each>
		    </tbody>
		</table>
    </div>
    
    
    <div class="row rowClassStyling" id="organizationTable1">
		<table class="table table-striped table-bordered margin_top25 width100 tableStylingTransactionTable"
		    id="edgeSystemListRecord">
		    <thead>
		        <tr>
		            <th width="60px"></th>           
		            <th width="90px"><g:message code="interop.edgeorganization.label.id" /></th>
		            <th><g:message code="interop.edgeorganization.label.name" /></th>
		            <th><g:message code="interop.edgeorganization.label.shortname" /></th>    
		            <th><g:message code="interop.edgeorganization.label.oid" /></th>    
		        </tr>
		    </thead>
		    <tbody>
		        <g:each in="${edgeOrgsList}" status="counter" var="edgeOrg">
		            <tr class="${(counter % 2) == 0 ? 'even' : 'odd'}">
		                <td></td>
		                <td><g:link controller="edgeSystems" action="edgeOrganizationById" id="${edgeOrg.id}">${edgeOrg.id}</g:link></td>
		                <td class="break-word">
		                    ${edgeOrg.name}
		                </td>               
		                <td class="break-word">
		                    ${edgeOrg.shortName}
		                </td>       
		                <td class="break-word">
		                    ${edgeOrg.organizationOID}
		                </td>
		            </tr>
		        </g:each>
		    </tbody>
		</table>
    </div>
    
 <g:form name="edgeSystemForm" url="[action:'reloadEdgeSystemDetails',controller:'edgeSystems']">
  <div class="row" style="display: none">           
            <div class="col-md-4 col-xs-4">
                <div class="row">
                    <g:actionSubmit
                            class="btn btn-success btn-block width100" id="reloadPage" value="Save Changes" action="reloadEdgeSystemDetails" />
                </div>
            </div>
        </div>
</g:form>
    
    
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