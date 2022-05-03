package com.epam.esm.model.mapper;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderRowMapper implements RowMapper<Order> {

    private final UserRowMapper userRowMapper;
    private final CertificateRowMapper certificateRowMapper;

    @Autowired
    public OrderRowMapper(UserRowMapper userRowMapper, CertificateRowMapper certificateRowMapper) {
        this.userRowMapper = userRowMapper;
        this.certificateRowMapper = certificateRowMapper;
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            Order order = new Order();
            order.setId(rs.getLong(DatabaseColumnName.ORDER_ID));
            User user = userRowMapper.mapRow(rs, rowNum);
            order.setUser(user);
            order.setPrice(rs.getBigDecimal(DatabaseColumnName.ORDER_PRICE));
            order.setPurchaseDate(rs.getTimestamp(DatabaseColumnName.ORDER_PURCHASE_DATE));
            return order;
        } catch (SQLException e) {
            return null;
        }
    }
}
