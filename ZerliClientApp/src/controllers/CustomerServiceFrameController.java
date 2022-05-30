package controllers;

import java.io.IOException;

import Entities.Complaint;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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

public class CustomerServiceFrameController implements IContainable {

    @FXML
    private Button ComplaintsBtn;

    @FXML
    private Button SurveyMgmtBtn;

    @FXML
    private Label bellRedCricleLabl;

    @FXML
    private AnchorPane controlContainer;

    @FXML
    private ImageView exitBtn;

    @FXML
    private ImageView homeBtn;

    @FXML
    private ImageView redCircle;
    
    @FXML
    private Button logoutBtn;

    @FXML
    private Label nameLabel;


    /** example for testing auto-update red label circle **/
	boolean x = true;
    @FXML
	void complaintsPressed(ActionEvent event) {
    	bellRedCricleLabl.setText("0");
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
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
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerHomePage.fxml");
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

    @FXML
    void surveyPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/SurveyMangerHomeScreen.fxml");
    }

	@Override
	public AnchorPane getControlContainer() {
		return controlContainer;
	}
	
	public void init()
	{
		nameLabel.setText(ClientApp.UserID.toString());
		bellRedCricleLabl.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(Integer.parseInt(newValue) <= 0)
				{
					redCircle.setVisible(false);
					bellRedCricleLabl.setVisible(false);
				}
				else
				{
					redCircle.setVisible(true);
					bellRedCricleLabl.setVisible(true);
				}
			}
		});
		ClientApp.ProtocolHandler.Invoke(RequestType.GetComplaints,  null, null, true);
		LoginController.windowControl.putPipe("Complaints", ClientApp.ProtocolHandler.GetResponse(RequestType.GetComplaints));
		ObservableList<Complaint> ObservableList = (ObservableList<Complaint>)LoginController.windowControl.peekPipe("Complaints");
		int numberOfComplaints = ObservableList.size();
		bellRedCricleLabl.setText(String.valueOf(numberOfComplaints));
	}

}
