package lapr.project.structures;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.List;

public class KDTree<T> {

    public static class Node<T> {
        protected Point2D.Double coords;
        protected T element;
        protected Node<T> left;
        protected Node<T> right;

        public Node(double x, double y, T element) {
            this.coords = new Point2D.Double(x, y);
            this.element = element;
        }

        public Node() {
        }

        public Double getX() {
            return coords.x;
        }

        public Double getY() {
            return coords.y;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }
    }

    private final Comparator<Node<T>> cmpX = new Comparator<Node<T>>() {
        @Override
        public int compare(Node<T> p1, Node<T> p2) {
            return Double.compare(p1.getX(), p2.getX());
        }
    };

    private final Comparator<Node<T>> cmpY = new Comparator<Node<T>>() {
        @Override
        public int compare(Node<T> p1, Node<T> p2) {
            return Double.compare(p1.getY(), p2.getY());
        }
    };

    private Node<T> root;

    public void buildTree(List<Node<T>> nodes) {
        root = new Object() {
            Node<T> buildTree(boolean divX, List<Node<T>> nodes) {
                if (nodes == null || nodes.isEmpty())
                    return null;
                nodes.sort(divX ? cmpX : cmpY);
                int mid = nodes.size() >> 1;
                Node<T> node = new Node<>();
                node.coords = nodes.get(mid).coords;
                node.setElement(nodes.get(mid).getElement());
                node.left = buildTree(!divX, nodes.subList(0, mid));
                if (mid + 1 <= nodes.size() - 1)
                    node.right = buildTree(!divX, nodes.subList(mid + 1, nodes.size()));
                return node;
            }
        }.buildTree(true, nodes);
    }

    public T findNearestNeighbour(double x, double y) {
        return findNearestNeighbour(root, x, y, true);
    }

    private T findNearestNeighbour(Node<T> fromNode, final double x, final double y, boolean divX) {
        return new Object() {
            double closestDist = Double.POSITIVE_INFINITY;
            T closestNode = null;

            T findNearestNeighbour(Node<T> node, boolean divX) {
                if (node == null)
                    return null;
                double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
                if (closestDist > d) {
                    closestDist = d;
                    closestNode = node.getElement();
                }
                double delta = divX ? x - node.coords.x : y - node.coords.y;
                double delta2 = delta * delta;
                Node<T> node1 = delta < 0 ? node.left : node.right;
                Node<T> node2 = delta < 0 ? node.right : node.left;
                findNearestNeighbour(node1, !divX);
                if (delta2 < closestDist) {
                    findNearestNeighbour(node2, !divX);
                }
                return closestNode;
            }
        }.findNearestNeighbour(fromNode, divX);
    }
}