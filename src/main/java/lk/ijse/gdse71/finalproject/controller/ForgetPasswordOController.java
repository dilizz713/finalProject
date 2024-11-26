package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;
import lk.ijse.gdse71.finalproject.model.LoginModel;

import java.io.IOException;
import java.sql.SQLException;

public class ForgetPasswordOController {

    @FXML
    private Button btnNext;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnChangePw;

    @FXML
    private TextField txtConfirmNewPw;

    @FXML
    private TextField txtNewPw;

    @FXML
    private AnchorPane changePWAnchorPane;

    String emailText;

    LoginModel loginModel = new LoginModel();

    @FXML
    void navigateToChangePW(ActionEvent event) throws IOException {
        String email = txtEmail.getText();

       try{
           LoginDTO userDetails = loginModel.findByEmail(email);

           if(userDetails != null && email.equals(userDetails.getEmail())){
               emailText = email;
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/forgetPasswordNext.fxml"));
               Parent load = loader.load();

               Stage stage = new Stage();
               stage.setScene(new Scene(load));

               stage.initModality(Modality.APPLICATION_MODAL);

               Window underWindow = btnNext.getScene().getWindow();
               stage.initOwner(underWindow);

               stage.showAndWait();
           }
           else{
               new Alert(Alert.AlertType.ERROR, "Incorrect Email!").show();
           }
       }catch (SQLException e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "An error occurred while validating the email.").show();
       }



    }

    @FXML
    void changePWOnAction(ActionEvent event) throws IOException, SQLException {
        String password = txtNewPw.getText();
        String confirmPW = txtConfirmNewPw.getText();

        LoginDTO userDetails = loginModel.findByEmail(emailText);

        if(password.equals(confirmPW)){
            LoginDTO loginDTO = new LoginDTO(userDetails.getUserName(),userDetails.getPassword(),emailText);
            boolean isUpdate = loginModel.updateSignupDetails(loginDTO);

            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION, "password update successfully!").show();
                changePWAnchorPane.getChildren().clear();
            }
        }

    }







}
