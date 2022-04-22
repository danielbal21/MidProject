package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import Entities.Order;
import ProtocolHandler.Protocol;
import ProtocolHandler.RequestType;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class OrdersMenuController implements Initializable {

	private ObservableList<Order> observableList;
<<<<<<< HEAD
	ObservableList<String> Colors = FXCollections.observableArrayList("Yellow", "Green", "Pink", "White", "Blue",
			"Red");
=======
	ObservableList<String> Colors = FXCollections.observableArrayList("Yellow", "Green", "Pink", "white", "blue", "red");
>>>>>>> refs/heads/Michael

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
<<<<<<< HEAD
	@FXML
	private TextField timeField;
	@FXML
	private Button updateButton;
	@FXML
	private TextField hourField;
=======
>>>>>>> refs/heads/Michael

	@FXML
	private TextField minutesField;
	@FXML
	private Label errorLabel;

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
<<<<<<< HEAD
			updateButton.setVisible(true);
			orderIDLabel.setText(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getOrderNumber()));
			colorComboBox.setItems(Colors);
			colorComboBox.getSelectionModel().select(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getColor()));;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime localTime = LocalDateTime.parse(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getDate()),formatter);
			datePicker.setValue(localTime.toLocalDate());
			hourField.setText(String.valueOf(localTime.getHour()));
			minutesField.setText(String.valueOf(localTime.getMinute()));
=======
			orderIDLabel.setText(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getOrderNumber()));	
			colorComboBox.setItems(Colors);
			
			
>>>>>>> refs/heads/Michael
		}

<<<<<<< HEAD
	}


	@FXML
	void exitPressed(ActionEvent event) {
		try {
=======
    }
    @FXML
    void getColorFromCombox(MouseEvent event) {
    	colorLabel.setText(colorComboBox.getSelectionModel().getSelectedItem().toString());
    }
	
    @FXML
    void exitPressed(ActionEvent event) {
    	try {
>>>>>>> refs/heads/Michael
			ClientUI.ClientConnection.closeConnection();
			System.out.println(ClientUI.ClientConnection.isConnected());
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

	}

	@FXML
	void updatePresseed(ActionEvent event) {
		String orderID = orderIDLabel.getText();
		String time = hourField.getText() + ":" + minutesField.getText() + ":00";
		String [] data = new String [2];
		data[0] = colorComboBox.getSelectionModel().getSelectedItem().toString();
		LocalDateTime local = datePicker.getValue().atTime(LocalTime.parse(time,DateTimeFormatter.ofPattern("HH:mm:ss")));
		System.out.println(local.toString());
		data[1] = local.toString();
		ClientUI.ProtocolHandler.Invoke(RequestType.UpdateOrder, data, orderID, false);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		greetingCradColumn.setCellValueFactory(new PropertyValueFactory<>("grettingCard"));
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
		colorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		dOrderColumn.setCellValueFactory(new PropertyValueFactory<>("dOrder"));
		shopColumn.setCellValueFactory(new PropertyValueFactory<>("shop"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		oDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		orderTable.setEditable(true);
		ClientUI.ProtocolHandler.Invoke(RequestType.GetAllOrders, null, null, true); // send to server
		observableList = (ObservableList<Order>) ClientUI.ProtocolHandler.GetResponse(RequestType.GetAllOrders);
		orderTable.setItems(observableList);

		datePicker.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0);
			}
		});
		

	}

}
