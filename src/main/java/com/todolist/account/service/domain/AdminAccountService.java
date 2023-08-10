package com.todolist.account.service.domain;

import com.todolist.account.service.domain.models.AdminAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAccountService {
  private final AdminAccountRepository adminAccountRepository;
  public AdminAccount findByUsername(String username) {
    return adminAccountRepository.findByUsername(username);
  }

  public void save(AdminAccount account) {
    adminAccountRepository.save(account);
  }
}
