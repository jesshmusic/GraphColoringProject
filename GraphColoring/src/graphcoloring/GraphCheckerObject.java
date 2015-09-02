package graphcoloring;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Jess Hendricks
 */
public class GraphCheckerObject
{

    private final boolean debugMode = false;

    private AdjacencyList[] graph;
    private String outputFileString;
    private LinkedList<Integer> cycleContainer;
    private boolean isOddCycle;
    private boolean breakDFS;
    private int oddCycleStart;
    private int numberOfVertices;
    private int cycleSize;

    // Testing Code (keep track of edges
    private LinkedList<String> cycleEdgeList;

    public GraphCheckerObject(String inputFileNameString, String outputFileNameString)
            throws URISyntaxException, IOException
    {
        loadGraph(inputFileNameString, outputFileNameString);
    }

    /**
     * Loads the graph from an external file
     *
     * @param fileString Name of the file
     * @param outputFileString Output file name
     * @throws URISyntaxException
     * @throws IOException
     *
     * Run time: O(|E|)
     *
     */
    public void loadGraph(String fileString, String outputFileString)
            throws URISyntaxException, IOException
    {
        this.outputFileString = outputFileString;
        Scanner s = new Scanner(new File(fileString));
        numberOfVertices = 0;
        if (s.hasNext()) {
            numberOfVertices = s.nextInt() + 1;
        }
        graph = new AdjacencyList[numberOfVertices];

        for (int i = 1; i < numberOfVertices; i++) {
            graph[i] = new AdjacencyList();
        }

        while (s.hasNext()) {
            int vertexU = Integer.parseInt(s.next());
            int vertexV = Integer.parseInt(s.next());
            graph[vertexU].addEdge(vertexV);
            graph[vertexV].addEdge(vertexU);
        }
        s.close();

        checkGraphColoring(fileString);
    }
    
    /**
     * Checks the loaded graph for two-colorability by performing a modified DFS
     *
     * @param fileString
     * 
     * Run time: O()
     */
    public void checkGraphColoring(String fileString)
    {
        System.out.println("\n\n________________________________\nChecking: "
                + fileString + "\n________________________________\n");
        if (debugMode) {
            for (int i = 0; i < graph.length; i++) {
                if (graph[i] != null) {
                    System.out.println(i + ":  " + graph[i].toString());
                }
            }
        }
        cycleContainer = new LinkedList<>();
        cycleSize = 0;
        isOddCycle = false;
        breakDFS = false;

        // Testing Code (keep track of edges
        cycleEdgeList = new LinkedList<>();

        this.dfsColorGraph();

    }

    /**
     * Write the cycle to a file
     */
    private void saveCycleVertexList()
    {
        try {
            FileWriter fileWriter = new FileWriter(outputFileString);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the first line of the file as the number of vertices in the cycle.
//            bufferedWriter.write("" + cycleSize + "\n");

            //Write each line as an edge in the cycle
            for (Integer cycleVertex : cycleContainer) {
                bufferedWriter.write(cycleVertex + "\n");
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + outputFileString + "'");
        }
    }

    /**
     * Performs a depth-first-search of the entire graph. If a node is "White",
     * meaning unvisited, then dfsVisit is called. The first node in each tree
     * is assigned "Red" If an odd cycle is detected, the pertinent information
     * is recorded and displayed
     */
    private void dfsColorGraph()
    {
        for (int u = 1; u < graph.length; u++) {
            if (graph[u].vertexColor.equals("White")) {
                this.dfsVisit(u, "Red", 0);
            }
            if (isOddCycle) {
                System.out.println("------> ODD CYCLE DETECTED Starting at vertex: "
                        + oddCycleStart
                        + " <-------"
                        + "\n------>  Cycle Size: " + cycleSize);
                if (cycleSize <= 50) {
                    System.out.println("------>  Vertex: " + cycleContainer.toString());
                    saveCycleVertexList();
                    // Testing Code (keep track of edges 
                    System.out.println(cycleEdgeList.toString());
                } else {
                    saveCycleVertexList();
                }
                break;
            }
        }
        if (!isOddCycle) {
            System.out.println("\nGraph is two-colorable!");
        } else {
            System.out.println("\nGraph is NOT two-colorable!");
            if (debugMode) {
                if (isValidCycle()) {
                    System.out.println("\n.......Detected Cycle is valid.");
                    System.out.println(".......Number of edges: " + cycleEdgeList.size());
                } else {
                    System.err.println("\n.......Detected Cycle is INVALID.");
                }
            }
        }

    }

    /**
     * Recursive call to visit a vertex during a depth-first-search.
     *
     * @param u node to visit
     * @param parentColorString color of the parent node
     * @param parentNode number of the parent node (for cycle testing)
     */
    private void dfsVisit(int u, String parentColorString, int parentNode)
    {
        if (parentColorString.equals("Red")) {
            graph[u].vertexColor = "Blue";
        } else {
            graph[u].vertexColor = "Red";
        }

        if (debugMode) {
            System.out.println("Visiting Vertex: " + u);
        }

        for (Iterator<Integer> it = graph[u].iterator(); it.hasNext();) {
            int v = it.next();
            if (graph[v].vertexColor.equals("White") && !isOddCycle) {
                this.dfsVisit(v, graph[u].vertexColor, u);
            } else if (!isOddCycle && graph[v].vertexColor.equals(graph[u].vertexColor)) {
                oddCycleStart = v;
                addCycleEdge(u, v);
                isOddCycle = true;
            }
        }
        if (isOddCycle && !breakDFS) {
            cycleContainer.push(u);
            cycleSize++;

            // Testing Code (keep track of edges
            if (u != oddCycleStart) {
                addCycleEdge(parentNode, u);
            }
        }
        if (u == oddCycleStart) {
            breakDFS = true;
        }
        if (numberOfVertices < 100) {
            System.out.println("Vertex " + u + " color:  \t" + graph[u].vertexColor);
        }
    }

    /**
     * Adds an edge of a cycle to a linked list to confirm its status as a cycle
     *
     * @param parentNode
     * @param u
     */
    private void addCycleEdge(int parentNode, int u)
    {
        // Testing Code (keep track of edges
        String cycleEdge = parentNode + " " + u;
        cycleEdgeList.push(cycleEdge);
    }

    /**
     * Checks the discovered cycle to ensure that: 1) All vertices are actually
     * connected to their neighbors. 2) The number of edges are odd.
     *
     * @return valid or not valid
     */
    private boolean isValidCycle()
    {
        boolean returnVal = true;
        int fromVertex;
        int toVertex;
        String edgeString[] = cycleEdgeList.getFirst().split("\\s+");
        int firstVertex = Integer.parseInt(edgeString[0]);
        fromVertex = Integer.parseInt(edgeString[1]);
        for (int index = 1; index < cycleEdgeList.size(); index++) {
            edgeString = cycleEdgeList.get(index).split("\\s+");
            toVertex = Integer.parseInt(edgeString[0]);
            if (toVertex != fromVertex) {
                returnVal = false;
                break;
            }
            fromVertex = Integer.parseInt(edgeString[1]);
        }
        if (fromVertex != firstVertex) {
            returnVal = false;
        }
        if (cycleEdgeList.size() % 2 == 0) {
            returnVal = false;
        }
        return returnVal;
    }
}
