package skew_heap;

import java.util.List;

public class SkewHeap <V extends Comparable<V>> {

    SkewNode root;

    public SkewHeap() {
        this.root = null;
    }

    class SkewNode {

        SkewNode leftChild, rightChild;
        V data;

        public SkewNode(V _data, SkewNode _left, SkewNode _right) {
            this.data = _data;
            this.leftChild = _left;
            this.rightChild = _right;
        }
    }

    SkewNode merge(SkewNode left, SkewNode right) {
        if (null == left)
            return right;
        if (null == right)
            return left;
        if (left.data.compareTo(right.data) < 0 || left.data.compareTo(right.data) == 0) {
            return new SkewNode(left.data, merge(right, left.rightChild), left.leftChild);
        } else
            return new SkewNode(right.data, merge(left, right.rightChild), right.leftChild);
    }

    void insert(V e) {
        this.root = merge(new SkewNode(e, null, null), this.root);
    }

     V pop() {
        if (null != this.root) {
            V o = (V) this.root.data;
            this.root = merge(this.root.leftChild, this.root.rightChild);
            return o;
        }
        else
            throw new IndexOutOfBoundsException("pop: Index out of bounds");
    }
}

