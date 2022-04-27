package com.epam.esm.model.repository;

public final class CertificateQueryHolder {

    public static final String SQL_FIND_ALL_CERTIFICATES = """
            SELECT certificates.id, certificates.title, certificates.description,
                 certificates.price, certificates.duration, certificates.create_date,
                 certificates.update_date
            FROM certificates
            """;
    public static final String SQL_FIND_CERTIFICATE_BY_ID = """
            SELECT certificates.id, certificates.title, certificates.description,
                 certificates.price, certificates.duration, certificates.create_date,
                 certificates.update_date
            FROM certificates
            WHERE certificates.id = ?
            """;
    public static final String SQL_CREATE_CERTIFICATE = """
            INSERT INTO certificates(title, description, price, duration, create_date, update_date)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    public static final String SQL_DELETE_CERTIFICATE = """
            DELETE FROM certificates
            WHERE certificates.id = ?
            """;
    public static final String SQL_UPDATE_CERTIFICATE = """
            UPDATE certificates
            SET certificates.title = ?, certificates.description = ?,
                 certificates.price = ?, certificates.duration = ?,
                 certificates.create_date = ?, certificates.update_date = ?
            WHERE certificates.id = ?
            """;
    public static final String SQL_LINK_CERTIFICATE_WITH_TAG = """
            INSERT INTO certificates_has_tags (certificates_id, tags_id)
            VALUES (?, ?)
            """;
    public static final String SQL_UNLINK_CERTIFICATE_WITH_TAG = """
            DELETE FROM certificates_has_tags
            WHERE certificates_id = ?
            """;

    private CertificateQueryHolder() {
    }
}
