import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CircularLinkedListTest {

    private CircularLinkedList<String> test1;
    private CircularLinkedList<Integer> test2;
    private CircularLinkedList<Double> test3;

    @BeforeEach
    void setUp() {
        test1 = new CircularLinkedList<>();
        test2 = new CircularLinkedList<>(1, 3, 4, 6, 8, 9, 10);
        test3 = new CircularLinkedList<>(2.7);
    }

    @Test
    void size() {
        // checking size of each CircularLinkedList
        assertEquals(0, test1.size());
        assertEquals(7, test2.size());
        assertEquals(1, test3.size());
    }

    @Test
    void get() {
        // checking get with an index that doesn't exist in an empty CircularLinkedList
        assertThrows(IndexOutOfBoundsException.class, () -> test1.get(2));

        // checking get with an existing index in a CircularLinkedList
        assertEquals(6, test2.get(3));
        assertEquals(9, test2.get(5));

        // checking get with indexes that don't make sense in a CircularLinkedList
        assertThrows(IndexOutOfBoundsException.class, () -> test2.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> test2.get(20));

        // checking get with an index in a 1 element CircularLinkedList
        assertEquals(2.7, test3.get(0));

        // checking get with an index that doesn't exist in a 1 element CircularLinkedList
        assertThrows(IndexOutOfBoundsException.class, () -> test3.get(1));
    }

    @Test
    void add() {
        // adding 4 elements to an empty CircularLinkedList
        test1.add("hi");
        test1.add("bye");
        test1.add("moose");
        test1.add("blue");
        assertEquals(4, test1.size());
        assertEquals("bye", test1.get(1));
        assertEquals("blue", test1.get(3));
        assertThrows(IllegalArgumentException.class, () -> test1.add(null));

        // adding 1 element to a 7 element CircularLinkedList
        test2.add(17);
        assertEquals(8, test2.size());
        assertEquals(17, test2.get(7));
        assertThrows(IllegalArgumentException.class, () -> test2.add(null));

        // adding 1 element to a 1 element CircularLinkedList
        test3.add(3.14);
        assertEquals(2, test3.size());
        assertEquals(2.7, test3.get(0));
        assertEquals(3.14, test3.get(1));
        assertThrows(IllegalArgumentException.class, () -> test3.add(null));
    }

    @Test
    void removeValue() {
        // removing elements that don't exist in an empty CircularLinkedList
        assertEquals(false, test1.remove("red"));
        assertEquals(0, test1.size());
        assertThrows(IndexOutOfBoundsException.class, () -> test1.get(0));

        // removing elements that exist in a CircularLinkedList
        // intellij was confusing int value input and int position input (for removeValue with position parameter)
        // had to specify removeValue() takes Integer value
        assertEquals(true, test2.remove((Integer) 1));
        assertEquals(true, test2.remove((Integer) 9));
        assertEquals(true, test2.remove((Integer) 10));
        assertEquals(false, test2.remove((Integer) 13));
        assertEquals(4, test2.size());
        assertEquals(3, test2.get(0));
        assertEquals(8, test2.get(3));

        // removing an element that exists in a 1 element CircularLinkedList
        assertEquals(true, test3.remove(2.7));
        assertEquals(0, test3.size());
        assertThrows(IndexOutOfBoundsException.class, () -> test3.get(0));
    }

    @Test
    void removePosition() {
        // removing elements that don't exist in an empty CircularLinkedList
        assertThrows(IndexOutOfBoundsException.class, () -> test1.remove(0));
        assertEquals(0, test1.size());
        assertThrows(IndexOutOfBoundsException.class, () -> test1.get(3));

        // removing elements that exist in a CircularLinkedList
        test2.remove(0);
        test2.remove(3);
        test2.remove(4);
        assertEquals(4, test2.size());
        assertEquals(3, test2.get(0));
        assertEquals(9, test2.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> test2.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> test2.remove(100));

        // removing an element that exists in a 1 element CircularLinkedList
        test3.remove(0);
        assertEquals(0, test3.size());
        assertThrows(IndexOutOfBoundsException.class, () -> test3.get(0));
    }

    @Test
    void iterator() {
        // creating an iterator for an empty CircularLinkedList
        Iterator<String> test1Iterator = test1.iterator();
        assertNotNull(test1Iterator);
        assertEquals(false, test1Iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> test1Iterator.next());

        // testing for-each loop support on a CircularLinkedList
        int numCount = 0;
        for (int num : test2) {
            numCount++;

            if (numCount >= test2.size()) {
                break;
            }
        }
        // for-each loop should count the correct number of elements in a CircularLinkedList
        assertEquals(7, numCount);

        // creating an iterator for a CircularLinkedList with elements
        Iterator<Integer> test2Iterator = test2.iterator();
        assertNotNull(test2Iterator);
        assertEquals(true, test2Iterator.hasNext());
        assertEquals(1, test2Iterator.next());
        assertEquals(3, test2Iterator.next());

        // creating an iterator for a 1 element CircularLinkedList
        Iterator<Double> test3Iterator = test3.iterator();
        assertNotNull(test3Iterator);
        assertEquals(true, test3Iterator.hasNext());
        assertEquals(2.7, test3Iterator.next());
    }
}
