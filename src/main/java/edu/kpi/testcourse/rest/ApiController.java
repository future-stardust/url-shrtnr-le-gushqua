package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.bigtable.Alias;
import edu.kpi.testcourse.bigtable.AliasDao;
import edu.kpi.testcourse.utils.ShortenGenerator;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST API controller that provides logic for Micronaut framework.
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
public class ApiController {

  private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

  record ExampleClass(String first, String second) {}

  @Get(value = "/hello", produces = MediaType.APPLICATION_JSON)
  public String hello() {
    return Main.getGson().toJson(new ExampleClass("Hello", "world!"));
  }

  @Inject
  public AliasDao aliasDao;

  /**
   * Create an URL alias.
   *
   * @param url string, required - link which has to be shortened.
   * @param alias string, optional - desired alias for a full link.
   *
   * @return OK/error.
   */
  @Post(value = "/urls/shorten", consumes = MediaType.APPLICATION_JSON)
  public HttpResponse<Object> shortenUrl(String url, @Nullable String alias, Principal principal) {
    if (alias == null) {
      alias = ShortenGenerator.generate();
    }
    if (aliasDao.get(alias) == null) {
      Alias aliasObj = new Alias(alias, url, principal.getName());
      aliasDao.add(alias, aliasObj);
      return HttpResponse.ok(Main.getGson().toJson(aliasObj));
    } else {
      return HttpResponse.badRequest("Alias is not unique!");
    }
  }

  /**
   * List of user's aliases.
   *
   * @return array of user's aliases.
   */
  @Get(value = "/urls", produces = MediaType.APPLICATION_JSON)
  public String getUserAliases(Principal principal) {
    return Main.getGson().toJson(aliasDao.getAllByUser(principal.getName()));
  }

  /**
   * Delete alias.
   *
   * @param alias string, required - alias, that needs to be removed.
   * @return OK/error.
   */
  @Delete(value = "/urls/{alias}")
  public HttpResponse<Object> deleteAlias(String alias, Principal principal) {
    Alias aliasObj = aliasDao.get(alias);
    if (aliasObj != null && aliasObj.getUsername().equals(principal.getName())) {
      aliasDao.remove(alias);
      return HttpResponse.ok("Alias was successfully deleted");
    }
    return HttpResponse.badRequest("Could not find such alias among your aliases!");
  }

  /**
   * Redirect by alias to a full link.
   *
   * @param alias string, required - alias for the full link.
   * @return Redirect/Error.
   */
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get(value = "/r/{alias}")
  public HttpResponse<Object> redirectToUrl(String alias) {
    Alias aliasObj = aliasDao.get(alias);
    logger.info(alias);
    if (aliasObj != null) {
      try {
        URL uri = new URL(aliasObj.getUrl());
        return HttpResponse.redirect(uri.toURI());
      } catch (URISyntaxException | MalformedURLException e) {
        e.printStackTrace();
      }
    }
    return HttpResponse.badRequest("Could not find uri with such alias!");
  }
}
