package edu.kpi.testcourse.bigtable;

import com.google.gson.reflect.TypeToken;
import edu.kpi.testcourse.Main;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
    ArrayList<Alias> aliases = new ArrayList<>();
    for (Alias alias : map.values()) {
      if (alias.getUsername().equals(userName)) {
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

    Type typeOfAliasMap = new TypeToken<Map<String, Alias>>() { }.getType();
    Map<String, Alias> aliasMap = Main.getGson().fromJson(aliasJson, typeOfAliasMap);
    map.putAll(aliasMap);
  }

  @Override
  public String getFileName() {
    return "alias.json";
  }

}
