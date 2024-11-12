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
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.ReservationTM;
import lk.ijse.gdse71.finalproject.dto.tm.VehicleTM;
import lk.ijse.gdse71.finalproject.model.ReservationModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    public TableColumn<ReservationTM, String> colVehicleId;
    public TableColumn<ReservationTM, String> colDriverId;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbDriverId;

    @FXML
    private ComboBox<String> cmbVehiicleId;

    @FXML
    private TableColumn<ReservationTM, String> colCustomerId;

    @FXML
    private TableColumn<ReservationTM, String> colCustomerName;

    @FXML
    private TableColumn<ReservationTM, String> colDriverName;

    @FXML
    private TableColumn<ReservationTM, Date> colEndDate;

    @FXML
    private TableColumn<ReservationTM, Double> colMileage;

    @FXML
    private TableColumn<ReservationTM, String> colModel;

    @FXML
    private TableColumn<ReservationTM, String> colReservationId;

    @FXML
    private TableColumn<ReservationTM, Date> colStartDate;

    @FXML
    private TableColumn<ReservationTM, String> colStatus;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDriverName;

    @FXML
    private Label lblReservationId;

    @FXML
    private Label lblVehicleModel;

    @FXML
    private RadioButton rdbDone;

    @FXML
    private RadioButton rdbPending;

    @FXML
    private AnchorPane reservationAnchorpane;

    @FXML
    private TableView<ReservationTM> reservationTable;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtMileage;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField searchBar;

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblReservationId.getText();
        String startDateText = txtStartDate.getText();
        String endDateText = txtEndDate.getText();
        String estimatedMileage = txtMileage.getText();
        String customerId = cmbCustomerId.getValue();
        String vehicleId = cmbVehiicleId.getValue();
        String driverId = cmbDriverId.getValue();
        String status = rdbDone.isSelected() ? "Done" : rdbPending.isSelected() ? "Pending" : null;

        if (status == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a status (Done or Pending)").show();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        try {
            LocalDate startLocalDate = LocalDate.parse(startDateText, formatter);
            LocalDate endLocalDate = LocalDate.parse(endDateText, formatter);
            startDate = Date.valueOf(startLocalDate);
            endDate = Date.valueOf(endLocalDate);
        } catch (DateTimeException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter dates in the format yyyy-MM-dd").show();
            return;
        }

        double mileage = -1;
        mileage = Double.parseDouble(estimatedMileage);

        ReservationDTO reservationDTO = new ReservationDTO(id, startDate, endDate, mileage, customerId, vehicleId, driverId, status);
        boolean isSaved = reservationModel.saveReservation(reservationDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Reservation saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save reservation!").show();
        }


    }

    @FXML
    void clickedTable(MouseEvent event) {
        ReservationTM reservationTM = reservationTable.getSelectionModel().getSelectedItem();
        if (reservationTM != null) {
            lblReservationId.setText(reservationTM.getId());
            txtStartDate.setText(String.valueOf(reservationTM.getStartDate()));
            txtEndDate.setText(String.valueOf(reservationTM.getEndDate()));
            txtMileage.setText(String.valueOf(reservationTM.getEstimatedMileage()));
            cmbCustomerId.setValue(reservationTM.getCustomerId());
            cmbVehiicleId.setValue(reservationTM.getVehicleId());
            cmbDriverId.setValue(reservationTM.getDriverId());

            try {
                lblCustomerName.setText(reservationModel.getCustomerNameById(reservationTM.getCustomerId()));
                lblVehicleModel.setText(reservationModel.getVehicleNameById(reservationTM.getVehicleId()));
                lblDriverName.setText(reservationModel.getDriverNameById(reservationTM.getDriverId()));
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to load related details!").show();
            }

            if ("Done".equalsIgnoreCase(reservationTM.getStatus())) {
                rdbDone.setSelected(true);
                rdbPending.setDisable(true);
                rdbDone.setDisable(false);

            } else if ("Pending".equalsIgnoreCase(reservationTM.getStatus())) {
                rdbPending.setSelected(true);
                rdbDone.setDisable(true);
                rdbPending.setDisable(false);
            } else {
                rdbDone.setSelected(false);
                rdbPending.setSelected(false);
                rdbDone.setDisable(false);
                rdbPending.setDisable(false);
            }

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String reservationId = lblReservationId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = reservationModel.deleteReservation(reservationId);
            if(isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle deleted!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to delete vehicle!").show();
            }
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void selectCustomerId(ActionEvent event) {
        String selectedCustomerID = cmbCustomerId.getValue();
        try {
            lblCustomerName.setText(reservationModel.getCustomerNameById(selectedCustomerID));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer name!").show();
        }
    }

    @FXML
    void selectDone(ActionEvent event) {
        if (rdbDone.isSelected()) {
            rdbPending.setDisable(true);
        } else {
            rdbPending.setDisable(false);
        }
    }

    @FXML
    void selectDriverId(ActionEvent event) {
        String selectedDriverID = cmbDriverId.getValue();
        try {
            lblDriverName.setText(reservationModel.getDriverNameById(selectedDriverID));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load driver name!").show();
        }
    }

    @FXML
    void selectPending(ActionEvent event) {
        if (rdbPending.isSelected()) {
            rdbDone.setDisable(true);
        } else {
            rdbDone.setDisable(false);
        }
    }

    @FXML
    void selectVehicleId(ActionEvent event) {
        String selectedVehicleID = cmbVehiicleId.getValue();
        try {
            lblVehicleModel.setText(reservationModel.getVehicleNameById(selectedVehicleID));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle model!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblReservationId.getText();
        String startDateText = txtStartDate.getText();
        String endDateText = txtEndDate.getText();
        Double estimatedMileage = Double.valueOf(txtMileage.getText());
        String customerId = cmbCustomerId.getValue();
        String vehicleId = cmbVehiicleId.getValue();
        String driverId = cmbDriverId.getValue();
        String status = rdbDone.isSelected() ? "Done" : rdbPending.isSelected() ? "Pending" : null;

        if (status == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a status (Done or Pending)").show();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        try {
            LocalDate startLocalDate = LocalDate.parse(startDateText, formatter);
            LocalDate endLocalDate = LocalDate.parse(endDateText, formatter);
            startDate = Date.valueOf(startLocalDate);
            endDate = Date.valueOf(endLocalDate);
        } catch (DateTimeException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter dates in the format yyyy-MM-dd").show();
            return;
        }

//        double mileage = -1;
//        mileage = Double.parseDouble(estimatedMileage);

        ReservationDTO reservationDTO = new ReservationDTO(id, startDate, endDate, estimatedMileage, customerId, vehicleId, driverId, status);
        boolean isUpdate = reservationModel.updateReservation(reservationDTO);
        if (isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Reservation updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to updated reservation!").show();
        }

    }

    ReservationModel reservationModel = new ReservationModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colMileage.setCellValueFactory(new PropertyValueFactory<>("estimatedMileage"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        // Define the default style (black border and transparent background)
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";


        // Apply the default style to all fields at the beginning of each save attempt
        txtStartDate.setStyle(defaultStyle);
        txtEndDate.setStyle(defaultStyle);
        txtMileage.setStyle(defaultStyle);

        try {
            loadComboBoxData();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading combobox data").show();
        }


       searchBar.setOnAction(event ->{
            try{
                searchReservations();
            }catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error searching reservation").show();
            }
        });


        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }

    }

    private void loadComboBoxData() throws SQLException {
        ObservableList<String> customerId = FXCollections.observableArrayList(reservationModel.getAllCustomerIds());
        ObservableList<String> driverId = FXCollections.observableArrayList(reservationModel.getAllDriversIds());
        ObservableList<String> vehicleId = FXCollections.observableArrayList(reservationModel.getAllVehicleIds());

        cmbCustomerId.setItems(customerId);
        cmbDriverId.setItems(driverId);
        cmbVehiicleId.setItems(vehicleId);
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextReservationId();
        loadTableData();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        rdbDone.setSelected(false);
        rdbPending.setSelected(false);
        rdbDone.setDisable(false);
        rdbPending.setDisable(false);

        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";

        txtStartDate.setStyle(defaultStyle);
        txtEndDate.setStyle(defaultStyle);
        txtMileage.setStyle(defaultStyle);

        txtStartDate.setText("");
        txtEndDate.setText("");
        txtMileage.setText("");


    }

    public void loadNextReservationId() throws SQLException {
        String nextReservationId = reservationModel.getNextReservationId();
        lblReservationId.setText(nextReservationId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ReservationDTO> reservationDTOS = reservationModel.getAllReservations();
        ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();

        for (ReservationDTO reservationDTO : reservationDTOS) {
            ReservationTM reservationTM = new ReservationTM(
                    reservationDTO.getId(),
                    reservationDTO.getStartDate(),
                    reservationDTO.getEndDate(),
                    reservationDTO.getEstimatedMileage(),
                    reservationDTO.getCustomerId(),
                    reservationModel.getCustomerNameById(reservationDTO.getCustomerId()),
                    reservationDTO.getVehicleId(),
                    reservationModel.getVehicleNameById(reservationDTO.getVehicleId()),
                    reservationDTO.getDriverId(),
                    reservationModel.getDriverNameById(reservationDTO.getDriverId()),
                    reservationDTO.getStatus()
            );
            reservationTMS.add(reservationTM);
        }
        reservationTable.setItems(reservationTMS);

    }

    private void searchReservations() throws SQLException, ClassNotFoundException {
        String searchText = searchBar.getText().trim();

        if(searchText.isEmpty()){
            loadTableData();
            return;
        }

        ArrayList<ReservationDTO> reservationDTOS = reservationModel.getReservationsBySearch(searchText);

        // Populate the table with filtered data
        ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();

        for (ReservationDTO reservationDTO : reservationDTOS) {
            ReservationTM reservationTM = new ReservationTM(
                    reservationDTO.getId(),
                    reservationDTO.getStartDate(),
                    reservationDTO.getEndDate(),
                    reservationDTO.getEstimatedMileage(),
                    reservationDTO.getCustomerId(),
                    reservationModel.getCustomerNameById(reservationDTO.getCustomerId()),
                    reservationDTO.getVehicleId(),
                    reservationModel.getVehicleNameById(reservationDTO.getVehicleId()),
                    reservationDTO.getDriverId(),
                    reservationModel.getDriverNameById(reservationDTO.getDriverId()),
                    reservationDTO.getStatus()
            );
            reservationTMS.add(reservationTM);
        }
        reservationTable.setItems(reservationTMS);
    }


}
