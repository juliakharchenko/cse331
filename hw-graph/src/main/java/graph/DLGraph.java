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
     * @spec.effects Constructs a new, empty DLGraph (no Nodes or sets of Edges).
     */
    public DLGraph() {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    public void checkRep() {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Adds the given Node to the graph if the graph doesn't already contain Node
     *
     * @param n Node to add to the graph
     * @spec.requires n != null
     * @spec.modifies this
     * @return true if Node can be added to graph, false otherwise
     */
    public boolean addNode(Node n) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Adds an Edge to the graph for the parent Node 'from' pointing toward the child Node 'target'
     *
     * @param from the parent Node that the edge is added to in the graph
     * @param target the child Node that the Edge points to
     * @param label the label of the Edge
     * @spec.requires from != null, target != null, label != null, graph contains from and target
     * @spec.modifies this
     * @spec.effects adds the given Edge to the set of Edges for the parent Node
     * @return true if Edge can be added to graph, false otherwise
     */
    public boolean addEdge(Node from, Node target, String label) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Removes the given Node from the graph
     *
     * @param n Node to be removed from the graph
     * @spec.requires n != null, graph contains n
     * @spec.modifies this
     * @spec.effects removes the given Node from graph and all edges pointing to that Node
     * @return true if Node can be removed from graph, false otherwise
     */
    public boolean removeNode(Node n) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Removes the given Edge from the given Node in the graph
     *
     * @param from parent Node of Edge to be removed
     * @spec.requires from != null, e != null, graph contains from and e
     * @spec.modifies this
     * @spec.effects removes the given Edge from the graph
     * @return true if Edge can be removed from graph, false otherwise
     */
    public boolean removeEdge(Node from, Edge e) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Checks if the given Node is contained within the graph
     *
     * @param n Node to check if it is in the graph
     * @spec.requires n != null
     * @return true if Node is in graph, false otherwise
     */
    public boolean containsNode(Node n) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Returns a set of all the Nodes within the graph
     *
     * @return set of Nodes in graph
     */
    public Set<Node> getAllNodes() {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Returns the set of all the Edges for the given Node within the graph
     *
     * @param n Node to get all the Edges from
     * @spec.requires n != null, graph contains n
     * @return set of all Edges for the given Node in the graph
     */
    public Set<Edge> getAllEdges(Node n) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Returns the set of all child Nodes for the given Node within the graph
     *
     * @param n Mode to get all the children Nodes from
     * @spec.requires n != null, graph contains n
     * @return set of all children Nodes of the given Node in the graph
     */
    public Set<Node> getAllChildren(Node n) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Returns the number of Nodes present within the graph
     *
     * @return number of Nodes in this graph
     */
    public int size() {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Returns the number of Edges connecting the first given Node (parent) to the
     * second given Node (child).
     *
     * @param from parent Node
     * @param target child Node
     * @spec.requires from != null, target != null, graph contains from and target
     * @return number of Edges from 'from' to 'target'
     */
    public int numEdges(Node from, Node target) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Returns whether this graph contains any Nodes
     *
     * @return true if this graph has no Nodes, false otherwise
     */
    public boolean isEmpty() {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Clears this graph (removes all Nodes and all Edges)
     *
     * @spec.modifies this
     * @spec.effects clears all Nodes and Edges within this graph
     */
    public void clearGraph() {
        throw new RuntimeException("Method is yet to be implemented.");
    }
}
