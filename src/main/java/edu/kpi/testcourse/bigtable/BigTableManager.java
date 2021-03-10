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
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BigTableManager {

  private static final Logger logger = LoggerFactory.getLogger(BigTableManager.class);

  @Inject
  private TokenDao tokenDao;

  @Inject
  private UserDao userDao;

  @Scheduled(fixedRate = "1m", initialDelay = "1m")
  public void dumpData() throws IOException {
    logger.info("Start dump data");
    Map<String, String> users = userDao.getAll();
    String userJson = Main.getGson().toJson(users);
    FileOutputStream fileOutputStream = new FileOutputStream("test.json");
    byte[] userJsonBytes = userJson.getBytes(StandardCharsets.UTF_8);
    fileOutputStream.write(userJsonBytes);
    fileOutputStream.close();
    logger.info("End dump data");
  }

  @EventListener
  public void restoreData(final ServiceReadyEvent event) {
    logger.info("Restore data from file");
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader("test.json"));
      HashMap<String, String> userJson = Main.getGson().fromJson(bufferedReader, HashMap.class);
      userDao.putAll(userJson);
      logger.info("End restore data from file");
    } catch (IOException e) {
      logger.info("Restore file doesnt exist");
    }
  }
}
