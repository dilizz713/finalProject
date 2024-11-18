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
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.ReservationTM;
import lk.ijse.gdse71.finalproject.dto.tm.VehicleTM;
import lk.ijse.gdse71.finalproject.model.ReservationModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationTableController implements Initializable {

    @FXML
    public TableColumn<ReservationTM, Void> colAction;
    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<ReservationTM, String> colCustomer;

    @FXML
    private TableColumn<ReservationTM, LocalDate> colEndDate;

    @FXML
    private TableColumn<ReservationTM, String> colModel;

    @FXML
    private TableColumn<ReservationTM, Double> colPrice;

    @FXML
    private TableColumn<ReservationTM, LocalDate> colReservationDate;

    @FXML
    private TableColumn<ReservationTM, String> colReservationId;

    @FXML
    private TableColumn<ReservationTM, LocalDate> colStartDate;

    @FXML
    private TableColumn<ReservationTM, String> colStatus;

    @FXML
    private TableColumn<ReservationTM, String> colVehicleId;

    @FXML
    private TableView<ReservationTM> reservationTable;

    @FXML
    private AnchorPane reservationTableAnchorPane;

    @FXML
    private TextField searchBar;

    private ReservationTM selectedReservationTM;

    ReservationModel reservationModel = new ReservationModel();

    @FXML
    void clickedTable(MouseEvent event) {

        selectedReservationTM = reservationTable.getSelectionModel().getSelectedItem();
        if(selectedReservationTM != null){
            btnDelete.setDisable(false);

        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        if (selectedReservationTM == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a reservation to delete!").show();
            return;
        }

        // Confirm with the user
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    // Delete the selected reservation from the database
                    boolean deleted = reservationModel.deleteReservation(selectedReservationTM.getId());

                    if (deleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Reservation deleted successfully.").show();
                        try {
                            refreshPage();  // Refresh the table to reflect changes
                        } catch (SQLException e) {
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "Failed to reload reservation data.").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete reservation.").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error while deleting reservation.").show();
                }
            }
        });
    }

    @FXML
    void navigateToVehicleView(ActionEvent event) {
        try{
            reservationTableAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/reservation-view.fxml"));
            reservationTableAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }
    /*@FXML
    public void updateOnAction(ActionEvent actionEvent) {
        ReservationTM reservationTM = (ReservationTM) reservationTable.getSelectionModel().getSelectedItems();
        if(reservationTM != null){
            new Alert(Alert.AlertType.WARNING, "Please select a reservation to update!").show();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reservation-view.fxml"));
            AnchorPane reservationPane = loader.load();
            ReservationController controller = loader.getController();

            // Pass data to ReservationController
            controller.loadReservationDetails(reservationTM);
            controller.setReservationTableController(this);

            reservationTableAnchorPane.getChildren().clear();
            reservationTableAnchorPane.getChildren().add(reservationPane);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load reservation view!").show();
        }
    }
*/

    @FXML
    void updateOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        if (selectedReservationTM == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a reservation to update!").show();
            return;
        }

        ReservationDTO selectedReservation = reservationModel.getReservationById(selectedReservationTM.getId());

        // Open the Reservation UI and pass the reservation details for editing
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reservation-view.fxml"));
        AnchorPane reservationPane = loader.load();
        ReservationController controller = loader.getController();

        // Pass the reservation details to the ReservationController for displaying
        controller.setReservationDetails(selectedReservation);
        controller.setReservationTableController(this); // Allow it to update the table once the reservation is saved

        reservationTableAnchorPane.getChildren().clear();
        reservationTableAnchorPane.getChildren().add(reservationPane);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("numberPlate"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("updateButton"));

        try {
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle data").show();
        }

        try {
            refreshPage();
        } catch (SQLException  e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }

        btnDelete.setDisable(true);



    }

    private void refreshPage() throws SQLException {
        loadTableData();
    }

    private void loadTableData() throws SQLException {
        ArrayList<ReservationDTO> reservationDTOS = reservationModel.getAllReservations();
        ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();

        for(ReservationDTO reservationDTO : reservationDTOS){

            String customerName = reservationModel.getCustomerNameById(reservationDTO.getCustomerId());
            String model = reservationModel.getVehicleNameById(reservationDTO.getVehicleId());
            String price = reservationModel.getVehiclePriceById(reservationDTO.getVehicleId());
            String numberPlate = reservationModel.getNumberPlateById(reservationDTO.getVehicleId());

            double vehiclePrice = 0;
            if (price != null && !price.isEmpty()) {
                try {
                    vehiclePrice = Double.parseDouble(price);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.WARNING, "Invalid price format for vehicle ID: " + reservationDTO.getVehicleId()).show();
                }
            }

            Button updateButton = new Button("Update");

            updateButton.setOnAction(event -> {
                try {
                    openReservationUpdateView(reservationDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to load update view").show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            ReservationTM reservationTM = new ReservationTM(
                    reservationDTO.getId(),
                    reservationDTO.getReservationDate(),
                    customerName,
                    numberPlate,
                    model,
                    reservationDTO.getStartDate(),
                    reservationDTO.getEndDate(),
                    vehiclePrice,
                    reservationDTO.getStatus(),
                    updateButton
            );

            reservationTMS.add(reservationTM);
        }
        reservationTable.setItems(reservationTMS);

    }
    private void openReservationUpdateView(ReservationDTO reservationDTO) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reservation-view.fxml"));
        AnchorPane pane = loader.load();

        ReservationController controller = loader.getController();
        controller.setReservationDetails(reservationDTO);  // Set data to be edited
        controller.setReservationTableController(this);

        reservationTableAnchorPane.getChildren().clear();  // Display the view
        reservationTableAnchorPane.getChildren().add(pane);  // Display the view
    }
}

   /* public  void refreshTable(){
        try{
            ArrayList<ReservationDTO> reservationDTOS = new ReservationModel().getAllReservations();
            ObservableList<ReservationTM> reservationList = FXCollections.observableArrayList();

            for(ReservationDTO reservationDTO : reservationDTOS){
                reservationList.add(new ReservationTM(
                        reservationDTO.getId(),
                        reservationDTO.getReservationDate(),
                        reservationDTO.getCustomerId(),
                        new ReservationModel().getCustomerNameById(reservationDTO.getCustomerId()),
                        reservationDTO.getVehicleId(),
                        new ReservationModel().getVehicleNameById(reservationDTO.getVehicleId()),
                        reservationDTO.getStartDate(),
                        reservationDTO.getEndDate(),
                        Double.parseDouble(new ReservationModel().getVehiclePriceById(Double.parseDouble(reservationDTO.getVehicleId()))),
                        reservationDTO.getStatus()
                ));
            }
            reservationTable.setItems(reservationList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load reservations.").show();
        }
    }*/

