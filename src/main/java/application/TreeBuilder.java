package application;

import domain.model.Tree;
import domain.model.TriangleNode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TreeBuilder {

  Tree buildSimpleTree(List<String> lines) {
    int level = 1;
    Tree tree = new Tree();
    for (String line : lines) {
      log.debug(line);
      String[] splits = line.split("\\W+");
      if (splits.length != level) {
        throw new RuntimeException(String.format("Invalid data. Expected [%d] elements, got: [%s]", level, line));
      }
      for (String value : splits) {
        TriangleNode childNode = buildNode(value);
        tree.addNodeForLevel(level, childNode);
      }
      level++;
    }
    return tree;
  }

  private TriangleNode buildNode(String split) {
    try {
      return new TriangleNode(Integer.parseInt(split.trim()));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid data: " + split);
    }
  }
}

