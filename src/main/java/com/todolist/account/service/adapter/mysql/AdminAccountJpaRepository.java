package com.todolist.account.service.adapter.mysql;

import com.todolist.account.service.adapter.mysql.models.AdminAccountPersistModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminAccountJpaRepository extends JpaRepository<AdminAccountPersistModel,String> {

  AdminAccountPersistModel findByUsername(String username);
}
