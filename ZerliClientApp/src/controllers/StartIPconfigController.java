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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartIPconfigController {

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
    	startConnaction();
    	
    }


    @FXML
    void Enter(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    		startConnaction();
    }
    @FXML
    void exitPressed(MouseEvent event) {
    	System.exit(0);
    }
    private void startConnaction() 
    {
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
    		LoginController.windowControl.stage.close();
    		Stage newstage = new Stage();
    		Parent root = null;
    		try 
    		{
    			FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(getClass().getResource("/gui/mainframes/Login.fxml"));
    			root =  loader.load(); 
    			Utilities.GenericUtilties.SetWindowMovable(root, newstage);
    			Scene scene = new Scene(root);
    			newstage.initStyle(StageStyle.UNDECORATED);
    			newstage.setScene(scene); 
    			newstage.show();
    			LoginController.windowControl.stage = newstage;
    			
    		}
    		catch (Exception e) {e.printStackTrace();	}
    	}
    }
}
