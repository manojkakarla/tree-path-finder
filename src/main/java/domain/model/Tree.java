package domain.model;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
  private Map<Integer, List<TriangleNode>> nodesLevelMap = new HashMap<>();

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
    String path = Joiner.on(" -> ").join(Lists.reverse(sumValue.getValue()));
    return String.format("%s: %s and sum is: %d", pathType, path, sumValue.getKey());
  }

  private TriangleNode getRoot() {
    return nodesLevelMap.get(1).get(0);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("\n");
    for (Map.Entry<Integer, List<TriangleNode>> entry : nodesLevelMap.entrySet()) {
      builder.append(entry.getKey()).append(": ")
              .append(entry.getValue())
              .append("\n");
    }
    return builder.toString();
  }
}
