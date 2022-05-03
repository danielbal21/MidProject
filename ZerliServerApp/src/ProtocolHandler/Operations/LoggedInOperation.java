package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class LoggedInOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		String loggedIn = Server.SqlServerManager.LoggedIn(requestee);
		response.SetResponse(loggedIn);
		System.out.println("sent: " + loggedIn);
		return true;
	}

}
