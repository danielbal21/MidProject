package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Complaint;
import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetComplaintsOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
	    Server.SqlServerManager.GetComplaints(complaints,requestee);
	    response.SetResponse(complaints);
		return true;
	}

}
