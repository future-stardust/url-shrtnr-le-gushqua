package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.bigtable.Alias;
import edu.kpi.testcourse.bigtable.AliasDao;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
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
  public HttpResponse<Object> shortenUrl(String url, String alias, Principal principal) {
    if (aliasDao.get(alias) == null) {
      Alias aliasObj = new Alias(alias, url, principal.getName());
      aliasDao.add(alias, aliasObj);
      return HttpResponse.ok("A new alias for url was created successfully");
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
  @Delete(value = "/urls/<alias>")
  public HttpResponse<Object> deleteAlias(String alias, Principal principal) {
    Alias aliasObj = aliasDao.get(alias);
    if (aliasObj != null && aliasObj.username().equals(principal.getName())) {
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
  @Get(value = "/r/<alias>", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<Object> redirectToUrl(String alias) {
    Alias aliasObj = aliasDao.get(alias);
    if (aliasObj != null) {
      try {
        URI uri = new URI(aliasObj.url());
        return HttpResponse.redirect(uri);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }
    return HttpResponse.badRequest("Could not find uri with such alias!");
  }
}
