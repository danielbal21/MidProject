package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import Entities.Complaint;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerServicePostComplaintController implements UserControl {

    @FXML
    private TextArea feedBackText;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private TextField branchField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField userIDField;

    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    }

    @FXML
    void postPress(ActionEvent event) {
    	Complaint complaint = new Complaint();
    	complaint.setUser_id(userIDField.getText());
    	complaint.setBranch(branchField.getText());
    	complaint.setComplain_text(feedBackText.getText());
    	complaint.setComplain_time(Timestamp.valueOf(LocalDateTime.now()));
    	ClientApp.ProtocolHandler.Invoke(RequestType.MakeComplaint,complaint,null,true);
    	if(!(boolean)ClientApp.ProtocolHandler.GetResponse(RequestType.MakeComplaint))
    	{
    		errorLabel.setVisible(true);
    		errorLabel.setText("No such user");
    	}
    	ClientApp.ProtocolHandler.Invoke(RequestType.GetComplaints,  null, null, true);
    	LoginController.windowControl.putPipe("Complaints", ClientApp.ProtocolHandler.GetResponse(RequestType.GetComplaints));
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    	
    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		errorLabel.setVisible(false);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
