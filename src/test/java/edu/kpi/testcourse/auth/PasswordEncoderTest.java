package edu.kpi.testcourse.auth;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class PasswordEncoderTest {

  @Test
  public void checkPasswordMatch() {
    String hash = Pbkdf2PasswordEncoder.encodePassword("pass");
    Assertions.assertTrue(Pbkdf2PasswordEncoder.validatePassword("pass", hash));
  }

  @Test
  public void checkPasswordNonMatch() {
    String hash = Pbkdf2PasswordEncoder.encodePassword("pass1");
    Assertions.assertFalse(Pbkdf2PasswordEncoder.validatePassword("pass2", hash));
  }

}
