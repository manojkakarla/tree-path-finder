package application;

import java.util.Stack;

public class StopWatch {
  private long start;
  private long stop;
  private boolean running = false;
  private final Stack<Long> splits = new Stack<>();
  private long splitTime;

  public void start() {
    running = true;
    start = System.currentTimeMillis();
    splits.clear();
    splits.push(start);
  }

  public void stop() {
    split();
    stop = splits.peek();
    running = false;
  }
  public void split() {
    Long previous = splits.peek();
    long split = System.currentTimeMillis();
    splits.push(split);
    splitTime = splits.peek() - previous;
  }

  public long elapsedTime() {
    if(running) stop();
    return stop - start;
  }

  public long getStartTime() {
    return start;
  }

  public Object getSplitTime() {
    return splitTime;
  }
}
