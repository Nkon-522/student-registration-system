package org.nkon.studentmanagementsystem.Managers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AlertManager {
    @FXML
    public static void ShowErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    public static void showSuccessfulAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);

        alert.show();
    }
}
