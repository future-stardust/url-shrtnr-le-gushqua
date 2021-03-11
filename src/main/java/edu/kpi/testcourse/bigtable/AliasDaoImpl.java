package edu.kpi.testcourse.bigtable;

import edu.kpi.testcourse.Main;
import com.sun.xml.internal.bind.v2.TODO;
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
  public Alias[] getAllByUser(UserDao user) {
    return null;
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
