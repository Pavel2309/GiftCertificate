package com.epam.esm.model.repository;

public final class CertificateQueryHolder {

    public static final String SQL_FIND_ALL = """
            SELECT certificates.id, certificates.title, certificates.description,
                 certificates.price, certificates.duration, certificates.create_date,
                 certificates.update_date
            FROM certificates
            """;

    private CertificateQueryHolder() {
    }
}
