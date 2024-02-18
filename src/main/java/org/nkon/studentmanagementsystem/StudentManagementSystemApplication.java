package org.nkon.studentmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nkon.studentmanagementsystem.Managers.MainAppManager;

import java.io.IOException;

public class StudentManagementSystemApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StudentManagementSystemApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Student Management System");
        stage.setScene(scene);
        stage.show();

        MainAppManager.setMainApp(this);
        MainAppManager.setMainStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}