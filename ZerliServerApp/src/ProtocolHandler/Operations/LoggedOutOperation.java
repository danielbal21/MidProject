package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class LoggedOutOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		Server.SqlServerManager.LoggedOut(requestee);
		System.out.println("logOut: " + requestee);
		return true;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
