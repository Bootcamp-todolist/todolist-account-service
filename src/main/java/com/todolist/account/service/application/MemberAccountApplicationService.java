package com.todolist.account.service.application;

import com.todolist.account.service.application.mapper.CreateMemberCommandMapper;
import com.todolist.account.service.application.mapper.MemberAccountDTOMapper;
import com.todolist.account.service.application.models.CreateMemberCommand;
import com.todolist.account.service.application.models.MemberAccountDTO;
import com.todolist.account.service.domain.MemberAccountService;
import com.todolist.account.service.domain.enums.Role;
import com.todolist.account.service.domain.models.MemberAccount;
import com.todolist.account.service.exception.BusinessException;
import com.todolist.account.service.exception.Error;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAccountApplicationService {

  private final MemberAccountService memberAccountService;

  private final BCryptPasswordEncoder passwordEncoder;

  @Transactional
  public MemberAccount createMember(CreateMemberCommand createMemberCommand, String userId) {
    String password = createMemberCommand.getPassword();
    createMemberCommand.setPassword(passwordEncoder.encode(password));
    MemberAccount memberAccount = CreateMemberCommandMapper.MAPPER.toDomain(createMemberCommand,
        userId);
    memberAccount.setRole(Role.USER);
    return memberAccountService.save(memberAccount);
  }

  public List<MemberAccountDTO> getAllMembers() {
    List<MemberAccount> memberAccounts = memberAccountService.findAll();
    return MemberAccountDTOMapper.MAPPER.toDTO(memberAccounts);
  }

  public void deleteMember(String memberId, String userId) {
    MemberAccount memberAccount = memberAccountService.findById(memberId);
    if (Objects.isNull(memberAccount)) {
      throw new BusinessException(Error.USER_NOT_EXIST, HttpStatus.NOT_FOUND);
    }
    memberAccount.setDeleted(true);
    memberAccount.setUpdatedBy(userId);
    memberAccountService.save(memberAccount);
  }
}
