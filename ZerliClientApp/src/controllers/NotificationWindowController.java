package controllers;

import java.awt.desktop.ScreenSleepEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.NotificationInTable;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class NotificationWindowController implements UserControl,Initializable {
	
	private ObservableList<NotificationInTable> notificationTableList;
    private boolean unreadNotification=true;
	@FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Label notificationTable;
    @FXML
    private Label noNotifiLabel;
    

    @FXML
    private Label allreadyReadLabel;
    
    @FXML
    private TableView<NotificationInTable> notificationsCenterTable;
    @FXML
    private TableColumn<NotificationInTable, String> content;
    @FXML
    private TableColumn<NotificationInTable, String> sendFrom;
    @FXML
    private TableColumn<NotificationInTable, Integer> notificationNumber;
    @FXML
    private TableColumn<NotificationInTable, String> status;
    
    @FXML
    private ToggleButton ureadOnlyBtn;
    
   
    @FXML
    void Unreadonly(MouseEvent event) {
    	
    	if (unreadNotification)
    	{
    		unreadNotification=false;
    		ureadOnlyBtn.setText("All notification");
    		ObservableList<NotificationInTable> list=createUnreadNotificationList();
    		if(list.size()>0)
    		setTable(list);
    	}
    	else
    	{
    		unreadNotification=true;
    		ureadOnlyBtn.setText("Unread only");
		 	setTable(notificationTableList); 
    	}
    }

	@Override
	public void onEnter()
	{
		allreadyReadLabel.setVisible(false);
		ClientApp.ProtocolHandler.Invoke(RequestType.GetNotification, null, null, true);
		notificationTableList= (ObservableList<NotificationInTable>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetNotification);
		setTable(notificationTableList);
	}
	private void setTable(ObservableList<NotificationInTable> list)
	{
		noNotifiLabel.setVisible(false);
		notificationNumber.setCellValueFactory(new PropertyValueFactory<NotificationInTable, Integer>("notificationnumber"));
		sendFrom.setCellValueFactory(new PropertyValueFactory<NotificationInTable, String>("from"));
		content.setCellValueFactory(new PropertyValueFactory<NotificationInTable, String>("content"));
		status.setCellValueFactory(new PropertyValueFactory<NotificationInTable, String>("status"));
		if(list.size()<1)
	    	   noNotifiLabel.setVisible(true);
		notificationsCenterTable.setItems(list);
	}
	
	@Override
	public void onExit() {
		ArrayList<NotificationInTable>list=new ArrayList<NotificationInTable>();
		for (NotificationInTable notificationInTable : notificationTableList) {
			list.add(notificationInTable);
		}
		ClientApp.ProtocolHandler.Invoke(RequestType.UpdateNotification, list, null, false);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
    @FXML
    void setStatus(MouseEvent event) {
    	
    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
    		NotificationInTable temp=notificationsCenterTable.getSelectionModel().getSelectedItem();
    		if(temp.getStatus().equals("unread"))
    		{
    			allreadyReadLabel.setVisible(false);
    			temp.setStatus("read");
    		}
    		else {
    			allreadyReadLabel.setVisible(true);
    		
    			Thread thread =new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						allreadyReadLabel.setVisible(false);
					}
				});
    			thread.start();
			}
    		
			setTable(notificationTableList);
			notificationsCenterTable.refresh();
		}
    }
    private ObservableList<NotificationInTable> createUnreadNotificationList()
    {
    	ObservableList<NotificationInTable> unreadNotificationTableList= FXCollections.observableArrayList();
		    	for (NotificationInTable notificationInTable : notificationTableList) {
					if(notificationInTable.getStatus().equals("unread"))
						unreadNotificationTableList.add(notificationInTable);
	    	}
    	return unreadNotificationTableList;
    }
}
