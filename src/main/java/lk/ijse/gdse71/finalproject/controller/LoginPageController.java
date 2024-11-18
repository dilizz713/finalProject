package lk.ijse.gdse71.finalproject.controller;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;
import lk.ijse.gdse71.finalproject.model.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    public Button btnSignup;

    @FXML
    private Button btnLogin;

    @FXML
    private ToggleButton btnShow;

    @FXML
    private ImageView loginImage;

    @FXML
    private AnchorPane loginPageAnchorPane;

    @FXML
    private Label shownPwLabel;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void SignupBtnOnAction(ActionEvent event) {
        navigateTo("/view/sign-up-view.fxml");
    }


    LoginModel loginModel = new LoginModel();
    @FXML
    void LoginButtonOnAction(ActionEvent event) {
        String userName = txtUserName.getText().trim();
        String pw = txtPassword.getText().trim();

        if(userName.isEmpty() || pw.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter both username and password").show();
            return;
        }
        try{
            LoginDTO loginDTO = loginModel.findByUserName(userName);

            if(loginDTO == null){
                new Alert(Alert.AlertType.INFORMATION,"User name not found. Please signup first").show();
            }else if(!loginDTO.getPassword().equals(pw)){
                new Alert(Alert.AlertType.ERROR, "Incorrect password!. Please try again!").show();
            }else{
                navigateTo("/view/home-page.fxml");
            }
        } catch (SQLException e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "An error ocuured while logging in.").show();
        }

        /*String userName = txtUserName.getText();
        String pw = txtPassword.getText();

        LoginDTO loginDTO = new LoginDTO();

        boolean hasErrors = false;

        if(userName.isEmpty() || pw.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Enter User Name and Password");
        }

        if(!userName.equals(loginDTO.getUserName()) || !pw.equals(loginDTO.getPassword())){
            new Alert(Alert.AlertType.ERROR,"Invalid Credentials! Please try again" );
        }else{
            navigateTo("/view/home-page.fxml");
        }
*/

       /* if(userName.equals("dilini") && pw.equals("1234")){
            navigateTo("/view/home-page.fxml");
        }else{
            new Alert(Alert.AlertType.INFORMATION, "user name or password is incorrect!").show();
        }*/

    }

    @FXML
    void passwordKeyFieldType(KeyEvent event) {
        shownPwLabel.textProperty().bind(Bindings.concat(txtPassword.getText()));

    }

    @FXML
    void passwordShowButtonOnAction(ActionEvent event) {
        if(btnShow.isSelected()){
            shownPwLabel.setVisible(true);
            shownPwLabel.textProperty().bind(Bindings.concat(txtPassword.getText()));
            btnShow.setText("Hide");
        }else{
            shownPwLabel.setVisible(false);
            btnShow.setText("Show");
        }
    }
    public void navigateTo(String fxmlPath){
        try{
            loginPageAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            loginPageAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shownPwLabel.setVisible(false);

        txtUserName.setOnAction(event -> txtPassword.requestFocus());



    }

}
