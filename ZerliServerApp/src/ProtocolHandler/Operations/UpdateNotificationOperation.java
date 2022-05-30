package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.NotificationInTable;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import javafx.collections.ObservableList;

/**
 * The Class UpdateNotificationOperation defines the operation
 * that is invoked upon an UpdateNotification Request.
 */
public class UpdateNotificationOperation implements IOperation {

	/**
	 * The perform operation is the method that is being invoked when the matching request is made
	 * all database calls are managed here, along with some processing and input validation
	 *
	 * @param requestee - the request sender
	 * @param data - the data that was sent
	 * @param params - the parameters constraints that were pinned to the data
	 * @return true, if successful
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if (data instanceof ArrayList<?>)
			server.Server.SqlServerManager.UpdateNotification(requestee,(ArrayList<NotificationInTable>)data);
		return true;
	}

	/**
	 * This perform is not used since the operation is an Operate-Only request
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
		return false;
	}

}