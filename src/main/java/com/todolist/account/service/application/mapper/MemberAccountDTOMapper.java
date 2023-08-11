package com.todolist.account.service.application.mapper;

import com.todolist.account.service.application.models.MemberAccountDTO;
import com.todolist.account.service.domain.models.MemberAccount;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MemberAccountDTOMapper {

  public static final MemberAccountDTOMapper MAPPER = Mappers.getMapper(
      MemberAccountDTOMapper.class);

  public abstract List<MemberAccountDTO> toDTO(List<MemberAccount> memberAccounts);
}
