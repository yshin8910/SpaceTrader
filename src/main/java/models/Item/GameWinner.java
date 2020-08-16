package main.java.models.Item;

import javafx.scene.image.Image;

import java.util.Random;

public class GameWinner extends Item {
    private static Random rand = new Random();

    public GameWinner() {
        super("Ancient Scrolls of Past Traders", 1200, ItemLevel.BRONZE);
        setImage(new Image(Item.class.getResourceAsStream("/images/items/ancient-scrolls.png")));
    }

    public boolean shouldGenerate() {
        int shouldGenerate = rand.nextInt(10); // 10% chance of generating the game winning item
        System.out.println("Generated game winning number: " + shouldGenerate);
        return shouldGenerate == 0;
    }

    @Override
    public Item clone() {
        return new GameWinner();
    }
}
