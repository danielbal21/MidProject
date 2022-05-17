package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class EndOrderOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		Order order = (Order)data;
		  Server.SqlServerManager.EndOrder(Integer.valueOf(order.getOrderID()),(String)params);
			return true;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
	  return false;
	}

}
