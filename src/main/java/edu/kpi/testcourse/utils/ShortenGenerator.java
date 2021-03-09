package edu.kpi.testcourse.utils;

public class ShortenGenerator {

  // function to generate a random string of length n
  static String generate(int n) {

      // chose a Character random from this String
      String AlphaNumericString = "abcdefghijklmnopqrstuvxyz";

      // create StringBuffer size of AlphaNumericString
      StringBuilder sb = new StringBuilder(n);

      for (int i = 0; i < n; i++) {

        // generate a random number between
        // 0 to AlphaNumericString variable length
        int index
          = (int)(AlphaNumericString.length()
          * Math.random());

        // add Character one by one in end of sb
        sb.append(AlphaNumericString
          .charAt(index));
      }

      return sb.toString();
  }

  static String generate() {

    final int n = 8;
    // chose a Character random from this String
    String AlphaNumericString = "abcdefghijklmnopqrstuvxyz";

    // create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(n);

    for (int i = 0; i < n; i++) {

      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index
        = (int)(AlphaNumericString.length()
        * Math.random());

      // add Character one by one in end of sb
      sb.append(AlphaNumericString
        .charAt(index));
    }

    return sb.toString();
  }

}
