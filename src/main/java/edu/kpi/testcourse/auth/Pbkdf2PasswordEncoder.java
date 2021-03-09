package edu.kpi.testcourse.auth;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * PBKDF2 salted password hashing.
 */
public class Pbkdf2PasswordEncoder {

  public static final String ALGORITHM = "PBKDF2WithHmacSHA1";

  // The following constants may be changed without breaking existing hashes
  public static final int SALT_BYTES = 16;
  public static final int KEY_LENGTH = 64;
  public static final int ITERATIONS_COUNT = 1000;

  /**
   * Returns a salted PBKDF2 hash of the password.
   *
   * @param password the password to hash
   * @return a salted PBKDF2 hash of the password
   */
  public static String encodePassword(final String password) {
    try {
      byte[] salt = getSalt();
      byte[] hash = pbkdf2(password, salt, ITERATIONS_COUNT, KEY_LENGTH);
      return ITERATIONS_COUNT + ":" + toHex(salt) + ":" + toHex(hash);

    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Validates a password using a PBKDF2 hash.
   *
   * @param originalPassword the password to check
   * @param storedPassword the hash of the stored password
   * @return true if the password is correct, false if not
   */
  public static boolean validatePassword(String originalPassword, String storedPassword) {
    try {
      String[] parts = storedPassword.split(":");
      int iterations = Integer.parseInt(parts[0]);
      byte[] salt = fromHex(parts[1]);
      byte[] hash = fromHex(parts[2]);

      byte[] testHash = pbkdf2(originalPassword, salt, iterations, hash.length);
      return compare(hash, testHash);

    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  private static byte[] pbkdf2(String password, byte[] salt, final int iterations,
      final int keyLength)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength * 8);
    return factory.generateSecret(spec).getEncoded();
  }

  private static byte[] getSalt() throws NoSuchAlgorithmException {
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[SALT_BYTES];
    sr.nextBytes(salt);
    return salt;
  }

  private static boolean compare(byte[] hash, byte[] testHash) {
    int diff = hash.length ^ testHash.length;
    for (int i = 0; i < hash.length && i < testHash.length; i++) {
      diff |= hash[i] ^ testHash[i];
    }
    return diff == 0;
  }

  private static String toHex(byte[] array) {
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    if (paddingLength > 0) {
      return String.format("%0" + paddingLength + "d", 0) + hex;
    } else {
      return hex;
    }
  }

  private static byte[] fromHex(String hex) {
    byte[] bytes = new byte[hex.length() / 2];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return bytes;
  }

}
