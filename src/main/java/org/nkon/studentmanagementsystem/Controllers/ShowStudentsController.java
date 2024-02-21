package org.nkon.studentmanagementsystem.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.nkon.studentmanagementsystem.Entities.Student;
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

public class ShowStudentsController implements Initializable {
    @FXML
    private Pagination studentsPagination;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn<Student,String> nameTableColumn;
    @FXML
    private TableColumn<Student,String> fatherNameTableColumn;
    @FXML
    private TableColumn<Student,String> bloodTableColumn;
    @FXML
    private TableColumn<Student,String> phoneTableColumn;
    @FXML
    private TableColumn<Student,String> cityTableColumn;
    @FXML
    private TableColumn<Student,Number> classTableColumn;

    static private final Integer numberOfRowsPerPage = 7;

    static private Integer firstIdAccess;

    static private Integer lastIdAccess;

    @FXML
    private void handleBackButton() throws IOException {
        MainAppManager.loadNewScene("mainMenu.fxml");
    }

    private void initializeStudentsTableView () {
        nameTableColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        fatherNameTableColumn.setCellValueFactory(data -> data.getValue().fatherNameProperty());
        bloodTableColumn.setCellValueFactory(data -> data.getValue().bloodProperty());
        phoneTableColumn.setCellValueFactory(data -> data.getValue().phoneProperty());
        cityTableColumn.setCellValueFactory(data -> data.getValue().cityProperty());
        classTableColumn.setCellValueFactory(data -> data.getValue().classProperty());
    }

    @SuppressWarnings("unused")
    private int getLastId() {
        int lastId = 0;
        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return -1;
            }
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = st.executeQuery("SELECT id FROM students ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                lastId = resultSet.getInt(1);
            } else {
                lastId = -1;
            }
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("Students request failure","An error occurred while requesting for the list of students!");
        }
        return lastId;
    }

    private int getTotalNumberOfStudents() {
        int totalNumberOfStudents = 0;
        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return -1;
            }
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT COUNT(id) FROM students");
            if (resultSet.next()) {
                totalNumberOfStudents = resultSet.getInt(1);
            }
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("Students request failure","An error occurred while requesting for the list of students!");
        }
        return totalNumberOfStudents;
    }

    private void managePagination(ResultSet resultSet) throws SQLException {
        ObservableList<Student> studentsList = FXCollections.observableArrayList();
        if (resultSet.next()) {
            resultSet.last();
            lastIdAccess = resultSet.getInt(1);
            resultSet.first();
            firstIdAccess = resultSet.getInt(1);
        }
        resultSet.beforeFirst();
        while (resultSet.next()) {
            studentsList.add(new Student(
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getInt(7)
                    )
            );
        }
        studentsTableView.setItems(studentsList);
    }

    private void handleGetPage(String sql) {
        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return;
            }
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = st.executeQuery(sql);
            managePagination(resultSet);
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("Students request failure","An error occurred while requesting for the list of students!");
        }
    }

    private void getNextPage() {
        String get_next_page_sql =
                String.format(
                        "SELECT " +
                                "id, \"stdName\", \"stdFatherName\", \"stdBlood\", \"stdPhone\", \"stdCity\", class " +
                                "FROM students " +
                                "WHERE id > %s " +
                                "ORDER BY id ASC " +
                                "LIMIT %s;",
                        lastIdAccess,
                        numberOfRowsPerPage
                );
        handleGetPage(get_next_page_sql);
    }

    private void getPrevPage() {
        String get_previous_page_sql =
                String.format(
                        "SELECT * FROM ( " +
                                "SELECT " +
                                "id, \"stdName\", \"stdFatherName\", \"stdBlood\", \"stdPhone\", \"stdCity\", class " +
                                "FROM students " +
                                "WHERE id < %s " +
                                "ORDER BY id DESC " +
                                "LIMIT %s " +
                                ") as t ORDER BY id ASC",
                        firstIdAccess,
                        numberOfRowsPerPage
                );
        handleGetPage(get_previous_page_sql);
    }

    private void initializeStudentsPagination() {
        String get_first_page_sql =
                String.format(
                        "SELECT " +
                        "id, \"stdName\", \"stdFatherName\", \"stdBlood\", \"stdPhone\", \"stdCity\", class " +
                        "FROM students " +
                        "ORDER BY id " +
                        "LIMIT %s",
                        numberOfRowsPerPage
                );

        handleGetPage(get_first_page_sql);
        studentsPagination.setPageCount((int) Math.ceil( getTotalNumberOfStudents() / Double.valueOf(numberOfRowsPerPage) ));
        studentsPagination.currentPageIndexProperty().addListener(
                (obs, oldIndex, newIndex) -> {
                    if (oldIndex.intValue() < newIndex.intValue()) {
                        getNextPage();
                    }else{
                        getPrevPage();
                    }
                }
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeStudentsTableView();
        initializeStudentsPagination();
    }
}
