package org.nkon.studentmanagementsystem.Managers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class ErrorAlert {
    @FXML
    public static void ShowErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
