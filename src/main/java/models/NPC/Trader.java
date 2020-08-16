package main.java.models.NPC;

import main.java.exceptions.IllegalInventoryRemoveException;
import main.java.exceptions.InventoryFullException;
import main.java.controllers.GameController;
import main.java.models.*;
import main.java.models.Item.Item;

import java.util.Random;

/**
 * This class represents a Trader Event
 * @author Yeajin
 */
public class Trader extends NPC {
    private static Random rand = new Random();

    /**
     * Creates a Trader Encounter event
     * @param name of the trader
     */
    public Trader(String name) {
        super(name, 100, new Inventory(100, new TraderPriceCalculator()), 1000);
        welcomeText = "Hello, stranger. What do you say for some good stuff?";
        deathText = "It was a fluke. It won't be like this next time.";
        badComplyText = "Very unwise choice. You should've taken "
                + "the first offer!"; //negotiation failed
        goodComplyText = "You drive a hard bargain. I'll see what I "
                + "can do for you.";     // negotiation successful
        buyItemText = "Very good choice. That item will serve you well trader!";
        robFailedText = "It's a shame if you really thought you could compete with me.";
        ignoreText = "Until next time trader... safe travels.";
    }

    /**
     * Initiates a negotiation with a player
     * @param player Player to negotiate
     * @param item Item to negotiate
     * @return true if the negotiation was successful, false otherwise
     */
    public boolean negotiate(Player player, Item item) {
        int negotiationDenominator = 5;
        int determineNegotiation = rand.nextInt(2
                + (player.getMerchantPts() / negotiationDenominator));
        boolean wasSuccessful = determineNegotiation > 0;
        if (wasSuccessful) {
            item.setPrice(item.getPrice() / 3);
        } else {
            item.setPrice(item.getPrice() * 2);
        }
        return wasSuccessful;
    }

    /**
     * Initiates a rob event
     * @param player Player whos trying to rob trader
     * @throws InventoryFullException When inventory is full
     * @return true if the rob was successful, false otherwise
     */
    public boolean rob(Player player) throws InventoryFullException {
        int robPointsDenominator = 5;
        int determineRobSuccessful = rand.nextInt(2
                + (player.getFighterPts() / robPointsDenominator));
        boolean wasSuccessful = determineRobSuccessful > 0;
        if (wasSuccessful) {
            Item randomItemOne = inventory.getRandomItem();
            Item randomItemTwo = inventory.getRandomItem();
            player.getShip().getInventory().addItem(randomItemOne, 1);
            player.getShip().getInventory().addItem(randomItemTwo, 1);
        } else {
            addCredits(player.getCredits());
            player.takeCredits(player.getCredits());
            int amountToDamage = rand.nextInt((20 - 10) + 1) + 10;
            player.getShip().takeDamage(amountToDamage);
        }
        return wasSuccessful;
    }

    /**
     * Determines if the player can buy an item
     * @param item that the player wants to buy
     * @param quantity that the player wants to buy
     * @return true if the player can buy it, else false
     */
    public boolean buy(Item item, int quantity) {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        if (player.getCredits() >= item.getPrice()
                && item.getQuantity() >= quantity) {
            try {
                player.getShip().getInventory().addItem(item, quantity);
                inventory.removeItem(item, quantity);
                // decrementing credits
                player.setCredits(player.getCredits() - item.getPrice());
                return true;
            } catch (InventoryFullException | IllegalInventoryRemoveException e) {
                // inventory is already full
                return false;
            }
        }
        return false;
    }
}