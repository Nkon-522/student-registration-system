package org.nkon.studentmanagementsystem.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.nkon.studentmanagementsystem.Managers.AlertManager;
import org.nkon.studentmanagementsystem.Managers.DataBaseConnectionManager;
import org.nkon.studentmanagementsystem.Managers.MainAppManager;
import org.nkon.studentmanagementsystem.Managers.PasswordAuthenticationManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddAdminController {
    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private void backButtonClick() throws IOException {
        MainAppManager.loadNewScene("mainMenu.fxml");
    }

    @FXML
    private void submitButtonClick() {
        String email = emailTextField.getText();
        if (email.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Email field is empty!");
            return;
        }
        String password = passwordTextField.getText();
        if (password.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Password field is empty!");
            return;
        }

        String insert_new_admin_sql = String.format(
                "INSERT INTO admins ( \"email\", \"password\" ) VALUES ( '%s', %s);",
                email,
                new PasswordAuthenticationManager().hash(password.toCharArray())
        );

        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return;
            }
            Statement st = conn.createStatement();
            st.execute(insert_new_admin_sql);
            AlertManager.showSuccessfulAlert("New Admin","The admin was added successfully!");
            MainAppManager.loadNewScene("mainMenu.fxml");
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("New Admin Failure","An error occurred while adding the new admin!");
        } catch (IOException e) {
            AlertManager.ShowErrorAlert("Main Menu Loading Failure","Couldn't go back to the Main Menu!");
        }
    }
}
