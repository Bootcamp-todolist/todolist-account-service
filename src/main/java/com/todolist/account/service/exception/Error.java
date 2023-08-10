package com.todolist.account.service.exception;

public enum Error {
  USER_NOT_EXIST("user_not_exist"),
  REPEATED_USER_NAME("repeated_user_name"),
  AUTHORIZE_FAILED("authorize_failed");

  private final String value;

  public String getValue() {
    return value;
  }

  Error(String value) {
    this.value = value;
  }
}
