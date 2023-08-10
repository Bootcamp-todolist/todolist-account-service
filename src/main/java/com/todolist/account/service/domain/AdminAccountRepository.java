package com.todolist.account.service.domain;

import com.todolist.account.service.domain.models.AdminAccount;

public interface AdminAccountRepository {

  AdminAccount findByUsername(String username);

  void save(AdminAccount account);
}
