package client;

import java.io.IOException;

import Entities.Access;
import ProtocolHandler.Protocol;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientApp extends Application { 
	final public static int DEFAULT_PORT = 5555;
	public static Protocol ProtocolHandler  = new Protocol();
	public static String UserID = "NO-AUTH";
	public static Access UserStatus = Access.noaut;
	public static ClientConn ClientConnection;
	
	@Override
	public void start(Stage stage) throws IOException {	
		Parent root = null;
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/mainframes/StartIPconfig.fxml"));
			root =  loader.load(); 
			Utilities.GenericUtilties.SetWindowMovable(root, stage);
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene); 	
			stage.show();
			LoginController.windowControl.stage = stage;
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
