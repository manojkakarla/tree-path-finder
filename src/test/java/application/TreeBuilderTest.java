package application;

// Corrected imports
import domain.model.Tree;
import domain.model.TriangleNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreeBuilderTest {

    private final TreeBuilder treeBuilder = new TreeBuilder();

    @Test
    void buildSimpleTree_validInput_shouldBuildTree() {
        List<String> input = List.of("1", "2 3", "4 5 6");
        Tree tree = treeBuilder.buildSimpleTree(input); // buildSimpleTree returns a Tree
        assertNotNull(tree, "Tree should not be null");

        TriangleNode root = tree.getRoot(); // Get the root node from the Tree
        assertNotNull(root, "Root node should not be null");
        assertEquals(1, root.getValue(), "Root value should be 1");

        // Check children of the root node
        TriangleNode child2 = root.getLeft(); // Use Lombok generated getter
        TriangleNode child3 = root.getRight(); // Use Lombok generated getter

        assertNotNull(child2, "Left child of root should not be null");
        assertEquals(2, child2.getValue(), "Value of root's left child should be 2");

        assertNotNull(child3, "Right child of root should not be null");
        assertEquals(3, child3.getValue(), "Value of root's right child should be 3");
        
        // Optionally, check deeper nodes if necessary, e.g., children of node 2
        TriangleNode node4 = child2.getLeft(); // Use Lombok generated getter
        TriangleNode node5_from_child2 = child2.getRight(); // Use Lombok generated getter
        assertNotNull(node4, "Left child of node 2 should not be null");
        assertEquals(4, node4.getValue(), "Value of node 2's left child should be 4");
        assertNotNull(node5_from_child2, "Right child of node 2 should not be null");
        assertEquals(5, node5_from_child2.getValue(), "Value of node 2's right child should be 5");

        // Optionally, check children of node 3
        TriangleNode node5_from_child3 = child3.getLeft(); // Use Lombok generated getter
        TriangleNode node6 = child3.getRight(); // Use Lombok generated getter
        assertNotNull(node5_from_child3, "Left child of node 3 should not be null");
        assertEquals(5, node5_from_child3.getValue(), "Value of node 3's left child should be 5");
        assertNotNull(node6, "Right child of node 3 should not be null");
        assertEquals(6, node6.getValue(), "Value of node 3's right child should be 6");
        
        assertSame(node5_from_child2, node5_from_child3, "Node 5 should be the same instance for both parents");
    }

    @Test
    void buildSimpleTree_invalidRowWidth_shouldThrowRuntimeException() {
        List<String> input = List.of("1", "2 3 4"); // Expected 2 elements at level 2, got 3

        Exception exception = assertThrows(RuntimeException.class, () -> {
            treeBuilder.buildSimpleTree(input);
        });

        String expectedMessage = "Invalid data. Expected [2] elements";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void buildSimpleTree_nonNumericInput_shouldThrowIllegalArgumentException() {
        List<String> input = List.of("1", "a b");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            treeBuilder.buildSimpleTree(input);
        });

        String expectedMessage = "Invalid data: a";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
