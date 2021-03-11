package edu.kpi.testcourse.bigtable;

import edu.kpi.testcourse.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;


@Singleton
class AliasDaoImpl implements AliasDao {

  private final Map<String, Alias> map = new HashMap<>();

  @Override
  public void add(String shorten, Alias alias) {
    map.put(shorten, alias);
  }

  @Override
  public void remove(String alias) {
    map.remove(alias);
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
  public ArrayList<Alias> getAllByUser(String userName) {
    ArrayList<Alias> aliases = null;
    for (Alias alias:map.values()
    ) {
      if (alias.username() == userName) {
        aliases.add(alias);
      }
    }
    return aliases;
  }

  @Override
  public String toJson() {
    Map<String, Alias> users = this.getAll();
    return Main.getGson().toJson(users);
  }

  @Override
  public void fromJson(String aliasJson) {
    Map<String, Alias> aliasMap = Main.getGson().fromJson(aliasJson, Map.class);
    map.putAll(aliasMap);
  }

  @Override
  public String getFileName() {
    return "alias.json";
  }

}
