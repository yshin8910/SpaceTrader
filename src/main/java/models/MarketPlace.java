package main.java.models;

import main.java.controllers.GameController;
import main.java.exceptions.IllegalInventoryRemoveException;
import main.java.exceptions.InventoryFullException;
import main.java.models.Item.*;

/**
 * This class creates a MarketPlace object in the game.
 *
 * @author A'maya
 */
public class MarketPlace {
    private Inventory inventory;

    /**
     * This class creates a marketplace for a Planet.
     */
    public MarketPlace() {
        this.inventory = new Inventory(100, new MarketPlacePriceCalculator());
    }

    /**
     * Determines if the player can buy an item
     * @param item that the player wants to buy
     * @param quantity that the player wants to buy
     * @return item that the player wants to buy
     */
    public boolean buy(Item item, int quantity) {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        int price = item.getPrice();
        if (player.getCredits() >= (price)
                && inventory.getItem(item).getQuantity() >= quantity) {
            try {
                player.getShip().getInventory().addItem(item, quantity);
                inventory.removeItem(item, quantity);
                // decrementing credits
                player.setCredits(player.getCredits() - price);
                return true;
            } catch (Exception e) {
                // inventory is already full
                return false;
            }
        }
        return false;
    }

    /**
     * Determines if the player can sell an item
     * @param item that the player wants to buy
     * @return true if the player can sell it, else false
     */
    public boolean sell(Item item) {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        Inventory shipInventory =
                player.getShip().getInventory();
        if (shipInventory.containsItem(item)
                && shipInventory.getItem(item).getQuantity() > 0) {
            try {
                player.getShip().getInventory().removeItem(item, 1);
                inventory.addItem(item, 1);
                // now accounts for level of item
                player.setCredits(player.getCredits() + (item.getPrice()
                        + item.getLevel().getPriceIncrease()));
                return true;
            } catch (IllegalInventoryRemoveException | InventoryFullException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Gets the market inventory
     * @return Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
}