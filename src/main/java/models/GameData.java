package main.java.models;

import javafx.stage.Stage;

/**
 * Model for the global game state
 *
 */
public class GameData {
    private Player player;
    private Universe universe;
    private Planet currentPlanet;
    private Planet currentPlanetLocation;
    private Stage stage;


    /**
     * Creates a new GameData instance with null initial values
     */
    public GameData() {
        this(null);
    }

    /**
     * Creates a new GameData instance with specified player
     * @param player Player
     */
    public GameData(Player player) {
        this(player, null);
    }

    /**
     * Creates a new GameData instance
     * @param player Player
     * @param universe Universe
     */
    public GameData(Player player, Universe universe) {
        this.player = player;
        this.universe = universe;
    }

    /**
     * Sets the current player to the new player provided
     * @param player New player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the current player
     * @return Current player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the universe
     *
     * @param universe Universe
     */
    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    /**
     * Get the current universe
     *
     * @return Current universe
     */
    public Universe getUniverse() {
        return universe;
    }

    /**
     * Sets the current planet
     *
     * @param currentPlanet New current planet
     */
    public void setCurrentPlanet(Planet currentPlanet) {
        this.currentPlanet = currentPlanet;
    }

    /**
     * Gets the current planet
     *
     * @return Current planet
     */
    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    /**
     * Sets the current planet location
     *
     * @param currentPlanetLocation Current planet location
     */
    public void setCurrentPlanetLocation(Planet currentPlanetLocation) {
        this.currentPlanetLocation = currentPlanetLocation;
    }

    /**
     * Gets the current planet location
     *
     * @return Current plant location the player is at
     */
    public Planet getCurrentPlanetLocation() {
        return currentPlanetLocation;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
