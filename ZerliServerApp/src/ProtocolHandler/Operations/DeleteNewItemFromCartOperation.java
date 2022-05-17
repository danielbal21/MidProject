package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

public class DeleteNewItemFromCartOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if (data instanceof Integer)
		{
			server.Server.SqlServerManager.DeleteNewItemFromCart(requestee,(int)data);
			return true;
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
