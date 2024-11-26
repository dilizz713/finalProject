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

    private GridPane vehicleGrid;

    private final int COLUMN_COUNT = 6;
    private final int CARD_PADDING = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicleGrid = new GridPane();
        vehicleGrid.setHgap(10);
        vehicleGrid.setVgap(15);
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
            int itemsPerPage = 12;
            int pageCount = (int) Math.ceil((double) vehicles.size() / itemsPerPage);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::createPage);


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


        pageBox.setPrefWidth(imageAnchorPane.getWidth() - 20);
        pageBox.setPrefHeight(imageAnchorPane.getHeight() - 60);

        vehicleGrid.getChildren().clear();

        try {
            VehicleModel vehicleModel = new VehicleModel();
            ArrayList<VehicleDTO> vehicles = vehicleModel.getAllVehicles();

            int itemsPerPage = 12;
            int start = pageIndex * itemsPerPage;  // Correct usage of pageIndex
            int end = Math.min(start + itemsPerPage, vehicles.size());

            int colIndex = 0, rowIndex = 0;

            for (int i = start; i < end; i++) {
                VehicleDTO vehicle = vehicles.get(i);

                String vehicleId = vehicle.getId();
                String vehicleStatus = getVehicleStatus(vehicleId);
                String maintenanceStatus = getMaintenanceStatus(vehicleId);

                if (("Pending".equals(vehicleStatus) || "Ongoing".equals(vehicleStatus)) || "Ongoing".equals(maintenanceStatus) ) {
                    continue;
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
                "AND startDate <= CURRENT_DATE " +
                "ORDER BY startDate DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(query, vehicleId);

        if (resultSet.next()) {
            String status = resultSet.getString("status");
            Date endDate = resultSet.getDate("endDate");


            if ("Ongoing".equals(status) && (endDate == null || endDate.after(Date.valueOf(LocalDate.now())))) {
                return "Ongoing";
            }
            return status;
        }

        return "Done";
    }


    private String getVehicleStatus(String vehicleId) throws SQLException {
        String query = "select status from Reservation where vehicleId = ? order by reservationDate  desc, field(status, 'Pending', 'Done','Ongoing') limit 1";
        ResultSet resultSet = CrudUtil.execute(query,vehicleId);

        if(resultSet.next()){
            return resultSet.getString("status");
        }
        return "Done";
    }

    private VBox createCard(VehicleDTO vehicle) {
        VBox vehicleCard = new VBox();
        vehicleCard.setSpacing(5);
        vehicleCard.setPadding(new Insets(10));
        vehicleCard.setStyle("-fx-border-color: #ddd; -fx-background-color: lightblue; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);");

        vehicleCard.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();
        if (vehicle.getImage() != null) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(vehicle.getImage());
            Image image = new Image(byteArrayInputStream);
            imageView.setImage(image);
        }
        imageView.setFitHeight(120);
        imageView.setFitWidth(140);
        vehicleCard.getChildren().add(imageView);


        Label modelLabel = new Label(vehicle.getModel());
        modelLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;-fx-text-fill: white;");

        Label numberPlateLabel = new Label(vehicle.getNumberPlate());
        numberPlateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label priceLabel = new Label("LKR " + String.valueOf(vehicle.getPrice()));
        priceLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label vehicleIdLabel = new Label(vehicle.getId());
        vehicleIdLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white;-fx-effect: dropshadow(gaussian, darkred, 3, 1, 0, 0);");


        vehicleCard.getChildren().addAll(vehicleIdLabel,modelLabel, numberPlateLabel,priceLabel);

        // Button for adding vehicle to reservation
        Button addButton = new Button("Add +");
        addButton.setOnAction(event -> addReservationOnAction(event, vehicle));

        vehicleCard.getChildren().add(addButton);

       addButton.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-background-color: navy; -fx-border-radius: 2; -fx-background-radius: 2;-fx-font-size: 12px");

        return vehicleCard;
    }


    @FXML
    void addReservationOnAction(ActionEvent event, VehicleDTO vehicle) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newReservation.fxml"));
            AnchorPane load = loader.load();


            NewReservationController reservationController = loader.getController();
            reservationController.setVehicleDetails(vehicle);


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
