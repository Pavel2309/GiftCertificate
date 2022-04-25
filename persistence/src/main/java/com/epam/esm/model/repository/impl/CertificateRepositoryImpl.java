package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.mapper.CertificateRowMapper;
import com.epam.esm.model.repository.CertificateRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        return null;
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
