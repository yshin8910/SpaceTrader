package main.java.models.NPC;

import main.java.controllers.GameController;
import main.java.models.Difficulty;
import main.java.models.GameData;
import main.java.models.Inventory;
import main.java.models.Item.*;
import main.java.models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * NPC Tests for ensuring proper functionality.
 *
 * @author Isaac
 * @version 1.0
 */
public class NPCTest {
    private Fighter[] f = new Fighter[6];
    private GameData g = new GameData();
    private Player p = new Player("", Difficulty.EASY);
    private Item staff;
    private Inventory i;

    @Before
    public void gameDataSetup() throws Exception {
        GameController.setGameData(g);
        g.setPlayer(p);
        staff = new Weapon(WeaponType.GOLD_STAFF);
        i = new Inventory();
        f[0] = new Fighter();
        f[1] = new Fighter("");
        f[2] = new Fighter(" ", -3);
        f[3] = new Fighter("name", 0, -10);
        f[4] = new Fighter("name", 3, 0);
        f[5] = new Fighter("Goku", 100, null, 100);
    }

    @Test
    public void gettersTest() {
        assertNotNull(f[0].getInventory());
        assertNotNull(f[0].getName());
        assertEquals("Unknown", f[0].getName());
        assertSame("Unknown", f[0].getName());
        assertFalse(f[0].getCredits() < 0);
        assertFalse(f[0].getHealth() < 0);

        assertNotNull(f[1].getInventory());
        assertNotNull(f[1].getName());
        assertEquals("Unknown", f[1].getName());
        assertSame("Unknown", f[1].getName());
        assertFalse(f[1].getCredits() < 0);
        assertFalse(f[1].getHealth() < 0);

        assertNotNull(f[2].getInventory());
        assertNotNull(f[2].getName());
        assertEquals("Unknown", f[2].getName());
        assertSame("Unknown", f[2].getName());
        assertFalse(f[2].getCredits() < 0);
        assertFalse(f[2].getHealth() < 0);
        assertEquals(0, f[2].getHealth());

        assertNotNull(f[3].getInventory());
        assertNotNull(f[3].getName());
        assertEquals("name", f[3].getName());
        assertSame("name", f[3].getName());
        assertFalse(f[3].getCredits() < 0);
        assertFalse(f[3].getHealth() < 0);
        assertEquals(0, f[3].getHealth());
        assertEquals(0, f[3].getCredits());

        assertNotNull(f[4].getInventory());
        assertNotNull(f[4].getName());
        assertEquals("name", f[4].getName());
        assertSame("name", f[4].getName());
        assertFalse(f[4].getCredits() < 0);
        assertFalse(f[4].getHealth() < 0);
        assertEquals(3, f[4].getHealth());
        assertEquals(0, f[4].getCredits());

        assertNotNull(f[5].getInventory());
        assertNotNull(f[5].getName());
        assertEquals("Goku", f[5].getName());
        assertSame("Goku", f[5].getName());
        assertFalse(f[5].getCredits() < 0);
        assertFalse(f[5].getHealth() < 0);
        assertEquals(100, f[5].getHealth());
        assertEquals(100, f[5].getCredits());
    }

    @Test
    public void settersTest() throws Exception {
        assertNotNull(f[0].getInventory());
        f[0].setName(null);
        assertNotNull(f[0].getName());
        assertEquals("Unknown", f[0].getName());
        assertSame("Unknown", f[0].getName());
        f[0].setCredits(-1);
        assertFalse(f[0].getCredits() < 0);
        assertEquals(0, f[0].getCredits());
        f[0].setCredits(1);
        assertEquals(1, f[0].getCredits());
        f[0].setHealth(-1);
        assertFalse(f[0].getHealth() < 0);
        assertEquals(0, f[0].getHealth());
        f[0].setHealth(1);
        assertEquals(1, f[0].getHealth());
        i.addItem(staff, 1);
        assertNotNull(f[0].getInventory());
        //No equals method for Inventory so comparing Items in
        // Inventories.
        assertEquals(i.getItem(staff),
                f[0].getInventory().getItem(staff));
        assertEquals(i.getItems(),
                f[0].getInventory().getItems());
        assertNotSame(i.getItem(staff),
                f[0].getInventory().getItem(staff));
        assertNotSame(i.getItems(),
                f[0].getInventory().getItems());
        assertNotSame(i, f[0].getInventory());
    }

    private class Fighter extends NPC {

        /**
         * Creates an Unknown Fighter with all random information.
         *
         * @throws Exception                When the inventory is full
         * @throws IllegalArgumentException When the item being added
         *                                  to the inventory is null.
         */
        private Fighter() throws Exception {
            super("Fighter");
        }


        /**
         * One-arg constructor which creates a fighter with a given
         * name. Also initializes inventory with random items, and
         * allocates random health and credits based on game
         * difficulty.
         *
         * @param name Fighter name
         * @throws Exception                When the inventory is full
         * @throws IllegalArgumentException When the item being added
         *                                  to the inventory is null.
         */
        private Fighter(String name) throws Exception {
            super(name);
        }

        /**
         * Two-arg constructor which creates a Fighter with a name and
         * given health.
         * Creates an inventory with random items and allocates a base
         * amount of credits to the Fighter based on game difficulty.
         *
         * @param name   Fighter name
         * @param health Fighter health
         * @throws Exception                When the inventory is full
         * @throws IllegalArgumentException When the item being added
         *                                  to the inventory is null.
         */
        private Fighter(String name, int health) throws Exception {
            super(name, health);
        }

        /**
         * Three-arg constructor for initializing a name, health, and
         * credits. Also creates an inventory with random items.
         *
         * @param name    Fighter name
         * @param health  Fighter health
         * @param credits Fighter credits
         * @throws Exception                When the inventory is full
         * @throws IllegalArgumentException When the item being added
         *                                  to the inventory is null.
         */
        private Fighter(String name, int health, int credits) throws Exception {
            super(name, health, credits);
        }

        /**
         * Four-arg constructor for initializing name, health, inventory,
         * and credits.
         *
         * @param name      Fighter name
         * @param health    Fighter health
         * @param inventory Fighter inventory
         * @param credits   Fighter credits
         * @throws Exception                When the inventory is full
         * @throws IllegalArgumentException when the item being added
         *                                  to the inventory is null.
         */
        private Fighter(String name, int health, Inventory inventory,
                       int credits) throws Exception {
            super(name, health, inventory, credits);
        }
    }
}
