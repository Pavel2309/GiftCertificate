package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.mapper.CertificateRowMapper;
import com.epam.esm.model.query.impl.CertificateQueryBuilder;
import com.epam.esm.model.repository.CertificateRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.model.repository.CertificateQueryHolder.*;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CertificateRowMapper certificateRowMapper;
    private final CertificateQueryBuilder queryBuilder;

    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate,
                                     CertificateRowMapper certificateRowMapper,
                                     CertificateQueryBuilder queryBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateRowMapper = certificateRowMapper;
        this.queryBuilder = queryBuilder;
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_CERTIFICATES, certificateRowMapper);
    }

    @Override
    public List<Certificate> findAllWithParameters(Map<String, String> parameters) {
        String query = queryBuilder.buildQuery(SQL_FIND_ALL_CERTIFICATES_WITH_TAGS, parameters);
        List<String> arguments = queryBuilder.extractQueryArguments(parameters);
        return jdbcTemplate.query(query, certificateRowMapper, arguments.toArray());
    }

    @Override
    public Optional<Certificate> findOne(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_CERTIFICATE_BY_ID, certificateRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(SQL_CREATE_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
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
        jdbcTemplate.update(SQL_UPDATE_CERTIFICATE,
                certificate.getTitle(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateTime(),
                certificate.getUpdateTime(),
                certificate.getId());
        return certificate;
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_CERTIFICATE, id) == 1;
    }

    @Override
    public boolean linkCertificateWithTags(Certificate certificate) {
        boolean result = false;
        if (certificate.getTags() != null) {
            certificate.getTags().forEach(tag -> {
                jdbcTemplate.update(con -> {
                    PreparedStatement statement = con.prepareStatement(SQL_LINK_CERTIFICATE_WITH_TAG, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, certificate.getId());
                    statement.setLong(2, tag.getId());
                    return statement;
                });
            });
            result = true;
        }
        return result;
    }

    @Override
    public boolean unlinkCertificateWithTags(Certificate certificate) {
        return jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(SQL_UNLINK_CERTIFICATE_WITH_TAG);
            statement.setLong(1, certificate.getId());
            return statement;
        }) >= 1;
    }

}
