package edu.kpi.testcourse.bigtable;

import java.util.Objects;

/**
 * Клас Alias`a.
 */
public class Alias {

  private String shorten;
  private String url;
  private String username;

  /**
   * Об'єкт Alias`a.
   *
   * @param shorten Короткий урл.
   * @param url Оригінальний урл.
   * @param username Юзер хто цей урл зареєстрував.
   *
   */
  public Alias(String shorten, String url, String username) {
    this.shorten = shorten;
    this.url = url;
    this.username = username;
  }

  public String getShorten() {
    return shorten;
  }

  public void setShorten(String shorten) {
    this.shorten = shorten;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Alias alias = (Alias) o;
    return Objects.equals(shorten, alias.shorten) && Objects
      .equals(url, alias.url) && Objects.equals(username, alias.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shorten, url, username);
  }

  @Override
  public String toString() {
    return "Alias{"
      + "shorten='" + shorten + '\''
      + ", url='" + url + '\''
      + ", username='" + username + '\''
      + '}';
  }
}
