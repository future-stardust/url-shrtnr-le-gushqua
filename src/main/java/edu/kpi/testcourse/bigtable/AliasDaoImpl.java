package edu.kpi.testcourse.bigtable;

import com.sun.xml.internal.bind.v2.TODO;
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
    return null;
  }

  @Override
  public void fromJson() {

  }

  @Override
  public String getFileName() {
    return null;
  }

}
