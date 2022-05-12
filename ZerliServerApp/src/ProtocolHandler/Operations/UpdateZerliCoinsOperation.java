package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class UpdateZerliCoinsOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof Integer)
		{
			Server.SqlServerManager.UpdateZerliCoins(requestee,(int)data);
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
