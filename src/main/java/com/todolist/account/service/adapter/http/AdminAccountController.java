package com.todolist.account.service.adapter.http;

import static com.todolist.account.service.common.Constant.USER_ID;

import com.todolist.account.service.adapter.http.models.AdminAccountRegisterCommand;
import com.todolist.account.service.adapter.http.models.AdminLoginRequest;
import com.todolist.account.service.application.AdminAccountApplicationService;
import com.todolist.account.service.application.models.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAccountController {

  private final AdminAccountApplicationService adminAccountApplicationService;

  @PostMapping("/login")
  public TokenDTO adminLogin(@RequestBody AdminLoginRequest adminLoginRequest) {
    return adminAccountApplicationService.adminLogin(adminLoginRequest);
  }

  @PostMapping("/register")
  public void registerAdminAccount(@RequestBody AdminAccountRegisterCommand registerCommand,
      @RequestHeader(USER_ID) String userId) {
    adminAccountApplicationService.register(registerCommand, userId);
  }
}
