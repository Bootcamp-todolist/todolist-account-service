package com.todolist.account.service.application;

import com.todolist.account.service.adapter.http.models.MemberLoginCommand;
import com.todolist.account.service.application.mapper.CreateMemberCommandMapper;
import com.todolist.account.service.application.mapper.MemberAccountDTOMapper;
import com.todolist.account.service.application.models.CreateMemberCommand;
import com.todolist.account.service.application.models.MemberAccountDTO;
import com.todolist.account.service.application.models.TokenDTO;
import com.todolist.account.service.domain.MemberAccountService;
import com.todolist.account.service.domain.enums.Role;
import com.todolist.account.service.domain.models.MemberAccount;
import com.todolist.account.service.exception.BusinessException;
import com.todolist.account.service.exception.Error;
import com.todolist.account.service.security.TokenUtil;
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

  private final TokenUtil tokenUtil;

  @Transactional
  public MemberAccount createMember(CreateMemberCommand createMemberCommand, String userId) {
    validateUsername(createMemberCommand);
    String password = createMemberCommand.getPassword();
    createMemberCommand.setPassword(passwordEncoder.encode(password));
    MemberAccount memberAccount = CreateMemberCommandMapper.MAPPER.toDomain(createMemberCommand,
        userId);
    memberAccount.setRole(Role.USER);
    return memberAccountService.save(memberAccount);
  }

  private void validateUsername(CreateMemberCommand createMemberCommand) {
    String username = createMemberCommand.getUsername();
    MemberAccount memberAccountByUsername = memberAccountService.findByUsername(username);
    if (Objects.nonNull(memberAccountByUsername)){
      throw new BusinessException(Error.REPEATED_USER_NAME,HttpStatus.NOT_FOUND);
    }
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

  public TokenDTO login(MemberLoginCommand memberLoginCommand) {
    String username = memberLoginCommand.getUsername();
    MemberAccount memberAccount = memberAccountService.findByUsername(username);
    verifyMemberAccount(memberLoginCommand,memberAccount);

    return new TokenDTO(tokenUtil.generateToken(memberAccount));
  }

  private void verifyMemberAccount(MemberLoginCommand memberLoginCommand, MemberAccount memberAccount) {
    if (Objects.isNull(memberAccount)) {
      throw new BusinessException(Error.USER_NOT_EXIST, HttpStatus.NOT_FOUND);
    }
    boolean isPasswordCorrect = passwordEncoder.matches(memberLoginCommand.getPassword(),
        memberAccount.getPassword());
    if (Boolean.FALSE.equals(isPasswordCorrect)) {
      throw new BusinessException(Error.AUTHORIZE_FAILED, HttpStatus.UNAUTHORIZED);
    }
  }
}
