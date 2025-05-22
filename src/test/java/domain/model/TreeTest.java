package domain.model;

import application.TreeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    private Tree tree;
    private TreeBuilder treeBuilder; // Not used in current setup, but could be for alternative setup

    @BeforeEach
    void setUp() {
        // treeBuilder = new TreeBuilder(); // Not needed for manual tree construction

        tree = new Tree();
        // Manually simulate the structure built by TreeBuilder using addNodeForLevel
        // This setup directly tests Tree's logic of adding nodes and internal linking.
        
        // Level 1
        TriangleNode node1 = new TriangleNode(1);
        tree.addNodeForLevel(1, node1);

        // Level 2
        TriangleNode node2 = new TriangleNode(2);
        TriangleNode node3 = new TriangleNode(3);
        tree.addNodeForLevel(2, node2);
        tree.addNodeForLevel(2, node3);

        // Level 3
        TriangleNode node4 = new TriangleNode(4);
        TriangleNode node5 = new TriangleNode(5);
        TriangleNode node6 = new TriangleNode(6);
        tree.addNodeForLevel(3, node4);
        tree.addNodeForLevel(3, node5);
        tree.addNodeForLevel(3, node6);
        // The linking is handled by addNodeForLevel within the Tree class
    }

    @Test
    void addNodeForLevel_andLinking_shouldBuildCorrectTreeStructure() {
        TriangleNode root = tree.getRoot(); // getRoot() is now public
        assertNotNull(root, "Root should not be null");
        assertEquals(1, root.getValue(), "Root value should be 1");

        TriangleNode node2 = root.getLeft(); // Use Lombok generated getter
        TriangleNode node3 = root.getRight(); // Use Lombok generated getter

        assertNotNull(node2, "Node 2 (left child of root) should not be null");
        assertEquals(2, node2.getValue(), "Node 2 value should be 2");
        assertNotNull(node3, "Node 3 (right child of root) should not be null");
        assertEquals(3, node3.getValue(), "Node 3 value should be 3");

        // Children of Node 2 (Value 2)
        TriangleNode node4 = node2.getLeft(); // Use Lombok generated getter
        TriangleNode node5_fromNode2 = node2.getRight(); // Use Lombok generated getter
        assertNotNull(node4, "Node 4 (left child of Node 2) should not be null");
        assertEquals(4, node4.getValue(), "Node 4 value should be 4");
        assertNotNull(node5_fromNode2, "Node 5 (right child of Node 2) should not be null");
        assertEquals(5, node5_fromNode2.getValue(), "Node 5 (from Node 2) value should be 5");

        // Children of Node 3 (Value 3)
        TriangleNode node5_fromNode3 = node3.getLeft(); // Use Lombok generated getter
        TriangleNode node6 = node3.getRight(); // Use Lombok generated getter
        assertNotNull(node5_fromNode3, "Node 5 (left child of Node 3) should not be null");
        assertEquals(5, node5_fromNode3.getValue(), "Node 5 (from Node 3) value should be 5");
        assertNotNull(node6, "Node 6 (right child of Node 3) should not be null");
        assertEquals(6, node6.getValue(), "Node 6 value should be 6");

        // Verify that node 5 is the same instance for both parents (shared node)
        assertSame(node5_fromNode2, node5_fromNode3, "Node 5 should be the same instance for both parents");
    }

    @Test
    void calculateMinPath_shouldReturnCorrectPathAndSum() {
        String expected = "min-path: 1 -> 2 -> 4 and sum is: 7";
        assertEquals(expected, tree.calculateMinPath());
    }

    @Test
    void calculateMaxPath_shouldReturnCorrectPathAndSum() {
        String expected = "max-path: 1 -> 3 -> 6 and sum is: 10";
        assertEquals(expected, tree.calculateMaxPath());
    }

    @Test
    void toString_shouldReturnFormattedString() {
        String treeString = tree.toString();
        assertNotNull(treeString);
        assertFalse(treeString.isEmpty(), "toString output should not be empty");

        // Expected format based on Tree.toString() and TriangleNode.toString()
        // The Tree.toString() format is:
        // "\n" +
        // nodesLevelMap.entrySet().stream().map(entry -> entry.getKey() + ": " +
        // " ".repeat(levels - (counter.getAndIncrement())) + // counter is 0,1,2 for levels 1,2,3
        // entry.getValue()).collect(Collectors.joining("\n", "", ""));
        // entry.getValue() is List<TriangleNode>, which calls TriangleNode.toString() (just the value).

        // levels = 3
        // Level 1 (key 1): counter=0. repeat = 3-0 = 3 spaces. Output: "1:   [1]"
        // Level 2 (key 2): counter=1. repeat = 3-1 = 2 spaces. Output: "2:  [2, 3]"
        // Level 3 (key 3): counter=2. repeat = 3-2 = 1 space.  Output: "3: [4, 5, 6]"
        // Total spaces after colon = 1 (from ": ") + (levels - counter.getAndIncrement())
        // L1: 1 + (3-0) = 4 spaces. "1:    [1]"
        // L2: 1 + (3-1) = 3 spaces. "2:   [2, 3]"
        // L3: 1 + (3-2) = 2 spaces. "3:  [4, 5, 6]"

        String expectedString = "\n" +
                                "1:    [1]\n" + // 4 spaces after :
                                "2:   [2, 3]\n" + // 3 spaces after :
                                "3:  [4, 5, 6]";    // 2 spaces after :

        assertEquals(expectedString, treeString);

        // More granular checks (already covered by the full string assertEquals, but can be kept for clarity)
        assertTrue(treeString.contains("1:    [1]"), "toString should contain level 1 info: " + treeString);
        assertTrue(treeString.contains("2:   [2, 3]"), "toString should contain level 2 info: " + treeString);
        assertTrue(treeString.contains("3:  [4, 5, 6]"), "toString should contain level 3 info: " + treeString);
    }
}
