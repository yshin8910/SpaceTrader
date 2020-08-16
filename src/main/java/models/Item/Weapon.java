package main.java.models.Item;

import main.java.controllers.GameController;
import main.java.models.GameData;
import main.java.models.Player;

/**
 * This class represents a Weapon
 *
 * @author A'maya
 */

public class Weapon extends Item implements IUsable, IUpgradable {
    private int damage;
    private WeaponType type;

    /**
     * Creates a new weapon
     * @param type Weapon type
     */
    public Weapon(WeaponType type) {
        super(type.getName(), type.getPrice(), ItemLevel.BRONZE);
        this.damage = type.getDamage();
        this.type = type;
        this.setImage(type.getImage());
    }

    /**
     * Creates a new weapon with a specified price
     * @param type Weapon type
     * @param price Weapon price
     */
    public Weapon(WeaponType type, int price) {
        super(type.getName(), price, ItemLevel.BRONZE);
        this.damage = type.getDamage();
        this.type = type;
        this.setImage(type.getImage());
    }

    /**
     * This method gets the current damage of the weapon
     * @return the damage of the weapon
     */
    public int getDamage() {
        return damage;
    }

    /**
     * This is a helper method for the upgradeItem() method
     * @param newDamage new damage of the weapon
     */
    private void setDamage(int newDamage) {
        this.damage = newDamage;
    }

    /**
     * Increases the damage output of the weapon
     * @param amount Amount to increase by
     */
    public void increaseDamage(int amount) {
        this.damage += amount;
    }

    public WeaponType getType() {
        return type;
    }

    @Override
    public void useItem() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        player.setStrength(player.getStrength() + this.damage);
    }

    @Override
    public boolean upgradeItem() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        if (this.getLevel() == ItemLevel.BRONZE && (player.getCredits() >= 15)) {
            player.setCredits(player.getCredits() -  (15 * this.getQuantity()));
            this.setLevel(ItemLevel.SILVER);
            this.setDamage(this.getDamage() + 30);
            return true;
        } else if (this.getLevel() == ItemLevel.SILVER  && (player.getCredits() >= 50)) {
            player.setCredits(player.getCredits() -  (100 * this.getQuantity()));
            this.setLevel(ItemLevel.GOLD);
            this.setDamage(this.getDamage() + 75);
            return true;
        }
        return false;
    }

    @Override
    public Item clone() {
        return new Weapon(type, getPrice());
    }
}
