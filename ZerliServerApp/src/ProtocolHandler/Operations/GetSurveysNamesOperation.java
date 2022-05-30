package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Survey;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import javafx.collections.ObservableList;
import server.Server;

public class GetSurveysNamesOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<String> list=new ArrayList<String>();
		Server.SqlServerManager.GetSurveysNames(list);
		response.SetResponse(list);
		return true;
	}

}
