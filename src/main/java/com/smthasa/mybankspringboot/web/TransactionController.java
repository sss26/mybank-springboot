package com.smthasa.mybankspringboot.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smthasa.mybankspringboot.dto.TransactionDto;
import com.smthasa.mybankspringboot.model.Transaction;
import com.smthasa.mybankspringboot.service.TransactionService;

import jakarta.validation.Valid;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return transactionService.findAll();
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return transactionService.create(transactionDto.getAmount(),
                                         transactionDto.getReference(),
                                         transactionDto.getReceivingUser());
    }

}
