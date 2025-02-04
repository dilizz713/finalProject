module lk.ijse.gdse71.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires java.mail;

    opens lk.ijse.gdse71.finalproject.controller to javafx.fxml;
    exports lk.ijse.gdse71.finalproject;
    opens lk.ijse.gdse71.finalproject.view.tdm to javafx.base;
    exports lk.ijse.gdse71.finalproject.controller;
}