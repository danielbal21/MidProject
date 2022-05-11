package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
           		stmt = conn.prepareStatement
    					("UPDATE login_details SET loggedin=? WHERE user_id=?");
    			stmt.setInt(1,1);
    			stmt.setString(2, username);
    			stmt.executeUpdate();
    			return logindetails;
		} 
		catch (SQLException e1) {
            System.err.println("Failed on Authenticate()");
			e1.printStackTrace();
			logindetails[1]=Access.noaut;
			return logindetails;
		}
	}


	public void LoggedOut(String username) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE login_details SET loggedin='0' WHERE user_id=?" );
			stmt.setString(1,username);
           	stmt.executeUpdate(); 	
		} 
		catch (SQLException e1) {
            System.err.println("Failed on LoggedOut()");
			e1.printStackTrace();
		}
	}

	public void getCartItems(ArrayList<Item> cartItems) {
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
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM items where catalog_type=?");
			stmt.setString(1, catalogType.toString());
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
	            	image=rs.getBlob(7);
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
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	            System.err.println(e.getStackTrace());
	        }
		 System.out.println("Get Catalog Items!");
	}
	public ArrayList<String> GetBranches() {
		ArrayList<String> branches = new ArrayList<String>();
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement("SELECT branchName FROM branches");
           	rs = stmt.executeQuery();
           	while(rs.next())
           		branches.add(rs.getString(1));
		} 
		catch (SQLException e1) {
			return null;
		}
		return branches;
	}

	public void AddToCart(String username, int item_id, int quantity) {
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement
					("SELECT ci.quantity FROM cart_item ci WHERE item_id=? "
							+ "AND cart_id = (SELECT c.cart_id FROM carts c WHERE user_id=?)");
			stmt.setInt(1,item_id);
			stmt.setString(2,username);
           	rs = stmt.executeQuery();
           	if(rs.next() == false) {
           		//insert
           		stmt = conn.prepareStatement("INSERT INTO cart_item "
           				+ "VALUES ((SELECT c.cart_id FROM carts c WHERE user_id=?),?,?)");
           		stmt.setString(1,username);
           		stmt.setInt(2,item_id);
           		stmt.setInt(3,quantity);
           		stmt.executeUpdate();
           	}
           	else {
           	//update
           		stmt = conn.prepareStatement("UPDATE cart_item SET quantity=? WHERE item_id=? "
           				+ "AND cart_id = (SELECT c.cart_id FROM carts c WHERE user_id=?)");
           		stmt.setInt(1, quantity + rs.getInt(1));
           		stmt.setInt(2,item_id);
           		stmt.setString(3,username);
           		stmt.executeUpdate();
           	}
           		
		} 
		catch (SQLException e1) {
            System.err.println("Failed on AddToCart()");
			e1.printStackTrace();
		}
		 System.out.println("Add to cart I"+ item_id + " Q="+ quantity);
		 
		
	}
}
