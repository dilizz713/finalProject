package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PaymentController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInvoice;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cmbReservationId;

    @FXML
    private ComboBox<?> cmbStatus;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colcustomerName;

    @FXML
    private Label lblPaymentId;

    @FXML
    private AnchorPane paymentAnchorPane;

    @FXML
    private TableView<?> paymentTable;

    @FXML
    private RadioButton rdbAdvancePayment;

    @FXML
    private RadioButton rdbFullPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtSearchBar;

    @FXML
    void SaveOnAction(ActionEvent event) {

    }

    @FXML
    void clickedTable(MouseEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void navigateToInvoice(ActionEvent event) {

    }

    @FXML
    void resetOnAction(ActionEvent event) {

    }

    @FXML
    void selectAdvancedPaymentButton(ActionEvent event) {

    }

    @FXML
    void selectFullPaymentButton(ActionEvent event) {

    }

    @FXML
    void selectReservationId(ActionEvent event) {

    }

    @FXML
    void selectStatus(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

}
