package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.NotificationInTable;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import javafx.collections.ObservableList;

public class UpdateNotificationOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if (data instanceof ArrayList<?>)
			server.Server.SqlServerManager.UpdateNotification(requestee,(ArrayList<NotificationInTable>)data);
		return true;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
		return false;
	}

}