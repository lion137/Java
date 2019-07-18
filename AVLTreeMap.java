import java.util.*;

public class AVLTreeMap<K extends Comparable<K>, V>  {

    private Node root;
    private int N;

    private class Node {

        private K key;
        private V val;
        private Node left, right, parent;
        private int balanceFactor;

        Node(K _key, V _val, Node _left, Node _right, Node _parent) {
            this.key = _key;
            this.val = _val;
            this.left = _left;
            this.right = _right;
            this.parent = _parent;
        }

        private boolean hasLeftChild() {
            return this.left != null;
        }

        private boolean hasRightChild() {
            return this.right != null;
        }

        private boolean isLeftChild() {
            return (this.parent != null) && (this.parent.left == this);
        }

        private boolean isRightChild() {
            return (this.parent != null) && (this.parent.right == this);
        }

        private boolean isRoot() {
            return this.parent == null;
        }

        private boolean isLeaf() {
            return !(this.right != null || this.left != null);
        }

        private boolean hasAnyChildren() {
            return (this.right != null || this.left != null);
        }

        private boolean hasBothChildren() {
            return (this.right != null && this.left != null);
        }

        private void replaceNodeData(K _key, V _val, Node _left, Node _right) {
            this.key = _key;
            this.val = _val;
            this.left = _left;
            this.right = _right;
            if (this.hasLeftChild())
                this.left.parent = this;
            if (this.hasRightChild())
                this.right.parent = this;
        }

        private Node findSuccessor() {
            Node succ = null;
            if (this.hasRightChild()) {
                succ = this.right.findMin();
            } else {
                if (this.parent != null) {
                    if (this.isLeftChild()) {
                        succ = this.parent;
                    } else {
                        this.parent.right = null;
                        succ = this.parent.findSuccessor();
                        this.parent.right = this;
                    }
                }
            }
            return succ;

        }

        private Node findMin() {
            Node current = this;
            while (current.hasLeftChild())
                current = current.left;
            return current;
        }

        private void spliceOut() {
            if (this.isLeaf()) {
                if (this.isLeftChild())
                    this.parent.left = null;
                else
                    this.parent.right = null;
            } else if (this.hasAnyChildren()) {
                if (this.hasLeftChild()) {
                    if (this.isLeftChild())
                        this.parent.left = this.left;
                    else
                        this.parent.right = this.left;
                    this.left.parent = this.parent;
                } else {
                    if (this.isLeftChild())
                        this.parent.left = this.right;
                    else
                        this.parent.right = this.right;
                    this.right.parent = this.parent;
                }
            }
        }
    }

    public AVLTreeMap() {
        this.root = null;
        this.N = 0;
    }
    // length of the tree

    public int length() {
        return this.N;
    }


    // is empty

    public boolean isEmpty() {
        return null == this.root;
    }

    // find min
    // returns null if empty
    public K min() {
        Node node = root;
        if (null != node) {
            while (null != node.left) {
                node = node.left;
            }
            return node.key;
        }
        else return null;
    }

    // find max
    //returns null if empty
    public K max() {
        Node node = root;
        if (null != node) {
            while (null != node.right) {
                node = node.right;
            }
            return node.key;
        }
        else return null;
    }

    // get value

    // returns null if not in a tree

    public V get(K key) {
        Node res = root;
        while  (null != res) {
            int cmp = key.compareTo(res.key);
            if (cmp < 0) res = res.left;
            else if (cmp > 0) res = res.right;
            else return res.val;
        }
        return null;
    }

