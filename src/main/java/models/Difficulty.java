package main.java.models;

/**
 * This enum assigns skill points and credits to a character based off of their difficulty.
 *
 */
public enum Difficulty {
    // fields
    EASY(100, 1000), MEDIUM(50, 500), HARD(10, 100);

    private final int skillPoints;
    private final int credits;

    // constructor

    /**
     * Difficulty
     * @param skillPoints of the difficulty
     * @param credits of the difficulty
     */
    private Difficulty(int skillPoints, int credits) {
        this.skillPoints = skillPoints;
        this.credits = credits;
    }

    /**
     * This method gets the skill points that is associated with the character's difficulty.
     * @return number of skill points the player has
     */
    public int getSkillPoints() {
        return skillPoints;
    }

    /**
     * This method gets the credits that is associated with the character's difficulty.
     * @return number of credits the player has
     */
    public int getCredits() {
        return credits;
    }
}
