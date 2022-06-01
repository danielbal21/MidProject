package controllers;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import Entities.Complaint;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomerServiceFrameController implements IContainable {

	
	Thread task;
	ObservableList<Complaint> complaints;
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
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    	
    }

    @FXML
    void exitPressed(MouseEvent event) {
     	ClientApp.ProtocolHandler.Invoke(RequestType.SetLogOut,null,null,false);
    	try {
    		task.interrupt();
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
		task.interrupt();
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
	
	@SuppressWarnings("unchecked")
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
		complaints = (ObservableList<Complaint>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetComplaints);
		int numberOfComplaints = complaints.size();
		bellRedCricleLabl.setText(String.valueOf(numberOfComplaints));
		
		task = new Thread(new Runnable() {

			@Override
			public void run() {
			try {
				while(true)
				{
					Thread.sleep(1200 * 1000);
					ClientApp.ProtocolHandler.Invoke(RequestType.GetComplaints,  null, null, true);
					complaints = (ObservableList<Complaint>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetComplaints);
					for(Complaint c : complaints)
					{
						//c.getComplain_time().toLocalDateTime().plusHours(24).isBefore(LocalDateTime.now())
						if(Duration.between(c.getComplain_time().toLocalDateTime(), LocalDateTime.now()).abs().toHours() >= 24)
						{
							if(c.getIsNotNotified() == 1)
							{
								c.setIsNotNotified(0);
								ClientApp.ProtocolHandler.Invoke(RequestType.SetComplaintAlerted, null, c.getComplaint_id(), false);
								Platform.runLater( () -> {
								Alert confirmAlert = new Alert(AlertType.NONE);
								confirmAlert.setTitle("Please provide assistance");
								confirmAlert.setContentText("Please handle complaint number #" + c.getComplaint_id() + "\n more than 24 hours have passed");
								ButtonType ok = new ButtonType("ok",ButtonData.OK_DONE);
								confirmAlert.getDialogPane().getButtonTypes().add(ok);
								confirmAlert.showAndWait();
								});
							}
						}
					}
				}
			}
			catch (InterruptedException e) {
			}
		}
	});
		
		task.start();
	}

	public void editCircle(int i)
	{
		String bellCnt = bellRedCricleLabl.getText();
		int bellInt = Integer.valueOf(bellCnt);
		bellInt=bellInt + i;
		bellRedCricleLabl.setText(String.valueOf(bellInt));
	}

	
	
}
