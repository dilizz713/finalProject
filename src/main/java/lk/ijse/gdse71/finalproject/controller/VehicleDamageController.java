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
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDamageDTO;
import lk.ijse.gdse71.finalproject.dto.tm.PaymentTM;
import lk.ijse.gdse71.finalproject.dto.tm.VehicleDamageTM;
import lk.ijse.gdse71.finalproject.model.CustomerModel;
import lk.ijse.gdse71.finalproject.model.VehicleDamageModel;
import lk.ijse.gdse71.finalproject.model.VehicleModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleDamageController implements Initializable {

    @FXML
    private TableView<VehicleDamageTM> DamageRecordsTable;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbCustomerName;

    @FXML
    private ComboBox<String> cmbVehicleId;

    @FXML
    private TableColumn<VehicleDamageTM, Double> colCost;

    @FXML
    private TableColumn<VehicleDamageTM, String> colCustomerId;

    @FXML
    private TableColumn<VehicleDamageTM, String> colCustomerName;

    @FXML
    private TableColumn<VehicleDamageTM, String> colDamageId;

    @FXML
    private TableColumn<VehicleDamageTM, LocalDate> colDate;

    @FXML
    private TableColumn<VehicleDamageTM, String> colDesc;

    @FXML
    private TableColumn<VehicleDamageTM, String> colModel;

    @FXML
    private TableColumn<VehicleDamageTM, String> colVehicleId;

    @FXML
    private AnchorPane damageAnchorPAne;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblId;

    @FXML
    private Label lblModel;

    @FXML
    private TextArea txtAreaDesc;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtSearchBar;

    VehicleModel vehicleModel = new VehicleModel();
    CustomerModel customerModel = new CustomerModel();
    VehicleDamageModel vehicleDamageModel = new VehicleDamageModel();

    @FXML
    void SaveOnAction(ActionEvent event) {
        String id = lblId.getText();
        String desc = txtAreaDesc.getText();
        String costText = txtCost.getText();
        String model = lblModel.getText();
        String vehicleId = cmbVehicleId.getValue();
        String customerName = cmbCustomerName.getValue();
        String customerId = lblCustomerId.getText();
        String date = lblDate.getText();

        LocalDate reportedDate = LocalDate.now();
        reportedDate = LocalDate.parse(date);

        if (vehicleId == null || txtCost.getText().isEmpty() || customerName == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle id, customer name and enter an amount.").show();
            return;
        }
        double amount =  0;
        try{
            amount = Double.parseDouble(costText);
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid amount entered!").show();
            return;
        }


        VehicleDamageDTO vehicleDamageDTO = new VehicleDamageDTO(id, desc, reportedDate, amount, vehicleId);

        try {
            boolean isSaved = vehicleDamageModel.saveVehicleDamage(vehicleDamageDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Damage record saved successfully.").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save record.").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
        }


    }

    @FXML
    void clickedTable(MouseEvent event) {
        VehicleDamageTM selectedItem = DamageRecordsTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblId.setText(selectedItem.getId());
            txtAreaDesc.setText(selectedItem.getDescription());
            txtCost.setText(String.valueOf(selectedItem.getRepairCost()));
            cmbVehicleId.setValue(selectedItem.getVehicleId());
            lblModel.setText(selectedItem.getModel());
            cmbCustomerName.setValue(selectedItem.getCustomerName());
            lblCustomerId.setText(selectedItem.getCustomerId());
            lblDate.setText(selectedItem.getReportedDate().toString());


            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException {
        String damageId = lblId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this record?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = vehicleDamageModel.deleteRecord(damageId);
            if(isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Record deleted!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to delete record!").show();
            }
        }

    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void selectVehicleId(ActionEvent event) {
        try {
            String selectedVehicleId = cmbVehicleId.getSelectionModel().getSelectedItem();
            if (selectedVehicleId != null) {

                String vehicleModelStr = vehicleModel.getVehicleModelById(selectedVehicleId);
                lblModel.setText(vehicleModelStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load vehicle model").show();
        }
    }
    @FXML
    public void selectCustomerName(ActionEvent actionEvent) {
        try {
            String selectedCustomerName = cmbCustomerName.getSelectionModel().getSelectedItem();
            if (selectedCustomerName != null) {
                // Retrieve the customer ID for the selected customer name and set it to lblCustomerId
                String customerId = customerModel.getCustomerIdByName(selectedCustomerName);
                lblCustomerId.setText(customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ID").show();
        }
    }



    @FXML
    void updateOnAction(ActionEvent event) {
        String id = lblId.getText();
        String desc = txtAreaDesc.getText();
        String costText = txtCost.getText();
        String vehicleId = cmbVehicleId.getValue();
        String customerName = cmbCustomerName.getValue();
        String date = lblDate.getText();

        LocalDate reportedDate = LocalDate.now();
        reportedDate = LocalDate.parse(date);

        if (vehicleId == null || txtCost.getText().isEmpty() || customerName == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle id, customer name and enter an amount.").show();
            return;
        }
        double amount =  0;
        try{
            amount = Double.parseDouble(costText);
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid amount entered!").show();
            return;
        }


        VehicleDamageDTO vehicleDamageDTO = new VehicleDamageDTO(id, desc, reportedDate, amount, vehicleId);

        try {
            boolean isUpdated = vehicleDamageModel.updateVehicleDamage(vehicleDamageDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Damage record updated successfully.").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update record.").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDamageId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("reportedDate"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        lblDate.setText(LocalDate.now().toString());

        try {
            loadVehicleIds();
            loadCustomerName();
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load combo box data id").show();
        }

    }

    private void refreshPage() throws SQLException {
        loadNextDamageId();
        loadTableData();

        String defaultStyle = "-fx-border-color: white; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-width:  0 0 1";

        txtCost.setStyle(defaultStyle);

        lblCustomerId.setText("");
        lblModel.setText("");


        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void loadTableData() throws SQLException {
        ArrayList<VehicleDamageDTO> vehicleDamageDTOS = vehicleDamageModel.getAllDamageVehicles();
        ObservableList<VehicleDamageTM> vehicleDamageTMS = FXCollections.observableArrayList();

        for(VehicleDamageDTO vehicleDamageDTO : vehicleDamageDTOS){
          String model = vehicleModel.getVehicleModelById(vehicleDamageDTO.getVehicleId());

          String customerId = vehicleDamageModel.getCustomerIdByVehicleId(vehicleDamageDTO.getVehicleId());

          String customerName = customerModel.getCustomerNameById(customerId);


            VehicleDamageTM vehicleDamageTM = new VehicleDamageTM(
                    vehicleDamageDTO.getId(),
                    vehicleDamageDTO.getDescription(),
                    vehicleDamageDTO.getReportedDate(),
                    vehicleDamageDTO.getRepairCost(),
                    vehicleDamageDTO.getVehicleId(),
                    model,
                    customerId,
                    customerName
            );

            vehicleDamageTMS.add(vehicleDamageTM);
        }

        DamageRecordsTable.setItems(vehicleDamageTMS);
    }

    private void loadNextDamageId() throws SQLException {
        String nextDamageID = vehicleDamageModel.getNextDamageId();
        lblId.setText(nextDamageID);
    }

    private void loadCustomerName() throws SQLException {
        ArrayList<String> customerName = customerModel.getAllCustomerNames();
        cmbCustomerName.setItems(FXCollections.observableArrayList(customerName));
    }


    private void loadVehicleIds() throws SQLException {
        ArrayList<String> vehicleId = vehicleModel.getAllVehcileIds();
        cmbVehicleId.setItems(FXCollections.observableArrayList(vehicleId));
    }


}
