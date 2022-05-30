package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

/**
 * The Class UpdateOrderOperation defines the operation
 * that is invoked upon an UpdateOrder Request.
 */
public class UpdateOrderOperation implements IOperation {

	/**
	 * The perform operation is the method that is being invoked when the matching request is made
	 * all database calls are managed here, along with some processing and input validation
	 *
	 * @param requestee - the request sender
	 * @param data - the data that was sent
	 * @param params - the parameters constraints that were pinned to the data
	 * @return true, if successful
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

	
	
	/**
	 * This perform is not used since the operation is an Operate-Only request
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
