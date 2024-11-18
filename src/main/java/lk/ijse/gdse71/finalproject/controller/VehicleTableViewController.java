package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.ReservationTM;
import lk.ijse.gdse71.finalproject.dto.tm.VehicleTM;
import lk.ijse.gdse71.finalproject.model.VehicleModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class VehicleTableViewController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<VehicleTM, String> colCompanyName;

    @FXML
    private TableColumn<VehicleTM, LocalDate> colDate;

    @FXML
    private TableColumn<VehicleTM, String> colId;

    @FXML
    private TableColumn<VehicleTM, Byte> colImage;

    @FXML
    private TableColumn<VehicleTM, String> colModel;

    @FXML
    private TableColumn<VehicleTM, String> colNumberPlate;

    @FXML
    private TableColumn<VehicleTM, String> colType;

    @FXML
    private TableColumn<VehicleTM, Void> colAction;

    @FXML
    private TableColumn<VehicleTM, Double> colPrice;

    @FXML
    private TextField searchBar;

    @FXML
    private AnchorPane vehicleTableAnchorPane;

    @FXML
    private TableView<VehicleTM> vehicleTableView;

    private VehicleTM selectedVehicleTM;
    @FXML
    void clickedTable(MouseEvent event) {
        selectedVehicleTM = vehicleTableView.getSelectionModel().getSelectedItem();
       if (selectedVehicleTM != null) {
            btnDelete.setDisable(false);
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        if(selectedVehicleTM != null){
            try{
                String vehicleID = selectedVehicleTM.getId();
                boolean isDeleted = vehicleModel.deleteVehicle(vehicleID);
                if(isDeleted){
                    vehicleTableView.getItems().remove(selectedVehicleTM);
                    selectedVehicleTM = null;

                    new Alert(Alert.AlertType.INFORMATION, "Vehicle deleted successfully").show();
                }else{
                    new Alert(Alert.AlertType.ERROR, "fail").show();
                }
            }catch (SQLException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Vehicle deleted successfully").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please select a vehicle to delete.").show();
        }
    }

    @FXML
    void navigateToVehicleView(ActionEvent event) {
        try{
            vehicleTableAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/vehicle-view.fxml"));
            vehicleTableAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }


    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    VehicleModel vehicleModel = new VehicleModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumberPlate.setCellValueFactory(new PropertyValueFactory<>("numberPlate"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("make"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("updateButton"));


        try {
            loadTableData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle data").show();
        }

      /* searchBar.setOnAction(event ->{
            try{
                searchVehicle();
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

        btnDelete.setDisable(true);

    }




   /* private void searchVehicle() throws SQLException, ClassNotFoundException {
        String searchText = searchBar.getText().trim();

        if(searchText.isEmpty()){
            loadTableData();
            return;
        }

        ArrayList<VehicleDTO> vehicleDTOS = vehicleModel.getVehiclesBySearch(searchText);
        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();



        LocalDate currentDate = LocalDate.now();  // Get the current date

        for (VehicleDTO vehicleDTO : vehicleDTOS) {
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDTO.getId(),
                    vehicleDTO.getNumberPlate(),
                    vehicleDTO.getMake(),
                    vehicleDTO.getModel(),
                    vehicleDTO.getVehicleType(),
                    currentDate,
                    vehicleDTO.getPrice(),
                    vehicleDTO.getImage(),




            );
            vehicleTMS.add(vehicleTM);
        }
        vehicleTableView.setItems(vehicleTMS);
    }
*/

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadTableData();

    }
    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<VehicleDTO> vehicleDTOS = vehicleModel.getAllVehicles();
        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();

        for (VehicleDTO vehicleDTO : vehicleDTOS) {
            ImageView imageView = new ImageView();

            // If image is a BLOB, convert it to an Image
            if (vehicleDTO.getImage() != null) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(vehicleDTO.getImage());
                Image image = new Image(byteArrayInputStream);
                imageView.setImage(image); // Set the image in ImageView
                imageView.setFitHeight(50);  // Set the size for displaying
                imageView.setFitWidth(50);   // Adjust the size to fit the table
            }
            // Create the update button for each row
            Button updateButton = new Button("Update");

            // Set up the action for the update button
            updateButton.setOnAction(event -> {
                try {
                    openVehicleUpdateView(vehicleDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to load update view").show();
                }
            });

            LocalDate registrationDate = null;
            if (vehicleDTO.getRegistrationDate() != null) {
                registrationDate = vehicleDTO.getRegistrationDate();
            }

            // Create VehicleTM with the update button included
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDTO.getId(),
                    vehicleDTO.getNumberPlate(),
                    vehicleDTO.getMake(),
                    vehicleDTO.getModel(),
                    vehicleDTO.getVehicleType(),
                    registrationDate,
                    vehicleDTO.getPrice(),
                    vehicleDTO.getImage(),
                    updateButton  // Pass the update button here
            );

            vehicleTMS.add(vehicleTM);
        }

        vehicleTableView.setItems(vehicleTMS);
    }


    private void openVehicleUpdateView(VehicleDTO vehicleDTO) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vehicle-view.fxml"));
        AnchorPane pane = loader.load();
        VehicleController controller = loader.getController();
        controller.setVehicleData(vehicleDTO);  // Set data to be edited

        vehicleTableAnchorPane.getChildren().clear();  // Display the view
        vehicleTableAnchorPane.getChildren().add(pane);  // Display the view
    }
}
