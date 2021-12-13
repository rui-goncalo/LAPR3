package lapr.project.structures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author DEI-ESINF
 * @author Rui Gonçalves - 1191831
 */
public class AVLTest {

    public AVLTest() {
    }

    /**
     * Test of insert method, of class AVL.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");

        //test Simple right rotation 
        AVL<Integer> instance = new AVL<>();
        int[] arr;
        arr = new int[]{8, 4, 10, 2, 6, 3};
        Integer[] inorder1 = {2, 3, 4, 6, 8, 10};
        //new elements
        for (int j : arr) instance.insert(j);

        List<Integer> lExpected = Arrays.asList(inorder1);
        assertEquals(lExpected, instance.inOrder(), "inOrder should be " + lExpected.toString());
        System.out.println("<instance 1>");
        System.out.println(instance);
        System.out.println("height1=" + instance.height());
        System.out.println("------------------------------------");

        //test Simple left rotation
        AVL<Integer> instance2 = new AVL<>();
        int[] arr2 = {8, 4, 10, 9, 15, 12};
        Integer[] inorder2 = {4, 8, 9, 10, 12, 15};
        for (int j : arr2) instance2.insert(j);
        System.out.println("<instance 2>");
        System.out.println(instance2);
        System.out.println("height2=" + instance2.height());
        lExpected = Arrays.asList(inorder2);
        assertEquals(lExpected, instance2.inOrder(), "inOrder should be " + lExpected.toString());
        assertEquals(instance2.height(), 2, "height should be 2 ");
        System.out.println("------------------------------------");

        //test double rotation 
        AVL<Integer> instance3 = new AVL<>();
        int[] arr3;
        arr3 = new int[]{8, 4, 10, 2, 6, 5};
        Integer[] inorder3 = {2, 4, 5, 6, 8, 10};
        for (int j : arr3) instance3.insert(j);
        System.out.println("<instance 3>");
        System.out.println(instance3);
        System.out.println("height3=" + instance3.height());
        lExpected = Arrays.asList(inorder3);
        assertEquals(lExpected, instance3.inOrder(), "inOrder should be " + lExpected.toString());
        assertEquals(instance3.height(), 2, "height should be 2 ");
        System.out.println("------------------------------------");
    }

    /**
     * Test of remove method, of class AVL.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        List<Integer> lExpected;
        AVL<Integer> instance;

        instance = new AVL<>();
        int[] arr = {8, 4, 10, 2, 6, 3};
        for (int j : arr) instance.insert(j);


        //no rotations needed
        instance.remove(3);
        lExpected = Arrays.asList(2, 4, 6, 8, 10);
        assertEquals(lExpected, instance.inOrder(), "inOrder should be " + lExpected.toString());
        assertEquals(instance.height(), 2, "height should be 2 ");

        //test Simple left rotation 
        instance.remove(2);
        lExpected = Arrays.asList(4, 6, 8, 10);
        assertEquals(lExpected, instance.inOrder(), "inOrder should be " + lExpected.toString());
        assertEquals(instance.height(), 2, "height should be 2 ");

        instance.remove(10);
        lExpected = Arrays.asList(4, 6, 8);
        assertEquals(lExpected, instance.inOrder(), "inOrder should be " + lExpected.toString());
        assertEquals(instance.height(), 1, "height should be 1 ");

        instance.remove(6);
        lExpected = Arrays.asList(4, 8);
        assertEquals(lExpected, instance.inOrder(), "inOrder should be " + lExpected.toString());
        assertEquals(1, instance.height(), "height should be 1 ");

        instance.remove(4);
        lExpected = Collections.singletonList(8);
        assertEquals(lExpected, instance.inOrder(), "inOrder should be " + lExpected.toString());
        assertEquals(0, instance.height(), "height should be 1 ");

        instance.remove(8);
        lExpected = Collections.emptyList();
        assertEquals(lExpected, instance.inOrder(), "inOrder should be " + lExpected.toString());
        assertEquals(-1, instance.height(), "height should be -1 ");

        System.out.println("------------------------------------");
    }

    /**
     * Test of equals method, of class AVL.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        AVL<Integer> instance = new AVL<>();
        int[] arr = {4, 7};
        for (int k : arr) {
            instance.insert(k);
        }
        AVL<Integer> instance2 = new AVL<>();
        int[] arr2 = {4, 7};
        for (int j : arr2) {
            instance2.insert(j);
        }
        assertEquals(instance, instance2);
        instance2.remove(7);
        assertNotEquals(instance, instance2);
    }
}
