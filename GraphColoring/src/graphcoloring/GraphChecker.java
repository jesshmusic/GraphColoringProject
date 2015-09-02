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
public class GraphChecker
{

    private AdjacencyList[] graph;
    private String outputFileString;
    private LinkedList<Integer> cycleList;
    private LinkedList<String> coloringList;
    private boolean isOddCycle;
    private boolean breakDFS;
    private int oddCycleStart;
    private int numberOfVertices;
    private int cycleSize;

    private static GraphChecker instance = new GraphChecker();

    private GraphChecker()
    {
    }

    public static GraphChecker getInstance()
    {
        return instance;
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
    public void checkGraphColoring(String fileString, String outputFileString)
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

        checkGraphColoringDFS(fileString);
    }

    /**
     * Checks the loaded graph for two-colorability by performing a modified DFS
     *
     * @param fileString
     *
     * Run time: O(|V|+|E|) for dfsColorGraoh.
     */
    private void checkGraphColoringDFS(String fileString)
    {
        System.out.println("\n\n________________________________\nChecking: "
                + fileString + "\n________________________________\n");
        cycleList = new LinkedList<>();
        coloringList = new LinkedList<>();
        cycleSize = 0;
        isOddCycle = false;
        breakDFS = false;

        this.dfsColorGraph();

    }

    /**
     * Write the cycle to a file
     *
     * Run time: O(|V|) worst case if the entire graph is a cycle.
     */
    private void saveCycleVertexList()
    {
        try {
            FileWriter fileWriter = new FileWriter(outputFileString);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the first line of the file as the number of vertices in the cycle.
//            bufferedWriter.write("" + cycleSize + "\n");
            //Write each line as an edge in the cycle
            for (Integer cycleVertex : cycleList) {
                bufferedWriter.write(cycleVertex + "\n");
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + outputFileString + "'");
        }
    }

    /**
     * Write the coloring to a file
     */
    private void saveGraphColoring()
    {
        try {
            FileWriter fileWriter = new FileWriter(outputFileString);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the first line of the file as the number of vertices in the cycle.
//            bufferedWriter.write("" + cycleSize + "\n");
            //Write each line as an edge in the cycle
            for (String vertex : coloringList) {
                bufferedWriter.write(vertex + "\n");
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
     *
     *
     * Run Time: O(|V|+|E|)
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
                    System.out.println("------>  Vertex: " + cycleList.toString());
                    outputFileString = "CYCLE-"+outputFileString;
                    saveCycleVertexList();
                } else {
                    outputFileString = "CYCLE-"+outputFileString;
                    saveCycleVertexList();
                }
                break;
            }
        }
        if (!isOddCycle) {        
            if (coloringList.size() <= 50) {
            for (String vertexString : coloringList) {
                System.out.println(vertexString);
            }
        }
        this.saveGraphColoring();
            System.out.println("\nGraph is two-colorable!");
        } else {
            System.out.println("\nGraph is NOT two-colorable!");
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
        for (Iterator<Integer> it = graph[u].iterator(); it.hasNext();) {
            int v = it.next();
            if (graph[v].vertexColor.equals("White") && !isOddCycle) {
                this.dfsVisit(v, graph[u].vertexColor, u);
            } else if (!isOddCycle && graph[v].vertexColor.equals(graph[u].vertexColor)) {
                oddCycleStart = v;
                isOddCycle = true;
            }
        }
        if (isOddCycle && !breakDFS) {
            cycleList.push(u);
            cycleSize++;
        }
        if (u == oddCycleStart) {
            breakDFS = true;
        }
        
        //Add the coloring to the list for output
        coloringList.add("Vertex " + u + " color:  \t" + graph[u].vertexColor);
    }
}
