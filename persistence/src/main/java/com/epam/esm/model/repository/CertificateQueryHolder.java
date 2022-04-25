package com.epam.esm.model.repository;

public final class CertificateQueryHolder {

    public static final String SQL_FIND_ALL = """
            SELECT certificates.id, certificates.title, certificates.description,
                 certificates.price, certificates.duration, certificates.create_date,
                 certificates.update_date
            FROM certificates
            """;

    public static final String SQL_CREATE = """
            INSERT INTO certificates(title, description, price, duration, create_date, update_date)
            VALUES (?, ?, ?, ?, ?, ?)
            """;


    private CertificateQueryHolder() {
    }
}
