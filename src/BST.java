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

    // обход дерева по схеме: нерекурсивной (Lt -> t -> Rt)

    // определение длины внутреннего пути дерева
    int getInternalPathLength ();

    // вывод структуры дерева на экран
    void showTree ();

    // опрос числа просмотренных операцией узлов дерева

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

}

class Iterator {
    // установка на корень дерева

    // проверка конца дерева

    // доступ к данным текущего элемента дерева

    // переход к следующему по значению ключа элементу дерева

    // переход к предыдущему по значению ключа элементу дерева
}

public class BST implements Tree {

    private static Node root;
    private int length;

    private BST () {
        root = null;
    }

    public int getLength () {
        return length;
    }

    public void clearTree () {
        root = null;
        length = 0;
    }

    public boolean isEmpty () {
        return root == null;
    }

    public Node findNode(int key) {
        if (root == null) {
            System.out.println("The tree is empty.");
            return null;
        }
        Node current = root;
        while (true) {
            if (key == current.key) {
                return current;
            } else if (key < current.key) {
                if (current.left == null) {
                    System.out.println("There's no such element in the tree.");
                    return null;
                } else {
                    current = current.left;
                }
            } else {
                if (current.right == null) {
                    System.out.println("There's no such element in the tree.");
                    return null;
                } else {
                    current = current.right;
                }
            }
        }
    }

    public void addNode (int key) {
        length++;
        Node newOne = new Node(key);
        if (root == null) {
            root = newOne;
            return;
        }
        Node current = root;
        Node parent = null;
        while (true) {
            parent = current;
            if (key == current.key) {
                length--;
                return;
            } else if (key < current.key) {
                current = current.left;
                if (current == null) {
                    parent.left = newOne;
                    return;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newOne;
                    return;
                }
            }
        }
    }

    public void removeNode(int key) {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }
        length--;
        Node current = root;
        Node parent = root;
        boolean isLeft = false;
        while (key != current.key) {
            parent = current;
            if (key < current.key) {
                isLeft = true;
                current = current.left;
            } else {
                isLeft = false;
                current = current.right;
            }
            if (current == null) {
                length++;
                System.out.println("There's no such element in the tree.");
                return;
            }
        }
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            }
            if (isLeft) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (current.right == null) {
            if (current == root) {
                root = current.left;
            }
            if (isLeft) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
            }
            if (isLeft) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else {
            Node replacement = null;
            Node repParent = null;
            Node repCurrent = current.right;
            while (repCurrent != null) {
                repParent = replacement;
                replacement = repCurrent;
                repCurrent = repCurrent.left;
            }
            if (replacement != current.right) {
                repParent.left = replacement.right;
                replacement.right = current.right;
            }
            if (current == root) {
                root = replacement;
            }
            if (isLeft) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
            replacement.left = current.left;
        }
    }

    public int getInternalPathLength() {

        /*
            МЕСТО ПОСЛЕДНЕГО ВЫДОХА ГОСПОДИНА ПЖ
            НУЖНО БОЛЬШЕ СТАКОВ!!!
        */

        if (root == null) {
            System.out.println("The tree is empty.");
            return 0;
        }
        int level = 0;
        int internalPathLength = 0;
        Node current = root;
        Node parent = null;
        while (true) {
            if (current.left != null || current.right != null) {

            } else {
                return internalPathLength;
            }
        }
    }

    public void showTree() {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(".....................................");
        while (!isRowEmpty) {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for (int j = 0; j < nBlanks; j++)
                System.out.print(' ');

            while (!globalStack.isEmpty()) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.key);
                    localStack.push(temp.left);
                    localStack.push(temp.right);

                    if (temp.right != null || temp.left != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++)
                    System.out.print(' ');

            }

            System.out.println();
            nBlanks /= 2;
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop());
        }
        System.out.println(".....................................");
    }


    /*
        Execution of program
        Just for testing
    */
    public static void main (String []args) {
        BST tree = new BST();
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
        System.out.println();
        tree.removeNode(30);
        tree.showTree();
        System.out.println();
        tree.clearTree();
        tree.showTree();
        System.out.println("Length of the tree is: " + tree.getLength());
        System.out.println("Emptiness of the tree is: " + tree.isEmpty());
        System.out.println("Trying to find element 10. Successful: " + (tree.findNode(10) != null));
    }

}
