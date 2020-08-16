package main.java.models.Item;

import main.java.controllers.GameController;
import main.java.models.GameData;
import main.java.models.Player;

/**
 * This class represents a Potion item
 *
 * @author A'maya
 */
public class Potion extends Item implements IUsable, IUpgradable {
    private int healthBoost;
    private PotionType type;

    /**
     * This constructor creates a potion
     * @param potionType Type of potion
     */
    public Potion(PotionType potionType) {
        super(potionType.getName(), potionType.getPrice(), ItemLevel.BRONZE);
        this.healthBoost = potionType.getHealthIncrease();
        this.type = potionType;
        this.setImage(type.getImage());
    }

    /**
     * This constructor creates a potion with a specified price
     * @param potionType Type of potion
     * @param price Price of potion
     */
    public Potion(PotionType potionType, int price) {
        super(potionType.getName(), price, ItemLevel.BRONZE);
        this.healthBoost = potionType.getHealthIncrease();
        this.type = potionType;
        this.setImage(type.getImage());
    }

    /**
     * This getter gets the increase in health of the potion
     * @return the increase in health
     */
    public int getHealthBoost() {
        return healthBoost;
    }

    /**
     * This setter sets the increase in health of the potion, helper for upgradeItem()
     * @param healthBoost increase in health of potion
     */
    private void setHealthBoost(int healthBoost) {
        this.healthBoost = healthBoost;
    }

    /**
     * Increases the health boost by a specified amount
     * @param amountToIncrease Amount to increase health boost by
     */
    public void increaseHealthBoost(int amountToIncrease) {
        this.healthBoost += amountToIncrease;
    }

    public PotionType getType() {
        return type;
    }

    @Override
    public void useItem() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        player.getShip().repair(this.healthBoost);
    }

    @Override
    public boolean upgradeItem() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        if (this.getLevel() == ItemLevel.BRONZE && (player.getCredits() >= 15)) {
            player.setCredits(player.getCredits() -  (15 * this.getQuantity()));
            this.setLevel(ItemLevel.SILVER);
            this.setHealthBoost(this.getHealthBoost() + 10);
            return true;
        } else if (this.getLevel() == ItemLevel.SILVER  && (player.getCredits() >= 50)) {
            player.setCredits(player.getCredits() -  (100 * this.getQuantity()));
            this.setLevel(ItemLevel.GOLD);
            this.setHealthBoost(this.getHealthBoost() + 30);
            return true;
        }
        return false;
    }

    @Override
    public Item clone() {
        return new Potion(type, getPrice());
    }
}
