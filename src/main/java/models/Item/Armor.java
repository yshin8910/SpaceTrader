package main.java.models.Item;

import main.java.controllers.GameController;
import main.java.models.GameData;
import main.java.models.Player;

/**
 * This class represents an armor item
 *
 * @author A'maya
 */
public class Armor extends Item implements IUsable, IUpgradable {
    private int protectionBoost;
    private ArmorType type;

    /**
     * Creates an armor object
     * @param type Type of armor to create
     */
    public Armor(ArmorType type) {
        super(type.getName(), type.getPrice(), ItemLevel.BRONZE);
        this.protectionBoost = type.getProtectionBoost();
        this.type = type;
        this.setImage(type.getImage());
    }

    /**
     * Creates an armor object with a specified price
     * @param type Type of armor to create
     * @param price Price of armor
     */
    public Armor(ArmorType type, int price) {
        super(type.getName(), price, ItemLevel.BRONZE);
        this.protectionBoost = type.getProtectionBoost();
        this.type = type;
        this.setImage(type.getImage());
    }

    /**
     * This getter returns the protection boost of the armor
     * @return the endurance boost of the armor
     */
    public int getProtectionBoost() {
        return protectionBoost;
    }

    /**
     * This setter sets the endurance boost of the armor, helper for upgradeItem
     * @param protectionBoost level of the armor
     */
    private void setProtectionBoost(int protectionBoost) {
        this.protectionBoost = protectionBoost;
    }

    /**
     * Increases the protection boost by a specified amount
     * @param amount Amount to increase by
     */
    public void increaseProtectionBoost(int amount) {
        this.protectionBoost += amount;
    }

    @Override
    public void useItem() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        player.setProtection(player.getProtection() + this.protectionBoost);
    }

    @Override
    public boolean upgradeItem() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        if (this.getLevel() == ItemLevel.BRONZE && (player.getCredits() >= 15)) {
            player.setCredits(player.getCredits() -  (15 * this.getQuantity()));
            this.setLevel(ItemLevel.SILVER);
            this.setProtectionBoost(this.getProtectionBoost() + 45);
            return true;
        } else if (this.getLevel() == ItemLevel.SILVER  && (player.getCredits() >= 50)) {
            player.setCredits(player.getCredits() -  (100 * this.getQuantity()));
            this.setLevel(ItemLevel.GOLD);
            this.setProtectionBoost(this.getProtectionBoost() + 100);
            return true;
        }
        return false;
    }

    @Override
    public Item clone() {
        return new Armor(type, getPrice());
    }
}
