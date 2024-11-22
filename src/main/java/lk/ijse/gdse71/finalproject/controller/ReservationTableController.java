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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationTableController implements Initializable {

    @FXML
    public TableColumn<ReservationTM, Void> colAction;
    @FXML
    public TableColumn<ReservationTM, Void> colAddMileage;
    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<ReservationTM, String> colCustomer;

    @FXML
    private TableColumn<ReservationTM, String> colModel;

    @FXML
    private TableColumn<ReservationTM, Double> colPrice;

    @FXML
    private TableColumn<ReservationTM, LocalDate> colReservationDate;

    @FXML
    private TableColumn<ReservationTM, String> colReservationId;

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

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean deleted = reservationModel.deleteReservation(selectedReservationTM.getId());

                    if (deleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Reservation deleted successfully.").show();
                        try {
                            refreshPage();
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
       navigateTo("/view/newReservation.fxml");
    }

    public void navigateTo(String fxmlPath){
        try{
            reservationTableAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
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

    @FXML
    void updateOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        if (selectedReservationTM == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a reservation to update!").show();
            return;
        }

        ReservationDTO selectedReservation = reservationModel.getReservationById(selectedReservationTM.getId());


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newReservation.fxml"));
        AnchorPane reservationPane = loader.load();
        NewReservationController controller = loader.getController();


        controller.setReservationDetails(selectedReservation);
        controller.setReservationTableController(this);

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
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colAddMileage.setCellValueFactory(new PropertyValueFactory<>("addMileageButton"));

        try {
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle data").show();
        }
        searchBar.setOnAction(event ->{
            try{
                searchReservation();
            }catch (SQLException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error searching reservation").show();
            }
        });

        try {
            refreshPage();
        } catch (SQLException  e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }

        btnDelete.setDisable(true);



    }

    private void searchReservation() throws SQLException {
        String searchText = searchBar.getText().trim();

        if(searchText.isEmpty()){
            loadTableData();
            return;
        }

        ArrayList<ReservationDTO> reservationDTOS = reservationModel.getReservationsBySearch(searchText);
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
            Button addMileageButton = new Button("Add");

            updateButton.setStyle("-fx-text-fill: black; -fx-font-weight: bold;-fx-background-color: white; -fx-border-radius: 1 1 1 1;-fx-start-margin:2;-fx-end-margin: 2;  -fx-background-radius: 1 1 1 1;-fx-border-color:#6b6e76;-fx-font-size: 12px; ");

            addMileageButton.setStyle("-fx-text-fill: black; -fx-font-weight: bold;-fx-background-color: white; -fx-border-radius: 1 1 1 1;-fx-start-margin:2;-fx-end-margin: 2;  -fx-background-radius: 1 1 1 1;-fx-border-color:#6b6e76;-fx-font-size: 12px; ");



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

            String status = reservationDTO.getStatus();

            if(status.equals("Pending")){
                addMileageButton.setDisable(true);
            }
            addMileageButton.setOnAction(event -> {
                navigateTo("/view/mileage-tracking-view.fxml");

            });

            ReservationTM reservationTM = new ReservationTM(
                    reservationDTO.getId(),
                    reservationDTO.getReservationDate(),
                    customerName,
                    numberPlate,
                    model,
                    vehiclePrice,
                    reservationDTO.getStatus(),
                    updateButton,
                    addMileageButton

            );

            reservationTMS.add(reservationTM);
        }
        reservationTable.setItems(reservationTMS);

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
            Button addMileageButton = new Button("Add");

            updateButton.setStyle("-fx-text-fill: black; -fx-font-weight: bold;-fx-background-color: white; -fx-border-radius: 1 1 1 1;-fx-start-margin:2;-fx-end-margin: 2;  -fx-background-radius: 1 1 1 1;-fx-border-color:#6b6e76;-fx-font-size: 12px; ");

            addMileageButton.setStyle("-fx-text-fill: black; -fx-font-weight: bold;-fx-background-color: white; -fx-border-radius: 1 1 1 1;-fx-start-margin:2;-fx-end-margin: 2;  -fx-background-radius: 1 1 1 1;-fx-border-color:#6b6e76;-fx-font-size: 12px; ");



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

            String status = reservationDTO.getStatus();

            if(status.equals("Pending")){
                addMileageButton.setDisable(true);
            }
            addMileageButton.setOnAction(event -> {
                navigateTo("/view/mileage-tracking-view.fxml");

            });

            ReservationTM reservationTM = new ReservationTM(
                    reservationDTO.getId(),
                    reservationDTO.getReservationDate(),
                    customerName,
                    numberPlate,
                    model,
                    vehiclePrice,
                    reservationDTO.getStatus(),
                    updateButton,
                    addMileageButton

            );

            reservationTMS.add(reservationTM);
        }
        reservationTable.setItems(reservationTMS);

    }

    private void openReservationUpdateView(ReservationDTO reservationDTO) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newReservation.fxml"));
        AnchorPane pane = loader.load();

        NewReservationController  controller = loader.getController();

        controller.setReservationDetails(reservationDTO);
        controller.setReservationTableController(this);


        reservationTableAnchorPane.getChildren().clear();
        reservationTableAnchorPane.getChildren().add(pane);
    }

    public void refreshTable() throws SQLException {
        loadTableData();
    }
}



