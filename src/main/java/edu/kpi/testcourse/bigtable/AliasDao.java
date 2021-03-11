package edu.kpi.testcourse.bigtable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Dao for users.
 */
public interface AliasDao extends Serializible {

  void add(String shorten, Alias alias);

  Alias get(String shorten);

  Map<String, Alias> getAll();

  ArrayList<Alias> getAllByUser(String userName);

  void remove(String alias);
}
