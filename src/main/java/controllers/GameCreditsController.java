package main.java.controllers;

import javafx.fxml.FXML;
import main.java.SceneNavigator;
import main.java.models.GameData;

public class GameCreditsController {
    @FXML
    public void handleStartOverClick() {
        GameData gameData = new GameData();
        GameController.setGameData(gameData);
        SceneNavigator.navigateTo("welcome");
    }
}