package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class CancelOrderOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof Integer && params instanceof Integer ) {
			Server.SqlServerManager.cancelOrder((Integer)data,(Integer)params);
			return true;
		}
		else return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
