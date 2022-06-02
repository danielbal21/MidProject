package controllers;

import java.util.ArrayList;
import Entities.Access;
import Entities.AccountInfo;
import Entities.Roles;
import ProtocolHandler.RequestType;
import Utilities.GenericUtilties;
import client.ClientApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManagerEditAccountsController implements UserControl{

	ObservableList<AccountInfo> customerList;
	ObservableList<AccountInfo> employeeList;
	AccountInfo accountInfo = null;
	
    @FXML
    private TableView<AccountInfo> AccountManagment;

    @FXML
    private TableColumn<AccountInfo, String> IDCol;

    @FXML
    private TableColumn<AccountInfo, String> accountInfoCol;

    @FXML
    private ComboBox<String> accountInfoChoice;

    @FXML
    private Label accountInfoLbl;

    @FXML
    private ToggleGroup accountType;

    @FXML
    private AnchorPane accountsSection;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private AnchorPane changeSection;

    @FXML
    private RadioButton customerBtn;

    @FXML
    private RadioButton employeeBtn;

    @FXML
    private TableColumn<AccountInfo, String> firstNameCol;

    @FXML
    private Label fullNameLbl;

    @FXML
    private TableColumn<AccountInfo, String> lastNameCol;

    @FXML
    private TableColumn<AccountInfo, String> userIDCol;
    
    @FXML
    private Label changeErrorLbl;

    @FXML
    void ChangeAccountInfoPressed(ActionEvent event) {
    	
    	if(accountInfoChoice.getValue() == null) {
    		changeErrorLbl.setText("Choose option from list");
    	}
    	else if (accountInfoChoice.getValue().equals(accountInfoLbl.getText())) {
    		changeErrorLbl.setText("Choose diffrent option\nfrom exist one");
    	}
    	else {
    		String str;
    		if(accountType.getSelectedToggle() == customerBtn){
    			Access newAccess =  GenericUtilties.StringToAccessStatus(accountInfoChoice.getValue());
        		ClientApp.ProtocolHandler.Invoke(RequestType.UpdateAccountAccess, newAccess, accountInfo.getUserID(), false);
        		str = "Customer " + fullNameLbl.getText() + " changed access to " + accountInfoChoice.getValue();
        		accountType.selectToggle(employeeBtn);
        		accountType.selectToggle(customerBtn);
    		}
    		else {
        		Roles newRole = GenericUtilties.StringToRoles(accountInfoChoice.getValue());
        		ClientApp.ProtocolHandler.Invoke(RequestType.UpdateEmployeeRole, newRole, accountInfo.getUserID(), false);
        		str = "Employee " + fullNameLbl.getText() + " changed role to " + accountInfoChoice.getValue();
        		accountType.selectToggle(customerBtn);
        		accountType.selectToggle(employeeBtn);
    		}
    	}
    }

    @FXML
    void getAccountInfo(MouseEvent event) {
    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
    		accountInfoChoice.setValue(null);
    		
    		if(customerBtn.isSelected()) {
    			accountInfo = customerList.get(AccountManagment.getSelectionModel().getSelectedIndex());
    			accountInfoLbl.setText(GenericUtilties.AccessStatusToString(accountInfo.getAccess()));
    			accountType.selectToggle(customerBtn);
    		}
    		else if(employeeBtn.isSelected()){
    			accountInfo = employeeList.get(AccountManagment.getSelectionModel().getSelectedIndex());
    			accountInfoLbl.setText(GenericUtilties.RolesToString(accountInfo.getRole()));
    			accountType.selectToggle(employeeBtn);
    		}
    		fullNameLbl.setText(accountInfo.getFirstName() + " " + accountInfo.getLastName());
    		changeSection.setVisible(true);
    	}
    	else return;
    }
    

    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerAccountManagment.fxml");
    }

    @FXML
    void refreshPressed(MouseEvent event) {
    	accountType.selectToggle(null);
    	onEnter();
    }
    
    
	@Override
	public void onEnter() {
		changeErrorLbl.setText("");
		accountsSection.setVisible(false);
		changeSection.setVisible(false);
		customerBtn.setSelected(false);
		employeeBtn.setSelected(false);
		
		ArrayList<String> accountStatusList = new ArrayList<>();
		accountStatusList.add("Inactive");
		accountStatusList.add("Active");
		accountStatusList.add("Frozen");
		
		ArrayList<String> employeeRoleList = new ArrayList<>();
		for (Roles role : Roles.values()) {
			if(role == Roles.ceo || role == Roles.manager || role == Roles.customer  ) continue;
			employeeRoleList.add(GenericUtilties.RolesToString(role));
        }
		
		userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));	
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));	
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));	
		IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		accountType.selectToggle(null);
		accountType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (newValue == null) {}
				else if(!newValue.equals(oldValue))
				{	 
					if(newValue.equals(customerBtn))
					{
						ClientApp.ProtocolHandler.Invoke(RequestType.GetAllCustomersInfo, null, null, true);
						customerList = (ObservableList<AccountInfo>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetAllCustomersInfo);
						accountInfoChoice.setItems( FXCollections.observableArrayList(accountStatusList));
						accountInfoCol.setText("Status");
						accountInfoCol.setCellValueFactory(new PropertyValueFactory<>("access"));
						AccountManagment.setItems(customerList);
					}
					else
					{
						ClientApp.ProtocolHandler.Invoke(RequestType.GetAllEmployeesInfo, null, null, true);
						employeeList = (ObservableList<AccountInfo>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetAllEmployeesInfo);
						accountInfoChoice.setItems( FXCollections.observableArrayList(employeeRoleList));
						accountInfoCol.setText("Role");
						accountInfoCol.setCellValueFactory(new PropertyValueFactory<>("role"));
					    AccountManagment.setItems(employeeList);
					}
					accountsSection.setVisible(true);
					changeSection.setVisible(false);
				}
			}
		});
	}

	@Override
	public void onExit() {
		accountType.selectToggle(null);
	}

}

