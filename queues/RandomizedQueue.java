/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] resizingArray;

    public RandomizedQueue() { // construct an empty randomized queue
        this.resizingArray = (Item[]) new Object[1];
        this.size = 0;
    }

    public void enqueue(Item item) { // add the item
        if (item == null) {
            throw new IllegalArgumentException("Null Argument!");
        }

        if (this.size == this.resizingArray.length) resize(2 * this.resizingArray.length);
        this.resizingArray[this.size++] = item;
    }

    public Item dequeue() { // remove and return a random item
        if (this.size == 0) { // empty array
            throw new NoSuchElementException("Empty RandomizedQueue!");
        }

        int randomIndex = StdRandom.uniformInt(this.size);
        Item toReturn = this.resizingArray[randomIndex];

        // Shift elements to the left starting from randomIndex+1
        for (int i = randomIndex + 1; i < this.size; i++) {
            this.resizingArray[i - 1] = this.resizingArray[i];
        }

        // Set the last element to null to avoid memory leak (if applicable)
        this.resizingArray[this.size - 1] = null;

        // 25% full or 100% full
        if (--this.size > 0 && this.size == this.resizingArray.length / 4) {
            this.resize(this.resizingArray.length / 2);
        }
        return toReturn;
    }

    public Item sample() { // return a random item (but do not remove it)
        if (this.size == 0) { // empty array
            throw new NoSuchElementException("Empty RandomizedQueue!");
        }

        int randomIndex = StdRandom.uniformInt(this.size);
        return this.resizingArray[randomIndex];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            copy[i] = this.resizingArray[i];
        }
        this.resizingArray = copy;
    }

    public boolean isEmpty() { // is the randomized queue empty?
        return this.size == 0;
    }

    public int size() { // return the number of items on the randomized queue
        return this.size;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] copy;
        private int n;

        public RandomizedQueueIterator() {
            this.copy = resizingArray.clone();
            n = size;
            shuffle();
        }

        public void shuffle() { // shuffle to get random sequence
            for (int i = 0; i < n; i++) {
                int r = StdRandom.uniformInt(i + 1);
                Item temp = this.copy[i];
                this.copy[i] = this.copy[r];
                this.copy[r] = temp;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Deprecated method.");
        }

        public boolean hasNext() {
            return n > 0;
        }

        public Item next() { // stack-pop like
            if (this.n == 0) {
                throw new NoSuchElementException("Empty");
            }

            Item toReturn = this.copy[--n];
            return toReturn;
        }
    }

    /* private class RandomizedQueueIterator implements Iterator<Item> {
        private Node curr = head;

        public void remove() {
            throw new UnsupportedOperationException("Deprecated method.");
        }

        public boolean hasNext() {
            return curr.next != null;
        }

        public Item next() {
            Item toReturn = curr.item;
            curr = curr.next;
            return toReturn;
        }
    }

   private class Node {
        Item items;
        Node next;

        Node(Item item) {
            this.item = item;
        }
    }*/

    public static void main(String[] args) {
        RandomizedQueue<Integer> rdqueue = new RandomizedQueue<>();

        // test enqueue, dequeue, size
        StdOut.println("Test enqueue");
        rdqueue.enqueue(1);
        rdqueue.enqueue(2);
        rdqueue.enqueue(3);
        rdqueue.enqueue(4);
        rdqueue.enqueue(5);
        rdqueue.enqueue(6);
        StdOut.println(rdqueue.size());

        StdOut.println("Test dequeue");
        StdOut.println(rdqueue.dequeue());
        StdOut.println(rdqueue.size());
        StdOut.println(rdqueue.dequeue());
        StdOut.println(rdqueue.size());

        StdOut.println("Test sample");
        StdOut.println(rdqueue.sample());
        StdOut.println(rdqueue.size());

        // test iterator
        StdOut.println("Test iterator");
        Iterator<Integer> iter = rdqueue.iterator();

        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }
}
