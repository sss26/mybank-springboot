package com.smthasa.mybankspringboot.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.smthasa.mybankspringboot.model.Transaction;

@Component
public class TransactionService {

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    private List<Transaction> transactions = new CopyOnWriteArrayList<Transaction>();

    private final String bankSlogan;

    public TransactionService(@Value("${bank.slogan}") String bankSlogan) {
        this.bankSlogan = bankSlogan;
    }

    public List<Transaction> findAll() {
        return transactions;
    }

    public Transaction create(BigDecimal amount, String reference, String receivingUser) {
        ZonedDateTime timestamp = LocalDateTime.now().atZone(DEFAULT_ZONE);
        Transaction tx = new Transaction(amount, timestamp, reference, bankSlogan, receivingUser);
        transactions.add(tx);
        return tx;
    }

}
