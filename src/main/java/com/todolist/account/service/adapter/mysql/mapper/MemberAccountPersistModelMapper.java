package com.todolist.account.service.adapter.mysql.mapper;

import com.todolist.account.service.adapter.mysql.models.MemberAccountPersistModel;
import com.todolist.account.service.domain.models.MemberAccount;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MemberAccountPersistModelMapper {
  public static final MemberAccountPersistModelMapper MAPPER = Mappers.getMapper(
      MemberAccountPersistModelMapper.class);


  public abstract MemberAccountPersistModel toPersistModel(MemberAccount memberAccount);

  @InheritInverseConfiguration
  public abstract MemberAccount toDomain(MemberAccountPersistModel memberAccountPersistModel);
}
