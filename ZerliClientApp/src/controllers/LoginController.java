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
import javafx.scene.control.PasswordField;
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
    private PasswordField passwordText;

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
    		loginLabel.setText("Username and password fields\ncannot be empty");
    		return false;
    	}
    }
    
    
    @FXML
    void EnterPassword(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    	{
    		logIn();
    	}
    }

    @FXML
    void EnterUserName(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    	{
    		logIn();
    	}
    }
    
	private void logIn() {
		loginLabel.setVisible(false);
    	if(!validateInput()) return;
    	ClientApp.ProtocolHandler.Invoke(RequestType.AuthenticateUser, null, new String[] {userNameText.getText(),passwordText.getText()}, true);
    	Object[] loginDetails;//= {0,Access.active,Roles.expert};
    	loginDetails =(Object[])ClientApp.ProtocolHandler.GetResponse(RequestType.AuthenticateUser);
    	Access access=(Access)loginDetails[1];
    	if (access==Access.active || access==Access.frozen)
    	{
    		int loggedIn=(int)loginDetails[0];
    		if (loggedIn==0)
    		{
    			Roles role =(Roles)loginDetails[2];
    			FXMLLoader loader = new FXMLLoader();
    	    	Parent root = null;
    	    	ClientApp.UserID =userNameText.getText();
    	    	ClientApp.UserStatus = access;
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
    				windowControl.setUserControl("/gui/usercontrols/CustomerHomePage.fxml");
    				cfc.init();
    					
    	    	}
        		if(role == Roles.manager)
        		{
        			windowControl.stage.close();
    				loader.setLocation(getClass().getResource("/gui/mainframes/ManagerMainScreen.fxml"));

    				try {
    					root =  loader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				} 
    				ManagerFrameController mfc= loader.getController();
    				windowControl = new WindowControl(mfc);
					mfc.init();
    				windowControl.setUserControl("/gui/usercontrols/ManagerHomePage.fxml");
        		}
        		if(role == Roles.delivery)
        		{
        			windowControl.stage.close();
    				loader.setLocation(getClass().getResource("/gui/mainframes/DeliveryMainScreen.fxml"));

    				try {
    					root =  loader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				} 
    				DeliveryFrameController mfc= loader.getController();
    				windowControl = new WindowControl(mfc);
					mfc.init();
    				windowControl.setUserControl("/gui/usercontrols/DeliveryOrderManager.fxml");
        		}
        		if(role == Roles.marketing)
        		{
        			windowControl.stage.close();
    				loader.setLocation(getClass().getResource("/gui/mainframes/MarketingMainScreen.fxml"));

    				try {
    					root =  loader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				} 
    				MarketingFrameController mfc= loader.getController();
    				windowControl = new WindowControl(mfc);
					mfc.init();
    				windowControl.setUserControl("/gui/usercontrols/MarketingCatalogEditor.fxml");
        		}
        		if(role == Roles.service)
        		{
        			windowControl.stage.close();
    				loader.setLocation(getClass().getResource("/gui/mainframes/CustomerServiceMainScreen.fxml"));

    				try {
    					root =  loader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				} 
    				CustomerServiceFrameController mfc= loader.getController();
    				windowControl = new WindowControl(mfc);
    				windowControl.putPipe("service frame controller", mfc);
					mfc.init();
    				windowControl.setUserControl("/gui/usercontrols/ServiceHomePage.fxml");
        		}
        		
        		if(role==Roles.expert)
        		{
        			windowControl.stage.close();
    				loader.setLocation(getClass().getResource("/gui/mainframes/ExpertMainScreen.fxml"));
    				try {
    					root =  loader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				} 
    				ExpertMainFrameController efc= loader.getController();
    				windowControl = new WindowControl(efc);
    				efc.init();
    				windowControl.setUserControl("/gui/usercontrols/ExpertHomePage.fxml");
        		}
        		if(role == Roles.ceo)
        		{
        			windowControl.stage.close();
    				loader.setLocation(getClass().getResource("/gui/mainframes/CEOMainScreen.fxml"));

    				try {
    					root =  loader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				} 
    				CEOFrameController mfc= loader.getController();
    				windowControl = new WindowControl(mfc);
					mfc.init();
    				windowControl.setUserControl("/gui/usercontrols/CEOReportsSelection.fxml");
        		}
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
    	else if(access == Access.noaut)
    	{
    		loginLabel.setVisible(true);
    		loginLabel.setText("The Credentials entered are wrong");
    		return;
    	}
    	else if(access==Access.inactive)
    	{
    		FXMLLoader loader = new FXMLLoader();
	    	Parent root = null;
	    	ClientApp.UserID =userNameText.getText();
	    	ClientApp.UserStatus = access;
    		Roles role =(Roles)loginDetails[2];
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
				windowControl.setUserControl("/gui/usercontrols/CustomerHomePage.fxml");
				cfc.init();
				Stage newStage = new Stage();
        		Utilities.GenericUtilties.SetWindowMovable(root, newStage);
        		Scene scene = new Scene(root);
        		newStage.initStyle(StageStyle.UNDECORATED);
        		newStage.setScene(scene); 	
        		newStage.show();
        		windowControl.stage = newStage;
	    	}
    		else {
        		loginLabel.setVisible(true);
        		loginLabel.setText("Your employee account is inactive\nContact Zerli's administration");
        		return;	
    		}
    	}
	}
    @FXML
    void loginPressed(ActionEvent event) 
    {
    	logIn();
    }

    @FXML
    void userNameEnterprassed(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    	{
    		logIn();
    	}
    }
}
