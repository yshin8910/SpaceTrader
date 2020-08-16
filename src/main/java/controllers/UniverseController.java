package main.java.controllers;

import java.awt.*;
import java.net.URL;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;

import java.util.ArrayList;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.SceneNavigator;
import main.java.models.*;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Controller for the universe view
 *
 * @author Sam & Isaac & A'maya
 */
public class UniverseController implements Initializable {
    private Universe universe;
    private static boolean clicked = false;
    private static Random rand = new Random();

    @FXML
    private TilePane universeMap;

    @FXML
    private ProgressBar shipHealthProgressBar;

    @FXML
    private ProgressBar shipFuelProgressBar;

    /**
     * For each planet render a circle at the position of its coordinates
     */
    public void render() {
        GameData gameData = GameController.getGameData();
        Ship ship = gameData.getPlayer().getShip();

        // render the ship metadata
        shipHealthProgressBar.setProgress(ship.getHealth() / (double) ship.getHealthCapacity());
        shipFuelProgressBar.setProgress(ship.getFuelTank().getFuelAmount()
                / ship.getFuelTank().getFuelCapacity());

        // render the universe
        ArrayList<Planet> planets = universe.getPlanets();
        universeMap.getChildren().clear();
        universeMap.setPrefColumns(3);
        universeMap.setPrefRows(4);

        for (Planet planet : planets) {
            HBox planetContainer = new HBox();
            planetContainer.setPrefWidth(300);
            planetContainer.setAlignment(Pos.CENTER);

            ImageView planetImage = new ImageView(planet.getPlanetImage());
            planetImage.setFitWidth(60);
            planetImage.setFitHeight(60);
            planetImage.setTranslateX(planet.getOrbitRadius());
            planetImage.setTranslateY(planet.getOrbitTilt());
            planetImage.setOnMouseClicked(new HandlePlanetClicked(planet));
            Platform.runLater(() -> {
                Bounds boundsInScene = planetImage.localToScene(planetImage.getBoundsInLocal());
                planet.setCoordinates(new Point((int) boundsInScene.getMinX() / 100,
                        (int) boundsInScene.getMinY() / 100));
            });
            // check if we are located at the current planet
            if (planet.equals(gameData.getCurrentPlanetLocation())) {
                ImageView shipImage = new ImageView(gameData.getPlayer().getShip().getShipImage());
                shipImage.setFitWidth(40);
                shipImage.setFitHeight(40);
                shipImage.setTranslateX(planet.getOrbitRadius() + 120);
                shipImage.setTranslateY(planet.getOrbitTilt());
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(10.0);
                dropShadow.setColor(Color.color(0, 1, 0));
                planetImage.setEffect(dropShadow);
                planetContainer.getChildren().add(shipImage);
            }

            planetContainer.getChildren().add(planetImage);
            universeMap.getChildren().add(planetContainer);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MusicController.multipleThreads();
        GameData gameData = GameController.getGameData();

        // load universe from game data
        if (gameData.getUniverse() != null) {
            universe = gameData.getUniverse();
            render();
            return;
        }

        universe = new Universe();
        universe.generatePlanets();
        gameData.setUniverse(universe);
        int randomPlanetIndex = rand.nextInt(universe.getPlanets().size());
        Planet randomFirstPlanet = universe.getPlanets().get(randomPlanetIndex);
        randomFirstPlanet.setVisited(true);
        gameData.setCurrentPlanetLocation(randomFirstPlanet);
        render();
    }


    /**
     * EventHandler class for when a planet is clicked
     */
    public class HandlePlanetClicked implements EventHandler<MouseEvent> {
        private Planet planet;

        /**
         * Assigns the planet the clicked planet for later use.
         *
         * @param planet Clicked planet
         */
        public HandlePlanetClicked(Planet planet) {
            this.planet = planet;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            MusicController.multipleThreads();
            MusicController.buttonClick();
            if (!clicked) {
                Popup pop = new Popup();
                pop.display();
                clicked  = true;
            }
            GameData gameData = GameController.getGameData();
            gameData.setCurrentPlanet(planet);
            SceneNavigator.getInstance().navigateTo("planet");
        }

        /**
         * This is a Popup Window Class
         * author: A'maya Solomon
         */
        private class Popup { //
            /**
             * Method displays pop up window
             */
            public void display() {
                Stage popUp = new Stage();
                popUp.initModality(Modality.APPLICATION_MODAL);
                popUp.setTitle("GAME INSTRUCTIONS");

                // Welcome Text
                Label welcome = new Label("Welcome to SpaceTrader, fellow Earthling!");
                welcome.setTextFill(Color.DARKOLIVEGREEN);
                welcome.setFont(new Font("Verdana", 20));

                // Instructions
                Label label = new Label(" Once you close this message,"
                        + " you will see the main map of the galaxy."
                        + "\n The planet highlighted in green represents the planet that "
                        + "you are currently on. \n In order to "
                        +  "travel to a new planet, click on another planet on the map. "
                        + "\n May your journey in the galaxy serve you well.");
                label.setTextFill(Color.DARKSLATEBLUE);
                label.setFont(new Font("Verdana", 12));

                // Close Button
                Button button = new Button("CLOSE");
                button.setOnAction(e -> {
                    popUp.close();
                });

                // VBox
                VBox layout = new VBox(10);
                layout.setStyle("-fx-background-color: #D3D3D3");
                layout.getChildren().addAll(welcome, label, button);
                layout.setAlignment(Pos.CENTER);
                layout.setSpacing(20);
                Scene scene1 = new Scene(layout, 550, 250, Color.LIGHTBLUE);
                popUp.setScene(scene1);
                popUp.showAndWait();
            }
        }
    }

}