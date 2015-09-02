package graphcoloring;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Each vertex will have a Linked List of edges.
 *
 * @author Jess Hendricks
 */
public class AdjacencyList
{

    private Vertex head;
    public String vertexColor;

    public AdjacencyList()
    {
        head = new Vertex(-1);
        vertexColor = "White";
    }
    
    /**
     * Adds a single edge to the adjacency list.
     * @param vertexNumberValue 
     */
    public void addEdge(int vertexNumberValue)
    {
        Vertex newVertex = new Vertex(vertexNumberValue);
        newVertex.next = head;
        head = newVertex;
    }
    
    /**
     * The standard Iterator method and class for traversing the Linked List of edges.
     * @return 
     */
    public Iterator<Integer> iterator() 
    {
        return new AdjacencyListIterator();
    }
    
    private class AdjacencyListIterator implements Iterator<Integer>
    {
        private Vertex nextNode;
        
        public AdjacencyListIterator()
        {
            nextNode = head;
        }

        @Override
        public boolean hasNext()
        {
            return nextNode.next != null;
        }

        @Override
        public Integer next()
        {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Integer returnVertexNumber = nextNode.vertexNumber;
            nextNode = nextNode.next;
            return returnVertexNumber;
        }
    }

    /**
     * hasNext
     * @return whether or not there are any edges
     */
    public boolean hasNext() {
        return head.next != null;
    }

    /**
     * To check the next vertex without iterating.
     * @return next vertexNumber
     */
    public int getNext()
    {
        return head.next.vertexNumber;
    }

    /**
     * Returns a string that displays the adjacency list.
     * @return String
     */
    @Override
    public String toString()
    {
        String returnString = "";
        for (Iterator<Integer> it = this.iterator(); it.hasNext();) {
            returnString = returnString + ("-> " + it.next());
            
        }
        return returnString;
    }

    /**
     * A private class for each vertex that maintains the vertex number and a pointer to the next vertex.
     */
    private class Vertex
    {
        Vertex next;
        int vertexNumber;

        public Vertex(int vertexNumberValue)
        {
            next = null;
            vertexNumber = vertexNumberValue;
        }

        public Vertex(int vertexNumberValue, Vertex nextValue)
        {
            next = nextValue;
            vertexNumber = vertexNumberValue;
        }
    }

}
