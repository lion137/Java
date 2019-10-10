package skew_heap;

public class SkewHeap <V extends Comparable<V>> {

    V data;
    SkewHeap leftChild;
    SkewHeap rightChild;

    SkewHeap(V _data, SkewHeap _left, SkewHeap _right) {
        data      = _data;
        leftChild = _left;
        rightChild = _right;
    }

}


