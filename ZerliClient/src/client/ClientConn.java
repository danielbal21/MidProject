package client;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Order;
import ocsf.client.*;

public class ClientConn extends AbstractClient{

	public ClientConn(String host, int port) {
		super(host, port);
		try {
			openConnection();
		} catch (IOException e) {e.printStackTrace();}
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		ClientUI.ProtocolHandler.Handle(msg);
		
		
	}

}
