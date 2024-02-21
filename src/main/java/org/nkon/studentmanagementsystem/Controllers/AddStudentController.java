package org.nkon.studentmanagementsystem.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.nkon.studentmanagementsystem.Managers.AlertManager;
import org.nkon.studentmanagementsystem.Managers.DataBaseConnectionManager;
import org.nkon.studentmanagementsystem.Managers.MainAppManager;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField fatherNameTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private ComboBox<String> bloodTypeComboBox;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField classTextField;

    @FXML
    private void backButtonClick() throws IOException {
        MainAppManager.loadNewScene("mainMenu.fxml");
    }

    @FXML
    private void submitButtonClick() {
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Name field is empty!");
            return;
        }
        String fatherName = fatherNameTextField.getText();
        if (fatherName.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Father name field is empty!");
            return;
        }
        String city = cityTextField.getText();
        if (city.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","City field is empty!");
            return;
        }
        String bloodType = bloodTypeComboBox.getValue();
        if (bloodType == null) {
            AlertManager.ShowErrorAlert("Incomplete field","Blood type field is empty!");
            return;
        }
        String phone = phoneTextField.getText();
        if (phone.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Phone field is empty!");
            return;
        }
        String classText = classTextField.getText();
        if (classText.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Class field is empty!");
            return;
        }

        String insert_new_student_sql = String.format(
                "INSERT INTO students ( \"stdName\", \"stdFatherName\", \"stdBlood\", \"stdPhone\", \"stdCity\", class ) VALUES ( '%s', '%s', '%s', '%s', '%s', %s);",
                name,
                fatherName,
                bloodType,
                phone,
                city,
                classText
        );

        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return;
            }
            Statement st = conn.createStatement();
            st.execute(insert_new_student_sql);
            AlertManager.showSuccessfulAlert("New Student","The student was added successfully!");
            MainAppManager.loadNewScene("mainMenu.fxml");
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("New Student Failure","An error occurred while adding the new student!");
        } catch (IOException e) {
            AlertManager.ShowErrorAlert("Main Menu Loading Failure","Couldn't go back to the Main Menu!");
        }
    }

    private void initializeBloodTypeComboBox() {
        bloodTypeComboBox.getItems().add("A");
        bloodTypeComboBox.getItems().add("B");
        bloodTypeComboBox.getItems().add("AB");
        bloodTypeComboBox.getItems().add("O");
    }

    /*
    Source:
    https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
     */
    private void initializePhoneTextField() {
        phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    private void initializeClassTextField() {
        classTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                classTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBloodTypeComboBox();
        initializePhoneTextField();
        initializeClassTextField();
    }
}
