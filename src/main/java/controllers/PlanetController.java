package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import main.java.models.*;
import main.java.SceneNavigator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Planet controller
 *
 */
public class PlanetController implements Initializable {
    private Planet planet;

    @FXML
    private Text planetName = new Text();

    @FXML
    private Text planetTechLevel = new Text();

    @FXML
    private Text planetDescription = new Text();

    @FXML
    private Button travelButton = new Button();

    @FXML
    private BorderPane layout = new BorderPane();

    /**
     * When travelling to a region we want to update the current planet location in the GameData
     */
    @FXML
    public void handleTravelToRegion() {
        RandomEncounter randomEncounter = new RandomEncounter();
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        boolean encounterResult = randomEncounter.shouldEncounter();
        System.out.println("Show encounter: " + encounterResult);
        System.out.println(randomEncounter.getEvent().getSceneName());
        boolean policeNotAllowed = false;
        if (randomEncounter.getEvent().equals(Event.POLICE)) {
            // this ensures us that a policeman cannot encounter the player if
            // they have no inventory
            policeNotAllowed = GameController.getGameData().getPlayer()
                    .getShip().getInventory().isEmpty();
        }
        if (encounterResult && !policeNotAllowed) {
            SceneNavigator.getInstance().navigateTo(randomEncounter.getEvent().getSceneName());
        } else {
            player.getShip().travelTo(planet);
            SceneNavigator.getInstance().navigateTo("planet");
        }
    }

    /**
     * Allows player to view the Universe
     */
    @FXML
    private void handleNext() {
        MusicController.buttonClick();
        SceneNavigator.getInstance().navigateTo("universe");
    }

    /**
     * Navigates to market view.
     */
    @FXML
    private void handleMarketBtnClick() {
        MusicController.multipleThreads();
        MusicController.buttonClick();
        SceneNavigator.getInstance().navigateTo("market");
    }

    /**
     * Creates the scene linked to a specified FXML document
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        planet = GameController.getGameData().getCurrentPlanet();
        // populate fields if the planet has been visited
        if (planet.hasVisited()) {
            planetName.setText(planet.getName());
            planetTechLevel.setText(Integer.toString(planet.getTechLevel()));
            planetDescription.setText(planet.getDescription());
        }
        // disable the travel button if the player is already at the planet
        if (planet.equals(GameController.getGameData().getCurrentPlanetLocation())) {
            travelButton.setDisable(true);
        }
        ImageView background = new ImageView(planet.getBackgroundImage());
        layout.getChildren().add(background);
        background.toBack();
        background.setFitWidth(1289);
        background.setFitHeight(721);
    }
}
