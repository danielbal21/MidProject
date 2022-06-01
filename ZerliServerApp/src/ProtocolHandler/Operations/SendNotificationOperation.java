package ProtocolHandler.Operations;

import javax.swing.text.AbstractDocument.Content;

import Entities.NotificationInTable;
import Entities.Survey;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class SendNotificationOperation implements IOperation {
	@Override
	public boolean Perform(String requestee, Object data, Object params) {

		if(data instanceof NotificationInTable)
		{
			NotificationInTable notification = (NotificationInTable)data;
			Server.SqlServerManager.SendNotification(notification);
			return true;
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}
}
