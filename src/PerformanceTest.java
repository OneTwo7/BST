import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Arrays.sort;

public class PerformanceTest {
    static private int getSum (int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    static public void main (String[] args) throws IOException {
        PrintWriter pw = null;

        BST tree = new BST();
        int numOfNodes = 10000;
        int max = numOfNodes > 100 ? numOfNodes : 100;
        String type = "double";
        OperationsTest.fillTree(tree, numOfNodes, type);
        int len = 1000000;
        int[] result = new int[len];
        int key;
        int idx = 0;
        Node node;
        while (idx < len) {
            node = null;
            while (node == null) {
                key = ThreadLocalRandom.current().nextInt(0, max);
                node = tree.findNode(key);
            }
            result[idx] = tree.getNumberOfNodesVisitedByOperation();
            idx++;
        }
        try {
            pw = new PrintWriter("../find.txt");
            sort(result);
            for (int i = 0; i < len; i++) {
                if (i % 1000 == 0) {
                    pw.append(Integer.toString(result[i])).append("\n");
                }
            }
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        int sum = getSum(result);
        double averageFind = (double) sum / len;

        idx = 0;
        while (idx < len) {
            key = ThreadLocalRandom.current().nextInt(0, max);
            tree.addNode(key);
            result[idx] = tree.getNumberOfNodesVisitedByOperation();
            tree.removeNode(key);
            idx++;
        }
        try {
            pw = new PrintWriter("../add.txt");
            sort(result);
            for (int i = 0; i < len; i++) {
                if (i % 1000 == 0) {
                    pw.append(Integer.toString(result[i])).append("\n");
                }
            }
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        sum = getSum(result);
        double averageAdd = (double) sum / len;

        idx = 0;
        while (idx < len) {
            key = ThreadLocalRandom.current().nextInt(0, max);
            tree.removeNode(key);
            result[idx] = tree.getNumberOfNodesVisitedByOperation();
            tree.addNode(key);
            idx++;
        }
        try {
            pw = new PrintWriter("../remove.txt");
            sort(result);
            for (int i = 0; i < len; i++) {
                if (i % 1000 == 0) {
                    pw.append(Integer.toString(result[i])).append("\n");
                }
            }
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        sum = getSum(result);
        double averageRemove = (double) sum / len;

        System.out.println("Средняя трудоемкость операции поиска: " + averageFind);
        System.out.println("Средняя трудоемкость операции вставки: " + averageAdd);
        System.out.println("Средняя трудоемкость операции удаления: " + averageRemove);
    }
}
