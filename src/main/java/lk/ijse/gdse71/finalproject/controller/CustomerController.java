package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.tm.CustomerTM;
import lk.ijse.gdse71.finalproject.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

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
    private TableView<?> customerTableView;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Pane tablePane;

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

    @FXML
    void SaveCustomerOnAction(ActionEvent event) throws SQLException {
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        int phone = Integer.parseInt(txtPhone.getText());
        String nic = txtNic.getText();

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


        txtName.setStyle(txtName.getStyle()+";-fx-border-color:  black;");
        txtAddress.setStyle(txtName.getStyle()+";-fx-border-color:  black;");
        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color:  black;");
        txtPhone.setStyle(txtPhone.getStyle()+";-fx-border-color:  black;");
        txtNic.setStyle(txtNic.getStyle()+";-fx-border-color:  black;");

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);


        if(!isValidName || name.isEmpty()){
            txtName.setStyle(txtName.getStyle()+";-fx-border-color: red;");
        }
        if(address.isEmpty()){
            txtAddress.setStyle(txtAddress.getStyle()+";-fx-border-color: red;");
        }
        if(!isValidEmail || email.isEmpty()){
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }
        if(phone == -1){
            txtPhone.setStyle(txtPhone.getStyle()+";-fx-border-color: red;");
        }
        if(!isValidNic || nic.isEmpty()){
            txtNic.setStyle(txtNic.getStyle()+";-fx-border-color: red;");
        }

        if(isValidName && isValidNic && isValidEmail ){
            CustomerDTO customerDTO = new CustomerDTO(
                    id,
                    name,
                    address,
                    email,
                    phone,
                    nic
            );

            boolean isSaved = customerModel.saveCustomer(customerDTO);
            if(isSaved){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to save Customer!").show();
            }
        }

    }

    @FXML
    void deleteCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void resetOnAction(ActionEvent event) {

    }

    @FXML
    void searchcustomerOnAction(ActionEvent event) {

    }

    @FXML
    void updateCustomerOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
    }

    CustomerModel customerModel = new CustomerModel();
    private void refreshPage() throws SQLException {
        loadNextCustomerId();

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
}
