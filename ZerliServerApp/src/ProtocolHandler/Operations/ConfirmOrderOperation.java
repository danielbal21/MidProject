package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class ConfirmOrderOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		Order order = (Order)data;
		  Server.SqlServerManager.ConfirmOrder(Integer.valueOf(order.getOrderID()));
			return true;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
	  return false;
	}

}
