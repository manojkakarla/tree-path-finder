package domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TriangleNodeTest {

    @Test
    void singleNode_getPathValues_shouldReturnNodeItself() {
        TriangleNode node = new TriangleNode(10);

        // Test min path
        Map.Entry<Integer, List<Integer>> minPathResult = node.getMinPathValue();
        assertNotNull(minPathResult);
        assertEquals(10, minPathResult.getKey());
        assertEquals(List.of(10), minPathResult.getValue());

        // Test max path
        Map.Entry<Integer, List<Integer>> maxPathResult = node.getMaxPathValue();
        assertNotNull(maxPathResult);
        assertEquals(10, maxPathResult.getKey());
        assertEquals(List.of(10), maxPathResult.getValue());
    }

    @Test
    void smallTree_getPathValues_shouldReturnCorrectPaths() {
        TriangleNode root = new TriangleNode(1);
        TriangleNode child2 = new TriangleNode(2);
        TriangleNode child3 = new TriangleNode(3);

        root.setLeft(child2); // Use Lombok generated setter
        root.setRight(child3); // Use Lombok generated setter

        // Test min path
        Map.Entry<Integer, List<Integer>> minPathResult = root.getMinPathValue();
        assertNotNull(minPathResult);
        assertEquals(1 + 2, minPathResult.getKey());
        assertEquals(List.of(2, 1), minPathResult.getValue());

        // Test max path
        Map.Entry<Integer, List<Integer>> maxPathResult = root.getMaxPathValue();
        assertNotNull(maxPathResult);
        assertEquals(1 + 3, maxPathResult.getKey());
        assertEquals(List.of(3, 1), maxPathResult.getValue());
    }
}
