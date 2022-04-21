package client;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import Entities.Order;
import ProtocolHandler.Protocol;
import controllers.OrdersMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientUI extends Application { 
	
	final public static int DEFAULT_PORT = 5555;
	public static Protocol ProtocolHandler = new Protocol();
	//final public static String DEFAULT_HOST = "localhost";
	public static ClientConn ClientConnection = new ClientConn("localhost", DEFAULT_PORT);
	
	@Override
	public void start(Stage stage) throws IOException {
		OrdersMenuController c;		
		Parent root = null;
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../gui/orderMenu.fxml"));
			root =  loader.load(); 
			c = loader.getController();
			Scene scene = new Scene(root);
			stage.setTitle("Orders");
			stage.setScene(scene); 		
			stage.show();
			c.addOrdersToTable();
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
