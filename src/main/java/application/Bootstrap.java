package application;

import domain.model.Tree;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
public class Bootstrap {

  public static void main(String[] args) throws IOException {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    log.info("application started at {}", new Date(stopWatch.getStartTime()));
    if (args.length < 1) {
      log.error("Please enter a filename to read the data from");
      System.exit(0);
    }

    String filename = args[0];
      List<String> lines = null;
    try {
      lines = IOUtils.readLines(new FileReader((filename)));
    } catch (IOException e) {
      log.error("Unable to read from the file: {}", filename);
      System.exit(0);
    }

    stopWatch.split();
    log.info("Started building tree after mSec: {}", stopWatch.getSplitTime());
    Tree tree = new TreeBuilder().buildSimpleTree(lines);
    log.info("Built tree:" + tree.toString());
    stopWatch.split();
    log.info("Finished building tree in mSec: {}", stopWatch.getSplitTime());
    log.info(tree.calculateMinPath());
    stopWatch.split();
    log.info("Calculated min-path in mSec: {}", stopWatch.getSplitTime());
    log.info(tree.calculateMaxPath());
    stopWatch.stop();
    log.info("Calculated max-path in mSec: {}", stopWatch.getSplitTime());


  }
}
