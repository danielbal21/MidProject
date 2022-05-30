package controllers;

import Entities.Survey;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SurveyHistogramController implements UserControl {

    @FXML
    private ImageView Image;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Label surveyNameLabel;

	private Survey survey=new Survey();

	@Override
	public void onEnter() {
		String string =(String)LoginController.windowControl.getPipe("surveyForExpert");
		survey.setId(Integer.parseInt(string.substring(0,string.indexOf(" "))));
		System.out.println(string);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
