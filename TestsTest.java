package skew_heap;
import org.junit.jupiter.api.Test;


import java.sql.Time;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class TestsTest {

    private AVLTreeMap<Integer, String> tree = new AVLTreeMap<>();

    @Test
    void test_AVL_constructor() {
        assertEquals(tree.length(), 0);
        assertEquals(tree.height(), 0);
        assertTrue(tree.isEmpty());
    }

    @Test
    void test_get_empty() {
        assertEquals(tree.get(1), null);
    }

    @Test
    void test_put() {
        assertTrue(tree.isBalanced());
        assertTrue( tree.isEmpty());
        tree.put(1, "A");
        assertTrue(tree.isBalanced());
        tree.put(2, "D");
        assertTrue(tree.isBalanced());
        tree.put(-1, "B");
        assertTrue(tree.isBalanced());
        tree.put(-2, "C");
        assertTrue(tree.isBalanced());
        tree.put(-5, "Z");
        tree.put(-6, "Z");
        tree.put(-7, "Z");
        tree.put(-8, "Z");
        tree.put(-9, "Z");
        tree.put(-11, "Z");
        assertTrue(tree.isBalanced());
        //tree.printTree();
        assertEquals(tree.height(), 4);
        assertEquals(tree.length(), 10);
        assertTrue(! tree.isEmpty());
    }

    @Test
    void test_get() {
        assertTrue(tree.isEmpty());
        assertTrue(tree.isBalanced());
        tree.put(10, "AA");
        assertTrue(tree.isBalanced());
        tree.put(7, "BB");
        assertTrue(tree.isBalanced());
        tree.put(6, "DD");
        assertTrue(tree.isBalanced());
        tree.put(13, "CC");
        assertTrue(tree.isBalanced());
        tree.put(14, "FF");
        tree.put(8, "EE");
        tree.put(12, "GG");
        tree.put(12, "GG");
        tree.put(12, "GG");
        tree.put(12, "GG");
        tree.put(12, "GG");
        tree.put(12, "GG");
        tree.put(12, "GG");
        tree.put(12, "GG");
        tree.put(12, "GG");
        tree.put(12, "GG");
        assertTrue(tree.isBalanced());
        tree.put(70, "ZZ");
        assertTrue(tree.isBalanced());
        //tree.printTree();
        assertEquals(tree.length(), 8);
        assertEquals(tree.height(), 4);
        assertEquals(tree.get(10), "AA");
        assertEquals(tree.get(7), "BB");
        assertEquals(tree.get(8), "EE");
        assertEquals(tree.get(14), "FF");
        assertEquals(tree.get(42), null);
        assertTrue(! tree.isEmpty());
    }

    @Test
    void test_remove_with_length_one() {
        tree.put(1, "A");
        assertTrue(tree.length() == 1);
        assertTrue(tree.get(1) == "A");
        assertTrue(tree.remove(1).equals("A"));
        assertTrue(tree.length() == 0);
        assertTrue(tree.isEmpty());
    }

    @Test
    void test_remove_from_empty() {
        assertTrue(tree.isEmpty());
        assertTrue(tree.length() == 0);
        assertEquals(tree.remove(0), null);
        assertTrue(tree.isEmpty());
        assertTrue(tree.length() == 0);
    }

    @Test
    void test_remove_not_empty() {
        tree.put(10, "A");
        tree.put(5, "B");
        tree.put(15, "C");
        assertEquals(tree.length(), 3);
        assertTrue(tree.remove(10).equals("A"));
        assertEquals(tree.length(), 2);
        assertTrue(tree.isBalanced());
    }

    @Test
    void test_remove_if_not_present() {
        tree.put(10, "A");
        tree.put(15, "B");
        tree.put(20, "C");
        assertEquals(tree.remove(42), null);
        assertEquals(tree.length(), 3);
    }

    @Test
    void test_contains() {
        tree.put(11, "A");
        tree.put(-1, "B");
        assertEquals(tree.contains(11), true);
        assertEquals(tree.contains(42), false);
    }

    @Test
    void test_in_order_traversal() {
        tree.put(34, "A");
        tree.put(11, "B");
        tree.put(-11, "C");
        tree.put(-13, "D");
        tree.put(-20, "E");
        tree.put(0, "F");

    }

    @Test
    void test_toString() {
        System.out.println(tree);
        tree.put(34, "A");
        System.out.println(tree);
        tree.put(11, "B");
        System.out.println(tree);
        tree.put(-11, "C");
        System.out.println(tree);
    }

   @Test
    void test_large_map_balanced_factor() {
        TreeMap<Integer, String> tm = new TreeMap<>();
        TreeMap<Integer, String> tm1 = new TreeMap<>();
        AVLTreeMap<Integer, String> tree1 = new AVLTreeMap<>();
        final long timePuts = System.currentTimeMillis();
        for (int i = 0; i < 20000000; ++i) {
            tree.put(ThreadLocalRandom.current().nextInt(-10000, 10000), "A");
            tree.put(i, "A");
            //assertTrue(tree.isBalanced());
            //tree1.put(i, "A");
            tm.put(ThreadLocalRandom.current().nextInt(-10000, 10000), "A");
            //tm1.put(ThreadLocalRandom.current().nextInt(-10000, 10000), "A");
            //tm1.put(i, "A");
        }
        final long timePute = System.currentTimeMillis();
        System.out.println(("Time of put: " + (timePute - timePuts) / 1000.0));
        final long st = System.currentTimeMillis();
        for (int i = 0; i < 2000000; ++i) {
                //assert(tree.equals(tree1));
                //tree.get(ThreadLocalRandom.current().nextInt(-10000, 10000));
                //assertTrue(tree.isBalanced());
                //assertTrue(tm.equals(tm1));
                tm.get(ThreadLocalRandom.current().nextInt(-10000, 10000));
                //assertTrue(tree.isBalanced());
        }
        final long en = System.currentTimeMillis();
        System.out.println("time of get/del: " + ((en - st) / 1000.0));

    }

    @Test
    void test_min() {
        assertEquals(tree.min(), null);
        tree.put(0, "A");
        tree.put(-1, "B");
        tree.put(3, "C");
        assertEquals(tree.min(), -1 );
    }

    @Test
    void test_max() {
        assertEquals(tree.max(), null);
        tree.put(0, "A");
        tree.put(-1, "B");
        tree.put(3, "C");
        tree.put(6, "C");
        assertEquals(tree.max(), 6 );
    }

    @Test
    void test_equals() {
        AVLTreeMap<Integer, String> tree1 = new AVLTreeMap<>();
        tree1.put(0, "A");
        tree1.put(-1, "B");
        tree1.put(3, "C");
        tree.put(0, "A");
        tree.put(-1, "B");
        tree.put(3, "C");
        assertTrue(tree.equals(tree1));
        assertTrue(! tree.equals(new String()));
    }


    @Test
    void test_equals_empty() {
        AVLTreeMap<Integer, String> tree1 = new AVLTreeMap<>();
        AVLTreeMap<Integer, String> tree2 = new AVLTreeMap<>();
        AVLTreeMap<Integer, String> tree3 = new AVLTreeMap<>();
        tree1.put(0, "A");
        tree1.put(-1, "B");
        tree1.put(3, "C");
        tree.put(0, "A");
        tree.put(-1, "B");
        tree.put(3, "C");
        tree.put(4, "D");
        assertTrue(tree2.equals(tree2));
        assertTrue(! tree.equals(tree1));
        assertTrue(! tree.equals(new String()));
    }
}