package com.todolist.account.service.adapter.http;

import com.todolist.account.service.adapter.http.models.MemberLoginCommand;
import com.todolist.account.service.application.MemberAccountApplicationService;
import com.todolist.account.service.application.models.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberAccountController {

  private final MemberAccountApplicationService memberAccountApplicationService;

  @PostMapping("/login")
  public TokenDTO memberLogin(@RequestBody MemberLoginCommand memberLoginCommand) {
    return memberAccountApplicationService.login(memberLoginCommand);
  }
}
