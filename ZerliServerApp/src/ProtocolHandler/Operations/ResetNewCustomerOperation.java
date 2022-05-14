package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class ResetNewCustomerOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		Server.SqlServerManager.resetNewCustomer(requestee);
		return true;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
