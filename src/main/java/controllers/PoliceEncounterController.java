package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import main.java.SceneNavigator;
import main.java.models.NPC.Police;
import main.java.models.Planet;
import main.java.models.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for police encounter scene
 *
 */
public class PoliceEncounterController implements Initializable {
    private Police police;
    private Player player;
    private Planet planet;

    @FXML
    private Text playerCreditAmount;

    @FXML
    private Text dialogText;

    /**
     * This method handles a forfeit with the police
     * @throws Exception exception
     */
    @FXML
    public void handleForfeit() throws Exception {
        police.forfeit(player);
        player.getShip().travelTo(planet);
    }

    /**
     * This method handles a flee with the police
     */
    @FXML
    public void handleFlee() {
        boolean didFlee = player.fleeFrom(police);
        updateCredits();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (didFlee) {
            alert.setTitle("Flee Result");
            alert.setHeaderText("Flee Successful");
            alert.setContentText(police.getFleeSuccessfulText());
            alert.showAndWait();
            // show planet which the player is currently located at and don't travel to new planet
            Planet prevTravelledPlanet = GameController.getGameData().getCurrentPlanetLocation();
            GameController.getGameData().setCurrentPlanet(prevTravelledPlanet);
            GameController.getGameData().setPlayer(player);
            SceneNavigator.navigateTo("planet");
        } else {
            alert.setTitle("Flee Result");
            alert.setHeaderText("Flee Failed");
            alert.setContentText(police.getFleeFailedText());
            alert.showAndWait();
            // failed the flee, travel to the planet and show it
            player.getShip().travelTo(planet);
        }
    }

    /**
     * This method handles a fight with the police
     */
    @FXML
    public void handleFight() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fight Initiated");
        alert.setHeaderText("Risky choice");
        alert.setContentText(police.getBadComplyText());
        alert.showAndWait();

        boolean banditWon = police.fight(player);
        updateCredits();

        alert.setTitle("Fight Result");
        if (banditWon) {
            alert.setHeaderText("Fight Lost");
            alert.setContentText(police.getWinText());
        } else {
            alert.setHeaderText("Fight Won");
            alert.setContentText(police.getDeathText());
        }
        alert.showAndWait();
        player.getShip().travelTo(planet);
    }

    /**
     * Updates the players credits
     */
    public void updateCredits() {
        Player player = GameController.getGameData().getPlayer();
        playerCreditAmount.setText("Your credits: " + player.getCredits() + " credits");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        police = new Police("Crystal");
        player = GameController.getGameData().getPlayer();
        planet = GameController.getGameData().getCurrentPlanet();

        // implementation
        dialogText.setText(police.getWelcomeText());
        updateCredits();
    }
}
