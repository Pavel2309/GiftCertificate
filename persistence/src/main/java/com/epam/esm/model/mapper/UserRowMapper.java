package com.epam.esm.model.mapper;

import com.epam.esm.model.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            User user = new User();
            user.setId(rs.getLong(DatabaseColumnName.USER_ID));
            user.setName(rs.getString(DatabaseColumnName.USER_NAME));
            return user;
        } catch (SQLException e) {
            return null;
        }
    }
}
