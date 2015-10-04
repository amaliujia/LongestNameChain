import java.util.*;

/**
 * @author Rui Wang
 *
 * Graph class is used for modeling the name chain problem. This class provides functionalities
 * such as detecting cycle, topological sort, and finding longest path between two given nodes.
 */
public class Graph {
    private int numNodes;
    private List<ArrayList<Integer>> edges;
    private Map<Integer, Node> nodes;
    private Stack<Integer> stack;

    /**
     * Constructor.
     * @param nodeSequence
     *          A sequence of node class that saves names.
     */
    public Graph(List<Node> nodeSequence){
        this.numNodes = nodeSequence.size();
        this.nodes = new HashMap<Integer, Node>();
        this.edges = new ArrayList<ArrayList<Integer>>();

        // alloc space for graph adjacent matrix.
        for (int i = 0; i < nodeSequence.size(); i++) {
            this.nodes.put(i, nodeSequence.get(i));
            edges.add(new ArrayList<Integer>());
        }

        // find the edges from nodes, add such relationship into matrix.
        for (int i = 0; i < nodeSequence.size(); i++) {
            for (int j = 0; j < nodeSequence.size(); j++){
                if (i != j && canFormNameChain(nodeSequence.get(i), nodeSequence.get(j))) {
                    addEdge(i, j);
                }
            }
        }
    }

    /**
     * Do topological sort on current graph, save topological order in stack. Top of the stack
     * is the first node in topological order.
     */
    public void topologicalSort() {
        this.stack = new Stack<Integer>();
        boolean[] visited = new boolean[this.numNodes];

        for (int i = 0; i < this.numNodes; i++){
            // use DFS approach on each unvisited vertex.
            if (visited[i] == false) {
                this.topologicalSortUtil(i, visited, stack);
            }
        }
    }

