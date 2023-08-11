package com.todolist.account.service.adapter.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.account.service.HttpControllerTest;
import com.todolist.account.service.adapter.http.models.AdminLoginRequest;
import com.todolist.account.service.application.AdminAccountApplicationService;
import com.todolist.account.service.application.models.TokenDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(AdminAccountController.class)
class AdminAccountControllerTest extends HttpControllerTest {
  @MockBean
  private AdminAccountApplicationService adminAccountApplicationService;

  @Test
  void should_return_TokenDTO_when_login_successful() throws Exception {
    TokenDTO tokenDTO = TokenDTO.builder().token("tokenDTO").build();
    AdminLoginRequest loginRequest = AdminLoginRequest.builder().username("name")
        .password("password").build();

    when(adminAccountApplicationService.adminLogin(any())).thenReturn(tokenDTO);

    mockMvc.perform(post("/admin/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("tokenDTO"));
    verify(adminAccountApplicationService).adminLogin(any());
  }
}
