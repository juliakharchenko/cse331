package marvel;

import java.util.*;
import graph.*;

/**
 * This class builds a directed, labeled graph consisting of marvel characters as nodes
 * and edges between nodes representing the comic they are in
 */
public class MarvelPaths {

    /**
     * Constructs a directed, label graph based on the information in the file.
     * @param filename the file that the graph is being created from
     * @return DLGraph constructed from given file, empty graph if file is empty
     * @throws IllegalArgumentException if filename = null
     */
    public DLGraph buildGraph(String filename) {
        if (filename == null) throw new IllegalArgumentException("File name cannot be null");

        Set<String> characters = new HashSet<>();
        Map<String, List<String>> books = MarvelParser.parseData(filename, characters);
        DLGraph marvelGraph = new DLGraph();

        // add all characters as nodes in the graph
        for (String character: characters) marvelGraph.addNode(new Node(character));

        for (String book: books.keySet()) { // for every book that there is
            List<String> charsInBook = books.get(book); // get list of characters in that book
            for (int i = 0; i < charsInBook.size() - 1; i++) {
                Node parent = new Node(charsInBook.get(i));
                for (int j = i + 1; j < charsInBook.size(); j++) {
                    Node child = new Node(charsInBook.get(j)); // since list has no duplicates, cannot have reflexive edge
                    marvelGraph.addEdge(parent, child, book);
                    marvelGraph.addEdge(child, parent, book);
                }
            }
        }
        return marvelGraph;
    }

    /**
     * Finds the shortest path between two nodes using a Breadth-First-Search(BFS) algorithm
     * @param g the graph looked at to find path between two given nodes
     * @param start the starting node
     * @param destination the destination node
     * @return list of edges containing the shortest path between the start and destination node,
     *         empty list if there is no path between two given nodes
     * @throws IllegalArgumentException if either given nodes are null or do not exist in the graph
     *                                  or if the graph is null
     */
    public List<Edge> shortestPath(DLGraph g, Node start, Node destination) {
        if (g == null) throw new IllegalArgumentException("Graph cannot be null.");
        else if (start == null || destination == null) {
            throw new IllegalArgumentException("Cannot give null nodes.");
        } else if (!(g.containsNode(start) && g.containsNode(destination))) {
            throw new IllegalArgumentException("Graph must contain start and destination nodes.");
        }

        Queue<Node> workList = new LinkedList<>();
        Map<Node, List<Edge>> path = new HashMap<>();

        workList.add(start);
        path.put(start, new ArrayList<>());

        while (!workList.isEmpty()) {
            Node parent = workList.remove();
            if (parent.equals(destination)) return new ArrayList<>(path.get(parent));

            List<Edge> sortedEdges = new ArrayList<Edge>(g.getAllEdges(parent));
            sortedEdges.sort(new EdgeComparator());

            for (Edge e: sortedEdges) {
                if (!path.containsKey(e.getChild())) {
                    List<Edge> currentPath = new ArrayList<>(path.get(parent));
                    List<Edge> newPath = new ArrayList<>(currentPath);
                    newPath.add(e);
                    path.put(e.getChild(), newPath);
                    workList.add(e.getChild());
                }
            }
        }
        return null;
    }

    /**
     * Implements a Comparator to compare two edges
     */
    private static class EdgeComparator implements Comparator<Edge> {
        /**
         * Compares two edges where child nodes are compared first, followed by edge label names
         * (if the child nodes are the same).
         * @param e1 First edge looked at
         * @param e2 Second edge looked at
         * @return a negative integer if first edge is alphabetically less than second edge,
         * a positive integer if first edge is alphabetically greater than second edge,
         * 0 if first edge is alphabetically equivalent to second edge.
         * @throws IllegalArgumentException if either of given edges are null
         */
        public int compare(Edge e1, Edge e2) {
            if (e1 == null || e2 == null) throw new IllegalArgumentException();
            if (e1.getChild().compareTo(e2.getChild()) != 0) {
                return e1.getChild().compareTo(e2.getChild());
            }
            return e1.getLabel().compareTo(e2.getLabel());
        }
    }
}
