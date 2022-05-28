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

	// data - branch
	// params manager/delivery
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		if(data instanceof String && params instanceof String) {
			ArrayList<Order> Orders = new ArrayList<Order>();
		    Server.SqlServerManager.GetOrdersByBranch(Orders, (String)data, (String)params);
		    response.SetResponse(Orders);
			return true;
		}
		return false;
	}

}
