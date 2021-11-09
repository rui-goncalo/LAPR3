package lapr.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lapr.project.model.Ship;
import lapr.project.tree.BST;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TopShipsControllerTest {
    
    public TopShipsControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of getNTopShips method, of class TopShipsController.
     */
    @Test
    public void testGetNTopShips() {
        System.out.println("getNTopShips");
        int n = 0;
        LocalDateTime start = null;
        LocalDateTime end = null;
        BST<Ship> shipTree = null;
        TopShipsController instance = new TopShipsController();
        ArrayList<Ship> expResult = null;
        ArrayList<Ship> result = instance.getNTopShips(n, start, end, shipTree);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
