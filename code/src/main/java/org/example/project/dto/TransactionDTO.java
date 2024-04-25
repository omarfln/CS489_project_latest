package org.example.project.dto;

import lombok.Data;
import org.example.project.model.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private String type;
    private BigDecimal amount;
    private LocalDateTime date;
    private AccountDTO account;
}
