package graph;

/**
 * This class represents a single, immutable node storing some data
 *
 * Specification fields:
 *  @spec.specfield data : E  // The information stored within this node
 *
 * Abstract Invariant:
 *  A node's data stored are never be null.
 */
public class Node<E extends Comparable<E>> implements Comparable<Node<E>> {

    /**
     * Holds the data of the Node.
     */
    private final E data;

    // Abstraction Function (this):
    // Node, n, represents some data
    // where n.data == this.data.

    // Representation Invariant for every Node n:
    // data stored within  Node n is never null

    /**
     * Constructs a new Node with the given data representing the Node
     *
     * @param data the value this node represents
     * @spec.requires data != null
     * @spec.effects Constructs a new Node equal to "data".
     */
    public Node(E data) {
        this.data = data;
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.data != null);
    }

    /**
     * Returns the data stored within "data" of this Node
     * @spec.requires this != null
     *
     * @return the data representing this Node
     */
    public E getData() {
        return this.data;
    }

    /**
     * Standard equality operation.
     *
     * @param obj the object to be compared for equality
     * @return true if and only if 'obj' is an instance of a Node and 'this' and 'obj' represent
     * the same data.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node<?>)) return false;
        Node<?> n = (Node<?>) obj;
        return this.data.equals(n.data);
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    @Override
    public int hashCode() {
        return data.hashCode();
    }


    /**
     * Compares this node to another object of type Node
     *
     * @param other the other Node object in comparison
     * @return positive integer if data of this is lexicographically greater than data of other
     *         negative integer if data of this is lexicographically less than data of other
     *         0 if this == other
     */
    @Override
    public int compareTo(Node<E> other) {
        other.checkRep();
        return this.data.compareTo(other.data);
    }
}
