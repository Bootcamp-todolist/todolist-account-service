package com.todolist.account.service.adapter.http.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
//@Builder
public class AdminLoginRequest {
  private String username;
  private String password;

}
