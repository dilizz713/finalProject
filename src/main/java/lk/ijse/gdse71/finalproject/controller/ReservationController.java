package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.ReservationTM;
import lk.ijse.gdse71.finalproject.model.CustomerModel;
import lk.ijse.gdse71.finalproject.model.ReservationModel;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    public Button btnUpdate;

    @FXML
    public Label lblVehicleId;
    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnAddMileage;

    @FXML
    private Button btnAddPayment;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<CustomerDTO> cmbCustomer;

    @FXML
    private Label lblCurrentDate;

    @FXML
    private Label lblEndDate;

    @FXML
    private Label lblModel;

    @FXML
    private Label lblNumberPlate;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblReservationId;

    @FXML
    private Label lblStartDate;

    @FXML
    private RadioButton rdbDone;

    @FXML
    private RadioButton rdbOngoing;

    @FXML
    private RadioButton rdbPending;

    @FXML
    private AnchorPane reservationAnchorpane;

    private ReservationTableController reservationTableController;
    private ReservationModel reservationModel = new ReservationModel();
    private VehicleDTO selectedVehicle;
    private boolean isEditMode = false;
    private String editingReservationId;
    private LocalDate originalStartDate;

    private ReservationDTO currentReservation;



    public void setReservationTableController(ReservationTableController controller){
        this.reservationTableController = controller;
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblReservationId.getText();

         CustomerDTO selectedCustomer = cmbCustomer.getValue();


       if (cmbCustomer.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer.").show();
            return;
        }

        String status = rdbPending.isSelected() ? "Pending" : rdbOngoing.isSelected() ? "Ongoing" : "Done";
        LocalDate reservationDate = LocalDate.now();
        String customerId = selectedCustomer.getId();
        String vehicleId = lblVehicleId.getText();

        LocalDate startDate = null;
        LocalDate endDate = null;

        if(btnSave.getText().equals("Update")){
            reservationDate = getOriginalReservationDate(id);
            if(reservationDate == null){
                new Alert(Alert.AlertType.ERROR, "Failed to fetch the original reservation date");
                return;
            }
        }else{
            reservationDate = LocalDate.now();
        }


        if(rdbOngoing.isSelected()){
          startDate = LocalDate.now();

        }else if(rdbDone.isSelected()){
            startDate = LocalDate.parse(lblStartDate.getText());
            endDate = LocalDate.now();
        }


        if (reservationDate == null) {
            new Alert(Alert.AlertType.ERROR, "Failed to fetch the original reservation date!").show();
            return;
        }



       /* LocalDate startDate = lblStartDate.getText().isEmpty() ? null : LocalDate.parse(lblStartDate.getText());
        LocalDate endDate = lblEndDate.getText().isEmpty() ? null : LocalDate.parse(lblEndDate.getText());
*/

       /* LocalDate startDate = rdbOngoing.isSelected() ? LocalDate.now() : null;
        LocalDate endDate = rdbDone.isSelected() ? LocalDate.now() : null;*/

        /*currentReservation.setStatus(status);
        currentReservation.setStartDate(startDate);
        currentReservation.setEndDate(endDate);*/



        System.out.println("Reservation saved with status: " + status);

        ReservationDTO reservationDTO = new ReservationDTO(id, startDate, endDate, customerId, vehicleId, status,reservationDate);

        boolean isSaved = false;
        boolean isUpdated = false;

        if(btnSave.getText().equals("Update")){
            isUpdated = reservationModel.updateReservation(reservationDTO);
        }else{
            isSaved = reservationModel.saveReservation(reservationDTO);
        }

        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Reservation saved successfully!").show();

            rdbPending.setSelected(false);
            rdbOngoing.setSelected(false);
            rdbDone.setSelected(false);

        }

        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Reservation updated successfully!").show();

            rdbPending.setSelected(false);
            rdbOngoing.setSelected(false);
            rdbDone.setSelected(false);

        }
    }

    @FXML
    void addCustomerOnAction(ActionEvent event) {
        navigateTo("/view/customer-view.fxml");
    }

    @FXML
    void addMileageOnaction(ActionEvent event) {
        navigateTo("/view/mileage-tracking-view.fxml");
    }

    @FXML
    void addPaymentOnAction(ActionEvent event) {
        navigateTo("/view/payment-view.fxml");
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       navigateTo("/view/reservation-vehicle-view.fxml");
    }

    @FXML
    void handleStatus(ActionEvent event) {
        if(rdbPending.isSelected()){
            lblStartDate.setText("");
            lblEndDate.setText("");

        }else if(rdbOngoing.isSelected()){
            lblStartDate.setText(LocalDate.now().toString());
            lblEndDate.setText("");
            /*rdbPending.setDisable(true);
            rdbPending.setDisable(false);*/

        }else if(rdbDone.isSelected()){
            lblEndDate.setText(LocalDate.now().toString());
           /* rdbPending.setDisable(true);
            rdbOngoing.setDisable(true);*/


        }
    }

    @FXML
    void watchHistory(ActionEvent event) {
       navigateTo("/view/reservation-table.fxml");
    }

    @FXML
    void selectCustomers(ActionEvent event) {
        CustomerDTO selectCustomers = cmbCustomer.getValue();
        if(selectCustomers != null){
            String customerID = selectCustomers.getId();
            System.out.println(customerID);

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbCustomer.setStyle("-fx-background-color: white;-fx-border-color: black;-fx-font-weight: bold;"); // White text for ComboBox

        lblCurrentDate.setText(LocalDate.now().toString());

        // ****
        lblStartDate.setText("");
        lblEndDate.setText("");
// ****

        rdbPending.setOnAction(event -> handleStatusChange());
        rdbOngoing.setOnAction(event -> handleStatusChange());
        rdbDone.setOnAction(event -> handleStatusChange());

        /*rdbPending.setOnAction(event -> clearDates());
        rdbOngoing.setOnAction(event -> lblStartDate.setText(LocalDate.now().toString()));
        rdbDone.setOnAction(event -> lblEndDate.setText(LocalDate.now().toString()));*/

        loadCustomerNames();

        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }
    }

    private void handleStatusChange() {
        if (rdbPending.isSelected()) {
            lblStartDate.setText("");
            lblEndDate.setText("");
        } else if (rdbOngoing.isSelected()) {
            lblStartDate.setText(originalStartDate == null ? LocalDate.now().toString() : originalStartDate.toString());
            lblEndDate.setText("");
        } else if (rdbDone.isSelected()) {
            lblEndDate.setText(LocalDate.now().toString());
        }
    }



    private void clearDates() {
        lblStartDate.setText("");
        lblEndDate.setText("");
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextId();

        rdbPending.setSelected(false);
        rdbOngoing.setSelected(false);
        rdbDone.setSelected(false);

    }
    public void loadNextId() throws SQLException {
        String nextId =reservationModel.getNextReservationId();
        lblReservationId.setText(nextId);
    }
    @FXML
    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!isEditMode) {
            new Alert(Alert.AlertType.ERROR, "No reservation selected for updating!").show();
            return;
        }
        CustomerDTO selectedCustomer = cmbCustomer.getValue();

        String id = editingReservationId;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String customerId = selectedCustomer.getId();
        String vehicleId = selectedVehicle.getId();

        String status = "";

        if(rdbOngoing.isSelected()){
            status = "Ongoing";
            startDate = LocalDate.now();
        }else if(rdbDone.isSelected()){
            status = "Done";
            endDate = LocalDate.now();
        }
       // String status = rdbPending.isSelected() ? "Pending" : rdbOngoing.isSelected() ? "Ongoing" : "Done";
        LocalDate reservationDate = LocalDate.now();

        if (selectedCustomer == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer.").show();
            return;
        }

        LocalDate vehicleReservationDate = getOriginalReservationDate(id);

        ReservationDTO reservationDTO = new ReservationDTO(id, startDate, endDate,customerId , vehicleId, status,vehicleReservationDate);
        boolean isUpdated = reservationModel.updateReservation(reservationDTO);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Reservation updated successfully!").show();
            isEditMode = false;
            editingReservationId = null;

            rdbPending.setSelected(false);
            rdbOngoing.setSelected(false);
            rdbDone.setSelected(false);

            refreshPage();

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update reservation!").show();
        }
    }

    private LocalDate getOriginalReservationDate(String reservationId) throws SQLException {
        String query = "select reservationDate from Reservation where id=?";
        ResultSet rst = CrudUtil.execute(query, reservationId);

        if(rst.next()){
            return rst.getDate("reservationDate").toLocalDate();
        }
        return null;
    }

    public void navigateTo(String fxmlPath){
        try{
            reservationAnchorpane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            reservationAnchorpane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    public void setReservationDetails(ReservationDTO reservationDTO) throws SQLException {
        currentReservation = reservationDTO;
        isEditMode = true;
        editingReservationId = reservationDTO.getId();

       lblReservationId.setText(reservationDTO.getId());

      /* lblStartDate.setText(String.valueOf(reservationDTO.getStartDate()));
       lblEndDate.setText(String.valueOf(reservationDTO.getEndDate()));*/

       lblCurrentDate.setText(reservationDTO.getReservationDate() != null ? reservationDTO.getReservationDate().toString() : LocalDate.now().toString());
        lblStartDate.setText(reservationDTO.getStartDate() != null ? reservationDTO.getStartDate().toString() : "");
        lblEndDate.setText(reservationDTO.getEndDate() != null ? reservationDTO.getEndDate().toString() : "");

        originalStartDate = reservationDTO.getStartDate();



        String customerId = reservationDTO.getCustomerId();
        CustomerDTO selectedCustomer = reservationModel.getCustomerDTOsForReservation()
                .stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .orElse(null);
        if (selectedCustomer != null) {
            cmbCustomer.getSelectionModel().select(selectedCustomer);
        }

        String vehicleId = reservationDTO.getVehicleId();

        ResultSet vehicleDetailsResultSet = CrudUtil.execute("SELECT id, model, numberPlate, price FROM Vehicle WHERE id = ?", vehicleId);
        if (vehicleDetailsResultSet.next()) {
            String vId = vehicleDetailsResultSet.getString("id");
            String model = vehicleDetailsResultSet.getString("model");
            String numberPlate = vehicleDetailsResultSet.getString("numberPlate");
            double price = vehicleDetailsResultSet.getDouble("price");

            // Update vehicle labels in the UI
            lblVehicleId.setText(vId);
            lblModel.setText(model);
            lblNumberPlate.setText(numberPlate);
            lblPrice.setText(String.format("%.2f", price));
        } else {
            // If no details are found, display placeholders
            lblVehicleId.setText("Vehicle id not found");
            lblModel.setText("Model not found");
            lblNumberPlate.setText("Number plate not found");
            lblPrice.setText("0.00");
        }

        if(reservationDTO.getStatus().equals("Pending")) {
            rdbPending.setSelected(true);
        }else if(reservationDTO.getStatus().equals("Ongoing")){
            rdbOngoing.setSelected(true);
        }else if(reservationDTO.getStatus().equals("Done")){
            rdbDone.setSelected(true);
        }

        lblStartDate.setText(String.valueOf(reservationDTO.getStartDate()));
        lblEndDate.setText(String.valueOf(reservationDTO.getEndDate()));

       // manageRadioButton();



       /* switch (reservationDTO.getStatus()){
           case "Pending":
               rdbPending.setSelected(true);
               break;
           case "Ongoing":
               rdbOngoing.setSelected(true);
               lblStartDate.setText(String.valueOf(reservationDTO.getStartDate()));
               break;
           case "Done":
               rdbDone.setSelected(true);
               lblEndDate.setText(String.valueOf(reservationDTO.getEndDate()));
               break;*/

       btnSave.setText("Update");

    }

   /* private void manageRadioButton() {
        if (currentReservation.getStatus().equals("Pending")) {
            rdbPending.setDisable(true);
            rdbOngoing.setDisable(false);
            rdbDone.setDisable(false);
        } else if (currentReservation.getStatus().equals("Ongoing")) {
            rdbPending.setDisable(true);
            rdbOngoing.setDisable(true);
            rdbDone.setDisable(false);
        } else if (currentReservation.getStatus().equals("Done")) {
            rdbPending.setDisable(true);
            rdbOngoing.setDisable(true);
            rdbDone.setDisable(false);
        }
    }*/

    private void loadCustomerNames() {
        try {
            ArrayList<CustomerDTO> customers = reservationModel.getCustomerDTOsForReservation();

            // Convert the list to an observable list and set it to the ComboBox
            ObservableList<CustomerDTO> customerList = FXCollections.observableArrayList(customers);
            cmbCustomer.setItems(customerList);

            // Customize the ComboBox to display only the customer's name
            cmbCustomer.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(CustomerDTO customer, boolean empty) {
                    super.updateItem(customer, empty);
                    if (empty || customer == null || customer.getName() == null) {
                        setText(null);
                    } else {
                        setText(customer.getName());
                    }
                }
            });

            cmbCustomer.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(CustomerDTO customer, boolean empty) {
                    super.updateItem(customer, empty);
                    if (empty || customer == null || customer.getName() == null) {
                        setText(null);
                    } else {
                        setText(customer.getName());
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load customer names.").show();
        }
    }

   /* public void setReservationData(ReservationTM reservation) {
        lblReservationId.setText(reservation.getId());
        cmbCustomer.getSelectionModel().select(new CustomerDTO(reservation.getCustomerId(), reservation.getCustomerName(), null, null, 0, null));
        lblModel.setText(reservation.getModel());
        lblNumberPlate.setText(reservation.getVehicleId());
        lblPrice.setText(String.valueOf(reservation.getPrice()));

        if ("Pending".equals(reservation.getStatus())) rdbPending.setSelected(true);
        else if ("Ongoing".equals(reservation.getStatus())) rdbOngoing.setSelected(true);
        else if ("Done".equals(reservation.getStatus())) rdbDone.setSelected(true);

        btnSave.setText("update");
    }*/


    public void setVehicleDetails(VehicleDTO vehicle) {
        if(vehicle == null){
            System.err.println("Vehicle dto is null");
        }else{
            selectedVehicle = vehicle;
            lblVehicleId.setText(vehicle.getId());
            lblModel.setText(vehicle.getModel());
            lblNumberPlate.setText(vehicle.getNumberPlate());
            lblPrice.setText(String.valueOf(vehicle.getPrice()));

        }

    }
}
