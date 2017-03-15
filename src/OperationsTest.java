import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class OperationsTest {
    public static void fillTree (BST tree, int numOfNodes, String type) {
        if (!tree.isEmpty()) {
            tree.clearTree();
        }
        int key;
        Node current;
        tree.setNodeType(type);
        while (tree.getLength() < numOfNodes) {
            key = ThreadLocalRandom.current().nextInt(0, 100);
            tree.addNode(key);
            current = tree.findNode(key);
            current.setData(getRandomData(type).pop());
        }
    }

    public static Stack getRandomData (String type) {
        Stack stack = new Stack();
        switch (type) {
            case "byte":
                byte byteData = (byte) ThreadLocalRandom.current().nextInt(0, 128);
                stack.push(byteData);
                break;
            case "short":
                short shortData = (short) ThreadLocalRandom.current().nextInt();
                stack.push(shortData);
                break;
            case "int":
                int intData = ThreadLocalRandom.current().nextInt();
                stack.push(intData);
                break;
            case "long":
                long longData = (long) ThreadLocalRandom.current().nextInt();
                stack.push(longData);
                break;
            case "float":
                float floatData = ThreadLocalRandom.current().nextFloat();
                stack.push(floatData);
                break;
            case "double":
                double doubleData = ThreadLocalRandom.current().nextDouble();
                stack.push(doubleData);
                break;
            case "boolean":
                int newInt = ThreadLocalRandom.current().nextInt(0, 2);
                stack.push(newInt == 1);
                break;
            case "char":
                int charEncoding = ThreadLocalRandom.current().nextInt(0, 122);
                stack.push((char) charEncoding);
                break;
        }
        return stack;
    }

    public static void main (String[] args) {
        BST tree = new BST();
        final String[] TYPES = {"byte", "short", "int", "long", "float", "double", "boolean", "char"};
        Scanner reader = new Scanner(System.in);
        int n;
        int numOfNodes;
        String type;
        Node node;
        String line;
        Iterator iterator;
        boolean executionInProgress = true;
        while (executionInProgress) {
            System.out.println("Введите номер соответствующий одной из операций.");
            System.out.println("1 - заполнить дерево");
            System.out.println("2 - получить размер дерева");
            System.out.println("3 - очистить дерево");
            System.out.println("4 - проверить дерево на пустоту");
            System.out.println("5 - найти элемент");
            System.out.println("6 - добавить новый элемент");
            System.out.println("7 - удалить элемент");
            System.out.println("8 - получить итератор для доступа к данным массива");
            System.out.println("9 - обойти дерево");
            System.out.println("10 - определить длину внутреннего пути дерева");
            System.out.println("11 - вывести структуру дерева на экран");
            System.out.println("12 - получить число узлов дерева, просмотренных последней операцией");
            System.out.println("Для завершения программы, введите другое число.");
            n = reader.nextInt();
            switch(n) {
                case 1:
                    System.out.println("Введите размер дерева (1 - 100).");
                    numOfNodes = reader.nextInt();
                    if (numOfNodes < 1 || numOfNodes > 100) {
                        System.out.println("Введенное число не соответствует заданным границам.\n");
                        break;
                    }
                    System.out.println("Выберите тип данных для узлов дерева.");
                    System.out.println("0 - Byte");
                    System.out.println("1 - Short");
                    System.out.println("2 - Integer");
                    System.out.println("3 - Long");
                    System.out.println("4 - Float");
                    System.out.println("5 - Double");
                    System.out.println("6 - Boolean");
                    System.out.println("7 - Character");
                    n = reader.nextInt();
                    if (n < 0 || n > 7) {
                        System.out.println("Введенное число не соответствует заданным вариантам.\n");
                        break;
                    }
                    type = TYPES[n];
                    fillTree(tree, numOfNodes, type);
                    System.out.println("Операция исполнена.");
                    break;
                case 2:
                    System.out.println(tree.getLength());
                    System.out.println("Операция исполнена.");
                    break;
                case 3:
                    tree.clearTree();
                    System.out.println("Операция исполнена.");
                    break;
                case 4:
                    if (tree.isEmpty()) {
                        System.out.println("Дерево пусто.");
                    } else {
                        System.out.println("Дерево заполнено.");
                    }
                    System.out.println("Операция исполнена.");
                    break;
                case 5:
                    System.out.println("Введите ключ искомого элемента.");
                    n = reader.nextInt();
                    node = tree.findNode(n);
                    if (node == null) {
                        System.out.println("Элемент не найден.");
                    } else {
                        System.out.println("Элемент найден.");
                        System.out.println(node.key + ": " + node.data);
                    }
                    System.out.println("Операция исполнена.");
                    break;
                case 6:
                    System.out.println("Введите ключ элемента для добавления.");
                    n = reader.nextInt();
                    tree.addNode(n);
                    System.out.println("Операция исполнена.");
                    break;
                case 7:
                    System.out.println("Введите ключ элемента для удаления.");
                    n = reader.nextInt();
                    tree.removeNode(n);
                    System.out.println("Операция исполнена.");
                    break;
                case 8:
                    iterator = tree.getIterator();
                    while (n != 0) {
                        System.out.println("Введите число для выбора операции производимой итератором.");
                        System.out.println("1 - установка на корень дерева");
                        System.out.println("2 - проверка конца дерева");
                        System.out.println("3 - доступ к данным");
                        System.out.println("4 - переход к следующему узлу дерева");
                        System.out.println("5 - переход к предыдущему узлу дерева");
                        System.out.println("0 - завершение работы итератора");
                        n = reader.nextInt();
                        switch (n) {
                            case 0:
                                break;
                            case 1:
                                iterator.setToRoot();
                                break;
                            case 2:
                                if (iterator.isLast()) {
                                    System.out.println("Итератор установлен на последний элемент дерева.");
                                } else {
                                    System.out.println("Данный элемент не является последним.");
                                }
                                break;
                            case 3:
                                iterator.getData();
                                break;
                            case 4:
                                iterator.goToNext();
                                break;
                            case 5:
                                iterator.goToPrevious();
                                break;
                            default:
                                System.out.println("Введен некорректный идентификатор операции.");
                        }
                        if (n != 0) {
                            System.out.println("Выполнено.\n");
                        }
                    }
                    System.out.println("Операция исполнена.");
                    break;
                case 9:
                    tree.traverseTree();
                    System.out.println("Операция исполнена.");
                    break;
                case 10:
                    System.out.println("Длина внутреннего пути дерева:" + tree.getInternalPathLength());
                    System.out.println("Операция исполнена.");
                    break;
                case 11:
                    System.out.println();
                    tree.showTree();
                    System.out.println("Операция исполнена.");
                    break;
                case 12:
                    System.out.println("Число просмотренных узлов:" + tree.getNumberOfNodesVisitedByOperation());
                    System.out.println("Операция исполнена.");
                    break;
                default:
                    executionInProgress = false;
            }
            System.out.println("Для завершения программы введите 0. ");
            reader.nextLine();
            line = reader.nextLine();
            if (line.equals("0")) {
                break;
            }
            System.out.println();
        }
    }
}
