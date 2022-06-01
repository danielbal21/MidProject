package controllers;

import java.util.Optional;

import Entities.Survey;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;

public class InsertAnswersIntoSurveyController implements UserControl{
	private Spinner<Integer>[] spinnerArray= new Spinner[6];
	private Survey survey=new Survey();
	private int index;
	private TextField[] QuestionTextArray=new TextField[6];
	private ObservableList<String> surveyNames;
    @FXML
    private Button SubmitResult1;

    @FXML
    private AnchorPane activePanelContainer;

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
    private Spinner<Integer> spinnerQuestion1;

    @FXML
    private Spinner<Integer> spinnerQuestion2;

    @FXML
    private Spinner<Integer> spinnerQuestion3;

    @FXML
    private Spinner<Integer> spinnerQuestion4;

    @FXML
    private Spinner<Integer> spinnerQuestion5;

    @FXML
    private Spinner<Integer> spinnerQuestion6;
    

    @FXML
    private Label submitLabel;

    @FXML
    private ComboBox<String> surveyNameSelector;
    
	private boolean unsaved=true;

    @FXML
    void BackBtnPressed(ActionEvent event) 
    {
    	if(inputValid())
    	{
	      	Alert confirmAlert = new Alert(AlertType.NONE);
			confirmAlert.setTitle("Leaving Insert Answers Into Survey ");
			confirmAlert.setContentText("Do you want to save typed data?");
			ButtonType yes = new ButtonType("Yes", ButtonData.YES);
			ButtonType no = new ButtonType("No",ButtonData.NO);
			confirmAlert.getDialogPane().getButtonTypes().add(yes);
			confirmAlert.getDialogPane().getButtonTypes().add(no);
			Optional<ButtonType> result = confirmAlert.showAndWait();
			result.ifPresent(response -> { 
				if(response == no) {unsaved=true; }
				else unsaved=false;
			});
    	}
    	LoginController.windowControl.setUserControl("/gui/usercontrols/SurveyMangerHomeScreen.fxml");
    }

    private boolean inputValid() {
    	if(spinnerArray[0].getValue()!=null)
    	{
	    	for (Spinner<Integer> spinner : spinnerArray) {	
	    		
	    		if(spinner.getValue()!=0)
	    		{
	    			return true;
	    		}
	    	}
	    }
		return false;
	}

	@Override
    public void onEnter() 
    {
    	SetArraies();
    	setComboBox();
    }
    
    private void SetArraies()
    {
 	   spinnerArray[0]=spinnerQuestion1;
 	   spinnerArray[1]=spinnerQuestion2;
 	   spinnerArray[2]=spinnerQuestion3;
 	   spinnerArray[3]=spinnerQuestion4;
 	   spinnerArray[4]=spinnerQuestion5;
 	   spinnerArray[5]=spinnerQuestion6;
 	   ////
 	   QuestionTextArray[0]=questionText1;
 	   QuestionTextArray[1]=questionText2;
 	   QuestionTextArray[2]=questionText3;
 	   QuestionTextArray[3]=questionText4;
 	   QuestionTextArray[4]=questionText5;
 	   QuestionTextArray[5]=questionText6;
    }
    private void setComboBox() 
    {	
    	ClientApp.ProtocolHandler.Invoke(RequestType.GetSurveysNames, survey, null, true);
    	surveyNames=(ObservableList<String>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetSurveysNames);
    	surveyNameSelector.setItems(FXCollections.observableArrayList(surveyNames));
    }

    @FXML
    void SetSurvey(ActionEvent event) 
    {
		if(surveyNameSelector.getSelectionModel().getSelectedItem()!=null)
		{
			String	string=surveyNameSelector.getSelectionModel().getSelectedItem().toString();
			survey.setId(Integer.parseInt(string.substring(0,string.indexOf(" "))));
	    	SetSurveyById(survey.getId());
		}
    }
    
    private void SetSurveyById(int id) 
    {
    	if(unsaved)
    	{
    		SetSpinners();
    	}
		ClientApp.ProtocolHandler.Invoke(RequestType.GetSurvey, id, null, true);
		survey=(Survey)ClientApp.ProtocolHandler.GetResponse(RequestType.GetSurvey);
		setQuestion();
    }
    
    private void SetSpinners()
    {
		for (Spinner<Integer> spinner : spinnerArray) {
			SpinnerValueFactory<Integer> valueFactory = 
					new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
			valueFactory.setValue(0);
			spinner.setValueFactory(valueFactory);
		}
    }

    private void setQuestion() 
    {
    	for (int i = 0; i < QuestionTextArray.length; i++) {
    		QuestionTextArray[i].setText(survey.getQuestions()[i]);
    	}
    }

@FXML
void SubmitResult_Click(ActionEvent event) { 
	if (spinnerArray[0].getValue()==null) {
		return;
	}
	index=0;
	for (Spinner<Integer> spinner : spinnerArray) {		
		if(spinner.getValue()==0)
		{	
			submitLabel.setText("**At Q"+(index+1)+" the answer must be 1-10");
			submitLabel.setVisible(true);
			
			Thread thread =new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					submitLabel.setVisible(false);
				}
			});
			thread.start();
		break;
			
		}
		survey.getAnswers()[index]=spinner.getValue();
		index++;
	}
	if (index==6)
	{
		String	string=surveyNameSelector.getSelectionModel().getSelectedItem().toString();
		survey.setId(Integer.parseInt(string.substring(0,string.indexOf(" "))));
		ClientApp.ProtocolHandler.Invoke(RequestType.SaveSurveyAnswers, survey, null, false);
		Alert confirmAlert = new Alert(AlertType.NONE);
		confirmAlert.setTitle("Your feedback update into the system");
		confirmAlert.setContentText("Thank you for your feedback");
		ButtonType ok = new ButtonType("ok",ButtonData.OK_DONE);
		confirmAlert.getDialogPane().getButtonTypes().add(ok);
		Optional<ButtonType> result = confirmAlert.showAndWait();
		result.ifPresent(response -> { 
			if(response == ok) SetSpinners();
		});
		
	}
	
}

@Override
public void onExit() {
	
}

}
