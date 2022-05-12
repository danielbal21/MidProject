package controllers;

import java.io.IOException;

import Entities.Access;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

	private void logIn() {
		loginLabel.setVisible(false);
    	if(!validateInput()) return;
    	ClientApp.ProtocolHandler.Invoke(RequestType.AuthenticateUser, null, new String[] {userNameText.getText(),passwordText.getText()}, true);
    	Object[] loginDetails;
    	loginDetails =(Object[])ClientApp.ProtocolHandler.GetResponse(RequestType.AuthenticateUser);

    	Access access=(Access)loginDetails[1];
    	if (access==Access.active)
    	{
    		int loggedIn=(int)loginDetails[0];
    		if (loggedIn==0)
    		{
    			Roles role =(Roles)loginDetails[2];
    			FXMLLoader loader = new FXMLLoader();
    	    	Parent root = null;
    	    	ClientApp.UserID = userNameText.getText();
    	    	if(role == Roles.customer)
    	    	{
        			windowControl.stage.close();
    				loader.setLocation(getClass().getResource("/gui/mainframes/CustomerMainScreen.fxml"));
    			
    				try {
    					root =  loader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				} 
    				CustomerFrameController cfc= loader.getController();
    				windowControl = new WindowControl(cfc);
					cfc.init();
    				windowControl.setUserControl("/gui/usercontrols/CustomerHomePage.fxml");
    					
    	    	}
        			/*if(role == Roles.manager)
        			{
        				
        				windowControl.stage.close();
    					loader.setLocation(getClass().getResource("/gui/mainframes/ManagerMainScreen.fxml"));
    			
    					try {
    						root =  loader.load();
    					} catch (IOException e) {
    						e.printStackTrace();
    					} 
    					windowControl = new WindowControl(loader.getController());
    					windowControl.frameController.loadAll();
        			}*/

        		Stage newStage = new Stage();
        		Utilities.GenericUtilties.SetWindowMovable(root, newStage);
        		Scene scene = new Scene(root);
        		newStage.initStyle(StageStyle.UNDECORATED);
        		newStage.setScene(scene); 	
        		newStage.show();
        		windowControl.stage = newStage;
			}
    		else
    		{
    			loginLabel.setVisible(true);
        		loginLabel.setText("You are already logged In");
        		return;	
    		}
    	}
    	else if(access == Access.noaut )
    	{
    		loginLabel.setVisible(true);
    		loginLabel.setText("The Credentials entered are wrong");
    		return;
    	}
    	else if(access==Access.inactive)
    	{
    		
    		loginLabel.setVisible(true);
    		loginLabel.setText("Go to one of Zerli's branches to register");
    		return;	
    	}
    	else 
    	{
    		loginLabel.setVisible(true);
    		loginLabel.setText("Your acount is frozen please contact Zerli's administration");
    		return;	
		}
	}
    @FXML
    void loginPressed(ActionEvent event) 
    {
    	logIn();
    }
    @FXML
    void enterPressed(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    	{
    		logIn();
    	}
    }
    @FXML
    void passwordEnterPressed(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    	{
    		logIn();
    	}
    }

    @FXML
    void userNameEnterprassed(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    	{
    		logIn();
    	}
    }
}
