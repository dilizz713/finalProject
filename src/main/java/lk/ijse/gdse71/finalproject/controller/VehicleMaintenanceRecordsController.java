package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.MaintenanceRecordTM;
import lk.ijse.gdse71.finalproject.dto.tm.ReservationTM;
import lk.ijse.gdse71.finalproject.model.MaintenanceRecordModel;
import lk.ijse.gdse71.finalproject.model.VehicleModel;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleMaintenanceRecordsController implements Initializable {


    @FXML
    public RadioButton rdbOngoing;

    @FXML
    public RadioButton rdbDone;

    @FXML
    public Button btnHistory;

    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker endDatePicker;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> cmbVehicleId;

    @FXML
    public TableColumn<MaintenanceRecordTM, String> colStatus;

    @FXML
    private Label lblMAintenanceId;

    @FXML
    private Label lblModel;

    @FXML
    private AnchorPane maintenanceAnchorPane;

    @FXML
    private TextField txtDesc;

    MaintenanceRecordModel maintenanceRecordModel = new MaintenanceRecordModel();

    private LocalDate originalStartDate;
    private boolean isEditMode = false;

    private String editingRecordId;

    private MaintenanceRecordsHistoryController maintenanceRecordsHistoryController;
    private ToggleGroup statusGroup = new ToggleGroup();

    private MaintenanceRecordDTO currentRecord;

    public void setMaintenanceRecordsHistoryController(MaintenanceRecordsHistoryController controller){
        this.maintenanceRecordsHistoryController = controller;
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblMAintenanceId.getText();
        String dec = txtDesc.getText();
        String vehicleId = cmbVehicleId.getValue();


        if (vehicleId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle.").show();
            return;
        }

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = (rdbDone.isSelected()) ? endDatePicker.getValue() : null;

        String status = rdbOngoing.isSelected() ? "Ongoing" : "Done";

        System.out.println("Maintenance record saved with status: " + status);

        MaintenanceRecordDTO maintenanceRecordDTO = new MaintenanceRecordDTO(id, startDate, endDate, dec, vehicleId, status);

        boolean isSaved = false;
        boolean isUpdated = false;

        if(btnSave.getText().equals("Update")){
            isUpdated = maintenanceRecordModel.updateMaintenanceRecord(maintenanceRecordDTO);
        }else{
            isSaved = maintenanceRecordModel.saveMaintenanceRecord(maintenanceRecordDTO);
        }

        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Record saved successfully!").show();

            rdbOngoing.setSelected(false);
            rdbDone.setSelected(false);

        }

        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Record updated successfully!").show();

            rdbOngoing.setSelected(false);
            rdbDone.setSelected(false);

        }
    }
    @FXML
    void selectStartDateOnAction(ActionEvent event){

    }
    @FXML
    void selectEndDateOnAction(ActionEvent event){

    }



    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

        txtDesc.clear();
        cmbVehicleId.setValue(null);
        lblModel.setText("");
        isEditMode= false;
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



    private LocalDate getOriginalStartDate(String recordId) throws SQLException {
        String query = "select startDate from MaintenanceRecord where id=?";
        ResultSet rst = CrudUtil.execute(query, recordId);

        if(rst.next()){
            return rst.getDate("startDate").toLocalDate();
        }
        return null;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rdbOngoing.setToggleGroup(statusGroup);
        rdbDone.setToggleGroup(statusGroup);

        rdbOngoing.setOnAction(event -> handleOngoinSelected());
        rdbDone.setOnAction(event -> handleDoneSelected());


        txtDesc.setText("");
        lblModel.setText("");


        try {
            loadNextMaintenanceId();
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(null);

            rdbOngoing.setSelected(true);

            loadVehicleIds();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load vehicle IDs!").show();
        }
        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }


        
        cmbVehicleId.setOnAction(event -> {
            String selectedVehicleID = cmbVehicleId.getValue();
            if (selectedVehicleID != null) {
                updateVehicleModel(selectedVehicleID);
            }
        });



    }

    private void handleDoneSelected() {
        rdbOngoing.setSelected(false);
    }

    private void handleOngoinSelected() {
      rdbDone.setSelected(false);
    }


    private void updateVehicleModel(String vehicleId) {
        try {
            String vehicleModel = maintenanceRecordModel.getVehicleNameById(vehicleId);
            lblModel.setText(vehicleModel != null ? vehicleModel : "Unknown Model");
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load vehicle model!").show();
        }
    }

    private void loadVehicleIds() throws SQLException {
        ObservableList<String> vehicleIds = FXCollections.observableArrayList(maintenanceRecordModel.getAllVehicleIds());
        cmbVehicleId.setItems(vehicleIds);
    }



    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextMaintenanceId();

        btnSave.setDisable(false);

        rdbOngoing.setSelected(false);
        rdbDone.setSelected(false);
        cmbVehicleId.setValue(null);

        startDatePicker.setValue(null);
        endDatePicker.setValue(null);

       lblModel.setText("");
        txtDesc.setText("");


    }
    public void loadNextMaintenanceId() throws SQLException {
        String nextMaintenanceId = maintenanceRecordModel.getNextMaintenanceId();
        lblMAintenanceId.setText(nextMaintenanceId);
    }


    private void loadComboBoxData() throws SQLException {
        ObservableList<String> vehicleId = FXCollections.observableArrayList(maintenanceRecordModel.getAllVehicleIds());
        cmbVehicleId.setItems(vehicleId);
    }


    @FXML
    public void watchHistory(ActionEvent actionEvent) {
        try{
            maintenanceAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/maintenance-records-history.fxml"));
            maintenanceAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    public void setRecordDetails(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException {
        currentRecord = maintenanceRecordDTO;
        isEditMode = true;
        editingRecordId = maintenanceRecordDTO.getId();

        lblMAintenanceId.setText(maintenanceRecordDTO.getId());
        originalStartDate = maintenanceRecordDTO.getStartDate();
        startDatePicker.setValue(originalStartDate);


        String vehicleId = maintenanceRecordDTO.getVehicleId();


        ResultSet vehicleDetailsResultSet = CrudUtil.execute("select  model from Vehicle where id = ?", vehicleId);
        if (vehicleDetailsResultSet.next()) {
            String model = vehicleDetailsResultSet.getString("model");

            lblModel.setText(model);

        } else {

            lblModel.setText("Model not found");
        }

        if(maintenanceRecordDTO.getStatus().equals("Ongoing")){
            rdbOngoing.setSelected(true);
        }else if(maintenanceRecordDTO.getStatus().equals("Done")){
            rdbDone.setSelected(true);
            endDatePicker.setValue(maintenanceRecordDTO.getEndDate());
        }



        btnSave.setText("Update");

    }

    public void setRecordDetails(String recordId, String description, String vehicleId, String model, String status, LocalDate startDate,LocalDate endDate) throws SQLException {
        lblMAintenanceId.setText(recordId);
        txtDesc.setText(description);
        lblModel.setText(model);

        if(status.equals("Ongoing")){
            rdbOngoing.setSelected(true);
        }else if(status.equals("Done")){
            rdbDone.setSelected(true);
        }

        startDatePicker.setValue(startDate);
        endDatePicker.setValue(endDate);


        loadVehicleIds();
        cmbVehicleId.setValue(vehicleId);

        btnSave.setText("Update");
    }

    @FXML
    public void ongoingBtnOnAction(ActionEvent event) {

    }
    @FXML
    public void doneButtonOnAction(ActionEvent event){

    }
}
