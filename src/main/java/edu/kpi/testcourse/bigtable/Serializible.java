package edu.kpi.testcourse.bigtable;

/**
 * Інтерфейс серіалізації.
 */
public interface Serializible {

  String toJson();

  void fromJson(String json);

  String getFileName();

}
