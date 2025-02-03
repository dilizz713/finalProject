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
import lk.ijse.gdse71.finalproject.bo.BOFactory;
import lk.ijse.gdse71.finalproject.bo.custom.MaintenanceRecordBO;
import lk.ijse.gdse71.finalproject.bo.custom.VehicleBO;
import lk.ijse.gdse71.finalproject.bo.custom.impl.MaintenanceRecordBOImpl;
import lk.ijse.gdse71.finalproject.bo.custom.impl.VehicleBOImpl;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.view.tdm.MaintenanceRecordTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MaintenanceRecordsHistoryController implements Initializable {

    @FXML
    private TableView<MaintenanceRecordTM> MaintenanceTable;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<MaintenanceRecordTM, Void> colAction;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colDesc;

    @FXML
    private TableColumn<MaintenanceRecordTM, LocalDate> colEndDate;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colId;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colModel;

    @FXML
    private TableColumn<MaintenanceRecordTM, LocalDate> colStartDate;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colStatus;

    @FXML
    private TableColumn<MaintenanceRecordTM, String> colVehicleId;

    @FXML
    private AnchorPane recordHistoryAnchorPane;

    @FXML
    private TextField txtSearchBar;

    private MaintenanceRecordTM maintenanceRecordTM;

    MaintenanceRecordBO maintenanceRecordBO = (MaintenanceRecordBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MAINTENANCERECORD);
    VehicleBO vehicleBO = (VehicleBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.VEHICLE);

    @FXML
    void clickedTable(MouseEvent event) {
        maintenanceRecordTM = MaintenanceTable.getSelectionModel().getSelectedItem();
        if(maintenanceRecordTM != null){
            btnDelete.setDisable(false);

        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        if (maintenanceRecordTM == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a record to delete!").show();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this record?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    //***
                    boolean deleted = maintenanceRecordBO.deleteMaintenanceRecords(maintenanceRecordTM.getId());

                    if (deleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Record deleted successfully.").show();
                        try {
                            refreshPage();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "Failed to reload record data.").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete record.").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error while deleting record.").show();
                }
            }
        });
    }

    @FXML
    void navigateToMaintenanceRecordView(ActionEvent event) {
        try{
            recordHistoryAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/vehicle-maintenance-records-view.fxml"));
            recordHistoryAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }

    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    private void refreshPage() throws SQLException {
        loadTableData();
    }

    private void loadTableData() throws SQLException {
        //****
        ArrayList<MaintenanceRecordDTO> maintenanceRecordDTOS = maintenanceRecordBO.getAllMaintenanceRecords();
        ObservableList<MaintenanceRecordTM> maintenanceRecordTMS = FXCollections.observableArrayList();

        for(MaintenanceRecordDTO maintenanceRecordDTO : maintenanceRecordDTOS){

            String model = vehicleBO.getVehicleModelById(maintenanceRecordDTO.getVehicleId());

            Button updateButton = new Button("Update");

            updateButton.setStyle("-fx-text-fill: black; -fx-font-weight: bold;-fx-background-color: white; -fx-border-radius: 1 1 1 1;-fx-start-margin:2;-fx-end-margin: 2;  -fx-background-radius: 1 1 1 1;-fx-border-color:#6b6e76;-fx-font-size: 12px; ");


            updateButton.setOnAction(event -> {
                try {
                    openReservationUpdateView(maintenanceRecordDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to load update view").show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            LocalDate endDate = maintenanceRecordDTO.getEndDate();

            MaintenanceRecordTM maintenanceRecordTM = new MaintenanceRecordTM(
                    maintenanceRecordDTO.getId(),
                    maintenanceRecordDTO.getStartDate(),
                    endDate,
                    maintenanceRecordDTO.getVehicleId(),
                    model,
                    maintenanceRecordDTO.getDescription(),
                    maintenanceRecordDTO.getStatus(),
                    updateButton
            );

            maintenanceRecordTMS.add(maintenanceRecordTM);
        }

        MaintenanceTable.setItems(maintenanceRecordTMS);

    }

    @FXML
    void updateOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        if (maintenanceRecordTM == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a record to update!").show();
            return;
        }

        //*****
        MaintenanceRecordDTO selectedRecord = maintenanceRecordBO.getMaintenanceRecordsById(maintenanceRecordTM.getId());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reservation-view.fxml"));
        AnchorPane recordPane = loader.load();
        VehicleMaintenanceRecordsController controller = loader.getController();

        controller.setRecordDetails(selectedRecord);
        controller.setMaintenanceRecordsHistoryController(this);

        recordHistoryAnchorPane.getChildren().clear();
        recordHistoryAnchorPane.getChildren().add(recordPane);
    }

    private void openReservationUpdateView(MaintenanceRecordDTO maintenanceRecordDTO) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vehicle-maintenance-records-view.fxml"));
        AnchorPane pane = loader.load();

        VehicleMaintenanceRecordsController controller = loader.getController();



        controller.setRecordDetails(
                maintenanceRecordDTO.getId(),
                maintenanceRecordDTO.getDescription(),
                maintenanceRecordDTO.getVehicleId(),
                //***
                vehicleBO.getVehicleModelById(maintenanceRecordDTO.getVehicleId()),
                maintenanceRecordDTO.getStatus(),
                maintenanceRecordDTO.getStartDate(),
                maintenanceRecordDTO.getEndDate()

        );
        controller.setMaintenanceRecordsHistoryController(this);

        recordHistoryAnchorPane.getChildren().clear();  // Display the view
        recordHistoryAnchorPane.getChildren().add(pane);  //
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
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
}
