package org.nkon.studentmanagementsystem.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.nkon.studentmanagementsystem.Managers.DataBaseConnectionManager;
import org.nkon.studentmanagementsystem.Managers.ErrorAlertManager;
import org.nkon.studentmanagementsystem.Managers.PasswordAuthenticationManager;
import org.nkon.studentmanagementsystem.Mediators.LoginMainMenuMediator;

import java.io.IOException;
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

    private PasswordAuthenticationManager passwordAuthentication;

    @FXML
    protected void loginButtonClick() {
        String emailText = email.getText();
        String passwordText = password.getText();

        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return;
            }
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM admins WHERE email='"+emailText+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String hashedPassword = rs.getString(3);
                if (passwordAuthentication.authenticate(passwordText.toCharArray(), hashedPassword)) {
                    LoginMainMenuMediator.getInstance().handleShowMainMenuScene(emailText);
                } else {
                    ErrorAlertManager.ShowErrorAlert("Login failure","Incorrect credentials!");
                }
            } else {
                String new_user_sql = String.format(
                        "INSERT INTO admins ( email, password ) VALUES ( '%s', '%s');",
                        emailText,
                        passwordAuthentication.hash(passwordText.toCharArray())
                );
                st.executeQuery(new_user_sql);
                LoginMainMenuMediator.getInstance().handleShowMainMenuScene(emailText);
            }
            conn.close();
        } catch (SQLException | IOException e) {
            ErrorAlertManager.ShowErrorAlert("Login Error","An error occurred while login!");
        }

    }

    @FXML
    protected void exitButtonClick() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginMainMenuMediator.getInstance().registerLoginController(this);
        passwordAuthentication = new PasswordAuthenticationManager();
    }


}