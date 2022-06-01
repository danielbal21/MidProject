package controllers;

import java.time.format.DateTimeFormatter;

import Entities.Complaint;
import Entities.Order;
import ProtocolHandler.RequestType;
import Utilities.GenericUtilties;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerServiceViewComplaintInfoController implements UserControl{

	private Complaint complaint;
	
    @FXML
    private TextArea AnswerText;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Label branchLabel;

    @FXML
    private Label complaintText;

    @FXML
    private Label dateLabel;


    @FXML
    private TextField refundField;

    @FXML
    private Label userIDlLabel;
    
    @FXML
    private Label errorLabel;

    @FXML
    void EndPressed(ActionEvent event) {
    	int refund;
    	try {
    		if(refundField.getText().equals(""))
    		{
    			refund =0;
    		}
    		else
    		{
    			refund = Integer.parseInt(refundField.getText());
    		}
    		if(refund < 0)
    		{
    			errorLabel.setVisible(true);
        		errorLabel.setText("refund must be positive");
    		}
    		else
    		{
    			complaint.setAnswer_text(AnswerText.getText());
    			complaint.setRefund(refund);
    			ClientApp.ProtocolHandler.Invoke(RequestType.ComplaintResponse,complaint , null, false);
    			ClientApp.ProtocolHandler.Invoke(RequestType.GetComplaints,  null, null, true);
    			CustomerServiceFrameController mfc= (CustomerServiceFrameController)LoginController.windowControl.peekPipe("service frame controller");
				mfc.editCircle(-1);
    			LoginController.windowControl.putPipe("Complaints", ClientApp.ProtocolHandler.GetResponse(RequestType.GetComplaints));
        		LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    		}
    	}catch (NumberFormatException e)
    	{
    		errorLabel.setVisible(true);
    		errorLabel.setText("Please enter a Number");
    	}
    		
    
    }

    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		errorLabel.setVisible(false);
		complaint = (Complaint)LoginController.windowControl.peekPipe("Complaint select");
		complaintText.setText(complaint.getComplain_text());
		userIDlLabel.setText(complaint.getUser_id());
		dateLabel.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(GenericUtilties.Convert_LocalDate_To_SQLDate(complaint.getComplain_time())));
		branchLabel.setText(complaint.getBranch());
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		AnswerText.setText("");
		refundField.setText("");
		
	}

}
