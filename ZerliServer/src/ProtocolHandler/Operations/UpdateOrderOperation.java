package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class UpdateOrderOperation implements IOperation {

	/**
	 * @params - data = String[2]:[color,date]
	 * @params - params = String:orderNumber
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof String[] && params  instanceof String) {
			String[] stringData = (String[])data;
		    Server.SqlServerManager.UpdateOrder_ColorAndDate_ByNumber((String)params,stringData[0],stringData[1]);
			return true;	
		}
		return false;	
    }

	
	
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
