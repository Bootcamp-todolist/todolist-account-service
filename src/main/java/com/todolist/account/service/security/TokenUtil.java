package com.todolist.account.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.todolist.account.service.domain.models.UserAccount;
import com.todolist.account.service.exception.BusinessException;
import com.todolist.account.service.exception.Error;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenUtil {

  public String generateToken(UserAccount user) {
    var secret = System.getenv("JWT_SECRET");
    validSecretKey(secret);
    var algorithm = Algorithm.HMAC512(secret);
    return JWT.create()
        .withIssuer("todo-list")
        .withSubject(user.getId())
        .withClaim("role", user.getRole().name())
        .withExpiresAt(Instant.now().plus(12, ChronoUnit.HOURS))
        .sign(algorithm);
  }

  private static void validSecretKey(String secret) {
    if (Strings.isBlank(secret)) {
      log.error("Please set JWT_SECRET");
      throw new BusinessException(Error.AUTHORIZE_FAILED);
    }
  }
}
