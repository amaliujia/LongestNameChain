import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Solution class provides the functionality such as preprocess input data, and wrapper of graph operations.
 * @author Rui Wang
 */
public class Solution {
    private Graph graph;
    private Set<Node> nodes;
    private List<Node> nodeSequence;

    public Solution(){
        this.nodes = new HashSet<Node>();
        this.nodeSequence = new ArrayList<Node>();
    }

    /**
     * Read data from input file.
     * @param filepath
     *          The path of input file.
     * @throws IOException
     *          throws when inpuf file cannot open or read error occurs.
     */
    public void processData(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
        String line = null;
        while ((line = reader.readLine()) != null){
            String[] words = line.split(" ");
            if (words.length == 2 || words.length == 3) {
                if (words[words.length - 1].startsWith("(")) {
                    continue;
                }

                // change name to Graph Node.
                Node n = new Node(words[0], words[words.length - 1]);
                if (!this.nodes.contains(n)){
                    this.nodes.add(n);
                    this.nodeSequence.add(n);
                }
            }
        }
    }


    /**
     * Find the longest name chain.
     * @return
     *      string that describes the longest name chain or say infinite length if cycle exists.
     */
    public String getLongestNameChain() {
        // build graph from nodes
        this.graph = new Graph(this.nodeSequence);
        String result = null;

        // detect cycle
        if (this.graph.hasCycle()) {
            result = "The longest name chain has infinite length because of cycle";
            return result;
        }

        // find the topological order of graph
        this.graph.topologicalSort();

        // find the longest path in graph and end vertices.
        int maxDis = -1, startNode = -1, endNode = -1;
        int[] output = new int[2];
        for (int i = 0; i < this.graph.getNumNodes() ; i++) {
            this.graph.longestDistanceFromGivenVertex(i, output);
            if (output[1] > maxDis) {
                maxDis = output[1];
                startNode = i;
                endNode =output[0];
            }
        }

        // generate longest name chain
        result = this.graph.getLongestPath(startNode, endNode, maxDis);
        return result;
    }
}
