package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Complaint;
import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class ComplaintResponseOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		Complaint complaint = (Complaint)data;
		Server.SqlServerManager.ComplaintResponse(complaint);
		return true;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
