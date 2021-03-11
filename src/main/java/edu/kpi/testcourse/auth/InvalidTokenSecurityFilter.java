package edu.kpi.testcourse.auth;

import edu.kpi.testcourse.bigtable.TokenDao;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.OncePerRequestHttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.http.filter.ServerFilterPhase;
import io.micronaut.http.hateoas.JsonError;
import java.text.ParseException;
import java.util.Set;
import javax.inject.Inject;
import org.reactivestreams.Publisher;

/**
 * Security Filter to not allow invalid tokens.
 */
@Filter(Filter.MATCH_ALL_PATTERN)
public class InvalidTokenSecurityFilter extends OncePerRequestHttpServerFilter {

  @Inject
  private TokenDao tokenDao;

  private static final Integer ORDER = ServerFilterPhase.SECURITY.order();

  @Override
  public int getOrder() {
    return ORDER;
  }

  @Override
  protected Publisher<MutableHttpResponse<?>> doFilterOnce(HttpRequest<?> request,
      ServerFilterChain chain) {
    try {
      String authorization = request.getHeaders().getAuthorization().orElse(null);

      if (authorization != null) {
        String token = JwtUtils.getToken(authorization);
        String email = JwtUtils.getUsername(token);

        Set<String> invalidTokens = tokenDao.get(email);
        if (invalidTokens != null && invalidTokens.contains(token)) {
          return Publishers
            .just(HttpResponse.unauthorized().body(new JsonError("Token is invalid")));
        }
      }
    } catch (ParseException e) {
      return Publishers.just(HttpResponse.unauthorized());
    }

    return doFilter(request, chain);
  }

}
