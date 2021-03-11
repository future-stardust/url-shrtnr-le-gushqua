package edu.kpi.testcourse.bigtable;

/**
 * DAO for users.
 */
public interface UserDao {

  void put(String email, String password);

  String get(String email);

}
