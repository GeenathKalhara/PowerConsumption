<%@ page import="com.Consumption"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Power Consumptions</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/validation.js"></script> 
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>Power Consumptions</h1>
				<form id="formConsumption" name="formConsumption" method="post" action="Consumption.jsp">  
					Account No:  
 	 				<input id="AccNo" name="AccNo" type="text"  class="form-control form-control-sm">
					<br>Address :   
  					<input id="Address" name="Address" type="text" class="form-control form-control-sm">   
  					<br>PreviousUnits:   
  					<input id="PreviousUnits" name="PreviousUnits" type="text"  class="form-control form-control-sm">
  					<br>CurentUnits:   
  					<input id="CurentUnits" name="CurentUnits" type="text"  class="form-control form-control-sm">
					<br>ConsumedUnits :
					<input id="ConsumedUnits" name="ConsumedUnits" type="text"  class="form-control form-control-sm">
					<br> 
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidConsumptionIDSave" name="hidConsumptionIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
			   <div id="alertError" class="alert alert-danger"></div>
				
			   <br>
				<div id="divConsumptionGrid">
					<%
					    Consumption consumptionObj = new Consumption();
						out.print(consumptionObj.readConsumption());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
 
</body>
</html>