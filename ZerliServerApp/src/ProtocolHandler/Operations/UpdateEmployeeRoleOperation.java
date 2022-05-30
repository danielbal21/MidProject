package ProtocolHandler.Operations;

import Entities.*;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class UpdateEmployeeRoleOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof Roles && params instanceof String) {
			String role = ((Roles)data).toString();
			String userID = (String)params;
		    Server.SqlServerManager.UpdateEmployeeRole(userID,role);
			return true;
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
