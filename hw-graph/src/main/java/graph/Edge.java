package graph;

/**
 * This class represents a single, immutable, directed edge. An edge points
 * to a destination Node (child node). Edges also store data as a String representing
 * the label of the edge.
 *
 * A directed edge means the edge is one-way (Ex. Edge 'A' points to Node B in that direction
 * and that direction only).
 *
 * Specification fields:
 * @spec.specfield label : String // The label of this edge.
 * @spec.specfield target : Node // Ending destination (child node) of this edge (where edge points to)
 *
 * Abstract Invariant:
 * The end point of the edge (child node) and the label stored are never null.
 */
public class Edge {

    /**
     * Holds the label of the edge.
     */
    private final String label;

    /**
     * Holds the target (child) node of the edge.
     */
    private final Node target;

    // Abstraction Function (this):
    // Edge, e, represents a labeled edge that points
    // to a child Node (target) where this.label = e.label
    // and this.target = e.label.

    // Representation Invariant for every Edge e:
    // Edge's label and Edge's target Node are both never null

    /**
     *
     * @param label the String the label of this edge represents
     * @param target the destination (child) node of this edge
     * @spec.requires label != null, target != null
     * @spec.modifies this
     * @spec.effects Constructs a new Edge with {@code label} equal to "label", and {@code target}
     * equal to "target"
     */
    public Edge(String label, Node target) {
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
    public Node getChild() {
        return this.target;
    }

    /**
     * Returns the String stored within "label" of this Edge
     *
     * @return label: String representing this Edge
     * @spec.requires this != null
     */
    public String getLabel() {
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
        if (!(obj instanceof Edge)) return false;
        Edge e = (Edge) obj;
        return this.label.equals(e.label) && this.target.equals(e.target);
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    @Override
    public int hashCode() {
        return this.label.hashCode() + this.target.hashCode();
    }

    /**
     * Returns a string representation of this Edge. A valid example output with label = "Hi",
     * target = A is "A(Hi)".
     *
     * @return a String representation of the data stored in this in the form
     * "{target}({label})".
     */
    @Override
    public String toString() {
        return this.target.toString() + "(" + this.label + ")";
    }
}
