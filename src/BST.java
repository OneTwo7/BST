import java.util.Stack;

interface Tree {

    // опрос размера дерева

    // очистка дерева

    // проверка дерева на пустоту

    // поиск элемента с заданным ключом

    // включение нового элемента с заданным ключом
    void addNode (int key);

    // удаление элемента с заданным ключом

    // итератор для доступа к элементам дерева

    // обход дерева по схеме: нерекурсивной (Lt -> t -> Rt)

    // определение длины внутреннего пути дерева

    // вывод структуры дерева на экран
    void showTree ();

    // опрос числа просмотренных операцией узлов дерева

}

class Node {

    int key;
    double data;
    Node left;
    Node right;

    public Node (int key) {
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

    public static Node root;

    public BST () {
        root = null;
    }

    public void addNode (int key) {
        Node newOne = new Node(key);
        if (root == null) {
            root = newOne;
            return;
        }
        Node current = root;
        Node parent = null;
        while (true) {
            parent = current;
            if (key < current.key) {
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
        BST newTree = new BST();
        newTree.addNode(2);
        newTree.addNode(1);
        newTree.addNode(30);
        newTree.addNode(10);
        newTree.addNode(69);
        newTree.addNode(74);
        newTree.showTree();
    }

}
