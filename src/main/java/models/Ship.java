package main.java.models;

import javafx.collections.MapChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import main.java.SceneNavigator;
import main.java.controllers.GameController;
import main.java.models.Item.GameWinner;
import main.java.models.Item.Item;

public class Ship {
    private Inventory inventory;
    private String name;
    private int fuelCapacity;
    private int cargoCapacity;
    private FuelTank fuelTank;
    private int health;
    private int healthCapacity;
    private Image shipImage;
    private ShipType type;

    /**
     * @param name Ship name
     * @param fuelCapacity Ship fuelCapacity 1-10
     * @param cargoCapacity Ship cargoCapacity between 10-20 items
     * @param health Ship health
     * @param healthCapacity Ships health capacity
     * @param type Ship type
     * @param shipImage Image of the ship
     * */
    public Ship(String name, int fuelCapacity,
                int cargoCapacity, int health, int healthCapacity, ShipType type, Image shipImage) {
        this.inventory = new Inventory(cargoCapacity);
        this.name = name;
        this.fuelCapacity = fuelCapacity;
        this.cargoCapacity = cargoCapacity;
        this.health = health;
        this.healthCapacity = healthCapacity;
        this.type = type;
        this.shipImage = shipImage;
        this.fuelTank = new FuelTank(fuelCapacity);
        this.inventory.getItems().addListener((MapChangeListener<Integer, Item>) change -> {
            Item itemAdded = change.getValueAdded();
            if (itemAdded instanceof GameWinner) {
                SceneNavigator.navigateTo("gameWin");
            }
        });
    }

    /**
     * Creates a default ship with specified name
     * @param name Ship name
     */
    public Ship(String name) {
        this(name, ShipType.Default);
    }

    /**
     * Creates a ship with specified ship type
     * @param name Ship name
     * @param type Ship type
     */
    public Ship(String name, ShipType type) {
        this(name, type.getFuelCapacity(),
                type.getCargoCapacity(), type.getHealthCapacity(), type.getHealthCapacity(),
                type, type.getImage());
        this.type = type;
    }

    /**
     * Lets the player travel to another planet through the ship
     * @param planet Planet to travel to
     */
    public void travelTo(Planet planet) {
        if (fuelTank.isTankEmpty()) {
            Alert fuelEmptyAlert = new Alert(Alert.AlertType.ERROR);
            fuelEmptyAlert.setTitle("Out of Fuel");
            fuelEmptyAlert.setHeaderText("Your ship is out of fuel");
            fuelEmptyAlert.setContentText("You will need to buy more fuel at "
                    + "your planets marketplace before "
                    + "continuing your journey trader.");
            fuelEmptyAlert.showAndWait();
            return;
        }

        GameData gameData = GameController.getGameData();
        Planet previousPlanet = gameData.getCurrentPlanetLocation();
        gameData.setCurrentPlanetLocation(planet);
        planet.setVisited(true);
        int totalVisitedBefore = GameController.getGameData().getPlayer().getTotalPlanetsVisited();
        GameController.getGameData().getPlayer().setTotalPlanetsVisited(totalVisitedBefore + 1);
        double distance = 0;
        if (previousPlanet != null) {
            distance = previousPlanet.getCoordinates().distance(planet.getCoordinates());
        }
        System.out.println("Distance: " + distance);
        fuelTank.expendFuel(distance);
        SceneNavigator.navigateTo("planet");
    }

    /**
     * Gets the inventory.
     *
     * @return inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
    /**
     * sets the inventory of ship
     * @param inventory inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    /**
     * Gets the name of ship.
     *
     * @return Ship name
     */
    public String getName() {
        return name;
    }
    /**
     * sets the name of ship
     * @param name ship name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ships fuel tank
     * @return fuel tank
     */
    public FuelTank getFuelTank() {
        return fuelTank;
    }
    /**
     * Gets the health of ship.
     *
     * @return Ship health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the health capacity of the ship.
     * @return health capacity
     */
    public int getHealthCapacity() {
        return healthCapacity;
    }

    /**
     * Repairs the health of the ship
     * @param amount Amount to repair
     */
    public void repair(int amount) {
        this.health = Math.max(healthCapacity, health + amount);
    }

    /**
     * Lowers the ships health by a certain amount
     * @param damage Amount to damage
     */
    public void takeDamage(int damage) {
        this.health = Math.max(health - damage, 0);

        if (isDestroyed()) {
            SceneNavigator.navigateTo("gameOver");
        }
    }

    /**
     * Checks if the ship is destroyed.
     * @return true if it is destroyed and false otherwise
     */
    public boolean isDestroyed() {
        return health == 0;
    }

    /**
     * Gets the image of ship.
     *
     * @return Ship image
     */
    public Image getShipImage() {
        return shipImage;
    }

    /**
     * Gets the ship type
     * @return Ship type
     */
    public ShipType getType() {
        return type;
    }

    /**
     * returns the information of ship.
     *
     * @return Ship information
     */
    @Override
    public String toString() {
        return name + "\n FuelTank Capacity: " + fuelCapacity
               + "\n Cargo Capacity: " + cargoCapacity
               + "\n Health: " + health;
    }
}
