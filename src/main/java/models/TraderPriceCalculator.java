package main.java.models;

import main.java.controllers.GameController;
import main.java.models.Item.Item;

public class TraderPriceCalculator implements IPriceCalculator {
    @Override
    public int calculatePrice(Item item) {
        GameData gameData = GameController.getGameData();
        int planetTech = gameData.getCurrentPlanet().getTechLevel();
        int itemLevelPrice = item.getLevel().getPriceIncrease();
        return (item.getPrice() * item.getMinimumTechLevel()
                + planetTech) + itemLevelPrice;
    }
}
