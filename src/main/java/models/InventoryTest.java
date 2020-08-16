package main.java.models;

import main.java.exceptions.IllegalInventoryRemoveException;
import main.java.exceptions.InventoryFullException;
import main.java.models.Item.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the inventory model on edge cases
 *
 * @author Sam
 */
public class InventoryTest {
    private Inventory inventory;
    private Item armorItem;
    private Item weaponItem;
    private Item potionItem;

    @Before
    public void setup() {
        inventory = new Inventory();
        armorItem = new Armor(ArmorType.GOLD_CHESTPLATE);
        weaponItem = new Weapon(WeaponType.GOLD_STAFF);
        potionItem = new Potion(PotionType.POTION_OF_FURY);
    }

    @Test
    public void testAddItem() throws Exception {
        inventory.addItem(armorItem, 1);
        inventory.addItem(weaponItem, 3);
        inventory.addItem(potionItem, 2);

        // Chest Plate - 1
        // Sword - 3
        // Healing Potion - 2

        assertEquals(1, inventory.getItem(armorItem).getQuantity());
        assertEquals(3, inventory.getItem(weaponItem).getQuantity());
        assertEquals(2, inventory.getItem(potionItem).getQuantity());
    }

    @Test(expected = InventoryFullException.class)
    public void testAddItemException() throws InventoryFullException {
        inventory.addItem(weaponItem, 5);
        inventory.addItem(potionItem, 5);

        assertNull(inventory.getItem(armorItem));
        assertEquals(weaponItem, inventory.getItem(weaponItem));
        assertEquals(potionItem, inventory.getItem(potionItem));
        assertEquals(5, inventory.getItem(weaponItem).getQuantity());
        assertEquals(5, inventory.getItem(potionItem).getQuantity());

        inventory.addItem(armorItem, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemNull() throws InventoryFullException {
        inventory.addItem(null, 1);
    }

    @Test
    public void testRemoveItem() throws InventoryFullException, IllegalInventoryRemoveException {
        inventory.addItem(weaponItem, 2);
        inventory.addItem(armorItem, 3);
        inventory.addItem(potionItem, 5);

        inventory.removeItem(weaponItem, 2);
        inventory.removeItem(armorItem, 1);
        inventory.removeItem(potionItem, 2);

        assertEquals(2, inventory.getItem(armorItem).getQuantity());
        assertEquals(3, inventory.getItem(potionItem).getQuantity());
        assertNull(inventory.getItem(weaponItem));
    }

    @Test(expected = IllegalInventoryRemoveException.class)
    public void testRemoveItemException() throws InventoryFullException,
            IllegalInventoryRemoveException {
        inventory.addItem(weaponItem, 5);
        inventory.addItem(armorItem, 2);
        inventory.addItem(potionItem, 3);

        inventory.removeItem(weaponItem, 4);
        inventory.removeItem(armorItem, 2);
        inventory.removeItem(potionItem, 3);

        assertEquals(1, inventory.size());
        assertEquals(weaponItem, inventory.getItem(weaponItem));
        assertEquals(1, inventory.getItem(weaponItem).getQuantity());

        inventory.removeItem(armorItem, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemNull() throws IllegalInventoryRemoveException {
        inventory.removeItem(null, 1);
    }

    @Test
    public void testIsEmpty() throws InventoryFullException, IllegalInventoryRemoveException {
        inventory.addItem(weaponItem, 5);
        inventory.addItem(armorItem, 2);
        inventory.addItem(potionItem, 3);

        inventory.removeItem(weaponItem, 5);
        inventory.removeItem(armorItem, 2);
        inventory.removeItem(potionItem, 3);

        assertTrue(inventory.isEmpty());
    }

    @Test
    public void testIsFull() throws InventoryFullException {
        inventory.addItem(weaponItem, 5);
        inventory.addItem(armorItem, 2);
        inventory.addItem(potionItem, 3);

        assertTrue(inventory.isFull());
    }

    @Test
    public void testTakeAllItems() throws InventoryFullException {
        Inventory otherInventory = new Inventory(50);
        otherInventory.addItem(armorItem, 10);
        inventory.addItem(armorItem, 5);

        otherInventory.takeAllItems(inventory);

        assertEquals(15, otherInventory.size());
    }

    @Test
    public void testInventorySwap() throws InventoryFullException, IllegalInventoryRemoveException {
        // this test is simulating buying and selling in the marketplace

        Inventory otherInventory = new Inventory(100);
        inventory.addItem(armorItem, 2);
        inventory.addItem(potionItem, 3);
        otherInventory.addItem(armorItem, 20);
        otherInventory.addItem(potionItem, 30);

        otherInventory.removeItem(armorItem, 5);
        inventory.addItem(armorItem, 5);

        assertEquals(7, inventory.getItem(armorItem).getQuantity());
        assertEquals(15, otherInventory.getItem(armorItem).getQuantity());
    }

    @Test
    public void testGenerateItems() {
        // this test is simulating buying and selling in the marketplace

        inventory.generateItems(10);
        assertEquals(10, inventory.size());
        assertNotNull(inventory.getItemsAsCollection().toArray()[0]);
    }
}
