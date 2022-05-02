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

    @FXML
    private Button loginBtn;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField userNameText;

	private Stage stage;

    @FXML
    void exitPressed(MouseEvent event) {
    	try {
			ClientApp.ClientConnection.closeConnection();
			System.out.println(ClientApp.ClientConnection.isConnected());
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
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
    	else if(role == Roles.customer)
    	{
    		stage.close();
    		stage = new Stage();
    		ClientApp.UserID = userNameText.getText();
			loader.setLocation(getClass().getResource("/gui/mainframes/CustomerMainScreen.fxml"));
			try {
				root =  loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			//c = loader.getController();
    	}
		//Utilities.GenericUtilties.SetWindowMovable(root, stage);
		Scene scene = new Scene(root);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene); 	
		//c.setStage(stage);
		stage.show();
		}

	public void setStage(Stage newStage) {
		// TODO Auto-generated method stub
		this.stage = newStage;
	}

}
