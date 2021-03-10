package edu.kpi.testcourse.bigtable;


/**
 * Об'єкт Alias`a.
 *
 * @param shorten Короткий урл.
 * @param url Оригінальний урл.
 * @param username Юзер хто це урл зареєстрував.
 *
 */
public record Alias(String shorten, String url, String username) {}
