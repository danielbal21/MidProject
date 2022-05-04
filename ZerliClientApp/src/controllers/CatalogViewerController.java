
package controllers;
import Entities.CatalogType;
import Entities.Item;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CatalogViewerController {

    @FXML
    private GridPane GridPane;
    private ObservableList<Item> itemList;
    private CatalogType catalogType;
    @FXML
    private AnchorPane activePanelContainer;
	public CatalogViewerController() {
		
		ClientApp.ProtocolHandler.Invoke(RequestType.GetCatalog, catalogType, null, true);
		itemList=(ObservableList<Item>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetCatalog);
		for (Item item : itemList) {
			HBox itemHBoxController=new ItemHBoxController(item.getId(),item.getName(),String.valueOf(item.getPrice()) ,new Image("sds"));
			GridPane.getChildren().add(itemHBoxController);
		}
			
	}
	public void setCatalogType (CatalogType catalogType) 
	{
		this.catalogType=catalogType;
	}
}

