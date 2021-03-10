package edu.kpi.testcourse.bigtable;

public interface Serializible {

  String toJson();

  void fromJson(String json);

  String getFileName();

}
