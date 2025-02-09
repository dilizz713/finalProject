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
import lk.ijse.gdse71.finalproject.bo.BOFactory;
import lk.ijse.gdse71.finalproject.bo.custom.LoginBO;
import lk.ijse.gdse71.finalproject.bo.custom.impl.LoginBOImpl;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;

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

    LoginBO loginBO = (LoginBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.LOGIN);

    @FXML
    void navigateToChangePW(ActionEvent event) throws IOException {
        String email = txtEmail.getText();

        if(email.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter your email").show();
            return;
        }

        try {
            //****
            LoginDTO userDetails = loginBO.findByEmail(email);

            if (userDetails != null && email.equals(userDetails.getEmail())) {
                emailText = email;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/forgetPasswordNext.fxml"));
                Parent load = loader.load();

                ForgetPasswordOController controller = loader.getController();
                controller.setEmail(emailText);

                Stage stage = new Stage();
                stage.setScene(new Scene(load));

                stage.initModality(Modality.APPLICATION_MODAL);

                Window underWindow = btnNext.getScene().getWindow();
                stage.initOwner(underWindow);

                stage.showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect Email!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while validating the email.").show();
        }


    }

    private void setEmail(String emailText) {
        this.emailText = emailText;
    }

    @FXML
    void changePWOnAction(ActionEvent event) throws IOException, SQLException {
        String password = txtNewPw.getText();
        String confirmPW = txtConfirmNewPw.getText();

        if(password.isEmpty() || confirmPW.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return;
        }

        if(!password.equals(confirmPW)){
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return;
        }

        System.out.printf("Email : " + emailText);

        if(emailText == null || emailText.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Email is missing.please try again!").show();
            return;
        }

        try{
            //****
            LoginDTO userDetails = loginBO.findByEmail(emailText);

            if(userDetails != null){
                boolean isUpdate = loginBO.updatePasswordByEmail(emailText,password);
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "password update successfully!").show();
                    changePWAnchorPane.getChildren().clear();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update password.").show();
                }

            }else{
                new Alert(Alert.AlertType.ERROR, "User not found.").show();
            }
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the password.").show();
        }

    }


}
