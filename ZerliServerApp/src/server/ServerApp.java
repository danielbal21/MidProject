package server;

import java.io.IOException;
import controllers.ServerPanelController;
import controllers.StartSQLPassswordConfigController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServerApp extends Application{ 
	
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
		StartSQLPassswordConfigController SQLpasswordController;
		Parent root = null;
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/StartSQLPassswordConfig.fxml"));
			root =  loader.load(); 
			Utilities.GenericUtilties.SetWindowMovable(root, stage);
			SQLpasswordController = loader.getController();
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene); 
			SQLpasswordController.setStage(stage);
			stage.show();
		} 
		catch (IOException e) 
		{
			System.out.println("Failed load root");
			e.printStackTrace();
		}
	}
	

}