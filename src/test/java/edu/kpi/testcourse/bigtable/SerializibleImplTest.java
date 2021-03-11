package edu.kpi.testcourse.bigtable;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SerializibleImplTest {

  @Test
  void checkUserToJsonSerialize(){
    UserDao dao = new UserDaoImpl();
    dao.put("test@mail.com", "hash");

    String json = dao.toJson();

    assertThat(json).isEqualTo("""
      {"test@mail.com":"hash"}""");
    assertThat(json).isNotEqualTo("""
      {"test@mail.com":"hashh"}""");
  }

  @Test
  void checkTokenToJsonSerialize(){
    TokenDao dao = new TokenDaoImpl();
    dao.add("test@mail.com", "asd");

    String json = dao.toJson();

    assertThat(json).isEqualTo("""
      {"test@mail.com":["asd"]}""");
    assertThat(json).isNotEqualTo("""
      {"test@mail.com":["asdd"]}""");
  }

  @Test
  void checkAliasToJsonSerialize(){
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

}
