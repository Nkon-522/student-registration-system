package org.nkon.studentmanagementsystem.Mediators;

import org.nkon.studentmanagementsystem.Controllers.LoginController;
import org.nkon.studentmanagementsystem.Controllers.MainMenuController;
import org.nkon.studentmanagementsystem.Managers.MainAppManager;

import java.io.IOException;

public class LoginMainMenuMediator {

    private MainMenuController mainMenuController;
    private LoginController loginController;

    public void registerMainMenuController(MainMenuController mainMenuController) { this.mainMenuController = mainMenuController; }
    public void registerLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void handleShowMainMenuScene(String email) throws IOException {
        MainAppManager.loadNewScene("mainMenu.fxml");

        mainMenuController.setGreetings(email);

    }

    public void handleShowLoginScene() throws IOException {
        MainAppManager.loadNewScene("login.fxml");

    }

    private LoginMainMenuMediator(){}

    public static LoginMainMenuMediator getInstance() { return LoginMainMenuMediatorHolder.instance;}

    private static class LoginMainMenuMediatorHolder {
        private static final LoginMainMenuMediator instance = new LoginMainMenuMediator();
    }
}
