package lk.ijse.gdse71.finalproject.controller;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private Button btnBack;

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
    void BackButtonOnAction(ActionEvent event) {
        navigateTo("/view/dash-board.fxml");
    }

    @FXML
    void LoginButtonOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String pw = txtPassword.getText();

        if(userName.equals("dilini") && pw.equals("1234")){
            navigateTo("/view/home-page.fxml");
        }else{
            new Alert(Alert.AlertType.INFORMATION, "user name or password is incorrect!").show();
        }

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
    }
}
