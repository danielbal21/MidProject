package server;

import ProtocolHandler.Protocol;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer{
	
	final public static int DEFAULT_PORT = 5555;
	
	public static ServerConnSQL SqlServerManager = new ServerConnSQL();
	private static Protocol ProtocolHandler = new Protocol();
	
	public Server(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		
		try {
			ProtocolHandler.Handle(msg, client);
		} catch (Exception e1) {e1.printStackTrace();}  
	}

}
