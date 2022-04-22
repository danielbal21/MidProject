package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Entities.Order;
import ProtocolHandler.RequestType;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class OrdersMenuController implements Initializable {

	private ObservableList<Order> observableList;
	ObservableList<String> Colors = FXCollections.observableArrayList("Yellow", "Green", "Pink", "white", "blue", "red");

	@FXML
	private TableColumn<Order, Integer> orderNumberColumn;
	@FXML
	private TableColumn<Order, Integer> priceColumn;
	@FXML
	private TableColumn<Order, String> greetingCradColumn;
	@FXML
	private TableColumn<Order, String> colorColumn;
	@FXML
	private TableColumn<Order, String> dOrderColumn;
	@FXML
	private TableColumn<Order, String> shopColumn;
	@FXML
	private TableColumn<Order, String> dateColumn;
	@FXML
	private TableColumn<Order, String> oDateColumn;
	@FXML
	private TableView<Order> orderTable;
	@FXML
	private Label orderIDLabel;
	@FXML
	private Label orderLabel;
	@FXML
	private Label colorLabel;
    @FXML
    private Button exitBtn;
	@FXML
	private Label dateLabel;

	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox<String> colorComboBox;

	public void addOrdersToTable() {
		
		orderTable.setEditable(true);
		ClientUI.ProtocolHandler.Invoke(RequestType.GetAllOrders, null, null, true); // send to server
		observableList = (ObservableList<Order>) ClientUI.ProtocolHandler.GetResponse(RequestType.GetAllOrders);
		orderTable.setItems(observableList);
	}

	@FXML
	void EditColor(ActionEvent event) {

	}

	@FXML
	void EditDate(ActionEvent event) {

	}
	
	@FXML
    void getOrderInfo(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			orderIDLabel.setVisible(true);
			orderLabel.setVisible(true);
			colorLabel.setVisible(true);
			colorComboBox.setVisible(true);
			datePicker.setVisible(true);
			dateLabel.setVisible(true);
			orderIDLabel.setText(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getOrderNumber()));	
			colorComboBox.setItems(Colors);
			
			
		}
			

    }
    @FXML
    void getColorFromCombox(MouseEvent event) {
    	colorLabel.setText(colorComboBox.getSelectionModel().getSelectedItem().toString());
    }
	
    @FXML
    void exitPressed(ActionEvent event) {
    	try {
			ClientUI.ClientConnection.closeConnection();
			System.out.println(ClientUI.ClientConnection.isConnected());
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		greetingCradColumn.setCellValueFactory(new PropertyValueFactory<>("grettingCard"));
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
		dOrderColumn.setCellValueFactory(new PropertyValueFactory<>("dOrder"));
		shopColumn.setCellValueFactory(new PropertyValueFactory<>("shop"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		oDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
	}

}
