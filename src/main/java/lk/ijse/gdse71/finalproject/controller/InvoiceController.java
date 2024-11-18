package lk.ijse.gdse71.finalproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InvoiceController {
    @FXML
    private ImageView cardImage;

    @FXML
    private Label cardTitle;

    @FXML
    private Label cardDescription;

    @FXML
    private Button actionButton;

    // Method to set content dynamically
    public void setCardContent(String title, String description, String imageUrl) {
        cardTitle.setText(title);
        cardDescription.setText(description);
        cardImage.setImage(new Image(imageUrl));
    }
}
