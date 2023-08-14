package com.todolist.account.service.adapter.mysql;

import com.todolist.account.service.adapter.mysql.mapper.MemberAccountPersistModelMapper;
import com.todolist.account.service.adapter.mysql.models.MemberAccountPersistModel;
import com.todolist.account.service.domain.MemberAccountRepository;
import com.todolist.account.service.domain.models.MemberAccount;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberAccountRepositoryImpl implements MemberAccountRepository {

  private final MemberAccountJpaRepository memberAccountJpaRepository;

  @Override
  public MemberAccount save(MemberAccount memberAccount) {
    MemberAccountPersistModel memberAccountPersistModel = memberAccountJpaRepository.save(
        MemberAccountPersistModelMapper.MAPPER.toPersistModel(memberAccount));
    return MemberAccountPersistModelMapper.MAPPER.toDomain(memberAccountPersistModel);
  }

  @Override
  public List<MemberAccount> findAll() {
    return MemberAccountPersistModelMapper.MAPPER.toDomain(memberAccountJpaRepository.findAll());
  }

  @Override
  public MemberAccount findById(String memberId) {
    MemberAccountPersistModel accountPersistModel = memberAccountJpaRepository.findById(
        memberId).orElse(null);
    if (Objects.isNull(accountPersistModel)) {
      return null;
    }
    return MemberAccountPersistModelMapper.MAPPER.toDomain(accountPersistModel);
  }

  @Override
  public MemberAccount findByUsernameAndDeletedFalse(String username) {
    return MemberAccountPersistModelMapper.MAPPER.toDomain(
        memberAccountJpaRepository.findByUsernameAndDeletedFalse(username));
  }
}
