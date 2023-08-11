package com.todolist.account.service.domain;

import com.todolist.account.service.domain.models.MemberAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAccountRepository {

  MemberAccount save(MemberAccount memberAccount);
}
