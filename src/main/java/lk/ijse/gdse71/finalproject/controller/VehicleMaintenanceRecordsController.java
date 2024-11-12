package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.tm.MaintenanceRecordTM;
import lk.ijse.gdse71.finalproject.dto.tm.ReservationTM;
import lk.ijse.gdse71.finalproject.model.MaintenanceRecordModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleMaintenanceRecordsController implements Initializable {

    @FXML
    private TableView<MaintenanceRecordTM> MaintenanceTable;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbVehicleId;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colDesc;

    @FXML
    private TableColumn<MaintenanceRecordTM, Date> colEndDate;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colId;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colMaintenanceType;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colModel;

    @FXML
    private TableColumn<MaintenanceRecordTM, Date> colStartDate;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colVehicleId;

    @FXML
    private Label lblMAintenanceId;

    @FXML
    private Label lblModel;

    @FXML
    private AnchorPane maintenanceAnchorPane;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtMaintenanceType;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private TextField txtStartDtate;

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


    }

    @FXML
    void clickedTable(MouseEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

    }

    @FXML
    void selectVehicleId(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

    }

    MaintenanceRecordModel maintenanceRecordModel = new MaintenanceRecordModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colMaintenanceType.setCellValueFactory(new PropertyValueFactory<>("maintenanceType"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Define the default style (black border and transparent background)
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";


        // Apply the default style to all fields at the beginning of each save attempt
        txtStartDtate.setStyle(defaultStyle);
        txtEndDate.setStyle(defaultStyle);
        txtMaintenanceType.setStyle(defaultStyle);
        txtDesc.setStyle(defaultStyle);


    }




}
