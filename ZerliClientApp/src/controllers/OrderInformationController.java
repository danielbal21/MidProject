package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import Entities.ItemInList;
import Entities.Order;
import Entities.ShippingMethods;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class OrderInformationController implements UserControl {
	Order order = new Order();
	
    @FXML
    private Button backBtn;
    
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private DatePicker arrivalDate;

    @FXML
    private ComboBox<String> branchCombobox;

    @FXML
    private TextField cityText;
    
    @FXML
    private CheckBox useGreeting;
    
    @FXML
    private ToggleGroup deliveryMethod;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private TextArea greetingText;

    @FXML
    private Button nextBtn;

    @FXML
    private TextField streetAddressText;

    @FXML
    private RadioButton pickupRB;

    @FXML
    private RadioButton shippingRB;
    
    @FXML
    private Slider hourSlider;

    @FXML
    private Slider minutesSlider;
    
    @FXML
    private Label timePresented;
    boolean systemChange = false;
    @FXML
    void hourSlide(MouseEvent event) {
    	LocalTime timeMeasure = LocalTime.of((int)hourSlider.getValue(),(int)minutesSlider.getValue());
    	LocalDateTime arrivalFull = LocalDateTime.of(arrivalDate.getValue(), timeMeasure);
    	long diff;

    		if(LocalDateTime.now().plusHours(3).isAfter(arrivalFull))
    		{
    			var minDT = LocalDateTime.now().plusHours(3);
    			arrivalDate.setValue(minDT.toLocalDate());
    			hourSlider.setValue(minDT.getHour());
    			minutesSlider.setValue(minDT.getMinute());
    		}
    	
    	/*
		if((required = Duration.between(LocalDateTime.now(),LocalDateTime.of(arrivalDate.getValue(),timeMeasure))
				.toSeconds()) < 3*3600) 
		{
			systemChange = true;
			var minimumDate = LocalDateTime.of(arrivalDate.getValue(), timeMeasure).plusSeconds(required);
			arrivalDate.setValue(minimumDate.toLocalDate());
			hourSlider.setValue(minimumDate.getHour());
			minutesSlider.setValue(minimumDate.getMinute());
		}
		*/
    }

    @FXML
    void minuteSlide(MouseEvent event) {
    	LocalTime timeMeasure = LocalTime.of((int)hourSlider.getValue(),(int)minutesSlider.getValue());
    	LocalDateTime arrivalFull = LocalDateTime.of(arrivalDate.getValue(), timeMeasure);
    	long diff;

    		if(LocalDateTime.now().plusHours(3).isAfter(arrivalFull))
    		{
    			var minDT = LocalDateTime.now().plusHours(3);
    			arrivalDate.setValue(minDT.toLocalDate());
    			hourSlider.setValue(minDT.getHour());
    			minutesSlider.setValue(minDT.getMinute());
    		}
    	

    	/*else if((diff = Duration.between(LocalDateTime.now(),LocalDateTime.of(arrivalDate.getValue(),timeMeasure))
				.toSeconds()) < 3*3600) 
		{
			System.out.println(diff);
			systemChange = true;
			var minimumDate = LocalDateTime.of(arrivalDate.getValue(), timeMeasure).plusSeconds(diff);
			arrivalDate.setValue(minimumDate.toLocalDate());
			hourSlider.setValue(minimumDate.getHour());
			minutesSlider.setValue(minimumDate.getMinute());
		}*/
    }
    @FXML
    void changeDateEvent(ActionEvent event) {

    }
    @SuppressWarnings("unchecked")
	@FXML
    void nextBtn_Click(ActionEvent event) {
    	if(!isInputValid()) return;
    	order.setBranchName(branchCombobox.getSelectionModel().getSelectedItem().toString());
    	order.setItems((ArrayList<ItemInList>)LoginController.windowControl.getPipe("CartItems"));
    	LocalTime time = LocalTime.of((int)(hourSlider.getValue()), (int)(minutesSlider.getValue()));
    	order.setOrderDate(Utilities.GenericUtilties.Convert_LocalDate_To_SQLDate(LocalDateTime.now().toLocalDate(),time));

    	if(deliveryMethod.getSelectedToggle().equals(shippingRB))
    	{
    		order.setShippingMethod(ShippingMethods.shipping);
    		LoginController.windowControl.putPipe("isShipping", true);
    		order.setAddress(streetAddressText.getText());
    		order.setCity(cityText.getText());
        	order.setShippingDate(Utilities.GenericUtilties.Convert_LocalDate_To_SQLDate(arrivalDate.getValue(),time));
    	}
    	else if(deliveryMethod.getSelectedToggle().equals(pickupRB))
    	{
    		order.setShippingMethod(ShippingMethods.pickup);
    		LoginController.windowControl.putPipe("isShipping", false);
    		order.setAddress(null);
    		order.setCity(null);
        	order.setShippingDate(Utilities.GenericUtilties.Convert_LocalDate_To_SQLDate(arrivalDate.getValue(),time));
    	}
    	if(useGreeting.isSelected())
    		order.setGreetingCard(greetingText.getText());
    	else
    		order.setGreetingCard(null);
    	order.setUserID(ClientApp.UserID);
    	LoginController.windowControl.putPipe("orderInfo",order);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerPaymentWindow.fxml");
    }
    
    private boolean isInputValid()
    {
    	StringBuilder errorNotification = new StringBuilder("");
    	boolean valid = true;
    	if(deliveryMethod.getSelectedToggle() == null)
    	{
    		errorNotification.append("Must select delivery method\n");
    		valid = false;
    	}
    	else
    	{
	    	if(deliveryMethod.getSelectedToggle().equals(shippingRB))
	    	{
		    	if(cityText.getText().equals("") || streetAddressText.getText().equals(""))
		    	{
		    		errorNotification.append("Address fields cannot be empty\n");
		    		valid = false;
		    	}
		    	if(arrivalDate.getValue() == null)
		    	{
		    		errorNotification.append("Please select arrival date\n");
		    		valid = false;
		    	}
	    	}
    	}
    	if(branchCombobox.getValue() == null)
    	{
    		errorNotification.append("Please select a branch\n");
    		valid = false;
    	}
    	if(useGreeting.isSelected() && greetingText.getText().equals(""))
    	{
    		errorNotification.append("Greeting cannot be empty\n");
    		valid = false;
    	}

    	
    	errorLabel.setText(errorNotification.toString());
    	return valid;
    }
    boolean back = false;
	@SuppressWarnings("unchecked")
	@FXML
	void backBtn_press(ActionEvent event) {
		back = true;
		LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCart.fxml");
	}
	@Override
	public void onEnter() {
		if(LoginController.windowControl.getPipe("OrderCompleted")!=null) {
			clearAll();
		}
		
		arrivalDate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0);
			}
		});
		arrivalDate.setValue(LocalDate.now());

		ClientApp.ProtocolHandler.Invoke(RequestType.GetBranches, null, null, true);
		ObservableList<String> branches = (ObservableList<String>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetBranches);
		branchCombobox.setItems((ObservableList<String>)branches);
        hourSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(!oldValue.equals(newValue))
					timePresented.setText(String.format("%02d:%02d", newValue.intValue(),(int)minutesSlider.getValue()));
				else
					systemChange = false;
			}
		});
        minutesSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(!oldValue.equals(newValue))
					timePresented.setText(String.format("%02d:%02d", (int)hourSlider.getValue(),newValue.intValue()));
				else
					systemChange = false;
			}
		});
		deliveryMethod.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(!newValue.equals(oldValue))
				{
					if(newValue == null ) return;
					
					if(newValue.equals(pickupRB) )
					{
						cityText.setDisable(true);
						streetAddressText.setDisable(true);
						arrivalDate.setDisable(false);
						hourSlider.setDisable(false);
						minutesSlider.setDisable(false);
					}
					else
					{
						cityText.setDisable(false);
						streetAddressText.setDisable(false);
						arrivalDate.setDisable(false);
						hourSlider.setDisable(false);
						minutesSlider.setDisable(false);
					}
				}
			}
		});
		arrivalDate.valueProperty().addListener((observable, oldDate, newDate) -> { minuteSlide(null);});
		minuteSlide(null);
	}
	

	@Override
	public void onExit() {
		if(!back) return;
			Alert confirmAlert = new Alert(AlertType.NONE);
			confirmAlert.setTitle("Leaving Order Information");
			confirmAlert.setContentText("Do you want to save typed data?");
			ButtonType yes = new ButtonType("Yes", ButtonData.YES);
			ButtonType no = new ButtonType("No",ButtonData.NO);
			confirmAlert.getDialogPane().getButtonTypes().add(yes);
			confirmAlert.getDialogPane().getButtonTypes().add(no);
			Optional<ButtonType> result = confirmAlert.showAndWait();
			result.ifPresent(response -> { 
				if(response == no) clearAll();
			});
			back = false;
	}
	public void clearAll()
	{
    	cityText.clear();
    	deliveryMethod.selectToggle(null);
    	branchCombobox.getSelectionModel().select(null);
		streetAddressText.clear();
		useGreeting.setSelected(false);
		greetingText.clear();
	}
}
