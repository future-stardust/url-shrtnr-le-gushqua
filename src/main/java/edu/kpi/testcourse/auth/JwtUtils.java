package edu.kpi.testcourse.auth;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.generator.claims.JwtClaimsSetAdapter;
import java.text.ParseException;

/**
 * Utils to parse JWT tokens.
 */
public class JwtUtils {

  public static String getToken(String authorization) {
    return authorization.replace("Bearer ", "");
  }

  public static String getUsername(String token) throws ParseException {
    return (String) getClaims(token).get(JwtClaims.SUBJECT);
  }

  public static JwtClaimsSetAdapter getClaims(String token) throws ParseException {
    return parseToken(token);
  }

  private static JwtClaimsSetAdapter parseToken(String token) throws ParseException {
    JWT jwtToken = JWTParser.parse(token);
    return new JwtClaimsSetAdapter(jwtToken.getJWTClaimsSet());
  }

}
