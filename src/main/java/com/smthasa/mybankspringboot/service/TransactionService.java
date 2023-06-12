package com.smthasa.mybankspringboot.service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smthasa.mybankspringboot.model.Transaction;

@Component
public class TransactionService {

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    private final JdbcTemplate jdbcTemplate;
    private final String bankSlogan;

    public TransactionService(JdbcTemplate jdbcTemplate, @Value("${bank.slogan}") String bankSlogan) {
        this.jdbcTemplate = jdbcTemplate;
        this.bankSlogan = bankSlogan;
    }

    @Transactional
    public List<Transaction> findAll() {
        String query = "select id, receiving_user, amount, timestamp, reference, slogan from transactions";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            return mapRow(resultSet, rowNum);
        });
    }

    @Transactional
    public List<Transaction> findByReceivingUserId(String userId) {
        String query = "select id, receiving_user, amount, timestamp, reference, slogan "
                + "from transactions where receiving_user = ? ";
        return jdbcTemplate.query(connection -> {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, userId);
            return ps;
        }, (resultSet, rowNum) -> {
            return mapRow(resultSet, rowNum);
        });
    }

    @Transactional
    public Transaction create(BigDecimal amount, String reference, String receivingUser) {
        String query = "insert into transactions (receiving_user, amount, timestamp, reference, slogan)"
                + "values (?, ?, ?, ?, ?)";
        ZonedDateTime timestamp = LocalDateTime.now().atZone(DEFAULT_ZONE);
        Transaction tx = new Transaction(amount, timestamp, reference, bankSlogan, receivingUser);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, receivingUser);
            ps.setBigDecimal(2, amount);
            ps.setTimestamp(3, Timestamp.valueOf(tx.getTimestamp().toLocalDateTime()));
            ps.setString(4, reference);
            ps.setString(5, bankSlogan);
            return ps;
        }, keyHolder);

        String uuid = !keyHolder.getKeys().isEmpty()
                ? ((UUID) keyHolder.getKeys().values().iterator().next()).toString()
                : null;
        tx.setId(uuid);
        return tx;
    }

    private static Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction tx = new Transaction();
        tx.setId(rs.getObject("id").toString());
        tx.setReceivingUser(rs.getString("receiving_user"));
        tx.setAmount(rs.getBigDecimal("amount"));
        tx.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime().atZone(DEFAULT_ZONE));
        tx.setReference(rs.getString("reference"));
        tx.setSlogan(rs.getString("slogan"));
        return tx;
    }

}
