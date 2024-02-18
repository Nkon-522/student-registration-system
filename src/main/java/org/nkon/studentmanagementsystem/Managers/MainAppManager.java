package org.nkon.studentmanagementsystem.Managers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainAppManager {
    static private Stage mainStage;

    static private Application mainApp;

    public static void setMainStage(Stage mainStage) {
        MainAppManager.mainStage = mainStage;
    }

    public static void setMainApp(Application mainApp) {
        MainAppManager.mainApp = mainApp;
    }

    public static void loadNewScene(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainApp.getClass().getResource(file));
        Parent parent = fxmlLoader.load();
        mainStage.setScene(new Scene(parent));
    }

    private MainAppManager(){}
}
