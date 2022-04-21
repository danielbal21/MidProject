package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Entities.Order;
import ProtocolHandler.RequestType;
import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OrdersMenuController implements Initializable {

	private ObservableList<Order> observableList;

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
	private Label dateLabel;

	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox<?> colorComboBox;

	public void addOrdersToTable() {
		
		orderTable.setEditable(true);
		Client.ProtocolHandler.Invoke(RequestType.GetAllOrders, null, null, true);
		
		
		observableList = FXCollections.observableArrayList();
		observableList.add();
		observableList.add();
		observableList.add();
		observableList.add();

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
			
			orderIDLabel.setText(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getOrderNumber()));

			
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