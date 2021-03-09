package edu.kpi.testcourse.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

/**
 ShortenGeneratorTest class has two methods for testing generating random strings.
 checkShortenLen: checks for generating proper length
 checkEmptyN: checks if method works with value by default
 */

public class ShortenGeneratorTest {

  @Test
  void checkShortenLen() {
    ShortenGenerator shortenGenerator = new ShortenGenerator();

    String shorten = shortenGenerator.generate(17);

    assertThat(shorten.length()).isEqualTo(17);
  }

  @Test
  void checkEmptyN() {
    ShortenGenerator shortenGenerator = new ShortenGenerator();

    String shorten = shortenGenerator.generate();

    assertThat(shorten.length()).isEqualTo(8);
  }

}
