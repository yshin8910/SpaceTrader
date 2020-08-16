package main.java.exceptions;

/**
 * Exception for when an action on the inventory cannot be done because it is full
 * @author Sam
 */
public class InventoryFullException extends Exception {
    /**
     * Inventory full exception with default message
     */
    public InventoryFullException() {
        super("Cannot perform add operation. Not enough space in inventory.");
    }
}
