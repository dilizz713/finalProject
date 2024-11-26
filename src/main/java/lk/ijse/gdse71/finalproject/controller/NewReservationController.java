package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import lk.ijse.gdse71.finalproject.db.DBConnection;
import lk.ijse.gdse71.finalproject.dto.*;
import lk.ijse.gdse71.finalproject.model.PaymentModel;
import lk.ijse.gdse71.finalproject.model.ReservationModel;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewReservationController implements Initializable {
    @FXML
    public ComboBox cmbStatus;

    @FXML
    public TextField txtAdvancePayment;

    @FXML
    public Label txtfullPayment;
    @FXML
    public Button updatePAymentButton;

    @FXML
    private Button billBtn;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<CustomerDTO> cmbCustomer;

    @FXML
    private HBox hbox;

    @FXML
    private Label lblCurrentDate;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblModel;

    @FXML
    private Label lblNumberPlate;

    @FXML
    private Label lblPayment;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblReservationId;

    @FXML
    private Label lblVehicleId;

    @FXML
    private RadioButton rdbOngoing;

    @FXML
    private RadioButton rdbPending;

    @FXML
    private AnchorPane reservationAnchorpane;

    private ReservationTableController reservationTableController;
    private PaymentController paymentController;
    private ReservationModel reservationModel = new ReservationModel();
    private PaymentModel paymentModel = new PaymentModel();
    private VehicleDTO selectedVehicle;
    private boolean isEditMode = false;
    private String editingReservationId;
    private LocalDate originalStartDate;

    private ReservationDTO currentReservation;
    private PaymentDTO currentPayment;

    public void setReservationTableController(ReservationTableController controller){
        this.reservationTableController = controller;
    }
    public void setPaymentController(PaymentController controller){
        this.paymentController = controller;
    }




    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(isEditMode){
            if(btnSave.getText().equals("Update Reservation")) {
                updateReservation();
            }
        }else{
            saveReservationAndPAyment();
        }


    }

    private void saveReservationAndPAyment() throws SQLException {
        updatePAymentButton.setDisable(true);
        billBtn.setDisable(true);

        String id = lblReservationId.getText();
        CustomerDTO selectedCustomer = cmbCustomer.getValue();

        if (cmbCustomer.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer.").show();
            return;
        }

        String status = rdbPending.isSelected() ? "Pending" : rdbOngoing.isSelected() ? "Ongoing" : null;
        String customerId = selectedCustomer.getId();
        String vehicleId = lblVehicleId.getText();
        LocalDate reservationDate = LocalDate.now();

        String paymentId = lblPaymentId.getText();
        String customerName = selectedCustomer.getName();

        String advancePaymentText = txtAdvancePayment.getText();
        String fullPaymentText = txtfullPayment.getText();
        String paymentStatus = cmbStatus.getValue() != null ? cmbStatus.getValue().toString() : "Pending";

        if (advancePaymentText == null || advancePaymentText.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Advance payment field is empty. Please enter a valid amount.").show();
            return;
        }

        double advancePayment = 0 , fullPayment = 0;
       try{
            advancePayment = Double.parseDouble(advancePaymentText);

       }catch (NumberFormatException e){
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR,"Please enter valid number").show();
           return;
       }
        fullPayment = Double.parseDouble(fullPaymentText);

        if(advancePayment <= 0 || paymentStatus == null || paymentStatus.isEmpty() || status == null|| status.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill reservation details and payment details before saving").show();
            return;
        }


        ReservationDTO reservationDTO = new ReservationDTO(id, customerId, vehicleId, status, reservationDate);
        PaymentDTO paymentDTO = new PaymentDTO(paymentId, reservationDate, paymentStatus, id, advancePayment, fullPayment);

        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isReservationSaved = CrudUtil.execute(connection,
                    "insert into Reservation (id, customerId, vehicleId, status, reservationDate) values (?, ?, ?, ?, ?)",
                    reservationDTO.getId(), reservationDTO.getCustomerId(), reservationDTO.getVehicleId(),
                    reservationDTO.getStatus(), reservationDTO.getReservationDate());
            if (!isReservationSaved) {
                throw new SQLException("Failed to save reservation.");
            }

            boolean isPaymentSaved = CrudUtil.execute(connection,
                    "insert into Payment (id, date, status, reservationId, advancePayment, fullPayment) values (?, ?, ?, ?, ?, ?)",
                    paymentDTO.getId(), paymentDTO.getDate(), paymentDTO.getStatus(), paymentDTO.getReservationId(),
                    paymentDTO.getAdvancePayment(), paymentDTO.getFullPayment());
            if (!isPaymentSaved) {
                throw new SQLException("Failed to save payment.");
            }
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Reservation and payment saved successfully!").show();
        } catch (Exception e) {


            if (connection != null) {
                connection.rollback();
            }
            new Alert(Alert.AlertType.ERROR, "Failed to save reservation and payment:").show();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
        updatePAymentButton.setDisable(false);

    }

    private void updateReservation() throws SQLException {
        String status = rdbPending.isSelected() ? "Pending" : "Ongoing";
        LocalDate reservationDate = originalStartDate;
        String customerId = cmbCustomer.getValue().getId();
        String vehicleId = lblVehicleId.getText();
        String reservationId = lblReservationId.getText();

        ReservationDTO reservationDTO = new ReservationDTO(reservationId, customerId, vehicleId, status, originalStartDate);
        reservationModel.updateReservation(reservationDTO);

        reservationTableController.refreshTable();

        new Alert(Alert.AlertType.INFORMATION, "Reservation updated successfully!").show();
    }



    private LocalDate getOriginalReservationDate(String reservationId) throws SQLException {
        String query = "select reservationDate from Reservation where id=?";
        ResultSet rst = CrudUtil.execute(query, reservationId);

        if(rst.next()){
            return rst.getDate("reservationDate").toLocalDate();
        }
        return null;
    }

    @FXML
    void generateBillOnAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bill-view.fxml"));
            Parent load = loader.load();

            GenerateBillController generateBillController = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("");


            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = updatePAymentButton.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }



    @FXML
    void resetOnAction(ActionEvent event) {
        navigateTo("/view/reservation-vehicle-view.fxml");
    }



    @FXML
    void selectCustomers(ActionEvent event) {
        CustomerDTO selectCustomers = cmbCustomer.getValue();
        if(selectCustomers != null){
            String customerID = selectCustomers.getId();
            System.out.println(customerID);

        }
    }



    @FXML
    void selectedOngoingButton(ActionEvent event) {
        rdbPending.setSelected(false);

    }

    @FXML
    void selectedPendingButton(ActionEvent event) {
        rdbOngoing.setSelected(false);
    }

    @FXML
    void watchHistory(ActionEvent event) {
        navigateTo("/view/reservation-table.fxml");
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblCurrentDate.setText(LocalDate.now().toString());
        txtfullPayment.setText("0");

        updatePAymentButton.setDisable(true);

        loadCustomerNames();

        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }

        cmbStatus.setItems(FXCollections.observableArrayList("Pending","Done"));
        cmbStatus.setValue("Pending");
        cmbCustomer.setConverter(new StringConverter<>() {
            @Override
            public String toString(CustomerDTO customer) {
                return customer == null ? "" : customer.getName();
            }

            @Override
            public CustomerDTO fromString(String string) {
                return null;
            }
        });







    }
    private boolean isValidNumber(String input) {
        return input.matches("\\d*(\\.\\d{0,2})?");
    }

    private void loadCustomerNames() {
        try {
            ArrayList<CustomerDTO> customers = reservationModel.getCustomerDTOsForReservation();

            ObservableList<CustomerDTO> customerList = FXCollections.observableArrayList(customers);
            cmbCustomer.setItems(customerList);


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

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadPaymentId();

        rdbPending.setSelected(false);
        rdbOngoing.setSelected(false);


    }

    public void loadNextId() throws SQLException {
        String nextId =reservationModel.getNextReservationId();
       lblReservationId.setText(nextId);
    }
    public void loadPaymentId() throws SQLException {
        String nextPaymentId = paymentModel.getNextPaymentId();
       lblPaymentId.setText(nextPaymentId);
    }


    public void setReservationDetails(ReservationDTO reservationDTO) throws SQLException {
        currentReservation = reservationDTO;
        isEditMode = true;
        editingReservationId = reservationDTO.getId();

        lblReservationId.setText(reservationDTO.getId());

        originalStartDate = reservationDTO.getReservationDate();
        lblCurrentDate.setText(originalStartDate != null ? originalStartDate.toString() : LocalDate.now().toString());

        String customerId = reservationDTO.getCustomerId();
        for (CustomerDTO customer : reservationModel.getCustomerDTOsForReservation()) {
            if (customer.getId().equals(customerId)) {
                cmbCustomer.getSelectionModel().select(customer);
                break;
            }
        }

        String vehicleId = reservationDTO.getVehicleId();

        ResultSet vehicleDetailsResultSet = CrudUtil.execute("select id, model, numberPlate, price from Vehicle where id = ?", vehicleId);
        if (vehicleDetailsResultSet.next()) {
            String vId = vehicleDetailsResultSet.getString("id");
            String model = vehicleDetailsResultSet.getString("model");
            String numberPlate = vehicleDetailsResultSet.getString("numberPlate");
            double price = vehicleDetailsResultSet.getDouble("price");


            lblVehicleId.setText(vId);
            lblModel.setText(model);
            lblNumberPlate.setText(numberPlate);
            lblPrice.setText(String.format("%.2f", price));
        } else {

            lblVehicleId.setText("Vehicle id not found");
            lblModel.setText("Model not found");
            lblNumberPlate.setText("Number plate not found");
            lblPrice.setText("0.00");
        }

        if(reservationDTO.getStatus().equals("Pending")) {
            rdbPending.setSelected(true);
        }else if(reservationDTO.getStatus().equals("Ongoing")) {
            rdbOngoing.setSelected(true);
        }

        btnSave.setText("Update Reservation");
    }

    public void setPaymentDetails(PaymentDTO paymentDTO) {
        this.currentPayment = paymentDTO;

        txtAdvancePayment.setText(String.valueOf(paymentDTO.getAdvancePayment()));
        txtfullPayment.setText(String.valueOf(paymentDTO.getFullPayment()));
        cmbStatus.setValue(paymentDTO.getStatus());
    }

    @FXML
    public void selectStatusOnAction(ActionEvent event) throws SQLException {
        String status = cmbStatus.getValue().toString();
        String reservationId = lblReservationId.getText();
        btnSave.setDisable(true);

        if(status.equals("Done")){
            updatePAymentButton.setDisable(false);

            double endMilegae = reservationModel.getEndMileageForReservation(reservationId);
            if(endMilegae == 0){
                new Alert(Alert.AlertType.WARNING, "Please fill the end date mileage before calculating full payment!").show();
                cmbStatus.setValue("Pending");
                navigateTo("/view/mileage-tracking-view.fxml");
                return;
            }
            double fullPAyment = calculateFullPaymentAmount(reservationId) ;
            txtfullPayment.setText(String.format("%.2f", fullPAyment));
        }
        updateSaveButtonStatus();
    }
    private double calculateFullPaymentAmount(String reservationId) throws SQLException {
        String vehicleId = reservationModel.getVehicleIdByReservationId(reservationId);

        double estimatedMileageCost = reservationModel.getEstimatedMileageCost(reservationId);
        double totalExtraCharges = reservationModel.getTotalExtraCharges(reservationId);
        double repairCost = reservationModel.getRepairCostByVehicleId(vehicleId);
        double advancePayment = paymentModel.getAdvancePayment(reservationId);

        return estimatedMileageCost + totalExtraCharges + repairCost - advancePayment;

    }
    @FXML
    public void selectAdvancePaymentField(KeyEvent keyEvent) {
        updateSaveButtonStatus();
    }
    @FXML
    public void selctFullPaymentField(KeyEvent keyEvent) {
        updateSaveButtonStatus();
    }
    private void updateSaveButtonStatus() {
        boolean isPaymentValid = (!txtAdvancePayment.getText().isEmpty() || !txtfullPayment.getText().isEmpty() )&& cmbStatus.getValue() != null;
        btnSave.setDisable(!isPaymentValid);
    }

    @FXML
    public void updatePaymentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        btnSave.setDisable(true);

        String advancePaymentText = txtAdvancePayment.getText();
        String fullPaymentText = txtfullPayment.getText();
        String status = cmbStatus.getValue().toString();
        String reservationId = lblReservationId.getText();

        double advancePayment = Double.parseDouble(advancePaymentText);
        double fullPayment = Double.parseDouble(fullPaymentText);

        btnSave.setDisable(true);

        PaymentDTO paymentDTO = new PaymentDTO(currentPayment.getId(), LocalDate.now(), status, reservationId, advancePayment, fullPayment);

        Connection connection = null;
        try{
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isPaymentUpdated = CrudUtil.execute(connection,
                    "update Payment set date=?, status=?, reservationId=?, advancePayment=?, fullPayment=? where id=?",
                    paymentDTO.getDate(), paymentDTO.getStatus(), paymentDTO.getReservationId(), paymentDTO.getAdvancePayment(),
                    paymentDTO.getFullPayment(), paymentDTO.getId());

            if(!isPaymentUpdated){
                throw new SQLException("Failed to update payment");
            }
            if(status.equalsIgnoreCase("Done")){
                boolean isReservationUpdated = CrudUtil.execute(connection,
                        "update Reservation set status=? where id=?", "Done",reservationId
                        );
                if(!isReservationUpdated){
                    throw new SQLException("Failed to update reservation status.");
                }
            }

            connection.commit();

            new Alert(Alert.AlertType.INFORMATION, "Payment and reservation status updated successfully!").show();


        }
        catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update payment and reservation status.").show();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                showBillUI(reservationId, paymentDTO.getId());
            }
        }
    }

    private void showBillUI(String reservationId, String paymentId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bill-view.fxml"));
            Parent root = loader.load();

            GenerateBillController billController = loader.getController();

            MileageTrackingDTO mileage = reservationModel.getMileageDetails(reservationId);
            PaymentDTO payment = paymentModel.getPaymentDetails(paymentId);
            String vehicleId = reservationModel.getVehicleIdByReservationId(reservationId);

            billController.setBillDetails(reservationId, paymentId, payment, mileage, vehicleId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            Window currentWindow = updatePAymentButton.getScene().getWindow();
            stage.initOwner(currentWindow);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the bill UI!").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
