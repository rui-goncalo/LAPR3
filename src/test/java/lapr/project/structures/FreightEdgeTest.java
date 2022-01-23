package lapr.project.structures;

import java.util.Objects;

import lapr.project.data.FreightEdge;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 1180590
 */
public class FreightEdgeTest {
    
    final String vOrig= "A";
    final String vDest= "B";
    final Double weight= 1.0;
    
    public FreightEdgeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Test of getVOrig method, of class FreightEdge.
     */
    @Test
    public void testGetVOrig() {
        System.out.println("Test Get Orig Vertex");
        FreightEdge<String, Double> instance = new FreightEdge<>(vOrig, vDest, weight);

        
        Object expResult = "A";
        Object result = instance.getVOrig();
        assertEquals(expResult, result, "Result should be A");

    }

    /**
     * Test of getVDest method, of class FreightEdge.
     */
    @Test
    public void testGetVDest() {
        System.out.println("Test Get Dest Vertex");
        FreightEdge<String, Double> instance = new FreightEdge<>(vOrig, vDest, weight);

        
        Object expResult = "B";
        Object result = instance.getVDest();
        assertEquals(expResult, result, "Result should be B");
    }

    /**
     * Test of getWeight method, of class FreightEdge.
     */
    @Test
    public void testGetWeight() {
        System.out.println("Test getWeight");
        
        FreightEdge<String, Double> instance = new FreightEdge<>(vOrig, vDest, weight);
        
        Double expResult = 1.0;
        Double result = instance.getWeight();
        assertEquals(expResult, result, "Result should be 1.0");
    }

    /**
     * Test of setWeight method, of class FreightEdge.
     */
    @Test
    public void testSetWeight() {
        System.out.println("Test setWeight");
        FreightEdge<String, Double> instance = new FreightEdge<>(vOrig, vDest, weight);
        
        Double expResult = 3.0;
        
        Double weight = 3.0;
        instance.setWeight(weight);
        Double result = instance.getWeight();
        assertEquals(expResult, result, "Result should be 3.0");
    }

    /**
     * Test of toString method, of class FreightEdge.
     */
    @Test
    public void testToString() {
        System.out.println("Test toString");
        
        FreightEdge<String, Double> instance = new FreightEdge<>(vOrig, vDest, weight);
        String expResult = "A -> B\nWeight: 1.0";
        String result = instance.toString();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class FreightEdge.
     */
    @Test
    public void testEquals() {
        System.out.println("Test equals");
        FreightEdge<String, Double> instance = new FreightEdge<>(vOrig, vDest, weight);
        
        Object                      other1 = null;
        FreightEdge<String, Double> other2 = new FreightEdge<>("B", "A", 3.0);
        FreightEdge<String, Double> other3 = new FreightEdge<>("A", "B", 1.0);

        
        assertEquals(false, instance.equals(other1), "Should not be equal");
        assertEquals(false, instance.equals(other2), "Should not be equal2");
        assertEquals(true , instance.equals(other3), "Should be equal3");
    }

    /**
     * Test of hashCode method, of class FreightEdge.
     */
    @Test
    public void testHashCode() {
        FreightEdge<String, Double> instance = new FreightEdge<>(vOrig, vDest, weight);
        int expResult = Objects.hash("A", "B");
        int result = instance.hashCode();
        assertEquals(expResult , result, "Should be equal");
    }
    
}
