package Entities;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class RedNotificationCircle implements Serializable {
private ImageView image;
private Label label;
private int NotificationsNumber;

public RedNotificationCircle(ImageView image, Label label, int NotificationsNumber) {
	super();
	this.image = image;
	this.label = label;
	this.NotificationsNumber = NotificationsNumber;
}
public ImageView getImage() {
	return image;
}
public void setImage(ImageView image) {
	this.image = image;
}
public Label getLabel() {
	return label;
}
public void setLabel(Label label) {
	this.label = label;
}
public int getCartNotificationsNumber() {
	return NotificationsNumber;
}
public void setCartNotificationsNumber(int NotificationsNumber) {
	this.NotificationsNumber = NotificationsNumber;
}

}
