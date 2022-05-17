package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.io.InputStream;
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
           		stmt = conn.prepareStatement
    					("UPDATE login_details SET loggedin=? WHERE user_id=?");
    			stmt.setInt(1,1);
    			stmt.setString(2, username);
    			stmt.executeUpdate();
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

	public void getCartItems(String username,ArrayList<ItemInList> cartItems) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT quantity FROM cart_item WHERE cart_id=(SELECT cart_id FROM carts WHERE user_id= ?)");
			stmt.setString(1, username);
		} 
		catch (SQLException e1) {
            System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		ArrayList<Integer> quantityList=new ArrayList<Integer>();
		ResultSet rs;
		 try {
			 	rs=stmt.executeQuery();
			 	while (rs.next()) {
			 		quantityList.add(rs.getInt(1));
			 	}
		 }
		 catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
		try {
			stmt = conn.prepareStatement("SELECT * FROM items WHERE item_id IN  (SELECT item_id FROM Cart_item WHERE cart_id=(SELECT cart_id FROM carts WHERE user_id= ?))");
			stmt.setString(1, username);
		} 
		catch (SQLException e1) {
            System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		
		ItemInList itemInListlist=null;
		Blob image;
		InputStream stream;
		int i=0;
		 try {
			 	rs=stmt.executeQuery();
	            while (rs.next()) {
	            	Image bufferImage;
	            	image=rs.getBlob(7);
	            	if (image == null)
	            	{
	            		stream = getClass().getResourceAsStream("/png/no-image.png");
	            	}
	            	else {
	            		stream = image.getBinaryStream();
	            	}
	            	itemInListlist = new ItemInList(stream.readAllBytes(),rs.getInt(1),quantityList.get(i),
	            			rs.getInt(3), rs.getString(2),ItemType.valueOf(rs.getString(5)),
	            			CatalogType.valueOf(rs.getString(4)));
	            	cartItems.add(itemInListlist);
	            	i++;          
	            	}
/////////////////////////////////////////////////////////////////////////////////////////////
	            stmt = conn.prepareStatement("select new_item_id from cart_new_item where cart_id in "
            			+ "(select cart_id from carts where user_id=?)");
	            stmt.setString(1, username);
            	rs=stmt.executeQuery();
            	while(rs.next())
            	{
	            NewItem newItem = new NewItem();
	            newItem.setImage(getClass().getResourceAsStream("/png/New.png").readAllBytes());
	            newItem.setItem_id(rs.getInt(1));
	            PreparedStatement stmt2 = null;
	            ResultSet rs2;
	            stmt2 = conn.prepareStatement("select * from items where item_id in "
	            		+ "(select catalog_item_id from new_item_spec where new_item_id =?)");	
	            stmt2.setInt(1, newItem.getItem_id());
	            rs2=stmt2.executeQuery();
	            ///////
	            PreparedStatement stmt3 = null;
	            ResultSet rs3;
            	stmt3 = conn.prepareStatement("select quantity from new_item_spec where new_item_id =? ");
            	stmt3.setInt(1, newItem.getItem_id());
            	rs3=stmt3.executeQuery();
            	int price=0;
	            while (rs2.next()) {
            	image=rs2.getBlob(7);
            	if (image == null)
            	{
            		stream = getClass().getResourceAsStream("/png/no-image.png");
            	}
            	else {
            		stream = image.getBinaryStream();
            	}
            	
            	itemInListlist = new ItemInList(stream.readAllBytes(),rs2.getInt(1),0,
            			rs2.getInt(3), rs2.getString(2),ItemType.valueOf(rs2.getString(5)),
            			CatalogType.valueOf(rs2.getString(4)));
            	
            	rs3.next();
            	price+=rs2.getInt(3)*rs3.getInt(1);
            	itemInListlist.setQuantity(rs3.getInt(1));
            	newItem.addItem(itemInListlist);
		 		}//while end
	            ///////
	            newItem.setPrice(price);
            	stmt2 = conn.prepareStatement("SELECT quantity FROM cart_new_item WHERE cart_id="
            			+ " (SELECT cart_id FROM carts WHERE user_id= ?) and new_item_id =?");
            	stmt2.setString(1, username);
            	stmt2.setInt(2, newItem.getItem_id());
            	rs2=stmt2.executeQuery();
            	rs2.next();
            	newItem.setQuantity(rs2.getInt(1));
            	////
            	stmt3 = conn.prepareStatement("SELECT new_item_name FROM new_items WHERE"
            			+ " new_item_id=?");
            	stmt3.setInt(1, newItem.getItem_id());
            	rs3=stmt3.executeQuery();
            	rs3.next();
            	newItem.setItemName(rs3.getString(1));
            	///
            	cartItems.add(newItem);
            	}
     
	        } catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
		 System.out.println("Get Cart Items!");
		
	}

	public void getCatalogItems(ArrayList<Item> catalogItems,CatalogType catalogType) {
		Server.Log("Database", "Executing GetCatalogItems");
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
	            	image=rs.getBlob(7);
	            	if (image == null)
	            	{
	            		stream = getClass().getResourceAsStream("/png/no-image.png");
	            	}
	            	else {
	            		stream = image.getBinaryStream();
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
           	/////
           	/////
           	for(ItemInList item : order.getItems())
           	{
           		if (item instanceof NewItem) 
           		{
           			NewItem newItem =(NewItem)item;
           			stmt = conn.prepareStatement("INSERT INTO order_new_item VALUES (?,?,?)");
               		stmt.setInt(1, orderID);
               		stmt.setInt(2, newItem.getItem_id());
               		stmt.setInt(3, newItem.getQuantity());
               		stmt.executeUpdate();
               		PreparedStatement stmt2 = null;
                    ResultSet rs2;
                    stmt2 = conn.prepareStatement("select price from items where item_id in "
                    		+ "(select catalog_item_id from new_item_spec where new_item_id =?)");	
                    stmt2.setInt(1, newItem.getItem_id());
                    rs2=stmt2.executeQuery();
                    ///////
                    PreparedStatement stmt3 = null;
                    ResultSet rs3;
                	stmt3 = conn.prepareStatement("select quantity from new_item_spec where new_item_id =? ");
                	stmt3.setInt(1, newItem.getItem_id());
                	rs3=stmt3.executeQuery();
                	int price=0;
                    while (rs2.next()) {
                    rs3.next();
                	price+=rs2.getInt(1)*rs3.getInt(1);
        	 		}
                    stmt3 = conn.prepareStatement("UPDATE new_items Set price  =? where new_item_id =? ");
                    stmt3.setInt(1,price);
                	stmt3.setInt(2, newItem.getItem_id());
                	stmt3.executeUpdate();
				}
           		else
           		{
           			stmt = conn.prepareStatement("INSERT INTO order_item VALUES (?,?,?)");
               		stmt.setInt(1, orderID);
               		stmt.setInt(2, item.getItem_id());
               		stmt.setInt(3, item.getQuantity());
               		stmt.executeUpdate();
				}
               
           	}
           	
           	stmt = conn.prepareStatement("DELETE FROM cart_item WHERE "
           			+ "cart_id = (SELECT cart_id FROM carts Where user_id=?)" );
			stmt.setString(1, requestee);
           	stmt.executeUpdate();
           	
           	stmt = conn.prepareStatement("DELETE FROM cart_new_item WHERE "
           			+ "cart_id = (SELECT cart_id FROM carts Where user_id=?)" );
			stmt.setString(1, requestee);
           	stmt.executeUpdate();
           	

		} 
		catch (SQLException e1) {
    		Server.Log("Database", "Executing InsertOrder: FAILED");
			e1.printStackTrace();
		}
	}
	public String GetManagerBranch(String managerID) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null; 
		ResultSet rs;
		try {
			stmt = conn.prepareStatement
					("SELECT branch_name FROM manager_details WHERE user_id = ?");
			stmt.setString(1,managerID);
           	rs = stmt.executeQuery();
           	if(rs.next() == false)
			{
           		
           		return "no branch";
           	}
           	else
           		return rs.getString(1);
			}
		catch (SQLException e1) {
            System.err.println("Failed on GetManagerBranch()");
			e1.printStackTrace();
			return "";
	
		}
	}

	public void GetOrdersByBranch(ArrayList<Order> orders, String data) {
		// TODO Auto-generated method stub
		
	}

	public void GetOrdersByUsername(ArrayList<Order> orders, String requestee) {
		// TODO Auto-generated method stub
		
	}
	

	public int AddToCart(String username, int item_id, int quantity) {
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
		 return quantity;
		
	}

	public void GetAllCustomerOrders(String username, ArrayList<Order> customerOrders) {
		PreparedStatement stmt = null;
		ResultSet rs1,rs2;
		Order order;
		ItemInList itemInList;
		ArrayList<ItemInList> itemList ;
		try {
			stmt = conn.prepareStatement("SELECT * FROM orders WHERE user_id=?");
			stmt.setString(1,username);
           	rs1 = stmt.executeQuery();
           	while(rs1.next()) {
           		order = new Order();
           		order.setOrderID(String.valueOf(rs1.getInt(2)));
           		order.setPaymentMethod(PaymentMethods.valueOf(rs1.getString(3)));
           		order.setShippingMethod(ShippingMethods.valueOf(rs1.getString(4)));
           		order.setOrderDate(rs1.getTimestamp(5));
           		order.setShippingDate(rs1.getTimestamp(6));
           		order.setBranchName(rs1.getString(7));
           		order.setGreetingCard(rs1.getString(8));
           		order.setTotalPrice(rs1.getInt(9));
           		order.setStatus(OrderStatus.valueOf(rs1.getString(10)));
           		order.setAddress(rs1.getString(11));
           		order.setCity(rs1.getString(12));
         
   	       		stmt = conn.prepareStatement("SELECT i.name ,i.catalog_type,"
                   		+"i.item_type,i.price, oi.quantity " 
                   		+"FROM items i, order_item oi "
                   		+"WHERE i.item_id = oi.item_id" 
                   		+" AND oi.order_id=? " 
                   		+"AND i.item_id IN (SELECT item_id "
                   		+"from order_item"
                   		+" where order_id=?)");
           		stmt.setInt(1,rs1.getInt(2));
           		stmt.setInt(2,rs1.getInt(2));
           		rs2 = stmt.executeQuery();
           		itemList = new ArrayList<>();
           		while(rs2.next()) {
           			itemInList = new ItemInList();
           			itemInList.setItemName(rs2.getString(1));
           			itemInList.setCatalogType(CatalogType.valueOf((rs2.getString(2))));
           			itemInList.setItemType(ItemType.valueOf(rs2.getString(3)));
           			itemInList.setPrice(rs2.getInt(4));
           			itemInList.setQuantity(rs2.getInt(5));
           			itemList.add(itemInList);
           		}
           		//////
           		
           		stmt = conn.prepareStatement("SELECT new_item_id from order_new_item where order_id=?");
           		stmt.setInt(1,rs1.getInt(2));
           		rs2 = stmt.executeQuery();
           		ResultSet rs3;
           		while(rs2.next()) {
           			NewItem newItem=new NewItem() ;
           			newItem.setItem_id(rs2.getInt(1));
           			stmt = conn.prepareStatement("SELECT ni.new_item_name,oni.quantity,ni.price from order_new_item oni,new_items ni "
           					+ "where ni.new_item_id=oni.new_item_id and ni.new_item_id=?");
               		stmt.setInt(1,rs2.getInt(1));
               		rs3 = stmt.executeQuery();
               		rs3.next();
               		newItem.setItemName(rs3.getString(1));
               		newItem.setQuantity(rs3.getInt(2));
               		newItem.setPrice(rs3.getInt(3));
               		newItem.setCatalogType(CatalogType.new_item);
               		itemList.add(newItem);
           		}
           		/////
           		order.setItems(itemList);
           		customerOrders.add(order);
           	}

		
		} 
		catch (SQLException e1) {
			System.err.println("Failed on GetAllCustomerOrders()");
			e1.printStackTrace();
		}
		 System.out.println("Got All order to user id= "+username);
	
	}

	public void getCartItems(ArrayList<Item> cartItems) {
		// TODO Auto-generated method stub
		
	}

	public void GetItemsOfOrder(ArrayList<Item> items, Integer valueOf) {
		// TODO Auto-generated method stub
		
	}

	public void ConfirmOrder(String valueOf) {
		// TODO Auto-generated method stub
		
	}



	public void DeleteItemFromCart(String usernmae, int data) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("DELETE FROM cart_item WHERE item_id=? and cart_id =(select cart_id from carts WHERE user_id=? )" );
			stmt.setInt(1, data);
			stmt.setString(2, usernmae);
           	stmt.executeUpdate(); 	
           	System.out.println("Delete item");
		} 
		catch (SQLException e1) {
            System.err.println("Failed on DeleteItemFromCart()");
			e1.printStackTrace();
		}
		System.out.println("Delete Item From Cart!");
	}

	public void getNotification(String username, ArrayList<NotificationInTable> notificationList) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM notifications WHERE user_id= ?");
			stmt.setString(1, username);
		} 
		catch (SQLException e1) {
            System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		ResultSet rs;
		 try {
			 	rs=stmt.executeQuery();
			 	while (rs.next()) {
			 		notificationList.add(new NotificationInTable( rs.getInt(1),rs.getString(3),rs.getString(4),rs.getString(5)));
			 	}
		 }
		 catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
		 System.out.println("Get Notification !");
		}

	public void UpdateNotification(String username, ArrayList<NotificationInTable> data) {
		PreparedStatement stmt = null;
		try {
			for (NotificationInTable notificationInTable : data) {
				stmt = conn.prepareStatement("UPDATE notifications SET status = ? WHERE notification_id = ? " );
				stmt.setString(1,notificationInTable.getStatus());
				stmt.setInt(2,notificationInTable.getNotificationnumber());
	           	stmt.executeUpdate(); 
			}
				
		} 
		catch (SQLException e1) {
            System.err.println("Failed on UpdateNotification()");
			e1.printStackTrace();
		}
		
	}

	public void UpdateNewItemInCart(String username,NewItem newItem) {
		int newItemId;
		PreparedStatement stmt;
		ResultSet rs;
		//1
		
		
		 try {
			 
			 	stmt = conn.prepareStatement("INSERT INTO new_items (new_item_name) VALUES (?)");
			 	stmt.setString(1, newItem.getItemName());
			 	stmt.executeUpdate();
			 	///
			 	stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");
			 	rs=stmt.executeQuery();
			 	rs.next();		
			 	newItemId=rs.getInt(1);
			 	///
			 	
			 	///
			 	for (ItemInList item : newItem.getAssemble()) {
			 		stmt = conn.prepareStatement("INSERT INTO new_item_spec values(? , ? ,?) ");
			 		stmt.setInt(1, newItemId);
			 		stmt.setInt(2,item.getItem_id());
			 		stmt.setInt(3,item.getQuantity());
			 		stmt.executeUpdate();
				}
			 	///
			 	stmt = conn.prepareStatement("INSERT INTO cart_new_item values((select cart_id from carts where user_id=?), ?, ?)");
			 	stmt.setString(1, username);
			 	stmt.setInt(2, newItemId);
			 	stmt.setInt(3, newItem.getQuantity());
		 		stmt.executeUpdate();

		 
		 }
		 catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }		
	}

	public void DeleteItemFromNewItemList(int new_item,int catalog_item) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("DELETE FROM new_item_spec WHERE new_item_id = ? AND catalog_item_id =? " );
			stmt.setInt(1, new_item);
			stmt.setInt(2, catalog_item);
           	stmt.executeUpdate(); 	
           
		} 
		catch (SQLException e1) {
            System.err.println("Failed on DeleteItemFromCart()");
			e1.printStackTrace();
		}
		System.out.println("Delete item from newItemList !");	
	}

	public void DeleteNewItemFromCart(String requestee, int data) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("DELETE FROM cart_new_item WHERE new_item_id=? AND cart_id = (SELECT cart_id FROM carts Where user_id=?)" );
			stmt.setInt(1, data);
			stmt.setString(2, requestee);
           	stmt.executeUpdate(); 
           	/////
           	stmt = conn.prepareStatement("DELETE FROM new_items WHERE new_item_id = ?" );
			stmt.setInt(1, data);
           	stmt.executeUpdate(); 
		} 
		catch (SQLException e1) {
            System.err.println("Failed on DeleteItemFromCart()");
			e1.printStackTrace();
		}
		System.out.println("Delete item from newItemList !");	
		
	}

	public void DeleteAllnewItemFromCart(String requestee, int data) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("DELETE FROM cart_new_item WHERE cart_id = (SELECT cart_id FROM carts Where user_id=?)" );
			stmt.setString(1, requestee);
           	stmt.executeUpdate();
           	////
           	stmt = conn.prepareStatement("DELETE FROM new_item_spec WHERE new_item_id = ?" );
			stmt.setInt(1, data);
           	stmt.executeUpdate(); 	
           	/////
           	stmt = conn.prepareStatement("DELETE FROM new_items WHERE new_item_id = ?" );
			stmt.setInt(1, data);
           	stmt.executeUpdate(); 
		} 
		catch (SQLException e1) {
            System.err.println("Failed on DeleteAllnewItemFromCart()");
			e1.printStackTrace();
		}
		System.out.println("Delete All new item from cart !");	
		
		
		
	}

	public void cancelOrder(Integer refundZerli, Integer orderID) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE orders SET status = ?,"
					+ "refund_zerli = ? WHERE order_id = ? " );
			stmt.setString(1,OrderStatus.pending_cancel.toString());
			stmt.setInt(2,refundZerli);
			stmt.setInt(3,orderID);
           	stmt.executeUpdate(); 
			}
		catch (SQLException e1) {
            System.err.println("Failed on cancelOrder()");
			e1.printStackTrace();
		}
	}
}
