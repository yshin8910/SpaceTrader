package main.java.models.NPC;

import main.java.controllers.GameController;
import main.java.exceptions.InventoryFullException;
import main.java.models.*;
import main.java.models.Item.Item;
import main.java.models.Item.Weapon;
import main.java.models.Item.WeaponType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BanditTest {
    private Bandit bandit;
    private Player player;
    private static final boolean DEBUG = false;

    @Before
    public void setup() {
        player = new Player("Isaac", Difficulty.EASY);
        player.allocatePoints(10, 30, 40, 20);
        GameData dummyGameData = new GameData(player);
        GameController.setGameData(dummyGameData);
        bandit = new Bandit("Bob");
    }

    @Test
    public void testEncounter() {
        if (DEBUG) {
            System.out.println("Bandit demand amount:");
            System.out.println(bandit.getDemandCreditAmount());
        }
        assertTrue(bandit.getDemandCreditAmount() >= 100 && bandit.getDemandCreditAmount() <= 300);
    }

    @Test
    public void testDemandMoneyEnoughCredits() {
        player.setCredits(500);
        bandit.demandMoney(player);

        if (DEBUG) {
            System.out.println("Player credits:");
            System.out.println(player.getCredits());
        }

        assertTrue(player.getCredits() < 500);
    }

    @Test
    public void testDemandMoneyNotEnoughCreditsEmptyInventory() {
        player.setCredits(50);
        bandit.demandMoney(player);
        // damaged ship

        if (DEBUG) {
            System.out.println("Player inventory is empty:");
            System.out.println(player.getShip().getInventory().isEmpty());
            System.out.println("Player ship health:");
            System.out.println(player.getShip().getHealth());
        }

        assertTrue(player.getShip().getHealth() < 100);
    }

    @Test
    public void testDemandMoneyNotEnoughCreditsFilledInventory() {
        Inventory playerShipInventory = player.getShip().getInventory();
        player.setCredits(50);
        Item weaponToAdd = new Weapon(WeaponType.FIRE_SWORD);

        if (DEBUG) {
            System.out.println("Item to add:");
            System.out.println(weaponToAdd);
        }

        try {
            playerShipInventory.addItem(weaponToAdd, 4);
        } catch (InventoryFullException ignored) { }
        bandit.demandMoney(player);
        // bandit takes all of players items
        Inventory banditInventory = bandit.inventory;
        Item banditWeapon = banditInventory.getItem(weaponToAdd);

        if (DEBUG) {
            System.out.println("Bandit inventory:");
            System.out.println(banditInventory);
            System.out.println("Bandit first item from inventory:");
            System.out.println(banditWeapon);
        }

        assertTrue(player.getShip().getInventory().isEmpty());
        assertEquals(weaponToAdd, banditWeapon);
    }

    @Test
    public void testFight() {
        player.setCredits(200);
        bandit.setCredits(200);
        boolean banditWon = bandit.fight(player);

        if (DEBUG) {
            System.out.println("Bandit won:");
            System.out.println(banditWon);
            System.out.println("Bandit credits:");
            System.out.println(bandit.getCredits());
            System.out.println("Player credits:");
            System.out.println(player.getCredits());
            System.out.println("Player ship health:");
            System.out.println(player.getShip().getHealth());
        }

        if (banditWon) {
            assertEquals(0, player.getCredits());
            assertTrue(player.getShip().getHealth() < 100);
        } else {
            assertTrue(player.getCredits() > 200);
            assertTrue(bandit.getCredits() < 200);
        }
    }

    @Test
    public void testFlee() throws Exception {
        player.setCredits(200);
        bandit.setCredits(200);
        boolean didFlee = player.fleeFrom(bandit);

        if (DEBUG) {
            System.out.println("Player was able to flee:");
            System.out.println(didFlee);
            System.out.println("Bandit credits:");
            System.out.println(bandit.getCredits());
            System.out.println("Player credits:");
            System.out.println(player.getCredits());
            System.out.println("Player ship health:");
            System.out.println(player.getShip().getHealth());
        }

        if (!didFlee) {
            // player was not able to flee
            assertEquals(0, player.getCredits());
            assertEquals(400, bandit.getCredits());
            assertTrue(player.getShip().getHealth() < 100);
        } else {
            // player was able to flee, nothing should happen
            assertEquals(200, player.getCredits());
            assertEquals(200, bandit.getCredits());
            assertEquals(100, player.getShip().getHealth());
        }

    }
}
