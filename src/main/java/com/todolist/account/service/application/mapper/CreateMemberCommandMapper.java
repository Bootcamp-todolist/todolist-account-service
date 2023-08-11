package com.todolist.account.service.application.mapper;

import com.todolist.account.service.application.models.CreateMemberCommand;
import com.todolist.account.service.domain.models.MemberAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CreateMemberCommandMapper {

  public static final CreateMemberCommandMapper MAPPER = Mappers.getMapper(
      CreateMemberCommandMapper.class);

  @Mapping(target = "createdBy", source = "userId")
  @Mapping(target = "updatedBy", source = "userId")
  public abstract MemberAccount toDomain(CreateMemberCommand createMemberCommand, String userId);
}
