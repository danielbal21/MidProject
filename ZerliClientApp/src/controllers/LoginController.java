package controllers;

import java.io.IOException;

import Entities.Roles;
import ProtocolHandler.RequestType;
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

public class LoginController {
	public static WindowControl windowControl;
	
    @FXML
    private Button loginBtn;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField userNameText;

    @FXML
    void exitPressed(MouseEvent event) {
    	try {
			ClientApp.ClientConnection.closeConnection();
			System.out.println(ClientApp.ClientConnection.isConnected());
			System.exit(0);
		} catch (IOException e) {e.printStackTrace();}
    }
    
    private boolean validateInput()
    {
    	if(!passwordText.getText().equals("")  && !userNameText.getText().equals(""))
    	{
    		loginLabel.setVisible(false);
    		return true;
    	}
    	else
    	{
    		loginLabel.setVisible(true);
    		loginLabel.setText("Username and password field cannot be empty");
    		return false;
    	}
    }
    @FXML
    void loginPressed(ActionEvent event) {
    	if(!validateInput()) return;
		FXMLLoader loader = new FXMLLoader();
		Parent root = null;
    	ClientApp.ProtocolHandler.Invoke(RequestType.AuthenticateUser, null, new String[] {userNameText.getText(),passwordText.getText()}, true);
    	Roles role = (Roles)ClientApp.ProtocolHandler.GetResponse(RequestType.AuthenticateUser);
    	if(role == Roles.noaut || role == null)
    	{
    		loginLabel.setVisible(true);
    		loginLabel.setText("The Credentials entered are wrong");
    		
    		return;
    	}
    	else
    	{
    		ClientApp.UserID = userNameText.getText();
    		ClientApp.ProtocolHandler.Invoke(RequestType.IsLoggedIn,null,null,true);
        	String logIn=(String)ClientApp.ProtocolHandler.GetResponse(RequestType.IsLoggedIn);
			if(logIn.equals("1"))
    		{
    			loginLabel.setVisible(true);
        		loginLabel.setText("You are already logged In");
        		return;
    		}
    		else 
    		{
    			Stage newStage = new Stage();
    			
    			if(role == Roles.customer)
	    		{
    				WindowControl.stage.close();
					loader.setLocation(getClass().getResource("/gui/mainframes/CustomerMainScreen.fxml"));
			
					try {
						root =  loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					} 
					windowControl = new WindowControl(loader.getController());
					windowControl.frameController.loadAll();
					
	    		}
    			if(role == Roles.manager)
    			{
    				stage.close();
		    		stage = new Stage();
		    		
					loader.setLocation(getClass().getResource("/gui/usercontrols/ManagerOrderManager.fxml"));
					try {
						root =  loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					} 
					
					
    			}
    			
    			
    			Utilities.GenericUtilties.SetWindowMovable(root, newStage);
    			Scene scene = new Scene(root);
    			newStage.initStyle(StageStyle.UNDECORATED);
    			newStage.setScene(scene); 	
    			newStage.show();
    			WindowControl.stage = newStage;
    		}
	
    	}
	}	

}
