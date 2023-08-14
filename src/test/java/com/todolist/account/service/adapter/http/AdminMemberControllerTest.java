package com.todolist.account.service.adapter.http;

import static com.todolist.account.service.common.Constant.USER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.account.service.HttpControllerTest;
import com.todolist.account.service.application.MemberAccountApplicationService;
import com.todolist.account.service.application.models.CreateMemberCommand;
import com.todolist.account.service.application.models.MemberAccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(AdminMemberController.class)
class AdminMemberControllerTest extends HttpControllerTest {

  @MockBean
  private MemberAccountApplicationService memberAccountApplicationService;

  @Test
  void should_create_member_successfully() throws Exception {
    String username = "user";
    String password = "password";
    CreateMemberCommand command = CreateMemberCommand.builder()
        .username(username).password(password).build();
    String admin = "admin";
    MemberAccountDTO memberAccount = MemberAccountDTO.builder().build();

    when(memberAccountApplicationService.createMember(command, admin)).thenReturn(memberAccount);

    mockMvc.perform(post("/admin/member")
            .header(USER_ID, admin)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(command)))
        .andExpect(status().isCreated());
    verify(memberAccountApplicationService).createMember(any(CreateMemberCommand.class),
        anyString());
  }

  @Test
  void should_get_all_members_successfully() throws Exception {
    mockMvc.perform(get("/admin/members"))
        .andExpect(status().isOk());
    verify(memberAccountApplicationService).getAllMembers();
  }

  @Test
  void should_delete_member_successfully() throws Exception {
    doNothing().when(memberAccountApplicationService).deleteMember(anyString(),anyString());

    mockMvc.perform(delete("/admin/member/1")
            .header(USER_ID, "admin"))
        .andExpect(status().isOk());
    verify(memberAccountApplicationService).deleteMember(anyString(),anyString());
  }
}
