package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetOrdersByBranchOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<Order> Orders = new ArrayList<Order>();
	    Server.SqlServerManager.GetOrdersByBranch(Orders, (String) data);
	    response.SetResponse(Orders);
		return true;
	}

}
