package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class VehicleDamageController {

    @FXML
    private TableView<?> DamageRecordsTable;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private ComboBox<?> cmbVehicleId;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDamageId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblId;

    @FXML
    private Label lblModel;

    @FXML
    private TextArea txtAreaDesc;

    @FXML
    private TextField txtCost;

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
    void resetOnAction(ActionEvent event) {

    }

    @FXML
    void selectVehicleId(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

}
