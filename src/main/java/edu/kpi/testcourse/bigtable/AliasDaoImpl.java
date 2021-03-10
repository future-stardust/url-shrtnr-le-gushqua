package edu.kpi.testcourse.bigtable;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;


@Singleton
public class AliasDaoImpl implements AliasDao {

  private final Map<String, Alias> map = new HashMap<>();

  @Override
  public void add(String shorten, Alias alias) {
    map.put(shorten, alias);
  }

  @Override
  public Alias get(String shorten) {
    return map.get(shorten);
  }

  @Override
  public Map<String, Alias> getAll() {
    return map;
  }

}
