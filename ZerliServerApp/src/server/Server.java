package server;

import ProtocolHandler.Protocol;
import controllers.ServerPanelController;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Server extends AbstractServer{
	
	final public static int DEFAULT_PORT = 5555;

	public static final String VERSION = "0.0.2";
	
	public static ServerConnSQL SqlServerManager = new ServerConnSQL();
	private static Protocol ProtocolHandler = new Protocol();
	public static TextArea Console;
	
	public Server(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		
		try {
			ProtocolHandler.Handle(msg, client);
		} catch (Exception e1) {e1.printStackTrace();}  
	}
	public static void Log(String source,String content)
	{
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				var formatter = DateTimeFormatter.ofPattern("dd-MM-yy hh:mm");
				Console.appendText(String.format("%s >> [%s]: %s\n",formatter.format(LocalDateTime.now()),source,content));
			}

		});
	}

}
