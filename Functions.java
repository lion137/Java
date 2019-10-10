package skew_heap;

class Functions<V extends Comparable<V>> {

    static boolean isEmpty(SkewHeap tree) {
        return (tree.data == null && tree.leftChild == null && tree.rightChild == null);
    }
    static SkewHeap merge(SkewHeap left, SkewHeap right) {
        if (null == left)
            return right;
        if (null == right)
            return left;
        if (left.data.compareTo(right.data) < 0 || left.data.compareTo(right.data) == 0) {
            return new SkewHeap(left.data, merge(right, left.leftChild), left.leftChild);
        }
        else
            return new SkewHeap(right.data, merge(left, right.rightChild), left.leftChild);

    }

    SkewHeap insert(V e, SkewHeap tree) {
        return merge(new SkewHeap(e, null, null), tree);
    }


    //debug print
    public void inorderPrint(SkewHeap tree) {
        if (null != this) {
            inorderPrint(tree.leftChild);
            System.out.println(tree.data);
            inorderPrint(tree.rightChild);
        }
    }
}
