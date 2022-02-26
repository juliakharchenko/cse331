package pathfinder.junitTests;

import graph.*;
import pathfinder.*;
import org.junit.*;
import org.junit.rules.Timeout;

/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * DijkstrasAlgorithm class.
 */
public class TestDijkstrasAlgorithm {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private DLGraph<String,Double> testGraph;
    private DijkstrasAlgorithm shortestPath;
    private final Node<String> a = new Node<>("a");
    private final Node<String> b = new Node<>("b");
    private final Node<String> c = new Node<>("c");



    @Before
    public void setUp() throws Exception {
        testGraph = new DLGraph<>();
        shortestPath = new DijkstrasAlgorithm();
        testGraph.addNode(a);
        testGraph.addNode(b);
    }

    @Test (expected = IllegalArgumentException.class)
    public void dijkstrasOnNullGraph() {
        shortestPath.dijkstrasPath(null, a.getData(), b.getData());
    }

    @Test (expected = IllegalArgumentException.class)
    public void dijkstrasWithNullStart() {
        shortestPath.dijkstrasPath(testGraph, null, b.getData());
    }

    @Test (expected = IllegalArgumentException.class)
    public void dijkstrasWithNullEnd() {
        shortestPath.dijkstrasPath(testGraph, a.getData(), null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void dijkstrasWithStartNotInGraph() {
        shortestPath.dijkstrasPath(testGraph, c.getData(), b.getData());
    }

    @Test (expected = IllegalArgumentException.class)
    public void dijkstrasWithEndNotInGraph() {
        shortestPath.dijkstrasPath(testGraph, b.getData(), c.getData());
    }
}
