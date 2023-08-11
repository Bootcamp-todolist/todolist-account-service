package com.todolist.account.service.domain.models;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAccount extends UserAccount {

  private String username;

  private String password;

  private boolean isDeleted;

  private Instant createdTime;

  private String createdBy;

  private Instant updatedTime;

  private String updatedBy;
}
