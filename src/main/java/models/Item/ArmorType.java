package main.java.models.Item;

import javafx.scene.image.Image;

public enum ArmorType {
    GOLDEN_HELMET("Golden Helmet", 20, 4,
            new Image(Item.class.getResourceAsStream("/images/items/helmet-gold.png"))),

    IRON_HELMET("Iron Helmet", 25, 6,
            new Image(Item.class.getResourceAsStream("/images/items/helmet-iron.png"))),

    DIAMOND_HELMET("Diamond Helmet", 30, 8,
            new Image(Item.class.getResourceAsStream("/images/items/helmet-diamond.png"))),

    GOLD_CHESTPLATE("Golden Chest Plate", 25, 6,
            new Image(Item.class.getResourceAsStream("/images/items/chest-gold.png"))),

    IRON_CHESTPLATE("Iron Chest Plate", 30, 8,
            new Image(Item.class.getResourceAsStream("/images/items/chest-iron.png"))),

    DIAMOND_CHESTPLATE("Diamond Chest Plate", 35, 10,
            new Image(Item.class.getResourceAsStream("/images/items/chest-diamond.png"))),

    GOLD_BOOTS("Gold Boots", 20, 4,
            new Image(Item.class.getResourceAsStream("/images/items/boots-gold.png"))),

    IRON_BOOTS("Iron Boots", 25, 6,
            new Image(Item.class.getResourceAsStream("/images/items/boots-iron.png"))),

    DIAMOND_BOOTS("Diamond Boots", 30, 8,
            new Image(Item.class.getResourceAsStream("/images/items/boots-diamond.png")));

    private String name;
    private int price;
    private int protectionBoost;
    private Image image;

    ArmorType(String name, int price, int protectionBoost, Image image) {
        this.name = name;
        this.price = price;
        this.protectionBoost = protectionBoost;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getProtectionBoost() {
        return protectionBoost;
    }

    public Image getImage() {
        return image;
    }
}
