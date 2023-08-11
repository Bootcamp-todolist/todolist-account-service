package com.todolist.account.service.adapter.mysql.models;

import static jakarta.persistence.EnumType.STRING;

import com.todolist.account.service.domain.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "member_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MemberAccountPersistModel extends Auditable {

  @Id
  @UuidGenerator
  private String id;
  private String username;
  private String password;

  private boolean deleted;
  @Enumerated(STRING)
  private Role role;
}
