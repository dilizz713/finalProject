package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.VehicleTM;
import lk.ijse.gdse71.finalproject.model.VehicleModel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class VehicleController implements Initializable {

    public Button btnUpdate;
    @FXML
    private AnchorPane VehicleAnchorPane;

    @FXML
    private Button btnUploadImage;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;


    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lblVehicleId;

    @FXML
    private Label lblDate;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtNumberPlate;

    @FXML
    private TextField txtPrice;

    private byte[] imageBytes;           // Variable to store image data as byte array
    private boolean isEditMode = false;
    private String editingVehicleId;

    private VehicleTableViewController vehicleTableViewController;
    VehicleModel vehicleModel = new VehicleModel();

    public void setVehicleTableViewController(VehicleTableViewController controller){
        this.vehicleTableViewController = controller;
    }

    @FXML
    void selectImageOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Vehicle Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(VehicleAnchorPane.getScene().getWindow());
        if (selectedFile != null) {
            try {
                // Convert the selected file to a byte array
                imageBytes = Files.readAllBytes(selectedFile.toPath());

                // Create an Image from the byte array and display it in the ImageView
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load image").show();
            }
        }
    }
    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblVehicleId.getText();
        String make = txtCompanyName.getText();
        String model = txtModel.getText();
        String vehicleType = cmbType.getValue();
        String numberPlate = txtNumberPlate.getText();
        String price = txtPrice.getText();
        LocalDate date = LocalDate.now();

        String errorStyle = "-fx-border-color: red; -fx-text-fill: white; -fx-background-color: transparent;-fx-border-width: 0 0 2 ";
        String defaultStyle = "-fx-border-color: white; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-width: 0 0 2";

        
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        if(make.isEmpty()){
            txtCompanyName.setStyle(errorStyle);
            errorMessage.append("- Company name is empty\n");
            hasErrors = true;
        }else{
            txtCompanyName.setStyle(defaultStyle);
        }
        if(model.isEmpty()){
            txtModel.setStyle(errorStyle);
            errorMessage.append("- Model is empty\n");
            hasErrors = true;
        }else{
            txtModel.setStyle(defaultStyle);
        }
        if(numberPlate.isEmpty()){
            txtNumberPlate.setStyle(errorStyle);
            errorMessage.append("- Number plate is empty\n");
            hasErrors = true;
        }else{
            txtNumberPlate.setStyle(defaultStyle);
        }

        if(price.isEmpty()){
            txtPrice.setStyle(errorStyle);
            errorMessage.append("- Price is empty\n");
            hasErrors = true;
        }else{
            txtPrice.setStyle(defaultStyle);
        }

        double vehiclePrice = 0;
        vehiclePrice = Double.parseDouble(price);

        cmbType.setStyle(defaultStyle);

        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }

        VehicleDTO vehicleDTO = new VehicleDTO(id, make, model, vehicleType, imageBytes, numberPlate, vehiclePrice,date);
        boolean isSaved ;
        if(btnSave.getText().equals("Update")){
            isSaved = vehicleModel.updateVehicle(vehicleDTO);
        }else{
            isSaved = vehicleModel.saveVehicle(vehicleDTO);
        }

        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save vehicle!").show();
        }
    }



    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }


    @FXML
    void watchHistory(ActionEvent event) {
        try{
            VehicleAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/vehicle-table-view.fxml"));
            VehicleAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String defaultStyle = "-fx-border-color: white; -fx-text-fill: white; -fx-background-color: transparent;-fx-border-width: 0 0 2;";

        txtNumberPlate.setStyle(defaultStyle);
        txtCompanyName.setStyle(defaultStyle);
        txtModel.setStyle(defaultStyle);
        cmbType.setStyle(defaultStyle);
        cmbType.setStyle("-fx-background-color: white;-fx-border-color: black;-fx-font-weight: bold;"); // White text for ComboBox
        imageView.setStyle("-fx-border-color: white; -fx-border-width: 5;");  // White border around ImageView
        txtPrice.setStyle(defaultStyle);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        lblDate.setText(LocalDate.now().format(formatter)); // Format date to dd-MM-yyyy


        ObservableList<String> vehicleTypes = FXCollections.observableArrayList(
                "Car","Van","Bus","Pickup Trucks","Luxury Car"
        );
        cmbType.setItems(vehicleTypes);


        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }
    }


    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextId();

        txtNumberPlate.setText("");
        txtCompanyName.setText("");
        txtModel.setText("");
        txtPrice.setText("0.00");



    }

    public void loadNextId() throws SQLException {
        String nextId = vehicleModel.getNextVehicleId();
        lblVehicleId.setText(nextId);
    }

    @FXML
    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!isEditMode) {
            new Alert(Alert.AlertType.ERROR, "No vehicle selected for updating!").show();
            return;
        }

        String id = editingVehicleId;
        String make = txtCompanyName.getText();
        String model = txtModel.getText();
        String vehicleType = cmbType.getValue();
        String numberPlate = txtNumberPlate.getText();
        String price = txtPrice.getText();
        LocalDate date = LocalDate.now();


        if (make.isEmpty() || model.isEmpty() || vehicleType == null || numberPlate.isEmpty() || price == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            return;
        }

        double vehiclePrice = 0;
        vehiclePrice = Double.parseDouble(price);


        VehicleDTO vehicleDTO = new VehicleDTO(id, make, model, vehicleType, imageBytes, numberPlate,vehiclePrice,date);
        boolean isUpdated = vehicleModel.updateVehicle(vehicleDTO);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Vehicle updated successfully!").show();
            isEditMode = false;
            editingVehicleId = null;
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update vehicle!").show();
        }
    }

    public void setVehicleData(VehicleDTO vehicleDTO) {
        lblVehicleId.setText(vehicleDTO.getId());
        txtCompanyName.setText(vehicleDTO.getMake());
        txtModel.setText(vehicleDTO.getModel());
        cmbType.setValue(vehicleDTO.getVehicleType());
        txtNumberPlate.setText(vehicleDTO.getNumberPlate());
        txtPrice.setText(String.valueOf(vehicleDTO.getPrice()));
        lblDate.setText(String.valueOf(vehicleDTO.getRegistrationDate()));


        if (vehicleDTO.getImage() != null) {
           Image image = new Image(new ByteArrayInputStream(vehicleDTO.getImage()));
           imageView.setImage(image);
        }
        btnSave.setText("Update");
    }

}
