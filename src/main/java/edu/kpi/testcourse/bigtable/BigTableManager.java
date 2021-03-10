package edu.kpi.testcourse.bigtable;

import edu.kpi.testcourse.Main;
import io.micronaut.discovery.event.ServiceReadyEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Scheduled;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BigTableManager {

  private static final Logger logger = LoggerFactory.getLogger(BigTableManager.class);

  @Inject
  private List<Serializible> daos;

  @Scheduled(fixedRate = "1m", initialDelay = "1m")
  public void dumpData() throws IOException {
    logger.info("Start dump data");
    for (Serializible dao : daos) {
      Path path = Path.of(dao.getFileName());
      String json = dao.toJson();
      Files.writeString(path, json);
    }
    logger.info("End dump data");
  }

  @EventListener
  public void restoreData(final ServiceReadyEvent event) {
    logger.info("Restore data from file");
    try {
      for (Serializible dao : daos) {
        Path path = Path.of(dao.getFileName());
        String json = Files.readString(path);
        dao.fromJson(json);
      }
      logger.info("End restore data from file");
    } catch (IOException e) {
      logger.info("Restore file doesnt exist");
    }
  }
}
