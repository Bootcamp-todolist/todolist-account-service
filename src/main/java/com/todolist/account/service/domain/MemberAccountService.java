package com.todolist.account.service.domain;

import com.todolist.account.service.domain.models.MemberAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAccountService {
  private final MemberAccountRepository memberAccountRepository;
  public MemberAccount save(MemberAccount memberAccount) {
    return memberAccountRepository.save(memberAccount);
  }
}
