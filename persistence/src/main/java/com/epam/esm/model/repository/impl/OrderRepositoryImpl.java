package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.mapper.OrderRowMapper;
import com.epam.esm.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.model.repository.OrderQueryHolder.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final OrderRowMapper orderRowMapper;

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate, OrderRowMapper orderRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_ORDERS, orderRowMapper);
    }

    @Override
    public Optional<Order> findOne(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_ORDER_BY_ID, orderRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Order create(Order order) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUser().getId());
            statement.setBigDecimal(2, order.getPrice());
            statement.setTimestamp(3, order.getPurchaseDate());
            return statement;
        }, key);
        order.setId(Objects.requireNonNull(key.getKey()).longValue());
        return order;
    }

    @Override
    public Order update(Order order) {
        throw new UnsupportedOperationException("update order functionality is not supported");
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_ORDER_BY_ID, id) == 1;
    }

    @Override
    public List<Order> findOrdersByUserId(Long id) {
        return jdbcTemplate.query(SQL_FIND_ALL_ORDERS_BY_USER_ID, orderRowMapper, id);
    }

    @Override
    public boolean linkOrderWithCertificates(Order order) {
        boolean result = false;
        if (order.getCertificates() != null) {
            order.getCertificates().forEach(certificate -> {
                jdbcTemplate.update(con -> {
                    PreparedStatement statement = con.prepareStatement(SQL_LINK_ORDER_WITH_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, order.getId());
                    statement.setLong(2, certificate.getId());
                    return statement;
                });
            });
            result = true;
        }
        return result;
    }

    @Override
    public boolean unlinkOrderWithCertificates(Long id) {
        return jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(SQL_UNLINK_ORDER_WITH_CERTIFICATES);
            statement.setLong(1, id);
            return statement;
        }) >= 1;
    }
}
