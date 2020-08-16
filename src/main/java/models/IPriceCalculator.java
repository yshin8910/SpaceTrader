package main.java.models;

import main.java.models.Item.Item;

public interface IPriceCalculator {
    default int calculatePrice(Item item) {
        return item.getPrice();
    }
}
