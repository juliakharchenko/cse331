package graph.junitTests;

import graph.*;
import org.junit.*;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * Edge class.
 */
public class EdgeTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private final Node a = new Node("a");
    private final Node b = new Node("b");

    private final Edge AB1 = new Edge("AB1", b);
    private final Edge AB2 = new Edge("AB2", b);
    private final Edge AB1Prime = new Edge("AB1", b);


    /**
     * Tests if child node of an edge can be accessed.
     */
    @Test
    public void testGetChild() {
        assertTrue(AB1.getChild().equals(b));
        assertTrue(AB2.getChild().equals(b));
    }

    /**
     * Tests if the label of an edge can be accessed.
     */
    @Test
    public void testGetLabel() {
        assertEquals("AB1", AB1.getLabel());
        assertEquals("AB2", AB2.getLabel());
    }

    /**
     * Tests that two edges with different labels are not equal.
     */
    @Test
    public void testEqualsDifferentLabel() {
        assertFalse(AB1.equals(AB2));
    }

    /**
     * Tests that two edges with the same label and child nodes are equal.
     */
    @Test
    public void testEqualsSameLabel() {
        assertTrue(AB1.equals(AB1Prime));
    }

    @Test
    public void testHashCode() {
        assertEquals("AB1".hashCode() ^ "b".hashCode(), AB1.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("b(AB1)", AB1.toString());
        assertEquals("b(AB2)", AB2.toString());
    }

}
