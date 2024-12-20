import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A LinkedList where the last node connects to the first node
 * @author Maya Rao
 * @version 10-22-24
 *
 * @param <E>   elements in the list
 */

public class CircularLinkedList<E> implements CircularLinkedListInterface<E>, Iterable<E> {
    // instance variables
    /** Number of elements in the list */
    private int size;

    /** Reference to the first node in the list */
    private Node<E> front;

    /** Reference to the last node in the list */
    private Node<E> end;

    // constructors
    /** Constructs a CircularLinkedList with no elements/nodes */
    public CircularLinkedList() {
        this.size = 0;

        // front and end will refer to null because there are no elements in the list
        front = null;
        end = null;
    }

    /**
     * Constructs a CircularLinkedList with a specified number of elements/nodes
     *
     * @param elements  a specified number of elements/nodes in the list
     */
    @SafeVarargs
    public CircularLinkedList(E... elements) {
        // create an empty CircularLinkedList where size = 0
        this();

        // add all the elements passed into the constructor to the CircularLinkedList
        for (int elementIndex = 0; elementIndex < elements.length; elementIndex++) {
            add(elements[elementIndex]);
        }
    }

    // methods
    /**
     * Retrieves a count of elements being maintained by the list.
     *
     * @return the size of the list (count of elements)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Retrieves the data at the specified position in the list
     *
     * @param position 0-based index for the list; must be in the range 0 to size - 1
     * @return the data in the specified position in the list
     */
    @Override
    public E get(int position) {
        // check if the position is between 0 and size - 1
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("position must be between 0 and size - 1");
        }

        // make a reference that tracks where it currently is in the list, start current at the front
        Node<E> current = front;

        // from 0 to position, move current to the next node in the list
        for (int index = 0; index < position; index++) {
            current = current.next;
        }

        // return the data of current at the correct index
        return current.data;
    }

    /**
     * Adds a new node to the end of the list; by nature, this element's next will point to the first element
     *
     * @param value the element to add to the list
     */
    @Override
    public void add(E value) {
        // check if the value is null
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }

        // storing value to be added
        Node<E> newNode = new Node<>(value);

        // if the list is empty
        if (size == 0) {
            // make the front and end refer to the same newNode
            front = newNode;
            end = newNode;

            // ensures that newNode points to itself (front = newNode)
            newNode.next = front;
        }
        // adding newNode to the end of the list
        else {
            // make the last node point to newNode
            end.next = newNode;

            // make the last node (end) refer to newNode
            end = newNode;

            // make newNode point back to front to maintain a circular list
            end.next = front;
        }

        // increase size of the list by 1
        size++;
    }

    /**
     * Removes the specified item from the list, if it exists there.
     *
     * @param value the element to removeValue from the list
     * @return true, if the element was found and removed; false, if not found or list is empty
     */
    @Override
    public boolean remove(E value) {
        // if the list is empty, the element can't be found or removed
        if (size == 0) {
            // return false because list is empty
            return false;
        }

        // make pointers for where we are in the list
        Node<E> current = front;
        Node<E> previous = end;

        // the element has been found at the front of the list
        if (current.data.equals(value)) {
            // if the list only has 1 element
            if (size == 1) {
                // make all nodes null
                front = null;
                end = null;
            }
            // the list has more than 1 element
            else {
                // make front refer to the next node in the list
                front = front.next;

                // make end point to front to maintain a circular list
                end.next = front;
            }

            // decrease size by 1
            size--;

            // return true because element has been found and removed
            return true;
        }

        // update the pointers to move further along the list
        current = current.next;
        previous = front;

        // keep going through the list until you reach the front again
        while (current != front) {
            // the element has been found
            if (current.data.equals(value)) {
                // bypass the current node (the one that will be removed)
                previous.next = current.next;

                // if the node to removeValue is the end
                if (current == end) {
                    // update the end reference to the previous node
                    end = previous;
                }

                // decrease size by 1
                size--;

                // return true because element has been found
                return true;
            }
            // move pointers further along the list
            previous = current;
            current = current.next;
        }

        // return false because element has not been found and removed
        return false;
    }

    /**
     * Removes the node at the specified position in the list
     *
     * @param position position in the list; must be in range 0 to size - 1
     */
    @Override
    public void remove(int position) {
        // check if the position is between 0 and size - 1
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("position must be between 0 and size - 1");
        }

        // make pointers for where we are in the list
        Node<E> current = front;
        Node<E> previous = end;

        // the element has been found at the front of the list
        if (position == 0) {
            // if the list only has 1 element
            if (size == 1) {
                // make all nodes null
                front = null;
                end = null;
            }
            // the list has more than 1 element
            else {
                // make front refer to the next node in the list
                front = front.next;

                // make end point to front to maintain a circular list
                end.next = front;
            }
        }

        // traversing to find element/node in the middle of the list
        int currentIndex = 0;
        while (currentIndex < position) {
            // move pointers further along the list
            previous = current;
            current = current.next;

            // increment currentIndex
            currentIndex++;
        }

        // bypass current node (node to be removed)
        previous.next = current.next;

        // if node to removeValue is at the end
        if (current == end) {
            // update the end reference to the previous node
            end = previous;
        }

        // decrease size by 1
        size--;
    }

    /**
     * Retrieves an iterator over the list's elements.  Do not do other list operations like add or removeValue
     * from within an iterator loop; the results are not guaranteed to function as you might expect
     *
     * @return a strongly typed iterator over elements in the list
     */
    @Override
    public Iterator<E> iterator() {
        return new CircularLinkedListIterator();
    }


    /**
     * The Nodes that make up a CircularLinkedList
     *
     * @param <T>   data stored in the Node
     */
    private static class Node<T> {
        // instance variables
        /** Data of a generic type T stored inside Node */
        public T data;

        /** Reference to the next Node in the list */
        public Node<T> next;

        // constructor
        /**
         * Constructs a Node that has data and refers to itself for the next Node (to maintain a circular list)
         *
         * @param data  data of a generic type T stored inside Node
         */
        public Node(T data) {
            this.data = data;

            // have the node refer to itself to make the list circular
            next = this;
        }
    }


    /** A way to iterate over CircularLinkedLists */
    private class CircularLinkedListIterator implements Iterator<E> {
        // instance variables
        /** Reference to the current node the iterator is looking at */
        private Node<E> current;

        /** Reference to the node before current */
        private Node<E> previous;

        // constructor
        /** Constructs a CircularLinkedListIterator that tracks current and previous nodes and if removal is allowed */
        public CircularLinkedListIterator() {
            current = front;
            previous = end;
        }

        // methods
        /**
         * Checks if the CircularLinkedList has more elements/nodes to go through
         * Since the list is circular, there will always be a next element/node (assuming the list isn't empty)
         *
         * @return  true if there are more elements/nodes in the list; false if not
         */
        @Override
        public boolean hasNext() {
            return size != 0;
        }

        /**
         * Returns the next element in the iteration
         *
         * @return  data of the next element/node in the iteration
         */
        @Override
        public E next() {
           if (!hasNext()) {
               throw new NoSuchElementException();
           }

           // collect the data of the current node
           E result = current.data;

           // make previous refer to the current node
           previous = current;

           // make current refer to the node after current
           current = current.next;

           // return data
           return result;
        }
    }
}
