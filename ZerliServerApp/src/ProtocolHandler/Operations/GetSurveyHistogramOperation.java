package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Survey;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import reports.SurveyHistogramGenerator;
import server.Server;

public class GetSurveyHistogramOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		if(data instanceof Survey)
		{
			ArrayList<int[]> listOfSurveyAnswers=new ArrayList<int[]>();
			for (int i = 0; i < 6; i++) {
				listOfSurveyAnswers.add(new int [10]);
			}
			ArrayList<byte[]> listOfImages;
			Survey survey=(Survey) data; 
			Server.SqlServerManager.GetSurveyHistogram(survey,listOfSurveyAnswers);
			SurveyHistogramGenerator shg=new SurveyHistogramGenerator();
			listOfImages=shg.get6images(listOfSurveyAnswers,survey);
			response.SetResponse(listOfImages);
			return true;
			
		}
		return false;
	}

}
