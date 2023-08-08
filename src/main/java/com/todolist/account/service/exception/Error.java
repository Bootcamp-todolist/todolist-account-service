package com.todolist.account.service.exception;

public enum Error {
  USER_NOT_EXIST("user_not_exist");

  private final String value;

  public String getValue() {
    return value;
  }

  Error(String value) {
    this.value = value;
  }
}
