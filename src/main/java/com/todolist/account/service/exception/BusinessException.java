package com.todolist.account.service.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

  private final HttpStatus httpStatus;

  private final Error error;

  public BusinessException(Error error) {
    this(error, HttpStatus.BAD_REQUEST);
  }

  public BusinessException(Error error, HttpStatus httpStatus) {
    this(error, httpStatus, error.getValue());
  }

  protected BusinessException(Error error, HttpStatus httpStatus, String message) {
    super(message);
    this.error = error;
    this.httpStatus = httpStatus;
  }

  public Error getError() {
    return error;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
