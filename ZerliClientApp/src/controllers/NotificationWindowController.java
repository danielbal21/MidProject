package controllers;

import java.util.Comparator;

import Entities.NotificationInTable;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class NotificationWindowController implements UserControl {
	
	private ObservableList<NotificationInTable> AllnotificationTableList;
	private ObservableList<NotificationInTable> unreadNotificationTableList;
    private boolean allNotificationsShow;
    Thread thread;
    
    @FXML
    private Label ErrorLabel;

    @FXML
    private ToggleButton NotificationsStatusBtn;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private TableColumn<NotificationInTable, String> content;

    @FXML
    private TableColumn<NotificationInTable, Integer> notificationNumber;

    @FXML
    private TableView<NotificationInTable> notificationsCenterTable;

    @FXML
    private TableColumn<NotificationInTable, String> sendFrom;

    @FXML
    private TableColumn<NotificationInTable, String> status;
    
    @FXML
    private Label changeNotificationsLbl;
    
    
    private ObservableList<NotificationInTable> createUnreadNotificationList()
    {
    	ObservableList<NotificationInTable> unreadAllnotificationTableList= FXCollections.observableArrayList();
		for (NotificationInTable notificationInTable : AllnotificationTableList) {
			if(notificationInTable.getStatus().equals("unread"))
					unreadAllnotificationTableList.add(notificationInTable);
		}
    	return unreadAllnotificationTableList;
    }


    @FXML
    void setStatus(MouseEvent event) {
    	
    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
    		NotificationInTable temp = notificationsCenterTable.getSelectionModel().getSelectedItem();
    		if(temp.getStatus().equals("unread"))
    		{
    			int num = temp.getNotificationnumber();
    		    ClientApp.ProtocolHandler.Invoke(RequestType.UpdateNotification, num, null, false);
    			temp.setStatus("read");
    		}
			notificationsCenterTable.refresh();
		}
    }
    
    @FXML
    void NotificationsStatusPressed(ActionEvent event) {
      	if (allNotificationsShow)
    	{
  			allNotificationsShow=false;
  			NotificationsStatusBtn.setText("Show All Notifications");
  			changeNotificationsLbl.setText("Show Unread Notifications");
        	notificationsCenterTable.setItems(unreadNotificationTableList);
        	if(unreadNotificationTableList.size() == 0) {
        		ErrorLabel.setText("There are no new notifications");
        		ErrorLabel.setVisible(true);
    		}
    	}
    	else
    	{
    		ErrorLabel.setVisible(false);
    		allNotificationsShow=true;
    		changeNotificationsLbl.setText("Show All Notifications");
        	NotificationsStatusBtn.setText("Show Unread Notifications");
        	notificationsCenterTable.setItems(AllnotificationTableList);
    	}
    }
    @FXML
    void refreshPressed(MouseEvent event) {
    	onExit();
    	onEnter();
    }
    
    @Override
	public void onEnter()
	{
    	allNotificationsShow = true;
    	notificationNumber.setCellValueFactory(new PropertyValueFactory<NotificationInTable, Integer>("notificationnumber"));
		sendFrom.setCellValueFactory(new PropertyValueFactory<NotificationInTable, String>("from"));
		content.setCellValueFactory(new PropertyValueFactory<NotificationInTable, String>("content"));
		status.setCellValueFactory(new PropertyValueFactory<NotificationInTable, String>("status"));
		AllnotificationTableList = (ObservableList<NotificationInTable>) LoginController.windowControl.peekPipe("All Notification");
    	notificationsCenterTable.setItems(AllnotificationTableList);
    	
		thread =new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					AllnotificationTableList = (ObservableList<NotificationInTable>) LoginController.windowControl.peekPipe("All Notification");
					unreadNotificationTableList = createUnreadNotificationList();
					
					if(allNotificationsShow) {
				    	notificationsCenterTable.setItems(AllnotificationTableList);

					}
					else {
				    	notificationsCenterTable.setItems(unreadNotificationTableList);
					}
				}
			}
		});
		thread.start();
		ErrorLabel.setVisible(false);
    	changeNotificationsLbl.setText("Show All Notifications");
    	NotificationsStatusBtn.setText("Show Unread Notifications");
	}
	
	@Override
	public void onExit() {	
		thread.interrupt();
		notificationsCenterTable.getItems().clear();

	}
}
