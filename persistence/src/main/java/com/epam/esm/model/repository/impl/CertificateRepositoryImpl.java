package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.mapper.CertificateRowMapper;
import com.epam.esm.model.repository.CertificateRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.model.repository.CertificateQueryHolder.SQL_CREATE;
import static com.epam.esm.model.repository.CertificateQueryHolder.SQL_FIND_ALL;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CertificateRowMapper certificateRowMapper;

    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate, CertificateRowMapper certificateRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateRowMapper = certificateRowMapper;
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, certificateRowMapper);
    }

    @Override
    public Optional<Certificate> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, certificate.getTitle());
            statement.setString(2, certificate.getDescription());
            statement.setBigDecimal(3, certificate.getPrice());
            statement.setInt(4, certificate.getDuration());
            statement.setObject(5, certificate.getCreateTime());
            statement.setObject(6, certificate.getUpdateTime());
            return statement;
        }, key);
        certificate.setId(Objects.requireNonNull(key.getKey()).longValue());
        return certificate;
    }

    @Override
    public Certificate update(Certificate certificate) {
        return null;
    }

    @Override
    public Certificate delete(Certificate certificate) {
        return null;
    }
}