    /**
     * Detect cycle on current graph.
     * @return
     *      true if having cycle, false if not having cycle.
     */
    public boolean hasCycle() {
        boolean[] visited = new boolean[this.numNodes];
        boolean[] trackStack = new boolean[this.numNodes];

        for (int i = 0; i < this.numNodes; i++) {
            if (hasCycleUtil(i, visited, trackStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find the longest distance in graph from the given vertex.
     * @param v
     *          The row index of start vertex in graph matrix.
     * @param output
     *          Output is a two dimension array used for passing return value.
     *          output[0] is the index of end vertex for longest distances.
     *          output[1] is the longest distance.
     */
    public void longestDistanceFromGivenVertex(int v, int[] output) {
        //Stack<Integer> copyStack = (Stack<Integer>) this.stack.clone();
        Stack<Integer> copyStack = new Stack<Integer>();
        copyStack.addAll(this.stack);
        // alloc a distance arry to save the longest distance from one vertex
        // to start vertex given as parameter. Initialize dist[start vertex] = 0
        // indicates the start vertex. Initialize dist[other vertex] = -1 and wait
        // for updating.
        int[] dist = new int[this.numNodes];
        for (int i = 0; i < this.numNodes; i++) {
            if (i == v) {
                dist[v] = 0;
            } else {
                dist[i] = -1;
            }
        }

        // Use BFS approach to update distance array.
        while (!copyStack.isEmpty()) {
            // Get the next vertex from topological order
            int cur = copyStack.peek();
            copyStack.pop();

            // Update distances of all adjacent vertices
            if (dist[cur] != -1) {
                for (int neighbor : this.edges.get(cur)) {
                    if (dist[neighbor] < dist[cur] + 1) {
                        dist[neighbor] = dist[cur] + 1;
                    }
                }
            }
        }

        // find the vertex to which I get the longest distance from start vertex.
        int maxValue = Integer.MIN_VALUE;
        int maxNode = -1;

        for (int i = 0; i < dist.length; i++) {
            if (maxValue < dist[i]) {
                maxValue = dist[i];
                maxNode = i;
            }
        }

        // save final result input output array.
        output[0] = maxNode;
        output[1] = maxValue;
    }

    /**
     * Find the longest path, when given the start vertex, end vertext and the
     * longest distance between these two vertices.
     * @param start
     *          index of start vertex.
     * @param end
     *          index of end vertex.
     * @param depth
     *          the longest distance between start and end vertex (aka the depth that DFS tree should have).
     * @return
     *          A string that contains the names in the longest path (aka longest name chain).
     *          One example is "Leon Allen Williams".
     */
    public String getLongestPath(int start, int end, int depth) {
        Stack<Integer> trackStack = new Stack<Integer>();
        trackStack.push(start);

        for (int i : this.edges.get(start)) {
            if (getLongestPathUtil(i, end, depth, 1, trackStack)) {
                break;
            }
        }

        int cur = -1;
        String result = "";
        while (!trackStack.isEmpty()) {
            cur = trackStack.peek();
            trackStack.pop();
            result = " " + this.nodes.get(cur).getLastname() + result;
        }
        result = this.nodes.get(cur).getFirstname() + result;
        return result;
    }

    public int getNumNodes() {
        return this.numNodes;
    }

    public Stack<Integer> getStack() {
        return this.stack;
    }

    /**
     * Util function is recursively being called for finding the longest path. This is a function
     * that help finish DFS.
     *
     * The termination of the recursion is either it finds the end vertex or current depth of DFS is
     * equal or greater than estimate depth.
     * @param curNode
     *          index of current vertex that is being processed.
     * @param end
     *          index of destination (end vertex).
     * @param depth
     *          the limit on DFS depth.
     * @param curDepth
     *          current DFS depth.
     * @param trackStack
     *          a stack that help record current path of DFS.
     * @return
     *          return true when find the longest path.
     *          return false when fail to find the path.
     */
    private boolean getLongestPathUtil(int curNode, int end, int depth, int curDepth, Stack<Integer> trackStack) {
        trackStack.push(curNode);

        if (curDepth == depth && curNode == end) {
            return true;
        }

        if (curDepth > depth || curDepth == depth || curNode == end) {
            trackStack.pop();
            return false;
        }

        for (int i : this.edges.get(curNode)) {
            if (getLongestPathUtil(i, end, depth, curDepth + 1, trackStack)) {
                return true;
            }
        }
        trackStack.pop();
        return false;
    }


    /**
     *  Util function is recursively being called for finding cycle of graph.
     *
     * The termination of the recursion is either it finds a cycle in graph or it finishes DFS traversal
     * on current vertex.
     * @param v
     *          index of current vertex.
     * @param visited
     *          boolean array for recording visited information.
     * @param trackStack
     *          boolean array for recording visits on current path.
     * @return
     *          return true if detecting cycle.
     *          return false if not detecting cycle.
     */
    private boolean hasCycleUtil(int v, boolean[] visited, boolean[] trackStack) {
        if (visited[v] == false) {
            visited[v] = true;
            trackStack[v] = true;

            for (int i : this.edges.get(v)) {
                if (visited[i] == false && hasCycleUtil(i, visited, trackStack)) {
                    return true;
                } else if (trackStack[i]) {
                    return true;
                }
            }
        }

        trackStack[v] = false;
        return false;
    }

    /**
     * Util function is recursively being called for finding topological order.
     *
     * @param v
     *          index of current vertex.
     * @param visited
     *          boolean array for recording visited information.
     * @param stack
     *          stack used for saving the partial order.
     */
    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;

        for (int i : this.edges.get(v)) {
            if (visited[i] == false) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        stack.push(v);
    }

    /**
     * Add edge node1 ---> node2 to graph.
     * @param node1
     *          index of node1.
     * @param node2
     *          index of node2.
     */
    private void addEdge(int node1, int node2){
        edges.get(node1).add(node2);
    }

    /**
     * Given two nodes, if they can form a name chain.
     * @param n1
     *          First node.
     * @param n2
     *          Second node.
     * @return
     *         return true if second node's firstname is equal to first node's lastname.
     *         return false if statement above doesn't hold.
     */
    private boolean canFormNameChain(Node n1, Node n2){
        if (n1.getLastname().equals(n2.getFirstname())) {
            return true;
        }

        return false;
    }
}
