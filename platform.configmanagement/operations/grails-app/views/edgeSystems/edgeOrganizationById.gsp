<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="interop.edgeorganization.title.edgeOrganization"/></title>
<g:javascript>    
     
     function openMessage(){
       $("#viewMessage").modal("show");  
     }
     
      $(document).ready(function(){
       
           if('${source}' == "edgeSystemList"){         
            
              $("#breadcrumb2").css("display","block");
              $("#breadcrumb1").css("display","none");              
           }else{          
             
              $("#breadcrumb2").css("display","none");
              $("#breadcrumb1").css("display","block");
           }
       }); 
     
</g:javascript>
</head>
<body>

    <div class="row rowClassStyling" id="breadcrumb1">
        <div class="col-md-8 col-xs-12 leftAlignText">
             <ul class="breadcrumb" style="background-color:transparent">
                <li><g:link uri="/edgeSystems/index"><g:message code="interop.edgesystem.breadcrumb.index" /></g:link></li>
                <li><g:link uri="/edgeSystems/index"><g:message code="interop.edgesystem.breadcrumb.index" /></g:link></li>
                <li><g:link uri="/edgeSystems/edgeOrganizationsFromEdgeSystemDetails"><g:message code="interop.edgesystem.breadcrumb.edgeOrganizations" /></g:link></li>
                <li class="active"><g:message code="interop.edgesystem.breadcrumb.edgeOrganization" /></li>
            </ul>
        </div>  
    </div>  

    <div class="row rowClassStyling" id="breadcrumb2">
        <div class="col-md-8 col-xs-12 leftAlignText">
             <ul class="breadcrumb" style="background-color:transparent">
                <li><g:link uri="/edgeSystems/index"><g:message code="interop.edgesystem.breadcrumb.index" /></g:link></li>
                <li><g:link uri="/edgeSystems/edgeOrganizations"><g:message code="interop.edgesystem.breadcrumb.edgeOrganizations" /></g:link></li>
                <li class="active"><g:message code="interop.edgesystem.breadcrumb.edgeOrganization" /></li>
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
            <div class="sectionHeaderSmall"><g:message code="interop.edgeorganization.title.edgeOrganization" /> '<font color="#088A29">${edgeOrganization.id}</font>'</div>
        </div>
    </div>
    <br/>
<g:form name="myForm" url="[action:'saveEdgeOrganization',controller:'edgeSystems']">
  <div class="row">
    <div class="col-md-12 col-xs-12">
        <div class="row">
            <div class="col-md-3 col-xs-3 rightAlignText">
              <b><g:message code="interop.edgeorganization.label.name" />:</b>
            </div>
            <div class="col-md-9 col-xs-9 leftAlignText">
              <g:field type="text" size="70" name="name" value="${edgeOrganization.name}"/>
              <g:hiddenField name="id" value="${edgeOrganization.id}"/>
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="col-md-3 col-xs-3 rightAlignText">
              <b><g:message code="interop.edgeorganization.label.shortname" />:</b>
            </div>
            <div class="col-md-9 col-xs-9 leftAlignText">
              <g:field type="text" size="70" name="shortName" value="${edgeOrganization.shortName}"/>
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="col-md-3 col-xs-3 rightAlignText">
              <b><g:message code="interop.edgeorganization.label.oid" />:</b>
            </div>
            <div class="col-md-9 col-xs-9 leftAlignText">
              <g:field type="text" size="70" name="organizationOID" value="${edgeOrganization.organizationOID}"/>
            </div>
        </div>
    </div>
  </div>
  <HR WIDTH="100%" ALIGN="left">
  <div class="row">
    <div class="col-md-12 col-xs-12">  
        <div class="row">
          <div class="col-md-5 col-xs-5">
             
          </div>
          <div class="col-md-7 col-xs-7">
             <g:if test="${flash.message}">
					<b id="errorMsg" style="color: red">${flash.message}</b>
			 </g:if>
          </div>
        </div>
        <div>
        
        </div>
        <div class="row">
            <div class="col-md-8 col-xs-8">
                <div class="row">
                </div>
            </div>
            <div class="col-md-4 col-xs-4">
                <div class="row">
                    <g:actionSubmit
                            class="btn btn-success btn-block width100" value="Save Changes" action="saveEdgeOrganization" />
                </div>
            </div>
        </div>
    </div>
  </div>
</g:form>
<g:if test="${savedStatusMessage}">
    <div class="modal fade" id="viewMessage" role="dialog" class="modalBackgroundColor">
        <div class="modal-dialog">
            <div class="modal-content padding10">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body break-word overflowStyling">
                    <div class="float_left width100"> ${savedStatusMessage}</div>                  
                </div>
            </div>
        </div>
    </div>
</g:if>
<!-- Error Message Window -->
    
    <div class="modal fade" id="viewMessage" role="dialog" class="modalBackgroundColor">
        <div class="modal-dialog">
            <div class="modal-content padding10">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body break-word overflowStyling">
                    <div class="float_left width100">                          
                        <b><g:message code="errormessage.label" />:</b><br/>
                        <textarea class="form-control" rows="3" id="errorMsg"></textarea>
                        <br/>
                        <b><g:message code="errorstacktrace.label" />:</b><br/>
                        <textarea class="form-control whiteSpaceStyling" rows="30" id="errorStackTrace"></textarea>
                    </div>                      
                </div>
            </div>
        </div>
    </div>

<br />
</body>
</html>