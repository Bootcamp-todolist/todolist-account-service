package com.todolist.account.service.adapter.http.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAccountRegisterCommand {

  private String username;
  private String password;

}
