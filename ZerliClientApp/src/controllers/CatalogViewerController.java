package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Entities.CatalogType;
import Entities.Item;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class CatalogViewerController implements UserControl {

	private ObservableList<Item> itemList;
	private Map< Integer ,ArrayList<Item>> catalogPages;
	
    private CatalogType catalogType;
	
    @FXML
    private GridPane GridPane;
    
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Label catalogNameLbl;
    
    @FXML
    private Label currentPage;

    @FXML
    private Label maxPages;
    
    
    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerHomePage.fxml");
    }
    
    @FXML
    void browseLeft(ActionEvent event) {
    	if(currentPage.getText().equals("1")) return;
    	else {
     		ShowPage(Integer.parseInt(currentPage.getText())-1);
   		 currentPage.setText(String.valueOf(Integer.parseInt(currentPage.getText())-1));

    	}

    }

    @FXML
    void browseRight(ActionEvent event) {
    	if(currentPage.getText().equals(maxPages.getText())) return;
    	else {
    		ShowPage(Integer.parseInt(currentPage.getText())+1);
    	    currentPage.setText(String.valueOf(Integer.parseInt(currentPage.getText())+1));

    	}
    }
    
	@Override
	public void onEnter() {	
		catalogType = (CatalogType) LoginController.windowControl.peekPipe("catalog");
		ClientApp.ProtocolHandler.Invoke(RequestType.GetCatalog, null,LoginController.windowControl.peekPipe("catalog"), true);
		itemList=(ObservableList<Item>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetCatalog);
	
		if(catalogType== CatalogType.custom) {
			catalogNameLbl.setText("Custom");
		}
		else {
			catalogNameLbl.setText("Pre-defined");
		}
		
		int maxpages = ((itemList.size())/(GridPane.getColumnCount()*GridPane.getRowCount()));
		if((itemList.size())%(GridPane.getColumnCount()*GridPane.getRowCount())!=0) maxpages++;
		
		maxPages.setText(String.valueOf(maxpages));
		currentPage.setText("1");
		
		catalogPages = new HashMap<>();
		int currentpages = 1;
		int columnIndex=0,rowIndex=0;
		
		for (Item item : itemList) {
			
			if (columnIndex==GridPane.getColumnCount())
			{
				columnIndex=0;
				rowIndex++;
			}
			if (rowIndex==GridPane.getRowCount())
			{
				rowIndex=0;
				columnIndex=0;
				currentpages++;
			}
			
			if(!catalogPages.containsKey(currentpages)) {
				ArrayList<Item> newPage = new ArrayList<Item>();
				newPage.add(item);
				catalogPages.put(currentpages, newPage);
			}
			else {
				ArrayList<Item> samePage = catalogPages.get(currentpages);
				catalogPages.remove(currentpages);
				samePage.add(item);
				catalogPages.put(currentpages, samePage);
			}
			
			columnIndex++;
		}	
		
		ShowPage(1);
	}
	
	@Override
	public void onExit() {
		GridPane.getChildren().clear();
	}
	
	public void ShowPage(int num) {
		GridPane.getChildren().clear();
		
		ArrayList<Item> page = catalogPages.get(num);
		int columnIndex=0,rowIndex=0;
		for (Item item : page) {
		
			if (columnIndex==GridPane.getColumnCount())
			{
				columnIndex=0;
				rowIndex++;
			}
			if (rowIndex==GridPane.getRowCount())
			{
				break;
			}
			
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/components/ItemHBox.fxml"));
			Parent root;
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
	
}

