package main.java.models.Item;

/**
 * This enum provides the levels for items in the inventory
 *
 * @author A'maya
 */

public enum ItemLevel {
    BRONZE(0), SILVER(5), GOLD(15);

    private int priceIncrease;

    /**
     * item level
     * @param priceIncrease associated with the item
     */
    private ItemLevel(int priceIncrease) {
        this.priceIncrease = priceIncrease;
    }

    /**
     * This getter gets the price increase of the level
     * @return the price increase
     */
    public int getPriceIncrease() {
        return priceIncrease;
    }
}
