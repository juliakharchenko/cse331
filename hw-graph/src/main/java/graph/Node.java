package graph;

/**
 * This class represents a single, immutable node storing data as a String.
 *
 * Specification fields:
 *  @spec.specfield data : String  // The information stored within this node.
 *
 * Abstract Invariant:
 *  A node's data stored are never be null.
 */
public class Node{

    /**
     * Constructs a new Node with the given String representing the Node
     *
     * @param data the String this node represents
     * @spec.requires data != null
     * @spec.modifes this
     * @spec.effects Constructs a new Node equal to "data".
     */
    public Node(String data) {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Returns the String stored within "data" of this Node
     *
     * @return the String representing this Node
     * @spec.requires this != null
     */
    public String getData() {
        throw new RuntimeException("Method is yet to be implemented.");
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
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    @Override
    public int hashCode() {
        throw new RuntimeException("Method is yet to be implemented.");
    }

    /**
     * Returns a string representation of this Node. A valid example output with data = "Hello World"
     * is "Node: Hello World".
     *
     * @return a String representation of the data stored in this in the form "Node: {data}"
     */
    @Override
    public String toString() {
        throw new RuntimeException("Method is yet to be implemented.");
    }

}
