package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

public class DeleteItemFromCartOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		if (data instanceof Integer)
		{
		server.Server.SqlServerManager.DeleteItemFromCart(requestee,(int)data);
		response.SetResponse(true);
		return true;
		}
		return false;
	}

}
