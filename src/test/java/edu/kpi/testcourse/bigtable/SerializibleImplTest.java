package edu.kpi.testcourse.bigtable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class SerializibleImplTest {

  @Test
  void checkUserToJsonSerialize() {
    UserDao dao = new UserDaoImpl();
    dao.put("test@mail.com", "hash");

    String json = dao.toJson();

    assertThat(json).isEqualTo("""
      {"test@mail.com":"hash"}""");
    assertThat(json).isNotEqualTo("""
      {"test@mail.com":"hashh"}""");
  }

  @Test
  void checkJsonToUserDeserialize() {
    UserDao userdao = new UserDaoImpl();
    userdao.fromJson("{test@mail.com:hash}");
    assertThat(userdao.get("test@mail.com")).isEqualTo("hash");
  }

  @Test
  void checkTokenToJsonSerialize() {
    TokenDao dao = new TokenDaoImpl();
    dao.add("test@mail.com", "asd");

    String json = dao.toJson();

    assertThat(json).isEqualTo("""
      {"test@mail.com":["asd"]}""");
    assertThat(json).isNotEqualTo("""
      {"test@mail.com":["asdd"]}""");
  }

  @Test
  void checkAliasToJsonSerialize() {
    AliasDao dao = new AliasDaoImpl();
    String shrt = "shrt";
    Alias alias = new Alias(shrt, "longUrl", "itsMeMario");
    dao.add(shrt, alias);
    String json = dao.toJson();

    assertThat(json).isEqualTo("""
      {"shrt":{"shorten":"shrt","url":"longUrl","username":"itsMeMario"}}""");
    assertThat(json).isNotEqualTo("""
      {"shrt":{"shorten":"shrt","url":"longUrl","username":"itsMeWario"}}""");
  }

  @Test
  void checkJsonToAliasDeserialize() {
    AliasDao dao = new AliasDaoImpl();
    dao.fromJson("""
      {"shrt":{"shorten":"shrt","url":"longUrl","username":"itsMeMario"}}""");
    String shrt = "shrt";
    Alias etalon = new Alias(shrt, "longUrl", "itsMeMario");
    Alias fromJson = dao.get(shrt);
    assertThat(fromJson).isEqualTo(etalon);

  }

}
