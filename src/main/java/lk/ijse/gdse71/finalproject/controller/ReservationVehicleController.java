package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.model.VehicleModel;
import javafx.geometry.Pos;
import lk.ijse.gdse71.finalproject.util.CrudUtil;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationVehicleController implements Initializable {

    @FXML
    public Button btnHistory;

    @FXML
    public AnchorPane imageAnchorPane;

    @FXML
    public Label lblDate;

    @FXML
    private AnchorPane ReservationVehiclesAnchorPane;

    // Grid layout for vehicle cards
    private GridPane vehicleGrid;

    private final int COLUMN_COUNT = 6;  // Number of columns in grid
    private final int CARD_PADDING = 10;  // Padding around each card

    // Initialize scene
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create grid layout for organizing vehicle cards
        vehicleGrid = new GridPane();
        vehicleGrid.setHgap(10);  // Horizontal gap between cards
        vehicleGrid.setVgap(15);  // Vertical gap between cards
        vehicleGrid.setPadding(new Insets(CARD_PADDING));


        AnchorPane gridContainer = new AnchorPane(vehicleGrid);
        gridContainer.setPadding(new Insets(10));
        AnchorPane.setTopAnchor(gridContainer, 0.0);
        AnchorPane.setBottomAnchor(gridContainer, 50.0);
        AnchorPane.setLeftAnchor(gridContainer, 0.0);
        AnchorPane.setRightAnchor(gridContainer, 0.0);

        Pagination pagination = new Pagination();
        pagination.setStyle("-fx-background-color: black;");

        try {
            VehicleModel vehicleModel = new VehicleModel();
            ArrayList<VehicleDTO> vehicles = vehicleModel.getAllVehicles();
            int itemsPerPage = 12;  // Number of vehicles per page
            int pageCount = (int) Math.ceil((double) vehicles.size() / itemsPerPage); // Calculate page count dynamically
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::createPage);  // Link the page factory to createPage method

            // Add pagination and grid container to the anchor pane
            imageAnchorPane.getChildren().clear();
            imageAnchorPane.getChildren().add(gridContainer);
            imageAnchorPane.getChildren().add(pagination);
            AnchorPane.setBottomAnchor(pagination, 10.0);
            AnchorPane.setRightAnchor(pagination, 10.0);
            AnchorPane.setLeftAnchor(pagination, 10.0);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading vehicle cards!").show();
        }


        lblDate.setText(LocalDate.now().toString());
    }

    private Node createPage(Integer pageIndex) {
        VBox pageBox = new VBox();
        pageBox.setSpacing(10);
        pageBox.setPadding(new Insets(10));

        pageBox.setPrefWidth(imageAnchorPane.getWidth() - 20); // Example: Set width to fit within the parent pane with padding
        pageBox.setPrefHeight(imageAnchorPane.getHeight() - 60);

        vehicleGrid.getChildren().clear();  // Clear existing grid content

        try {
            VehicleModel vehicleModel = new VehicleModel();
            ArrayList<VehicleDTO> vehicles = vehicleModel.getAllVehicles();

            int itemsPerPage = 12;  // Number of cards per page
            int start = pageIndex * itemsPerPage;  // Correct usage of pageIndex
            int end = Math.min(start + itemsPerPage, vehicles.size());

            int colIndex = 0, rowIndex = 0;

            for (int i = start; i < end; i++) {
                VehicleDTO vehicle = vehicles.get(i);

                String vehicleId = vehicle.getId();
                String vehicleStatus = getVehicleStatus(vehicleId);
                String maintenanceStatus = getMaintenanceStatus(vehicleId);

                if (("Pending".equals(vehicleStatus) || "Ongoing".equals(vehicleStatus)) || "Ongoing".equals(maintenanceStatus) ) {
                    continue; // Skip "Pending" vehicles
                }

                VBox vehicleCard = createCard(vehicle);

                if ("Done".equals(vehicleStatus) || "Done".equals(maintenanceStatus)) {
                    vehicleGrid.add(vehicleCard, colIndex, rowIndex);

                    colIndex++;
                    if (colIndex >= COLUMN_COUNT) {
                        colIndex = 0;
                        rowIndex++;
                    }
                }
            }

            pageBox.getChildren().add(vehicleGrid);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading vehicle cards!").show();
        }

        return pageBox;
    }

    private String getMaintenanceStatus(String vehicleId) throws SQLException {
        String query = "SELECT status, startDate, endDate FROM MaintenanceRecord " +
                "WHERE vehicleId = ? " +
                "AND startDate <= CURRENT_DATE " + // Ensure that the maintenance record's start date is before or on today's date
                "ORDER BY startDate DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(query, vehicleId);

        if (resultSet.next()) {
            String status = resultSet.getString("status");
            Date endDate = resultSet.getDate("endDate");

            // If maintenance is ongoing (status is 'Ongoing') and either no endDate is set or the endDate is in the future
            if ("Ongoing".equals(status) && (endDate == null || endDate.after(Date.valueOf(LocalDate.now())))) {
                return "Ongoing";
            }
            return status;  // If not ongoing, return the status (could be "Done" or something else)
        }

        // If no maintenance record is found, assume it's not under maintenance
        return "Done";
    }

    // Load vehicle cards from database and add them to the grid layout
   /* public void loadVehicleCards() throws IOException, SQLException {
        VehicleModel vehicleModel = new VehicleModel();
        ArrayList<VehicleDTO> vehicles = vehicleModel.getAllVehicles();

        int itemsPerPage = 12;
        Pagination pagination = new Pagination();
        pagination.setPageCount((int) Math.ceil((double) vehicles.size()/itemsPerPage));
        pagination.setPageFactory(this::createPage);

        vehicleGrid.getChildren().clear();  // Clear existing cards

        int rowIndex = 0, colIndex = 0;
        for (VehicleDTO vehicle : vehicles) {


            String vehicleId = vehicle.getId();
            String vehicleStatus = getVehicleStatus(vehicleId);

            VBox vehicleCard = createCard(vehicle);

            if("Pending".equals(vehicleStatus)){
                continue;
            }

            if("Done".equals(vehicleStatus)){

                vehicleGrid.add(vehicleCard, colIndex, rowIndex);

                colIndex++;
                if (colIndex >= COLUMN_COUNT) {
                    colIndex = 0;
                    rowIndex++;
                }
            }




        }
    }
*/
    private String getVehicleStatus(String vehicleId) throws SQLException {
        String query = "select status from Reservation where vehicleId = ? order by reservationDate  desc, field(status, 'Pending', 'Done','Ongoing') limit 1";
        ResultSet resultSet = CrudUtil.execute(query,vehicleId);

        if(resultSet.next()){
            return resultSet.getString("status");
        }
        return "Done";
    }

    // Creates and styles an individual vehicle card
    private VBox createCard(VehicleDTO vehicle) {
        VBox vehicleCard = new VBox();
        vehicleCard.setSpacing(5);  // Space between elements in the card
        vehicleCard.setPadding(new Insets(10));
        vehicleCard.setStyle("-fx-border-color: lightgray; -fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");

        vehicleCard.setAlignment(Pos.CENTER);

        // Image for vehicle
        ImageView imageView = new ImageView();
        if (vehicle.getImage() != null) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(vehicle.getImage());
            Image image = new Image(byteArrayInputStream);
            imageView.setImage(image);
        }
        imageView.setFitHeight(120);
        imageView.setFitWidth(140);
        vehicleCard.getChildren().add(imageView);

        // Labels for vehicle details
        Label modelLabel = new Label(vehicle.getModel());
        Label numberPlateLabel = new Label(vehicle.getNumberPlate());
        Label priceLabel = new Label("LKR " + String.valueOf(vehicle.getPrice()));
        Label vehicleIdLabel = new Label(vehicle.getId());

        modelLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        numberPlateLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        priceLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        vehicleIdLabel.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");


        vehicleCard.getChildren().addAll(vehicleIdLabel,modelLabel, numberPlateLabel,priceLabel);

        // Button for adding vehicle to reservation
        Button addButton = new Button("Add +");
        addButton.setOnAction(event -> addReservationOnAction(event, vehicle));

        vehicleCard.getChildren().add(addButton);

        addButton.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-background-color: #289a18; -fx-border-radius: 5; -fx-background-radius: 5;");

        return vehicleCard;
    }

    // Action when "Add +" button is clicked for reserving the vehicle
    @FXML
    void addReservationOnAction(ActionEvent event, VehicleDTO vehicle) {
        try {

            // Load the reservation view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reservation-view.fxml"));
            AnchorPane load = loader.load();

            // Access the ReservationController and set the vehicle details
            ReservationController reservationController = loader.getController();
            reservationController.setVehicleDetails(vehicle);  // Pass vehicle details to controller

            // Clear current view and add the reservation view with populated data
            ReservationVehiclesAnchorPane.getChildren().clear();
            ReservationVehiclesAnchorPane.getChildren().add(load);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }


    }


    @FXML
    public void checkReservationHistory(ActionEvent actionEvent) {
        try{
            ReservationVehiclesAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/reservation-table.fxml"));
            ReservationVehiclesAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
