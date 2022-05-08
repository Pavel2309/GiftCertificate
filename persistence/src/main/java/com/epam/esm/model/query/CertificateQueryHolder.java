package com.epam.esm.model.query;

public final class CertificateQueryHolder {

    public static final String SQL_FIND_ALL_CERTIFICATES = """
            SELECT certificates.id, certificates.title, certificates.description,
                 certificates.price, certificates.duration, certificates.create_date,
                 certificates.update_date
            FROM certificates
            """;
    public static final String SQL_FIND_ALL_CERTIFICATES_WITH_TAGS = """
            SELECT certificates.id, certificates.title, certificates.description,
                 certificates.price, certificates.duration, certificates.create_date,
                 certificates.update_date
            FROM certificates
            INNER JOIN certificates_has_tags ON certificates.id = certificates_has_tags.certificates_id
            INNER JOIN tags ON certificates_has_tags.tags_id = tags.id
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

    public static final String SQL_FIND_CERTIFICATES_BY_ORDER_ID = """
            SELECT certificates.id, certificates.title, certificates.description, certificates.price, certificates.duration, certificates.create_date, certificates.update_date
            FROM certificates
            INNER JOIN orders_has_certificates ohc on certificates.id = ohc.certificates_id
            WHERE ohc.orders_id = ?
            """;

    public static final String HQL_FIND_BY_ORDER_ID = "SELECT o.certificates FROM Order o WHERE o.user.id = :id";
    public static final String HQL_DELETE_BY_ID = "DELETE FROM Certificate c WHERE c.id = :id";

    private CertificateQueryHolder() {
    }
}