    private Node __get(K key, Node x) {
        Node res = x;
        while (null != res) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) res = res.left;
            else if (cmp > 0) res = res.right;
            else return res;
        }
        return null;
    }

    // contains key

    public final boolean contains(K key) {
        if (null != this.__get(key, this.root))
            return true;
        return false;
    }

    // put value, search and add, update if found
    public void put(K key, V val) {
        if (this.root == null) {
            this.root = new Node(key, val, null, null, null);
            this.root.balanceFactor = 0;
        } else {
            this.__put(key, val, this.root);
        }
        ++this.N;
    }

    private void __put(K key, V val, Node x) {
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            if (x.hasLeftChild()) {
                this.__put(key, val, x.left);
            } else {
                x.left = new Node(key, val, null, null, x);
                this.updateBalance(x.left);
            }
        } else if (cmp > 0) {
            if (x.hasRightChild()) {
                this.__put(key, val, x.right);
            } else {
                x.right = new Node(key, val, null, null, x);
                this.updateBalance(x.right);
            }
        } else {
            x.val = val;
            --this.N;
        }
    }

    // delete

    // deletes value with key key and returns it, returns null if key not present
    public V remove(K key) {
        Node nodeToRemove;
        if (this.length() > 1) {
            nodeToRemove = this.__get(key, this.root);
            if (null != nodeToRemove) {
                V oldVal = nodeToRemove.val;
                this.delete(nodeToRemove);
                --this.N;
                return oldVal;
            } else {
                return null;
            }
        } else if ((this.length() == 1) && (this.root.key == key)) {
            V oldVal = this.root.val;
            this.root = null;
            --this.N;
            return oldVal;
        } else {
            return null;
        }
    }

    private void delete(Node currentNode) {
        if (currentNode.isLeaf()) {
            if (currentNode == currentNode.parent.left)
                currentNode.parent.left = null;
            else
                currentNode.parent.right = null;
        }

        else if (currentNode.hasBothChildren()) {
            Node successor = this.root.findSuccessor();
            successor.spliceOut();
            currentNode.key = successor.key;
            currentNode.val = successor.val;
        }

        else {
            if (currentNode.hasLeftChild()) {
                if (currentNode.isLeftChild()) {
                    currentNode.left.parent = currentNode.parent;
                    currentNode.parent.left = currentNode.left;
                }
                else if (currentNode.isRightChild()) {
                    currentNode.left.parent = currentNode.parent;
                    currentNode.parent.right = currentNode.left;
                }
                else {
                    currentNode.replaceNodeData(currentNode.left.key, currentNode.left.val,
                            currentNode.left.left, currentNode.left.right);
                }
            }
            else {
                if (currentNode.isLeftChild()) {
                    currentNode.right.parent = currentNode.parent;
                    currentNode.parent.left = currentNode.right;
                }
                else if (currentNode.isRightChild()) {
                    currentNode.right.parent = currentNode.parent;
                    currentNode.parent.right = currentNode.right;
                }
                else {
                    currentNode.replaceNodeData(currentNode.right.key, currentNode.right.val,
                            currentNode.right.left, currentNode.right.right);
                }
            }

        }
    }

    // rotate left

    private void rotateLeft(Node rotRoot) {
        Node newRoot = rotRoot.right;
        rotRoot.right = newRoot.left;
        if (newRoot.left != null)
            newRoot.left.parent = rotRoot;
        newRoot.parent = rotRoot.parent;
        if (rotRoot.isRoot()) {
            this.root = newRoot;
        } else {
            if (rotRoot.isLeftChild())
                rotRoot.parent.left = newRoot;
            else
                rotRoot.parent.right = newRoot;
        }
        newRoot.left = rotRoot;
        rotRoot.parent = newRoot;
        rotRoot.balanceFactor = rotRoot.balanceFactor + 1 - Math.min(newRoot.balanceFactor, 0);
        newRoot.balanceFactor = newRoot.balanceFactor + 1 + Math.max(rotRoot.balanceFactor, 0);

    }
    //rotate right

    private void rotateRight(Node rotRoot) {
        Node newRoot = rotRoot.left;
        rotRoot.left = newRoot.right;
        if (newRoot.right != null)
            newRoot.right.parent = rotRoot;
        newRoot.parent = rotRoot.parent;
        if (rotRoot.isRoot()) {
            this.root = newRoot;
        } else {
            if (rotRoot.isLeftChild())
                rotRoot.parent.left = newRoot;
            else
                rotRoot.parent.right = newRoot;
        }
        newRoot.right = rotRoot;
        rotRoot.parent = newRoot;
        rotRoot.balanceFactor = rotRoot.balanceFactor - 1 - Math.max(newRoot.balanceFactor, 0);
        newRoot.balanceFactor = newRoot.balanceFactor - 1 + Math.min(rotRoot.balanceFactor, 0);
    }

    private void updateBalance(Node node) {
        if ((node.balanceFactor > 1) || (node.balanceFactor < -1)) {
            this.rebalance(node);
            return;
        }
        if (null != node.parent) {
            if (node.isLeftChild())
                ++node.parent.balanceFactor;
            else if (node.isRightChild())
                --node.parent.balanceFactor;
            if (node.parent.balanceFactor != 0)
                this.updateBalance(node.parent);
        }
    }

    private void rebalance(Node node) {
        if (node.balanceFactor < 0) {
            if (node.right.balanceFactor > 0) {
                this.rotateRight(node.right);
                this.rotateLeft(node);
            } else {
                this.rotateLeft(node);
            }
        } else if (node.balanceFactor > 0) {
            if (node.left.balanceFactor < 0) {
                this.rotateLeft(node.left);
                this.rotateRight(node);
            } else {
                this.rotateRight(node);
            }
        }
    }
    // --------------------------Override-----------------------------------------------------------------------------

    @Override
    public String toString() {
        String b = "[";
        String o = "";
        o += b;
        Node current = this.root;
        Stack<Node> stack = new Stack<>();
        boolean isDone = false;
        int i = 0;
        while (! isDone) {
            if (null != current) {
                stack.push(current);
                current = current.left;
            }
            else {
                if (! stack.isEmpty()) {
                    current = stack.pop();
                    if (i <= this.length() - 2 )
                        o += current.key + ": " + current.val + ", ";
                    else
                        o += current.key + ": " + current.val;
                    current = current.right;
                    ++i;
                }
                else {
                    isDone = true;
                }
            }

        }
        o += "]";
        return o;
    }

    @Override
    public int hashCode() {
        int keyHash = this.root.key == null ? 0 : this.root.key.hashCode();
        int valueHash = this.root.val == null ? 0 : this.root.val.hashCode();
        return keyHash ^ valueHash;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof AVLTreeMap))return false;
        AVLTreeMap e = (AVLTreeMap) other;
        Node curr = this.root;
        Node curr1 = e.root;
        if (this.length() != e.length()) return false;
        Stack<Node> s = new Stack<>();
        Stack<Node> v = new Stack<>();
        boolean isDone = false;
        while (! isDone) {
            if ((null != curr) && (null != curr1)) {
                s.push(curr);
                v.push(curr1);
                curr = curr.left;
                curr1 = curr1.left;
            }
            else {
                if (! s.isEmpty()) {
                    curr = s.pop();
                    curr1 = v.pop();
                    if (! ( (curr.key.equals(curr1.key) && (curr.val.equals(curr1.val)) ))) return false;
                    curr = curr.right;
                    curr1 = curr1.right;
                }
                else {
                    isDone = true;
                }
            }
        }
        return true;
    }



    // debug functions ----------------------------------------------- debug ------------------------------------------

    // in order

    public void inOrder() {
        this.__inOrder(this.root);
    }

    private void __inOrder(Node node) {
        if (null != node) {
            __inOrder(node.left);
            System.out.print(node.key);
            System.out.print(", ");
            __inOrder(node.right);
        }
    }

    public boolean isBalanced() {
        if (!this.isEmpty())
            return Math.abs(this.__height(this.root.left) - this.__height(this.root.right)) <= 1;
        else
            return true;
    }

    public int getBalanceFactor(Node n) {
        return n.balanceFactor;
    }

    //height

    public int height() {
        return __height(root);
    }

    private int __height(Node x) {
        if (x == null) return 0;
        else {
            int l_height = __height(x.left);
            int r_height = __height(x.right);
            return 1 + Math.max(l_height, r_height);
        }
    }
    // print tree

    public <T extends Comparable<?>> void printTree() {
        int maxLevel = maxLevel(this.root);
        if (null == this.root)
            System.out.println("()");
        else
            printNodeInternal(Collections.singletonList(this.root), 1, maxLevel);
    }

    private <T extends Comparable<?>> void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.val + "," + node.key);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println();

        for (int i = 1; i <= edgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(edgeLines + edgeLines + i + 1);
                    continue;
                }
                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    printWhitespaces(1);
                printWhitespaces(i + i - 1);
                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    printWhitespaces(1);
                printWhitespaces(edgeLines + edgeLines - i);
            }
            System.out.println("");
        }
        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private <T extends Comparable<?>> int maxLevel(Node node) {
        if (node == null)
            return 0;

        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    private <T> boolean isAllElementsNull(List<T> list) {
        for (T object : list) {
            if (object != null)
                return false;
        }
        return true;
    }

}
