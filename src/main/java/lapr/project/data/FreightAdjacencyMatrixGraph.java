package lapr.project.data;

import lapr.project.structures.CommonGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;

/**
 *
 * @author 1180590
 */

public class FreightAdjacencyMatrixGraph<V, E> extends CommonGraph<V,E> implements Cloneable {

    public static final int INITIAL_CAPACITY = 70;
    public static final float RESIZE_FACTOR = 1.5F;


    FreightEdge<V,E>[][] edgeMatrix;

    /**
     * Resizes the matrix when a new vertex increases the length of ArrayList
     */
    private void resizeMatrix() {
        if(edgeMatrix.length < numVerts){
            int newSize = (int) (edgeMatrix.length * RESIZE_FACTOR);

            @SuppressWarnings("unchecked")
            FreightEdge<V, E>[][] temp = (FreightEdge <V,E>[][]) new FreightEdge<?, ?> [newSize][newSize];
            for (int i = 0; i < edgeMatrix.length; i++)
                temp[i] = Arrays.copyOf(edgeMatrix[i], newSize);

            edgeMatrix = temp;
        }
    }

    /**
     * Constructs an empty graph.
     */
    public FreightAdjacencyMatrixGraph(boolean directed) {
        this(directed, INITIAL_CAPACITY);
    }

    /**
     * Constructs a graph with an initial capacity.
     */
    @SuppressWarnings("unchecked")
    public FreightAdjacencyMatrixGraph(boolean directed, int initialCapacity) {
        super(directed);
        edgeMatrix = (FreightEdge <V,E> [][])( new FreightEdge<?, ?>[initialCapacity][initialCapacity]);
    }

    /**
     * Returns the number of vertices in the graph
     * @return number of vertices of the graph
     */
    public int numVertices() {
        return numVerts;
    }

    /**
     * Returns the number of edges in the graph
     * @return number of edges of the graph
     */
    public int numEdges() {
        return numEdges;
    }


    /**
     * Returns the actual vertices of the graph
     * @return an iterable collection of vertices
     */
    @SuppressWarnings("unchecked")
    public ArrayList<V> vertices() {
        return super.vertices();
    }

    /**
     * Returns the actual edges of the graph
     * @return an iterable collection of all edges
     */
        @Override
    public Collection<FreightEdge<V, E>> edges() {
        ArrayList<FreightEdge<V, E>> edges = new ArrayList<>();
        if(isDirected()){
            for(int i = 0; i<numVerts; i++){
                for(int j = 0; j<numVerts;j++){
                    if(edgeMatrix[j][i] != null){
                        edges.add(edgeMatrix[j][i]);
                    }
                }
            }
        } else {
            for(int i = 0; i<numVerts; i++){
                for(int j = 0; j<=i;j++){
                    if(edgeMatrix[j][i] != null){
                        edges.add(edgeMatrix[j][i]);
                    }
                }
            }
        }
        return edges;
    }

        @Override
    public FreightEdge<V, E> edge(V vOrig, V vDest) {
        int vOrigKey = key(vOrig);
        int vDestKey = key(vDest);

        if ((vOrigKey < 0) || (vDestKey < 0))
            return null;

        return edgeMatrix[vOrigKey][vDestKey];
    }
    
       @Override
    public FreightEdge<V, E> edge(int vOrigKey, int vDestKey) {
        if (vOrigKey >= numVerts && vDestKey >= numVerts)
            return null;
        return edgeMatrix[vOrigKey][vDestKey];
    }
    
    /**
     * Inserts a new vertex with the given element.
     * fails if vertex already exists
     * @param newVertex (vertex contents)
     * @return false if vertex exists in the graph
     */
    public boolean addVertex(V newVertex) {
        int key = key(newVertex);
        if (key != -1)
            return false;

        vertices.add(newVertex);
        numVerts++;
        resizeMatrix();
        return true;
    }

