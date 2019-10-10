package skew_heap;


import java.util.ArrayList;
import java.util.List;

class Functions {

    static SkewHeap listToHeap(List list) {
        SkewHeap o = new SkewHeap();
        for (int i = 0; i < list.size(); i++)
            o.insert((Comparable) list.get(i));
        return o;
    }

    static List<Object> heapToList(SkewHeap tree) {
        List<Object> ll = new ArrayList();
        try {
            while (true) {
                ll.add(tree.pop());
            }
        }
        catch (IndexOutOfBoundsException e) {
            return ll;
        }
    }

    static List sort(List list) {
        return heapToList(listToHeap(list));
    }

    //debug print helper
    private static void _print(SkewHeap.SkewNode tree) {
        if (null != tree) {
            _print(tree.leftChild);
            System.out.print(tree.data.toString() + "->");
            _print(tree.rightChild);
        }
    }

    //debug print
    static void inorderPrint(SkewHeap tree) {
        if (null == tree.root) {
            System.out.println("()");
            return;
        }
        _print(tree.root);
        System.out.println();
    }
}



