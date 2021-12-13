package lapr.project.structures;
/**
 *
 * @author DEI-ESINF
 */
public interface BasicGraphInterface<V,E> {

    /** Returns the number of vertices in the graph
     * @return number of vertices of the graph
     * */
    int numVertices();

    /** Returns the number of edges in the graph
     * @return number of edges of the graph
     * */
    int numEdges();

    /** Returns the actual vertices of the graph
     * @return an iterable collection of vertices
     * */
    Iterable<V> vertices();

    /** Returns the actual edges of the graph
     * @return an iterable collection of all edges
     * */
    Iterable<E> edges();

    /** Inserts a new vertex with the given element.
     * @param newVertex contents
     * @return false if vertex exists
     */
    boolean insertVertex(V newVertex);

    /** Inserts a new edge between two vertices
     * @param va, vb two vertices and the new edge contents
     * @return false if either vertices are not in the graph or an edge already exists between the two.
     */
    boolean insertEdge(V va, V vb, E newEdge);

    /** Removes a vertex and all its incoming/outgoing edges from the graph.
     * @param vertex
     * @return false if vertex does not exist in the graph
     */
    boolean removeVertex(V vertex);

    /** Removes the edge between two vertices
     * @param va vb
     * @return the edge or null if vertices are not in the graph or not connected
     */
    E removeEdge(V va, V vb);

}


