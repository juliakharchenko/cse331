package pathfinder;

import java.util.*;
import pathfinder.datastructures.Path;
import graph.*;

/**
 * <b>DijkstrasAlgorithm</b> implements Dijkstra's algorithm to determine the shortest path between
 * two Nodes of type N.
 */
public class DijkstrasAlgorithm {

    // This class is not an ADT

    /**
     * Returns the shortest path between two nodes represented by type N in a graph
     * based on the total distance to get from the start node to the end node. Thi
     * is a representation of Dijkstra's algorithm.
     * @param g graph that is being looked at
     * @param start start point for the path
     * @param dest end point for the path
     * @param <N> represents the Node type in the graph
     * @return shortest path calculated from Dijkstra's algorithm between start and end
     *         points in graph, null if no path found
     */
    public static <N> Path<N> dijkstrasPath(DLGraph<N, Double> g, N start, N dest) {
        if (g == null) throw new IllegalArgumentException("Cannot have null graph");
        if (start == null || dest == null) throw new IllegalArgumentException("Cannot have null start or end nodes.");
        if (!(g.containsNode(new Node<>(start)) && g.containsNode(new Node<>(dest)))) {
            throw new IllegalArgumentException("Given nodes must be contained in graph.");
        }

        Queue<Path<N>> active = new PriorityQueue<>(new PathComparator());
        Set<Node<N>> finished = new HashSet<>();
        active.add(new Path<>(start));

        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();
            Node<N> minDestNode = new Node<>(minPath.getEnd());
            if (minDest.equals(dest)) return minPath;
            if (!finished.contains(minDestNode)) {
                List<Edge<Double, N>> allEdges = new ArrayList<>(g.getAllEdges(minDestNode));
                for (Edge<Double, N> e: allEdges) {
                    if (!finished.contains(e.getChild())) {
                        Path<N> newPath = minPath.extend(e.getChild().getData(), e.getLabel());
                        active.add(newPath);
                    }
                }
            }
            finished.add(minDestNode);
        }
        return null;
    }

    /**
     * Implements a Comparator to compare two Paths represented by an unknown type
     */
    private static class PathComparator implements Comparator<Path<?>> {
        /**
         * Compares two Paths of unknown types based on the total cost to
         * travel through those paths
         * @param path1 First path looked at
         * @param path2 Second path looked at
         * @return -1 if first path cost is less than second path cost,
         * 1 if first path cost is greater than second path cost,
         * 0 if first path cost is equivalent to second path cost.
         * @throws IllegalArgumentException if either of given edges are null
         */
        public int compare(Path<?> path1, Path<?> path2) throws IllegalArgumentException {
            if (path1 == null || path2 == null) throw new IllegalArgumentException("Given paths cannot be null.");
            double diffCost = path1.getCost() - path2.getCost();
            if (diffCost < 0) return -1;
            if (diffCost > 0) return 1;
            return 0;
        }
    }
}
