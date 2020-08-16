package main.java.models.Item;

import javafx.scene.image.Image;

public enum WeaponType {
    GOLDEN_SWORD("Golden Sword", 60, 10,
            new Image(Item.class.getResourceAsStream("/images/items/sword-gold.png"))),

    IRON_SWORD("Iron Sword", 80, 14,
            new Image(Item.class.getResourceAsStream("/images/items/sword-iron.png"))),

    FIRE_SWORD("Fire Sword", 100, 20,
            new Image(Item.class.getResourceAsStream("/images/items/sword-fire.png"))),

    GOLD_STAFF("Golden Staff", 50, 8,
            new Image(Item.class.getResourceAsStream("/images/items/staff-gold.png"))),

    BRONZE_STAFF("Bronze Staff", 65, 10,
            new Image(Item.class.getResourceAsStream("/images/items/staff-bronze.png"))),

    IRON_STAFF("Iron Staff", 75, 12,
            new Image(Item.class.getResourceAsStream("/images/items/staff-iron.png")));

    private String name;
    private int price;
    private int damage;
    private Image image;

    WeaponType(String name, int price, int damage, Image image) {
        this.name = name;
        this.price = price;
        this.damage = damage;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDamage() {
        return damage;
    }

    public Image getImage() {
        return image;
    }
}
