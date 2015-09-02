## Graph Coloring Project

This is an exercise in finding an efficient algorithm that takes in a file containing the edges of an undirected graph and finds out it is [two-colorable](https://en.wikipedia.org/wiki/Bipartite_graph).
I chose to use a modified [Depth-First-Search](https://en.wikipedia.org/wiki/Depth-first_search) that, instead of keeping track of start and finish times, assigns each vertex a color that is the *opposite* of its parent.
When the end of the recursive DFS-Visit is reached, it must be checked for a cycle. This is accomplished by looking at the next outgoing edge to see if the vertex has the same color. If it does, that is the start of the odd length cycle. The number of that vertex is stored and, as the recursion peels back, each subsequent vertex is added to a linked list of vertices in the odd cycle. When the origin vertex of the odd cycle is reached, the recursion is stopped and the list of vertices is written to a file. If the algorithm completes with- out finding any odd cycles, then the graph is two-colorable and it is output showing the coloring created by the algorithm.

## Run Time Analysis
If *V* is the number of vertices and *E* is the number of edges, this algorithm runs at O(|*V*|+|*E*|).

## Running the project from Terminal/Command Line

This program can be run from an IDE such as Eclipse or Netbeans. If that is done, the VM flag, `Xss25m` must be set in the run/build configuration.
After it is built, it can be run from the command line:
`$ java Xss25m -jar GraphColoring.jar <Insert filename here>`

If no filename is provided as an argument, it will use the three sample files.

