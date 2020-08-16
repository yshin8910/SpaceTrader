package main.java.models;

import javafx.scene.image.Image;

public enum PlanetType {
    Pesmeila("Pesmeila",
            "What was once a desolate wasteland is now a thriving floating city that uses"
            + " the energy from a nearby star to produce some of the galaxies most abundant"
            + " resources.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Lava2.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image1.png"))),

    Nestona("Nestona",
            "A super Earth at nearly double total volume, Nestona is part of an array of"
                    + " planets that produce mountains so high they go past the clouds. "
                    + "The floating city near the edge of Pangea continues to be one of "
                    + "the most technologically advanced societies that exists in Space Trader.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Forest.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image2.jpg"))),

    Gachax("Gachax",
            "What was once a mineral-rich planet is now an industrial society with towering"
                    + " structures reaching past the clouds. Gachax is a part of a binary system"
                    + " and its seasons are prolonged for almost an entire year on Earth.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Lava.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image3.jpeg"))),

    Zuthines("Zuthines",
                   "An industrial society with over 3 trillion people inhabited, "
                   + "Zuthines is a technologically driven society with skyscrapers towering "
                   + "into the sky. Zuthines has one moon orbiting itself which is almost "
                   + "half its size in diameter.",
                   new Image(PlanetType.class.getResourceAsStream("/images/planets/Barren2.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image4.jpg"))),

    Criystein("Criystein",
            "The planet Criystein, as it's named by the native population, is a "
                    + "rogue planet in a small solar system with seven other planets.\n"
                    + "Criystein is about 2.6 times bigger than Earth and its gravity is "
                    + "about 0.23 times that of Earth.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Desert2.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image5.jpg"))),

    Brosau("Brosau", "The planet Brosau, which is a name which is still "
                    + "disputed, is an ice planet in a thinly populated solar system "
                    + "with only seven other planets. Brosau is about 1.4 times bigger "
                    + "than Earth and its gravity is about 1.51 times that of Earth.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Ice.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image6.jpg"))),

    Ragu("Ragu",
            "The planet Ragu is an earth-like planet in a fairly large solar system"
                    + " with fifteen other planets. The planet is made up of 11 continents, "
                    + "which make up 64% of the planet's landmass. 5 moon(s) orbit the "
                    + "planet and Ragu itself orbits a white sun in a slightly elliptic "
                    + "orbit.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Ocean2.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image7.jpg"))),

    Qushoucor("Qushoucor",
            "The planet Qushoucor is a terrestial planet in a fairly large solar system"
                    + " with eighteen other planets. The plant-like organisms on this planet "
                    + "are mostly consist of large fungi and trees, with a few dozen flower "
                    + "species, but almost no grass species and bushes are non-existent.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Forest2.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image8.jpg"))),

    Enceladus("Enceladus",
            "The planet Enceladus, named so by its discoverer, is an ice giant "
                    + "planet in a vast solar system with thirteen other planets. A single "
                    + "day lasts 15.32 hours and a year lasts 272 days. The planet is "
                    + "made up of 3 continents, which make up 59% of the planet's landmass.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Terran.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image9.jpg"))),

    Mimas("Mimas",
            "The planet Mimas is an earth-like planet in a vast solar system "
                    + "with fourteen other planets. Although most of the water on this "
                    + "planet is fresh, the water plants did develop into what is perhaps "
                    + "more closely related to corals than to plants. These highly "
                    + "specialized species roughly resemble what we're used to in the "
                    + "oceans of Earth, but are only seen in fresh water on very rare "
                    + "occasions, which makes the species on this planet all the more "
                    + "intriguing",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Terran2.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image10.jpg"))),

    Eternium("Eternium",
            "The planet Skaro is an iron planet within a large solar system. "
                    + "Resources are pretty much the only thing this planet is good for. "
                    + "For any alien species advanced enough to mine other planets, this "
                    + "planet would be a perfect choice.",
            new Image(PlanetType.class.getResourceAsStream("/images/planets/Baren.png")),
            new Image(PlanetType.class
                    .getResourceAsStream("/images/planet-backgrounds/image15.jpg")));

    private String name;
    private String description;
    private Image planetImage;
    private Image backgroundImage;

    PlanetType(String name, String description, Image planetImage, Image backgroundImage) {
        this.name = name;
        this.description = description;
        this.planetImage = planetImage;
        this.backgroundImage = backgroundImage;
    }

    /**
     * Gets the planet name
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the planet image
     * @return Planet image
     */
    public Image getPlanetImage() {
        return planetImage;
    }

    /**
     * Gets the background image
     * @return Background image
     */
    public Image getBackgroundImage() {
        return backgroundImage;
    }
}
