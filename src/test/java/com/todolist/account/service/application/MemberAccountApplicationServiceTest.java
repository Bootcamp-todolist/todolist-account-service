package com.todolist.account.service.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.todolist.account.service.UnitTest;
import com.todolist.account.service.application.models.CreateMemberCommand;
import com.todolist.account.service.application.models.MemberAccountDTO;
import com.todolist.account.service.domain.MemberAccountService;
import com.todolist.account.service.domain.models.MemberAccount;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class MemberAccountApplicationServiceTest extends UnitTest {

  @Mock
  private MemberAccountService memberAccountService;
  @Mock
  private BCryptPasswordEncoder passwordEncoder;

  private MemberAccountApplicationService memberAccountApplicationService;

  @BeforeEach
  void setUp() {
    memberAccountApplicationService = new MemberAccountApplicationService(memberAccountService,
        passwordEncoder);
  }

  @Test
  void should_create_member_successfully() {
    String username = "user";
    String password = "password";
    CreateMemberCommand command = CreateMemberCommand.builder()
        .username(username).password(password).build();
    String admin = "admin";
    MemberAccount memberAccount = MemberAccount.builder().username(username)
        .password("encode-password")
        .createdBy(admin).build();

    doReturn(memberAccount).when(memberAccountService).save(any());

    MemberAccount member = memberAccountApplicationService.createMember(command, admin);

    assertThat(member).isNotNull();
    verify(passwordEncoder).encode(any());
    verify(memberAccountService).save(any());
  }

  @Test
  void should_get_all_members() {
    MemberAccount memberAccount1 = MemberAccount.builder().username("username")
        .password("encode-password")
        .createdBy("admin").build();
    MemberAccount memberAccount2 = MemberAccount.builder().username("username")
        .password("encode-password")
        .createdBy("admin").build();
    List<MemberAccount> memberAccounts = List.of(memberAccount1, memberAccount2);

    doReturn(memberAccounts).when(memberAccountService).findAll();

    List<MemberAccountDTO> allMembers = memberAccountApplicationService.getAllMembers();

    assertThat(allMembers).usingRecursiveComparison().isEqualTo(memberAccounts);
    verify(memberAccountService).findAll();
  }
}
