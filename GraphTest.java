import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Rui Wang
 */
public class GraphTest {
    public int pass = 0;
    public int fail = 0;

    public static void addNode(List<Node> list, String firstname, String lastname) {
        list.add(new Node(firstname, lastname));
    }

    /**
     * Verify if hasCycle function work.
     *
     * Testcase
     *         {'a', 'b'} {'b', 'c'} {'c', 'a'}
     * Expected output
     *      true (cycle exit)
     */
    public void VerifyHasCycle(){
        List<Node> testList = new ArrayList<Node>();
        GraphTest.addNode(testList, "a", "b");
        GraphTest.addNode(testList, "b", "c");
        GraphTest.addNode(testList, "c", "a");
        Graph graph = new Graph(testList);

        if (graph.hasCycle()) {
            pass++;
            System.out.println("    Pass HasCycle test");
        } else {
            fail++;
            System.out.println("    Fail HasCycle test");
        }

    }

    /**
     * Verify if hasCycle function work.
     *
     * Testcase
     *         {'a', 'b'} {'b', 'c'} {'c', 'd'}
     * Expected output
     *      false (cycle doesn't exit)
     */
    public void VerifyNotHasCycle(){
        List<Node> testList = new ArrayList<Node>();
        GraphTest.addNode(testList, "a", "b");
        GraphTest.addNode(testList, "b", "c");
        GraphTest.addNode(testList, "c", "d");
        Graph graph = new Graph(testList);

        if (!graph.hasCycle()) {
            pass++;
            System.out.println("    Pass NotHasCycle test");
        } else {
            fail++;
            System.out.println("    Fail NotHasCycle test");
        }
    }

    /**
     * Verify if topological sort work.
     *
     * Testcase
     *         {'a', 'b'} {'a', 'c'} {'e', 'd'} {'b', 'd'} {'c', 'f'}
     * Expected output
     *      'e d', 'a c', 'c f', 'a b', 'b d'.
     */
    public void VerifyTopologicalSort() {
        List<Node> testList = new ArrayList<Node>();
        GraphTest.addNode(testList, "a", "b");
        GraphTest.addNode(testList, "a", "c");
        GraphTest.addNode(testList, "e", "d");
        GraphTest.addNode(testList, "b", "d");
        GraphTest.addNode(testList, "c", "f");

        Graph graph = new Graph(testList);
        graph.topologicalSort();

        Stack<Integer> stack = graph.getStack();
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()) {
            builder.append(stack.peek());
            stack.pop();
        }

        if (builder.toString().equals("21403")) {
            pass++;
            System.out.println("    Pass topological sort test");
        } else {
            fail++;
            System.out.println("    Fail topological sort test");
        }
    }

    /**
     * Verify if find the longest distance to a given vertex work.
     *
     * Testcase
     *         {'a', 'b'} {'b', 'c'} {'c', 'd'} {'e', 'f'} {'f', 'g'}
     * Expected output
     *      end vertex is 2, maximum distance is 2 (Maximum distance from node 0 to node 2 is 2)
     */
    public void VerifyLongestDistanceGivenOneVertex() {
        List<Node> testList = new ArrayList<Node>();
        GraphTest.addNode(testList, "a", "b");
        GraphTest.addNode(testList, "b", "c");
        GraphTest.addNode(testList, "c", "d");
        GraphTest.addNode(testList, "e", "f");
        GraphTest.addNode(testList, "f", "g");

        Graph graph = new Graph(testList);
        int[] output = new int[2];
        graph.topologicalSort();
        graph.longestDistanceFromGivenVertex(0, output);

        if (output[0] == 2 && output[1] == 2) {
            pass++;
            System.out.println("    Pass longest distance for given vertex");
        } else {
            fail++;
            System.out.println("    Fail longest distance for given vertex");
        }
    }

    /**
     * Verify if find the longest path (aka longest name chain) between two given vertices work.
     *
     * Testcase
     *         {'a', 'b'} {'b', 'c'} {'c', 'd'} {'e', 'f'} {'f', 'g'}
     * Expected output
     *      a b c d (longest name chain).
     */
    public void VerifyLongestPath() {
        List<Node> testList = new ArrayList<Node>();
        GraphTest.addNode(testList, "a", "b");
        GraphTest.addNode(testList, "b", "c");
        GraphTest.addNode(testList, "c", "d");
        GraphTest.addNode(testList, "e", "f");
        GraphTest.addNode(testList, "f", "g");

        Graph graph = new Graph(testList);
        int[] output = new int[2];
        graph.topologicalSort();
        graph.longestDistanceFromGivenVertex(0, output);
        String result = graph.getLongestPath(0, output[0], output[1]);
        if (result.equals("a b c d")) {
            pass++;
            System.out.println("    Pass longest path between two vertex test");
        } else {
            fail++;
            System.out.println("    Fail longest path between two vertex test");
        }
    }

    public static void main(String[] args) {
        GraphTest test = new GraphTest();
        System.out.println("-----------------Test graph----------------");
        test.VerifyHasCycle();
        test.VerifyNotHasCycle();
        test.VerifyTopologicalSort();
        test.VerifyLongestDistanceGivenOneVertex();
        test.VerifyLongestPath();
        System.out.println("-----------------End graph test----------------");
        System.out.println("Pass test " + test.pass  + "/" + (test.pass + test.fail));
        System.out.println("Fail test " + test.fail  + "/" + (test.pass + test.fail));
    }
}
