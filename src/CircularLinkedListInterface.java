import java.util.Iterator;

/**
 * Requirements for CircularLinkedList class
 */
public interface CircularLinkedListInterface<E> {

    /**
     * Retrieves a count of elements being maintained by the list.
     *
     * @return the size of the list (count of elements)
     */
    public int size();

    /**
     * Retrieves the data at the specified position in the list
     *
     * @param position 0-based index for the list; must be in the range 0 to size - 1
     * @return the data in the specified position in the list
     */
    public E get(int position);

    /**
     * Adds a new node to the end of the list; by nature, this element's next will point to the first element
     *
     * @param value the element to add to the list
     */
    public void add(E value);

    /**
     * Removes the specified item from the list, if it exists there.
     *
     * @param value the element to removeValue from the list
     * @return true, if the element was found and removed; false, if not found or list is empty
     */
    public boolean remove(E value);

    /**
     * Removes the node at the specified position in the list
     * @param position position in the list; must be in range 0 to size - 1
     */
    public void remove(int position);

    /**
     * Retrieves an iterator over the list's elements.  Do not do other list operations like add or removeValue
     * from within an iterator loop; the results are not guaranteed to function as you might expect
     *
     * @return a strongly typed iterator over elements in the list
     */
    public Iterator<E> iterator();

}
