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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class OperationController implements Initializable {
    @FXML
    private TextField idTextField;

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

    private void cleanFields(){
        idTextField.clear();

        nameTextField.clear();
        nameTextField.setDisable(true);

        fatherNameTextField.clear();
        fatherNameTextField.setDisable(true);

        cityTextField.clear();
        cityTextField.setDisable(true);

        bloodTypeComboBox.setValue(bloodTypeComboBox.getPromptText());
        bloodTypeComboBox.setDisable(true);

        phoneTextField.clear();
        phoneTextField.setDisable(true);

        classTextField.clear();
        classTextField.setDisable(true);
    }

    private void activateFields() {
        nameTextField.setDisable(false);
        fatherNameTextField.setDisable(false);
        cityTextField.setDisable(false);
        bloodTypeComboBox.setDisable(false);
        phoneTextField.setDisable(false);
        classTextField.setDisable(false);
    }

    private void fillFields(ResultSet resultSet) throws SQLException {
        nameTextField.setText(resultSet.getString(2));
        fatherNameTextField.setText(resultSet.getString(3));
        cityTextField.setText(resultSet.getString(4));
        bloodTypeComboBox.setValue(resultSet.getString(5));
        phoneTextField.setText(resultSet.getString(6));
        classTextField.setText(resultSet.getString(7));
    }

    @FXML
    private void searchButtonClick() {
        String id = idTextField.getText();
        if (id.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Id field is empty!");
            return;
        }

        String select_student_by_id_sql =
                String.format(
                        "SELECT " +
                                "id, \"stdName\", \"stdFatherName\", \"stdCity\", \"stdBlood\" ,\"stdPhone\", class " +
                                "FROM students " +
                                "WHERE id = %s ",
                        id
                );

        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return;
            }
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery(select_student_by_id_sql);
            if (resultSet.next()) {
                activateFields();
                fillFields(resultSet);
            } else {
                cleanFields();
                AlertManager.ShowErrorAlert("Student not found","The student was not found!");
            }
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("Delete Student Failure","An error occurred while deleting the student!");
        }
    }

    @FXML
    private void deleteButtonClick() {
        String id = idTextField.getText();
        if (id.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Id field is empty!");
            return;
        }

        String delete_student_sql = String.format(
                "DELETE FROM students " +
                        "WHERE id = %s ;",
                id
        );

        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return;
            }
            Statement st = conn.createStatement();
            st.execute(delete_student_sql);
            AlertManager.showSuccessfulAlert("Deleted Student","The student was deleted successfully!");
            cleanFields();
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("Delete Student Failure","An error occurred while deleting the student!");
        }
    }

    @FXML
    private void updateButtonClick() {
        String id = idTextField.getText();
        if (id.isEmpty()) {
            AlertManager.ShowErrorAlert("Incomplete field","Id field is empty!");
            return;
        }

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

        String update_student_sql = String.format(
                "UPDATE students " +
                        "SET " +
                        "\"stdName\" = '%s'," +
                        "\"stdFatherName\" = '%s'," +
                        "\"stdBlood\" = '%s',"+
                        "\"stdPhone\" = '%s',"+
                        "\"stdCity\" = '%s'," +
                        "class = %s" +
                        "WHERE id = %s ;",
                name,
                fatherName,
                bloodType,
                phone,
                city,
                classText,
                id
        );

        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return;
            }
            Statement st = conn.createStatement();
            st.execute(update_student_sql);
            AlertManager.showSuccessfulAlert("Updated Student","The student was updated successfully!");
            cleanFields();
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("Update Student Failure","An error occurred while updating the student!");
        }
    }

    private void initializeBloodTypeComboBox() {
        bloodTypeComboBox.getItems().add("A");
        bloodTypeComboBox.getItems().add("B");
        bloodTypeComboBox.getItems().add("AB");
        bloodTypeComboBox.getItems().add("O");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBloodTypeComboBox();
        cleanFields();
    }
}
