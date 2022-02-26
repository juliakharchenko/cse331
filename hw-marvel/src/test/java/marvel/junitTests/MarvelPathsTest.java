package marvel.junitTests;

import graph.*;
import marvel.*;
import org.junit.*;
import org.junit.rules.Timeout;


/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * MarvelPaths class.
 */
public class MarvelPathsTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private DLGraph<String,String> testGraph;

    @Before
    public void setUp() throws Exception {
        testGraph = MarvelPaths.buildGraph("nbaPlayers.csv");
    }

    @Test (expected = IllegalArgumentException.class)
    public void buildGraphNullFile() { MarvelPaths.buildGraph(null); }

    @Test (expected = IllegalArgumentException.class)
    public void bfsOnNullGraph() { MarvelPaths.shortestPath(null, "LEBRON-JAMES", "MICHAEL-JORDAN"); }

    @Test (expected = IllegalArgumentException.class)
    public void bfsWithNullStartCharacter() {
        MarvelPaths.shortestPath(testGraph, null, "MAGIC-JOHNSON");
    }

    @Test (expected = IllegalArgumentException.class)
    public void bfsWithNullEndCharacter() {
        MarvelPaths.shortestPath(testGraph, "MAGIC-JOHNSON", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void bfsWithStartCharacterNotInGraph() {
        MarvelPaths.shortestPath(testGraph, "SHAQUILLE-ONEAL", "MICHAEL-JORDAN");
    }

    @Test (expected = IllegalArgumentException.class)
    public void bfsWithEndCharacterNotInGraph() {
        MarvelPaths.shortestPath(testGraph, "MICHAEL-JORDAN", "SHAQUILLE-ONEAL");
    }
}
