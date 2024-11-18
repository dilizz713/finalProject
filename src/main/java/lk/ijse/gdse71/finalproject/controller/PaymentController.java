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
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.tm.CustomerTM;
import lk.ijse.gdse71.finalproject.dto.tm.PaymentTM;
import lk.ijse.gdse71.finalproject.model.PaymentModel;
import lk.ijse.gdse71.finalproject.model.ReservationModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    public ComboBox cmbReservationId;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInvoice;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colCutomer;

    @FXML
    private TableColumn<PaymentTM, LocalDate> colDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colReservationId;

    @FXML
    private TableColumn<PaymentTM, String> colStatus;

    @FXML
    private TableColumn<PaymentTM, String> colType;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblcurrentDate;


    @FXML
    private AnchorPane paymentAnchorPane;

    @FXML
    private TableView<PaymentTM> paymentTable;

    @FXML
    private RadioButton rdbAdvancePAymentBtn;

    @FXML
    private RadioButton rdbFullyPayment;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private TextField txtAmount;

    PaymentModel paymentModel = new PaymentModel();
    ReservationModel reservationModel = new ReservationModel();
    ReservationDTO reservationDTO = new ReservationDTO();

    @FXML
    void SaveOnAction(ActionEvent event) {
        String id = lblPaymentId.getText();
        String reservationId = cmbReservationId.getValue() != null ? cmbReservationId.getValue().toString() : null; // Selected Reservation ID
        String type = rdbAdvancePAymentBtn.isSelected() ? "Advance Payment" : "Full Payment";
        LocalDate date = LocalDate.now();
        String amountText = txtAmount.getText();

        if (reservationId == null || txtAmount.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a reservation and enter an amount.").show();
            return;
        }
        double amount =  0;
        try{
            amount = Double.parseDouble(amountText);
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid amount entered!").show();
            return;
        }

        String status = rdbAdvancePAymentBtn.isSelected() ? "Pending " : "Done";

        PaymentDTO paymentDTO = new PaymentDTO(id, amount, date, type, status, reservationId);

        try {
            boolean isSaved = paymentModel.savePayment(paymentDTO);

            // Update Advance Payment status to "Done" if Full Payment is made
            if (rdbFullyPayment.isSelected()) {
                paymentModel.updateAdvancePaymentStatus(reservationId);
            }

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully.").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save payment.").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
        }


    }

    @FXML
    void clickedTable(MouseEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void navigateToInvoice(ActionEvent event) {

    }

    @FXML
    void navigateToReservationView(ActionEvent event) {

    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void selectAdvancePaymentBtn(ActionEvent event) {

    }

    @FXML
    void selectFullyPaymentBtn(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCutomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));





        lblcurrentDate.setText(LocalDate.now().toString());

        try {
            loadReservationIds();
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }
    }

    private void loadReservationIds() throws SQLException {
        ArrayList<String> reservationID = reservationModel.getAllReservationIds();
        cmbReservationId.setItems(FXCollections.observableArrayList(reservationID));
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextPaymentId();
        loadTableData();

        String defaultStyle = "-fx-border-color: white; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-width:  0 0 1";

        txtAmount.setStyle(defaultStyle);

        lblCustomerName.setText("");
        txtAmount.clear();
        rdbAdvancePAymentBtn.setSelected(false);
        rdbFullyPayment.setSelected(false);

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);


    }

    public void loadNextPaymentId() throws SQLException {
        String nextPaymentID = paymentModel.getNextPaymentId();
        lblPaymentId.setText(nextPaymentID);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {

        ArrayList<PaymentDTO> paymentDTOS = paymentModel.getAllPayments();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for(PaymentDTO paymentDTO:paymentDTOS){
            String customerName = reservationModel.getCustomerNameByReservationId(paymentDTO.getReservationId());
            System.out.println(customerName);

            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getId(),
                    customerName,
                    paymentDTO.getType(),
                    paymentDTO.getAmount(),
                    paymentDTO.getReservationId(),
                    paymentDTO.getDate(),
                    paymentDTO.getStatus()


            );
            paymentTMS.add(paymentTM);
        }
        paymentTable.setItems(paymentTMS);

    }

    @FXML
    public void selectReservationId(ActionEvent actionEvent) {
        String selectedReservationId = cmbReservationId.getValue() != null ? cmbReservationId.getValue().toString() : null;

        if(selectedReservationId != null){
            try{
                String customerName = reservationModel.getCustomerNameByReservationId(selectedReservationId);
                lblCustomerName.setText(customerName);
            }catch (SQLException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to load customer name").show();
            }
        }
    }
}
