package skew_heap;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SkewHeapTests {
    SkewHeap tree = new SkewHeap();

    @Test
    void test_init() {
        assertNull(tree.root);
    }

    @Test
    void test_insert() {
        tree.insert(1);
        assertEquals(1, tree.root.data);
    }

    @Test
    void test_pop() {
        SkewHeap tree = new SkewHeap();
        tree.insert(5);
        tree.insert(3);
        tree.insert(10);
        assertEquals(tree.pop(), 3);
        assertEquals(tree.pop(), 5);
        assertEquals(tree.pop(), 10);
    }

    @Test
    void test_pop_empty_exception() {
        SkewHeap tree = new SkewHeap();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            tree.pop();
        });
    }

    @Test
    void test_list_to_heap() {
        List<Integer> ll = new ArrayList<>();
        ll.add(3);
        ll.add(2);
        ll.add(5);
        SkewHeap tree = null;
        tree = Functions.listToHeap(ll);
        assertEquals(tree.pop(), 2);
        assertEquals(tree.pop(), 3);
        assertEquals(tree.pop(), 5);
    }

    @Test
    void test_heap_to_list() {
        SkewHeap tree = null;
        tree.insert(4);
        tree.insert(3);
        tree.insert(5);
        List<Object> ll = Functions.heapToList(tree);
        assertEquals(ll.get(0), 3);
        assertEquals(ll.get(1), 4);
        assertEquals(ll.get(2), 5);
    }

    @Test
    void test_sorted(){
        List<Integer> ll = new ArrayList<>();
        ll.add(3);
        ll.add(2);
        ll.add(5);
        ll = Functions.sort(ll);
        assertEquals(ll.get(0), 2);
        assertEquals(ll.get(1), 3);
        assertEquals(ll.get(2), 5);
    }


}
