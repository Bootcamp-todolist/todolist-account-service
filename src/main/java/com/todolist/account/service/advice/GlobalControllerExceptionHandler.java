package com.todolist.account.service.advice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.todolist.account.service.exception.BusinessException;
import com.todolist.account.service.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseBody
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    log.error("Unexpected error: ", e);
    return new ResponseEntity<>(INTERNAL_SERVER_ERROR.getReasonPhrase(), INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResult> handleException(BusinessException e) {
    log.error("Client error: ", e);
    return new ResponseEntity<>(new ErrorResult(e.getError()), e.getHttpStatus());
  }
}
