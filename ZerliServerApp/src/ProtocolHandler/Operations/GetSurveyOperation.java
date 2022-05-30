package ProtocolHandler.Operations;

import Entities.Survey;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetSurveyOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		if(data instanceof Integer)
		{
			Survey survey=new Survey();
			survey.setId((int)data);
			Server.SqlServerManager.GetSurvey(survey);
			response.SetResponse(survey);
			return true;
		}
		return false;
	}

}
