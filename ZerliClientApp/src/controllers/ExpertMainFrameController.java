package controllers;

import java.io.IOException;

import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExpertMainFrameController implements IContainable{
    @FXML
    private ImageView homeBtn;
    @FXML
    private ImageView cartRedCircleImage;

    @FXML
    private AnchorPane controlContainer;

    @FXML
    private ImageView exitBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label nameLabel;

    @FXML
    void homePressed(MouseEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ExpertHomePage.fxml");
    }
    @FXML
    void exitPressed(MouseEvent event) {
    	ClientApp.ProtocolHandler.Invoke(RequestType.SetLogOut,null,null,false);
    	try {
			ClientApp.ClientConnection.closeConnection();
			System.out.println(ClientApp.ClientConnection.isConnected());
			System.exit(0);
		} catch (IOException e) {e.printStackTrace();}
    }

    @FXML
    void pressLogout(ActionEvent event) {
    	LoginController.windowControl.stage.close();
    	Stage newStage = new Stage();
		Parent root = null;
		try 
		{
			ClientApp.ProtocolHandler.Invoke(RequestType.SetLogOut,null,null,false);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/mainframes/Login.fxml"));
			root =  loader.load(); 
			Utilities.GenericUtilties.SetWindowMovable(root, newStage);
			Scene scene = new Scene(root);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.setScene(scene); 
			newStage.show();
			LoginController.windowControl.stage = newStage;	
		}
		catch (Exception e) {e.printStackTrace();	}
    }

    void init(){
    	nameLabel.setText(ClientApp.UserID.toString());	
    }
	@Override
	public AnchorPane getControlContainer() {
		return controlContainer;
	}

}
