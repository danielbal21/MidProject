package controllers;

import java.util.ArrayList;

import Entities.Survey;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ExpertHomePageController implements UserControl{
	private ObservableList<String> surveyNames;
	private Survey survey=new Survey();
	private ArrayList<Integer> surveyList=new ArrayList<Integer>();
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private TextField requiredSurveysText;

    @FXML
    private ComboBox<String> surveyNameSelector;
    private void setComboBox() 
    {	
    	surveyNameSelector.getItems().clear();
    	ClientApp.ProtocolHandler.Invoke(RequestType.GetSurveysNames, survey, null, true);
    	surveyNames=(ObservableList<String>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetSurveysNames);
    	surveyNameSelector.setItems(surveyNames);
    	
    }
    @FXML
    void NextBtnPressed(ActionEvent event) {
    	if(LoginController.windowControl.peekPipe("surveyForExpert") == null) return;
    	requiredSurveysText.clear();
    	LoginController.windowControl.setUserControl("/gui/usercontrols/SurveyHistogram.fxml");
    }

    @FXML
    void surveyNameSelectorPressed(ActionEvent event) 
    {
    	if(surveyNameSelector.getSelectionModel().getSelectedItem()!=null)
		{
			String	string=surveyNameSelector.getSelectionModel().getSelectedItem();
			requiredSurveysText.setText(string.substring(string.indexOf(" ")));
			LoginController.windowControl.putPipe("surveyForExpert", string);
		}
    }
		
	@Override
	public void onEnter() {
		setComboBox();
	}
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
