package main.java.models.Item;

import javafx.scene.image.Image;

public enum PotionType {
    TONIC_OF_BROKEN_LOCKS("Tonic of Broken Locks", 40, 10,
            new Image(Item.class.getResourceAsStream("/images/items/potion-blue.png"))),

    POTION_OF_FURY("Potion of Fury", 35, 9,
            new Image(Item.class.getResourceAsStream("/images/items/potion-green.png"))),

    FLASK_OF_THE_SPIRIT("Flask of the Spirit", 30, 8,
            new Image(Item.class.getResourceAsStream("/images/items/potion-lime.png"))),

    FLASK_OF_DAWN("Flask of Dawn", 40, 12,
            new Image(Item.class.getResourceAsStream("/images/items/potion-orange.png"))),

    ELIXIR_OF_LIES("Elixir of Lies", 45, 13,
            new Image(Item.class.getResourceAsStream("/images/items/potion-purple.png"))),

    VIAL_OF_BLOOD("Vial of Blood", 50, 15,
            new Image(Item.class.getResourceAsStream("/images/items/potion-red.png")));

    private String name;
    private int price;
    private int healthIncrease;
    private Image image;

    PotionType(String name, int price, int healthIncrease, Image image) {
        this.name = name;
        this.price = price;
        this.healthIncrease = healthIncrease;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getHealthIncrease() {
        return healthIncrease;
    }

    public Image getImage() {
        return image;
    }
}
