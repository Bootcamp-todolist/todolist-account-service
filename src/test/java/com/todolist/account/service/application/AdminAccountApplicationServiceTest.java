package com.todolist.account.service.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.todolist.account.service.UnitTest;
import com.todolist.account.service.adapter.http.models.AdminLoginCommand;
import com.todolist.account.service.application.models.TokenDTO;
import com.todolist.account.service.domain.AdminAccountService;
import com.todolist.account.service.domain.models.AdminAccount;
import com.todolist.account.service.exception.BusinessException;
import com.todolist.account.service.exception.Error;
import com.todolist.account.service.security.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AdminAccountApplicationServiceTest extends UnitTest {

  @Mock
  private AdminAccountService adminAccountService;
  @Mock
  private TokenUtil tokenUtil;

  @Mock
  private BCryptPasswordEncoder passwordEncoder;

  private AdminAccountApplicationService adminAccountApplicationService;
  @BeforeEach
  void setUp() {
    adminAccountApplicationService = new AdminAccountApplicationService(adminAccountService,
        tokenUtil,passwordEncoder);
  }

  @Test
  void should_login_successfully() {
    String username = "user";
    String password = "password";
    AdminAccount adminAccount = AdminAccount.builder().username(username).password(password)
        .build();
    String token = "token";
    AdminLoginCommand request = AdminLoginCommand.builder().username(username).password(password)
        .build();

    doReturn(adminAccount).when(adminAccountService).findByUsername(username);
    doReturn(true).when(passwordEncoder).matches(any(), any());
    doReturn(token).when(tokenUtil).generateToken(adminAccount);

    TokenDTO tokenDTO = adminAccountApplicationService.login(request);

    assertThat(tokenDTO.getToken()).isEqualTo(token);
    verify(adminAccountService).findByUsername(any());
    verify(tokenUtil).generateToken(any());
  }

  @Test
  void should_401_when_password_incorrect() {
    String username = "user";
    String password = "password";
    String wrongPassword = "wrong-password";
    AdminAccount adminAccount = AdminAccount.builder().username(username).password(password)
        .build();
    AdminLoginCommand request = AdminLoginCommand.builder().username(username)
        .password(wrongPassword)
        .build();

    doReturn(adminAccount).when(adminAccountService).findByUsername(username);
    doReturn(false).when(passwordEncoder).matches(any(), any());

    var exception = catchThrowable(() -> adminAccountApplicationService.login(request));

    var businessException = (BusinessException) exception;
    assertThat(businessException.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(businessException.getError()).isEqualTo(Error.AUTHORIZE_FAILED);
    verify(tokenUtil, never()).generateToken(any());
  }
  @Test
  void should_404_when_user_not_found() {
    String username = "user";
    String password = "password";
    AdminLoginCommand request = AdminLoginCommand.builder().username(username)
        .password(password)
        .build();

    doReturn(null).when(adminAccountService).findByUsername(username);

    var exception = catchThrowable(() -> adminAccountApplicationService.login(request));

    var businessException = (BusinessException) exception;
    assertThat(businessException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(businessException.getError()).isEqualTo(Error.USER_NOT_EXIST);
    verify(tokenUtil, never()).generateToken(any());
  }
}
