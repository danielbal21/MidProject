package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client{
	final public static int DEFAULT_PORT = 5555;
	final //public static String DEFAULT_HOST = "localhost";

	
	public static void main(String[] args) throws IOException {
		ClientConn clientConn = new ClientConn("localhost", DEFAULT_PORT);
		clientConn.sendToServer("Order1");
	}
	
	

}
