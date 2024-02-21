package org.nkon.studentmanagementsystem.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.nkon.studentmanagementsystem.Entities.Admin;
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

public class ShowAdminsController implements Initializable {
    @FXML
    private Pagination adminsPagination;
    @FXML
    private TableView<Admin> adminsTableView;
    @FXML
    private TableColumn<Admin,String> emailTableColumn;
    @FXML
    private TableColumn<Admin,String> passwordTableColumn;

    static private final Integer numberOfRowsPerPage = 7;

    static private Integer firstIdAccess;
    static private Integer lastIdAccess;

    @FXML
    private void handleBackButton() throws IOException {
        MainAppManager.loadNewScene("mainMenu.fxml");
    }

    private void initializeAdminsTableView () {
        emailTableColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        passwordTableColumn.setCellValueFactory(data -> data.getValue().passwordProperty());
    }

    private int getTotalNumberOfAdmins() {
        int totalNumberOfAdmins = 0;
        try {
            Connection conn = DataBaseConnectionManager.connection();
            if (conn == null) {
                return -1;
            }
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT COUNT(id) FROM admins");
            if (resultSet.next()) {
                totalNumberOfAdmins = resultSet.getInt(1);
            }
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            AlertManager.ShowErrorAlert("Admins request failure","An error occurred while requesting for the list of admins!");
        }
        return totalNumberOfAdmins;
    }

    private void managePagination(ResultSet resultSet) throws SQLException {
        ObservableList<Admin> adminsList = FXCollections.observableArrayList();
        if (resultSet.next()) {
            resultSet.last();
            lastIdAccess = resultSet.getInt(1);
            resultSet.first();
            firstIdAccess = resultSet.getInt(1);
        }
        resultSet.beforeFirst();
        while (resultSet.next()) {
            adminsList.add(new Admin(
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        adminsTableView.setItems(adminsList);
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
            AlertManager.ShowErrorAlert("Admins request failure","An error occurred while requesting for the list of admins!");
        }
    }

    private void getNextPage() {
        String get_next_page_sql =
                String.format(
                        "SELECT " +
                        "id, \"email\", \"password\" " +
                        "FROM admins " +
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
                                "id, \"email\", \"password\" " +
                                "FROM admins " +
                                "WHERE id < %s " +
                                "ORDER BY id DESC " +
                                "LIMIT %s " +
                                ") as t ORDER BY id ASC",
                        firstIdAccess,
                        numberOfRowsPerPage
                );
        handleGetPage(get_previous_page_sql);
    }

    private void initializeAdminsPagination() {
        String get_first_page_sql =
                String.format(
                        "SELECT " +
                                "id, \"email\", \"password\" " +
                                "FROM admins " +
                                "ORDER BY id " +
                                "LIMIT %s",
                        numberOfRowsPerPage
                );

        handleGetPage(get_first_page_sql);
        adminsPagination.setPageCount((int) Math.ceil( getTotalNumberOfAdmins() / Double.valueOf(numberOfRowsPerPage) ));
        adminsPagination.currentPageIndexProperty().addListener(
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
        initializeAdminsTableView();
        initializeAdminsPagination();
    }
}
