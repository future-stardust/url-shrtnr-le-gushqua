package edu.kpi.testcourse.bigtable;

import edu.kpi.testcourse.Main;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

@Singleton
class UserDaoImpl implements UserDao {

  private final Map<String, String> map = new HashMap<>();

  @Override
  public void put(String email, String password) {
    map.put(email, password);
  }

  @Override
  public String get(String email) {
    return map.get(email);
  }

  @Override
  public Map<String, String> getAll() {
    return map;
  }

  @Override
  public void putAll(Map<String, String> map) {
    this.map.putAll(map);
  }

  @Override
  public String toJson() {
    Map<String, String> users = this.getAll();
    return Main.getGson().toJson(users);
  }

  @Override
  public void fromJson(String usersJson) {
    Map<String, String> usersMap = Main.getGson().fromJson(usersJson, HashMap.class);
    map.putAll(usersMap);
  }

  @Override
  public String getFileName() {
    return "users.json";
  }

}
