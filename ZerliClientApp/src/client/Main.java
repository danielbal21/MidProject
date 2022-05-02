package client;

import java.io.IOException;
import ProtocolHandler.Protocol;
import controllers.OrdersMenuController;
import controllers.StartIPconfigController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application { 
	final public static int DEFAULT_PORT = 5555;
	public static Protocol ProtocolHandler = new Protocol();
	//final public static String DEFAULT_HOST = "localhost";
	public static ClientConn ClientConnection;
	
	@Override
	public void start(Stage stage) throws IOException {
		StartIPconfigController c;		
		Parent root = null;
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/StartIPconfig.fxml"));
			root =  loader.load(); 
			c = loader.getController();
			Utilities.GenericUtilties.SetWindowMovable(root, stage);
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene); 	
			c.setStage(stage);
			stage.show();
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
