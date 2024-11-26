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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.tm.CustomerTM;
import lk.ijse.gdse71.finalproject.dto.tm.ReservationTM;
import lk.ijse.gdse71.finalproject.model.CustomerModel;
import lk.ijse.gdse71.finalproject.model.ReservationModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public TextField searchBar;
    @FXML
    public Button btnSendMail;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colId;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colNic;

    @FXML
    private TableColumn<CustomerTM, Integer> colPhone;

    @FXML
    private AnchorPane customerAnchorPane;

    @FXML
    private TableView<CustomerTM> customerTableView;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    CustomerModel customerModel = new CustomerModel();

    @FXML
    void SaveCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String phoneText = txtPhone.getText();
        String nic = txtNic.getText();


        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]$|^[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        String errorStyle = "-fx-border-color: red; -fx-text-fill: white; -fx-background-color: transparent;";
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";


        if (name.isEmpty() || !name.matches(namePattern)) {
            txtName.setStyle(errorStyle);
            errorMessage.append("- Name is empty or in an incorrect format\n");
            hasErrors = true;

        }else{
            txtName.setStyle(defaultStyle);
        }
        if (address.isEmpty()) {
            txtAddress.setStyle(errorStyle);
            errorMessage.append("- Address is empty\n");
            hasErrors = true;

        }else{
            txtAddress.setStyle(defaultStyle);
        }
        if (email.isEmpty() || !email.matches(emailPattern)) {
            txtEmail.setStyle(errorStyle);
            errorMessage.append("- Email is empty or in an incorrect format\n");
            hasErrors = true;

        }else{
            txtEmail.setStyle(defaultStyle);
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


        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }


        CustomerDTO customerDTO = new CustomerDTO(id, name, address, email, phone, nic);
        boolean isSaved = customerModel.saveCustomer(customerDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save customer!").show();
        }
    }
    @FXML
    void clickedTable(MouseEvent event) {
        CustomerTM customerTM = customerTableView.getSelectionModel().getSelectedItem();
        if(customerTM != null){
            lblCustomerId.setText(customerTM.getId());
            txtName.setText(customerTM.getName());
            txtAddress.setText(customerTM.getAddress());
            txtEmail.setText(customerTM.getEmail());
            txtPhone.setText(String.valueOf(customerTM.getPhoneNumber()));
            txtNic.setText(customerTM.getNic());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }



    @FXML
    void deleteCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = customerModel.deleteCustomer(customerId);
            if(isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer!").show();
            }
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void searchcustomerOnAction(ActionEvent event) {

    }

    @FXML
    void updateCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String phoneText = txtPhone.getText();
        String nic = txtNic.getText();

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]$|^[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        String errorStyle = "-fx-border-color: red; -fx-text-fill: white; -fx-background-color: transparent;";
        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";


        if (name.isEmpty() || !name.matches(namePattern)) {
            txtName.setStyle(errorStyle);
            errorMessage.append("- Name is empty or in an incorrect format\n");
            hasErrors = true;

        }else{
            txtName.setStyle(defaultStyle);
        }
        if (address.isEmpty()) {
            txtAddress.setStyle(errorStyle);
            errorMessage.append("- Address is empty\n");
            hasErrors = true;

        }else{
            txtAddress.setStyle(defaultStyle);
        }
        if (email.isEmpty() || !email.matches(emailPattern)) {
            txtEmail.setStyle(errorStyle);
            errorMessage.append("- Email is empty or in an incorrect format\n");
            hasErrors = true;

        }else{
            txtEmail.setStyle(defaultStyle);
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


        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }


        CustomerDTO customerDTO = new CustomerDTO(id, name, address, email, phone, nic);
        boolean isUpdate = customerModel.updateCustomer(customerDTO);
        if (isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update customer!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        txtName.setOnAction(event -> txtAddress.requestFocus());
        txtAddress.setOnAction(event -> txtEmail.requestFocus());
        txtEmail.setOnAction(event -> txtPhone.requestFocus());
        txtPhone.setOnAction(event -> txtNic.requestFocus());


        String defaultStyle = "-fx-border-color: black; -fx-text-fill: white; -fx-background-color: transparent;";


        txtName.setStyle(defaultStyle);
        txtAddress.setStyle(defaultStyle);
        txtEmail.setStyle(defaultStyle);
        txtPhone.setStyle(defaultStyle);
        txtNic.setStyle(defaultStyle);

        searchBar.setOnAction(event ->{
            try{
                searchCustomers();
            }catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error searching customer").show();
            }
        });


        try{
            refreshPage();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }


    }

    private void searchCustomers() throws SQLException, ClassNotFoundException {
        String searchText = searchBar.getText().trim();

        if(searchText.isEmpty()){
            loadTableData();
            return;
        }

        ArrayList<CustomerDTO> customerDTOS = customerModel.getCustomersBySearch(searchText);


        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for(CustomerDTO customerDTO:customerDTOS){
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getId(),
                    customerDTO.getName(),
                    customerDTO.getAddress(),
                    customerDTO.getEmail(),
                    customerDTO.getPhoneNumber(),
                    customerDTO.getNic()


            );
            customerTMS.add(customerTM);
        }
        customerTableView.setItems(customerTMS);
    }



    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextCustomerId();
        loadTableData();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);


        txtName.setText("");
       txtAddress.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtNic.setText("");

    }
    public void loadNextCustomerId() throws SQLException {
        String nextCustomerID = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextCustomerID);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOS = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for(CustomerDTO customerDTO:customerDTOS){
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getId(),
                    customerDTO.getName(),
                    customerDTO.getAddress(),
                    customerDTO.getEmail(),
                    customerDTO.getPhoneNumber(),
                    customerDTO.getNic()


            );
            customerTMS.add(customerTM);
        }
        customerTableView.setItems(customerTMS);

    }

    @FXML
    public void openSendMailModel(ActionEvent event) {
        CustomerTM selectedItem = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sendEmail.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");


            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }



}
