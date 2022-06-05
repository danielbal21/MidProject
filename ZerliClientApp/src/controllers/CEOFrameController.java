package controllers;

import java.io.IOException;

import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CEOFrameController implements IContainable {

    @FXML
    private AnchorPane controlContainer;

    @FXML
    private Label userLabel;

    @FXML
    void reportsPressed(ActionEvent event) {
		LoginController.windowControl.setUserControl("/gui/usercontrols/CEOReportsSelection.fxml");
    }

    @FXML
    void serviceAnalyzerPressed(ActionEvent event) {
		LoginController.windowControl.setUserControl("/gui/usercontrols/ViewExpertReports.fxml");
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
    void homePressed(MouseEvent event) {
		LoginController.windowControl.setUserControl("/gui/usercontrols/ServiceHomePage.fxml");
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

	@Override
	public AnchorPane getControlContainer() {
		// TODO Auto-generated method stub
		return controlContainer;
	}

	public void init() {
    	userLabel.setText(ClientApp.UserID.toString());
	}

}
