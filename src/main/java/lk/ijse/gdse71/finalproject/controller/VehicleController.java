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
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.CustomerTM;
import lk.ijse.gdse71.finalproject.dto.tm.VehicleTM;
import lk.ijse.gdse71.finalproject.model.CustomerModel;
import lk.ijse.gdse71.finalproject.model.VehicleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleController implements Initializable {

    @FXML
    private AnchorPane VehicleAnchorPane;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<VehicleTM, String> colCompanyName;

    @FXML
    private TableColumn<VehicleTM, String> colId;

    @FXML
    private TableColumn<VehicleTM, Double> colMileagePrice;

    @FXML
    private TableColumn<VehicleTM, String> colModel;

    @FXML
    private TableColumn<VehicleTM, String> colRegNumber;

    @FXML
    private TableColumn<VehicleTM, String> colStatus;

    @FXML
    private TableColumn<VehicleTM, String> colVehicleType;

    @FXML
    private TableColumn<VehicleTM, Integer> colYear;

    @FXML
    private Label lblVehicleId;

    @FXML
    private TextField searchBar;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtMileage;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtRegNumber;

    @FXML
    private TextField txtStatus;

    @FXML
    private ComboBox<String> cmbVehicleType;

    @FXML
    private TextField txtYear;

    @FXML
    private TableView<VehicleTM> vehicleTable;


    VehicleModel vehicleModel = new VehicleModel();

    @FXML
    void SaveVehicleOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Get field values
        String id = lblVehicleId.getText();
        String regNumber = txtRegNumber.getText();
        String companyName = txtCompanyName.getText();
        String model = txtModel.getText();
        String year = txtYear.getText();
        String mileage =txtMileage.getText();
        String status = txtStatus.getText();
        String vehicleType = cmbVehicleType.getValue();

        // Validation patterns
        String regNumberPattern = "^[A-Za-z0-9]{6}$";  // Registration number must be 6 alphanumeric characters
        String yearPattern = "^[0-9]{4}$";  // Year must be exactly 4 digits
        String mileagePattern = "^[0-9]+(\\.[0-9]+)?$";  // Mileage must be a positive decimal number

        // Reset error indicators
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        String errorStyle = "-fx-border-color: red; -fx-text-fill: white; -fx-background-color: transparent;";
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-width: 0 0 1 0";


        // Check and apply error style if validation fails
        if (regNumber.isEmpty() || !regNumber.matches(regNumberPattern)) {
            txtRegNumber.setStyle(errorStyle);
            errorMessage.append("- Registration number is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtRegNumber.setStyle(defaultStyle);
        }
        if (companyName.isEmpty()) {
            txtCompanyName.setStyle(errorStyle);
            errorMessage.append("- Company name is empty\n");
            hasErrors = true;
        }else{
            txtCompanyName.setStyle(defaultStyle);
        }
        if (model.isEmpty()) {
            txtModel.setStyle(errorStyle);
            errorMessage.append("- Model is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtModel.setStyle(defaultStyle);
        }
        int y = -1;
        if (!year.matches(yearPattern) || year.isEmpty()) {
            txtYear.setStyle(errorStyle);
            errorMessage.append("- Year  is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            try{
                y = Integer.parseInt(year);
                txtYear.setStyle(defaultStyle);

            }catch (NumberFormatException e){
                txtYear.setStyle(errorStyle);
                errorMessage.append("- Year  is empty or in an incorrect format\n");
                hasErrors = true;
            }
        }


        double m = -1;
        if (!mileage.matches(mileagePattern) || mileage.isEmpty()) {
            txtMileage.setStyle(errorStyle);
            errorMessage.append("- Mileage  is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            try{
                m = Double.parseDouble(mileage);
                txtMileage.setStyle(defaultStyle);

            }catch (NumberFormatException e){
                txtMileage.setStyle(errorStyle);
                errorMessage.append("- mileage  is empty or in an incorrect format\n");
                hasErrors = true;
            }
        }
        if (status.isEmpty()) {
            txtStatus.setStyle(errorStyle);
            errorMessage.append("- Status is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtStatus.setStyle(defaultStyle);
        }


        // Show alert if there are validation errors and reset to default style
        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }

        // If validation is successful, proceed with saving the customer
        VehicleDTO vehicleDTO = new VehicleDTO(id, regNumber, companyName, model, y, m,status,vehicleType);
        boolean isSaved = vehicleModel.saveVehicle(vehicleDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save vehicle!").show();
        }
    }

    @FXML
    void clickedTable(MouseEvent event) {
        VehicleTM vehicleTM = vehicleTable.getSelectionModel().getSelectedItem();
        if(vehicleTM != null){
            lblVehicleId.setText(vehicleTM.getId());
            txtRegNumber.setText(vehicleTM.getRegistrationNumber());
            txtCompanyName.setText(vehicleTM.getMake());
            txtModel.setText(vehicleTM.getModel());
            txtYear.setText(String.valueOf(vehicleTM.getYear()));
            txtMileage.setText(String.valueOf(vehicleTM.getMileage()));
            txtStatus.setText(vehicleTM.getStatus());
            cmbVehicleType.setValue(vehicleTM.getVehicleType());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }


    @FXML
    void deleteVehicleOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String vehicleId = lblVehicleId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this vehicle?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = vehicleModel.deleteVehicle(vehicleId);
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
    void searchVehicleType(ActionEvent event) {

    }

    @FXML
    void updateVehicleOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Get field values
        String id = lblVehicleId.getText();
        String regNumber = txtRegNumber.getText();
        String companyName = txtCompanyName.getText();
        String model = txtModel.getText();
        String year = txtYear.getText();
        String mileage =txtMileage.getText();
        String status = txtStatus.getText();
        String vehicleType = cmbVehicleType.getValue();

        // Validation patterns
        String regNumberPattern = "^[A-Za-z0-9]{6}$";  // Registration number must be 6 alphanumeric characters
        String yearPattern = "^[0-9]{4}$";  // Year must be exactly 4 digits
        String mileagePattern = "^[0-9]+(\\.[0-9]+)?$";  // Mileage must be a positive decimal number

        // Reset error indicators
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        String errorStyle = "-fx-border-color: red; -fx-text-fill: white; -fx-background-color: transparent;";
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-width: 0 0 1 0";

        // Check and apply error style if validation fails
        if (regNumber.isEmpty() || !regNumber.matches(regNumberPattern)) {
            txtRegNumber.setStyle(errorStyle);
            errorMessage.append("- Registration number is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtRegNumber.setStyle(defaultStyle);
        }
        if (companyName.isEmpty()) {
            txtCompanyName.setStyle(errorStyle);
            errorMessage.append("- Company name is empty\n");
            hasErrors = true;
        }else{
            txtCompanyName.setStyle(defaultStyle);
        }
        if (model.isEmpty()) {
            txtModel.setStyle(errorStyle);
            errorMessage.append("- Model is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtModel.setStyle(defaultStyle);
        }
        int y = -1;
        if (!year.matches(yearPattern) || year.isEmpty()) {
            txtYear.setStyle(errorStyle);
            errorMessage.append("- Year  is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            try{
                y = Integer.parseInt(year);
                txtYear.setStyle(defaultStyle);

            }catch (NumberFormatException e){
                txtYear.setStyle(errorStyle);
                errorMessage.append("- Year  is empty or in an incorrect format\n");
                hasErrors = true;
            }
        }


        double m = -1;
        if (!mileage.matches(mileagePattern) || mileage.isEmpty()) {
            txtMileage.setStyle(errorStyle);
            errorMessage.append("- Mileage  is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            try{
                m = Double.parseDouble(mileage);
                txtMileage.setStyle(defaultStyle);

            }catch (NumberFormatException e){
                txtMileage.setStyle(errorStyle);
                errorMessage.append("- mileage  is empty or in an incorrect format\n");
                hasErrors = true;
            }
        }
        if (status.isEmpty()) {
            txtStatus.setStyle(errorStyle);
            errorMessage.append("- Status is empty or in an incorrect format\n");
            hasErrors = true;
        }else{
            txtStatus.setStyle(defaultStyle);
        }


        // Show alert if there are validation errors and reset to default style
        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }

        // If validation is successful, proceed with saving the customer
        VehicleDTO vehicleDTO = new VehicleDTO(id, regNumber, companyName, model, y, m,status,vehicleType);
        boolean isUpdate = vehicleModel.updateVehicle(vehicleDTO);
        if (isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle update successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update vehicle!").show();
        }
    }

    @FXML
    void selectVehicleType(ActionEvent event) {

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextVehicleId();
        loadTableData();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";

        txtRegNumber.setStyle(defaultStyle);
        txtCompanyName.setStyle(defaultStyle);
        txtModel.setStyle(defaultStyle);
        txtYear.setStyle(defaultStyle);
        txtMileage.setStyle(defaultStyle);
        txtStatus.setStyle(defaultStyle);
        cmbVehicleType.getSelectionModel().clearSelection();

        txtRegNumber.setText("");
        txtCompanyName.setText("");
        txtModel.setText("");
        txtYear.setText("");
        txtMileage.setText("");
        txtStatus.setText("");

    }
    public void loadNextVehicleId() throws SQLException {
        String nextVehicleId = vehicleModel.getNextVehicleId();
        lblVehicleId.setText(nextVehicleId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<VehicleDTO> vehicleDTOS = vehicleModel.getAllVehicles();
        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();

        for(VehicleDTO vehicleDTO:vehicleDTOS){
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDTO.getId(),
                    vehicleDTO.getRegistrationNumber(),
                    vehicleDTO.getMake(),
                    vehicleDTO.getModel(),
                    vehicleDTO.getYear(),
                    vehicleDTO.getMileage(),
                    vehicleDTO.getStatus(),
                    vehicleDTO.getVehicleType()


            );
            vehicleTMS.add(vehicleTM);
        }
        vehicleTable.setItems(vehicleTMS);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRegNumber.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("make"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMileagePrice.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));


        // Define the default style (black border and transparent background)
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";


        // Apply the default style to all fields at the beginning of each save attempt
        txtRegNumber.setStyle(defaultStyle);
        txtCompanyName.setStyle(defaultStyle);
        txtModel.setStyle(defaultStyle);
        txtYear.setStyle(defaultStyle);
        txtMileage.setStyle(defaultStyle);
        txtStatus.setStyle(defaultStyle);

        ObservableList<String> vehicleTypes = FXCollections.observableArrayList(
                "Car","Van","Bus","Pickup Trucks","Luxury Car"
        );
        cmbVehicleType.setItems(vehicleTypes);

        searchBar.setOnAction(event ->{
            try{
                searchVehicles();
            }catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error searching vehicles").show();
            }
        });


        try{
            refreshPage();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }

    }

    private void searchVehicles() throws SQLException, ClassNotFoundException {
        String searchText = searchBar.getText().trim();

        if(searchText.isEmpty()){
            loadTableData();
            return;
        }

        ArrayList<VehicleDTO> vehicleDTOS = vehicleModel.getVehiclesBySearch(searchText);

        // Populate the table with filtered data
        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();
        for (VehicleDTO vehicleDTO : vehicleDTOS) {
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDTO.getId(),
                    vehicleDTO.getRegistrationNumber(),
                    vehicleDTO.getMake(),
                    vehicleDTO.getModel(),
                    vehicleDTO.getYear(),
                    vehicleDTO.getMileage(),
                    vehicleDTO.getStatus(),
                    vehicleDTO.getVehicleType()
            );
            vehicleTMS.add(vehicleTM);
        }
        vehicleTable.setItems(vehicleTMS);
    }
}
