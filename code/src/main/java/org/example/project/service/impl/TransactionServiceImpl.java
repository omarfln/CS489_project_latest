package org.example.project.service.impl;

import org.example.project.dto.AccountDTO;
import org.example.project.dto.TransactionDTO;
import org.example.project.dto.UserDTO;
import org.example.project.model.Account;
import org.example.project.model.Transaction;
import org.example.project.repository.TransactionRepository;
import org.example.project.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }
    @Override
    public TransactionDTO addTransaction(Transaction transaction) {
        return convertToDTO(transactionRepository.save(transaction));
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        AccountDTO accountDTO = new AccountDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress(transaction.getAccount().getUser().getAddress());
        userDTO.setUsername(transaction.getAccount().getUser().getUsername());
        userDTO.setFirstName(transaction.getAccount().getUser().getFirstName());
        userDTO.setLastName(transaction.getAccount().getUser().getLastName());
        userDTO.setAddress(transaction.getAccount().getUser().getAddress());
        accountDTO.setUserDto(userDTO);
        accountDTO.setAccountNumber(transaction.getAccount().getAccountNumber());
        accountDTO.setBalance(transaction.getAccount().getBalance());
        dto.setAccount(accountDTO);
        return dto;
    }

    private TransactionDTO convertToDTOop(Optional<Transaction> transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setDate(transaction.get().getDate());
        dto.setType(transaction.get().getType());
        dto.setAmount(transaction.get().getAmount());

        AccountDTO accountDTO = new AccountDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress(transaction.get().getAccount().getUser().getAddress());
        userDTO.setUsername(transaction.get().getAccount().getUser().getUsername());
        userDTO.setFirstName(transaction.get().getAccount().getUser().getFirstName());
        userDTO.setLastName(transaction.get().getAccount().getUser().getLastName());
        userDTO.setAddress(transaction.get().getAccount().getUser().getAddress());
        accountDTO.setUserDto(userDTO);
        accountDTO.setAccountNumber(transaction.get().getAccount().getAccountNumber());
        accountDTO.setBalance(transaction.get().getAccount().getBalance());
        dto.setAccount(accountDTO);
        return dto;
    }

    @Override
    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public TransactionDTO updateTransaction(Transaction transaction) {
        return convertToDTO(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDTO getTransactionById(Long id) {
        return convertToDTOop(transactionRepository.findById(id));
    }
}
