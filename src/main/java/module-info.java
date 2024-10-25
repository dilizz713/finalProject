module lk.ijse.gdse71.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;



    opens lk.ijse.gdse71.finalproject.controller to javafx.fxml;
    exports lk.ijse.gdse71.finalproject;
}