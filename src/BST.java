import java.util.Stack;

interface Tree {

    // опрос размера дерева
    int getLength ();

    // очистка дерева
    void clearTree ();

    // проверка дерева на пустоту
    boolean isEmpty ();

    // поиск элемента с заданным ключом
    Node findNode (int key);

    // включение нового элемента с заданным ключом
    void addNode (int key);

    // удаление элемента с заданным ключом
    void removeNode (int key);

    // итератор для доступа к элементам дерева
    Iterator getIterator ();

    // обход дерева по схеме: нерекурсивной (Lt -> t -> Rt)
    void traverseTree ();

    // определение длины внутреннего пути дерева
    int getInternalPathLength ();

    // вывод структуры дерева на экран
    void showTree ();

    // опрос числа просмотренных операцией узлов дерева
    int getNumberOfNodesVisitedByOperation ();

}

class Node {

    int key;
    double data;
    Node left;
    Node right;

    Node (int key) {
        this.key = key;
        left = null;
        right = null;
    }

    public String toString () {
        return Integer.toString(this.key);
    }

}

class Iterator {
    private Node root = null;
    private Node current = null;
    private Stack temporary = new Stack();
    private Stack nextNodes = new Stack();
    private Stack previousNodes = new Stack();

    Iterator (Node treeRoot) {
        root = treeRoot;
        setToRoot();
        System.out.println(previousNodes + " " + nextNodes);
    }

    // установка на корень дерева
    public void setToRoot () {
        current = root;
        if (root.left != null) {
            temporary.clear();
            traverseSubtree(root.left);
            previousNodes.addAll(temporary);
        }
        if (root.right != null) {
            temporary.clear();
            traverseSubtree(root.right);
            while (!temporary.empty()) {
                nextNodes.push(temporary.pop());
            }
        }
    }

    // проверка конца дерева
    public boolean isLast () {
        return nextNodes.empty();
    }

    // доступ к данным текущего элемента дерева
    public double getData () {
        System.out.println(current.key + ": " + current.data);
        return current.data;
    }

    // переход к следующему по значению ключа элементу дерева
    public void goToNext () {
        previousNodes.push(current);
        current = (Node) nextNodes.pop();
    }

    // переход к предыдущему по значению ключа элементу дерева
    public void goToPrevious () {
        nextNodes.push(current);
        current = (Node) previousNodes.pop();
    }

    private void traverseSubtree (Node node) {
        if (node.left != null) {
            traverseSubtree(node.left);
        }
        temporary.push(node);
        if (node.right != null) {
            traverseSubtree(node.right);
        }
    }
}

public class BST implements Tree {

    private static Node root;
    private int length;
    private int counter = 0;

    private BST () {
        root = null;
    }

    public int getLength () {
        counter = 0;
        return length;
    }

    public void clearTree () {
        counter = 0;
        root = null;
        length = 0;
    }

    public boolean isEmpty () {
        counter = 0;
        return root == null;
    }

    public Node findNode (int key) {
        counter = 0;
        if (checkForEmptiness()) {
            return null;
        }
        Node current = root;
        while (true) {
            if (key == current.key) {
                counter++;
                return current;
            } else if (key < current.key) {
                counter += 2;
                if (current.left == null) {
                    counter++;
                    System.out.println("There's no such element in the tree.");
                    return null;
                } else {
                    current = current.left;
                    counter+= 2;
                }
            } else {
                counter += 2;
                if (current.right == null) {
                    counter++;
                    System.out.println("There's no such element in the tree.");
                    return null;
                } else {
                    current = current.right;
                    counter++;
                }
            }
        }
    }

