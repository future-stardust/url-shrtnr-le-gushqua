package edu.kpi.testcourse.bigtable;

import java.util.Map;

/**
 * DAO for users.
 */
public interface UserDao extends Serializible {

  void put(String email, String password);

  String get(String email);

  Map<String, String> getAll();

  void putAll(Map<String, String> map);

}
