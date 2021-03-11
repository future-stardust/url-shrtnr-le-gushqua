package edu.kpi.testcourse.bigtable;

import edu.kpi.testcourse.Main;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;

@Singleton
class TokenDaoImpl implements TokenDao {

  private final Map<String, Set<String>> map = new HashMap<>();

  @Override
  public void add(String email, String invalidToken) {
    Set<String> previousList = map.computeIfAbsent(email, k -> new HashSet<>());
    previousList.add(invalidToken);
  }

  @Override
  public void remove(String email, String invalidToken) {
    if (map.containsKey(email)) {
      map.get(email).remove(invalidToken);
    }
  }

  @Override
  public Set<String> get(String email) {
    return map.get(email);
  }

  @Override
  public Map<String, Set<String>> getAll() {
    return map;
  }

  @Override
  public String toJson() {
    Map<String, Set<String>> users = this.getAll();
    return Main.getGson().toJson(users);
  }

  @Override
  public void fromJson(String tokensJson) {
    Map<String, Set<String>> tokensMap = Main.getGson().fromJson(tokensJson, Map.class);
    map.putAll(tokensMap);
  }

  @Override
  public String getFileName() {
    return "tokens.json";
  }
}
