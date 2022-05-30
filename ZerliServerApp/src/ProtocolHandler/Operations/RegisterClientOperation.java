package ProtocolHandler.Operations;

import Entities.*;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class RegisterClientOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof PendingClientInfo && params instanceof String) {
			String userID = (String)params;
			PendingClientInfo clientInfo = (PendingClientInfo)data;
			Server.SqlServerManager.ActivateClient(clientInfo,userID);
			return true; 
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
