package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Entities.Order;
import javafx.application.Application;
import javafx.stage.Stage;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer{
	
	final public static int DEFAULT_PORT = 5555;
	
	ServerConnSQL serverConnSql = new ServerConnSQL();
	
	public Server(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		ArrayList<Order> orders = new ArrayList<>();
		Order result = null;
		
	    System.out.println("Message received: " + msg + " from " + client);
	   	if(((String)msg).equals("Order1"))
			try {
				serverConnSql.getOrderNumber1(orders);
				for(Order s:orders) {
					System.out.println(s);
				}
				
			} catch (SQLException e1) {e1.printStackTrace();}
	   
	    try {
			client.sendToClient(result);
		} catch (IOException e) {e.printStackTrace();}
	    
	}
	
	public static void main(String[] args) throws IOException {
		Server ser = new Server(DEFAULT_PORT);
		
		 try 
        {
		 ser.listen(); //Start listening for connections
        } 
        catch (Exception ex) 
        {
          System.out.println("ERROR - Could not listen for clients!");
        }
		
	}
	
	
	

}
