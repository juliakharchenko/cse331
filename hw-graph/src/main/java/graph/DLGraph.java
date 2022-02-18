package graph;

import java.util.*;

/**
 * This class represents a mutable, directed, labeled graph. This graph consists of
 * Nodes that store data in them and Edges that connect Nodes together and have labels.
 *
 * Specification fields:
 * @spec.specfield graph : Map of Nodes with each Node corresponding to a Set of Edges
 *                         that connect each Node with another Node.
 *
 * Abstract Invariant:
 * Graph cannot contain identical Nodes. No two Edges with the same parent and child Nodes
 * will have the same edge label. An Edge can start and end on the same Node. Graph cannot
 * contain null Nodes or any null Edges within the set of Edges for that given Node.
 */

public class DLGraph {

    /**
     * Holds the boolean value of whether large tests is checkRep() will be performed
     * True if long tests will be performed, false otherwose
     */
    public static final boolean DEBUG = false;
    /**
     * Holds all the Nodes and all the Edges of each Node within the graph in a map.
     */
    private Map<Node, Set<Edge>> graph;

    // Abstraction Function (this):
    // DLGraph, g, represents a map of Nodes with each node having
    // a set of edges connecting them to other nodes within the graph.

    // Representation Invariant for every graph g:
    // graph != null
    // for all Nodes in graph, nodes are never null and there are no duplicate nodes
    // for all Edges in each Node in graph, no Edges are null, no two edges are the same
    // and child node of edge must be in graph

    /**
     * @spec.effects Constructs a new, empty DLGraph (no Nodes or sets of Edges).
     */
    public DLGraph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    public void checkRep() {
        assert(graph != null) : "graph cannot be null";
        if (DEBUG) {
            for (Node n: graph.keySet()) assert(n != null) : "nodes cannot be null in graph";
            for (Node n: graph.keySet()) {
                assert (graph.get(n) != null) : "graph can't have nodes with null sets of edges";
                for (Edge e: graph.get(n)) {
                    assert(e != null) : "graph cannot have null edges";
                    assert(graph.containsKey(e.getChild())) : "child node must be in graph";
                    // stores all edges that have been visited (checks for duplicates)
                    Set<Edge> visited = new HashSet<>();
                    assert(!visited.contains(e)) : "graph can't have duplicated edges " +
                            "(same parent, same child, same label)";
                    visited.add(e);
                }
            }
        }

    }

    /**
     * Adds the given Node to the graph if the graph doesn't already contain Node
     *
     * @param n Node to add to the graph
     * @spec.requires n != null
     * @spec.modifies this
     * @spec.effects Adds given node to this graph
     * @return true if Node can be added to graph, false otherwise
     */
    public boolean addNode(Node n) {
        checkRep();
        if (graph.containsKey(n)) return false; // Node n already exists in graph
        graph.put(n, new HashSet<Edge>());
        checkRep();
        return true;

    }

    /**
     * Adds an Edge to the graph for the parent Node 'from' pointing toward the child Node 'target'
     *
     * @param from the parent Node that the edge is added to in the graph
     * @param target the child Node that the Edge points to
     * @param label the label of the Edge
     * @spec.requires from != null, target != null, label != null
     * @spec.modifies this
     * @spec.effects adds the given Edge to the set of Edges for the parent Node
     * @return true if Edge was added to the graph
     * @throws IllegalArgumentException if graph does not contain either given node
     */
    public boolean addEdge(Node from, Node target, String label) {
        checkRep();
        if (!graph.containsKey(from)) {
            throw new IllegalArgumentException("Parent node is not in graph.");
        } else if (!graph.containsKey(target))  {
            throw new IllegalArgumentException("Child node is not in the graph");
        }
        graph.get(from).add(new Edge(label, target));
        checkRep();
        return true;
    }


    /**
     * Checks if the given Node is contained within the graph
     *
     * @param n Node to check if it is in the graph
     * @spec.requires n != null
     * @return true if Node is in graph, false otherwise
     */
    public boolean containsNode(Node n) {
        checkRep();
        return graph.containsKey(n);
    }

    /**
     * Returns a set of all the Nodes within the graph
     *
     * @return set of Nodes in graph, empty set if no nodes
     */
    public Set<Node> getAllNodes() {
        checkRep();
        return new HashSet<Node>(graph.keySet());
    }

    /**
     * Returns the set of all the Edges for the given Node within the graph
     *
     * @param n Node to get all the Edges from
     * @spec.requires n != null
     * @return set of all Edges for the given Node in the graph, empty set if no edges for given node
     * @throws IllegalArgumentException if graph doesn't contain given node
     */
    public Set<Edge> getAllEdges(Node n) {
        checkRep();
        if (!graph.containsKey(n)) throw new IllegalArgumentException("Given node is not in graph.");
        return new HashSet<Edge>(graph.get(n));
    }

    /**
     * Returns the set of all child Nodes for the given Node within the graph
     *
     * @param n Mode to get all the children Nodes from
     * @spec.requires n != null
     * @return set of all children Nodes of the given Node in the graph, empty set if no children
     * @throws IllegalArgumentException if graph doesn't contain given node
     */
    public Set<Node> getAllChildren(Node n) {
        checkRep();
        if (!graph.containsKey(n)) {
            throw new IllegalArgumentException("Given node is not in graph.");
        }
        Set<Node> children = new HashSet<>();
        for (Edge e: graph.get(n)) children.add(e.getChild());
        checkRep();
        return children;
    }

    /**
     * Returns the number of Nodes present within the graph
     *
     * @return number of Nodes in this graph
     */
    public int size() {
        checkRep();
        return graph.size();
    }

    /**
     * Returns the number of Edges connecting the first given Node (parent) to the
     * second given Node (child).
     *
     * @param from parent Node
     * @param target child Node
     * @spec.requires from != null, target != null
     * @return number of Edges from 'from' to 'target'
     * @throws IllegalArgumentException if graph doesn't contain given parent or child node
     */
    public int numEdges(Node from, Node target) {
        checkRep();
        if (!graph.containsKey(from)) {
            throw new IllegalArgumentException("Parent node is not in graph.");
        }
        if (!graph.containsKey(target)) {
            throw new IllegalArgumentException("Child node is not in graph.");
        }
        int edges = 0;
        for (Edge e: graph.get(from)) {
            if (e.getChild().equals(target)) edges++;
        }
        checkRep();
        return edges;
    }

    /**
     * Returns whether this graph contains any Nodes
     *
     * @return true if this graph has no Nodes, false otherwise
     */
    public boolean isEmpty() {
        checkRep();
        return graph.isEmpty();
    }

    /**
     * Clears this graph (removes all Nodes and all Edges)
     *
     * @spec.modifies this
     * @spec.effects clears all Nodes and Edges within this graph
     */
    public void clearGraph() {
        checkRep();
        graph.clear();
        checkRep();
    }
}
