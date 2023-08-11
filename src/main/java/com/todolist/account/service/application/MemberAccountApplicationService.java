package com.todolist.account.service.application;

import com.todolist.account.service.application.mapper.CreateMemberCommandMapper;
import com.todolist.account.service.application.mapper.MemberAccountDTOMapper;
import com.todolist.account.service.application.models.CreateMemberCommand;
import com.todolist.account.service.application.models.MemberAccountDTO;
import com.todolist.account.service.domain.MemberAccountService;
import com.todolist.account.service.domain.enums.Role;
import com.todolist.account.service.domain.models.MemberAccount;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
}
