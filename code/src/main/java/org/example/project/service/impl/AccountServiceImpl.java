package org.example.project.service.impl;

import org.example.project.dto.AccountDTO;
import org.example.project.dto.UserDTO;
import org.example.project.model.*;
import org.example.project.repository.AccountRepository;
import org.example.project.service.AccountService;
import org.example.project.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionService transactionService){
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }
    @Override
    public AccountDTO addSavingAccount(Account account) {
        account.setType("saving");
        return convertToDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO addCheckingAccount(Account account) {
        account.setType("checking");
        return convertToDTO(accountRepository.save(account));
    }


    @Override
    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDTO updateAccount(Account account) {
        return convertToDTO(accountRepository.save(account));
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        User user = account.getUser();
        UserDTO userDTO = new UserDTO( user.getUsername(), user.getFirstName(), user.getLastName(), user.getAddress());
        dto.setUserDto(userDTO);
        return dto;
    }

    private AccountDTO convertToDTOop(Optional<Account> account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(account.get().getAccountNumber());
        dto.setBalance(account.get().getBalance());
        User user = account.get().getUser();
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getFirstName(), user.getLastName(), user.getAddress());
        dto.setUserDto(userDTO);
        return dto;
    }
    @Override
    public void processEndOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        if (today.equals(lastDayOfMonth)) {
            System.out.println("Applying monthly rate:");
            List<Account> accounts = accountRepository.findAll();
            for (Account account : accounts) {
                if (account.getType().equalsIgnoreCase("saving")) {
                    ((SavingsAccount) account).applyInterest();
                    accountRepository.save(account);

                }
            }
        }
        System.out.println("[Info]: Today is not the last day of the month, won't apply interests on savings");
    }

    @Override
    public AccountDTO makeWithdrawal(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));
        //if (account instanceof CheckingAccount && ((CheckingAccount) account).canWithdraw(amount)) {
        if (account.getType().equalsIgnoreCase( "checking") && ((CheckingAccount) account).canWithdraw(amount)) {
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
            addTrans(account,amount, "WITHDREW");

        } else if (account.getBalance().compareTo(amount) >= 0) {
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
            addTrans(account,amount, "WITHDREW");

        }
        return convertToDTO(account);
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        return convertToDTOop(accountRepository.findById(id));
    }

    @Override
    public AccountDTO makeDeposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));
        account.setBalance(account.getBalance().add(amount));
        addTrans(account,amount, "DEPOSIT");
        return  convertToDTO(account);
    }

    public void addTrans(Account account, BigDecimal amount, String type){
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setType(type);
        transactionService.addTransaction(transaction);
    }
}
