package client;

import java.io.IOException;

import ProtocolHandler.Protocol;
import ProtocolHandler.RequestType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client{
	final public static int DEFAULT_PORT = 5555;
	public static ClientConn ClientConnection;
	public static Protocol ProtocolHandler = new Protocol();
	//final public static String DEFAULT_HOST = "localhost";

	public static void main(String[] args) throws IOException {
		ClientConnection = new ClientConn("localhost", DEFAULT_PORT);
		
		//System.out.println();
	}
	
	

}
