import java.util.Stack;

interface Tree {
    /*
        Интерфейс АТД "BST - дерево"
     */

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

class Node<T> {
    /*
        Элемент дерева с абстрактным типом данных
     */

    int key;
    T data;
    Node left;
    Node right;

    Node (int key) {
        this.key = key;
        left = null;
        right = null;
    }

    // Присвоение элементу данных определенного типа
    public void setData(T data) {
        this.data = data;
    }

}

class Iterator {
    /*
        Итератор для доступа к элементам дерева
     */

    private Node root = null;
    private Node current = null;
    private Stack temporary = new Stack();
    private Stack nextNodes = new Stack();
    private Stack previousNodes = new Stack();

    Iterator (Node treeRoot) {
        root = treeRoot;
        setToRoot();
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
    public void getData () {
        System.out.println(current.key + ": " + current.data);
    }

    // переход к следующему по значению ключа элементу дерева
    public void goToNext () {
        if (nextNodes.empty()) {
            System.out.println("Следующий элемент отсутствует.");
        } else {
            previousNodes.push(current);
            current = (Node) nextNodes.pop();
        }
    }

    // переход к предыдущему по значению ключа элементу дерева
    public void goToPrevious () {
        if (previousNodes.empty()) {
            System.out.println("Предыдущий элемент отсутствует.");
        } else {
            nextNodes.push(current);
            current = (Node) previousNodes.pop();
        }
    }

    // вспомогательная функция для составление списка элементов дерева
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

class BST implements Tree {
    /*
        класс "BST - дерево"
     */

    private static Node root;
    private static final String[] TYPES = {"byte", "short", "int", "long", "float", "double", "boolean", "char"};
    private String type;
    private int length = 0;
    private int counter = 0;

    BST () {
        root = null;
    }

    // установка типа данных, хранящихся в дереве
    public void setNodeType (String nodeType) {
        boolean valid = false;
        for (int i = 0; i < TYPES.length; i++) {
            if (nodeType.equals(TYPES[i])) {
                valid = true;
            }
        }
        if (valid) {
            type = nodeType;
        } else {
            System.out.println("Incorrect type");
        }
    }

    // опрос размера дерева
    public int getLength () {
        counter = 0;
        return length;
    }

    // очистка дерева
    public void clearTree () {
        counter = 0;
        root = null;
        length = 0;
    }

    // проверка дерева на пустоту
    public boolean isEmpty () {
        counter = 0;
        return root == null;
    }

    // поиск элемента с заданным ключом
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
                    return null;
                } else {
                    current = current.left;
                    counter+= 2;
                }
            } else {
                counter += 2;
                if (current.right == null) {
                    counter++;
                    return null;
                } else {
                    current = current.right;
                    counter++;
                }
            }
        }
    }

    // включение нового элемента с заданным ключом
    public void addNode (int key) {
        counter = 0;
        length++;
        Node newOne;
        if (type.length() > 0) {
            switch(type) {
                case "byte":
                    newOne = new Node<Byte>(key);
                    break;
                case "short":
                    newOne = new Node<Short>(key);
                    break;
                case "int":
                    newOne = new Node<Integer>(key);
                    break;
                case "long":
                    newOne = new Node<Long>(key);
                    break;
                case "float":
                    newOne = new Node<Float>(key);
                    break;
                case "double":
                    newOne = new Node<Double>(key);
                    break;
                case "boolean":
                    newOne = new Node<Boolean>(key);
                    break;
                case "char":
                    newOne = new Node<Character>(key);
                    break;
                default:
                    newOne = new Node(key);
            }
        } else {
            newOne = new Node(key);
        }
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

    // удаление элемента с заданным ключом
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

    // получение итератора для доступа к элементам дерева
    public Iterator getIterator() {
        return new Iterator(root);
    }

    // обход дерева по схеме Lt -> t -> Rt в нерекурсивной форме
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

    // определение длины внутреннего пути дерева (нерекурсивная форма)
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
                rightLevels.push(level);
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

    // вывод структуры дерева на экран
    public void showTree () {
        counter = 0;
        if (checkForEmptiness()) {
            return;
        }
        printNode(root, "", true);
    }

    // опрос числа просмотренных операцией узлов дерева
    public int getNumberOfNodesVisitedByOperation() {
        return counter;
    }

    // дополнительная функцая для внутренней проверки дерева на пустоту
    private boolean checkForEmptiness () {
        if (root == null) {
            counter++;
            System.out.println("The tree is empty.");
            return true;
        }
        return false;
    }

    // функция поиска замены удаленному элементу
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

    // дополнительная функция для вывода данных элемента
    private void printNodeInfo (Node node) {
        System.out.println(node.key + ": " + node.data);
        counter += 2;
    }

    // дополнительная функция для вывода структуры дерева на экран
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

}
