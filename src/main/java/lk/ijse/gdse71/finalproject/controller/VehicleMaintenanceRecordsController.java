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
    public Label lblStartDate;

    @FXML
    public Label lblEndDate;

    @FXML
    public RadioButton rdbOngoing;

    @FXML
    public RadioButton rdbDone;

    @FXML
    public Button btnHistory;

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

    private MaintenanceRecordDTO currentRecord;

    public void setMaintenanceRecordsHistoryController(MaintenanceRecordsHistoryController controller){
        this.maintenanceRecordsHistoryController = controller;
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblMAintenanceId.getText();
        String dec = txtDesc.getText();
        String vehicleId = cmbVehicleId.getValue();


        if (cmbVehicleId.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle.").show();
            return;
        }

        String status = rdbOngoing.isSelected() ? "Ongoing" : "Done";


        LocalDate startDate = null;
        LocalDate endDate = null;


        if(rdbOngoing.isSelected()){
            startDate = LocalDate.now();

        }else if(rdbDone.isSelected()){
            startDate = LocalDate.parse(lblStartDate.getText());
            endDate = LocalDate.now();
        }




       /* LocalDate startDate = lblStartDate.getText().isEmpty() ? null : LocalDate.parse(lblStartDate.getText());
        LocalDate endDate = lblEndDate.getText().isEmpty() ? null : LocalDate.parse(lblEndDate.getText());
*/

       /* LocalDate startDate = rdbOngoing.isSelected() ? LocalDate.now() : null;
        LocalDate endDate = rdbDone.isSelected() ? LocalDate.now() : null;*/

        /*currentReservation.setStatus(status);
        currentReservation.setStartDate(startDate);
        currentReservation.setEndDate(endDate);*/



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
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
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
    void doneButtonOnAction(ActionEvent event) {
        if(rdbDone.isSelected()){
            LocalDate endDate = LocalDate.now();
            lblEndDate.setText(endDate.toString());
        }
    }

    @FXML
    void ongoingBtnOnAction(ActionEvent event) {
        if(rdbOngoing.isSelected()){
            LocalDate startDate = LocalDate.now();
            lblStartDate.setText(startDate.toString());
            lblEndDate.setText("");
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       /* String id = lblMAintenanceId.getText();
        String desc = txtDesc.getText();
        String vehicleId = cmbVehicleId.getValue();

        if(cmbVehicleId.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle").show();
            return;
        }

        String status = "";

       // LocalDate startDate = LocalDate.parse(lblStartDate.getText());
        LocalDate startDate = null;
        LocalDate endDate = null;

        if(rdbOngoing.isSelected()){
            status = "Ongoing";
            startDate = LocalDate.parse(lblStartDate.getText());
        }else if(rdbDone.isSelected()) {
            status = "Done";
            startDate = LocalDate.parse(lblStartDate.getText());
            endDate = LocalDate.now();

        }*/

        if(!isEditMode){
            new Alert(Alert.AlertType.ERROR, "No record selected for updating!").show();
            return;
        }
        String id = lblMAintenanceId.getText();
        String desc = txtDesc.getText();
        String vehicleId = cmbVehicleId.getValue();


        if (cmbVehicleId.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle.").show();
            return;
        }

         String  status = "";

        LocalDate startDate = null;
        LocalDate endDate = null;


        if(rdbDone.isSelected()){
            status = "Done";
            endDate = LocalDate.now();
        }

        LocalDate vehicleStartDate = getOriginalStartDate(id);



        MaintenanceRecordDTO maintenanceRecordDTO = new MaintenanceRecordDTO(id, startDate, endDate, desc, vehicleId , status);
        boolean isUpdated = maintenanceRecordModel.updateMaintenanceRecord(maintenanceRecordDTO);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Maintenance record updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update record!").show();
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

        lblStartDate.setText("");
        lblEndDate.setText("");
        txtDesc.setText("");
        lblModel.setText("");

        rdbOngoing.setOnAction(event -> handleStatusChange());
        rdbDone.setOnAction(event -> handleStatusChange());


        try {
            loadVehicleIds(); // Load vehicle IDs into ComboBox
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

    private void updateVehicleModel(String vehicleId) {
        try {
            String vehicleModel = maintenanceRecordModel.getVehicleNameById(vehicleId);
            lblModel.setText(vehicleModel != null ? vehicleModel : "Unknown Model");
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to fetch vehicle model!").show();
        }
    }

    private void loadVehicleIds() throws SQLException {
        ObservableList<String> vehicleIds = FXCollections.observableArrayList(maintenanceRecordModel.getAllVehicleIds());
        cmbVehicleId.setItems(vehicleIds);
    }

   

    private void handleStatusChange() {
        if(rdbOngoing.isSelected()){
            lblStartDate.setText(originalStartDate == null ? LocalDate.now().toString() : originalStartDate.toString());
            lblEndDate.setText("");
        }else if(rdbDone.isSelected()){
            lblEndDate.setText(LocalDate.now().toString());
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextMaintenanceId();

        btnSave.setDisable(false);


        rdbOngoing.setSelected(false);
        rdbDone.setSelected(false);


        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";

        txtDesc.setStyle(defaultStyle);
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

   /* private void searchRecords() throws SQLException, ClassNotFoundException {
        String searchText = txtSearchBar.getText().trim();

        if(searchText.isEmpty()){
            loadTableData();
            return;
        }

        ArrayList<MaintenanceRecordDTO> maintenanceRecordDTOS = maintenanceRecordModel.getRecordsBySearch(searchText);

        // Populate the table with filtered data
        ObservableList<MaintenanceRecordTM> maintenanceRecordTMS = FXCollections.observableArrayList();

        for (MaintenanceRecordDTO maintenanceRecordDTO : maintenanceRecordDTOS) {
            MaintenanceRecordTM maintenanceRecordTM = new MaintenanceRecordTM(
                    maintenanceRecordDTO.getId(),
                    maintenanceRecordDTO.getStartDate(),
                    maintenanceRecordDTO.getEndDate(),
                    maintenanceRecordDTO.getVehicleId(),
                    maintenanceRecordModel.getVehicleNameById(maintenanceRecordDTO.getVehicleId()),
                    maintenanceRecordDTO.getStatus(),
                    maintenanceRecordDTO.getDescription(),
            );
            maintenanceRecordTMS.add(maintenanceRecordTM);
        }
        MaintenanceTable.setItems(maintenanceRecordTMS);
    }*/

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

      /* lblStartDate.setText(String.valueOf(reservationDTO.getStartDate()));
       lblEndDate.setText(String.valueOf(reservationDTO.getEndDate()));*/


        lblStartDate.setText(maintenanceRecordDTO.getStartDate() != null ? maintenanceRecordDTO.getStartDate().toString() : "");
        lblEndDate.setText(maintenanceRecordDTO.getEndDate() != null ? maintenanceRecordDTO.getEndDate().toString() : "");

        originalStartDate = maintenanceRecordDTO.getStartDate();

        String vehicleId = maintenanceRecordDTO.getVehicleId();


        /*VehicleDTO selectedVehicle = maintenanceRecordModel.getVehicleDTOsForRecords()
                .stream()
                .filter(vehicle -> vehicle.getId().equals(vehicleId))
                .findFirst()
                .orElse(null);
        if (selectedCustomer != null) {
            cmbCustomer.getSelectionModel().select(selectedCustomer);
        }

        String vehicleId = reservationDTO.getVehicleId();*/

        ResultSet vehicleDetailsResultSet = CrudUtil.execute("SELECT  model FROM Vehicle WHERE id = ?", vehicleId);
        if (vehicleDetailsResultSet.next()) {
            String model = vehicleDetailsResultSet.getString("model");

            // Update vehicle labels in the UI
            lblModel.setText(model);

        } else {

            lblModel.setText("Model not found");
        }

        if(maintenanceRecordDTO.getStatus().equals("Ongoing")){
            rdbOngoing.setSelected(true);
        }else if(maintenanceRecordDTO.getStatus().equals("Done")){
            rdbDone.setSelected(true);
        }

        lblStartDate.setText(String.valueOf(maintenanceRecordDTO.getStartDate()));
        lblEndDate.setText(String.valueOf(maintenanceRecordDTO.getEndDate()));

        // manageRadioButton();



       /* switch (reservationDTO.getStatus()){
           case "Pending":
               rdbPending.setSelected(true);
               break;
           case "Ongoing":
               rdbOngoing.setSelected(true);
               lblStartDate.setText(String.valueOf(reservationDTO.getStartDate()));
               break;
           case "Done":
               rdbDone.setSelected(true);
               lblEndDate.setText(String.valueOf(reservationDTO.getEndDate()));
               break;*/

        btnSave.setText("Update");

    }

    public void setRecordDetails(String recordId, String description, String vehicleId, String model, String status, LocalDate startDate,LocalDate endDate) throws SQLException {
        lblMAintenanceId.setText(recordId);
        txtDesc.setText(description);
        lblModel.setText(model);

        lblStartDate.setText(String.valueOf(startDate));
        lblEndDate.setText(String.valueOf(endDate));

        if(status.equals("Ongoing")){
            rdbOngoing.setSelected(true);
        }else if(status.equals("Done")){
            rdbDone.setSelected(true);
        }


        loadVehicleIds();
        cmbVehicleId.setValue(vehicleId);

        btnSave.setText("Update");
    }
}
