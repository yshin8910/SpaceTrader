package main.java.models.NPC;

import main.java.controllers.GameController;
import main.java.models.Inventory;
import main.java.models.Ship;

import java.util.Random;

/**
 * Abstract NPC class for universe encounters.
 *
 * @author Isaac & Sam
 */
public abstract class NPC {
    protected String name;
    protected Inventory inventory;
    protected int credits;
    protected int health;
    protected String welcomeText;
    protected String deathText;
    protected String goodComplyText;
    protected String badComplyText;
    protected String fleeFailedText;
    protected String fleeSuccessfulText;
    protected String winText;
    protected String buyItemText;
    protected String robFailedText;
    protected String ignoreText;
    protected Ship ship;

    /**
     * Four-arg constructor for initializing name, health, inventory,
     * and credits.
     *
     * @param name NPC name
     * @param health NPC health
     * @param inventory NPC inventory
     * @param credits NPC credits
     */
    public NPC(String name, int health, Inventory inventory,
                int credits) {
        setName(name);
        setHealth(health);
        setCredits(credits);
        this.inventory = inventory;
        ship = new Ship("Falcon");
    }

    /**
     * Three-arg constructor for initializing a name, health, and
     * credits. Also creates an inventory with random items.
     * @param name NPC name
     * @param health NPC health
     * @param credits NPC credits
     */
    public NPC(String name, int health, int credits) {
        this(name, health, new Inventory(100), credits);
    }

    /**
     * Two-arg constructor which creates an NPC with a name and
     * given health.
     * Creates an inventory with random items and allocates a base
     * amount of credits to the NPC based on game difficulty.
     * @param name NPC name
     * @param health NPC health
     */
    public NPC(String name, int health) {
        this(name, health, new Random().nextInt(
                new Random().nextInt(GameController.getGameData().getPlayer().
                        getDifficulty().name().equals("EASY") ? 1001
                        : GameController.getGameData().getPlayer().getDifficulty().
                        name().equals("MEDIUM") ? 501
                        : 101)));
    }

    /**
     * One-arg constructor which creates an NPC with a given name.
     * Also initializes inventory with random items, and allocates
     * random health and credits based on game difficulty.
     *
     * @param name NPC name
     */
    public NPC(String name) {
        this(name,
                new Random().nextInt(GameController.getGameData().getPlayer().
                   getDifficulty().name().equals("EASY") ? 1
                   : GameController.getGameData().getPlayer().getDifficulty().
                   name().equals("MEDIUM") ? 401
                   : 901) + 100);
    }

    /**
     * Gets this NPCs name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets this NPCs health
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets this NPCs inventory
     *
     * @return inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets this NPCs credits
     *
     * @return credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Adds credits to the NPC
     * @param amount Amount to add
     */
    public void addCredits(int amount) {
        this.credits = amount + credits;
    }

    /**
     * Takes credits from the NPC
     *
     * @param amount Amount to take
     * @return new number of credits or -1 if the NPC doesn't have enough credits
     */
    public int takeCredits(int amount) {
        if (credits - amount < 0) {
            return -1;
        }
        credits = Math.max(credits - amount, 0);
        return credits;
    }

    /**
     * Sets this NPCs name
     *
     * @param name new name
     */
    protected void setName(String name) {
        this.name = (name != null) && !name.trim().equals("") ? name
                : "Unknown";
    }

    /**
     * Sets this NPCs health
     *
     * @param health new health
     */
    protected void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    /**
     * Sets this NPC's credits
     *
     * @param credits new credits
     */
    protected void setCredits(int credits) {
        this.credits = Math.max(credits, 0);
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public String getDeathText() {
        return deathText;
    }

    public String getGoodComplyText() {
        return goodComplyText;
    }

    public String getBadComplyText() {
        return badComplyText;
    }

    public String getFleeFailedText() {
        return fleeFailedText;
    }

    public String getFleeSuccessfulText() {
        return fleeSuccessfulText;
    }

    public String getWinText() {
        return winText;
    }

    public String getBuyItemText() {
        return buyItemText;
    }

    public String getRobFailedText() {
        return robFailedText;
    }

    public String getIgnoreText() {
        return ignoreText;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Bandit)) {
            return false;
        }
        NPC npc = (NPC) other;
        return name.equals(npc.name);
    }
}