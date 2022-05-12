package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;

import Entities.*;
import javafx.scene.image.Image;

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
		Server.Log("Database", "Executing GetOrders");
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
				/*newOrder = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3).equals("null") ? "" : rs.getString(3),
						rs.getString(4), rs.getString(5).equals("null") ? "" : rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8),rs.getString(9));
				orders.add(newOrder);*/
			}
		}
	catch(Exception e)
	{
		Server.Log("Database", "Executing GetOrders: FAILED");
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

	public Object[] Authenticate(String username, String password) {
		Server.Log("Database", "Executing Authenticate");
		PreparedStatement stmt = null;
		Object[] logindetails=new Object[3]; 
		ResultSet rs;
		try {
			stmt = conn.prepareStatement
					("SELECT ld.loggedin,ld.access,ud.role FROM login_details ld,user_details ud "
							+ "WHERE ld.user_id=? AND ld.user_id = ud.user_id "
							+ "And ld.password=?");
			stmt.setString(1,username);
			stmt.setString(2, password);
           	rs = stmt.executeQuery();
           	if(rs.next() == false)
           	{
           		logindetails[1]=Access.noaut;
           		
           		return logindetails;
           	}
           	else
           		logindetails[0]=rs.getInt(1);
           		logindetails[1]=Access.valueOf(rs.getString(2));
           		logindetails[2]=Roles.valueOf(rs.getString(3));
    			return logindetails;
		} 
		catch (SQLException e1) {
			Server.Log("Database", "Executing Authenticate: FAILED");
			e1.printStackTrace();
			logindetails[1]=Access.noaut;
       		
			return logindetails;
		}
	}


	public void LoggedOut(String username) {
		Server.Log("Database", "Executing Logout");
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE login_details SET loggedin='0' WHERE user_id=?");
			stmt.setString(1,username);
           	stmt.executeUpdate(); 	
		} 
		catch (SQLException e1) {
			Server.Log("Database", "Executing Logout: FAILED");
            System.err.println("Failed on LoggedOut()");
			e1.printStackTrace();
		}
	}

	public void getCartItems(ArrayList<Item> cartItems) {
		Server.Log("Database", "Executing GetCardItems");
		/*Statement stmt = null;
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
		*/
	}

	public void getCatalogItems(ArrayList<Item> catalogItems,CatalogType catalogType) {
		Server.Log("Database", "Executing GetCatalogItems");
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM items where catalog_type=?");
			stmt.setString(1, catalogType.toString());
			System.out.println(stmt.toString());
			} 
		catch (SQLException e1) {
            System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		Blob image;
		Item item=null;
		ResultSet rs;
		InputStream stream;
		 try {
	           	rs = stmt.executeQuery();
	            while (rs.next()) {

	            	//need to change blob to long blob 
	            	Image bufferImage;
	            	System.out.println("ENTERING LOOP");
	            	image=rs.getBlob(7);
	            	System.out.println("GOT BLOB");
	            	if (image == null)
	            	{
	            		stream = getClass().getResourceAsStream("/png/no-image.png");
	            		//stream =new FileInputStream("/png/no-image.png");
	            	}
	            	else {
	            		stream = image.getBinaryStream();
		            	//bufferImage = new Image(input);
	            	}
	            	
	            	item = new Item(rs.getInt(1), rs.getString(2), rs.getInt(3),CatalogType.valueOf(rs.getString(4)), 
	            			ItemType.valueOf(rs.getString(5)), 
	            			Color.valueOf(rs.getString(6)==null?"non_color":rs.getString(6)),stream.readAllBytes());
	            	catalogItems.add(item);
	            	}
	        } catch (Exception e) {
	    		Server.Log("Database", "Executing GetCatalogItems: FAILED");
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	            System.err.println(e.getStackTrace());
	        }
		 System.out.println("Get Catalog Items!");
	}
	public ArrayList<String> GetBranches() {
		Server.Log("Database", "Executing GetBranches");
		ArrayList<String> branches = new ArrayList<String>();
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement("SELECT branch_name FROM manager_details");
           	rs = stmt.executeQuery();
           	while(rs.next())
           		branches.add(rs.getString(1));
		} 
		catch (SQLException e1) {
			Server.Log("Database", "Executing GetCatalogItems: FAILED");
			e1.printStackTrace();
		}
		return branches;
	}

	public String[] GetCurrency(String requestee) {
		Server.Log("Database", "Executing GetCurrency");
		String[] details = null;
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement("SELECT credit_card,cvv,exp_month,exp_year,zerli_coin,new_customer FROM customer_details WHERE user_id='" + requestee + "'");
           	rs = stmt.executeQuery();
           	while(rs.next())
           		details = new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};
		} 
		catch (SQLException e1) {
			Server.Log("Database", "Executing GetCurrency: FAILED");
			e1.printStackTrace();
		}
		return details;
	}

	public void UpdateZerliCoins(String requestee, int data) {
		Server.Log("Database", "Executing UpdateZerliCoins");
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE customer_details SET zerli_coin=? WHERE user_id=?");
			stmt.setInt(1, data);
			stmt.setString(2, requestee);
           	stmt.executeUpdate();
		} 
		catch (SQLException e1) {
			Server.Log("Database", "Executing UpdateZerliCoins: FAILED");
			e1.printStackTrace();
		}
	}
	
	public void InsertOrder(Order order,String requestee)
	{
		Server.Log("Database", "Executing InsertOrder");
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO orders (user_id,payment_method,shipping_method,order_date,shipping_date,branch_name,greeting_card,total_price,status,address,city)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, requestee);
			stmt.setString(2, order.getPaymentMethod().toString());
			stmt.setString(3, order.getShippingMethod().toString());
			stmt.setTimestamp(4, order.getOrderDate());
			if(order.getShippingDate() != null)
				stmt.setTimestamp(5, order.getShippingDate());
			else
				stmt.setNull(5,Types.TIMESTAMP);;
			stmt.setString(6, order.getBranchName());
			if(order.getGreetingCard() != null)
				stmt.setString(7, order.getGreetingCard());
			else
				stmt.setNull(7,java.sql.Types.VARCHAR);
			stmt.setInt(8, order.getTotalPrice());
			stmt.setString(9,order.getStatus().toString());
			if(order.getAddress() != null)
				stmt.setString(10, order.getAddress());
			else
				stmt.setNull(10,Types.VARCHAR);;
			if(order.getCity() != null)
				stmt.setString(11, order.getCity());
			else
				stmt.setNull(11,Types.VARCHAR);;
           	stmt.executeUpdate();
           	
           	stmt = conn.prepareStatement("SELECT order_id FROM orders WHERE user_id = ? AND order_date = ?");
           	stmt.setString(1, requestee);
           	stmt.setTimestamp(2, order.getOrderDate());
           	ResultSet rs = stmt.executeQuery();
           	int orderID = -1;
           	while(rs.next())
           	{
           		orderID = rs.getInt(1);
           	}
           	if(orderID == -1){
        		Server.Log("Database", "Executing InsertOrder: FAILED could not track OrderID");
           		throw new RuntimeException("ERROR");
           	}
           	for(ItemInList item : order.getItems())
           	{
               	stmt = conn.prepareStatement("INSERT INTO order_item VALUES (?,?,?)");
           		stmt.setInt(1, orderID);
           		stmt.setInt(2, item.getItem_id());
           		stmt.setInt(3, item.getQuantity());
           		stmt.executeUpdate();
           	}

		} 
		catch (SQLException e1) {
    		Server.Log("Database", "Executing InsertOrder: FAILED");
			e1.printStackTrace();
		}
	}
}
