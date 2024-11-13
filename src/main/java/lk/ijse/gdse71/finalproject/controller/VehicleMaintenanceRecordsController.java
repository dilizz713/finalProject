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
        String id = lblMAintenanceId.getText();
        String startDateText = txtStartDtate.getText();
        String endDateText = txtEndDate.getText();
        String desc = txtDesc.getText();
        String maintenanceType = txtMaintenanceType.getText();
        String vehicleId = cmbVehicleId.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.sql.Date startDate;
        java.sql.Date endDate;

        try {
            LocalDate startLocalDate = LocalDate.parse(startDateText, formatter);
            LocalDate endLocalDate = LocalDate.parse(endDateText, formatter);
            startDate = java.sql.Date.valueOf(startLocalDate);
            endDate = java.sql.Date.valueOf(endLocalDate);
        } catch (DateTimeException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter dates in the format yyyy-MM-dd").show();
            return;
        }


        MaintenanceRecordDTO maintenanceRecordDTO = new MaintenanceRecordDTO(id, startDate, endDate, desc, maintenanceType, vehicleId);
        boolean isSaved = maintenanceRecordModel.saveMaintenanceRecord(maintenanceRecordDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Maintenance record saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save record!").show();
        }


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
        String selectedVehicleID = cmbVehicleId.getValue();
        try {
            lblModel.setText(maintenanceRecordModel.getVehicleNameById(selectedVehicleID));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle model!").show();
        }
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

        try {
            loadComboBoxData();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading combobox data").show();
        }


        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }


    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextMaintenanceId();
        loadTableData();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);


        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";

        txtStartDtate.setStyle(defaultStyle);
        txtEndDate.setStyle(defaultStyle);
        txtMaintenanceType.setStyle(defaultStyle);
        txtDesc.setStyle(defaultStyle);

        txtStartDtate.setText("");
        txtEndDate.setText("");
        txtMaintenanceType.setText("");
        txtDesc.setText("");


    }
    public void loadNextMaintenanceId() throws SQLException {
        String nextMaintenanceId = maintenanceRecordModel.getNextMaintenanceId();
        lblMAintenanceId.setText(nextMaintenanceId);
    }
    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<MaintenanceRecordDTO> maintenanceRecordDTOS = maintenanceRecordModel.getAllMaintenanceRecords();
        ObservableList<MaintenanceRecordTM> maintenanceRecordTMS = FXCollections.observableArrayList();

        for (MaintenanceRecordDTO maintenanceRecordDTO : maintenanceRecordDTOS) {
            MaintenanceRecordTM maintenanceRecordTM = new MaintenanceRecordTM(
                    maintenanceRecordDTO.getId(),
                    maintenanceRecordDTO.getStartDate(),
                    maintenanceRecordDTO.getEndDate(),
                    maintenanceRecordDTO.getMaintenanceType(),
                    maintenanceRecordDTO.getVehicleId(),
                    maintenanceRecordModel.getVehicleNameById(maintenanceRecordDTO.getVehicleId()),
                    maintenanceRecordDTO.getDescription()
            );
            maintenanceRecordTMS.add(maintenanceRecordTM);
        }
        MaintenanceTable.setItems(maintenanceRecordTMS);

    }

    private void loadComboBoxData() throws SQLException {
        ObservableList<String> vehicleId = FXCollections.observableArrayList(maintenanceRecordModel.getAllVehicleIds());
        cmbVehicleId.setItems(vehicleId);
    }





}
