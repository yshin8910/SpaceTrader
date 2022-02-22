package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import main.java.models.GameData;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * Main controller class for the root of the application.
 *
 * For now, this is where we will define global state. The state can exist as static fields.
 *
 */
public class GameController implements Initializable {
    private static GameData gameData;

    @FXML
    private VBox root;

    /**
     * Sets the current game data
     * @param gameData New game data
     */
    public static void setGameData(GameData gameData) {
        GameController.gameData = gameData;
    }

    /**
     * Gets the current game data
     * @return Game data
     */
    public static GameData getGameData() {
        return gameData;
    }

    /**
     * Replaces the current scene in the tree with the new scene
     *
     * @param node the node to be swapped into the tree.
     */
    public void setScene(Node node) {
        if (root == null) {
            throw new NoSuchElementException("Root does not exist yet");
        }
        root.getChildren().setAll(node);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.setPrefHeight(720);
        root.setPrefWidth(1280);
        GameController.setGameData(new GameData());
    }
}
