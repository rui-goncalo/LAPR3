package lapr.project.tree;

import lapr.project.model.Port;
import lapr.project.model.ShipData;
import lapr.project.utils.CSVReaderUtils;
import oracle.ucp.common.waitfreepool.kdtree.KDTree;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KdTreeTest {

    ArrayList<Port> portsArray = new ArrayList<>();
    //KDTree<Port> portTree = new KDTree<>();

    ShipData dynamic1 = new ShipData(LocalDateTime.of(2020, 12, 31, 2, 20, 0), 27.78238f, -78.30668f, 10.4f, 72f, 78, 'B');
    ShipData dynamic2 = new ShipData(LocalDateTime.of(2020, 12, 31, 2, 30, 0), 39.45f, -0.3f, 10.4f, 72f, 78, 'B');

    //@Test
/*    public void KDTree() {
        portsArray = CSVReaderUtils.readPortCSV("..src/data/sports.csv");

        List<Node<Port>> nodes = new ArrayList<>();
        for (Port port : portsArray) {
            Node<Port> node = new Node<>(port.getLat(), port.getLon(), port);
            nodes.add(node);
        }
        portTree.buildTree(nodes);

        Port port = portTree.findNearestNeighbour(dynamic1.getLat(), dynamic1.getLon());
        assertEquals(port.getPortName(), "New Jersey");
        port = portTree.findNearestNeighbour(dynamic2.getLat(), dynamic2.getLon());
        assertEquals(port.getPortName(), "Valencia");
    }*/

}
