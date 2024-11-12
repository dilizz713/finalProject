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
import javafx.scene.layout.HBox;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.DriverDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.CustomerTM;
import lk.ijse.gdse71.finalproject.dto.tm.DriverTM;
import lk.ijse.gdse71.finalproject.dto.tm.VehicleTM;
import lk.ijse.gdse71.finalproject.model.DriverModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DriverController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<DriverTM, String> colDriverId;

    @FXML
    private TableColumn<DriverTM, String > colLicenceNum;

    @FXML
    private TableColumn<DriverTM, String> colName;

    @FXML
    private TableColumn<DriverTM, String> colNic;

    @FXML
    private TableColumn<DriverTM, Integer> colPhone;

    @FXML
    private AnchorPane driverAnchorPane;

    @FXML
    private TableView<DriverTM> driverTable;

    @FXML
    private Label lblDriverId;

    @FXML
    private TextField txtLicenceNum;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSearchBar;

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Get field values
        String id = lblDriverId.getText();
        String name = txtName.getText();
        String licenceNumber = txtLicenceNum.getText();
        String phoneText = txtPhone.getText();
        String nic = txtNic.getText();

        // Validation patterns
        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]$|^[0-9]{12}$";


        // Reset error indicators
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        String errorStyle = "-fx-border-color: red; -fx-text-fill: white; -fx-background-color: transparent;";
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-width: 0 0 1 0";

        // Check and apply error style if validation fails
        if (name.isEmpty() || !name.matches(namePattern)) {
            txtName.setStyle(errorStyle);
            errorMessage.append("- Name is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtName.setStyle(defaultStyle);
        }

        if (licenceNumber.isEmpty()) {
            txtLicenceNum.setStyle(errorStyle);
            errorMessage.append("- Licence number is empty\n");
            hasErrors = true;
        }else{
            txtLicenceNum.setStyle(defaultStyle);
        }

        int phone = -1;
        try {
            phone = Integer.parseInt(phoneText);
            txtPhone.setStyle(defaultStyle);
        } catch (NumberFormatException e) {
            txtPhone.setStyle(errorStyle);
            errorMessage.append("- Phone number is empty or not a valid number\n");
            hasErrors = true;
        }

        if (nic.isEmpty() || !nic.matches(nicPattern)) {
            txtNic.setStyle(errorStyle);
            errorMessage.append("- NIC is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtNic.setStyle(defaultStyle);
        }

        // Show alert if there are validation errors and reset to default style
        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }

        // If validation is successful, proceed with saving the customer
        DriverDTO driverDTO = new DriverDTO(id, name, licenceNumber, phone, nic);
        boolean isSaved = driverModel.saveDriver(driverDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Driver saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save driver!").show();
        }
    }

    @FXML
    void clickedTable(MouseEvent event) {
        DriverTM driverTM = driverTable.getSelectionModel().getSelectedItem();
        if(driverTM != null){
            lblDriverId.setText(driverTM.getId());
            txtName.setText(driverTM.getName());
            txtLicenceNum.setText(driverTM.getLicenseNumber());
            txtPhone.setText(String.valueOf(driverTM.getPhoneNumber()));
            txtNic.setText(driverTM.getNic());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String driverId = lblDriverId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this driver?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = driverModel.deleteDriver(driverId);
            if(isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Driver deleted!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to delete driver!").show();
            }
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblDriverId.getText();
        String name = txtName.getText();
        String licenceNumber = txtLicenceNum.getText();
        String phoneText = txtPhone.getText();
        String nic = txtNic.getText();

        // Validation patterns
        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]$|^[0-9]{12}$";


        // Reset error indicators
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        String errorStyle = "-fx-border-color: red; -fx-text-fill: white; -fx-background-color: transparent;";
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-width: 0 0 1 0";

        // Check and apply error style if validation fails
        if (name.isEmpty() || !name.matches(namePattern)) {
            txtName.setStyle(errorStyle);
            errorMessage.append("- Name is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtName.setStyle(defaultStyle);
        }

        if (licenceNumber.isEmpty()) {
            txtLicenceNum.setStyle(errorStyle);
            errorMessage.append("- Licence number is empty\n");
            hasErrors = true;
        }else{
            txtLicenceNum.setStyle(defaultStyle);
        }

        int phone = -1;
        try {
            phone = Integer.parseInt(phoneText);
            txtPhone.setStyle(defaultStyle);
        } catch (NumberFormatException e) {
            txtPhone.setStyle(errorStyle);
            errorMessage.append("- Phone number is empty or not a valid number\n");
            hasErrors = true;
        }

        if (nic.isEmpty() || !nic.matches(nicPattern)) {
            txtNic.setStyle(errorStyle);
            errorMessage.append("- NIC is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtNic.setStyle(defaultStyle);
        }

        // Show alert if there are validation errors and reset to default style
        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }

        DriverDTO driverDTO = new DriverDTO(id, name, licenceNumber, phone, nic);
        boolean isUpdate = driverModel.updateDriver(driverDTO);
        if (isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Driver updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update driver!").show();
        }
    }

    DriverModel driverModel = new DriverModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLicenceNum.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        // Define the default style (black border and transparent background)
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";

        // Apply the default style to all fields at the beginning of each save attempt
        txtName.setStyle(defaultStyle);
        txtLicenceNum.setStyle(defaultStyle);
        txtPhone.setStyle(defaultStyle);
        txtNic.setStyle(defaultStyle);

        txtSearchBar.setOnAction(event ->{
            try{
                searchDrivers();
            }catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error searching drivers").show();
            }
        });


        try{
            refreshPage();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }

    }
    private void searchDrivers() throws SQLException, ClassNotFoundException {
        String searchText = txtSearchBar.getText().trim();

        if(searchText.isEmpty()){
            loadTableData();
            return;
        }

        ArrayList<DriverDTO> driverDTOS = driverModel.getDriverBySearch(searchText);

        // Populate the table with filtered data
        ObservableList<DriverTM> driverTMS = FXCollections.observableArrayList();
        for (DriverDTO driverDTO : driverDTOS) {
            DriverTM driverTM = new DriverTM(
                    driverDTO.getId(),
                    driverDTO.getName(),
                    driverDTO.getLicenseNumber(),
                    driverDTO.getPhoneNumber(),
                    driverDTO.getNic()

            );
            driverTMS.add(driverTM);
        }
        driverTable.setItems(driverTMS);
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextDriverId();
        loadTableData();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";

        txtName.setStyle(defaultStyle);
        txtLicenceNum.setStyle(defaultStyle);
        txtPhone.setStyle(defaultStyle);
        txtNic.setStyle(defaultStyle);

        txtName.setText("");
        txtLicenceNum.setText("");
        txtPhone.setText("");
        txtNic.setText("");

    }

    private void loadTableData() throws SQLException {
        ArrayList<DriverDTO> driverDTOS = driverModel.getAllDrivers();
        ObservableList<DriverTM> driverTMS = FXCollections.observableArrayList();

        for(DriverDTO driverDTO:driverDTOS){
            DriverTM driverTM = new DriverTM(
                    driverDTO.getId(),
                    driverDTO.getName(),
                    driverDTO.getLicenseNumber(),
                    driverDTO.getPhoneNumber(),
                    driverDTO.getNic()


            );
            driverTMS.add(driverTM);
        }
        driverTable.setItems(driverTMS);

    }

    private void loadNextDriverId() throws SQLException {
        String nextDriverId = driverModel.getNextDriverId();
        lblDriverId.setText(nextDriverId);
    }
}
