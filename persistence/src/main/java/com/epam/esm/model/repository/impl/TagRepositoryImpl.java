package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.mapper.TagRowMapper;
import com.epam.esm.model.repository.TagRepository;
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

import static com.epam.esm.model.repository.TagQueryHolder.*;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_TAGS, tagRowMapper);
    }

    @Override
    public Optional<Tag> findOne(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_TAG_BY_ID, tagRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(SQL_CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getTitle());
            return statement;
        }, key);
        tag.setId(Objects.requireNonNull(key.getKey()).longValue());
        return tag;
    }

    @Override
    public Tag update(Tag tag) {
        jdbcTemplate.update(SQL_UPDATE_TAG, tag.getTitle(), tag.getId());
        return tag;
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_TAG, id) == 1;
    }

    @Override
    public Optional<Tag> findByTitle(String title) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_TAG_BY_TITLE, tagRowMapper, title));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findByCertificateId(Long id) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_CERTIFICATE_ID, tagRowMapper, id);
    }
}
