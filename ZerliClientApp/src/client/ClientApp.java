package client;

import java.io.IOException;
import ProtocolHandler.Protocol;
import controllers.LoginController;
import controllers.ManagerOrderManagerController;
import controllers.StartIPconfigController;
import controllers.WindowControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientApp extends Application { 
	final public static int DEFAULT_PORT = 5555;
	public static Protocol ProtocolHandler = new Protocol();
	public static String UserID = "NO-AUTH";
	//final public static String DEFAULT_HOST = "localhost";
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
