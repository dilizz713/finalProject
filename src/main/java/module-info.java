module lk.ijse.gdse71.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires java.mail;


    opens lk.ijse.gdse71.finalproject.dto.tm to javafx.base;
    opens lk.ijse.gdse71.finalproject.controller to javafx.fxml;
    exports lk.ijse.gdse71.finalproject;
}