package main.java.models;

import javafx.scene.image.Image;

/**
 * Enum for defining different ship types
 *
 */
public enum ShipType {
    /**
     * Default ship type
     */
    Default(10, 20, 100,
            new Image(ShipType.class.getResourceAsStream("/images/ship-default.png")));

    private final int cargoCapacity;
    private final int fuelCapacity;
    private final int healthCapacity;
    private final Image image;

    ShipType(int cargoCapacity, int fuelCapacity, int healthCapacity, Image image) {
        this.cargoCapacity = cargoCapacity;
        this.fuelCapacity = fuelCapacity;
        this.healthCapacity = healthCapacity;
        this.image = image;
    }

    /**
     * Gets the cargo capacity for the ship
     * @return Cargo capacity
     */
    public int getCargoCapacity() {
        return cargoCapacity;
    }

    /**
     * Gets the fuel capacity of the ship
     * @return The fuel capacity
     */
    public int getFuelCapacity() {
        return fuelCapacity;
    }

    /**
     * Gets the health capacity of the ship
     * @return health capacity
     */
    public int getHealthCapacity() {
        return healthCapacity;
    }

    /**
     * Gets the ships image
     * @return Image
     */
    public Image getImage() {
        return image;
    }
}
