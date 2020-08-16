package main.java.datastructures.CircularlySinglyLinkedList;

/**
 * Node class used for implementing the CircularSinglyLinkedList.
 *
 * DO NOT ALTER THIS FILE!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class CircularSinglyLinkedListNode<T> {

    private T data;
    private CircularSinglyLinkedListNode<T> next;

    /**
     * Constructs a new CircularSinglyLinkedListNode with the given data and
     * next node reference.
     *
     * @param data the data stored in the new node
     * @param next the next node in the list
     */
    public CircularSinglyLinkedListNode(T data,
                                 CircularSinglyLinkedListNode<T> next) {
        this.data = data;
        this.next = next;
    }

    /**
     * Constructs a new CircularSinglyLinkedListNode with only the given data.
     *
     * @param data the data stored in the new node
     */
    public CircularSinglyLinkedListNode(T data) {
        this(data, null);
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Gets the next node.
     *
     * @return the next node
     */
    public CircularSinglyLinkedListNode<T> getNext() {
        return next;
    }

    /**
     * Sets the data.
     *
     * @param data the new data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Sets the next node.
     *
     * @param next the new next node
     */
    public void setNext(CircularSinglyLinkedListNode<T> next) {
        this.next = next;
    }


    @Override
    public String toString() {
        return "Node containing: " + data;
    }
}