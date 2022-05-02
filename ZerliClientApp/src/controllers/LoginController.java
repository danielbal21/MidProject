package controllers;

import java.io.IOException;

import client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

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
			Main.ClientConnection.closeConnection();
			System.out.println(Main.ClientConnection.isConnected());
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
    }

    @FXML
    void loginPressed(ActionEvent event) {
    	
    }

}
