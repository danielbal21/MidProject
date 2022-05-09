package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import Entities.Order;
import ProtocolHandler.RequestType;
import client.ClientApp;
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

   
	

	

/*@FXML
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

	}*/


	@Override
	public void onEnter() {
	
		branchName.setText((String) LoginController.windowControl.peekPipe("Manager Branch"));
		/*orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
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
						ClientApp.ProtocolHandler.Invoke(RequestType.GetAllOrders, null, null, true); // send to server
						
						javafx.application.Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								observableList = (ObservableList<Order>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetAllOrders);
								orderTable.setItems(observableList);
							}
						});
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

		*/
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}



}
