package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Entities.CatalogType;
import Entities.Color;
import Entities.Item;
import Entities.ItemType;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ItemEditorController implements UserControl {
	ArrayList<String> colors = new ArrayList<String>();
	ArrayList<String> item_types = new ArrayList<String>();
	private Item currentEdited;
	
	@FXML
	private Label errorLabel;
	
    @FXML
    private ImageView ItemImage;

    @FXML
    private RadioButton ItemRB;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Button cancelBtn;

    @FXML
    private ToggleGroup catalogTypes;

    @FXML
    private ComboBox<String> itemColor;

    @FXML
    private ComboBox<String> itemType;

    @FXML
    private TextField name;

    @FXML
    private CheckBox onSaleCB;

    @FXML
    private TextField price;

    @FXML
    private RadioButton productRB;

    @FXML
    private TextField salePrice;

    @FXML
    private Button updateBtn;

    @FXML
    void cancelBtn_press(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/MarketingCatalogEditor.fxml");
    }
    private boolean inputValid() {
    	StringBuilder error = new StringBuilder("");
    	boolean valid = true;
    	if(name.getText().equals(""))
    	{
    		error.append("Name cannot be empty!\n");
    		valid = false;
    	}
    	try {
			Integer.parseInt(price.getText());
		} catch (Exception e) {
    		error.append("Price is invalid!\n");
    		valid = false;
		}
    	if(catalogTypes.getSelectedToggle() == null)
    	{
    		error.append("Please select Catalog Type!\n");
    		valid = false;
    	}
    	if(itemType.getValue() == null)
    	{
    		error.append("Please select Item Type!\n");
    		valid = false;
    	}
    	if(itemColor.getValue() == null)
    	{
    		error.append("Please select Item Type!\n");
    		valid = false;
    	}
    	if(onSaleCB.isSelected())
    	{
        	try {
    			Integer.parseInt(salePrice.getText());
    		} catch (Exception e) {
        		error.append("Sale Price is invalid!\n");
        		valid = false;
    		}
    	}
    	errorLabel.setText(error.toString());
		return valid;
	}
    @FXML
    void updateBtn_Click(ActionEvent event) {
    	if(!inputValid()) return;
    	CatalogType ctype;
    	if(catalogTypes.getSelectedToggle().equals(ItemRB))
    	{
    		ctype = CatalogType.custom;
    	}
    	else
    	{
    		ctype = CatalogType.pre_define;
    	}
    	currentEdited.setName(name.getText());
    	currentEdited.setPrice(Integer.parseInt(price.getText()));
    	currentEdited.setItemType(Utilities.GenericUtilties.StringToItemType(itemType.getValue()));
    	currentEdited.setCatalogType(ctype);
    	currentEdited.setColor(Utilities.GenericUtilties.StringToColor(itemColor.getValue()));
    	currentEdited.setOnSale(onSaleCB.isSelected());
    	currentEdited.setSalePrice(Integer.parseInt(salePrice.getText()));
    	ClientApp.ProtocolHandler.Invoke(RequestType.UpdateItem, currentEdited, null, false);
		 cancelBtn_press(null);
    }

	@Override
	public void onEnter() {
		 int id = (int) LoginController.windowControl.getPipe("currentEdited");
		 ClientApp.ProtocolHandler.Invoke(RequestType.GetItemByID, null, id, true);
		 currentEdited = (Item)ClientApp.ProtocolHandler.GetResponse(RequestType.GetItemByID);
		 salePrice.disableProperty().bind(onSaleCB.selectedProperty().not());
		 ItemImage.setImage(currentEdited.getJXImage());
		 for(Color c : Color.values())
			 colors.add(Utilities.GenericUtilties.ColorToString(c));
		 for(ItemType it : ItemType.values())
			 item_types.add(Utilities.GenericUtilties.ItemTypeToString(it));
		 itemType.setItems(FXCollections.observableArrayList(item_types));
		 itemColor.setItems(FXCollections.observableArrayList(colors));
		 itemColor.setValue(Utilities.GenericUtilties.ColorToString(currentEdited.getColor()));
		 itemType.setValue(Utilities.GenericUtilties.ItemTypeToString(currentEdited.getItemType()));
		 name.setText(String.valueOf(currentEdited.getName()));
		 price.setText(String.valueOf(currentEdited.getPrice()));
		 if(currentEdited.getCatalogType() == CatalogType.custom)
			 ItemRB.selectedProperty().set(true);
		 else
			 productRB.selectedProperty().set(true);
		 onSaleCB.setSelected(currentEdited.isOnSale());
		 salePrice.setText(String.valueOf(currentEdited.getSalePrice()));
	}

	@Override
	public void onExit() {
		
	}

}
