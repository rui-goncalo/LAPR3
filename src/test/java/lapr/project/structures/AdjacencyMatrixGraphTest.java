
package lapr.project.structures;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdjacencyMatrixGraphTest {

    @Test
    public void testNumVertices() {

        System.out.println("Test of numVertices");
        AdjacencyMatrixGraph<String, Integer> instance = new AdjacencyMatrixGraph<>();
        assertEquals(0, instance.numVertices());
        instance.insertVertex("Vert 1");
        assertEquals(1, instance.numVertices());
        instance.insertVertex("Vert 2");
        assertEquals(2, instance.numVertices());
        instance.removeVertex("Vert 1");
        assertEquals(1, instance.numVertices());
        instance.removeVertex("Vert 2");
        assertEquals(0, instance.numVertices());
    }

    @Test
    public void testNumEdges() {
        System.out.println("Test of numEdges");
        AdjacencyMatrixGraph<String, Integer> instance = new AdjacencyMatrixGraph<>();

        assertEquals(0, instance.numEdges());

        for (int i = 1; i < 5; i++)
            instance.insertVertex("Vert " + i);

        instance.insertEdge("Vert 3", "Vert 2", 12);
        assertEquals(1, instance.numEdges());

        instance.insertEdge("Vert 1", "Vert 4", 11);
        assertEquals(2, instance.numEdges());

        instance.removeEdge("Vert 2", "Vert 3");
        assertEquals(1, instance.numEdges());

        instance.removeEdge("Vert 4", "Vert 1");
        assertEquals(0, instance.numEdges());
    }

    @Test
    public void testEdges() {
        System.out.println("Test of Edges getter");

        AdjacencyMatrixGraph<String, String> instance = new AdjacencyMatrixGraph<String, String>();

        Iterator<String> itEdge = instance.edges().iterator();

        assertEquals(false, itEdge.hasNext());

        for (int i = 1; i < 5; i++)
            instance.insertVertex("Vert " + i);

        instance.insertEdge("Vert 1", "Vert 2", "Edge 1");
        instance.insertEdge("Vert 2", "Vert 4", "Edge 2");
        instance.insertEdge("Vert 1", "Vert 3", "Edge 3");

        itEdge = instance.edges().iterator();

        assertEquals(0, itEdge.next().compareTo("Edge 1"));
        assertEquals(0, itEdge.next().compareTo("Edge 3"));
        assertEquals(0, itEdge.next().compareTo("Edge 2"));

        instance.removeEdge("Vert 1", "Vert 2");

        itEdge = instance.edges().iterator();

        instance.removeEdge("Vert 1", "Vert 3");
        instance.removeEdge("Vert 2", "Vert 4");

        itEdge = instance.edges().iterator();
        assertEquals(false, itEdge.hasNext());
    }

    @Test
    public void testInsertEdge() {
        System.out.println("Test of insert edge");

        AdjacencyMatrixGraph<String, String> instance = new AdjacencyMatrixGraph<String, String>();

        for (int i = 1; i < 5; i++)
            instance.insertVertex("Vert " + i);

        assertEquals(0, instance.numEdges());

        instance.insertEdge("Vert 1", "Vert 2", "Edge 1");
        assertEquals(1, instance.numEdges());

        instance.insertEdge("Vert 1", "Vert 3", "Edge 2");
        assertEquals(2, instance.numEdges());

        instance.removeEdge("Vert 1", "Vert 3");
        assertEquals(1, instance.numEdges());

        instance.insertEdge("Vert 2", "Vert 4", "Edge 3");
        assertEquals(2, instance.numEdges());

        Iterator<String> itEdge = instance.edges().iterator();
    }

    @Test
    public void testInsertVertex() {
        System.out.println("Test of insert vertex");

        AdjacencyMatrixGraph<String, Integer> instance = new AdjacencyMatrixGraph<String, Integer>();
        assertEquals(0, instance.numVertices());
        instance.insertVertex("Vert 1");
        assertEquals(1, instance.numVertices());
        instance.insertVertex("Vert 2");
        assertEquals(2, instance.numVertices());

        instance.removeVertex("Vert 1");
        assertEquals(1, instance.numVertices());

        instance.insertVertex("Vert 3");
        assertEquals(2, instance.numVertices());

        instance.insertVertex("Vert 4");

        Iterator<String> itVert = instance.vertices().iterator();

        assertEquals(0, (itVert.next().compareTo("Vert 2")));
        assertEquals(0, (itVert.next().compareTo("Vert 3")));
        assertEquals(0, (itVert.next().compareTo("Vert 4")));

        // Force resize of matrix

        for (int i = 0; i < 100; i++)
            instance.insertVertex("Vert " + i);

        instance.insertEdge("Vert 1", "Vert 80", 80);

        Iterator<Integer> itEdge = instance.edges().iterator();

        assertEquals(80, itEdge.next());
    }

    @Test
    public void testToString() {
        System.out.println("Test of To String");

        AdjacencyMatrixGraph<String, String> instance = new AdjacencyMatrixGraph<String, String>();

        for (int i = 1; i <= 5; i++)
            instance.insertVertex("Vert " + i);

        instance.insertEdge("Vert 1", "Vert 2", "Edge 1");
        instance.insertEdge("Vert 2", "Vert 4", "Edge 2");
        instance.insertEdge("Vert 1", "Vert 3", "Edge 3");
        instance.insertEdge("Vert 2", "Vert 3", "Edge 4");
        instance.insertEdge("Vert 1", "Vert 5", "Edge 5");

        System.out.println(instance);
    }
}

