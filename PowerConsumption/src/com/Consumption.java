package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Consumption {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/monitor?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertConsumption(String AccNo, String Address, String PreviousUnits, String CurentUnits, String ConsumedUnits)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into consumption(`cID`,`AccNo`,`Address`,`PreviousUnits`,`CurentUnits`,`ConsumedUnits`)"
					+ " values (?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, AccNo);
				preparedStmt.setString(3, Address);
				preparedStmt.setString(4, PreviousUnits);
				preparedStmt.setString(5, CurentUnits);
				preparedStmt.setString(5, ConsumedUnits);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newConsumption = readConsumption(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newConsumption + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the consumption.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	
	public String readConsumption()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>AccNo</th><th>Address</th><th>PreviousUnits</th><th>CurentUnits</th><th>ConsumedUnits</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from consumption";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				String cID = Integer.toString(rs.getInt("cID"));
				String AccNo = rs.getString("AccNo");
				String Address = rs.getString("Address");
				String PreviousUnits = rs.getString("PreviousUnits");
				String CurentUnits = rs.getString("CurentUnits");
				String ConsumedUnits = rs.getString("ConsumedUnits");
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidConsumptionIDUpdate\' name=\'hidConsumptionIDUpdate\' type=\'hidden\' value=\'" + cID + "'>" 
							+ AccNo + "</td>"; 
				output += "<td>" + Address + "</td>";
				output += "<td>" + PreviousUnits + "</td>";
				output += "<td>" + CurentUnits + "</td>";
				output += "<td>" + ConsumedUnits + "</td>";
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-consumid='" + cID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the consumption.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	
	public String updateConsumption(String cID, String AccNo, String Address, String PreviousUnits, String CurentUnits, String ConsumedUnits)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE consumption SET AccNo=?,Address=?,PreviousUnits=?,CurentUnits=?,ConsumedUnits=?" + "WHERE cID=?"; 	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, AccNo);
			preparedStmt.setString(2, Address);
			preparedStmt.setString(3, PreviousUnits);
			preparedStmt.setString(4, CurentUnits);
			preparedStmt.setString(4, ConsumedUnits);
			preparedStmt.setInt(5, Integer.parseInt(cID));
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newConsumption = readConsumption();    
			output = "{\"status\":\"success\", \"data\": \"" + newConsumption + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the consumption.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	public String deleteConsumption(String cID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from billpay where cID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(cID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newConsumption = readConsumption();    
			output = "{\"status\":\"success\", \"data\": \"" +  newConsumption + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the consumption.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
