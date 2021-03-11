package edu.kpi.testcourse.bigtable;

import java.util.Map;

/**
 * Dao for users.
 */
public interface AliasDao extends Serializible {

  void add(String shorten, Alias alias);

  Alias get(String shorten);

  Map<String, Alias> getAll();

  Alias[] getAllByUser(UserDao user);

}
