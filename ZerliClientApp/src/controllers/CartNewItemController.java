package controllers;

import Entities.*;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class CartNewItemController extends HBox {
	private int id;
	private int price;
	private int quntity;
	@FXML
	private Button deleteBtn;

	@FXML
	private HBox hboxRoot;

	@FXML
	private Circle circle;

	@FXML
	private Label itemNameLabel;

	@FXML
	private Label quntityLabel;

	private Label totalCost;

	private VBox Vbox;
	private NewItem newItem;

	@FXML
	void ViewNewItemComponents(ActionEvent event) {
		LoginController.windowControl.putPipe("newItemList", newItem);
		LoginController.windowControl.setUserControl("/gui/usercontrols/NewItemList.fxml");

	}

	public void init(Label totalCost, VBox Vbox, Image image1, int id, String name, int quntity, int price,
			NewItem newItem) {
		this.totalCost = totalCost;
		this.Vbox = Vbox;
		this.id = id;
		this.price = price;
		this.quntity = quntity;
		circle.setFill(new ImagePattern(image1));
		itemNameLabel.setText(newItem.getItemName());
		quntityLabel.setText(String.valueOf(quntity));
		this.newItem = newItem;
	}

	@FXML
	void DeleteBtnPressed(ActionEvent event) {
		Vbox.getChildren().remove(hboxRoot);
		ClientApp.ProtocolHandler.Invoke(RequestType.DeleteAllnewItemFromCart, id, null, true);
		totalCost.setText(
				String.valueOf((Integer) LoginController.windowControl.peekPipe("totalCost") - price * quntity));
		if ((boolean) ClientApp.ProtocolHandler.GetResponse(RequestType.DeleteAllnewItemFromCart)) {
			RedNotificationCircle cartLabelAndImage = (RedNotificationCircle) LoginController.windowControl
					.getPipe("cartLabel");
			int cartNotificationsNumber = cartLabelAndImage.getCartNotificationsNumber();
			cartNotificationsNumber = cartNotificationsNumber - quntity;
			cartLabelAndImage.setCartNotificationsNumber(cartNotificationsNumber);
			Label label;
			ImageView image = new ImageView();
			label = cartLabelAndImage.getLabel();
			image = cartLabelAndImage.getImage();
			if (cartNotificationsNumber == 0) {
				label.setVisible(false);
				image.setVisible(false);
			}
			label.setText(String.valueOf(cartNotificationsNumber));
			LoginController.windowControl.putPipe("cartLabel", cartLabelAndImage);
		}
		LoginController.windowControl.putPipe("totalCost", Integer.parseInt(totalCost.getText()));

	}
}
