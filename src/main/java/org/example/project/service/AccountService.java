package org.example.project.service;

import org.example.project.dto.AccountDTO;
import org.example.project.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface AccountService {
    AccountDTO addSavingAccount(Account account);
    AccountDTO addCheckingAccount(Account account);

    void deleteAccountById(Long id);
    AccountDTO updateAccount(Account account);
    List<AccountDTO> getAllAccounts();
    void processEndOfMonth();
    AccountDTO makeWithdrawal(Long accountId, BigDecimal amount);
    AccountDTO getAccountById(Long id);
    AccountDTO makeDeposit(Long accountId, BigDecimal amount);

}
