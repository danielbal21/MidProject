package server;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerUI extends Application{ 
	
	final public static int DEFAULT_PORT = 5555;
	public static Server server = new Server(DEFAULT_PORT) ;

	
	public static void main(String[] args) {
        try 
        {
        	server.listen();
        } 
        catch (Exception ex) 
        {
          System.out.println("Failed listen clients!");
        }
        
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		ServerPanelController serverPanelController;
		
		Parent root = null;
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/ServerPanel.fxml"));
			root =  loader.load(); 
			serverPanelController = loader.getController();
			Scene scene = new Scene(root);
			stage.setTitle("Client Status");
			stage.setScene(scene); 
			stage.show();
		} 
		catch (IOException e) 
		{
			System.out.println("Failed load root");
			e.printStackTrace();
		}
	}
	

}