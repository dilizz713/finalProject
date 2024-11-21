package lk.ijse.gdse71.finalproject.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeMenuController implements Initializable {
    @FXML
    public VBox homevBox;
    @FXML
    private AnchorPane homeMenuAnchorPane;

    @FXML
    private Label lblCustomersCount;

    @FXML
    private Label lblWelcome;

    @FXML
    private Label label1;

    @FXML
    private Label lblReservationCount;

    @FXML
    private Label lblVehicleCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autoTypeText(lblWelcome, "Welcome");

        try{
            lblCustomersCount.setText(String.valueOf(getCustomerCount()));
            lblVehicleCount.setText(String.valueOf(getVehcileCount()));
            lblReservationCount.setText(String.valueOf(getReservationCount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void autoTypeText(Label lblWelcome, String text) {
        final StringBuilder displayedText = new StringBuilder();
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(100), // Typing speed
                        event -> {
                            if (displayedText.length() < text.length()) {
                                displayedText.append(text.charAt(displayedText.length()));
                                lblWelcome.setText(displayedText.toString());
                            }
                        }
                )
        );
        timeline.setCycleCount(text.length());
        timeline.play();

    }



    private int getReservationCount() throws SQLException {
        String query = "select count(*) as reservationCount from Reservation";
        ResultSet rst = CrudUtil.execute(query);

        if(rst.next()){
            return rst.getInt("reservationCount");
        }
        return 0;
    }

    private int getVehcileCount() throws SQLException {
        String query = "select count(*) as vehicleCount from Vehicle";
        ResultSet rst = CrudUtil.execute(query);

        if(rst.next()){
            return rst.getInt("vehicleCount");
        }
        return 0;
    }

    private int getCustomerCount() throws SQLException {
        String query = "select count(*) as customerCount from Customer";
        ResultSet rst = CrudUtil.execute(query);

        if(rst.next()){
            return rst.getInt("customerCount");
        }
        return 0;
    }
}
