package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.auth.JwtUtils;
import edu.kpi.testcourse.auth.Pbkdf2PasswordEncoder;
import edu.kpi.testcourse.auth.UserCredentials;
import edu.kpi.testcourse.bigtable.TokenDao;
import edu.kpi.testcourse.bigtable.UserDao;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.text.ParseException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST API controller that provides logic for user authorization.
 */
@Produces
@Controller
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @Inject
  private UserDao userDao;

  @Inject
  private TokenDao tokenDao;

  /**
   * Registers user.
   *
   * @param credentials user email and password
   * @return http response
   */
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Post(value = "/signup")
  public MutableHttpResponse<JsonError> signUp(@Body UserCredentials credentials) {
    logger.info("Sign up");

    if (userDao.get(credentials.getUsername()) != null) {
      return HttpResponse.unprocessableEntity().body(new JsonError("User already exists"));
    }
    String encodedPassword = Pbkdf2PasswordEncoder.encodePassword(credentials.getPassword());
    userDao.put(credentials.getUsername(), encodedPassword);

    return HttpResponse.ok();
  }

  /**
   * Logouts user.
   *
   * @param authorization user JWT token
   * @return http response
   */
  @Secured(SecurityRule.IS_AUTHENTICATED)
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
