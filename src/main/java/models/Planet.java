package main.java.models;

import javafx.scene.image.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Planet/Region within SpaceTrader universe
 *
 * @author Isaac and Sam
 * */
public class Planet {
    private int techLevel;
    private String name;
    private Point coordinates;
    private MarketPlace market;
    private boolean visited;
    private String description;
    private Image planetImage;
    private Image backgroundImage;
    private static Random rand = new Random();
    private int orbitRadius;
    private int orbitTilt;


    /**
     * Creates a new planet
     *
     * @param name Planet name
     * @param description Planet description
     * @param techLevel Technology level
     * @param visited Planet visited by player
     * @param coordinates Point location of planet in universe
     * @param planetImage Planet image
     * @param backgroundImage Background image for the planet
     * */
    public Planet(String name, String description, int techLevel,
                  boolean visited, Point coordinates, Image planetImage, Image backgroundImage) {
        this.name = name;
        this.description = description;
        this.techLevel = techLevel;
        this.visited = visited;
        this.coordinates = coordinates;
        this.planetImage = planetImage;
        this.backgroundImage = backgroundImage;
        this.orbitRadius = (rand.nextInt(150) + 1) - 75;
        this.orbitTilt = (rand.nextInt(40) + 1) - 20;
        this.market = new MarketPlace();
    }

    /**
     * Creates a new planet with random tech level and starting point
     *
     * @param name Planet name
     * @param description Planet description
     * @param planetImage Image
     * @param backgroundImage Background image for the planet
     */
    public Planet(String name, String description, Image planetImage, Image backgroundImage) {
        this(name, description, rand.nextInt(9) + 1, false,
                (new Point(rand.nextInt(21), rand.nextInt(21))), planetImage, backgroundImage);
    }

    /**
     * Technology Level
     *
     * @param techLevel The technology level for this planet.
     * */
    public void setTechLevel(int techLevel) {
        this.techLevel = techLevel;
    }

    /**
     * Changes whether the boolean value representing if planet has
     * been visited by the player.
     *
     * @param visited The boolean representation of a planet's
     *                discovery by the player.
     * */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Planet coordinates
     *
     * @return This planet's x and y point coordinates in the
     * universe.
     * */
    public Point getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the market for a planet
     * @return Market
     */
    public MarketPlace getMarket() {
        return market;
    }

    /**
     * Planet name
     *
     * @return Planet name
     * */
    public String getName() {
        return name;
    }

    /**
     * Planet description
     *
     * @return Planet's description
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Technology level
     *
     * @return The technology level for this planet.
     * */
    public int getTechLevel() {
        return techLevel;
    }

    /**
     * Returns whether the player has visited this planet or not.
     *
     * @return True is the player has visited this planet, false
     * otherwise.
     * */
    public boolean hasVisited() {
        return visited;
    }

    /**
     * Distance between this planet and another planet.
     *
     * @param o The distant planet
     * @return The distance between this planet and another planet.
     * */
    public double distance(Planet o) {
        return coordinates.distance(o.coordinates);
    }

    /**
     * Gets the planets image
     * @return Planet image
     */
    public Image getPlanetImage() {
        return planetImage;
    }

    /**
     * Gets the background image for the planet
     * @return Background image
     */
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Gets the planets orbit radius
     * @return Orbit radius
     */
    public int getOrbitRadius() {
        return orbitRadius;
    }

    /**
     * Gets the planets orbit tilt
     * @return Orbit tilt
     */
    public int getOrbitTilt() {
        return orbitTilt;
    }

    /**
     * Sets the coordinates for the planet
     * @param coordinates Planet coordinates
     */
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Checks if two planets are equal
     *
     * @param o Other planet
     * @return True if the planets are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Planet)) {
            return false;
        }
        Planet other = (Planet) o;
        return this.name.equals(other.name)
                && this.description.equals(other.description)
                && this.coordinates.equals(other.coordinates)
                && this.techLevel == other.techLevel && this.visited
                == other.visited;
    }

    /**
     * Planet information
     *
     * @return Information about the planet.
     * */
    @Override
    public String toString() {
        return "Name: " + name + "\nDescription: " + description + ".\n"
                + "Technology Level: " + techLevel + "\nLocation: "
                + coordinates;
    }
}