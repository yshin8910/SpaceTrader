package main.java.models;

public class FuelTank {
    private double fuelAmount;
    private double fuelCapacity;

    public FuelTank(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
        this.fuelAmount = fuelCapacity;
    }

    /**
     * Gets the fuel amount
     * @return fuel amount
     */
    public double getFuelAmount() {
        return fuelAmount;
    }

    /**
     * Gets the fuel capacity
     * @return fuel capacity
     */
    public double getFuelCapacity() {
        return fuelCapacity;
    }

    /**
     * Adds fuel to tank.
     * @param amount amount of fuel to be added
     */
    public void addFuel(double amount) {
        if (fuelAmount + amount > fuelCapacity) {
            fuelAmount = fuelCapacity;
        } else {
            fuelAmount += amount;
        }
    }

    /**
     * Expend the amount of fuel necessary based on distance travelled
     *
     * @param distance The distance traveled
     */
    public void expendFuel(double distance) {
        if (fuelAmount - distance < 0) {
            fuelAmount = 0;
        } else {
            fuelAmount -= distance;
        }
    }

    /**
     * Restores tank fuel to maximum.
     */
    public void refuel() {
        fuelAmount = fuelCapacity;
    }

    /**
     * Checks if the tank is empty of fuel
     * @return true if tank is empty, false otherwise
     */
    public boolean isTankEmpty() {
        return fuelAmount == 0;
    }
}
