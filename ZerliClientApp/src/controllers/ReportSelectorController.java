/*
 * 
 */
package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
// TODO: Auto-generated Javadoc

/**
 * The Class ReportSelectorController.
 */
public class ReportSelectorController implements UserControl {

	/** The report income. */
	private final String report_income = "Branch Income Report";
	
	/** The report orders. */
	private final String report_orders = "Branch Order Report";
	
	/** The report service. */
	private final String report_service = "Branch Complaints Report";
	   
		/** The active panel container. */
		@FXML
	    private AnchorPane activePanelContainer;

	    /** The error label. */
    	@FXML
	    private Label errorLabel;

	    /** The is compare. */
    	@FXML
	    private CheckBox isCompare;

	    /** The is monthly. */
    	@FXML
	    private RadioButton isMonthly;

	    /** The is quarterly. */
    	@FXML
	    private RadioButton isQuarterly;

	    /** The panel month. */
    	@FXML
	    private AnchorPane panelMonth;

	    /** The panel quarter. */
    	@FXML
	    private AnchorPane panelQuarter;

	    /** The period group. */
    	@FXML
	    private ToggleGroup periodGroup;
	    
	    /** The report month. */
    	@FXML
	    private ComboBox<String> reportMonth;
	    
	    /** The report year. */
    	@FXML
	    private ComboBox<String> reportYear;
	    
	    /** The compared month. */
    	@FXML
	    private ComboBox<String> comparedMonth;
	    
	    /** The compared year. */
    	@FXML
	    private ComboBox<String> comparedYear;

	    /** The compared quarter. */
    	@FXML
	    private ComboBox<String> comparedQuarter;

	    /** The compared quarter Y. */
    	@FXML
	    private ComboBox<String> comparedQuarterY;    

	    /** The report quarter. */
    	@FXML
	    private ComboBox<String> reportQuarter;

	    /** The report quarter Y. */
    	@FXML
	    private ComboBox<String> reportQuarterY;

	    /** The report type. */
    	@FXML
	    private ComboBox<String> reportType;

	    /** The view rep btn. */
    	@FXML
	    private Button viewRepBtn;


    /**
     * View report click.
     *
     * @param event the event
     */
    @FXML
    void viewReport_click(ActionEvent event) {
    	if(!isInputValid()) return;
    	var comparing = isCompare.isSelected();
    	byte[] reportMain,reportCompared = null;
    	LocalDate d;
    	if(!periodGroup.getSelectedToggle().equals(isMonthly))
    	{
    		d = LocalDate.of(Integer.parseInt(reportQuarterY.getValue()), Integer.parseInt(reportQuarter.getValue()), 1);
    	}
    	else
    	{
    		d = LocalDate.of(Integer.parseInt(reportYear.getValue().toString()),Month.valueOf(reportMonth.getValue()).getValue(),1);
    	}
    	
		ClientApp.ProtocolHandler.Invoke(RequestType.GetReport,null,
				new Object[] {
							  Utilities.GenericUtilties.StringToReportType(reportType.getValue()),
							  periodGroup.getSelectedToggle().equals(isMonthly),
							  d
						     },
				true);
		reportMain = (byte[])ClientApp.ProtocolHandler.GetResponse(RequestType.GetReport);
		if(reportMain == null)
		{
			Alert confirmAlert = new Alert(AlertType.NONE);
			confirmAlert.setTitle("No such report");
			confirmAlert.setContentText("The requested report does not exist");
			ButtonType yes = new ButtonType("OK", ButtonData.OK_DONE);
			confirmAlert.getDialogPane().getButtonTypes().add(yes);
			confirmAlert.showAndWait();
			return;
		}
    	if(comparing)
    	{
        	if(!periodGroup.getSelectedToggle().equals(isMonthly))
        	{
        		d = LocalDate.of(Integer.parseInt(comparedQuarterY.getValue()), Integer.parseInt(comparedQuarter.getValue()), 1);
        	}
        	else
        	{
        		d = LocalDate.of(Integer.parseInt(comparedYear.getValue().toString()),Month.valueOf(comparedMonth.getValue()).getValue(),1);
        	}
        	
    		ClientApp.ProtocolHandler.Invoke(RequestType.GetReport,null,
    				new Object[] {
    							  Utilities.GenericUtilties.StringToReportType(reportType.getValue()),
    							  periodGroup.getSelectedToggle().equals(isMonthly),
    							  d
    						     },
    				true);
    		reportCompared = (byte[])ClientApp.ProtocolHandler.GetResponse(RequestType.GetReport);
    		if(reportCompared == null)
    		{
    			Alert confirmAlert = new Alert(AlertType.NONE);
    			confirmAlert.setTitle("No such report");
    			confirmAlert.setContentText("The requested report does not exist");
    			ButtonType yes = new ButtonType("OK", ButtonData.OK_DONE);
    			confirmAlert.getDialogPane().getButtonTypes().add(yes);
    			confirmAlert.showAndWait();
    			return;
    		}
    	}
    	
    	if(comparing)
    		OpenReportViewer(reportMain, reportCompared);
    	else
    		OpenReportViewer(reportMain);
    }
    
