package skew_heap;
import org.junit.jupiter.api.Test;


/*import java.sql.Time;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;*/

import static org.junit.jupiter.api.Assertions.*;

public class SkewHeapTests {
    private SkewHeap sk_heap = new SkewHeap(null, null, null);

    @Test
    void test_init_null() {
        assertNotNull(sk_heap);
        assertTrue(Functions.isEmpty(sk_heap));

        assertNull(sk_heap.leftChild);
        assertNull(sk_heap.rightChild);
        assertNull(sk_heap.data);
    }

    @Test
    void test_merge_both_null() {
        assertTrue(Functions.isEmpty(Functions.merge(sk_heap, sk_heap)));
    }

    @Test
    void test_insert_to_empty() {
        Functions op = new Functions();
        SkewHeap tree1 = op.insert("A", null);
        assertEquals(tree1.data, "A");
    }

    @Test
    void test_insert_to_non_empty() {
        Functions op = new Functions();
        SkewHeap tree1 = op.insert(1, null);
        tree1 = op.insert(2, tree1);
        assertEquals(tree1.data, 1);
    }
}
