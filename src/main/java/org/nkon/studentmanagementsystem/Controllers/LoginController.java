package org.nkon.studentmanagementsystem.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.nkon.studentmanagementsystem.Managers.DataBaseConnection;
import org.nkon.studentmanagementsystem.Managers.ErrorAlert;
import org.nkon.studentmanagementsystem.Managers.PasswordAuthentication;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    private PasswordAuthentication passwordAuthentication;

    @FXML
    protected void loginButtonClick() {
        String emailText = email.getText();
        String passwordText = password.getText();


        try {
            Connection conn = DataBaseConnection.connection();
            if (conn == null) {
                return;
            }
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM admins WHERE email='"+emailText+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String hashedPassword = rs.getString(3);
                if (passwordAuthentication.authenticate(passwordText.toCharArray(), hashedPassword)) {
                    System.out.println("Logged correctly!");
                } else {
                    ErrorAlert.ShowErrorAlert("Login failure","Incorrect credentials!");
                }
            } else {
                String new_user_sql = String.format(
                        "INSERT INTO admins ( email, password ) VALUES ( '%s', '%s');",
                        emailText,
                        passwordAuthentication.hash(passwordText.toCharArray())
                );
                st.executeQuery(new_user_sql);
                System.out.println("New user created and logged!");
            }
        } catch (SQLException e) {
            ErrorAlert.ShowErrorAlert("Login Error","An error ocurred while login!");
        }

    }

    @FXML
    protected void exitButtonClick() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordAuthentication = new PasswordAuthentication();
    }
}