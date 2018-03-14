$(document).ready(function(){
			   
			var logoWidth = '257px';
			var logoHeight = '90px';
			var authType = $('#authType').val();
			
			
			if(authType=="AD"){
				  $('#show-fa').hide();
	        	  $('#show-ad').show();
	        	  $("#authTypeAD").attr('checked', 'checked');
	         }else if(authType=="FA"){
	        	  $('#show-ad').hide();
	        	  $('#show-fa').show();
	        	  $("#authTypeFA").attr('checked', 'checked');
	         }else{
	        	 $('#show-ad').hide();
		         $('#show-fa').hide();
	 		}
			
			
			$('.btnOpenFileDialog').click(function(){
				$("#OpenFileDialog").click();
				
		    });
			
			var logoSrc = $("#vendorLogo").attr('src');
			if(logoSrc==""){
				$("#addLogoBttn").show();
				$("#updateLogoBttn").hide();
			}else{
				$("#addLogoBttn").hide();
				$("#updateLogoBttn").show();
			}
			
			$('#vendorLogo').width(logoWidth)
		    $('#vendorLogo').height(logoHeight);
			
			
		    $("#OpenFileDialog").change(function(e) {
				var custInput =  $('#OpenFileDialog')[0];
				var newLogo;
			    if (custInput.files && custInput.files[0]) {
		           var reader = new FileReader();
					reader.onload = function (e) {
						newLogo = e.target.result;
						var data = {'logo': newLogo};
				  	    uploadData('/clientInfo/updateLogo',data);
		               $('#vendorLogo')
		                   .attr('src', e.target.result)
		                   .width(logoWidth)
		                   .height(logoHeight);
		               $("#addLogoBttn").hide();
					   $("#updateLogoBttn").show();
		           };
					reader.readAsDataURL(custInput.files[0]);
				}
			});
		    
		    $('.authGrp').click(function(){
		        
		    	if ($(this).is(':checked'))
		        {
		          var selectedType = $(this).val();
		          if(selectedType=="AD"){
		        	  $('#show-fa').hide();
		        	  $('#show-ad').show();
		        }else{
		        	  $('#show-ad').hide();
		        	  $('#show-fa').show();
		        }
		         var data = {'authType': selectedType};
			  	  uploadData('/clientInfo/updateAuthType',data);
		        }
		      });
		     
		});