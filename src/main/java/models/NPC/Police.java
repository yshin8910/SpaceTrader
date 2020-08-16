package main.java.models.NPC;

import main.java.controllers.GameController;
import main.java.models.Difficulty;
import main.java.models.Inventory;
import main.java.models.Item.Item;
import main.java.models.Player;

import java.util.Random;

/**
 * This class represents a Police Event
 * author: A'maya
 */
public class Police extends NPC implements IFleeable {
    private static Random rand = new Random();
    private Item suspectedStolenItem;
    private int fine;

    /**
     * Creates a Police Encounter event
     * @param name of policeman or policewoman
     */
    public Police(String name) {
        super(name);
        inventory = new Inventory(100);
        welcomeText = "Stop right there cosmic lawbreaker. You are violating the laws "
                + "of the universe.";
        deathText = "You win this time, but you WON'T get away with it.";
        goodComplyText = "Seems as though you aren't as dumb as I thought. Carry on "
                + "with your travels.";
        badComplyText = "I am warning you. You do as I say. Turn over the stolen items- OR ELSE! ";
        fleeFailedText = "How dare you disrespect the law of the space land? "
                + "I have no choice but to take the stolen items, damage your ship, and "
                + "cite you a fine.";
        fleeSuccessfulText = "You win this time cosmic lawbreaker. You will not get "
                + "away with this.";
        winText = "You should have given me your stolen items while you had the chance.";

        if (canEncounter(GameController.getGameData().getPlayer())) {
            suspectedStolenItem = GameController.getGameData().getPlayer()
                    .getShip().getInventory().getRandomItem();
            fine = rand.nextInt((300 - 100) + 1) + 50; // player will be this if they lose
            welcomeText = "Stop in the name of the law! I am Space Officer " + name + " , "
                    + "and I demand you release the " + suspectedStolenItem.getName()
                    + " you stole.";
        }
    }

    /**
     * This method determine if the police can encounter the player.
     * If the player has no items in his/her inventory, a police encounter can NOT happen
     * @param player that the police will encounter
     * @return true if player can encountered
     */
    private boolean canEncounter(Player player) {
        if (player.getShip().getInventory().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Initiates a fight with a player
     * @param player Player to fight
     * @return true if the bandit won, false otherwise
     */
    public boolean fight(Player player) {
        Difficulty currentDifficulty = GameController.getGameData().getPlayer().getDifficulty();
        int fighterPointsDenominator = currentDifficulty == Difficulty.EASY ? 10
                : currentDifficulty == Difficulty.MEDIUM ? 5 : 1;
        int determineFightWin = rand.nextInt(2
                + (player.getFighterPts() / fighterPointsDenominator));
        boolean didLose = determineFightWin > 0;
        int amountToTake = rand.nextInt((500 - 200) + 1) + 200;
        int amountToDamage = rand.nextInt((60 - 30) + 1) + 30;
        if (didLose) {
            player.addCredits(amountToTake);
            takeCredits(amountToTake);
        } else {
            addCredits(player.getCredits());
            player.takeCredits(player.getCredits());
            player.getShip().takeDamage(amountToDamage);
        }
        return !didLose;
    }

    /**
     *
     * @param player that the police encounters
     * @throws Exception if attempting to remove too much or a null item
     */
    public void forfeit(Player player) throws Exception {
        Item suspectedStolenItem = getSuspectedStolenItem();
        player.getShip().getInventory()
                .removeItem(suspectedStolenItem, suspectedStolenItem.getQuantity());
    }

    /**
     * Gets the suspected stolen item
     * @return suspected stolen item
     */
    public Item getSuspectedStolenItem() {
        return suspectedStolenItem;
    }

    /**
     * Gets the demanded fine
     * ONLY NEEDED IF THE PLAYER TRYS TO FLEE THE POLICE AND FAILS!
     * @return Demand credit amount
     */
    public int getFine() {
        return fine;
    }


    /**
     * Gets the credits of the Police
     * @return Credits
     */
    public int getCredits() {
        return super.getCredits();
    }

    /**
     * Sets the credits of the Police
     * @param credits Credits
     */
    public void setCredits(int credits) {
        super.setCredits(credits);
    }

    /**
     * Takes credits from the police
     * @param amount Amount to take
     * @return new number of credits
     */
    public int takeCredits(int amount) {
        return 0;
    }

    /**
     * Adds credits to the Police
     * @param amount Amount to add
     */
    public void addCredits(int amount) {
        super.setCredits(amount + super.getCredits());
    }

    @Override
    public void executeFailedFleeLogic() {
        int amountToDamage = rand.nextInt((20 - 10) + 1) + 10;
        Player player = GameController.getGameData().getPlayer();
        // taking fine from Player
        player.takeCredits(this.getFine());
        // taking items from Player
        Item suspectedStolenItem = this.getSuspectedStolenItem();
        try {
            player.getShip().getInventory()
                    .removeItem(suspectedStolenItem, suspectedStolenItem.getQuantity());
        } catch (Exception ignored) { }
        // doing damage to ship
        player.getShip().takeDamage(amountToDamage);
    }
}
