package com.todolist.account.service.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.todolist.account.service.UnitTest;
import com.todolist.account.service.adapter.http.models.MemberLoginCommand;
import com.todolist.account.service.application.models.CreateMemberCommand;
import com.todolist.account.service.application.models.MemberAccountDTO;
import com.todolist.account.service.application.models.TokenDTO;
import com.todolist.account.service.domain.MemberAccountService;
import com.todolist.account.service.domain.models.MemberAccount;
import com.todolist.account.service.exception.BusinessException;
import com.todolist.account.service.exception.Error;
import com.todolist.account.service.security.TokenUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class MemberAccountApplicationServiceTest extends UnitTest {

  @Mock
  private MemberAccountService memberAccountService;
  @Mock
  private BCryptPasswordEncoder passwordEncoder;
  @Mock
  private TokenUtil tokenUtil;


  private MemberAccountApplicationService memberAccountApplicationService;

  @BeforeEach
  void setUp() {
    memberAccountApplicationService = new MemberAccountApplicationService(memberAccountService,
        passwordEncoder, tokenUtil);
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

    MemberAccountDTO member = memberAccountApplicationService.createMember(command, admin);

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

  @Test
  void should_set_deleted_true_when_delete_member() {
    var captor = ArgumentCaptor.forClass(MemberAccount.class);
    String id = "123";
    String admin = "admin";
    MemberAccount memberAccount = MemberAccount.builder()
        .id(id)
        .username("username")
        .password("encode-password")
        .createdBy(admin).deleted(false).updatedBy("aa").build();

    doReturn(memberAccount).when(memberAccountService).findById(id);

    memberAccountApplicationService.deleteMember(id, admin);
    verify(memberAccountService).findById(any());
    verify(memberAccountService).save(captor.capture());
    assertThat(captor.getValue().isDeleted()).isTrue();
    assertThat(captor.getValue().getUpdatedBy()).isEqualTo(admin);
  }

  @Test
  void should_return_404_when_can_not_found_member() {
    String id = "123";

    doReturn(null).when(memberAccountService).findById(id);

    var exception = catchThrowable(() -> memberAccountApplicationService.deleteMember(id, "admin"));

    var businessException = (BusinessException) exception;
    assertThat(businessException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(businessException.getError()).isEqualTo(Error.USER_NOT_EXIST);
  }


  @Test
  void should_login_successfully() {
    String username = "user";
    String password = "password";
    String token = "token";
    MemberAccount memberAccount = MemberAccount.builder().username(username).password(password)
        .build();
    MemberLoginCommand memberLoginCommand = MemberLoginCommand.builder().username(username)
        .password(password)
        .build();

    doReturn(memberAccount).when(memberAccountService).findByUsernameAndDeletedFalse(username);
    doReturn(true).when(passwordEncoder).matches(any(), any());
    doReturn(token).when(tokenUtil).generateToken(memberAccount);

    TokenDTO tokenDTO = memberAccountApplicationService.login(memberLoginCommand);

    assertThat(tokenDTO.getToken()).isEqualTo(token);
    verify(memberAccountService).findByUsernameAndDeletedFalse(any());
    verify(tokenUtil).generateToken(any());
  }

  @Test
  void should_401_when_password_incorrect() {
    String username = "user";
    String password = "password";
    String wrongPassword = "wrong-password";
    MemberAccount memberAccount = MemberAccount.builder().username(username).password(password)
        .build();
    MemberLoginCommand memberLoginCommand = MemberLoginCommand.builder().username(username)
        .password(wrongPassword)
        .build();

    doReturn(memberAccount).when(memberAccountService).findByUsernameAndDeletedFalse(username);
    doReturn(false).when(passwordEncoder).matches(any(), any());

    var exception = catchThrowable(() -> memberAccountApplicationService.login(memberLoginCommand));

    var businessException = (BusinessException) exception;
    assertThat(businessException.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(businessException.getError()).isEqualTo(Error.AUTHORIZE_FAILED);
    verify(tokenUtil, never()).generateToken(any());
  }

  @Test
  void should_404_when_user_not_found() {
    String username = "user";
    String password = "password";
    MemberLoginCommand memberLoginCommand = MemberLoginCommand.builder().username(username)
        .password(password)
        .build();

    doReturn(null).when(memberAccountService).findByUsernameAndDeletedFalse(username);

    var exception = catchThrowable(() -> memberAccountApplicationService.login(memberLoginCommand));

    var businessException = (BusinessException) exception;
    assertThat(businessException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(businessException.getError()).isEqualTo(Error.USER_NOT_EXIST);
    verify(tokenUtil, never()).generateToken(any());
  }
}
