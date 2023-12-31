package com.todolist.account.service.adapter.http;

import static com.todolist.account.service.common.Constant.USER_ID;

import com.todolist.account.service.application.MemberAccountApplicationService;
import com.todolist.account.service.application.models.CreateMemberCommand;
import com.todolist.account.service.application.models.MemberAccountDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMemberController {

  private final MemberAccountApplicationService memberAccountApplicationService;

  @PostMapping("/member")
  @ResponseStatus(HttpStatus.CREATED)
  public MemberAccountDTO createMember(@RequestBody CreateMemberCommand createMemberCommand,
      @RequestHeader(USER_ID) String userId) {
    return memberAccountApplicationService.createMember(createMemberCommand, userId);
  }

  @GetMapping("/members")
  public List<MemberAccountDTO> getAllMembers() {
    return memberAccountApplicationService.getAllMembers();
  }

  @DeleteMapping("/member/{id}")
  public void deleteMember(@PathVariable("id") String memberId,
      @RequestHeader(USER_ID) String userId) {
    memberAccountApplicationService.deleteMember(memberId, userId);
  }

}
