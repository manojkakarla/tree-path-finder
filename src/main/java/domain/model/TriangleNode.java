package domain.model;

import lombok.Data;

import javax.annotation.Nonnull;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import java.util.List;

@Data
public class TriangleNode {
  @Nonnull
  private final Integer value;
  private TriangleNode left;
  private TriangleNode right;
  private TriangleNode next;

  public SimpleEntry<Integer, List<Integer>> getMinPathValue() {
    if (hasChildren()) {
      SimpleEntry<Integer, List<Integer>> entry = selectMin(left.getMinPathValue(), right.getMinPathValue());
      entry.getValue().add(value);
      return new SimpleEntry<>(entry.getKey() + value, entry.getValue());

    } else {
      LinkedList<Integer> elements = new LinkedList<Integer>();
      elements.add(value);
      return new SimpleEntry<>(value, elements);
    }

  }

  public SimpleEntry<Integer, List<Integer>> getMaxPathValue() {
    if (hasChildren()) {
      SimpleEntry<Integer, List<Integer>> entry = selectMax(left.getMaxPathValue(), right.getMaxPathValue());
      entry.getValue().add(value);
      return new SimpleEntry<>(entry.getKey() + value, entry.getValue());

    } else {
      LinkedList<Integer> elements = new LinkedList<Integer>();
      elements.add(value);
      return new SimpleEntry<>(value, elements);
    }

  }

  private SimpleEntry<Integer, List<Integer>> selectMin(
          SimpleEntry<Integer, List<Integer>> leftSumValue,
          SimpleEntry<Integer, List<Integer>> rightSumValue) {
    return leftSumValue.getKey() < rightSumValue.getKey() ? leftSumValue : rightSumValue;

  }

  private SimpleEntry<Integer, List<Integer>> selectMax(
          SimpleEntry<Integer, List<Integer>> leftSumValue,
          SimpleEntry<Integer, List<Integer>> rightSumValue) {
    return leftSumValue.getKey() > rightSumValue.getKey() ? leftSumValue : rightSumValue;

  }

  @Override
  public String toString() {
    return value.toString();
  }

  private boolean hasChildren() {
    return left != null;
  }
}