    public void addNode (int key) {
        counter = 0;
        length++;
        Node newOne = new Node(key);
        if (root == null) {
            root = newOne;
            return;
        }
        counter++;
        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (key == current.key) {
                counter++;
                length--;
                return;
            } else if (key < current.key) {
                current = current.left;
                counter += 3;
                if (current == null) {
                    parent.left = newOne;
                    counter++;
                    return;
                }
                counter++;
            } else {
                current = current.right;
                counter += 3;
                if (current == null) {
                    parent.right = newOne;
                    counter++;
                    return;
                }
                counter++;
            }
        }
    }

    public void removeNode (int key) {
        if (checkForEmptiness()) {
            return;
        }
        length--;
        Node current = root;
        Node parent = root;
        boolean isLeft = false;
        while (key != current.key) {
            counter++;
            parent = current;
            if (key < current.key) {
                isLeft = true;
                current = current.left;
                counter += 2;
            } else {
                isLeft = false;
                current = current.right;
                counter += 2;
            }
            if (current == null) {
                counter++;
                length++;
                System.out.println("There's no such element in the tree.");
                return;
            }
        }
        counter++;
        if (current.left == null && current.right == null) {
            counter += 2;
            if (current == root) {
                root = null;
            }
            counter += 2;
            if (isLeft) {
                parent.left = null;
                counter++;
            } else {
                parent.right = null;
                counter++;
            }
        } else if (current.right == null) {
            counter += 3;
            if (current == root) {
                root = current.left;
                counter++;
            }
            counter += 2;
            if (isLeft) {
                parent.left = current.left;
                counter += 2;
            } else {
                parent.right = current.left;
                counter += 2;
            }
        } else if (current.left == null) {
            counter += 3;
            if (current == root) {
                root = current.right;
                counter++;
            }
            counter += 2;
            if (isLeft) {
                parent.left = current.right;
                counter += 2;
            } else {
                parent.right = current.right;
                counter += 2;
            }
        } else {
            Node replacement = getReplacement(current);
            if (current == root) {
                root = replacement;
            }
            counter += 2;
            if (isLeft) {
                parent.left = replacement;
                counter++;
            } else {
                parent.right = replacement;
                counter++;
            }
            replacement.left = current.left;
            counter += 2;
        }
    }

    public Iterator getIterator() {
        return new Iterator(root);
    }

    public void traverseTree () {
        counter = 0;
        if (checkForEmptiness()) {
            return;
        }
        Stack nodesQueue = new Stack ();
        Node current = root;
        while (true) {
            if (current.left != null) {
                nodesQueue.push(current);
                current = current.left;
                counter += 2;
            } else {
                counter++;
                printNodeInfo(current);
                while (current.right == null) {
                    counter++;
                    if (nodesQueue.empty()) {
                        return;
                    } else {
                        current = (Node) nodesQueue.pop();
                        printNodeInfo(current);
                    }
                }
                current = current.right;
                counter++;
            }
        }
    }

    public int getInternalPathLength () {
        counter = 0;
        if (checkForEmptiness()) {
            return 0;
        }
        int level = 0;
        int internalPathLength = 0;
        Node current = root;
        Stack rightNodes = new Stack();
        Stack rightLevels = new Stack();
        while (true) {
            if (current.left != null && current.right != null) {
                internalPathLength += level++;
                rightNodes.push(current.right);
                rightLevels.push(new Integer(level));
                current = current.left;
                counter += 4;
            } else if (current.left != null) {
                internalPathLength += level++;
                current = current.left;
                counter += 4;
            } else if (current.right != null) {
                internalPathLength += level++;
                current = current.right;
                counter += 4;
            } else {
                counter += 2;
                if (rightNodes.empty()) {
                    return internalPathLength;
                } else {
                    current = (Node) rightNodes.pop();
                    level = Integer.parseInt(rightLevels.pop().toString());
                }
            }
        }
    }

    public void showTree () {
        counter = 0;
        if (checkForEmptiness()) {
            return;
        }
        printNode(root, "", true);
    }

    public int getNumberOfNodesVisitedByOperation() {
        return counter;
    }

    private boolean checkForEmptiness () {
        if (root == null) {
            counter++;
            System.out.println("The tree is empty.");
            return true;
        }
        return false;
    }

    private Node getReplacement (Node nodeToBeReplaced) {
        Node replacement = null;
        Node repParent = null;
        Node current = nodeToBeReplaced.right;
        counter++;
        while (current != null) {
            repParent = replacement;
            replacement = current;
            current = current.left;
            counter += 2;
        }
        counter++;
        if (replacement != nodeToBeReplaced.right) {
            repParent.left = replacement.right;
            replacement.right = nodeToBeReplaced.right;
            counter += 6;
        }
        counter += 2;
        return replacement;
    }

    private void printNodeInfo (Node node) {
        System.out.println(node.key + ": " + node.data);
        counter += 2;
    }

    private void printNode (Node node, String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.key);
        counter++;
        if (node.left != null || node.right != null) {
            if (node.left != null) {
                printNode(node.left, prefix + (isTail ? "    " : "│   "), false);
                counter += 3;
            }
            if (node.right != null) {
                printNode(node.right, prefix + (isTail ? "    " : "│   "), false);
                counter += 5;
            }
        } else {
            counter++;
        }
    }

    /*
        Execution of program
        Just for testing
    */
    public static void main (String []args) {
        BST tree = new BST();
        /*
        tree.addNode(2);
        tree.addNode(1);
        tree.addNode(1);
        tree.addNode(30);
        tree.addNode(10);
        tree.addNode(69);
        tree.addNode(74);
        tree.showTree();
        System.out.println("Trying to find element 11. Successful: " + (tree.findNode(11) != null));
        System.out.println("Trying to find element 1. Successful: " + (tree.findNode(1) != null));
        System.out.println("Trying to find element 10. Successful: " + (tree.findNode(10) != null));
        System.out.println("Length of the tree is: " + tree.getLength());
        System.out.println("Emptiness of the tree is: " + tree.isEmpty());
        System.out.println("The internal path of the tree is: " + tree.getInternalPathLength());
        System.out.println("Starting traversing the tree...");
        tree.traverseTree();
        System.out.println();
        tree.removeNode(30);
        tree.showTree();
        System.out.println("The internal path of the tree is: " + tree.getInternalPathLength());
        System.out.println("Starting traversing the tree...");
        tree.traverseTree();
        System.out.println();
        tree.clearTree();
        tree.showTree();
        System.out.println("Length of the tree is: " + tree.getLength());
        System.out.println("Emptiness of the tree is: " + tree.isEmpty());
        System.out.println("Trying to find element 10. Successful: " + (tree.findNode(10) != null));
        System.out.println("The internal path of the tree is: " + tree.getInternalPathLength());
        System.out.println("Starting traversing the tree...");
        tree.traverseTree();
        System.out.println();
        */
        int[] keys = {8, 4, 3, 2, 5, 6, 7, 11, 10, 13, 15, 17, 19, 18, 22, 21};
        for (int i = 0; i < keys.length; i++) {
            tree.addNode(keys[i]);
            System.out.println("Number of nodes visited: " + tree.getNumberOfNodesVisitedByOperation());
        }
        tree.getLength();
        System.out.println("Number of nodes visited: " + tree.getNumberOfNodesVisitedByOperation());
        tree.showTree();
        System.out.println("Number of nodes visited: " + tree.getNumberOfNodesVisitedByOperation());
        /*
        System.out.println("The internal path of the tree is: " + tree.getInternalPathLength());
        System.out.println("Starting traversing the tree...");
        Stack result = tree.traverseTree();
        System.out.println(result);
        */
        Iterator it = tree.getIterator();
        it.getData();
        it.goToNext();
        it.goToNext();
        it.goToNext();
        it.getData();
        it.goToPrevious();
        it.getData();
        it.setToRoot();
        it.goToPrevious();
        it.goToPrevious();
        it.goToPrevious();
        it.goToPrevious();
        it.getData();
    }

}
