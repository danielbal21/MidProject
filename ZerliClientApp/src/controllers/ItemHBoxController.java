package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ItemHBoxController extends HBox {
	private int counter=0;
	private int id;
    public ItemHBoxController() {
    	super();
	
	}
    public void init(int id,String name,String costLabel,Image image) 
    {
    	this.id=id;
    	CostLabel.setText(costLabel+ " ₪");
		nameLabel.setText(name);
		itemImage.setImage(image);
    }
    @FXML
    private Label nameLabel;
    
	@FXML
    private Label CostLabel;
	
	@FXML
	private ImageView itemImage;
	
    @FXML
    private VBox V;

    @FXML
    private Button addToCartBtn;

    @FXML
    private ImageView plusBtn;

    @FXML
    private Label quntityLabel;

    @FXML
    void AddToCart(ActionEvent event) {
   
    }

    @FXML
    void MinusBtnPressed(MouseEvent event) {
    	if(counter>0)
    	{
    		counter--;
    		quntityLabel.setText(String.valueOf(counter));
    	}
    }

    @FXML
    void PlusBtnPressed(MouseEvent event) {
     	counter++;
    	quntityLabel.setText(String.valueOf(counter));
    }

}

