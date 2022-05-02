package ProtocolHandler.Operations;

import Entities.Roles;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.ServerApp;
import server.Server;

public class AuthenticationOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		if(params instanceof String[])
		{
			String creds[] = (String[])params;
			Roles role = Server.SqlServerManager.Authenticate(creds[0],creds[1]);
			response.SetResponse(role);
			System.out.println("sent: " + role.toString());
			return true;
		}
		else
			return false;
	}

}
