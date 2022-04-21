package server;

import java.io.IOException;
import controllers.ServerPanelController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ServerUI extends Application{ 
	
	final public static int DEFAULT_PORT = 5555;
	public static Server server = new Server(DEFAULT_PORT) ;
//	private double xOffset = 0; 
//	private double yOffset = 0;
	
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
			loader.setLocation(getClass().getResource("../gui/ServerPanel.fxml"));
			root =  loader.load(); 
			Utilities.GenericUtilties.SetWindowMovable(root, stage);
			serverPanelController = loader.getController();
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.UNDECORATED);
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