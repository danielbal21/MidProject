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
	
	public static ServerConnSQL SqlServerManager = new ServerConnSQL();
	
	public Server(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		ArrayList<Order> orders = new ArrayList<>();
		Order result = null;
		
	    System.out.println("Message received: " + msg + " from " + client);
	    
	   	if(((String)msg).equals("Order1")) {// protocol handler
	   		SqlServerManager.getOrders(orders);
			System.out.println(((ConnectionToClient)super.getClientConnections()[0]).toString());
	   	}

	   	
	    try {
			client.sendToClient(result);
		} catch (IOException e) {e.printStackTrace();}
	    
	}

}
