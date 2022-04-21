package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entities.Order;

public class ServerConnSQL{
		
	private Connection conn;
	private String mySQLpassword = "CFcf3719";
	
	
	public ServerConnSQL() {  
	    try 
		{
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        System.out.println("Driver definition succeed");
	    } catch (Exception ex) {
	    	/* handle the error*/
	    	 System.out.println("Driver definition failed");
	    	 }
	    
	    try 
	    {
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/midproject?serverTimezone=IST","root",mySQLpassword);
	        System.out.println("SQL connection succeed");
	 	} catch (SQLException ex) 
	 	    {/* handle any errors*/
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	        }
	}
	
	public void getOrderNumber1(ArrayList<Order> orders) throws SQLException{
		Statement stmt = conn.createStatement();
		Order newOrder=null;
		ResultSet rs;
		 try {
	           	rs = stmt.executeQuery("SELECT * FROM Orders");
	            while (rs.next()) {
	            	newOrder = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), 
	            			rs.getString(4), rs.getString(5), rs.getString(6), 
	            			rs.getString(7), rs.getString(8));
	            	orders.add(newOrder);
	            	}
	        } catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
		 System.out.println("Get Order!");
	}
}
