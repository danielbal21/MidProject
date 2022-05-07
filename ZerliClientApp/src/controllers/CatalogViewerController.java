package controllers;

import java.io.IOException;
import Entities.CatalogType;
import Entities.Item;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CatalogViewerController implements UserControl {

	private ObservableList<Item> itemList;
    private CatalogType catalogType;
	
    @FXML
    private GridPane GridPane;
    
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Label catalogNameLbl;
    
	@Override
	public void onEnter() {
		int columnIndex=0,rowIndex=0;
		catalogType = (CatalogType) LoginController.windowControl.peekPipe("catalog");
		ClientApp.ProtocolHandler.Invoke(RequestType.GetCatalog, null,LoginController.windowControl.getPipe("catalog"), true);
		itemList=(ObservableList<Item>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetCatalog);
		
		if(catalogType== CatalogType.custom) {
			catalogNameLbl.setText("Custom");
		}
		else {
			catalogNameLbl.setText("Pre-defined");
		}
		
		for (Item item : itemList) {
			System.out.println(item.getId());
			if (columnIndex==GridPane.getColumnCount())
			{
				columnIndex=0;
				rowIndex++;
			}
			if (rowIndex==GridPane.getRowCount())
			{
				rowIndex=0;
				//page++
			}
			FXMLLoader loader=new FXMLLoader();
			HBox root;
			loader.setLocation(getClass().getResource("/gui/components/ItemHBox.fxml"));
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			ItemHBoxController itemHBoxController=loader.getController();
			itemHBoxController.init(item.getId(),item.getName(),String.valueOf(item.getPrice()) ,item.getJXImage());
			GridPane.add(root, columnIndex++, rowIndex);
		}	
		
	}
	
	@Override
	public void onExit() {
		GridPane.getChildren().clear();
	}
}

