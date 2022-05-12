package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

public class DeleteItemFromCartOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		System.out.println(data);
		if (data instanceof Integer)
		{
		server.Server.SqlServerManager.DeleteItemFromCart(requestee,(int)data);
		return true;
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
		return false;
	}

}
