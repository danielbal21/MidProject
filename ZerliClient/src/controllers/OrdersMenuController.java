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
import javafx.scene.layout.AnchorPane;

public class OrdersMenuController implements Initializable {

	private ObservableList<Order> observableList;
	ObservableList<String> Colors = FXCollections.observableArrayList("Yellow", "Green", "Pink", "White", "Blue",
			"Red");

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

	@FXML
	private TextField timeField;
	@FXML
	private Button updateButton;
	@FXML
	private TextField hourField;

	@FXML
	private TextField minutesField;
	@FXML
	private Label errorLabel;
	@FXML
	private Label hourLabel;
	@FXML
	private Label minutesLabel;
	@FXML
	private Label colonLabel;
    @FXML
    private AnchorPane editPanelContainer;
	@FXML
	void EditColor(ActionEvent event) {

	}

	@FXML
	void EditDate(ActionEvent event) {

	}

	@FXML
	void getOrderInfo(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			editPanelContainer.setVisible(true);
			errorLabel.setVisible(false);
			orderIDLabel.setText(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getOrderNumber()));
			colorComboBox.setItems(Colors);
			colorComboBox.getSelectionModel()
					.select(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getColor()));
			;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime localTime = LocalDateTime
					.parse(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getDate()), formatter);
			datePicker.setValue(localTime.toLocalDate());
			hourField.setText(String.valueOf(localTime.getHour()));
			minutesField.setText(String.valueOf(localTime.getMinute()));

		}

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

	@FXML
	void updatePresseed(ActionEvent event) {
		int hours;
		int minutes;
		try
		{
			hours = Integer.parseInt(hourField.getText());
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			errorLabel.setVisible(true);
			errorLabel.setText("Hours should be a number");
			return;
		}
		if(hours >= 24 || hours < 0)
		{
			errorLabel.setVisible(true);
			errorLabel.setText("Invalid Hour (0-23)");
			return;
		}
		try
		{
			minutes = Integer.parseInt(minutesField.getText());
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			errorLabel.setVisible(true);
			errorLabel.setText("Minutes should be a number");
			return;
		}
		if(minutes >= 60 || hours < 0)
		{
			errorLabel.setVisible(true);
			errorLabel.setText("Invalid Minutes (0-59)");
			return;
		}
		errorLabel.setVisible(false);
		String orderID = orderIDLabel.getText();
		String time = hourField.getText() + ":" + minutesField.getText() + ":00";
		String[] data = new String[2];
		data[0] = colorComboBox.getSelectionModel().getSelectedItem().toString();
		LocalDateTime local = datePicker.getValue()
				.atTime(LocalTime.parse(time, DateTimeFormatter.ofPattern("H:m:ss")));
		System.out.println(local.toString());
		data[1] = local.toString();
		ClientUI.ProtocolHandler.Invoke(RequestType.UpdateOrder, data, orderID, false);

		ClientUI.ProtocolHandler.Invoke(RequestType.GetAllOrders, null, null, true); // send to server
		observableList = (ObservableList<Order>) ClientUI.ProtocolHandler.GetResponse(RequestType.GetAllOrders);
		orderTable.setItems(observableList);

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
		orderTable.setEditable(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						ClientUI.ProtocolHandler.Invoke(RequestType.GetAllOrders, null, null, true); // send to server
						observableList = (ObservableList<Order>) ClientUI.ProtocolHandler
								.GetResponse(RequestType.GetAllOrders);
						orderTable.setItems(observableList);
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();

		datePicker.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0);
			}
		});

	}

}
