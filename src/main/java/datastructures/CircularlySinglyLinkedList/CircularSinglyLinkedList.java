package main.java.datastructures.CircularlySinglyLinkedList;

/**
 * Singular Circularly Linked List Mini-Data Structure Implementation
 *
 * @author Isaac
 */
public class CircularSinglyLinkedList<T> {
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data input is null.");
        } else if (head == null) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
        } else if (head.getNext() != null) {
            CircularSinglyLinkedListNode<T> next =
                    new CircularSinglyLinkedListNode<>(head.getData(),
                            head.getNext());
            head.setData(data);
            head.setNext(next);
        } else {
            CircularSinglyLinkedListNode<T> next =
                    new CircularSinglyLinkedListNode<T>(head.getData(), head);
            head.setNext(next);
            head.setData(data);
        }
        size++;
    }

    /**
     * Returns the head node of the list.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        return head;
    }
}