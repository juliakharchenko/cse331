/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel.scriptTestRunner;

import java.io.*;
import java.util.*;
import graph.*;
import marvel.*;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    // ***************************
    // ***  JUnit Test Driver  ***
    // ***************************

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, DLGraph> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new MarvelTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    public MarvelTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "LoadGraph":
                    loadGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new DLGraph());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        DLGraph g = graphs.get(graphName);
        g.addNode(new Node(nodeName));
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        DLGraph g = graphs.get(graphName);
        g.addEdge(new Node(parentName), new Node(childName), edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + " to "
                + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        DLGraph g = graphs.get(graphName);
        String nodeList = graphName + " contains:";
        List<Node> sortedNodes = new ArrayList<>(g.getAllNodes());
        Collections.sort(sortedNodes);
        for (Node n: sortedNodes) nodeList += " " + n;
        output.println(nodeList);

    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }



    private void listChildren(String graphName, String parentName) {
        DLGraph g = graphs.get(graphName);
        List<Edge> edges = new ArrayList<>(g.getAllEdges(new Node(parentName)));
        edges.sort(new EdgeComparator());
        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (Edge e: edges) output.print(" " + e.getChild() + "(" + e.getLabel() + ")");
        output.println();
    }

    private void loadGraph(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String filename = arguments.get(1);
        loadGraph(graphName, filename);
    }

    private void loadGraph(String graphName, String filename) {
        DLGraph current = MarvelPaths.buildGraph(filename);
        graphs.put(graphName, current);
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String startNode = arguments.get(1);
        String destNode = arguments.get(2);
        findPath(graphName, startNode, destNode);
    }

    private void findPath(String graphName, String startNode, String destNode) {
        DLGraph g = graphs.get(graphName);
        Node start = new Node(startNode);
        Node dest = new Node(destNode);

        if (!(g.containsNode(start) || g.containsNode(dest))) {
            output.println("unknown: " + startNode);
            output.println("unknown: " + destNode);
        } else if (!g.containsNode(start)) {
            output.println("unknown: " + startNode);
        } else if (!g.containsNode(dest)) {
            output.println("unknown: " + destNode);
        } else {
            output.println("path from " + startNode + " to " + destNode + ":");
            List<Edge> bfs = MarvelPaths.shortestPath(g, startNode, destNode);
            if (bfs == null) output.println("no path found");
            else {
                String parent = startNode;
                for (Edge e: bfs) {
                    output.println(parent + " to " + e.getChild().getData() + " via " + e.getLabel());
                    parent = e.getChild().getData();
                }
            }
        }
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
    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}

