package gui;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import Entities.Order;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		OrdersMenuController c;		
		Parent root = null;
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/orderMenu.fxml"));
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
