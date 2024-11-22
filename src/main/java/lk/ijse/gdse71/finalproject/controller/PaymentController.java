package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    public ComboBox cmbReservationId;

    @FXML
    public TableColumn<PaymentTM, Void> colAction;

    @FXML
    public TableColumn<PaymentTM, Double> colAdvanceAmount;

    @FXML
    public TableColumn<PaymentTM, Double> colFullAmount;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

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
    private AnchorPane paymentAnchorPane;

    @FXML
    private TableView<PaymentTM> paymentTable;

    @FXML
    private TextField txtSearchBar;


    PaymentModel paymentModel = new PaymentModel();
    ReservationModel reservationModel = new ReservationModel();
    ReservationDTO reservationDTO = new ReservationDTO();

    private PaymentTM selectedPaymentTM;


    @FXML
    void clickedTable(MouseEvent event) {
       selectedPaymentTM = paymentTable.getSelectionModel().getSelectedItem();
       if(selectedPaymentTM != null){
           btnDelete.setDisable(false);
       }


    }

    @FXML
    void deleteOnAction(ActionEvent event) {

        if (selectedPaymentTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a record to delete.").show();
            return;
        }

        try {
            boolean isDeleted = paymentModel.deletePayment(selectedPaymentTM.getId());

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully.").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete payment.").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
        }
    }


    @FXML
    void navigateToReservationView(ActionEvent event) {
        try{
            paymentAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/newReservation.fxml"));
            paymentAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }





    @FXML
    void updateOnAction(ActionEvent event) throws IOException, SQLException {
        if(selectedPaymentTM == null){
            new Alert(Alert.AlertType.WARNING, "Please select a payment to update!").show();
            return;
        }
        PaymentDTO selectedPayment = paymentModel.getPaymentById(selectedPaymentTM.getId());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newReservation.fxml"));
        AnchorPane paymentPane = loader.load();
        NewReservationController controller = loader.getController();


        controller.setPaymentDetails(selectedPayment);
        controller.setPaymentController(this);

        paymentAnchorPane.getChildren().clear();
        paymentAnchorPane.getChildren().add(paymentPane);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCutomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colAdvanceAmount.setCellValueFactory(new PropertyValueFactory<>("advancePayment"));
        colFullAmount.setCellValueFactory(new PropertyValueFactory<>("fullPayment"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("updateButton"));


        try {
            loadTableData();
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }
        txtSearchBar.setOnAction(event ->{
            try {
                searchPaymentRecords();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }

        btnDelete.setDisable(true);
    }

    private void searchPaymentRecords() throws SQLException, ClassNotFoundException {
        String searchText = txtSearchBar.getText().trim();

        if(searchText.isEmpty()){
            loadTableData();
            return;
        }

        ArrayList<PaymentDTO> paymentDTOS = paymentModel.getPaymentRecordsBySearch(searchText);
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for(PaymentDTO paymentDTO:paymentDTOS){
            ReservationDTO reservationDTO = reservationModel.getReservationById(paymentDTO.getReservationId());
            String customerName = null;


            if (reservationDTO != null) {
                customerName = reservationModel.getCustomerNameById(reservationDTO.getCustomerId());
            }


            if (customerName == null) {
                customerName = "Unknown Customer"; // Fallback name
            }
            Button updateButton = new Button("Update");

            updateButton.setStyle(" -fx-text-fill: black; -fx-font-weight: bold;-fx-background-color: white; -fx-border-radius: 1 1 1 1;-fx-start-margin:2;-fx-end-margin: 2;  -fx-background-radius: 1 1 1 1;-fx-border-color:#6b6e76;-fx-font-size: 12px;");


            updateButton.setOnAction(event -> {
                try {
                    navigateToReservationDetails(paymentDTO.getReservationId(),paymentDTO);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getId(),
                    customerName,
                    paymentDTO.getAdvancePayment(),
                    paymentDTO.getFullPayment(),
                    paymentDTO.getReservationId(),
                    paymentDTO.getDate(),
                    paymentDTO.getStatus(),
                    updateButton



            );
            paymentTMS.add(paymentTM);
        }
        paymentTable.setItems(paymentTMS);
    }


    private void loadReservationIds() throws SQLException {
        ArrayList<String> reservationID = reservationModel.getAllReservationIds();
        cmbReservationId.setItems(FXCollections.observableArrayList(reservationID));
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadTableData();

    }


    private void loadTableData() throws SQLException, ClassNotFoundException {

        ArrayList<PaymentDTO> paymentDTOS = paymentModel.getAllPayments();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for(PaymentDTO paymentDTO:paymentDTOS){
            ReservationDTO reservationDTO = reservationModel.getReservationById(paymentDTO.getReservationId());
            String customerName = null;


            if (reservationDTO != null) {
                customerName = reservationModel.getCustomerNameById(reservationDTO.getCustomerId());
            }


            if (customerName == null) {
                customerName = "Unknown Customer"; // Fallback name
            }
            Button updateButton = new Button("Update");

            updateButton.setStyle(" -fx-text-fill: black; -fx-font-weight: bold;-fx-background-color: white; -fx-border-radius: 1 1 1 1;-fx-start-margin:2;-fx-end-margin: 2;  -fx-background-radius: 1 1 1 1;-fx-border-color:#6b6e76;-fx-font-size: 12px;");


            updateButton.setOnAction(event -> {
                try {
                    navigateToReservationDetails(paymentDTO.getReservationId(),paymentDTO);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

           PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getId(),
                    customerName,
                    paymentDTO.getAdvancePayment(),
                    paymentDTO.getFullPayment(),
                    paymentDTO.getReservationId(),
                    paymentDTO.getDate(),
                    paymentDTO.getStatus(),
                    updateButton



            );
             paymentTMS.add(paymentTM);
        }
        paymentTable.setItems(paymentTMS);

    }

    private void navigateToReservationDetails(String reservationId, PaymentDTO paymentDTO) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newReservation.fxml"));
        AnchorPane reservationAnchorPane = loader.load();
        NewReservationController controller = loader.getController();

        ReservationDTO reservationDTO = reservationModel.getReservationById(reservationId);

        controller.setReservationDetails(reservationDTO);
        controller.setPaymentDetails(paymentDTO);

        paymentAnchorPane.getChildren().clear();
        paymentAnchorPane.getChildren().add(reservationAnchorPane);




    }


    public void refreshTable() throws SQLException, ClassNotFoundException {
        loadTableData();
    }
}
