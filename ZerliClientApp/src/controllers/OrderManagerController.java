package controllers;

import java.net.URL;
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

public class OrderManagerController implements Initializable{


	private ObservableList<Order> observableList;
	
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Label branchName;

    @FXML
    private TableColumn<Order, String> dateColumn;

    @FXML
    private TableColumn<Order, String> expectedColumn;

    @FXML
    private TableColumn<Order, Integer> orderNumberColumn;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> priceColumn;

    @FXML
    private Button saveButton;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private TableColumn<Order, String> usernameColumn;

    @FXML
    void save(ActionEvent event) {

    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		expectedColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		ordersTable.setEditable(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true)
				{
					try 
					{
						ClientApp.ProtocolHandler.Invoke(RequestType.GetOrders, null, new String[] {"", branchName.getText()}, true); // send to server
						javafx.application.Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								observableList = (ObservableList<Order>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetAllOrders);
								ordersTable.setItems(observableList);
							}
						});
						Thread.sleep(1500);
					} 
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}

		}).start();
    }

}