    /**
     * Checks if is input valid.
     *
     * @return true, if is input valid
     */
    private boolean isInputValid()
    {
    	boolean valid = true;
    	StringBuilder error = new StringBuilder("");
    	if(reportType.getValue() == null)
    	{
    		valid = false;
    		error.append("please select a report\n");
    	}
    	if(periodGroup.getSelectedToggle() == null)
    	{
    		valid = false;
    		error.append("Please select the report period\n");
    	}
    	else if(periodGroup.getSelectedToggle().equals(isMonthly))
    	{
	    	if(reportMonth.getValue() == null || reportYear.getValue() == null)
	    	{
	    		valid = false;
	    		error.append("Please select a date for the report\n");
	    	}
	    	if(isCompare.isSelected())
	    	{
	    		if(comparedMonth.getValue() == null || comparedYear.getValue() == null)
	    		{
	        		error.append("Please select a date for the compared report\n");
	        		valid = false;
	    		}
	    	}
    	}
    	else //Quarterly
    	{
	    	if(reportQuarter.getValue() == null || reportQuarterY.getValue() == null)
	    	{
	    		valid = false;
	    		error.append("Please select a quarter for the report\n");
	    	}
	    	if(isCompare.isSelected())
	    	{
	    		if(comparedQuarter.getValue() == null || comparedQuarterY.getValue() == null)
	    		{
	        		error.append("Please select a quarter for the compared report\n");
	        		valid = false;
	    		}
	    	}
    	}
    	errorLabel.setText(error.toString());
    	return valid;
    }
    
	/**
	 * On enter.
	 */
	@Override
	public void onEnter() {
		
		reportType.getItems().add(report_income);
		reportType.getItems().add(report_orders);
		reportType.getItems().add(report_service);
		
		for(int i = 1;i<=4;i++)
		{
			reportQuarter.getItems().add(i + "");
			comparedQuarter.getItems().add(i + "");
		}
		
		for(Month m : Month.values())
		{
			reportMonth.getItems().add(m.toString());
			comparedMonth.getItems().add(m.toString());
		}
		
		for(int y = 2015;y<LocalDate.now().getYear()+1;y++)
		{
			reportYear.getItems().add(y + "");
			comparedYear.getItems().add(y + "");
			reportQuarterY.getItems().add(y + "");
			comparedQuarterY.getItems().add(y + "");		
		}
		
		isMonthly.selectedProperty().addListener(new ChangeListener<>() {
		
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(oldValue != newValue)
				{
					panelMonth.setDisable(!newValue);
					panelQuarter.setDisable(newValue);
				}
			}
		});
		isCompare.selectedProperty().addListener(new ChangeListener<>() {
		
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(oldValue != newValue)
				{
					comparedMonth.setDisable(!newValue);
					comparedYear.setDisable(!newValue);
					comparedQuarter.setDisable(!newValue);
					comparedQuarterY.setDisable(!newValue);
				}
			}
		});
		comparedMonth.setDisable(true);
		comparedYear.setDisable(true);
		comparedQuarter.setDisable(true);
		comparedQuarterY.setDisable(true);
		isMonthly.setSelected(true);
	}

	/**
	 * On exit.
	 */
	@Override
	public void onExit() {
		reportType.getItems().clear();
		
		reportQuarter.getItems().clear();
		reportQuarterY.getItems().clear();
		
		comparedQuarter.getItems().clear();
		comparedQuarterY.getItems().clear();
		
		reportMonth.getItems().clear();
		reportYear.getItems().clear();
		
		comparedMonth.getItems().clear();
		comparedYear.getItems().clear();
	}
	
	/**
	 * Open report viewer.
	 *
	 * @param report the report
	 */
	private void OpenReportViewer(byte[] report)
	{
		FXMLLoader loader = new FXMLLoader();
    	Parent root = null;
		loader.setLocation(getClass().getResource("/gui/mainframes/PdfViewer.fxml"));
		try {
			root =  loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		ReportViewerController rvCtrl = loader.getController();
		Stage newStage = new Stage();
		//Utilities.GenericUtilties.SetWindowMovable(root, newStage);
		Scene scene = new Scene(root);
		//newStage.initStyle(StageStyle.UNDECORATED);
		newStage.setScene(scene); 	
		rvCtrl.loadReport(report);
		newStage.show();
	}
	
	/**
	 * Open report viewer.
	 *
	 * @param reportLeft the report left
	 * @param reportRight the report right
	 */
	private void OpenReportViewer(byte[] reportLeft,byte[] reportRight)
	{
		FXMLLoader loader = new FXMLLoader();
    	Parent root = null;
		loader.setLocation(getClass().getResource("/gui/mainframes/pdfComparer.fxml"));
		try {
			root =  loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		pdfComparer rvCtrl = loader.getController();
		Stage newStage = new Stage();
		//Utilities.GenericUtilties.SetWindowMovable(root, newStage);
		Scene scene = new Scene(root);
		//newStage.initStyle(StageStyle.UNDECORATED);
		newStage.setScene(scene); 	
		rvCtrl.start(reportLeft, reportRight);
		newStage.show();
	}

}
