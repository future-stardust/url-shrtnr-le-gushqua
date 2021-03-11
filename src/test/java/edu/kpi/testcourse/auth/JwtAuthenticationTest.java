package edu.kpi.testcourse.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.text.ParseException;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class JwtAuthenticationTest {

  @Inject
  @Client("/")
  private RxHttpClient client;

  @Test
  public void checkUnauthorized() {
    HttpClientResponseException ex = Assertions.assertThrows(
      HttpClientResponseException.class,
      () -> client.toBlocking().exchange(HttpRequest.GET("/hello"))
    );
    assertThat(ex.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  public void checkAuthorized() throws ParseException {
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
      "sherlock",
      "password"
    );

    HttpResponse<?> signUp = client.toBlocking().exchange(
      HttpRequest.POST("/signup", credentials)
    );
    assertThat(signUp.getStatus()).isEqualTo(HttpStatus.OK);

    HttpResponse<BearerAccessRefreshToken> signIn = client.toBlocking().exchange(
      HttpRequest.POST("/signin", credentials),
      BearerAccessRefreshToken.class
    );
    BearerAccessRefreshToken token = signIn.getBody().orElseThrow();
    assertThat(token.getUsername()).isEqualTo(credentials.getUsername());
    assertThat(JWTParser.parse(token.getAccessToken())).isInstanceOf(SignedJWT.class);

    HttpRequest<?> hello = HttpRequest.GET("/hello")
      .bearerAuth(token.getAccessToken());

    HttpResponse<String> response = client.toBlocking().exchange(hello, String.class);
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
  }

}
