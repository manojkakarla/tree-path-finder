package domain.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Tree {
  private final Map<Integer, List<TriangleNode>> nodesLevelMap = new HashMap<>();

  public void addNodeForLevel(int level, TriangleNode node) {
    List<TriangleNode> triangleNodes = nodesLevelMap.computeIfAbsent(level, k -> new ArrayList<>(level));
    triangleNodes.add(node);
    if (level > 1)
      createLinks(level, triangleNodes.size() - 1, node);
  }

  private void createLinks(int level, int index, TriangleNode node) {
    List<TriangleNode> nodesForLevel = nodesLevelMap.get(level - 1);
    if (index != 0) {
      TriangleNode parent = nodesForLevel.get(index - 1);
      parent.setRight(node);
    }
    if (index != nodesForLevel.size()) {
      TriangleNode parent = nodesForLevel.get(index);
      parent.setLeft(node);
    }
  }

  public String calculateMinPath() {
    SimpleEntry<Integer, List<Integer>> sumValue = getRoot().getMinPathValue();
    return buildResult(sumValue, "min-path");
  }

  public String calculateMaxPath() {
    SimpleEntry<Integer, List<Integer>> sumValue = getRoot().getMaxPathValue();
    return buildResult(sumValue, "max-path");
  }

  private String buildResult(SimpleEntry<Integer, List<Integer>> sumValue, String pathType) {
    List<Integer> values = sumValue.getValue();
    Collections.reverse(values);
    String path = values.stream().map(Object::toString).collect(Collectors.joining(" -> "));
    return String.format("%s: %s and sum is: %d", pathType, path, sumValue.getKey());
  }

  private TriangleNode getRoot() {
    return nodesLevelMap.get(1).get(0);
  }

  @Override
  public String toString() {
    AtomicInteger counter = new AtomicInteger();
    int levels = nodesLevelMap.size();
    return nodesLevelMap.entrySet().stream().map(entry -> entry.getKey() + ": " +
            " ".repeat(levels - (counter.getAndIncrement())) +
            entry.getValue()).collect(Collectors.joining("\n", "\n", ""));
  }
}
