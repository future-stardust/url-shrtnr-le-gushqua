package edu.kpi.testcourse.bigtable;

import java.util.Map;

/**
 * Dao for users.
 */
public interface AliasDao {

  void add(String shorten, Alias alias);

  Alias get(String shorten);

  Map<String, Alias> getAll();

  String toJson();

  void fromJson();

  String getFileName();

}
