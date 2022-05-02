package controllers;

import client.ClientConn;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartIPconfigController {
	private Stage stage;

    @FXML
    private Label errrorLabel;
    
    @FXML
    private Button connectBtn;

    @FXML
    private Label enteripTextField;

    @FXML
    private TextField ipTextField;

    @FXML
    void connectPressed(ActionEvent event) {
    	errrorLabel.setVisible(false);
    	String ip = ipTextField.getText();
    	if(ip.equals("")) {
    		ip = "localhost";
    	}
    	
    	ClientApp.ClientConnection = new ClientConn(ip, ClientApp.DEFAULT_PORT);
    	if(!ClientApp.ClientConnection.isConnected()) {
    		errrorLabel.setVisible(true);
    	}
    	else {
    		stage.close();
    		Stage newStage = new Stage();
    		LoginController c;
    		Parent root = null;
    		try 
    		{
    			FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(getClass().getResource("/gui/mainframes/Login.fxml"));
    			root =  loader.load(); 
    			c = loader.getController();
    			Utilities.GenericUtilties.SetWindowMovable(root, newStage);
    			Scene scene = new Scene(root);
    			newStage.initStyle(StageStyle.UNDECORATED);
    			newStage.setScene(scene); 
    			c.setStage(newStage);
    			newStage.show();
    			
    		}
    		catch (Exception e) {e.printStackTrace();	}
    	}
    	
    }

    @FXML
    void exitPressed(MouseEvent event) {
    	System.exit(0);
    }

	public void setStage(Stage stage) {
		this.stage = stage;
		 ipTextField.setFocusTraversable(false);
	}
    
}
