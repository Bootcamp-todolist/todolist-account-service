package com.todolist.account.service.adapter.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.account.service.HttpControllerTest;
import com.todolist.account.service.adapter.http.models.MemberLoginCommand;
import com.todolist.account.service.application.MemberAccountApplicationService;
import com.todolist.account.service.application.models.TokenDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(MemberAccountController.class)
class MemberAccountControllerTest extends HttpControllerTest {
  @MockBean
  private MemberAccountApplicationService memberAccountApplicationService;

  @Test
  void should_return_TokenDTO_when_member_login_successful() throws Exception {
    TokenDTO tokenDTO = TokenDTO.builder().token("tokenDTO").build();
    MemberLoginCommand loginCommand = MemberLoginCommand.builder().username("name")
        .password("password").build();

    when(memberAccountApplicationService.login(any())).thenReturn(tokenDTO);

    mockMvc.perform(post("/member/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(loginCommand)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("tokenDTO"));
    verify(memberAccountApplicationService).login(any());
  }
}
