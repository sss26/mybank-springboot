package com.smthasa.mybankspringboot.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Transaction {

    private String id;

    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmZ")
    private ZonedDateTime timestamp;

    private String reference;

    private String slogan;

    private String receivingUser;

    public Transaction() {

    }

    public Transaction(BigDecimal amount, ZonedDateTime timestamp, String reference, String slogan, String receivingUser) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.timestamp = timestamp;
        this.reference = reference;
        this.slogan = slogan;
        this.receivingUser = receivingUser;

    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getReference() {
        return reference;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getReceivingUser() {
        return receivingUser;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }

}
