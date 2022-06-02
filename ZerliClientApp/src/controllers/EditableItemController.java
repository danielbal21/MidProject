package controllers;

import java.util.Optional;

import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class EditableItemController {

	private int id;
	
    @FXML
    private ImageView Edit;

    @FXML
    private ImageView Remove;

    @FXML
    private Rectangle imageRec;

    @FXML
    private Label nameLabel;

    @FXML
    void EditPressed(MouseEvent event) {
    	LoginController.windowControl.putPipe("currentEdited", id);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ItemEditor.fxml");
    }

    @FXML
    void RemovePressed(MouseEvent event) {
		Alert confirmAlert = new Alert(AlertType.NONE);
		confirmAlert.setTitle("Removing item");
		confirmAlert.setContentText("Are you sure you want to remove "
		+ nameLabel.getText() + " ?\n(This action is ireversible)");
		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
		ButtonType no = new ButtonType("No",ButtonData.NO);
		confirmAlert.getDialogPane().getButtonTypes().add(yes);
		confirmAlert.getDialogPane().getButtonTypes().add(no);
		Optional<ButtonType> result = confirmAlert.showAndWait();
		result.ifPresent(response -> { 
			if(response == yes) 
			{
				ClientApp.ProtocolHandler.Invoke(RequestType.RemoveItemByID, null, id, false);
				LoginController.windowControl.Refresh();
			}
		});
    }
    public void init(int id,String name,Image image) 
    {
    	this.id=id;
		nameLabel.setText(name);
		imageRec.setFill(new ImagePattern(image));
    }
}
