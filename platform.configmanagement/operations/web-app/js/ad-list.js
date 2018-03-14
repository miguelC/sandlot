var selectedId;

$(document).ready(function(){
	 $("#add").click(function(){
		$("#addErrorMsg").hide();
		$("#addSuccessMsg").hide();
	 });
	 $("#edit").click(function(){
		$("#updateErrorMsg").hide();
		$("#updateSuccessMsg").hide();
	 });
	 testAll();
});

function radioClick(e){
	if(selectedId==$(e).val()){
		$("#deleteImg"+selectedId).hide();
		$("#chkImg"+selectedId).show();
		$(e).attr('checked', false);
		selectedId = null
	   	$("#add").show();
	   	$("#edit").hide();
	   	return true;
	}
	if ($(e).is(':checked')){
	   	$("#deleteImg"+selectedId).hide();
	   	$("#chkImg"+selectedId).show();
	   	var data = {'id': $(e).val()};
	   	selectedId = $(e).val()
		$("#add").hide();
		$("#edit").show();
		$("#deleteImg"+selectedId).show();
		$("#chkImg"+selectedId).hide();
	}
}


function updateADList(){
	var sendData = {'id': selectedId};
	downloadDataFn('/ADList/beforeEdit',sendData,updateADListSuccess);
} 

function updateADListSuccess(outData){
	$("#templateUpdateForm").html(outData);
   	$("#updateModal").modal('show');
}

function deleteADList(){
	var sendData = {'id': selectedId};
	downloadData('/ADList/delete',sendData,'templatetable');
	$("#add").show();
   	$("#edit").hide();
} 

function testAddSuccess(data){
	var obj = jQuery.parseJSON(data);
	if(obj.success){
		$("#addSuccessMsg").show();
		$("#addErrorMsg").hide();
		$("#addSuccessMsg").html(obj.success);
		$("#addModal").find("#connectionChecked").val(true);
	}else if(obj.error){
		$("#addErrorMsg").show();
		$("#addSuccessMsg").hide();
		$("#addErrorMsg").html(obj.error);
		$("#addModal").find("#connectionChecked").val(false);
	}
}

function testUpdateSuccess(data){
	var obj = jQuery.parseJSON(data);
	if(obj.success){
		$("#updateSuccessMsg").show();
		$("#updateErrorMsg").hide();
		$("#updateSuccessMsg").html(obj.success);
		$("#updateModal").find("#connectionChecked").val(true);
	}else if(obj.error){
		$("#updateErrorMsg").show();
		$("#updateSuccessMsg").hide();
		$("#updateErrorMsg").html(obj.error);
		$("#updateModal").find("#connectionChecked").val(false);
	}
}

function saveAddSuccess(data){
	$("#templatetable").html(data);
	$('#addModal').modal('hide');
	emptyForm();
}

function saveUpdateSuccess(data){
	$("#templatetable").html(data);
	$('#updateModal').modal('hide');
	$("#add").show();
   	$("#edit").hide();
}


function testAll(){
	downloadData('/ADList/testAll',{},'templatetable');
}

function emptyForm(){
	downloadData('/ADList/emptyForm',{},'templateSaveForm');
}