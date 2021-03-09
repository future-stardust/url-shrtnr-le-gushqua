package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.auth.JwtUtils;
import edu.kpi.testcourse.bigtable.TokenDao;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.text.ParseException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST API controller that provides logic for user logout.
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Produces
@Controller
public class LogoutController {

  private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

  @Inject
  private TokenDao tokenDao;

  /**
   * Logouts user.
   *
   * @param authorization user JWT token
   * @return http response
   */
  @Get(value = "/signout")
  public MutableHttpResponse<JsonError> signOut(@Header String authorization)
      throws ParseException {
    logger.info("Sign out");

    String token = JwtUtils.getToken(authorization);
    String email = JwtUtils.getUsername(token);
    tokenDao.add(email, token);

    return HttpResponse.ok();
  }

}
