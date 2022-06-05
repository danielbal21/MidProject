package Entities;

import java.io.Serializable;

public class SurveyResult implements Serializable {
	private int surveyId;
	private String surveyName;
	byte[] expertSummary;
	byte[] surveyResults;
	
	public SurveyResult(int surveyId, String surveyName, byte[] expertSummary, byte[] surveyResults) {
		super();
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.expertSummary = expertSummary;
		this.surveyResults = surveyResults;
	}
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public String getSurveyName() {
		return surveyName;
	}
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	public byte[] getExpertSummary() {
		return expertSummary;
	}
	public void setExpertSummary(byte[] expertSummary) {
		this.expertSummary = expertSummary;
	}
	public byte[] getSurveyResults() {
		return surveyResults;
	}
	public void setSurveyResults(byte[] surveyResults) {
		this.surveyResults = surveyResults;
	}


}
