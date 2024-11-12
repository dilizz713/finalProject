package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MileageTrackingController {

    @FXML
    private TableView<?> TrackingTable;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cmbReservationId;

    @FXML
    private TableColumn<?, ?> colActualMileage;

    @FXML
    private TableColumn<?, ?> colCostforPerKm;

    @FXML
    private TableColumn<?, ?> colEstimatedMileage;

    @FXML
    private TableColumn<?, ?> colExtraChargesPerKm;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colTotalExtraCharges;

    @FXML
    private TableColumn<?, ?> colTrackingId;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblEstimatedMileage;

    @FXML
    private Label lblTotalExtraCharges;

    @FXML
    private Label lblTrackingId;

    @FXML
    private TextField txtActualMileage;

    @FXML
    private TextField txtExtraChargesPerKm;

    @FXML
    private TextField txtSearchBar;

    @FXML
    void SaveOnAction(ActionEvent event) {

    }

    @FXML
    void clickedTable(MouseEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void resetOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

}
