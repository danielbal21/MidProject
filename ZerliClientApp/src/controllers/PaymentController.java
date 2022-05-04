package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PaymentController {

    @FXML
    private AnchorPane activePanelContainer2;

    @FXML
    private TextField cardNumTxt1;

    @FXML
    private TextField cardNumTxt2;

    @FXML
    private TextField cardNumTxt3;

    @FXML
    private TextField cardNumTxt4;

    @FXML
    private AnchorPane creditCardDialog;

    @FXML
    private RadioButton creditCardRB;

    @FXML
    private TextField cvvText;

    @FXML
    private Label discountPercentageLabel;

    @FXML
    private ComboBox<?> expMonthCB;

    @FXML
    private TextField holderID;

    @FXML
    private Label netPriceLabel;

    @FXML
    private Button nextBtn;

    @FXML
    private ToggleGroup paymentMethod;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label w;

    @FXML
    private ComboBox<?> yearCB;

    @FXML
    private VBox zerliCoinsDialog;

    @FXML
    private RadioButton zerliCoinsRB;

    @FXML
    void nextBtn_Click(ActionEvent event) {

    }

}
