package org.nkon.studentmanagementsystem;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
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
            e.printStackTrace();
            return null;
        }
    }
}
