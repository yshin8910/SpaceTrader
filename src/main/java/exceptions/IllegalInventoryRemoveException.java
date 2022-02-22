package main.java.exceptions;

/**
 * Exception for when an illegal inventory remove operation is performed
 */
public class IllegalInventoryRemoveException extends Exception {
    /**
     * Illegal inventory remove exception with default message
     */
    public IllegalInventoryRemoveException() {
        super("Cannot perform remove operation. You do not have enough of this item to remove.");
    }

    /**
     * Illegal inventory remove exception with specified message
     * @param message Message
     */
    public IllegalInventoryRemoveException(String message) {
        super(message);
    }
}
