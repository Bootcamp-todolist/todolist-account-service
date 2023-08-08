package com.todolist.account.service.exception;

import lombok.Data;

@Data
public class ErrorResult {

  private String errorKey;

  private String errorMsg;

  public ErrorResult(Error error) {
    this.errorKey = error.getValue();
    this.errorMsg = error.getValue();
  }

  public ErrorResult(Error error, String errorMsg) {
    this.errorKey = error.getValue();
    this.errorMsg = errorMsg;
  }
}
