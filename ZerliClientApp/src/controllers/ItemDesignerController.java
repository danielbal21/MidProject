package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import Entities.CatalogType;
import Entities.Color;
import Entities.Item;
import Entities.ItemType;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class ItemDesignerController implements UserControl {
	ArrayList<String> colors = new ArrayList<String>();
	ArrayList<String> item_types = new ArrayList<String>();
	FileInputStream imagedata = null;
	private Item newItem;
	
	@FXML
	private Label errorLabel;
	
    @FXML
    private ImageView ItemImage;
  
    @FXML
    private Label currency;
   
    @FXML
    private RadioButton ItemRB;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Button addBtn;

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
    private Button selectImage;

    @FXML
    void addBtn_Click(ActionEvent event) {
    	byte[] buf = null;
    	if(!inputValid()) return;
    	if(ItemImage.getImage() != null)
    	{
    		try {
				buf = imagedata.readAllBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		//ItemImage.getImage().getPixelReader().getPixels(0, 0, (int)ItemImage.getImage().getWidth(), (int)ItemImage.getImage().getHeight(), PixelFormat.getByteBgraInstance(), buf, 0, (int)ItemImage.getImage().getWidth() * 4);
    	}
    	else
    		buf = null;
    	CatalogType ctype;
    	if(catalogTypes.getSelectedToggle().equals(ItemRB))
    	{
    		ctype = CatalogType.custom;
    	}
    	else
    	{
    		ctype = CatalogType.pre_define;
    	}
    	newItem = new Item(0, name.getText(), Integer.parseInt(price.getText()), ctype, Utilities.GenericUtilties.StringToItemType(itemType.getValue()),Utilities.GenericUtilties.StringToColor(itemColor.getValue()), buf,onSaleCB.isSelected(),  Integer.parseInt(salePrice.getText()));
    	ClientApp.ProtocolHandler.Invoke(RequestType.AddItem, newItem, null, false);
    	cancelBtn_press(null);
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
    	try {
			Integer.parseInt(salePrice.getText());
		} catch (Exception e) {
    		error.append("Sale Price is invalid!\n");
    		valid = false;
		}
    	errorLabel.setText(error.toString());
		return valid;
	}

	@FXML
    void cancelBtn_press(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/MarketingCatalogEditor.fxml");
    }
    @FXML
    void selectimage_cllick(ActionEvent event) {
    	FileChooser imageChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
    	imageChooser.getExtensionFilters().add(extFilterPNG);
        //Show open file dialog
        File file = imageChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            ItemImage.setImage(image);
            try {
				imagedata = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        }
    }
	@Override
	public void onEnter() {
		currency.setText(Utilities.Constants.SHEKEL);
		 //salePrice.disableProperty().bind(onSaleCB.selectedProperty().not());
		 for(Color c : Color.values())
			 colors.add(Utilities.GenericUtilties.ColorToString(c));
		 for(ItemType it : ItemType.values())
			 item_types.add(Utilities.GenericUtilties.ItemTypeToString(it));
		 itemType.setItems(FXCollections.observableArrayList(item_types));
		 itemColor.setItems(FXCollections.observableArrayList(colors));
	}

	@Override
	public void onExit() {}

}
