package com.todolist.account.service.application.models;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAccountDTO {

  private String id;
  private String username;

  private boolean deleted;

  private Instant createdTime;

  private String createdBy;

}
