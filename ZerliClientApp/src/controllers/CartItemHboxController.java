package controllers;

import Entities.CatalogType;
import Entities.ItemType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CartItemHboxController extends HBox{

    @FXML
    private Label catalogTypeLabel;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemTypeLabel;

    @FXML
    private Label quntityLabel;
    
    private int id;
    public CartItemHboxController(int id,String name,ItemType itemType,CatalogType catalogType,int quntity) {
	super();
	this.id=id;
	itemNameLabel.setText(name);
	itemTypeLabel.setText(itemType.toString());
	catalogTypeLabel.setText(catalogType.toString());
	quntityLabel.setText(String.valueOf(quntity));
}
    @FXML
    void DeleteBtnPressed(ActionEvent event) {
    	quntityLabel.setText("0");
    }

}
