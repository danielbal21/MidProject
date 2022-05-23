package ProtocolHandler.Operations;

import Entities.Complaint;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class MakeComplaintOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
	
		boolean b= Server.SqlServerManager.MakeComplaint((Complaint)data);
		response.SetResponse(b);
		return true;
	}

}
