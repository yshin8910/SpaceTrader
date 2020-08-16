package main.java.models;

import main.java.controllers.GameController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import static org.junit.Assert.*;

@RunWith(RandomEncounterTest.class)
@Suite.SuiteClasses({ RandomEncounterTest.PseudoRandomEncounterTest.class,
        RandomEncounterTest.PoliceEventTest.class, RandomEncounterTest.BanditEventTest.class,
        RandomEncounterTest.TraderEventTest.class })
public class RandomEncounterTest extends Suite {
    public RandomEncounterTest(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class PseudoRandomEncounterTest {
        private RandomEncounter pseudoRandomEncounter;

        @Before
        public void setup() {
            Player player = new Player("Isaac", Difficulty.EASY);
            player.allocatePoints(10, 30, 40, 20);
            GameData dummyGameData = new GameData(player);
            GameController.setGameData(dummyGameData);
            pseudoRandomEncounter = new RandomEncounter();
        }

        @Test
        public void testEventCreation() {
            assertNotNull(pseudoRandomEncounter.getEvent());
        }
    }

    public static class PoliceEventTest {
        private RandomEncounter policeEvent;

        @Before
        public void setup() {
            Player player = new Player("Isaac", Difficulty.EASY);
            player.allocatePoints(10, 30, 40, 20);
            GameData dummyGameData = new GameData(player);
            GameController.setGameData(dummyGameData);
            policeEvent = new RandomEncounter(Event.POLICE);
        }

        @Test
        public void testPoliceEventCreation() {
            Event event = policeEvent.getEvent();
            assertNotNull(event);
        }
    }

    public static class BanditEventTest {
        private RandomEncounter banditEvent;

        @Before
        public void setup() {
            Player player = new Player("Isaac", Difficulty.EASY);
            player.allocatePoints(10, 30, 40, 20);
            GameData dummyGameData = new GameData(player);
            GameController.setGameData(dummyGameData);
            banditEvent = new RandomEncounter(Event.BANDIT);
        }

        @Test
        public void testBanditEventCreation() {
            Event event = banditEvent.getEvent();
            assertNotNull(event);
        }
    }

    public static class TraderEventTest {
        private RandomEncounter traderEvent;

        @Before
        public void setup() {
            Player player = new Player("Isaac", Difficulty.EASY);
            player.allocatePoints(10, 30, 40, 20);
            GameData dummyGameData = new GameData(player);
            GameController.setGameData(dummyGameData);
            traderEvent = new RandomEncounter(Event.TRADER);
        }

        @Test
        public void testTraderEventCreation() {
            Event event = traderEvent.getEvent();
            assertNotNull(event);
        }
    }
}
