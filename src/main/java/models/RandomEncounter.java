package main.java.models;

import main.java.controllers.GameController;

import java.util.Random;

/**
 * Class for generating a random encounter event.
 *
 * To populate the event attribute just instantiate a new RandomEncounter object.
 *
 * @author Sam
 */
public class RandomEncounter {
    private static Random rand = new Random();
    private Event event;

    /**
     * Generates a random event based on the current difficulty
     */
    public RandomEncounter() {
        Difficulty difficulty = GameController.getGameData().getPlayer().getDifficulty();
        switch (difficulty) {
        case EASY:
            generateEvent(100, 75, 75);
            break;
        case MEDIUM:
            generateEvent(100, 200, 200);
            break;
        case HARD:
            generateEvent(100, 500, 500);
            break;
        default:
            break;
        }
    }

    /**
     * Generate a fixed event from a parameter passed in.
     *
     * This will be useful in the demo when we want to demo a certain event and
     * have it not be pseudo-random.
     * @param event Event to generate
     */
    public RandomEncounter(Event event) {
        switch (event) {
        case TRADER:
            generateEvent(1, 0, 0);
            break;
        case BANDIT:
            generateEvent(0, 1, 0);
            break;
        case POLICE:
            generateEvent(0, 0, 1);
            break;
        default:
            break;
        }
    }

    /**
     * Generates an event based on the maximum allowed random number for each event type.
     * @param traderMax Maximum allowed random number for trader event
     * @param banditMax Maximum allowed random number for bandit event
     * @param policeMax Maximum allowed random number for police event
     */
    private void generateEvent(int traderMax, int banditMax, int policeMax) {
        int traderProbability = traderMax == 0 ? 0 : rand.nextInt(traderMax) + 1;
        int banditProbability = banditMax == 0 ? 0 : rand.nextInt(banditMax) + 1;
        int policeProbability = policeMax == 0 ? 0 : rand.nextInt(policeMax) + 1;
        int max = findMax(traderProbability, banditProbability, policeProbability);
        if (max == traderProbability) {
            event = Event.TRADER;
        } else if (max == banditProbability) {
            event = Event.BANDIT;
        } else {
            event = Event.POLICE;
        }
    }

    /**
     * Finds the max of three generated numbers
     * @param one First number
     * @param two Second number
     * @param three Third number
     * @return Max of the three
     */
    private int findMax(int one, int two, int three) {
        return Math.max(Math.max(one, two), three);
    }

    /**
     * Determines whether an encounter should occur.
     * @return true if the encounter should occur, false otherwise.
     */
    public boolean shouldEncounter() {
        int determinesEncounter = rand.nextInt(2); // can only be 0 or 1
        return determinesEncounter == 0;
    }

    /**
     * Gets the current event generated by creating a RandomEncounter
     * @return Event
     */
    public Event getEvent() {
        return event;
    }
}
