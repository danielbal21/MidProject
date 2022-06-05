package ProtocolHandler.Operations;

import java.util.ArrayList;
import Entities.*;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

// TODO: Auto-generated Javadoc
/**
 * The Class GetAllCustomersInfoOperation defines the operation
 * that is invoked upon an GetAllCustomersInfoOperation Request.
 */
public class GetAllCustomersInfoOperation implements IOperation{

	/**
	 * This perform is not used since the operation is an Operate&Respond request.
	 *
	 * @param requestee the requestee
	 * @param data the data
	 * @param params the params
	 * @param response the response
	 * @return true, if successful
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	/**
	 * The perform operation is the method that is being invoked when the matching request is made
	 * all database calls are managed here, along with some processing and input validation.
	 *
	 * @param requestee - the request sender
	 * @param data - the data that was sent
	 * @param params - the parameters constraints that were pinned to the data
	 * @return true, if successful
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<AccountInfo> Customers = new ArrayList<>();
	 	Server.SqlServerManager.getAllCustomersInfo(Customers);
	    response.SetResponse(Customers);
		return true;
	}

}
