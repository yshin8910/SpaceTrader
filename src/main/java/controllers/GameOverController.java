package main.java.controllers;

import javafx.fxml.FXML;
import main.java.SceneNavigator;

public class GameOverController {
    @FXML
    public void handleViewCreditsClick() {
        SceneNavigator.navigateTo("gameCredits");
    }
}
