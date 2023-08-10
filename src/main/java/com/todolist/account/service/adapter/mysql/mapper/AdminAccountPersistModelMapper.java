package com.todolist.account.service.adapter.mysql.mapper;

import com.todolist.account.service.adapter.mysql.models.AdminAccountPersistModel;
import com.todolist.account.service.domain.models.AdminAccount;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AdminAccountPersistModelMapper {
  public static final AdminAccountPersistModelMapper MAPPER = Mappers.getMapper(
      AdminAccountPersistModelMapper.class);


  public abstract AdminAccountPersistModel toPersistModel(AdminAccount adminAccount);

  @InheritInverseConfiguration
  public abstract AdminAccount toDomain(AdminAccountPersistModel persistModel);

}
