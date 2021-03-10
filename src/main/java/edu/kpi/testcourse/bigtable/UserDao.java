package edu.kpi.testcourse.bigtable;

import java.util.Map;

/**
 * DAO for users.
 */
public interface UserDao {

  void put(String email, String password);

  String get(String email);

  Map<String, String> getAll();

  void putAll(Map<String, String> map);

  String toJson();

  void fromJson();

  String getFileName();

}
