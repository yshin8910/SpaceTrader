package main.java.models;

import java.util.Random;

import main.java.controllers.GameController;
import main.java.models.NPC.IFleeable;

/**
 * This class represents a Player in the game.
 *
 */
public class Player {
    private static Random rand = new Random();
    private String name;
    private final Difficulty difficulty;
    private final int skillPoints;
    private int credits;
    private int engineerPts;
    private int pilotPts;
    private int fighterPts;
    private int merchantPts;
    private boolean alreadyExecuted; // this tracks if allocatePoints has been used
    private int totalPlanetsVisited;
    private int speed;
    private int protection;
    private int strength;
    private Ship ship;

    /**
     * This constructor creates a new player object.
     * @param name of player
     * @param difficulty that player chose
     */
    public Player(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.skillPoints = difficulty.getSkillPoints();
        this.credits = difficulty.getCredits();
        this.engineerPts = 0;
        this.pilotPts = 0;
        this.fighterPts = 0;
        this.merchantPts = 0;
        this.alreadyExecuted = false;
        this.totalPlanetsVisited = 0;
        this.speed = 10;
        this.protection = 5;
        this.strength = 5;
        this.ship = new Ship("Falcon");
    }

    /**
     * This method allows players to allocate their points to characters as long
     * as it doesn't exceed their skill points. It should only be used once
     * for each player!
     * @param engineerPts points allocated to engineer attribute
     * @param pilotPts points allocated to pilot attribute
     * @param fighterPts points allocated to fighter attribute
     * @param merchantPts points allocated to merchant attribute
     * @return true if the allocation was successful and false otherwise
     */
    public boolean allocatePoints(int engineerPts, int pilotPts, int fighterPts, int merchantPts) {
        int addedPoints = engineerPts + pilotPts + fighterPts + merchantPts;
        if (alreadyExecuted) {
            return false;
        } else if (addedPoints <= skillPoints) {
            this.engineerPts = engineerPts;
            this.pilotPts = pilotPts;
            this.fighterPts = fighterPts;
            this.merchantPts = merchantPts;
            alreadyExecuted = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method gets the name of the player.
     * @return the current name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * If the player choose to change their name later in the game.
     * @param name the player's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method gets the credits the player has.
     * @return the current number of credits the player has
     */
    public int getCredits() {
        return credits;
    }

    /**
     * The player's credits are reset when they purchase items.
     * @param credits Credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Takes credits from the player
     * @param amount Amount to take
     * @return new number of credits
     */
    public int takeCredits(int amount) {
        if (credits - amount < 0) {
            return -1;
        }
        this.credits = Math.max(credits - amount, 0);
        return credits;
    }

    /**
     * Adds credits to the player
     * @param amount Amount to add
     */
    public void addCredits(int amount) {
        this.credits = credits + amount;
    }

    /**
     * Determines if the player can flee
     * @param npc NPC to flee from
     * @return true if the player was able to flee, false otherwise.
     */
    public boolean fleeFrom(IFleeable npc) {
        Difficulty currentDifficulty = GameController.getGameData().getPlayer().getDifficulty();
        int pilotPointsDenominator = currentDifficulty == Difficulty.EASY ? 10
                : currentDifficulty == Difficulty.MEDIUM ? 5 : 1;
        int determineEscape = rand.nextInt(2 + (pilotPts / pilotPointsDenominator));

        boolean didFlee = determineEscape != 0;
        if (!didFlee) {
            npc.executeFailedFleeLogic();
        }
        return didFlee;
    }

    /**
     * This method gets the total number of skill points of the player.
     * @return number of skill points the player has in total
     */
    public int getSkillPoints() {
        return skillPoints;
    }

    /**
     * This method gets the level of difficulty of the player.
     * @return difficulty enum
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    // THE FOLLOWING GETTERS ARE FOR THE SKILL ATTRIBUTES (ENGINEER, PILOT, MERCHANT, FIGHTER) ONLY.
    /**
     * This method gets the total number of engineer points of the player.
     * @return number of engineer points the player has.
     */
    public int getEngineerPts() {
        return engineerPts;
    }

    /**
     * This method gets the total number of fighter points of the player.
     * @return number of fighter points the player has.
     */
    public int getFighterPts() {
        return fighterPts;
    }

    /**
     * This method gets the total number of merchant points of the player.
     * @return number of merchant points the player has.
     */
    public int getMerchantPts() {
        return merchantPts;
    }

    /**
     * This method gets the total number of pilot points of the player.
     * @return number of pilot points the player has.
     */
    public int getPilotPts() {
        return pilotPts;
    }

    /**
     * This is a getter for number of planets visited.
     * @return number visited
     */
    public int getTotalPlanetsVisited() {
        return totalPlanetsVisited;
    }

    /**
     * This is a setter for number of planets visited
     * @param newTotal of planets visited
     */
    public void setTotalPlanetsVisited(int newTotal) {
        totalPlanetsVisited = newTotal;
    }

    /**
     * This method gets the protection level of the player
     * @return protection of the player
     */
    public int getProtection() {
        return protection;
    }

    /**
     * This method sets the protection level of the player
     * @param protection of player
     */
    public void setProtection(int protection) {
        this.protection = protection;
    }

    /**
     * This method gets the speed of the player
     * @return speed of the player
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * This method sets the speed of the player
     * @param speed of player
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * This method sets the strength of the player
     * @param strength of player
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * This method gets the strength of the player
     * @return strength of the player
     */
    public int getStrength() {
        return strength;
    }

    /**
     * This method gets the ship of the player
     * @return the ship of the player
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * This method sets the ship of the player
     * @param ship new ship of the player
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
