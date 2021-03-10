package edu.kpi.testcourse.bigtable;

import edu.kpi.testcourse.Main;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;


@Singleton
class AliasDaoImpl implements AliasDao {

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

  @Override
  public String toJson() {
    Map<String, Alias> users = this.getAll();
    return Main.getGson().toJson(users);
  }

  @Override
  public void fromJson(String aliasJson) {
    HashMap<String, Alias> aliasMap = Main.getGson().fromJson(aliasJson, HashMap.class);
    map.putAll(aliasMap);
  }

  @Override
  public String getFileName() {
    return null;
  }

}
