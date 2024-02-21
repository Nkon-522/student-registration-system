package org.nkon.studentmanagementsystem.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.nkon.studentmanagementsystem.Managers.MainAppManager;
import org.nkon.studentmanagementsystem.Mediators.LoginMainMenuMediator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    private String email;

    @FXML
    private Text Greetings;

    public void setGreetings(String email) {
        this.email = email;
        Greetings.setText("Welcome "+email+"!");
    }

    @FXML
    private void handleHomeMenuItemClick() throws IOException {
        LoginMainMenuMediator.getInstance().handleShowMainMenuScene(email);
    }

    @FXML
    private void handleLogoutMenuItemClick() throws IOException {
        LoginMainMenuMediator.getInstance().handleShowLoginScene();
    }

    @FXML
    private void handleAboutMenuItemClick() {
        System.out.println("ABOUT");
    }

    @FXML
    private void handleAddStudentButton() throws IOException {
        MainAppManager.loadNewScene("addStudent.fxml");
    }

    @FXML
    private void handleAddAdminButton() throws IOException {
        MainAppManager.loadNewScene("addAdmin.fxml");
    }

    @FXML
    private void handleShowStudentsButton() throws IOException {
        MainAppManager.loadNewScene("showStudents.fxml");
    }

    @FXML
    private void handleShowAdminsButton() throws IOException {
        MainAppManager.loadNewScene("showAdmins.fxml");
    }

    @FXML
    private void handleOperationButton() throws IOException {
        MainAppManager.loadNewScene("operation.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginMainMenuMediator.getInstance().registerMainMenuController(this);
    }
}
