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
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.tm.MileageTrackingTM;
import lk.ijse.gdse71.finalproject.model.MileageTrackingModel;
import lk.ijse.gdse71.finalproject.model.ReservationModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MileageTrackingController implements Initializable {

    @FXML
    private TableView<MileageTrackingTM> TrackingTable;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbReservationId;

    @FXML
    private TableColumn<MileageTrackingDTO, Double>colActualMileage;

    @FXML
    private TableColumn<MileageTrackingDTO, Double> colEndMile;

    @FXML
    private TableColumn<MileageTrackingDTO, Double> colEstimatedMile;

    @FXML
    private TableColumn<MileageTrackingDTO, Double> colEstimatedMileageCost;

    @FXML
    private TableColumn<MileageTrackingDTO, Double> colExtraChargesPerKm;

    @FXML
    private TableColumn<MileageTrackingDTO, String> colReservationId;

    @FXML
    private TableColumn<MileageTrackingDTO, Double> colStartMile;

    @FXML
    private TableColumn<MileageTrackingDTO, Double> colTotalExtraCharges;

    @FXML
    private TableColumn<MileageTrackingDTO, String> colTrackingId;

    @FXML
    private Label lblActualMile;

    @FXML
    private Label lblEstimatedMile;

    @FXML
    private Label lblEstimatedMileCost;

    @FXML
    private Label lblTotalExtraCharges;

    @FXML
    private Label lblTrackingId;

    @FXML
    private AnchorPane mileageTrackingAnchorPane;

    @FXML
    private TextField txtEndMile;

    @FXML
    private TextField txtExtraChargesPerKm;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private TextField txtStartMile;

    MileageTrackingModel mileageTrackingModel = new MileageTrackingModel();

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String trackingId = lblTrackingId.getText();
        String reservationId = cmbReservationId.getValue();
        String startDateMileageText = txtStartMile.getText();


        double startDateMileage = 0;
        startDateMileage = Double.parseDouble(startDateMileageText);




        MileageTrackingDTO mileageTrackingDTO = new MileageTrackingDTO(trackingId, 0,0, 0, 0, reservationId, startDateMileage, 0, 0);
        boolean isSaved = mileageTrackingModel.saveRecords(mileageTrackingDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Tracking details saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save tracking details!").show();
        }

    }

    @FXML
    void clickedTable(MouseEvent event) throws SQLException {
        MileageTrackingTM mileageTrackingTM = TrackingTable.getSelectionModel().getSelectedItem();
        if (mileageTrackingTM != null) {
            lblTrackingId.setText(mileageTrackingTM.getTrackingId());
            cmbReservationId.setValue(mileageTrackingTM.getReservationId());
            txtStartMile.setText(String.valueOf(mileageTrackingTM.getStartDateMileage()));
            txtEndMile.setText(String.valueOf(mileageTrackingTM.getEndDateMileage()));
            lblEstimatedMile.setText(String.valueOf(mileageTrackingTM.getEstimatedMileage()));
            lblActualMile.setText(String.valueOf(mileageTrackingTM.getActualMileage()));
            lblEstimatedMileCost.setText(String.valueOf(mileageTrackingTM.getEstimatedMileageCost()));
            txtExtraChargesPerKm.setText(String.valueOf(mileageTrackingTM.getExtraChargePerKm()));
            lblTotalExtraCharges.setText(String.valueOf(mileageTrackingTM.getTotalExtraCharges()));

            calculateAndDisplayLabels(mileageTrackingTM);


            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void calculateAndDisplayLabels(MileageTrackingTM mileageTrackingTM) throws SQLException {
        try{
            double estimatedMileage = calculateEstimatedMileage(mileageTrackingTM.getReservationId());
            double actualMileage = mileageTrackingTM.getEndDateMileage() - mileageTrackingTM.getStartDateMileage();
            double totalExtraCharges = (actualMileage - estimatedMileage) * mileageTrackingTM.getExtraChargePerKm();

            lblEstimatedMile.setText(String.format("%.2f", estimatedMileage));
            lblActualMile.setText(String.format("%.2f", actualMileage));
            lblTotalExtraCharges.setText(String.format("%.2f", totalExtraCharges));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error calculating mileage details").show();

        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String trackingId = lblTrackingId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this record?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = mileageTrackingModel.deleteRecords(trackingId);
            if(isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Record deleted!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to delete record!").show();
            }
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String trackingId = lblTrackingId.getText();
        String reservationId = cmbReservationId.getValue();
        String startDateMileageText = txtStartMile.getText();
        String endDateMileageText = txtEndMile.getText();
        String extraChargePerKmText = txtExtraChargesPerKm.getText();


        double startDateMileage = 0 , endDateMileage = 0, extraChargePerKm = 0 , estimatedMileage = 0 , actualMileage = 0, estimatedMileageCost = 0, totalExtraCharges = 0;


        try{
            startDateMileage = Double.parseDouble(startDateMileageText);
            endDateMileage = Double.parseDouble(endDateMileageText);
            extraChargePerKm = Double.parseDouble(extraChargePerKmText);



            estimatedMileage = calculateEstimatedMileage(reservationId);
            actualMileage = endDateMileage - startDateMileage;
            estimatedMileageCost = calculateEstimatedMileageCost(reservationId, estimatedMileage);
            totalExtraCharges = (actualMileage - estimatedMileage) * extraChargePerKm;

            System.out.println("estimated mileage : " + estimatedMileage);
            System.out.println("actual mileage : " + actualMileage);
            System.out.println("estimated mileage cost : " + estimatedMileageCost);
            System.out.println("total extra charges : " + totalExtraCharges);


            lblEstimatedMile.setText(String.format("%.2f", estimatedMileage));
            lblActualMile.setText(String.format("%.2f", actualMileage));
            lblTotalExtraCharges.setText(String.format("%.2f", totalExtraCharges));
            lblEstimatedMileCost.setText(String.format("%.2f", estimatedMileageCost));




            MileageTrackingDTO mileageTrackingDTO = new MileageTrackingDTO(trackingId, estimatedMileage,actualMileage, extraChargePerKm, totalExtraCharges, reservationId, startDateMileage, endDateMileage, estimatedMileageCost);
            boolean isUpdated = mileageTrackingModel.updateRecords(mileageTrackingDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Tracking details updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update tracking details!").show();
            }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "please enter valid numbers for mileage and extra charges");
        }

    }

    private double calculateEstimatedMileageCost(String reservationId, double estimatedMileage) throws SQLException {
        ReservationModel reservationModel = new ReservationModel();

        ReservationDTO reservationDTO = reservationModel.getReservationDetails(reservationId);
        long durationDays = 0;
        if(reservationDTO != null){
           durationDays = reservationDTO.getEndDate().toEpochDay() - reservationDTO.getStartDate().toEpochDay();
        }
        String vehiclePrice =(reservationModel.getVehiclePrice(reservationId));
        double price = 0;
        price = Double.parseDouble(vehiclePrice);

        return  durationDays * price;
    }

    private double calculateEstimatedMileage(String reservationId) throws SQLException {
        ReservationModel reservationModel = new ReservationModel();
        ReservationDTO reservationDTO = reservationModel.getReservationDetails(reservationId);

        if(reservationDTO != null){
            System.out.println("Start Date: " + reservationDTO.getStartDate());
            System.out.println("End Date: " + reservationDTO.getEndDate());
            long durationDays = reservationDTO.getEndDate().toEpochDay() - reservationDTO.getStartDate().toEpochDay();
            System.out.println("Duration Days: " + durationDays);

            if(durationDays == 0){
                System.out.println("start and end date are the same");
            }
            return durationDays * 100;
        }
        return 0;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTrackingId.setCellValueFactory(new PropertyValueFactory<>("trackingId"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colStartMile.setCellValueFactory(new PropertyValueFactory<>("startDateMileage"));
        colEndMile.setCellValueFactory(new PropertyValueFactory<>("endDateMileage"));
        colEstimatedMile.setCellValueFactory(new PropertyValueFactory<>("estimatedMileage"));
        colActualMileage.setCellValueFactory(new PropertyValueFactory<>("actualMileage"));
        colEstimatedMileageCost.setCellValueFactory(new PropertyValueFactory<>("estimatedMileageCost"));
        colExtraChargesPerKm.setCellValueFactory(new PropertyValueFactory<>("extraChargePerKm"));
        colTotalExtraCharges.setCellValueFactory(new PropertyValueFactory<>("totalExtraCharges"));

        String defaultStyle = "-fx-border-color: white; -fx-text-fill: white; -fx-background-color: transparent;-fx-border-width: 0 0 1 0;";

        txtStartMile.setStyle(defaultStyle);
        txtEndMile.setStyle(defaultStyle);
        txtExtraChargesPerKm.setStyle(defaultStyle);



        try {
            loadComboBoxData();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading combobox data").show();
        }


        /*searchBar.setOnAction(event ->{
            try{
                searchReservations();
            }catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error searching reservation").show();
            }
        });*/


        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }
    }

    private void loadComboBoxData() throws SQLException {
        ObservableList<String> reservationId = FXCollections.observableArrayList(mileageTrackingModel.getAllReservationIds());
        cmbReservationId.setItems(reservationId);

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextTrackingId();
        loadTableData();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtStartMile.clear();
        txtEndMile.clear();
        txtExtraChargesPerKm.clear();


    }

    public void loadNextTrackingId() throws SQLException {
        String nextTrackingId = mileageTrackingModel.getNextTrackingId();
        lblTrackingId.setText(nextTrackingId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<MileageTrackingDTO> mileageTrackingDTOS = mileageTrackingModel.getAllTrakingDetails();
        ObservableList<MileageTrackingTM> mileageTrackingTMS = FXCollections.observableArrayList();

        for (MileageTrackingDTO mileageTrackingDTO : mileageTrackingDTOS) {
            MileageTrackingTM mileageTrackingTM = new MileageTrackingTM(
                    mileageTrackingDTO.getId(),
                    mileageTrackingDTO.getReservationId(),
                    mileageTrackingDTO.getStartDateMileage(),
                    mileageTrackingDTO.getEndDateMileage(),
                    mileageTrackingDTO.getEstimatedMileage(),
                    mileageTrackingDTO.getActualMileage(),
                    mileageTrackingDTO.getEstimatedMileageCost(),
                    mileageTrackingDTO.getExtraChargePerKm(),
                    mileageTrackingDTO.getTotalExtraCharges()
            );
            mileageTrackingTMS.add(mileageTrackingTM);
        }
        TrackingTable.setItems(mileageTrackingTMS);

    }

}
