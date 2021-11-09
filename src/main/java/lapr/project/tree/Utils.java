
package lapr.project.tree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DEI-ESINF
 */
public class Utils {
    public static <E extends Comparable<E>> Iterable<E> sortByBST(List<E> listUnsorted){
        BST binarySearchTree = new BST();
        for (E elemento : listUnsorted) {
            binarySearchTree.insert(elemento);
        }
        return binarySearchTree.inOrder();
    }    
}
