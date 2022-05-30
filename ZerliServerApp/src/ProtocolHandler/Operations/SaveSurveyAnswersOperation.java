package ProtocolHandler.Operations;

import Entities.Survey;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class SaveSurveyAnswersOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof Survey)
		{
			Survey survey =(Survey)data; 
			Server.SqlServerManager.SaveSurveyAnswers(survey);
			return true;
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
		return false;
	}

}
