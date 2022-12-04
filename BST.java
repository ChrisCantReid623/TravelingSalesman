public class BST<K extends Comparable<K>, V> {
    private class Node {
        public K key;
        public V value;
        public Node left = null;
        public Node right = null;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root = null;

//    public void add(K key, V value) {
//        if (root == null) {
//            root = new Node(key, value);
//        } else {
//            addRecur(root, key ,value);
//        }
//    }

//    private void addRecur(Node curr, K key, V value) {
//        if (curr == null) {
//            curr = new Node(key, value);
//        }
//        if (compare(key, curr.key) < 0) {
//            addRecur(curr.left, key, value);
//        } else if (compare(key, curr.key) > 0) {
//            addRecur(curr.right, key, value);
//        }
//    }

    private int compare(K a, K b) { return a.compareTo(b); }

    public void add(K key, V value) {
        root = addRecur(root, key, value);
    }

    private Node addRecur(Node curr, K key, V value) {
        if (curr == null) {
            return new Node(key, value);
        }
        if (compare(key, curr.key) < 0) {
            curr.left = addRecur(curr.left, key, value);
        }
        else if (compare(key, curr.key) > 0) {
            curr.right = addRecur(curr.right, key, value);
        }
        return curr;
    }

    public V get(K key) { return getRecur(root, key); }

    private V getRecur(Node curr, K key) {
        if (curr == null) {
            return null;
        }
        if (compare(key, curr.key) == 0) {
            return curr.value;
        }
        if (compare(key, curr.key) < 0) {
            return getRecur(curr.left, key);
        }
        else {
            return getRecur(curr.right, key);
        }
    }

    public void traversePreOrder(Node node) {
        if (node != null) {
            System.out.println(" " + node.value);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.println(" " + node.value);
            traverseInOrder(node.right);
        }
    }

    public void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.println(" " + node.value);
        }
    }

    public void print() {
        System.out.println("PreOrder: ");
        traversePreOrder(root);
        System.out.println();
        System.out.println("InOrder: ");
        traverseInOrder(root);
        System.out.println();
        System.out.println("PostOrder: ");
        traversePostOrder(root);
        System.out.println();
    }

    public K min() { return min(root); }

    public K min(Node root) { return root.left == null ? root.key : min(root.left); }

    public K max() { return max(root); }

    public K max(Node root) { return root.right == null ? root.key : max(root.right); }

    public void delete(K key) { root = deleteRecur(root, key); }

    private Node deleteRecur(Node curr, K key) {
        if (curr == null) {
            return null;
        }
        if (compare(key, curr.key) == 0) {
            if (curr.left == null && curr.right == null) {
                return null;
            }
            else if (curr.right == null) {
                return curr.left;
            }
            else if (curr.left == null) {
                return curr.right;
            }
            else {
                Node n = maxNode(curr.left);
                curr.key = n.key;
                curr.value = n.value;
                curr.left = deleteRecur(curr.left, n.key);
                return curr;
            }
        }
        else {
            if (compare(key, curr.key) < 0) {
                curr.left = deleteRecur(curr.left, key);
            }
            else {
                curr.right = deleteRecur(curr.right, key);
            }
            return curr;
        }
    }

    private Node maxNode(Node root) { return root.right == null ? root : maxNode(root.right); }
}