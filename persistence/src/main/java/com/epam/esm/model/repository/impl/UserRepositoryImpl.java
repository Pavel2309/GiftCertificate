package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.mapper.UserRowMapper;
import com.epam.esm.model.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.model.repository.UserQueryHolder.SQL_FIND_ALL_USERS;
import static com.epam.esm.model.repository.UserQueryHolder.SQL_FIND_USER_BY_ID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_USERS, userRowMapper);
    }

    @Override
    public Optional<User> findOne(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("create user functionality is not supported");
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException("update user functionality is not supported");

    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("delete user functionality is not supported");
    }
}
