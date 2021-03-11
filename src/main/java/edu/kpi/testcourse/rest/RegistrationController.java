package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.auth.Pbkdf2PasswordEncoder;
import edu.kpi.testcourse.auth.UserCredentials;
import edu.kpi.testcourse.bigtable.UserDao;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST API controller that provides logic for user registration.
 */
@Secured(SecurityRule.IS_ANONYMOUS)
@Produces
@Controller
public class RegistrationController {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

  @Inject
  private UserDao userDao;

  /**
   * Registers user.
   *
   * @param credentials user email and password
   * @return http response
   */
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

}
