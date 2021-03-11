package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.Main;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import javax.inject.Inject;

/**
 * REST API controller that provides logic for Micronaut framework.
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
public class ApiController {

  record ExampleClass(String first, String second) {}

  @Get(value = "/hello", produces = MediaType.APPLICATION_JSON)
  public String hello() {
    return Main.getGson().toJson(new ExampleClass("Hello", "world!"));
  }

  /**
   * Create an URL alias.
   *
   * @param url string, required - link which has to be shortened.
   * @param alias string, optional - desired alias for a full link.
   *
   * @return OK/error.
   */
  @Post(value = "/urls/shorten", consumes = MediaType.APPLICATION_JSON)
  public HttpResponse<Object> shortenUrl(String url, String alias) {
    return HttpResponse.ok();
  }

  /**
   * List of user's aliases.
   *
   * @return array of user's aliases.
   */
  @Get(value = "/urls", produces = MediaType.APPLICATION_JSON)
  public String[] getUserAliases() {
    return new String[]{"Some aliases"};
  }

  /**
   * Delete alias.
   *
   * @param alias string, required - alias, that needs to be removed.
   * @return OK/error.
   */
  @Delete(value = "/urls/<alias>")
  public HttpResponse<Object> deleteAlias(String alias) {
    return HttpResponse.ok();
  }

  /**
   * Redirect by alias to a full link.
   *
   * @param alias string, required - alias for the full link.
   * @return Redirect/Error.
   */
  @Get(value = "/r/<alias>", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<Object> redirectToUrl(String alias) {
    return HttpResponse.ok();
  }
}
