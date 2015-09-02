## Graph Coloring Project

This is an exercise in finding an efficient algorithm that takes in a file containing the edges of an undirected graph and finds out it is [two-colorable](https://en.wikipedia.org/wiki/Bipartite_graph).
I chose to use a modified [Depth-First-Search]() that, instead of keeping track of start and finish times, assigns each vertex a color that is the *opposite* of its parent.
When the end of the recursive DFS-Visit is reached, it must be checked for a cycle. This is accomplished by looking at the next outgoing edge to see if the vertex has the same color. If it does, that is the start of the odd length cycle. The number of that vertex is stored and, as the recursion peels back, each subsequent vertex is added to a linked list of vertices in the odd cycle. When the origin vertex of the odd cycle is reached, the recursion is stopped and the list of vertices is written to a file. If the algorithm completes with- out finding any odd cycles, then the graph is two-colorable and it is output showing the coloring created by the algorithm.

## Running the project from Terminal/Command Line

Provide code examples and explanations of how to get the project.

