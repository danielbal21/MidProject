package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class RemoveItemOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		Server.SqlServerManager.removeItemByID((int)params);
		return true;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
		return false;
	}

}
