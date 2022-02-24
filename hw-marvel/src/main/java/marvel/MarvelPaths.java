package marvel;

import java.util.*;
import graph.*;

/**
 * This class builds a directed, labeled graph consisting of marvel characters as nodes
 * and edges between nodes representing the comic they share
 */
public class MarvelPaths {

    // This is not an ADT

    /**
     * Allows client to search for the shortest path between two marvel characters
     * using a BFS algorithm. If there is no path between the two marvel characters,
     * method tells client that a path doesn't exist.
     * @param args contains the supplied command-line arguments as an array of Strings
     */
    public static void main(String[] args) {
        DLGraph<String,String> marvelGraph = MarvelPaths.buildGraph("marvel.csv");
        Scanner input = new Scanner(System.in);
        boolean playAgain = true;
        System.out.println("Welcome to MarvelPaths!");
        System.out.println("Here you can determine the shortest path between two marvel characters across the comics.");
        System.out.println("Input characters in all CAPS with a dash between first and last names (Ex: CAPTAIN-AMERICA).");
        while (playAgain) {
            System.out.print("Please choose your first character: ");
            Node<String> char1 = new Node<>(input.nextLine());
            System.out.print("Please choose your second character: ");
            Node<String> char2 = new Node<>(input.nextLine());
            if (!(marvelGraph.containsNode(char1) || marvelGraph.containsNode(char2))) {
                System.out.println("Neither character is a valid marvel character");
            } else if (!marvelGraph.containsNode(char1)) {
                System.out.println("First character is not a valid marvel character");
            } else if (!marvelGraph.containsNode(char2)) {
                System.out.println("Second character is not a valid marvel character");
            } else {
                System.out.println("path from " + char1.getData() + " to " + char2.getData() + ":");
                List<Edge<String,String>> bfs = MarvelPaths.shortestPath(marvelGraph, char1.getData(), char2.getData());
                if (bfs == null) System.out.println("no path found");
                else {
                    String parent = char1.getData();
                    for (Edge<String,String> e: bfs) {
                        System.out.println(parent + " to " + e.getChild().getData() + " via " + e.getLabel());
                        parent = e.getChild().getData();
                    }
                }
            }
            System.out.println("\nWould you like to play again (yes/no)?");
            String response = input.nextLine();
            if (!response.toLowerCase().equals("yes")) playAgain = false;
        }
        input.close();
    }

    /**
     * Constructs a directed, label graph based on the information in the file.
     * @param filename the file that the graph is being created from
     * @return DLGraph constructed from given file, empty graph if file is empty
     * @throws IllegalArgumentException if filename is null
     */
    public static DLGraph<String,String> buildGraph(String filename) {
        if (filename == null) throw new IllegalArgumentException("File name cannot be null");

        Map<String, List<String>> books = MarvelParser.parseData(filename);
        DLGraph<String,String> marvelGraph = new DLGraph<>();

        for (String book: books.keySet()) {
            List<String> charsInBook = books.get(book);
            marvelGraph.addNode(new Node<>(charsInBook.get(0)));
            for (int i = 0; i < charsInBook.size() - 1; i++) {
                Node<String> parent = new Node<>(charsInBook.get(i));
                for (int j = i + 1; j < charsInBook.size(); j++) {
                    Node<String> child = new Node<>(charsInBook.get(j)); // since list has no duplicates, cannot have reflexive edge
                    marvelGraph.addNode(child);
                    marvelGraph.addEdge(parent, child, book);
                    marvelGraph.addEdge(child, parent, book);
                }
            }
        }
        return marvelGraph;
    }

    /**
     * Finds the shortest path between two nodes using a Breadth-First-Search(BFS) algorithm
     * @param g the graph looked at to find path between two given characters
     * @param char1 the starting character
     * @param char2 the destination character
     * @return list of edges containing the shortest path between the start and destination characters,
     *         null if there is no path between two given characters
     * @throws IllegalArgumentException if either given characters are null or do not exist in the graph
     *                                  or if the graph is null
     */
    public static List<Edge<String,String>> shortestPath(DLGraph<String,String> g, String char1, String char2) {
        if (g == null) throw new IllegalArgumentException("Graph cannot be null.");
        if (char1 == null || char2 == null) {
            throw new IllegalArgumentException("Cannot give null characters.");
        }
        Node<String> start = new Node<>(char1);
        Node<String> destination = new Node<>(char2);
        if (!(g.containsNode(start) && g.containsNode(destination))) {
            throw new IllegalArgumentException("Graph must contain start and destination nodes.");
        }

        Queue<Node<String>> workList = new LinkedList<>();
        Map<Node<String>, List<Edge<String,String>>> path = new HashMap<>();

        workList.add(start);
        path.put(start, new ArrayList<>());

        while (!workList.isEmpty()) {
            Node<String> parent = workList.remove();
            if (parent.equals(destination)) return new ArrayList<>(path.get(parent));

            List<Edge<String,String>> sortedEdges = new ArrayList<>(g.getAllEdges(parent));
            sortedEdges.sort(new EdgeComparator());

            for (Edge<String,String> e: sortedEdges) {
                if (!path.containsKey(e.getChild())) {
                    List<Edge<String,String>> currentPath = new ArrayList<>(path.get(parent));
                    List<Edge<String,String>> newPath = new ArrayList<>(currentPath);
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
    private static class EdgeComparator implements Comparator<Edge<String,String>> {
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
        public int compare(Edge<String,String> e1, Edge<String,String> e2) {
            if (e1 == null || e2 == null) throw new IllegalArgumentException();
            if (!e1.getChild().equals(e2.getChild())) {
                return e1.getChild().getData().compareTo(e2.getChild().getData());
            }
            return e1.getLabel().compareTo(e2.getLabel());
        }
    }
}
