package com.todolist.account.service.application;

import com.todolist.account.service.adapter.http.models.AdminAccountRegisterCommand;
import com.todolist.account.service.adapter.http.models.AdminLoginRequest;
import com.todolist.account.service.application.models.TokenDTO;
import com.todolist.account.service.domain.AdminAccountService;
import com.todolist.account.service.domain.enums.Role;
import com.todolist.account.service.domain.models.AdminAccount;
import com.todolist.account.service.exception.BusinessException;
import com.todolist.account.service.exception.Error;
import com.todolist.account.service.security.TokenUtil;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAccountApplicationService {

  private final AdminAccountService adminAccountService;
  private final TokenUtil tokenUtil;
  private final BCryptPasswordEncoder passwordEncoder;

  public TokenDTO adminLogin(AdminLoginRequest adminLoginRequest) {
    String username = adminLoginRequest.getUsername();
    AdminAccount adminAccount = adminAccountService.findByUsername(username);
    verifyAdminAccount(adminLoginRequest, adminAccount);

    return new TokenDTO(tokenUtil.generateToken(adminAccount));
  }

  private void verifyAdminAccount(AdminLoginRequest adminLoginRequest, AdminAccount adminAccount) {
    if (Objects.isNull(adminAccount)) {
      throw new BusinessException(Error.USER_NOT_EXIST, HttpStatus.NOT_FOUND);
    }

    boolean isPasswordCorrect = passwordEncoder.matches(adminLoginRequest.getPassword(),
        adminAccount.getPassword());
    if (Boolean.FALSE.equals(isPasswordCorrect)) {
      throw new BusinessException(Error.AUTHORIZE_FAILED, HttpStatus.UNAUTHORIZED);
    }
  }

  public void register(AdminAccountRegisterCommand registerCommand, String userId) {
    String username = registerCommand.getUsername();
    AdminAccount adminAccount = adminAccountService.findByUsername(username);
    if (Objects.nonNull(adminAccount)) {
      throw new BusinessException(Error.REPEATED_USER_NAME,HttpStatus.BAD_REQUEST);
    }
    String password = passwordEncoder.encode(registerCommand.getPassword());
    AdminAccount account = AdminAccount.builder().role(Role.ADMIN).username(username)
        .password(password).createdBy(userId).build();
    adminAccountService.save(account);
  }
}
