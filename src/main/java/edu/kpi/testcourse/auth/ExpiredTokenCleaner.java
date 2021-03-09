package edu.kpi.testcourse.auth;

import edu.kpi.testcourse.bigtable.TokenDao;
import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.security.token.jwt.validator.ExpirationJwtClaimsValidator;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scheduler to delete expired JWT tokens.
 */
@Singleton
public class ExpiredTokenCleaner {

  private static final Logger logger = LoggerFactory.getLogger(ExpiredTokenCleaner.class);

  @Inject
  private TokenDao tokenDao;

  @Inject
  private ExpirationJwtClaimsValidator validator;

  /**
   * Removes invalid JWT tokens that have expired.
   *
   * @throws ParseException if the string couldn't be parsed to a valid JWT token.
   */
  @Scheduled(fixedRate = "15m", initialDelay = "15m")
  public void removeExpiredTokens() throws ParseException {
    logger.info("Remove expired tokens");

    Map<String, Set<String>> tokens = tokenDao.getAll();
    for (String email : tokens.keySet()) {
      for (String token : tokens.get(email)) {

        boolean tokenExpired = validator.validate(JwtUtils.getClaims(token), null);
        if (tokenExpired) {
          tokenDao.remove(email, token);
        }
      }
    }
  }

}
