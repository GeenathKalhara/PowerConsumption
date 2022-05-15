$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateConsumptionForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidConsumptionIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "ConsumptionService",   
			type : type,  
			data : $("#formConsumption").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onConsumptionSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onConsumptionSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divConsumptionGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidConsumptionIDSave").val("");  
	$("#formConsumption")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidConsumptionIDSave").val($(this).closest("tr").find('#hidConsumptionIDUpdate').val());     
	$("#AccNo").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#Address").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#PreviousUnits").val($(this).closest("tr").find('td:eq(2)').text());
	$("#CurentUnits").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#ConsumedUnits").val($(this).closest("tr").find('td:eq(4)').text());
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "ConsumptionService",   
		type : "DELETE",   
		data : "cID=" + $(this).data("consumid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onConsumptionDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onConsumptionDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divConsumptionGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateConsumptionForm() 
{  

	if ($("#AccNo").val().trim() == "")  
	{   
		return "Insert AccNo.";  
	}
	

	var tmpAcc = $("#Address").val().trim();
	if (!$.isNumeric(tmpAcc)) 
	{
	 return "Insert Address.";
	} 
	
	
	if ($("#PreviousUnits").val().trim() == "")  
	{   
		return "Insert PreviousUnits.";  
	}
	
	if ($("#CurentUnits").val().trim() == "")  
	{   
		return "Insert CurentUnits.";  
	}
	

	 var tmpAmount = $("#ConsumedUnits").val().trim();
	if (!$.isNumeric(tmpConsumedUnits)) 
	 {
	 return "Insert ConsumedUnits.";
	 }

	return true; 
}