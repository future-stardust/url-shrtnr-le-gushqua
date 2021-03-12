package edu.kpi.testcourse.utils;

/**
 ShortenGenerator class has two methods for generating random strings.
 Input: n - length of wanted strings, if empty the value of 8 is using.
 Output: string of wanted length - 8 or any other specified
 */

public class ShortenGenerator {

  static final int  DEFAULT_LENGTH = 8;

  // function to generate a random string of length n
  public static String generate(int n) {

    // chose a Character random from this String
    String alphaString = "abcdefghijklmnopqrstuvxyz";

    // create StringBuffer size of alphaString
    StringBuilder sb = new StringBuilder(n);

    for (int i = 0; i < n; i++) {

      // generate a random number between
      // 0 to AlphaString variable length
      int index = (int) (alphaString.length() * Math.random());

      // add Character one by one in end of sb
      sb.append(alphaString.charAt(index));
    }

    return sb.toString();
  }


  public static String generate() {

    return ShortenGenerator.generate(DEFAULT_LENGTH);
  }

}
