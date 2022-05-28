package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.AccountInfo;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetAllEmployeesInfoOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<AccountInfo> employees = new ArrayList<>();
	 	Server.SqlServerManager.getAllEmployeesInfo(employees,requestee);
	    response.SetResponse(employees);
		return true;
	}

}
