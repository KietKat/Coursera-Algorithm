/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node tail;
    private Node head;
    private int size;

    public Deque() { // construct an empty deque
        size = 0;
    }

    // add the item to the front
    public void addFirst(Item item) { // return an iterator over items in order from front to back
        if (item == null) {
            throw new IllegalArgumentException("Null Input!");
        }
        else {
            Node newNode = new Node(item);
            if (this.size == 0) {
                this.tail = newNode;
                this.head = newNode;
            }
            else {
                head.prev = newNode;
                newNode.next = head;
                head = newNode;
            }

            this.size++;
        }
    }

    // add the item to the back
    public void addLast(Item item) { // return an iterator over items in order from front to back
        if (item == null) {
            throw new IllegalArgumentException("Null Input!");
        }
        else {
            Node newNode = new Node(item);
            if (this.size == 0) {
                this.tail = newNode;
                this.head = newNode;
            }
            else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }

            this.size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException("Empty queue!");
        }

        Item toRemove = head.item;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        else {
            tail = null;
        }
        this.size--;

        return toRemove;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException("Empty queue!");
        }

        Item toRemove = tail.item;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        }
        else {
            head = null;
        }
        this.size--;

        return toRemove;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node curr = head;

        public void remove() {
            throw new UnsupportedOperationException("Deprecated method.");
        }

        public boolean hasNext() {
            return curr.next != null;
        }

        public Item next() {
            if (curr == null) {
                throw new NoSuchElementException();
            }

            Item toReturn = curr.item;
            curr = curr.next;
            return toReturn;
        }
    }

    private class Node {
        Item item;
        Node next;
        Node prev;

        Node(Item item) {
            this.item = item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(0);
        deque.addFirst(3);
        deque.addFirst(7);
        deque.addLast(8);
        deque.addFirst(9);
        deque.addFirst(4);

        Iterator<Integer> iter = deque.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
        StdOut.print(iter.next());

        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();

        StdOut.println(deque.size());

        deque.removeLast();
        deque.removeLast();
        StdOut.println(deque.isEmpty());
    }
}