    /**
     * Inserts a new edge between two vertices.
     * Package level method is for use of algorithms class
     * @param indexA
     * @param indexB
     * @param newEdge
     * @return false if vertices are not in the graph or are the same vertex
     *         or an edge already exists between the two.
     */  
     @Override
    public boolean addEdge(V vOrig, V vDest, E weight) {
        if (vOrig == null || vDest == null) throw new RuntimeException("Vertices cannot be null!");
        if (edge(vOrig, vDest) != null)
            return false;

        if (!validVertex(vOrig))
            addVertex(vOrig);

        if (!validVertex(vDest))
            addVertex(vDest);

        int vOrigKey = key(vOrig);
        int vDestKey = key(vDest);

        edgeMatrix[vOrigKey][vDestKey] = new FreightEdge<>(vOrig, vDest, weight );
        numEdges++;
        if (!isDirected) {
            edgeMatrix[vDestKey][vOrigKey] = new FreightEdge<>(vDest, vOrig, weight );
            numEdges++;
        }
        return true;
    }

    /**
     * Removes a vertex and all its incoming/outgoing edges from the graph.
     *
     * @param vertex vertex
     * @return false if vertex does not exist in the graph
     */
    @Override
    public boolean removeVertex(V vert) {
        int vertKey = key(vert);
        if (vertKey == -1)
            return false;

        // first let's remove edges from the vertex
        for (int i = 0; i < numVerts; i++)
                removeEdge(vertKey,i);
        if (isDirected) {
            // first let's remove edges to the vertex
            for (int i = 0; i < numVerts; i++)
                removeEdge(i, vertKey);
        }

        // remove shifts left all vertices after the one removed
        // It is necessary to collapse the edge matrix        
        for (int i = vertKey; i < numVerts - 1; i++) {
            for (int j = 0; j < numVerts; j++) {
                edgeMatrix[i][j] = edgeMatrix[i + 1][j];
            }
        }
        for (int i = vertKey; i < numVerts - 1; i++) {
            for (int j = 0; j < numVerts; j++) {
                edgeMatrix[j][i] = edgeMatrix[j][i + 1];
            }
        }
        for (int j = 0; j < numVerts; j++) {
            edgeMatrix[j][numVerts - 1] = null;
            edgeMatrix[numVerts - 1][j] = null;
        }

        vertices.remove(vert);
        numVerts--;
        return true;
    }

    /**
     * Removes the edge between two vertices
     * Package level method is for use of algorithms class
     *
     * @param indexA is the vertex 1
     * @param indexB is the vertex 1
     * @return the edge or null if vertices are not in the graph or not
     *         connected
     */
    private void removeEdge(int vOrigKey, int vDestKey) {
        if (edgeMatrix[vOrigKey][vDestKey] != null) {
            edgeMatrix[vOrigKey][vDestKey] = null;
            numEdges--;
        }
        if (!isDirected && (edgeMatrix[vDestKey][vOrigKey] != null)) {
            edgeMatrix[vDestKey][vOrigKey] = null;
            numEdges--;
        }
    }

    @Override
    public boolean removeEdge(V vOrig, V vDest) {
        int vOrigKey = key(vOrig);
        int vDestKey = key(vDest);

        if ((vOrigKey < 0) || (vDestKey < 0) || (edgeMatrix[vOrigKey][vDestKey] == null))
            return false;

        removeEdge(vOrigKey,vDestKey);
        return true;
    }

