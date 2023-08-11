package com.todolist.account.service.adapter.http.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AdminLoginCommand {
  private String username;
  private String password;

}
