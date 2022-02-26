package graph;

/**
 * This class represents a single, immutable, directed edge represented by type E. An edge points
 * to a destination Node (child node) represented by type N. Edges also store data representing
 * the label of the edge.
 *
 * A directed edge means the edge is one-way (Ex. Edge 'A' points to Node B in that direction
 * and that direction only).
 *
 * Specification fields:
 * @spec.specfield label : E // The label of this edge.
 * @spec.specfield target : Node // Ending destination (child node) of this edge (where edge points to)
 *
 * Abstract Invariant:
 * The end point of the edge (child node) and the label stored are never null.
 */
public class Edge<E, N> {

    /**
     * Holds the label of the edge.
     */
    private final E label;

    /**
     * Holds the target (child) node of the edge.
     */
    private final Node<N> target;

    // Abstraction Function (this):
    // Edge, e, represents a labeled edge that points
    // to a child Node (target) where this.label = e.label
    // and this.target = e.label.

    // Representation Invariant for every Edge e:
    // Edge's label and Edge's target Node are both never null

    /**
     *
     * @param label the value the label of this edge represents
     * @param target the destination (child) node of this edge
     * @spec.requires label != null, target != null
     * @spec.effects Constructs a new Edge with {@code label} equal to "label", and {@code target}
     * equal to "target"
     */
    public Edge(E label, Node<N> target) {
        this.label = label;
        this.target = target;
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    public void checkRep() {
        assert (this.label != null && this.target != null);
    }


    /**
     * Returns the target (child) node of this edge
     *
     * @return from : the target destination node of this edge
     * @spec.requires this != null
     */
    public Node<N> getChild() {
        return this.target;
    }

    /**
     * Returns the String stored within "label" of this Edge
     *
     * @return label: String representing this Edge
     * @spec.requires this != null
     */
    public E getLabel() {
        return this.label;
    }

    /**
     * Standard equality operation.
     *
     * @param obj the object to be compared for equality
     * @return true if and only if 'obj' is an instance of an Edge and 'this' and 'obj' represent
     * the same child nodes and edge label.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge<?,?>)) return false;
        Edge<?,?> e = (Edge<?,?>) obj;
        return this.label.equals(e.label) && this.target.equals(e.target);
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    @Override
    public int hashCode() {
        return this.label.hashCode() ^ this.target.hashCode();
    }

}
