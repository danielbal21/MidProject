package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

public class ManagerViewOrderDetailsController implements UserControl{

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private TableColumn<?, ?> catalogColumn;

    @FXML
    private TableColumn<?, ?> colorColumn;

    @FXML
    private Label costLabel;

    @FXML
    private TableColumn<?, ?> itemIDColumn;

    @FXML
    private TableColumn<?, ?> itemNameColumn;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private Label shippingDateLabel;

    @FXML
    private Label shippingMethodLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    void approvePressed(ActionEvent event) {

    }

    @FXML
    void backPressed(ActionEvent event) {

    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
