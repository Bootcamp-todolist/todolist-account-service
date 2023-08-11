package com.todolist.account.service.adapter.mysql;

import com.todolist.account.service.adapter.mysql.models.MemberAccountPersistModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAccountJpaRepository extends JpaRepository<MemberAccountPersistModel,String> {

}
