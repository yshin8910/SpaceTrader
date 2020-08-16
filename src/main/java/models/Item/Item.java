package main.java.models.Item;

import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

/**
 * Item class used to trade items at planet markets.
 *
 * @author Isaac King and A'maya Solomon
 * @version 1.0
 *
 */
public abstract class Item implements Comparable<Item> {
    // making abstract so it cannot be instantiated by itself
    private String name;
    private int price;
    private int quantity;
    private int minimumTechLevel;
    private ItemLevel level;
    private Image image;
    private boolean hasBeenNegotiated;

    /**
     * Constructor for creating basic items.
     * @param name of the item
     */
    public Item(String name) {
        this(name, ItemLevel.BRONZE);
    }

    /**
     * Two-arg constructor for creating one item with a random price.
     *
     * @param name Item name
     * @param level level of item
     */
    public Item(String name, ItemLevel level) {
        this(name, (new Random().nextInt(51)), level);
    }

    /**
     * Three-arg constructor for creating item(s) with a specific
     * price and tech level.
     *
     * @param name Item name
     * @param price Item price
     * @param level level of item
     */
    public Item(String name, int price, ItemLevel level) {
        this(name, price,
                new Random().nextInt(10) + 1, level);
    }

    /**
     * Four-arg constructor for creating item(s) with a specific
     * price and tech level.
     *
     * @param name Item name
     * @param price Item price
     * @param minimumTechLevel Minimum tech level required for item
     * @param level level of item
     */
    public Item(String name, int price,
                int minimumTechLevel, ItemLevel level) {
        this.name = name + "";
        this.price = Math.abs(price);
        this.minimumTechLevel = Math.abs(minimumTechLevel);
        this.level = level;
    }

    /**
     * Gets the price of the item.
     *
     * @return Item price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Changes the item price.
     *
     * @param price New item price
     *
     */
    public void setPrice(int price) {
        this.price = Math.abs(price);
    }

    /**
     * Gets the name of the item.
     *
     * @return Item name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the item name.
     *
     * @param newName New item name
     */
    public void setName(String newName) {
        name = newName + "";
    }

    /**
     * Gets the item quantity.
     *
     * @return Item quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity
     * @param quantity Quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Increases the quantity of an item by an amount specified
     * @param increaseAmount Amount to increase by
     */
    public void increaseQuantity(int increaseAmount) {
        quantity += increaseAmount;
    }

    /**
     * Decreases the quantity be a specified amount. The quantity cannot
     * be decreased past 0.
     * @param decreaseAmount Amount to decrease by
     */
    public void decreaseQuantity(int decreaseAmount) {
        if (quantity - decreaseAmount > 0) {
            quantity -= decreaseAmount;
        }
    }

    /**
     * Gets the minimum tech level required to use the item.
     *
     * @return Minimum tech level required for item use
     */
    public int getMinimumTechLevel() {
        return minimumTechLevel;
    }

    /**
     * Gets whether or not has been negotiated before
     * @return true if it has been previously negotiated, false otherwise.
     */
    public boolean hasBeenNegotiated() {
        return hasBeenNegotiated;
    }

    /**
     * Sets whether or not an item has been negotiated before
     * @param hasBeenNegotiated Has the item been negotiated before
     */
    public void setHasBeenNegotiated(boolean hasBeenNegotiated) {
        this.hasBeenNegotiated = hasBeenNegotiated;
    }

    /**
     * Changes the minimum tech level.
     *
     * @param newMinimumTechLevel New minimum tech level
     */
    public void setMinimumTechLevel(int newMinimumTechLevel) {
        minimumTechLevel = Math.abs(newMinimumTechLevel);
    }

    /**
     * Sets the items image
     * @param image Image
     */
    protected void setImage(Image image) {
        this.image = image;
    }

    /**
     * Gets the items image
     * @return Image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Allows the item to be cloned
     * @return Deep copy of item
     */
    public abstract Item clone();

    /**
     * author: A'maya
     * This method returns the level of the item
     * @return the level of the item
     */
    public ItemLevel getLevel() {
        return level;
    }

    /**
     * author: A'maya
     * This method sets the level of the item
     * @param level of item
     */
    public void setLevel(ItemLevel level) {
        this.level = level;
    }

    @Override
    public int compareTo(Item otherItem) {
        return ((price * minimumTechLevel) - (otherItem.minimumTechLevel * otherItem.price));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) obj;
        return otherItem.getName().equals(this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", price: " + price + ", quantity: " + quantity
                + ", level: " + this.getLevel();
    }
}