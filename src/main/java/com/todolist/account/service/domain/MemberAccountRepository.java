package com.todolist.account.service.domain;

import com.todolist.account.service.domain.models.MemberAccount;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAccountRepository {

  MemberAccount save(MemberAccount memberAccount);

  List<MemberAccount> findAll();

  MemberAccount findById(String memberId);

  MemberAccount findByUsername(String username);
}
