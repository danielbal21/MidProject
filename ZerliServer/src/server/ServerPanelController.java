package server;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ServerPanelController {
	private Server server;
	
	@FXML
	private Button exitBtn;
	
	@FXML
    private TableView<?> clientTable;

    @FXML
    private TableColumn<?, ?> hostCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> statusCol;
    
    @FXML
    void exitPressed(ActionEvent event) {
    	try {
			server.close();
		} catch (IOException e) {
			System.out.println("Falied close Server");
			e.printStackTrace();
		}
    }

	public void start(Stage stage, Server server) {
		this.server = server;
		Parent root = null;
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/ServerPanel.fxml"));
			root =  loader.load(); 
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

