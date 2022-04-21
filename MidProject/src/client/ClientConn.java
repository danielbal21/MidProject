package client;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Order;
import ocsf.client.*;

public class ClientConn extends AbstractClient{

	public ClientConn(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
	    System.out.println("Message received: " + msg);
	    
	    if(msg != null) {
		    if(msg instanceof ArrayList<?>) {
		    
		    }
	    }
	    
	}

}
