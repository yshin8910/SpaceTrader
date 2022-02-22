package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import main.java.SceneNavigator;
import main.java.models.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for defining the logic needed in the character sheet screen.
 *
 */
public class CharacterSheetController implements Initializable {
    @FXML
    private Text name;

    @FXML
    private Text difficulty;

    @FXML
    private Text credits;

    @FXML
    private Text skillPoints;

    @FXML
    private Text engineerPoints;

    @FXML
    private Text pilotPoints;

    @FXML
    private Text fighterPoints;

    @FXML
    private Text merchantPoints;

    /**
     * Handles functionality for going back to the configuration
     * screen.
     */
    @FXML
    private void handleGoBack() {
        MusicController.buttonClick();
        SceneNavigator.getInstance().navigateTo("config");
    }

    /**
     * Handles functionality for going to the universe map
     */
    @FXML
    private void handleNext() {
        MusicController.buttonClick();
        SceneNavigator.getInstance().navigateTo("universe");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Player player = GameController.getGameData().getPlayer();
        name.setText(player.getName());
        difficulty.setText(player.getDifficulty().name().toLowerCase());
        skillPoints.setText(Integer.toString(player.getDifficulty().getSkillPoints()));
        credits.setText(Integer.toString(player.getDifficulty().getCredits()));
        engineerPoints.setText(Integer.toString(player.getEngineerPts()));
        pilotPoints.setText(Integer.toString(player.getPilotPts()));
        fighterPoints.setText(Integer.toString(player.getFighterPts()));
        merchantPoints.setText(Integer.toString(player.getMerchantPts()));
    }
}
