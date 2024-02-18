package org.nkon.studentmanagementsystem.Managers;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnectionManager {
    static String DB_URL;
    static String USER;
    static String PASSWORD;

    static {
        Dotenv dotenv = Dotenv.load();
        DB_URL = dotenv.get("DB_URL");
        USER = dotenv.get("USER");
        PASSWORD = dotenv.get("PASSWORD");
    }

    public static Connection connection() {
        try {
            return DriverManager.getConnection(
                    DB_URL,
                    USER,
                    PASSWORD
            );
        } catch (Exception e) {
            ErrorAlertManager.ShowErrorAlert("Database Connection Error", "Couldn't establish a connection to the database");
            return null;
        }
    }
}
