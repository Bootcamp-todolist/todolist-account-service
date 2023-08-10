package com.todolist.account.service.adapter.mysql;

import com.todolist.account.service.adapter.mysql.mapper.AdminAccountPersistModelMapper;
import com.todolist.account.service.domain.AdminAccountRepository;
import com.todolist.account.service.domain.models.AdminAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminAccountRepositoryImpl implements AdminAccountRepository {
  private final AdminAccountJpaRepository adminAccountJpaRepository;

  @Override
  public AdminAccount findByUsername(String username) {
    return AdminAccountPersistModelMapper.MAPPER.toDomain(adminAccountJpaRepository.findByUsername(username));
  }

  @Override
  public void save(AdminAccount account) {

    adminAccountJpaRepository.save(AdminAccountPersistModelMapper.MAPPER.toPersistModel(
        account));
  }
}
