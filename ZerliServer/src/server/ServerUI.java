package server;

import javafx.application.Application;
import javafx.stage.Stage;

public class ServerUI extends Application{ 
	
	public static Server server;
	final public static int DEFAULT_PORT = 5555;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		ServerPanelController serverPanelController = new ServerPanelController(); // Create controller
		serverPanelController.start(stage,server);
	}
	
	public static void runServer(String p)
	{
		server = new Server(DEFAULT_PORT);
        try 
        {
        	server.listen();
        } 
        catch (Exception ex) 
        {
          System.out.println("Failed listen clients!");
        }
	}

}