package main.java.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Universe class
 *
 */

public class Universe {
    private ArrayList<Planet> planets;

    /**
     * Creates a universe with pre-defined planets
     * @param planets Planets
     */
    public Universe(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    /**
     * Creates an empty universe
     */
    public Universe() {
        this(new ArrayList<>());
    }

    /**
     * Generate planet-backgrounds
     *
     */
    public void generatePlanets() {
        PlanetType[] planetTypes = PlanetType.values();
        List<PlanetType> planetTypesList = Arrays.asList(planetTypes);
        Collections.shuffle(planetTypesList); // shuffle the planets
        for (PlanetType planetType : planetTypesList) {
            Planet planet = new Planet(planetType.getName(), planetType.getDescription(),
                    planetType.getPlanetImage(), planetType.getBackgroundImage());
            planets.add(planet);
        }
    }

    /**
     * Getter for Universe
     *
     * @return planets
     */
    public ArrayList<Planet> getPlanets() {
        return planets;
    }
}
