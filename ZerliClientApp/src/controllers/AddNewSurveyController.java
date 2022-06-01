package controllers;

import java.util.Optional;

import Entities.Survey;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;

public class AddNewSurveyController implements UserControl {
	private Survey survey=new Survey();
	private int index;
	private TextField[] QuestionTextArray=new TextField[6];
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Button addSurvey;

    @FXML
    private Label addSurveyLabel;

    @FXML
    private Label addViolationLabel;

    @FXML
    private TextField questionText1;

    @FXML
    private TextField questionText2;

    @FXML
    private TextField questionText3;

    @FXML
    private TextField questionText4;

    @FXML
    private TextField questionText5;

    @FXML
    private TextField questionText6;

    @FXML
    private TextField surveyName;

	@Override
	public void onEnter() {
		SetQuestions();		
	}
    private void SetQuestions() {

  	   QuestionTextArray[0]=questionText1;
  	   QuestionTextArray[1]=questionText2;
  	   QuestionTextArray[2]=questionText3;
  	   QuestionTextArray[3]=questionText4;
  	   QuestionTextArray[4]=questionText5;
  	   QuestionTextArray[5]=questionText6;
  }
    @FXML
    void addSurvey_Cllick(ActionEvent event) {
    	
	    	index=0;
	    	for (TextField textField : QuestionTextArray) {
				if(textField.getText().equals(""))
				{
					addSurveyLabel.setText("**Please answer question "+(index+1));
					addSurveyLabel.setVisible(true);
		    		
					Thread thread =new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							addSurveyLabel.setVisible(false);
						}
					});
					thread.start();
				break;
				}
				survey.getQuestions()[index]=textField.getText();
				index++;
				}
		    if(index==6)
		    {	
		    	survey.setContent(surveyName.getText());
		    	ClientApp.ProtocolHandler.Invoke(RequestType.SaveSurvey, survey, null, false);
		    	Alert confirmAlert = new Alert(AlertType.NONE);
				confirmAlert.setTitle("The survey added successfully");
				confirmAlert.setContentText("Thank you , the Survey update into the system");
				ButtonType ok = new ButtonType("ok",ButtonData.OK_DONE);
				confirmAlert.getDialogPane().getButtonTypes().add(ok);
				Optional<ButtonType> result = confirmAlert.showAndWait();
				result.ifPresent(response -> { 
					if(response == ok) resetQuestion();
				});
		    	
		    }
    }
    private void resetQuestion() {
    	for (TextField textField : QuestionTextArray) {
			textField.setText("");
    	}
    	surveyName.setText("");
	}

    @FXML
    void BackBtnPressed(ActionEvent event) {
    	if(inputValid())
    	{
	    	Alert confirmAlert = new Alert(AlertType.NONE);
			confirmAlert.setTitle("Leaving Add New Survey");
			confirmAlert.setContentText("Do you want to save typed data?");
			ButtonType yes = new ButtonType("Yes", ButtonData.YES);
			ButtonType no = new ButtonType("No",ButtonData.NO);
			confirmAlert.getDialogPane().getButtonTypes().add(yes);
			confirmAlert.getDialogPane().getButtonTypes().add(no);
			Optional<ButtonType> result = confirmAlert.showAndWait();
			result.ifPresent(response -> { 
				if(response == no) resetQuestion();
			});
    	}
    	LoginController.windowControl.setUserControl("/gui/usercontrols/SurveyMangerHomeScreen.fxml");
    }
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}
	private boolean inputValid()
	{
		for (TextField textField : QuestionTextArray) {
			if (!textField.getText().equals(""))
				return true;
    	}
		if(!surveyName.getText().equals(""))
			return true;
		return false;
	}

}

