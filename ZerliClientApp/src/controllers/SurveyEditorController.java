package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SurveyEditorController implements UserControl {

    @FXML
    private Button SubmitResult;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Button addSurvey;

    @FXML
    private Label q10L;

    @FXML
    private TextField q10T;

    @FXML
    private Label q1L;

    @FXML
    private TextField q1T;

    @FXML
    private Label q2L;

    @FXML
    private TextField q2T;

    @FXML
    private Label q3L;

    @FXML
    private TextField q3T;

    @FXML
    private Label q4L;

    @FXML
    private TextField q4T;

    @FXML
    private Label q5L;

    @FXML
    private TextField q5T;

    @FXML
    private Label q6L;

    @FXML
    private TextField q6T;

    @FXML
    private Label q7L;

    @FXML
    private TextField q7T;

    @FXML
    private Label q8L;

    @FXML
    private TextField q8T;

    @FXML
    private Label q9L;

    @FXML
    private TextField q9T;

    @FXML
    private TextField surveyName;

    @FXML
    private ComboBox<String> surveyNameSelector;

    @FXML
    void SubmitResult_Click(ActionEvent event) {

    }

    @FXML
    void addSurvey_Cllick(ActionEvent event) {

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
