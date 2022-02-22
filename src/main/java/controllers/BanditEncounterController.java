package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.java.SceneNavigator;
import main.java.models.BanditDemandResult;
import main.java.models.NPC.Bandit;
import main.java.models.Planet;
import main.java.models.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for bandit encounter scene
 *
 */
public class BanditEncounterController implements Initializable {
    private Bandit bandit;
    private Player player;
    private Planet planet;

    @FXML
    private Text playerCreditAmount;

    @FXML
    private Text dialogText;

    @FXML
    private Button payBtn;

    @FXML
    public void handleFlee() {
        boolean didFlee = player.fleeFrom(bandit);
        updateCredits();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (didFlee) {
            alert.setTitle("Flee Result");
            alert.setHeaderText("Flee Successful");
            alert.setContentText(bandit.getFleeSuccessfulText());
            alert.showAndWait();
        } else {
            alert.setTitle("Flee Result");
            alert.setHeaderText("Flee Failed");
            alert.setContentText(bandit.getFleeFailedText() + "\n"
                    + "I am taking all your credits and damaging your ship! I hope we meet again.");
            alert.showAndWait();
        }

        if (didFlee) {
            // show planet which the player is currently located at and don't travel to new planet
            Planet prevTravelledPlanet = GameController.getGameData().getCurrentPlanetLocation();
            GameController.getGameData().setCurrentPlanet(prevTravelledPlanet);
            GameController.getGameData().setPlayer(player);
            SceneNavigator.navigateTo("planet");
        } else {
            // failed the flee, travel to the planet and show it
            player.getShip().travelTo(planet);
        }
    }

    @FXML
    public void handlePay() {
        BanditDemandResult result = bandit.demandMoney(player);
        updateCredits();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        switch (result) {
        case PAY:
            alert.setTitle("Pay Result");
            alert.setHeaderText("Pay Successful");
            alert.setContentText(bandit.getGoodComplyText());
            alert.showAndWait();
            break;
        case NO_ITEMS:
            alert.setTitle("Pay Result");
            alert.setHeaderText("Pay Unsuccessful");
            alert.setContentText(bandit.getNoItemsText());
            alert.showAndWait();
            break;
        case TAKE_ALL_ITEMS:
            alert.setTitle("Pay Result");
            alert.setHeaderText("Pay Unsuccessful");
            alert.setContentText(bandit.getTakeAllItemsText());
            alert.showAndWait();
            break;
        default:
            break;
        }

        player.getShip().travelTo(planet);
    }

    /**
     * Updates the players credits
     */
    public void updateCredits() {
        Player player = GameController.getGameData().getPlayer();
        playerCreditAmount.setText("Your credits: " + player.getCredits() + " credits");
    }

    @FXML
    public void handleFight() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fight Initiated");
        alert.setHeaderText("Unwise choice");
        alert.setContentText(bandit.getBadComplyText());
        alert.showAndWait();

        boolean banditWon = bandit.fight(player);
        updateCredits();

        alert.setTitle("Fight Result");
        if (banditWon) {
            alert.setHeaderText("Fight Lost");
            alert.setContentText(bandit.getWinText());
        } else {
            alert.setHeaderText("Fight Won");
            alert.setContentText(bandit.getDeathText());
        }
        alert.showAndWait();
        player.getShip().travelTo(planet);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bandit = new Bandit("Victor");
        player = GameController.getGameData().getPlayer();
        planet = GameController.getGameData().getCurrentPlanet();
        dialogText.setText(bandit.getWelcomeText());
        updateCredits();
        payBtn.setText(payBtn.getText() + " " + bandit.getDemandCreditAmount() + " credits");
    }
}
