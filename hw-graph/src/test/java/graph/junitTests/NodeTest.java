package graph.junitTests;

import graph.*;
import org.junit.*;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * Node class.
 */
public class NodeTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private final Node a = new Node("a");
    private final Node b = new Node("b");
    private final Node a2 = new Node("a");

    /**
     * Tests if data within node can be accessed.
     */
    @Test
    public void testGetData() {
        assertEquals("a", a.getData());
        assertEquals("b", b.getData());
    }

    /**
     * Tests if two Nodes are equal.
     */
    @Test
    public void testEquals() {
        assertTrue(a.equals(a2));
        assertFalse(a.equals(b));
    }

    /**
     * Tests if hashCode of two Nodes are equal if both Nodes contain same data.
     */
    @Test
    public void testHashCode() {
        assertTrue(a.hashCode() == a2.hashCode());
        assertFalse(a.hashCode() == b.hashCode());
    }

    /**
     * Tests that the string representation of a Node is in the form
     * "Node: {data}".
     */
    @Test
    public void testToString() {
        assertEquals("Node: a", a.toString());
        assertEquals("Node: b", b.toString());
    }
}
