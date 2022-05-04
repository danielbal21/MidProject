package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entities.*;

public class ServerConnSQL{
		
	private static Connection conn;
	//private String mySQLpassword = "123456";
	
	public static boolean startConn(String mySQLpassword) {  
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
	        return true;
	 	} catch (SQLException ex) 
	 	    {/* handle any errors*/
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	        return false;
	        }
	}
	
	public void getOrders(ArrayList<Order> orders, String[] params){
		PreparedStatement stmt = null;
		Order newOrder=null;
		ResultSet rs;
		try
		{
			if(params ==null)
			{
				stmt = conn.prepareStatement("SELECT * FROM Orders");
			}
			else 
			{
				if(params[0].equals(""))
				{
					stmt = conn.prepareStatement("SELECT * FROM Orders WHERE branch = ? AND status = 'pending'");
					stmt.setString(1,params[1]);
				}
				else
				{
					stmt = conn.prepareStatement("SELECT * FROM Orders WHERE username = ? ");
					stmt.setString(1,params[0]);
				}
			}
			rs = stmt.executeQuery();
			while (rs.next()) {
				newOrder = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3).equals("null") ? "" : rs.getString(3),
						rs.getString(4), rs.getString(5).equals("null") ? "" : rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8),rs.getString(9));
				orders.add(newOrder);
			}
		}
	catch(Exception e)
	{
		System.err.println("Got an exception! ");
		System.err.println(e.getMessage());
	}System.out.println("Get Orders!");
	}

	public void UpdateOrder_ColorAndDate_ByNumber(String orderNumber,String newColor, String newDate) {
		PreparedStatement stmt;
		try 
		{
			stmt = conn.prepareStatement("UPDATE Orders SET color=?,date=? WHERE orderNumber=?");
			stmt.setString(1, newColor);
			stmt.setString(2, newDate);
			stmt.setString(3, orderNumber);
			stmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}	
		System.out.println("Updated order "+orderNumber);	
	}

	public void UpdateOrder_ColorAndDate_ByNumber(String orderNumber,String newColor, String newDate) {
		PreparedStatement stmt;
		try 
		{
			stmt = conn.prepareStatement("UPDATE Orders SET color=?,date=? WHERE orderNumber=?");
			stmt.setString(1, newColor);
			stmt.setString(2, newDate);
			stmt.setString(3, orderNumber);
			stmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}	
		System.out.println("Updated order "+orderNumber);	
	}

	public Roles Authenticate(String username, String password) {
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement("SELECT role FROM logindetails WHERE username=? AND password=?");
			stmt.setString(1,username);
			stmt.setString(2, password);
           	rs = stmt.executeQuery();
           	if(rs.next() != false)
           		return Roles.valueOf(rs.getString(1));
           	else
    			return Roles.noaut;
		} 
		catch (SQLException e1) {
            System.err.println("Failed on Authenticate()");
			e1.printStackTrace();
			return Roles.noaut;
		}
	}

	public String LoggedIn(String username) {
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement("SELECT LoggedIn FROM logindetails WHERE username=?");
			stmt.setString(1,username);
           	rs = stmt.executeQuery();
           	if(rs.next() != false) {
           		String loggedin = rs.getString(1);
           		if(loggedin.equals("0")) {
           			stmt = conn.prepareStatement("UPDATE logindetails SET LoggedIn='1' WHERE username=?");
           			stmt.setString(1,username);
           			stmt.executeUpdate();
           		}
           		return loggedin;
           	}	
           	else
    			return "Error";
		} 
		catch (SQLException e1) {
            System.err.println("Failed on LoggedIn()");
			e1.printStackTrace();
			return "Error";
		}
	}

	public void LoggedOut(String username) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE logindetails SET LoggedIn='0' WHERE username=?" );
			stmt.setString(1,username);
           	stmt.executeUpdate(); 	
		} 
		catch (SQLException e1) {
            System.err.println("Failed on LoggedOut()");
			e1.printStackTrace();
		}
	}

	public void getCartItems(ArrayList<Item> cartItems) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			} 
		catch (SQLException e1) {
            System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		
		Item item=null;
		ResultSet rs;
		 try {
	           	rs = stmt.executeQuery("SELECT * FROM Orders");//table for item
	            while (rs.next()) {
	            	item = new Item(rs.getInt(1), rs.getString(2), rs.getInt(3), 
	            			(CatalogType)rs.getObject(4), (ItemType)rs.getObject(5), (Color)rs.getObject(6));
	            	cartItems.add(item);
	            	}
	        } catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
		 System.out.println("Get Cart Items!");
		
	}

	public void getCatalogItems(ArrayList<Item> catalogItems) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			} 
		catch (SQLException e1) {
            System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		
		Item item=null;
		ResultSet rs;
		 try {
	           	rs = stmt.executeQuery("SELECT * FROM Orders");//table for item
	            while (rs.next()) {
	            	item = new Item(rs.getInt(1), rs.getString(2), rs.getInt(3), 
	            			(CatalogType)rs.getObject(4), (ItemType)rs.getObject(5), (Color)rs.getObject(6));
	            	catalogItems.add(item);
	            	}
	        } catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
		 System.out.println("Get Catalog Items!");
		
	}
}
