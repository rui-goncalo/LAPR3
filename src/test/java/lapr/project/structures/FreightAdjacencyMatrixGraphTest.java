
package lapr.project.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import lapr.project.data.FreightAdjacencyMatrixGraph;
import lapr.project.data.FreightBasicGraphInterface;
import lapr.project.data.FreightEdge;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author 1180590
 */

public class FreightAdjacencyMatrixGraphTest {
    final ArrayList<String> co = new ArrayList<>(Arrays.asList( "A", "A", "B", "C", "C", "D", "E", "E"));
    final ArrayList <String> cd = new ArrayList<>(Arrays.asList("B", "C", "D", "D", "E", "A", "D", "E"));
    final ArrayList <Double> cw = new ArrayList<>(Arrays.asList( 1.0,  2.0 ,  3.0 ,  4.0 ,  5.0 ,  6.0 ,  7.0 ,  8.0 ));

    final ArrayList <String> ov = new ArrayList<>(Arrays.asList( "A",  "B",  "C" ,  "D" ,  "E" ));
    FreightAdjacencyMatrixGraph<String, Double> instance = null;
    
    public FreightAdjacencyMatrixGraphTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void initializeGraph() {
        instance = new FreightAdjacencyMatrixGraph<>(true) ;
    }
    
    /**
     * Test of numVertices method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testNumVertices() {
        System.out.println("numVertices");
        //INSERTS
        assertEquals(0, instance.numVertices(), "result should be zero");
        instance.addVertex("A");
        assertEquals(1, instance.numVertices(), "result should be one");
        instance.addVertex("B");
        assertEquals(2, instance.numVertices(), "result should be two");
        instance.removeVertex("A");
        assertEquals(1, instance.numVertices(), "result should be one");
        instance.removeVertex("B");
        assertEquals(0, instance.numVertices(), "result should be zero");
    }

    /**
     * Test of numEdges method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testNumEdges() {
        System.out.println("Test numEdges");

        assertEquals(0, instance.numEdges(), "result should be zero");

        instance.addEdge("A", "B", 1.0);
        assertEquals(1, instance.numEdges(), "result should be one");

        instance.addEdge("A", "C", 2.0);
        assertEquals(2, instance.numEdges(), "result should be two");

        instance.removeEdge("A", "B");
        assertEquals(1, instance.numEdges(), "result should be one");

        instance.removeEdge("A", "C");
        assertEquals(0, instance.numEdges(), "result should be zero");
    }

    /**
     * Test of vertices method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testVertices() {
        System.out.println("Test vertices");

        assertEquals(0, instance.vertices().size(), "vertices should be empty");

        instance.addVertex("A");
        instance.addVertex("B");

        Collection<String> cs = instance.vertices();
        assertEquals(2, cs.size(), "Must have 2 vertices");
        cs.removeAll(Arrays.asList("A","B"));
        assertEquals(0, cs.size(), "Vertices should be A and B");

        instance.removeVertex("A");

        cs = instance.vertices();
        assertEquals(1, cs.size(), "Must have 1 vertice1");
        cs.removeAll(Arrays.asList("B"));
        assertEquals(0, cs.size(), "Vertice should be B");

        instance.removeVertex("B");
        cs = instance.vertices();
        assertEquals(0, cs.size(), "Must not have any vertice");
    }

    /**
     * Test of edges method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testEdges() {
        System.out.println("Test Edges");

        assertEquals(0, instance.edges().size(), "edges should be empty");

        for (int i = 0; i <co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        Collection <FreightEdge<String,Double>> ced = instance.edges();
        assertEquals(8, ced.size(), "Must have 8 edges");
        for (int i = 0; i <co.size(); i++) {
            int finalI = i;
            ced.removeIf(e -> e.getVOrig().equals(co.get(finalI)) && e.getVDest().equals(cd.get(finalI)) && e.getWeight().equals(cw.get(finalI)));
        }
        assertEquals(0, ced.size(), "Edges should be as inserted");

        instance.removeEdge("A","B");
        ced = instance.edges();
        assertEquals(7, ced.size(), "Must have 7 edges");
        for (int i = 1; i <co.size(); i++) {
            int finalI = i;
            ced.removeIf(e -> e.getVOrig().equals(co.get(finalI)) && e.getVDest().equals(cd.get(finalI)) && e.getWeight().equals(cw.get(finalI)));
        }
        assertEquals(0, ced.size(), "Edges should be as inserted");

        instance.removeEdge("E","E");
        ced = instance.edges();
        assertEquals(6, ced.size(), "Must have 6 edges");
        for (int i = 1; i < co.size()-1; i++) {
            int finalI = i;
            ced.removeIf(e -> e.getVOrig().equals(co.get(finalI)) && e.getVDest().equals(cd.get(finalI)) && e.getWeight().equals(cw.get(finalI)));
        }
        assertEquals(0, ced.size(), "Edges should be as inserted");

        instance.removeEdge("A","C"); instance.removeEdge("B","D");
        instance.removeEdge("C","D"); instance.removeEdge("C","E");
        instance.removeEdge("D","A"); instance.removeEdge("E","D");

        assertEquals(0, instance.edges().size(), "edges should be empty");
    }

    /**
     * Test of edge method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testEdge() {
        System.out.println("Test Edge");

        for (int i = 0; i <co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        for (int i = 0; i <co.size(); i++)
            assertEquals(cw.get(i), instance.edge(co.get(i),cd.get(i)).getWeight(), "edge between "+co.get(i)+" - "+cd.get(i)+" should be "+cw.get(i));

        assertNull(instance.edge("A","E"), "edge should be null");
        assertNull(instance.edge("D","B"), "edge should be null");
        instance.removeEdge("D","A");
        assertNull(instance.edge("D","A"), "edge should be null");
    }

    /**
     * Test of getEdge by key method, of class Graph.
     */
    @Test
    public void testGetEdgeByKey() {
        System.out.println("Test Edge by key");

        for (int i = 0; i <co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        for (int i = 0; i <co.size(); i++)
            assertEquals(cw.get(i), instance.edge(instance.key(co.get(i)),instance.key(cd.get(i))).getWeight(), "edge between "+co.get(i)+" - "+cd.get(i)+" should be "+cw.get(i));

        assertNull(instance.edge(instance.key("A"),instance.key("E")), "edge should be null");
        assertNull(instance.edge(instance.key("D"),instance.key("B")), "edge should be null");
        instance.removeEdge("D","A");
        assertNull(instance.edge(instance.key("D"),instance.key("A")), "edge should be null");
    }

    /**
     * Test of addVertex method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testAddVertex() {
        System.out.println("Test Vertex insertion");
        assertEquals(0, instance.numVerts, "result should be zero");
        instance.addVertex("A");
        assertEquals(1, instance.numVertices(), "result should be one");
    }

    /**
     * Test of addEdge method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testAddEdge() {
        System.out.println("Test numEdges");
        assertEquals(0, instance.numEdges(), "result should be zero");

        instance.addEdge("A","B",1.0);
        assertEquals(1, instance.numEdges(), "result should be one");
    }

    /**
     * Test of removeVertex method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testRemoveVertex() {
        System.out.println("Test removeVertex");

        for (int i = 0; i < co.size(); i++) {
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));
        }

        assertEquals(5, instance.numVertices(), "Num vertices should be 5");
        assertEquals(8, instance.numEdges(), "Num vertices should be 8");
        instance.removeVertex("A");
        assertEquals(4, instance.numVertices(), "Num vertices should be 4");
        assertEquals(5, instance.numEdges(), "Num vertices should be 5");
        instance.removeVertex("B");
        assertEquals(3, instance.numVertices(), "Num vertices should be 3");
        assertEquals(4, instance.numEdges(), "Num vertices should be 4");
        instance.removeVertex("C");
        assertEquals(2, instance.numVertices(), "Num vertices should be 2");
        assertEquals(2, instance.numEdges(), "Num vertices should be 2");
        instance.removeVertex("D");
        assertEquals(1, instance.numVertices(), "Num vertices should be 1");
        assertEquals(1, instance.numEdges(), "Num vertices should be 1");
        instance.removeVertex("E");
        assertEquals(0, instance.numVertices(), "Num vertices should be 0");
        assertEquals(0, instance.numEdges(), "Num vertices should be 0");
    }

    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("Test removeEdge");

        assertEquals(0, instance.numEdges(), "Num edges should be 0");

        for (int i = 0; i <co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        assertEquals(5, instance.numVertices(), "Num edges should be 5");
        assertEquals(8, instance.numEdges(), "Num edges should be 8");

        for (int i = 0; i <co.size() - 1; i++) {
            instance.removeEdge(co.get(i),cd.get(i));
            Collection <FreightEdge< String, Double>> ced = instance.edges();
            int expected = co.size() - i - 1;
            assertEquals(expected, ced.size(), "Expected size is "+expected);
            for (int j = i + 1; j < co.size(); j++) {
                int finalJ = j;
                ced.removeIf(e -> e.getVOrig().equals(co.get(finalJ)) && e.getVDest().equals(cd.get(finalJ)) && e.getWeight().equals(cw.get(finalJ)));
            }
            assertEquals(0, ced.size(),"Expected size is 0");
        }
    }
    
    /**
     * Test of inDegree method, of class Graph.
     */
    @Test
    public void testInDegree() {
        System.out.println("Test inDegree");

        for (int i = 0; i <co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        assertEquals(-1, instance.inDegree("G"), "degree should be -1");
        assertEquals(1, instance.inDegree("A"), "degree should be 1");
        assertEquals(3, instance.inDegree("D"), "degree should be 3");
        assertEquals(2, instance.inDegree("E"), "degree should be 2");
    }
    
    /**
     * Test of incomingEdges method, of class Graph.
     */
    @Test
    public void testIncomingEdges() {
        System.out.println(" Test incomingEdges");

        for (int i = 0; i <co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        Collection <FreightEdge<String,Double>> cie = instance.incomingEdges("D");
        assertEquals(3, cie.size(), "Incoming edges of vert C should be 3");
        cie.removeIf(e -> e.getWeight() == 3 || e.getWeight() == 4 || e.getWeight() == 7);
        assertEquals(0, cie.size(), "Incoming edges of vert C should be 3, 4 and 7");

        cie = instance.incomingEdges("E");
        assertEquals(2, cie.size(), "Incoming edges of vert E should be 2");
        cie.removeIf(e -> e.getWeight() == 5 || e.getWeight() == 8);
        assertEquals(0, cie.size(), "Incoming edges of vert C should be 5 and 8");

        instance.removeEdge("E","E");

        cie = instance.incomingEdges("E");
        assertEquals(1, cie.size(), "Incoming edges of vert E should be 1");
        cie.removeIf(e -> e.getWeight() == 5);
        assertEquals(0, cie.size(), "Incoming edges of vert C should be 5");

        instance.removeEdge("C","E");

        cie = instance.incomingEdges("E");
        assertEquals(0, cie.size(), "Incoming edges of vert C should be empty");
    }
    
    /**
     * Test of testAdjVertices method, of class Graph.
     */
    @Test
    public void testAdjVertices() {
        System.out.println("Test adjVertices");

        for (int i = 0; i < co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        Collection <String> cs = instance.adjVertices("A");
        assertEquals(2, cs.size(), "Num adjacents should be 2");
        cs.removeIf(s -> s.equals("B") || s.equals("C"));
        assertEquals(0, cs.size(), "Adjacents should be B and C");

        cs = instance.adjVertices("B");
        assertEquals(1, cs.size(), "Num adjacents should be 1");
        cs.removeIf(s -> s.equals("D"));
        assertEquals(0, cs.size(), "Adjacents should be S");

        cs = instance.adjVertices("E");
        assertEquals(2, cs.size(), "Num adjacents should be 2");
        cs.removeIf(s -> s.equals("D") || s.equals("E"));
        assertEquals(0, cs.size(), "Adjacents should be D and E");
    }
    /**
     * Test of toString method, of class FreightAdjacencyMatrixGraph.
     */
    @Test
    public void testToString() {
        System.out.println("Test toString");
        
//        FreightAdjacencyMatrixGraph instance = null;
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");

        instance.addVertex("A");
        instance.addVertex("B");
        instance.addVertex("C");
        instance.addVertex("D");
        instance.addVertex("E");
        instance.addVertex("F");
        instance.addVertex("G");
        instance.addVertex("H");
        instance.addVertex("I");
        instance.addVertex("J");
        
        instance.addEdge("A", "B", 3.0);
        instance.addEdge("A", "C", 3.0);
        instance.addEdge("A", "D", 3.0);
        instance.addEdge("A", "E", 3.0);
        
        System.out.println("Only this positions should be filled:\n[0][1] [0][2] [0][3] [0][4]");
        System.out.println(instance.toString());
    }

    /**
     * Test of toString method, of class Graph.
     */
    @Test
    public void testClone() {
        System.out.println("Test Clone");

        for (int i = 0; i <co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        assertEquals(5, instance.numVertices(), "Num vertices should be 5");
        assertEquals(8, instance.numEdges(), "Num vertices should be 8");

        FreightBasicGraphInterface<String,Double> instClone = instance.clone();

        for (int i = 0; i <co.size(); i++) {
            FreightEdge<String, Double> ec = instClone.edge(co.get(i), cd.get(i));
            assertEquals(co.get(i), ec.getVOrig());
            assertEquals(cd.get(i), ec.getVDest());
            assertEquals(cw.get(i), ec.getWeight());
        }

        for (String v : co)
            instClone.removeVertex(v);

        assertEquals(5, instance.numVertices(), "Num vertices should be 5");
        assertEquals(8, instance.numEdges(), "Num vertices should be 8");
        assertEquals(0, instClone.numVertices(), "Num vertices should be 0");
        assertEquals(0, instClone.numEdges(), "Num vertices should be 0");
    }
    @Test
    public void testEquals() {
        System.out.println("Test Equals");

        for (int i = 0; i <co.size(); i++)
            instance.addEdge(co.get(i), cd.get(i), cw.get(i));

        FreightAdjacencyMatrixGraph<String,Double> otherInst =new FreightAdjacencyMatrixGraph<>(true) ;
        for (int i = 0; i <co.size(); i++)
            otherInst.addEdge(co.get(i), cd.get(i), cw.get(i));

        assertEquals(instance, otherInst, "Graphs should be equal");

        otherInst.removeVertex("A");

        assertNotEquals(instance, otherInst, "Graphs should NOT be equal");

        instance.removeVertex("A");

        assertEquals(instance, otherInst, "Graphs should be equal");

        otherInst.removeEdge("C", "E");

        assertNotEquals(instance, otherInst, "Graphs should NOT be equal");

        instance.removeEdge("C", "E");

        assertEquals(instance, otherInst, "Graphs should be equal");
    }
    
}
