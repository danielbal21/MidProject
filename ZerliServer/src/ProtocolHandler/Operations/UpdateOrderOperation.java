package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class UpdateOrderOperation implements IOperation {

	/**
	 * @params - data = Order instance
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof Order) {
		    Server.SqlServerManager.UpdateOrder_ColorAndDate_ByNumber((Order)data);
		    return true;
		}
		return true;		
    }

	
	
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
