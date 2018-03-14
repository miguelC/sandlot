var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
var selectedMenu;

$(document).ready(function(){
	selectedMenu = $.session.get('mainMenuClick');
	 if(selectedMenu=="sso"){
		 $('#myTab a[href="#SSO-menu"]').tab('show')
	 }else if(selectedMenu=="interOp"){
		 $('#myTab a[href="#interOp-menu"]').tab('show')
	 }else if(selectedMenu=="tab"){
		 $('#myTab a[href="#tab-menu"]').tab('show')
	 }
	 
	 $(".dropdown").hover(function() {
            $('.dropdown-menu', this).stop( true, true ).fadeIn("fast");
            $(this).toggleClass('open');
            $('b', this).toggleClass("caret caret-up");                
        },
        function() {
            $('.dropdown-menu', this).stop( true, true ).fadeOut("fast");
            $(this).toggleClass('open');
            $('b', this).toggleClass("caret caret-up");                
	   });
});

function uploadData(url,InData){
	jQuery.ajax({
		   type:'POST', 
		   url:contextPath+url,
		   data:InData,
		   success:function(){
			   	
			   },
		   error:function(XMLHttpRequest,textStatus,errorThrown){
			   }
	   });
}

function downloadData(url,Indata,loadElement){
	jQuery.ajax({
		   type:'POST', 
		   url:contextPath+url,
		   data:Indata,
		   success:function(outData){
			   	$("#"+loadElement).html(outData);
			   },
		   error:function(XMLHttpRequest,textStatus,errorThrown){
				}
	   });
}

function downloadDataFn(url,Indata,successFn){
	jQuery.ajax({
		   type:'POST', 
		   url:contextPath+url,
		   data:Indata,
		   success:function(outData){
			   successFn(outData);
			   },
		   error:function(XMLHttpRequest,textStatus,errorThrown){
		   		}
	   });
}

function showSSOPage(){
	$.session.set('mainMenuClick', 'sso');
	document.location.href=contextPath;
}

function showinterOpPage(){
	$.session.set('mainMenuClick', 'interOp');
	document.location.href=contextPath;
}

function showtabPage(){
	$.session.set('mainMenuClick', 'tab');
	document.location.href=contextPath;
}

function removeClientSession(){
	$.session.remove('mainMenuClick');
}
