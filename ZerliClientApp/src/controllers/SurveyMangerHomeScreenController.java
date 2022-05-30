package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SurveyMangerHomeScreenController implements UserControl {

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    void AddNewSurvey(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/AddNewSurvey.fxml");
    }

    @FXML
    void InsertAnswers(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/InsertAnswersIntoSurvey.fxml");
    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
