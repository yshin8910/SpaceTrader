package main.java.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import main.java.datastructures.CircularlySinglyLinkedList.CircularSinglyLinkedList;
import main.java.datastructures.CircularlySinglyLinkedList.CircularSinglyLinkedListNode;
import main.java.exceptions.IllegalInventoryRemoveException;
import main.java.exceptions.InventoryFullException;
import main.java.models.Item.*;

import java.util.*;


/**
 * Inventory model for a ship
 *
 * @author Sam & Isaac
 */
public class Inventory implements Iterable<Item> {
    private int maxNumItems;
    private ObservableMap<Integer, Item> items;
    private int size;
    private static Random rand = new Random();
    private IPriceCalculator priceCalculator;

    /**
     * Creates a new inventory with 10 slots for items
     */
    public Inventory() {
        this(10);
    }

    /**
     * Creates a new inventory with a specified number of slots for items
     *
     * @param maxNumItems Max number of items allowed in inventory
     */
    public Inventory(int maxNumItems) {
        this.maxNumItems = maxNumItems;
        items = FXCollections.observableHashMap();
    }

    /**
     * Creates an inventory with a price calculator. This constructor is useful when creating
     * marketplaces with inventories as their primary point of sale.
     *
     * @param maxNumItems Max number of items allowed in inventory
     * @param priceCalculator Price calculator to be used in calculating the
     *                        price of an item
     */
    public Inventory(int maxNumItems, IPriceCalculator priceCalculator) {
        this(maxNumItems);
        this.priceCalculator = priceCalculator;
    }

    /**
     * Gets the items HashMap
     * @return Items HashMap
     */
    public ObservableMap<Integer, Item> getItems() {
        return items;
    }

    /**
     * Gets the items as a Collection instead of HashMap
     * @return items HashMap values
     */
    public Collection<Item> getItemsAsCollection() {
        return items.values();
    }

