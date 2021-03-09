package edu.kpi.testcourse.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ShortenGeneratorTest {

  @Test
  void checkShortenLen(){
    ShortenGenerator shortenGenerator = new ShortenGenerator();

    String shorten = shortenGenerator.generate(17);

    assertThat(shorten.length()).isEqualTo(17);
  }

  @Test
  void checkEmptyN(){
    ShortenGenerator shortenGenerator = new ShortenGenerator();

    String shorten = shortenGenerator.generate();

    assertThat(shorten.length()).isEqualTo(8);
  }

}
