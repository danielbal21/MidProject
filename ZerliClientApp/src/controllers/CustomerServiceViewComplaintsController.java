package controllers;

import java.sql.Timestamp;

import Entities.Complaint;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CustomerServiceViewComplaintsController implements UserControl{

	private ObservableList<Complaint> observableList;
    @FXML
    private Button PostButton;

    @FXML
    private TableView<Complaint> complaintsTable;

    @FXML
    private TableColumn<Complaint, Timestamp> postTimeColumn;

    @FXML
    private TableColumn<Complaint, String> userIDColumn;
    
    @FXML
    private TableColumn<Complaint, String> branchColumn;

    @FXML
    void PostComplaintPress(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServicePostComplaint.fxml");
    }

 

    @FXML
    void getComplainInfo(MouseEvent event) {
    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
    		Complaint chosenComplaint = complaintsTable.getSelectionModel().getSelectedItem();
    		LoginController.windowControl.putPipe("Complaint select", chosenComplaint);
    		LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewOrderComplaintInfo.fxml");
    	}
    	else return;
    	
    }
    @FXML
    void refreshPressed(MouseEvent event) {
    	onEnter();
    }

	@SuppressWarnings("unchecked")
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		userIDColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		postTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Comp_date"));
		branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));
		ClientApp.ProtocolHandler.Invoke(RequestType.GetComplaints,  null, null, true);
		complaintsTable.setItems((ObservableList<Complaint>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetComplaints));
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
