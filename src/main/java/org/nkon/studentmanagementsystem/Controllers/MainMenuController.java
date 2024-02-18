package org.nkon.studentmanagementsystem.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.nkon.studentmanagementsystem.Mediators.LoginMainMenuMediator;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Text Greetings;

    public void setGreetings(String email) {
        Greetings.setText("Welcome "+email+"!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginMainMenuMediator.getInstance().registerMainMenuController(this);
    }
}
