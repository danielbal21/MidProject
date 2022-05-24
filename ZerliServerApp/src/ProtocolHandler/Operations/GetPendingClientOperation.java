package ProtocolHandler.Operations;

import Entities.PendingClientInfo;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetPendingClientOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		if(params instanceof String) {
			String ID = (String)params;
		    response.SetResponse(Server.SqlServerManager.GetPendingClient(ID));
		    return true;
		}
		else return false;
	}

}
