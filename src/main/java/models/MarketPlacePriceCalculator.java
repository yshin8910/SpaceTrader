package main.java.models;

import main.java.controllers.GameController;
import main.java.models.Item.Item;

public class MarketPlacePriceCalculator implements IPriceCalculator {
    @Override
    public int calculatePrice(Item item) {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();
        int planetTech = gameData.getCurrentPlanet().getTechLevel();
        int p1MerchantSkill = player.getMerchantPts();
        if (p1MerchantSkill > (item.getPrice()
                * item.getMinimumTechLevel() + planetTech)) {
            return item.getPrice();
        }
        int itemLevelPrice = item.getLevel().getPriceIncrease();
        int price = (item.getPrice() * item.getMinimumTechLevel()
                + planetTech) - p1MerchantSkill + itemLevelPrice;
        // now accounts for level of item
        return price;
    }
}
