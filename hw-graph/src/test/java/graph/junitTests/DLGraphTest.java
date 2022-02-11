package graph.junitTests;

import graph.*;
import org.junit.*;
import org.junit.rules.Timeout;
import java.util.*;

import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * DLGraph class.
 */
public class DLGraphTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private DLGraph graph1;
    private final Node a = new Node("a");
    private final Node b = new Node("b");
    private final Node c = new Node("c");

    @Before
    public void setUp() throws Exception {
        graph1 = new DLGraph();
    }
    @Test
    public void testSizeOnConstruction() { assertEquals(0, graph1.size()); }

    @Test
    public void testSize() {
        graph1.addNode(a);
        assertEquals(1, graph1.size());
    }

    @Test
    public void testEmptyOnConstruction() { assertTrue(graph1.isEmpty()); }

    @Test
    public void testEmpty() {
        graph1.addNode(a);
        assertFalse(graph1.isEmpty());
    }

    @Test
    public void testContainsNodeOnConstruction() { assertFalse(graph1.containsNode(a)); }

    @Test
    public void testContainsNode() {
        graph1.addNode(a);
        assertTrue(graph1.containsNode(a));
        assertFalse(graph1.containsNode(b));
    }

    @Test
    public void testGetAllNodesOnConstruction() {
        Set<Node> allNodes = new HashSet<>();
        assertEquals(allNodes, graph1.getAllNodes());
    }

    @Test
    public void testGetAllNodes() {
        Set<Node> allNodes = new HashSet<>();
        allNodes.add(a);
        allNodes.add(b);
        graph1.addNode(a);
        graph1.addNode(b);
        assertEquals(allNodes, graph1.getAllNodes());
    }

    @Test
    public void testGetAllEdgesForNodeWithNoEdges() {
        Set<Edge> allEdges = new HashSet<>();
        graph1.addNode(a);
        assertEquals(allEdges, graph1.getAllEdges(a));
    }

    @Test
    public void testGetAllEdges() {
        Set<Edge> allEdges = new HashSet<>();
        graph1.addNode(a);
        graph1.addNode(b);
        allEdges.add(new Edge("AB1", b));
        allEdges.add(new Edge("AB2", b));
        graph1.addEdge(a, b, "AB1");
        graph1.addEdge(a, b, "AB2");
        assertEquals(allEdges, graph1.getAllEdges(a));
    }

    @Test
    public void testGetAllChildrenForNodeWithNoChildren() {
        Set<Node> allChildren = new HashSet<>();
        graph1.addNode(a);
        assertEquals(allChildren, graph1.getAllChildren(a));
    }

    @Test
    public void testGetAllChildren() {
        Set<Node> allChildren = new HashSet<>();
        Node c = new Node("c");
        allChildren.add(b);
        allChildren.add(c);
        graph1.addNode(a);
        graph1.addNode(b);
        graph1.addNode(c);
        graph1.addEdge(a, b,"AB");
        graph1.addEdge(a, c, "AC");
        assertEquals(allChildren, graph1.getAllChildren(a));
    }

    @Test
    public void testNumEdges() {
        graph1.addNode(a);
        graph1.addNode(b);
        graph1.addEdge(a, b, "AB1");
        graph1.addEdge(a, b, "AB2");
        assertEquals(2, graph1.numEdges(a, b));
    }

    @Test
    public void testNumEdgesReverse() {
        graph1.addNode(a);
        graph1.addNode(b);
        graph1.addEdge(a, b, "AB1");
        graph1.addEdge(a, b, "AB2");
        assertEquals(0, graph1.numEdges(b, a));
    }

    @Test
    public void testClearGraph() {
        graph1.addNode(a);
        graph1.addNode(b);
        graph1.addEdge(a, b, "AB1");
        graph1.addEdge(a, b, "AB2");
        graph1.clearGraph();
        assertTrue(graph1.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addEdgeNoParent() {
        graph1.addNode(a);
        graph1.addEdge(b, a, "BA1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addEdgeNoChild() {
        graph1.addNode(a);
        graph1.addEdge(a, b, "AB1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllEdgesNoNode() {
        graph1.addNode(a);
        graph1.getAllEdges(b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllChildrenNoNode() {
        graph1.addNode(a);
        graph1.addNode(c);
        graph1.addEdge(a, c, "AC1");
        graph1.getAllChildren(b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void numEdgesNoParent() {
        graph1.addNode(a);
        graph1.addNode(c);
        graph1.addEdge(a, c, "AC1");
        graph1.numEdges(b, a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void numEdgesNoChild() {
        graph1.addNode(a);
        graph1.addNode(c);
        graph1.addEdge(a, c, "AC1");
        graph1.numEdges(a, b);
    }
}