    /**
     * Adds an item to the inventory
     * @param item Item to add
     * @param quantity Amount of item to add
     * @throws InventoryFullException When the inventory is full before performing the add operation
     * @throws IllegalArgumentException When the item passed in is null or the quantity is 0
     */
    public void addItem(Item item, int quantity)
            throws IllegalArgumentException, InventoryFullException {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to inventory.");
        }
        if (quantity == 0) {
            throw new IllegalArgumentException("Amount cannot be 0 when adding to an inventory.");
        }
        if (quantity + size > maxNumItems || isFull()) {
            throw new InventoryFullException();
        }
        int itemHashCode = item.hashCode();
        if (items.containsKey(itemHashCode)) {
            items.get(itemHashCode).increaseQuantity(quantity);
        } else {
            Item newItem = item.clone();
            newItem.setQuantity(quantity);
            items.put(itemHashCode, newItem);
        }
        size += quantity;
    }

    /**
     * Takes all the items from another inventory.
     * @param otherInventory Other inventory to take items from
     * @throws InventoryFullException When the take all operation cannot
     *                                be performed due to overflow.
     */
    public void takeAllItems(Inventory otherInventory) throws InventoryFullException {
        if (otherInventory == null) {
            throw new IllegalArgumentException("Cannot take all items from a null inventory.");
        }
        if (size + otherInventory.size() > maxNumItems) {
            throw new InventoryFullException();
        }

        for (Item item : otherInventory) {
            addItem(item, item.getQuantity());
        }
        otherInventory.clear();
    }

    /**
     * Removes the specified amount of a certain item from the inventory
     * @param item Item to remove
     * @param amount Amount to remove
     * @throws IllegalInventoryRemoveException When remove operation cannot be performed
     * @throws IllegalArgumentException When the item passed in is null
     */
    public void removeItem(Item item, int amount)
            throws IllegalInventoryRemoveException, IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("Cannot remove null item from inventory.");
        }
        int itemHashCode = item.hashCode();
        if (!items.containsKey(itemHashCode)) {
            throw new IllegalInventoryRemoveException("Cannot remove an item the doesn't "
                    + "exist in the inventory.");
        }

        if ((items.get(itemHashCode).getQuantity() - amount) >= 1) {
            items.get(itemHashCode).decreaseQuantity(amount);
        } else {
            items.remove(itemHashCode);
        }
        decreaseSize(amount);
    }

    /**
     * Gets an item from the inventory.
     * @param item Item to get from the inventory.
     * @return Item
     */
    public Item getItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        return items.get(item.hashCode());
    }

    /**
     * Gets a random item in the inventory
     * @return Random item
     */
    public Item getRandomItem() {
        int randomIndex = rand.nextInt(items.values().size());
        return (Item) items.values().toArray()[randomIndex];
    }

    /**
     * Generates a specified number of items to be placed in the inventory
     *
     * If a price calculator has been specified then the method will use this to
     * calculate the price of the item.
     *
     * @param numItems Number of items to generate
     */
    public void generateItems(int numItems) {
        assert priceCalculator != null;
        // generate weapons
        WeaponType[] weaponTypes = WeaponType.values();
        Item[] weapons = new Weapon[weaponTypes.length];
        for (int i = 0; i < weaponTypes.length; i++) {
            weapons[i] = new Weapon(weaponTypes[i]);
            weapons[i].setPrice(priceCalculator.calculatePrice(weapons[i]));
        }

        // generate potions
        PotionType[] potionTypes = PotionType.values();
        Item[] potions = new Potion[potionTypes.length];
        for (int i = 0; i < potionTypes.length; i++) {
            potions[i] = new Potion(potionTypes[i]);
            potions[i].setPrice(priceCalculator.calculatePrice(potions[i]));
        }

        // generate armor
        ArmorType[] armorTypes = ArmorType.values();
        Item[] armor = new Armor[armorTypes.length];
        for (int i = 0; i < armorTypes.length; i++) {
            armor[i] = new Armor(armorTypes[i]);
            armor[i].setPrice(priceCalculator.calculatePrice(armor[i]));
        }

        // generate game winning item (maybe)
        GameWinner gameWinningItem = new GameWinner();
        gameWinningItem.setPrice(priceCalculator.calculatePrice(gameWinningItem));
        if (gameWinningItem.shouldGenerate()) {
            try {
                addItem(gameWinningItem, 1);
            } catch (InventoryFullException e) {
                System.out.println("Unable to add item to NPC inventory. Inventory is full.");
            }
        }

        // the circular list allows for a wide range of items to be generated
        // this will make adding items easier and less complex as this method will
        // have less reason to change unless a new item type is created
        CircularSinglyLinkedList<Item[]> itemArrays = new CircularSinglyLinkedList<>();
        itemArrays.addToFront(weapons);
        itemArrays.addToFront(potions);
        itemArrays.addToFront(armor);
        CircularSinglyLinkedListNode<Item[]> itemArraysHead = itemArrays.getHead();
        try {
            for (int i = 0; i < Math.min(numItems, maxNumItems); i++) {
                Item[] itemTypeArray = itemArraysHead.getData();
                Item randomItem = itemTypeArray[rand.nextInt(itemTypeArray.length)];
                addItem(randomItem, 1);
                itemArraysHead = itemArraysHead.getNext();
            }
        } catch (InventoryFullException e) {
            System.out.println("Unable to add item to NPC inventory. Inventory is full.");
        }
    }

    /**
     * Checks whether the item is in the inventory
     * @param item Item
     * @return True if inventory contains item false otherwise
     */
    public boolean containsItem(Item item) {
        return items.containsKey(item.hashCode());
    }

    /**
     *
     */
    public void clear() {
        items.clear();
        size = 0;
    }

    /**
     * Gets the size of the inventory
     * @return Size
     */
    public int size() {
        return size;
    }

    /**
     * Decreases the size
     * @param amount Amount to decrease size by
     */
    private void decreaseSize(int amount) {
        if (size - amount >= 0) {
            size -= amount;
        }
    }

    /**
     * Checks if the inventory is empty
     * @return true if inventory is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the inventory is full
     * @return true if inventory is full, false otherwise
     */
    public boolean isFull() {
        return size >= maxNumItems;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Inventory)) {
            return false;
        }

        Inventory otherInventory = (Inventory) obj;
        return otherInventory.getItems().equals(this.items);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        Collection<Item> itemValues = items.values();
        for (Item item : itemValues) {
            string.append(item);
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public Iterator<Item> iterator() {
        return items.values().iterator();
    }
}
