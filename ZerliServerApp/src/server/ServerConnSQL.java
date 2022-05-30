package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import Entities.*;
import javafx.scene.image.Image;

public class ServerConnSQL {

	private static Connection conn;
	// private String mySQLpassword = "123456";

	public static boolean startConn(String mySQLpassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/midproject?useLegacyDatetimeCode=false&serverTimezone=Israel","root",mySQLpassword);
			System.out.println("SQL connection succeed");
			return true;
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
	}



	public void UpdateOrder_ColorAndDate_ByNumber(String orderNumber, String newColor, String newDate) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("UPDATE Orders SET color=?,date=? WHERE orderNumber=?");
			stmt.setString(1, newColor);
			stmt.setString(2, newDate);
			stmt.setString(3, orderNumber);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Updated order " + orderNumber);
	}

	public Object[] Authenticate(String username, String password) {
		Server.Log("Database", "Executing Authenticate");
		PreparedStatement stmt = null;
		Object[] logindetails = new Object[3];
		ResultSet rs;
		try {
			stmt = conn.prepareStatement("SELECT ld.loggedin,ld.access,ud.role FROM login_details ld,user_details ud "
					+ "WHERE ld.user_id=? AND ld.user_id = ud.user_id " + "And ld.password=?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next() == false) {
				logindetails[1] = Access.noaut;
				return logindetails;
			} else
				logindetails[0] = rs.getInt(1);
			logindetails[1] = Access.valueOf(rs.getString(2));
			logindetails[2] = Roles.valueOf(rs.getString(3));
			stmt = conn.prepareStatement("UPDATE login_details SET loggedin=? WHERE user_id=?");
			stmt.setInt(1, 1);
			stmt.setString(2, username);
			stmt.executeUpdate();
			return logindetails;
		} catch (SQLException e1) {
			Server.Log("Database", "Executing Authenticate: FAILED");
			e1.printStackTrace();
			logindetails[1] = Access.noaut;
			return logindetails;
		}
	}

	public void LoggedOut(String username) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE login_details SET loggedin='0' WHERE user_id=?");
			stmt.setString(1, username);
			stmt.executeUpdate();
		} catch (SQLException e1) {
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
			stmt = conn.prepareStatement("SELECT * FROM items WHERE item_id IN  (SELECT item_id FROM Cart_item WHERE cart_id=(SELECT cart_id FROM carts WHERE user_id= ?)) AND isAvailable=1");
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
	            	/*** take sale price or regular price ***/
	            	int price;
	            	if(rs.getInt(8) == 1)
	            		price = rs.getInt(9);
	            	else
	            		price = rs.getInt(3);
	            	itemInListlist = new ItemInList(stream.readAllBytes(),rs.getInt(1),quantityList.get(i),
	            			price, rs.getString(2),ItemType.valueOf(rs.getString(5)),
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
            	/*** get sale price or regular price ***/
            	int actual;
            	if(rs2.getInt(8) == 1)
            		actual = rs2.getInt(9);
            	else
            		actual = rs2.getInt(3);
            	price+=actual*rs3.getInt(1);
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
	public void removeItemByID(int id)
	{
		Server.Log("Database", "Executing removeItemByID");
		PreparedStatement stmt;
		try 
		{
			stmt = conn.prepareStatement("UPDATE Items SET isAvailable=0 WHERE item_id=?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
    		Server.Log("Database", "Executing RemoveItemByID: FAILED");
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
		}	
		 System.out.println("Remove Item By ID!");
	}
	public Item getItemByID(int id) {
		Server.Log("Database", "Executing getItemByID");
		Item item=null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM items where item_id=?");
			stmt.setInt(1, id);
			} 
		catch (SQLException e1) {
            System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		Blob image;
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
	            			Color.valueOf(rs.getString(6)==null?"non_color":rs.getString(6)),stream.readAllBytes(),rs.getInt(8) == 1? true : false,rs.getInt(9));
	            	return item;
	            }
	        } catch (Exception e) {
	    		Server.Log("Database", "Executing GetItemByID: FAILED");
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	            System.err.println(e.getStackTrace());
	        }
		 System.out.println("Get Items By ID!");
		 return item;
	}

	public void getCatalogItems(ArrayList<Item> catalogItems, CatalogType catalogType) {
		Server.Log("Database", "Executing GetCatalogItems");
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM items where catalog_type=? AND isAvailable=1");
			stmt.setString(1, catalogType.toString());
		} catch (SQLException e1) {
			System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		Blob image;
		Item item = null;
		ResultSet rs;
		InputStream stream;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {

				// need to change blob to long blob
				Image bufferImage;
				image = rs.getBlob(7);
				if (image == null) {
					stream = getClass().getResourceAsStream("/png/no-image.png");
					// stream =new FileInputStream("/png/no-image.png");
				} else {
					stream = image.getBinaryStream();
					// bufferImage = new Image(input);
				}

				item = new Item(rs.getInt(1), rs.getString(2), rs.getInt(3), CatalogType.valueOf(rs.getString(4)),
						ItemType.valueOf(rs.getString(5)),
						Color.valueOf(rs.getString(6) == null ? "non_color" : rs.getString(6)), stream.readAllBytes(),rs.getInt(8) > 0 ? true : false , rs.getInt(9));
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
			while (rs.next())
				branches.add(rs.getString(1));
		} catch (SQLException e1) {
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
			stmt = conn.prepareStatement(
					"SELECT credit_card,cvv,exp_month,exp_year,zerli_coin,new_customer FROM customer_details WHERE user_id='"
							+ requestee + "'");
			rs = stmt.executeQuery();
			while (rs.next())
				details = new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6) };
		} catch (SQLException e1) {
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
		} catch (SQLException e1) {
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
                    stmt2 = conn.prepareStatement("select price,is_sale,salePrice from items where item_id in "
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
                	/*** get sale price or regular price ***/
                	int actual;
                	if(rs2.getInt(2) == 1)
                		actual = rs2.getInt(3);
                	else
                		actual = rs2.getInt(1);
                	price+=actual*rs3.getInt(1);
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

	public String GetBranch(String user_id, String type) {
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			if(type.equals("Manager")) {
				stmt = conn.prepareStatement("SELECT branch_name FROM manager_details WHERE user_id = ?");
			}
			if(type.equals("Delivery")) {
				stmt = conn.prepareStatement("SELECT branch_name FROM delivery_details WHERE user_id = ?");
			}
			stmt.setString(1, user_id);
			rs = stmt.executeQuery();
			if (rs.next() == false) {

				return "no branch";
			} else
				return rs.getString(1);
		} catch (SQLException e1) {
			System.err.println("Failed on GetManagerBranch()");
			e1.printStackTrace();
			return "";

		}
	}

	public void GetOrdersByBranch(ArrayList<Order> orders, String branch_name, String status) {
		PreparedStatement stmt = null;
		ResultSet rs;
		Order newOrder;
		try {
			stmt = conn.prepareStatement("SELECT * FROM orders WHERE branch_name = ? AND status = ?");
			stmt.setString(1, branch_name);
			if(status.equals("pending"))
			{
				stmt.setString(2, "pending_confirm");
			}
			if(status.equals("confirmed"))
			{
				stmt.setString(2, "confirmed");
			}
		
			rs = stmt.executeQuery();
			while (rs.next()) {
				newOrder = new Order();
				newOrder.setUserID(rs.getString(1));
				newOrder.setOrderID(rs.getString(2));
				newOrder.setPaymentMethod(PaymentMethods.valueOf(rs.getString(3)));
				newOrder.setShippingMethod(ShippingMethods.valueOf(rs.getString(4)));
				newOrder.setShippingDate(rs.getTimestamp(5));
				newOrder.setOrderDate(rs.getTimestamp(6));
				newOrder.setTotalPrice(rs.getInt(9));
				newOrder.setStatus(OrderStatus.valueOf(rs.getString(10)));
				orders.add(newOrder);
			}
		} catch (SQLException e1) {
			Server.Log("Database", "Executing GetOrdersByBranch: FAILED");
			e1.printStackTrace();
		}

	}

	public void GetItemsOfOrder(ArrayList<ItemInList> itemsOfOrder, int order_id) {
		PreparedStatement stmt = null;
		ResultSet rs;
		ResultSet rs2;
		ItemInList newItemInList;
		try {
			stmt = conn.prepareStatement(
					"SELECT * FROM items WHERE item_id IN (SELECT item_id FROM order_item WHERE order_id = ?)");
			stmt.setInt(1, order_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				newItemInList = new ItemInList();
				stmt = conn.prepareStatement(
						"SELECT quantity FROM order_item WHERE item_id = ? AND order_id =? ");
				stmt.setInt(1, rs.getInt(1));
				stmt.setInt(2, order_id);
				rs2 = stmt.executeQuery();
				rs2.next();
				newItemInList.setQuantity(rs2.getInt(1));
				newItemInList.setItem_id(rs.getInt(1));
				newItemInList.setItemName(rs.getString(2));
				newItemInList.setPrice(rs.getInt(3));
				newItemInList.setCatalogType(CatalogType.valueOf(rs.getString(4)));
				newItemInList.setItemType(ItemType.valueOf(rs.getString(5)));
				itemsOfOrder.add(newItemInList);
			}
		} catch (SQLException e1) {
			Server.Log("Database", "Executing GetItemsOfOrder: FAILED");
			e1.printStackTrace();
		}

	}

	public void ConfirmOrder(int order_id) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE orders SET status = 'confirmed' WHERE order_id = ?");
			stmt.setInt(1, order_id);
			stmt.executeUpdate();
		} catch (SQLException e1) {
			Server.Log("Database", "Executing ConfirmOrder: FAILED");
			e1.printStackTrace();
		}

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

	public void DeleteItemFromCart(String usernmae, int data) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(
					"DELETE FROM cart_item WHERE item_id=? and cart_id =(select cart_id from carts WHERE user_id=? )");
			stmt.setInt(1, data);
			stmt.setString(2, usernmae);
			stmt.executeUpdate();
			System.out.println("Delete item");
		} catch (SQLException e1) {
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
		} catch (SQLException e1) {
			System.err.println("Failed on createStatement()");
			e1.printStackTrace();
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				notificationList
						.add(new NotificationInTable(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		System.out.println("Get Notification !");
	}

	public void UpdateNotification(String username, ArrayList<NotificationInTable> data) {
		PreparedStatement stmt = null;
		try {
			for (NotificationInTable notificationInTable : data) {			
				stmt = conn.prepareStatement("UPDATE notifications SET status = ? WHERE notification_id = ? ");
				stmt.setString(1, notificationInTable.getStatus());
				stmt.setInt(2, notificationInTable.getNotificationnumber());
				stmt.executeUpdate();
			}

		} catch (SQLException e1) {
			System.err.println("Failed on UpdateNotification()");
			e1.printStackTrace();
		}
	}

	public void EndOrder(int order_id, String action) {
		PreparedStatement OrderDetailsStmt = null;
		PreparedStatement SetStatusStmt = null;
		ResultSet rs;
		try {
			OrderDetailsStmt = conn.prepareStatement("SELECT user_id, shipping_date,total_price FROM orders WHERE order_id =?");
			SetStatusStmt = conn.prepareStatement("UPDATE orders SET status = ? WHERE order_id = ?");
			OrderDetailsStmt.setInt(1, order_id);
			SetStatusStmt.setInt(2, order_id);
			if(action.equals("cancel"))
				SetStatusStmt.setString(1, "canceled");
			if(action.equals("done"))
				SetStatusStmt.setString(1, "completed");
			rs = OrderDetailsStmt.executeQuery();
			if(rs.next())
			{
				String user_id = rs.getString(1);
				String currency[]=GetCurrency(user_id);
				int zCoin = Integer.valueOf(currency[4]);
				int orderCost = rs.getInt(3);
				java.sql.Timestamp  ts=  rs.getTimestamp(2);
				LocalDateTime requested = Utilities.GenericUtilties.Convert_LocalDate_To_SQLDate(ts);
				Duration difference = Duration.between(requested, LocalDateTime.now()); //requested - now
				if(difference.getSeconds() > 0 && action.equals("done"))
				{
					//Full refund
					zCoin +=  orderCost;
				}
				else if(difference.getSeconds() < 0 && action.equals("done"))
				{
					//full price
				}
				else if (difference.getSeconds() > -3600 && action.equals("cancel"))
				{
					//no refund
				}
				else if (difference.getSeconds() > -10800 && action.equals("cancel"))
				{
					//half refund
					zCoin +=(orderCost/2);
				}
				else
				{
					//Full refund
					zCoin +=  orderCost;
				}
				UpdateZerliCoins(user_id, zCoin);
				SetStatusStmt.executeUpdate();
				
			}
			
			
			
		} catch (SQLException e1) {
			System.err.println("Failed on EndOrder()");
			e1.printStackTrace();
		}
		
		System.out.println("order end!");

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

	public void resetNewCustomer(String user_id) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement
					("UPDATE customer_details "
					+ "SET new_customer = 0 "
					+ "WHERE user_id = ?");
			stmt.setString(1,user_id);
           	stmt.executeUpdate(); 
			}
		catch (SQLException e1) {
            System.err.println("Failed on resetNewCustomer()");
			e1.printStackTrace();
		}
		System.out.println("RESET new customer for "+user_id);
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
	

	public void UpdateItem(Item data) {
		Server.Log("Database", "Executing UpdateItem");
		PreparedStatement stmt;
		try 
		{
			stmt = conn.prepareStatement("UPDATE Items SET name=?,price=?, item_type=?,catalog_type=?,color=?,is_sale=?,salePrice=? WHERE item_id=?");
			stmt.setString(1, data.getName());
			stmt.setInt(2, data.getPrice());
			stmt.setString(3, data.getItemType().toString());
			stmt.setString(4, data.getCatalogType().toString());
			stmt.setString(5, data.getColor().toString());
			stmt.setInt(6, data.isOnSale() ? 1 : 0);
			stmt.setInt(7, data.getSalePrice());
			stmt.setInt(8, data.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace(); 		Server.Log("Database", "Executing UpdateItem: FAILED");}		

	}
	
	public void InsertItem(Item data)
	{
		Server.Log("Database", "Executing InsertItem");
		PreparedStatement stmt;
		try 
		{
			stmt = conn.prepareStatement("INSERT INTO Items (name,price,catalog_type,item_type,color,image,is_sale,salePrice) VALUES (?,?,?,?,?,?,?,?)");
			stmt.setString(1, data.getName());
			stmt.setInt(2, data.getPrice());
			stmt.setString(3, data.getCatalogType().toString());
			stmt.setString(4, data.getItemType().toString());
			stmt.setString(5, data.getColor().toString());
			if(data.getImage() != null)
				stmt.setBlob(6, new ByteArrayInputStream(data.getImage()));
			else
				stmt.setNull(6,Types.BLOB);
			stmt.setInt(7, data.isOnSale() ? 1 : 0);
			stmt.setInt(8, data.getSalePrice());
			stmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}		
		Server.Log("Database", "Executing InsertItem: FAILED");
	}



	public void SaveSurvey(Survey survey) {
		Server.Log("Database", "Executing SaveSurvey");
		PreparedStatement stmt;
		ResultSet rs;
		try 
		{
			stmt = conn.prepareStatement("insert into midproject.surveys (survey_content,q1,q2,q3,q4,q5,q6) values(?,?,?,?,?,?,?)");
			stmt.setString(1, survey.getContent());
			stmt.setString(2, survey.getQuestions()[0]);
			stmt.setString(3, survey.getQuestions()[1]);
			stmt.setString(4, survey.getQuestions()[2]);
			stmt.setString(5, survey.getQuestions()[3]);
			stmt.setString(6, survey.getQuestions()[4]);
			stmt.setString(7, survey.getQuestions()[5]);
			stmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}		
		Server.Log("Database", "Executing SaveSurvey: FAILED");
	}



	public void GetSurveysNames(ArrayList<String> list) {
		Server.Log("Database", "Executing GetSurveysNames");
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement("SELECT survey_id,survey_content FROM midproject.surveys");
           	rs = stmt.executeQuery();
           	while(rs.next()) {
           		String string=rs.getString(1)+" "+rs.getString(2);
           		list.add(string);
           	}
		}catch (SQLException e) {e.printStackTrace();}		
		Server.Log("Database", "Executing GetSurveysNames: FAILED");
		
	}



	public void GetSurvey(Survey survey) {
		Server.Log("Database", "Executing GetSurvey");
		PreparedStatement stmt;
		ResultSet rs;
		try 
		{
			stmt = conn.prepareStatement("SELECT q1,q2,q3,q4,q5,q6,survey_content FROM midproject.surveys where survey_id =?");
			stmt.setInt(1,survey.getId());
			rs = stmt.executeQuery();
			rs.next();
			survey.getQuestions()[0]=(rs.getString(1));
			survey.getQuestions()[1]=(rs.getString(2));
			survey.getQuestions()[2]=(rs.getString(3));
			survey.getQuestions()[3]=(rs.getString(4));
			survey.getQuestions()[4]=(rs.getString(5));
			survey.getQuestions()[5]=(rs.getString(6));
			survey.setContent((rs.getString(7)));
			
		} catch (SQLException e) {e.printStackTrace();}		
		Server.Log("Database", "Executing GetSurvey: FAILED");
	}



	public void SaveSurveyAnswers(Survey survey) {
		Server.Log("Database", "Executing SaveSurveyAnswers");
		PreparedStatement stmt;
		ResultSet rs;
		try 
		{	stmt = conn.prepareStatement("SELECT a1,a2,a3,a4,a5,a6 FROM midproject.surveys where survey_id =?");
			stmt.setInt(1,survey.getId());
			rs = stmt.executeQuery();
			rs.next();
			survey.getAnswers()[0]+=rs.getInt(1);
			survey.getAnswers()[1]+=rs.getInt(2);
			survey.getAnswers()[2]+=rs.getInt(3);
			survey.getAnswers()[3]+=rs.getInt(4);
			survey.getAnswers()[4]+=rs.getInt(5);
			survey.getAnswers()[5]+=rs.getInt(6);
			
			////
			stmt = conn.prepareStatement("UPDATE midproject.surveys SET a1=?,a2=?, a3=?,a4=?,a5=?,a6=? WHERE survey_id=?");
			stmt.setInt(1, survey.getAnswers()[0]);
			stmt.setInt(2, survey.getAnswers()[1]);
			stmt.setInt(3, survey.getAnswers()[2]);
			stmt.setInt(4, survey.getAnswers()[3]);
			stmt.setInt(5, survey.getAnswers()[4]);
			stmt.setInt(6, survey.getAnswers()[5]);
			stmt.setInt(7, survey.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}		
		Server.Log("Database", "Executing SaveSurveyAnswers: FAILED");
		
	}
}
