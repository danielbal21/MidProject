/*
 * 
 */
package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

// TODO: Auto-generated Javadoc
/**
 * The Class SurveyMangerHomeScreenController.
 */
public class SurveyMangerHomeScreenController implements UserControl {

    /** The active panel container. */
    @FXML
    private AnchorPane activePanelContainer;

    /**
     * Adds the new survey.
     *
     * @param event the event
     */
    @FXML
    void AddNewSurvey(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/AddNewSurvey.fxml");
    }

    /**
     * Insert answers.
     *
     * @param event the event
     */
    @FXML
    void InsertAnswers(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/InsertAnswersIntoSurvey.fxml");
    }

	/**
	 * On enter.
	 */
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * On exit.
	 */
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
