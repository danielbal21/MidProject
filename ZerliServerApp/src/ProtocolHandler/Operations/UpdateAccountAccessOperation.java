package ProtocolHandler.Operations;

import Entities.*;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class UpdateAccountAccessOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof Access && params instanceof String) {
			String access = ((Access)data).toString();
			String userID = (String)params;
		    Server.SqlServerManager.UpdateAccountAccess(userID,access);
			return true;
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
