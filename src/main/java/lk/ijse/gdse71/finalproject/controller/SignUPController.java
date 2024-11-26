package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;
import lk.ijse.gdse71.finalproject.model.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUPController implements Initializable {
    @FXML
    public Button btnClose;
    @FXML
    public VBox signUpVBox;

    @FXML
    public AnchorPane signUpAnchorPane;

    @FXML
    public TextField txtEMail;

    @FXML
    public TextField txtConfirmPw;
    @FXML
    private Button btnSignup;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    LoginModel loginModel = new LoginModel();
    @FXML
    void SignupOnAction(ActionEvent event) throws SQLException {
        String email = txtEMail.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String confirmPw = txtConfirmPw.getText();

        if(!password.equals(confirmPw)){
            new Alert(Alert.AlertType.WARNING, "Incorrect Password!").show();
            txtConfirmPw.setStyle("-fx-border-color: red; -fx-text-fill: white; -fx-background-color: transparent;");
        }


        boolean hasErrors = false;

        if(userName.isEmpty() || password.isEmpty() || email.isEmpty() || confirmPw.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all fields for signup!").show();

        }

        LoginDTO loginDTO = new LoginDTO(userName,password,email);
        boolean isSaved = loginModel.saveSignupDetails(loginDTO);

        if(isSaved){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Signup details saved successfully!").show();
            navigateTo("/view/login-page.fxml");
        }

    }

    private void refreshPage() {
        txtUserName.setText("");
        txtPassword.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUserName.setOnAction(event -> txtPassword.requestFocus());
        txtPassword.setOnAction(event -> btnSignup.requestFocus());
    }

    public void navigateTo(String fxmlPath){
        try{
            signUpAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            signUpAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    public void closeSignupAction(ActionEvent actionEvent) {
       navigateTo("/view/login-page.fxml");
    }
}
