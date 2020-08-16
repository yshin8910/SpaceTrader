package main.java.models.NPC;

import main.java.controllers.GameController;
import main.java.exceptions.InventoryFullException;
import main.java.models.*;

import java.util.Random;

/**
 * Class for representing a Bandit in an encounter
 *
 * @author Sam
 */
public class Bandit extends NPC implements IFleeable {
    private static Random rand = new Random();
    private int demandCreditAmount;
    private String noItemsText;
    private String takeAllItemsText;

    public Bandit(String name) {
        super(name);
        welcomeText = "Stop right there scumbag. Forfeit your money or face me if you dare.";
        winText = "Should have given me your credits while you had the chance.";
        deathText = "Arg!! You win this time cosmic scum. I'll be back to face you once again.";
        goodComplyText = "Seems as though you aren't as dumb as I thought. "
                + "Carry on with your travels.";
        badComplyText = "Very unwise choice. Your skills are no match for mine scum.";
        fleeFailedText = "Trying to escape are we? Cowardly scum.";
        fleeSuccessfulText = "You win this time cosmic scum. Next time however, you "
                + "won't be so lucky!";
        noItemsText = "Looks like you don't have any items poor man. I will have to damage "
                + "your ship to make my time worth it.";
        takeAllItemsText = "Fool! You don't have enough credits! Your items are mine now!";
        demandCreditAmount = rand.nextInt((300 - 100) + 1) + 100;
    }

    public String getNoItemsText() {
        return noItemsText;
    }

    public String getTakeAllItemsText() {
        return takeAllItemsText;
    }

    /**
     * Gets the demanded credit amount
     * @return Demand credit amount
     */
    public int getDemandCreditAmount() {
        return demandCreditAmount;
    }

    /**
     * Demands money from the player
     * @param player Player to demand money from
     * @return Result
     */
    public BanditDemandResult demandMoney(Player player) {
        BanditDemandResult demandState;
        Ship playerShip = player.getShip();
        int amountToDamage = rand.nextInt((60 - 30) + 1) + 30;
        int newPlayerCredits = player.takeCredits(demandCreditAmount);
        if (newPlayerCredits == -1) {
            // player didn't have enough credits
            if (playerShip.getInventory().isEmpty()) {
                // player doesn't have any items
                playerShip.takeDamage(amountToDamage);
                demandState = BanditDemandResult.NO_ITEMS;
            } else {
                // take all items from players ship inventory
                demandState = BanditDemandResult.TAKE_ALL_ITEMS;
                Inventory playerShipInventory = playerShip.getInventory();
                try {
                    inventory.takeAllItems(playerShipInventory);
                } catch (InventoryFullException ignored) { }
                playerShipInventory.clear();
            }
        } else {
            demandState = BanditDemandResult.PAY;
        }
        return demandState;
    }

    /**
     * Initiates a fight with a player
     * @param player Player to fight
     * @return true if the bandit won, false otherwise
     */
    public boolean fight(Player player) {
        Difficulty currentDifficulty = GameController.getGameData().getPlayer().getDifficulty();
        /*
         * We want to increase our chances of winning based on the fighter points.
         *
         * If the player selected easy and has 100 fighter points allocated then
         * the fighter points will get divided by 10 and the result will be added to
         * the bounds which will give them a 92% chance of winning and a 9% chance
         * of losing.
         *
         * If the player has no fighter points allocated then the chances of them winning
         * or losing are both 50%.
         *
         * The same concept applies to the other difficulties.
         */
        int fighterPointsDenominator = currentDifficulty == Difficulty.EASY ? 10
                : currentDifficulty == Difficulty.MEDIUM ? 5 : 1;
        int determineFightWin = rand.nextInt(2
                + (player.getFighterPts() / fighterPointsDenominator));
        boolean didLose = determineFightWin > 0;
        int creditAmountToTake = rand.nextInt((300 - 100) + 1) + 100;
        int amountToDamage = rand.nextInt((60 - 30) + 1) + 30;
        if (didLose) {
            player.addCredits(creditAmountToTake);
            takeCredits(creditAmountToTake);
        } else {
            addCredits(player.getCredits());
            player.takeCredits(player.getCredits());
            player.getShip().takeDamage(amountToDamage);
        }
        return !didLose;
    }

    @Override
    public void executeFailedFleeLogic() {
        // give all of the players credits to the Bandit
        int amountToDamage = rand.nextInt((20 - 10) + 1) + 10;
        Player player = GameController.getGameData().getPlayer();
        this.addCredits(player.getCredits());
        player.setCredits(0);
        player.getShip().takeDamage(amountToDamage);
    }
}
