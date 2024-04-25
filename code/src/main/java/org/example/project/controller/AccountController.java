package org.example.project.controller;
import org.apache.catalina.authenticator.SavedRequest;
import org.example.project.dto.AccountDTO;
import org.example.project.model.Account;
import org.example.project.model.CheckingAccount;
import org.example.project.model.SavingsAccount;
import org.example.project.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/mbweb/api/v1/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountDTO>> listAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping("/create/saving")
    public ResponseEntity<AccountDTO> registerAccount(@RequestBody SavingsAccount account) {
        return ResponseEntity.ok(accountService.addSavingAccount(account));
    }

    @PostMapping("/create/checking")
    public ResponseEntity<AccountDTO> registerAccount(@RequestBody CheckingAccount account) {
        return ResponseEntity.ok(accountService.addCheckingAccount(account));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        account.setId(id);
        return ResponseEntity.ok(accountService.updateAccount(account));
    }

    @PutMapping("/withdrew/{id}/{amount}")
    public ResponseEntity<AccountDTO> withdrewMoney(@PathVariable Long id, @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(accountService.makeWithdrawal(id, amount));
    }

    @PutMapping("/deposite/{id}/{amount}")
    public ResponseEntity<AccountDTO> depositMoney(@PathVariable Long id, @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(accountService.makeDeposit(id, amount));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok().build();
    }
}


