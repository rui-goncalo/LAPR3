package lapr.project.ui;

import lapr.project.model.PortInfo;
import lapr.project.structures.Edge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.BinaryOperator;

public class GraphDijkstra<V extends Comparable<V>, E>  {
    private ArrayList<V> vertices = new ArrayList<>();
    private ArrayList<Edge<V, E>> edges = new ArrayList<>();

    private static <V extends Comparable<V>, E> void shortestPathDijkstra(GraphDijkstra<V, E> g, V vOrig,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    boolean[] visited, V [] pathKeys, E [] dist) {
        int vkey = g.key(vOrig);
        dist[vkey] = zero;
        pathKeys[vkey] = vOrig;
        while (vOrig != null) {
            vkey = g.key(vOrig);
            visited[vkey] = true;
            for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
                int vkeyAdj = g.key(edge.getVDest());
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
            for (V vert : g.vertices()) {
                int i = g.key(vert);
                if (!visited[i] && (dist[i] != null) && ((minDist == null) || ce.compare(dist[i], minDist) < 0)) {
                    minDist = dist[i];
                    vOrig = vert;
                }
            }
        }
    }

    private ArrayList<Edge<V,E>> outgoingEdges(V vOrig) {
        ArrayList<Edge<V, E>> outgoing = new ArrayList<>();
        for (Edge<V, E> edge : this.edges) {
            if (edge.getVOrig().compareTo(vOrig) == 0) {
                outgoing.add(edge);
            }
        }
        return outgoing;
    }

    private static <V, E> void initializePathDist(int nVerts, V [] pathKeys, E[] dist) {
        for (int i = 0; i < nVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }
    }

    public String shortestPath(GraphDijkstra<V, E> g, V vOrig, V vDest,
                                                              Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                              LinkedList<V> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        shortPath.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts]; //default value: false
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Comparable[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object [numVerts];
        initializePathDist(numVerts, pathKeys, dist);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        E lengthPath = dist[g.key(vDest)];

        if (lengthPath != null) {
            getPath(g, vOrig, vDest, pathKeys, shortPath);
            String path = "";
            int counter = 0;
            for (boolean visit : visited) {
                if (visit == true) {
                    path = path + counter + ",";
                }
                counter++;
            }
            path = path + lengthPath;
            return path;
        }
        return null;
    }

    public static <V extends Comparable<V>, E> boolean shortestPaths(GraphDijkstra<V, E> g, V vOrig,
                                                                     Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                                     ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {

        if (!g.validVertex(vOrig)) return false;

        paths.clear(); dists.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts]; //default value: false
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object [numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object [numVerts];
        initializePathDist(numVerts, pathKeys, dist);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        dists.clear();
        paths.clear();
        for (int i = 0; i < numVerts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (V vDst : g.vertices()) {
            int i = g.key(vDst);
            if (dist[i] != null) {
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(g, vOrig, vDst, pathKeys, shortPath);
                paths.set(i, shortPath);
                dists.set(i, dist[i]);
            }
        }
        return true;
    }

    private static <V extends Comparable<V>, E> void getPath(GraphDijkstra<V, E> g, V vOrig, V vDest,
                                                             V [] pathKeys, LinkedList<V> path) {

        if (vOrig.equals(vDest))
            path.push(vOrig);
        else {
            path.push(vDest);
            int vKey = g.key(vDest);
            vDest = pathKeys[vKey];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

    public int numVertices() {
        return vertices.size();
    }

    public ArrayList<V> vertices() {
        return vertices;
    }

    public boolean validVertex(V vert) {
        boolean isValidVertex = false;
        for (V vertex : vertices) {
            if (vertex.compareTo(vert) == 0) {
                isValidVertex = true;
            }
        }
        return isValidVertex;
    }

    public V getVertex(int index) {
        return vertices.get(index);
    }

    // percorrer os vertices e verificar se já existe algum vertice com o mesmo port id

    public Integer key(V vert) {
        Integer index = this.vertices.indexOf(vert);
        if (Integer.compare(index, -1) == 0) {
            return null;
        }
        return index;
    }

    public V vertex(int key) {
        return this.vertices.get(key);
    }

    public ArrayList<V> adjVertices(V vert) {
        return null;
    }

    public ArrayList<Edge<V, E>> edges() {
        return this.edges;
    }


    public Edge<V, E> edge(V vOrig, V vDest) {
        for (Edge<V, E> edge : this.edges) {
            if ((edge.getVOrig() == vOrig && edge.getVDest() == vDest)
                    || (edge.getVOrig() == vDest && edge.getVDest() == vOrig)) {
                return edge;
            }
        }
        return null;
    }

    public Edge<V, E> edge(int vOrigKey, int vDestKey) {
        V vOrig = this.vertices.get(vOrigKey);
        V vDest = this.vertices.get(vDestKey);
        return this.edge(vOrig, vDest);
    }

    public boolean addVertex(V vert) {
        if (!validVertex(vert)) {
            this.vertices.add(vert);
            return true;
        }
        return false;
    }


    public boolean addEdge(V vOrig, V vDest, E weight) {
        if(this.validVertex(vOrig)) {
            this.addVertex(vOrig);
        }
        if(this.validVertex(vDest)) {
            this.addVertex(vDest);
        }
        if (edge(vOrig, vDest) == null) {
            Edge<V, E> edge = new Edge(vOrig, vDest, weight);
            this.edges.add(edge);
            return true;
        }
        return false;
    }

    public boolean removeVertex(V vert) {
        if (this.validVertex(vert)) {
            vertices.remove(vert);
            return true;
        }
        return false;
    }

    public boolean removeEdge(V vOrig, V vDest) {
        Edge<V, E> edge = this.edge(vOrig, vDest);
        if (edge != null) {
            this.edges.remove(edge);
        }
        return false;
    }

    public GraphDijkstra clone() {
        return this;
    }

}