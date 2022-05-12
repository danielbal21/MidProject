package controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import Entities.Order;
import Entities.OrderStatus;
import Entities.PaymentMethods;
import Entities.paymentInfo;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PaymentController implements UserControl {

	private Order currentOrder;
	private boolean isShipping;
	boolean save = false;
    paymentInfo payDetails;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private AnchorPane activePanelContainer2;

    @FXML
    private TextField cardNumTxt1;

    @FXML
    private TextField cardNumTxt2;

    @FXML
    private TextField cardNumTxt3;

    @FXML
    private TextField cardNumTxt4;

    @FXML
    private AnchorPane creditCardDialog;

    @FXML
    private RadioButton creditCardRB;

    @FXML
    private TextField cvvText;

    @FXML
    private Label discountPercentageLabel;

    @FXML
    private ComboBox<String> expMonthCB;

    @FXML
    private TextField holderID;

    @FXML
    private Label netPriceLabel;

    @FXML
    private Button nextBtn;

    @FXML
    private ToggleGroup paymentMethod;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label w;

    @FXML
    private ComboBox<String> yearCB;

    @FXML
    private VBox zerliCoinsDialog;

    @FXML
    private RadioButton zerliCoinsRB;

    @FXML
    private Label postPayBalanceLbl;

    @FXML
    private Label balanceLbl;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    void nextBtn_Click(ActionEvent event) {
    	if(!validateInput()) return;
    	if(paymentMethod.getSelectedToggle().equals(creditCardRB))
    		currentOrder.setPaymentMethod(PaymentMethods.credit_card);
    	else if(paymentMethod.getSelectedToggle().equals(zerliCoinsRB))
    		currentOrder.setPaymentMethod(PaymentMethods.zerli_coins);
    	
    	if(paymentMethod.getSelectedToggle() == zerliCoinsRB)
    	{
    		if(payDetails.ZerliCoins < currentOrder.getTotalPrice())
    		{
    			Alert confirmAlert = new Alert(AlertType.ERROR);
    			confirmAlert.setTitle("No funds");
    			confirmAlert.setContentText("Insufficient ZCoins to perform payment");
    			ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
    			confirmAlert.getDialogPane().getButtonTypes().add(ok);
    			confirmAlert.showAndWait();
    			return;
    		}
    		else
    		{
    			var newval = payDetails.ZerliCoins - currentOrder.getTotalPrice();
    			ClientApp.ProtocolHandler.Invoke(RequestType.UpdateZerliCoins, newval, null, false);
    		}
    	}
    	currentOrder.setStatus(OrderStatus.pending_confirm);
    	ClientApp.ProtocolHandler.Invoke(RequestType.InsertOrder, currentOrder, null, false);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerOutroViewWindow.fxml");
    }
    
    public boolean validateInput()
    {
    	StringBuilder error = new StringBuilder("");
    	var creds = new String[] {cardNumTxt1.getText(),cardNumTxt2.getText(),cardNumTxt3.getText(),cardNumTxt4.getText()};
    	boolean valid = true;
    	if(paymentMethod.getSelectedToggle() == null)
    	{
    		error.append("Please select payment method\n");
    		valid = false;
    		return valid;
    	}
    	if(creditCardDialog.isVisible())
    	{
	    	for(String s : creds)
	    	{
	    		if(s.length() != 4)
	    		{
	    			error.append("Please enter valid credit card\n");
	    			valid = false;
	    			break;
	    		}
	    		try
	    		{
	    			Integer.parseInt(s);
	    		}
	    		catch(NumberFormatException e)
	    		{
	    			error.append("Please enter valid credit card\n");
	    			valid = false;
	    			break;
	    		}
	    	}
	    	if(cvvText.getText().equals("") || cvvText.getText().length() < 3)
	    	{
	    		error.append("CVV Cannot be empty\n");
	    		valid = false;
	    	}
	    	if(holderID.getText().equals(""))
	    	{
	    		error.append("Please enter holder ID\n");
	    		valid = false;
	    	}
	    	if(yearCB.getValue() == null || expMonthCB.getValue() == null)
	    	{
	    		error.append("Please enter a valid expiration date\n");
	    		valid = false;
	    	}
	    	errorLabel.setText(error.toString());
	    	return valid;
    	}
    	else
    	{
    		return valid;
    	}
    }

	@Override
	public void onEnter() {
		/* load user status */
		ClientApp.ProtocolHandler.Invoke(RequestType.GetUserCurrency,null ,null,true);
		payDetails = (paymentInfo)ClientApp.ProtocolHandler.GetResponse(RequestType.GetUserCurrency);
		isShipping = (boolean)LoginController.windowControl.getPipe("isShipping");
		currentOrder = (Order)LoginController.windowControl.getPipe("orderInfo");
    	int netPrice = (int) LoginController.windowControl.getPipe("totalCost");
    	int discount = payDetails.newUser ? (int)(0.1f * netPrice) : 0;
    	int shipping = isShipping ? 15 : 0;
    	currentOrder.setTotalPrice(netPrice - discount + shipping);
    	
    	/** credit card view **/
    	if(!save)
    	{
			var currentYear = LocalDateTime.now().toLocalDate().getYear();
			var years = new ArrayList<String>();
			var months = new ArrayList<String>();
			for(var i = currentYear;i < currentYear + 12;i++)
				years.add(i + "");
			for(var i = 0;i<12;i++)
				months.add((i+"").length() == 1 ? ("0"+ i) : i + "");
			yearCB.setItems(FXCollections.observableArrayList(years));
			expMonthCB.setItems(FXCollections.observableArrayList(months));
			cardNumTxt1.setText(payDetails.creditPhrases[0]);
			cardNumTxt2.setText(payDetails.creditPhrases[1]);
			cardNumTxt3.setText(payDetails.creditPhrases[2]);
			cardNumTxt4.setText(payDetails.creditPhrases[3]);
			cvvText.setText(payDetails.CVV);
			yearCB.getSelectionModel().select(payDetails.expirationYear);
			expMonthCB.getSelectionModel().select(payDetails.expirationMonth);
    	}
		/** zerli coins view **/	
		balanceLbl.setText(payDetails.ZerliCoins + " ZCoins");
		postPayBalanceLbl.setText("" + (payDetails.ZerliCoins - currentOrder.getTotalPrice()));

		/** summary **/

		if(isShipping)
			w.setText("15 ILS");
		else
			w.setText("-");
		
    	netPriceLabel.setText("" + netPrice);
		discountPercentageLabel.setText(payDetails.newUser ? "-10%" : "-");
		totalPriceLabel.setText("" + currentOrder.getTotalPrice());
		if(payDetails.ZerliCoins < currentOrder.getTotalPrice())
			zerliCoinsRB.setDisable(true);
		paymentMethod.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(!newValue.equals(oldValue))
				{
					if(newValue.equals(creditCardRB))
					{
						creditCardDialog.setVisible(true);
						zerliCoinsDialog.setVisible(false);
					}
					else
					{

						creditCardDialog.setVisible(false);
						zerliCoinsDialog.setVisible(true);
					}
				}
				
			}
		});
		if(!save)
		{
			paymentMethod.selectToggle(creditCardRB);
			holderID.clear();
		}
		
	}
	boolean back = false;
    @FXML
    void backBtn_press(ActionEvent event) {
    	back = true;
		LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerOrderInformation.fxml");
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
			else save = true;
		});
		back = false;
		
	}
	private void clearAll() {
		save = false;
		
	}

}
