package ProtocolHandler.Operations;

import java.util.ArrayList;
import Entities.*;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetAllCustomersInfoOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<AccountInfo> Customers = new ArrayList<>();
	 	Server.SqlServerManager.getAllCustomersInfo(Customers);
	    response.SetResponse(Customers);
		return true;
	}

}
