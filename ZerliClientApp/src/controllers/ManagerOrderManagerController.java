package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import Entities.Order;
import Entities.PaymentMethods;
import Entities.ShippingMethods;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManagerOrderManagerController implements UserControl {

	private ObservableList<Order> observableList;
	
	   @FXML
	    private Label branchName;

	    @FXML
	    private TableColumn<Order, String> expectedDateColumn;

	    @FXML
	    private TableColumn<Order, String> orderDateColumn;

	    @FXML
	    private TableColumn<Order, String> orderIDcolumn;

	    @FXML
	    private TableView<Order> ordersTable;

	    @FXML
	    private TableColumn<Order, String> paymentMethodColumn;

	    @FXML
	    private TableColumn<Order, Integer> priceColumn;

	    @FXML
	    private TableColumn<Order, String> shippingMethodColumn;

	    @FXML
	    private TableColumn<Order, String> statusColumn;

	    @FXML
	    private TableColumn<Order, String> userIDColumn;

	    @FXML
	    void backPressed(ActionEvent event) 
	    {
	    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerHomePage.fxml");
	    }

   
	

	

	    @FXML
	    void getOrderInfo(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			Order OrderChoose = ordersTable.getSelectionModel().getSelectedItem();
			LoginController.windowControl.putPipe("Order select", OrderChoose );
			LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerViewOrderDetails.fxml");

		}

	}


	@Override
	public void onEnter() {
	
		branchName.setText((String) LoginController.windowControl.peekPipe("Branch"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		orderIDcolumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		expectedDateColumn.setCellValueFactory(new PropertyValueFactory<>("shippingDate"));
		shippingMethodColumn.setCellValueFactory(new PropertyValueFactory<>("shippingMethod"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
		
		ClientApp.ProtocolHandler.Invoke(RequestType.GetOrdersByBranch,  LoginController.windowControl.peekPipe("Branch"), "pending", true);
		
		observableList = (ObservableList<Order>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetOrdersByBranch);
		
		ordersTable.setItems(observableList);
	
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}



}
