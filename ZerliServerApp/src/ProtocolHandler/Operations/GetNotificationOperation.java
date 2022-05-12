package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.NotificationInTable;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

public class GetNotificationOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<NotificationInTable> notificationList=new ArrayList<NotificationInTable>();
		server.Server.SqlServerManager.getNotification(requestee,notificationList);
		response.SetResponse(notificationList);
		return true;
	}

}