    @Override
    public int inDegree(V vert) {
        int vertKey = key(vert);
        if (vertKey == -1)
            return -1;

        int edgeCount = 0;
        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[i][vertKey] != null)
                edgeCount++;
        return edgeCount;
    }
    
        @Override
    public Collection<FreightEdge<V, E>> incomingEdges(V vert) {
        Collection <FreightEdge<V, E>> ce = new ArrayList<>();
        int vertKey = key(vert);
        if (vertKey == -1)
            return ce;

        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[i][vertKey] != null)
                ce.add(edgeMatrix[i][vertKey]);
        return ce;
    }
    
    public Collection<FreightEdge<V, E>> outgoingEdges(V vert) {
        Collection <FreightEdge<V, E>> ce = new ArrayList<>();
        int vertKey = key(vert);
        if (vertKey == -1)
            return ce;
        
        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[vertKey][i] != null)
                ce.add(edgeMatrix[vertKey][i]);
        return ce;
    }
    
     @Override
    public Collection<V> adjVertices(V vert) {
        int index = key(vert);
        if (index == -1)
            return null;

        ArrayList<V> outVertices = new ArrayList<>();
        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[index][i] != null)
                outVertices.add(vertices.get(i));
        return outVertices;
    }
    
     public void shortestPathDijkstraFreight(V vOrig,Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        boolean[] visited, V [] pathKeys, E [] dist) {
        int vkey = key(vOrig);
        dist[vkey] = zero;
        pathKeys[vkey] = vOrig;
        while (vOrig != null) {
            vkey = key(vOrig);
            visited[vkey] = true;
            for (FreightEdge<V, E> edge : outgoingEdges(vOrig)) {
                int vkeyAdj = key(edge.getVDest());
                if (!visited[vkeyAdj]) {
                    E s = sum.apply(dist[vkey], edge.getWeight());
                    if (dist[vkeyAdj] == null || ce.compare(dist[vkeyAdj], s) > 0) {
                        dist[vkeyAdj] = s;
                        pathKeys[vkeyAdj] = vOrig;
                    }
                }
            }
            E minDist = null;  //next vertice, that has minimum dist
            vOrig = null;
            for (V vert : vertices()) {
                int i = key(vert);
                if (!visited[i] && (dist[i] != null) && ((minDist == null) || ce.compare(dist[i], minDist) < 0)) {
                    minDist = dist[i];
                    vOrig = vert;
                }
            }
        }
    }
     /**
     * Returns a string representation of the graph.
     * Matrix only represents existence of Edge 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vertices:\n");
        for (int i = 0 ; i < numVerts ; i++)
            sb.append(vertices.get(i)+"\n");

        sb.append("\nMatrix:\n");

        sb.append("  ");
        for (int i = 0 ; i < numVerts ; i++)
        {
            sb.append(" |  "+ i + " ");
        }
        sb.append("\n");

        // aligned only when vertices < 10
        for (int i = 0 ; i < numVerts ; i++)
        {
            sb.append(" "+ i + " ");
            for (int j = 0 ; j < numVerts ; j++)
                if(edgeMatrix[i][j] != null)
                    sb.append("|  X  ");
                else
                    sb.append("|     ");
            sb.append("\n");
        }

        sb.append("\nEdges:\n");

        for (int i = 0; i < numVerts ; i++)
            for (int j = 0 ; j < numVerts; j++)
                if (edgeMatrix[i][j] != null)
                    sb.append("From " + i + " to " + j + "-> "+ edgeMatrix[i][j] + "\n");

        sb.append("\n");

        return sb.toString();
    }
    
    /**
     * Returns a clone of the graph (a shallow copy).
     *
     * @return the new cloned graph
     */

    @SuppressWarnings("unchecked")
    public FreightAdjacencyMatrixGraph<V, E> clone() {
        
        
        FreightAdjacencyMatrixGraph<V, E> g = new FreightAdjacencyMatrixGraph<>(this.isDirected, this.edgeMatrix.length);

        copy(this,g);

        return g;
    }

    /**
     * Implementation of equals
     * @param oth other graph to test for equality
     * @return true if both objects represent the same graph
     */

    public boolean equals(Object oth) {
        if(this == oth) return true;
        
        if(oth == null) return false;

        if (!(oth instanceof FreightAdjacencyMatrixGraph<?, ?>))
            return false;

        FreightAdjacencyMatrixGraph<?, ?> other = (FreightAdjacencyMatrixGraph<?, ?>) oth;

        if(numVerts != other.numVerts || numEdges != other.numEdges) return false;

        if(!vertices.equals(other.vertices)) return false;
        
        if(!Arrays.deepEquals(edgeMatrix,other.edgeMatrix))
            return false;

        return true;
    }

}