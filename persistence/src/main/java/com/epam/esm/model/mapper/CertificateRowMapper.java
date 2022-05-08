package com.epam.esm.model.mapper;

import com.epam.esm.model.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class CertificateRowMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) {
        try {
            Certificate certificate = new Certificate();
            certificate.setId(rs.getLong(DatabaseColumnName.CERTIFICATE_ID));
            certificate.setTitle(rs.getString(DatabaseColumnName.CERTIFICATE_TITLE));
            certificate.setDescription(rs.getString(DatabaseColumnName.CERTIFICATE_DESCRIPTION));
            certificate.setPrice(rs.getBigDecimal(DatabaseColumnName.CERTIFICATE_PRICE));
            certificate.setDuration(rs.getInt(DatabaseColumnName.CERTIFICATE_DURATION));
            certificate.setCreateDate(rs.getObject(DatabaseColumnName.CERTIFICATE_CREATE_DATE, LocalDateTime.class));
            certificate.setUpdateDate(rs.getObject(DatabaseColumnName.CERTIFICATE_UPDATE_DATE, LocalDateTime.class));
            return certificate;
        } catch (SQLException e) {
            return null;
        }
    }
}
